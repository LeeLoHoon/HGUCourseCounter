package edu.handong.analysis.datamodel;

import java.util.ArrayList;

public class Course {
	private String studentld;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	public Course(String[] line) {

		ArrayList<String> element = new ArrayList<String>();
		//String[] s = line.trim().split(", ");
		for (String t : line) {
			element.add(t);
		}
		studentld = element.get(0);
		yearMonthGraduated = element.get(1);
		firstMajor = element.get(2);
		secondMajor = element.get(3);
		courseCode = element.get(4);
		courseName = element.get(5);
		courseCredit = element.get(6);
		yearTaken = Integer.parseInt(element.get(7).trim());
		semesterCourseTaken = Integer.parseInt(element.get(8).trim());
	}

	public int getYearTaken() {
		int year = yearTaken;
		return year;
	}

	public int getSemesterCourseTaken() {
		int semesterCourse = semesterCourseTaken;
		return semesterCourse;
	}
}
