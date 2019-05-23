package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	String studentId;
	ArrayList<Course> coursesTaken; //학생이 들은 수업 목록
	HashMap<String,Integer> semestersByYearAndSemester; //key: Year-Semester //e.g 2003-1
	
	public Student(String studentld) {
		
	}
	
	public void addCourse(Course newRecord) {
		
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		return null;
	}
	public int getNumCourseInNthSementer(int semester) {
		return 1;
	}
	//field에 대한 getter setter는 필요하면 추가
}
