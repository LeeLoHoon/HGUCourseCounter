package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

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
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		//open csv를 사용해서 trim 안쓰고깔끔하게 읽
		students = loadStudentCourseRecords(lines);

		Map<String, Student> sortedStudents = new TreeMap<String, Student>(students);

		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);

		Utils.writeAFile(linesToBeSaved, resultPath);
	}

	private HashMap<String, Student> loadStudentCourseRecords(ArrayList<String> lines) {
		String trimLine = null;
		HashMap<String, Student> makeStudents = new HashMap<String, Student>();

		for (String line : lines) {
			trimLine = line.trim().split(", ")[0];

			if (makeStudents.containsKey(trimLine)) {
				Course newCourse = new Course(line);
				makeStudents.get(trimLine).addCourse(newCourse);
				makeStudents.get(trimLine).getSemestersByYearAndSemester();

			} else {
				Student newStudent = new Student(trimLine);
				Course newCourse = new Course(line);
				newStudent.addCourse(newCourse);
				newStudent.getSemestersByYearAndSemester();
				makeStudents.put(trimLine, newStudent);
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
