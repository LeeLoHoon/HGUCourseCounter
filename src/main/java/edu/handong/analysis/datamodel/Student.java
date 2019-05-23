package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken; //Course instance 추가
	private HashMap<String,Integer> semestersByYearAndSemester; //key: Year-Semester //e.g 2003-1
	
	public Student(String studentld) {
		studentId=studentld;
		coursesTaken=new ArrayList<Course>();
		semestersByYearAndSemester= new HashMap<String,Integer>();
		
	}
	
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		int a,b,q,check,go;
		String c;
		q=coursesTaken.size();
		ArrayList<Course> w = (ArrayList<Course>) coursesTaken.clone();
		Course e =w.get(q-1);
		a=e.getYearTaken();
		b=e.getSemesterCourseTaken();
		//String numStr2 = String.valueOf(numInt);
		c=String.valueOf(a)+"-"+String.valueOf(b);
		if(semestersByYearAndSemester.containsKey(c))
			go=1;
		else {
			check=semestersByYearAndSemester.size();
			semestersByYearAndSemester.put(c,check+1);
			System.out.println(semestersByYearAndSemester.get(c));
		}
		return semestersByYearAndSemester;
		
	}
	public int getNumCourseInNthSementer(int semester) {
		//semestersByYearAndSemester에서 value값이 semester 
		//이고 semester 값이 들어오면 해당 년도 return하고 그년도에 들은 coure갯수 세기
		
		return 1;
	}

	//field에 대한 getter setter는 필요하면 추가
}
