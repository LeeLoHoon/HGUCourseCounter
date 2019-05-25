package edu.handong.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;

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

		students = loadStudentCourseRecords(lines);

		Map<String, Student> sortedStudents = new TreeMap<String, Student>(students);

		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);

		Utils.writeAFile(linesToBeSaved, resultPath);
	}

	private HashMap<String, Student> loadStudentCourseRecords(ArrayList<String> lines) {
		String a = null;
		HashMap<String, Student> makeStudents = new HashMap<String, Student>();

		for (String line : lines) {
			a = line.trim().split(", ")[0];

			if (makeStudents.containsKey(a)) {
				Course c = new Course(line);
				makeStudents.get(a).addCourse(c);
				makeStudents.get(a).getSemestersByYearAndSemester();

			} else {
				Student b = new Student(a);
				Course c = new Course(line);
				b.addCourse(c);
				b.getSemestersByYearAndSemester();
				makeStudents.put(a, b);
			}
		}

		return makeStudents; 
	}

	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Integer> semester = new ArrayList<Integer>();
		String sen, b, c;
		int q, a, f;

		sen = "StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester";
		result.add(sen);
		for (String Id : sortedStudents.keySet()) {
			f = sortedStudents.get(Id).getSemestersByYearAndSemester().values().size();
			
			for (int k = 1; k < f + 1; k++) {

				q = sortedStudents.get(Id).getSemestersByYearAndSemester().size();
				a = sortedStudents.get(Id).getNumCourseInNthSementer(k);
				b = Id + ", " + String.valueOf(q) + ", " + String.valueOf(k) + ", " + String.valueOf(a);
				result.add(b);
			}

		}

		return result; 
	}
}
