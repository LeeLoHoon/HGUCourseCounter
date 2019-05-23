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
		c=String.valueOf(a)+"-"+String.valueOf(b);
		if(semestersByYearAndSemester.containsKey(c))
			go=1;
		else {
			check=semestersByYearAndSemester.size();
	
		}
		return semestersByYearAndSemester;
		
	}
	public int getNumCourseInNthSementer(int semester) {
		int a,b,c=0;
		String d;
		
		for(Course h:coursesTaken) {
			a=h.getYearTaken();
			b=h.getSemesterCourseTaken();
			d=String.valueOf(a)+"-"+String.valueOf(b);
			if(semestersByYearAndSemester.get(d)==semester) {
				c++;
			}
			
		}

		
		return c;
	}
}
