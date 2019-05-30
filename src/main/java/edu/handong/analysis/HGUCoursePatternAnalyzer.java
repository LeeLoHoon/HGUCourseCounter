package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;
import au.com.bytecode.opencsv.CSVReader;
public class HGUCoursePatternAnalyzer {

	private HashMap<String, Student> students;

	public void run(String[] args) {

		try {
			if (args.length < 2)
				throw new NotEnoughArgumentException("No CLI argument Exception! Please put a file path.");
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		String dataPath = args[0];
		String resultPath = args[1];
		ArrayList<String[]> lines = Utils.getLines(dataPath, true);
		//open csv를 사용해서 trim 안쓰고깔끔하게 읽기
		
		students = loadStudentCourseRecords(lines);
		//lines
		
		Map<String, Student> sortedStudents = new TreeMap<String, Student>(students);

		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);

		Utils.writeAFile(linesToBeSaved, resultPath);
	}

	private HashMap<String, Student> loadStudentCourseRecords(ArrayList<String[]> lines) {
		HashMap<String, Student> makeStudents = new HashMap<String, Student>();

		for (String[] line : lines) {
			
			if (makeStudents.containsKey(line[0])) {
				Course newCourse = new Course(line);
				makeStudents.get(line[0]).addCourse(newCourse);
				makeStudents.get(line[0]).getSemestersByYearAndSemester();

			} else {
				Student newStudent = new Student(line[0]);
				Course newCourse = new Course(line);
				newStudent.addCourse(newCourse);
				newStudent.getSemestersByYearAndSemester();
				makeStudents.put(line[0], newStudent);
			}
		}
		return makeStudents; 
	}

	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> result = new ArrayList<String>();
		String sentence, fixedLine;
		int TotalNumberOfSemestersRegistered, NumCoursesTakenInTheSemester,totalSemester;

		sentence = "StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester";
		result.add(sentence);
		for (String Id : sortedStudents.keySet()) {
			totalSemester = sortedStudents.get(Id).getSemestersByYearAndSemester().values().size();
			
			for (int semester = 1; semester < totalSemester + 1; semester++) {

				TotalNumberOfSemestersRegistered = sortedStudents.get(Id).getSemestersByYearAndSemester().size();
				NumCoursesTakenInTheSemester = sortedStudents.get(Id).getNumCourseInNthSementer(semester);
				fixedLine = Id + ", " + String.valueOf(TotalNumberOfSemestersRegistered) + ", " + String.valueOf(semester) + ", " + String.valueOf(NumCoursesTakenInTheSemester);
				result.add(fixedLine);
			}

		}

		return result; 
	}
}
