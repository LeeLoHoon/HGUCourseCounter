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

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException("No CLI argument Exception! Please put a file path.");
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		String a=null;
		HashMap<String,Student> makeStudents = new HashMap<String,Student>();

		for(String line:lines) {
			a=line.trim().split(", ")[0];

			if(makeStudents.containsKey(a)) {
				Course c = new Course(line);
				makeStudents.get(a).addCourse(c);
				makeStudents.get(a).getSemestersByYearAndSemester();
				
				
			}
			else {
				Student b = new Student(a);
				Course c = new Course(line);
				b.addCourse(c);
				b.getSemestersByYearAndSemester();
				makeStudents.put(a,b);
			}
		}
		// TODO: Implement this method
		
		return makeStudents; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Integer> semester = new ArrayList<Integer>();
		String sen,b,c;
		int q,a,f;
		
		sen="StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester";
		result.add(sen);
		for (String Id: sortedStudents.keySet()) {
			//TotalNumberOfSemestersRegistered
			//semester= (ArrayList<Integer>) 
			f=sortedStudents.get(Id).getSemestersByYearAndSemester().values().size();
			//해야할일: semester 오름차순 sorting
			//Collections.sort(semester);

			for(int k=1;k<f+1;k++) {
				
				q=sortedStudents.get(Id).getSemestersByYearAndSemester().size();
				//System.out.println(q);
				//System.out.println(k);
				a=sortedStudents.get(Id).getNumCourseInNthSementer(k);
				//해야할일: getNumCourseInNthSementer method 완성
				b=Id+", "+String.valueOf(q)+", "+String.valueOf(k)+", "+String.valueOf(a);
				result.add(b);
				//System.out.println(b);
			}
			
		
			
		}
		// TODO: Implement this method
		
		return result; // do not forget to return a proper variable.
	}
}
