package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.Utils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class HGUCoursePatternAnalyzer {

	private HashMap<String, Student> students;
	private String inputPath;
	private String outputPath;
	String courseCode;
	private int analysisOption;
	private int startYear;
	private int endYear;
	private boolean help;

	public void run(String[] args) {
		Options options = createOptions();
		if (parseOptions(options, args)) {
			if (help) {
				printHelp(options);
				return;
			}
			if (analysisOption == 1) {
				ArrayList<String[]> lines = Utils.getLines(inputPath, startYear, endYear, true);
				students = loadStudentCourseRecords(lines);
				Map<String, Student> sortedStudents = new TreeMap<String, Student>(students);
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
				Utils.writeAFile(linesToBeSaved, outputPath);
			} else {
				if (courseCode == null) {
					printHelp(options);
					return;
				} else {

					ArrayList<String[]> lines = Utils.getLines(inputPath, startYear, endYear, true);
					String courseName = findCourseName(courseCode, lines);
					HashMap<String, ArrayList<String>> studentNumByYearToSemester = countAllStudents(lines);
					HashMap<String, Integer> choiceStudentNumByYearToSemester = countListenStudents(courseCode, lines);
					Map<String, Integer> sortedChoiceStudentNumByYearToSemester = new TreeMap<String, Integer>(
							choiceStudentNumByYearToSemester);
					ArrayList<String> finalLines = new ArrayList<String>();
					String sen = "Year, Semester, CourseCode, CourseName, TotalStudents, StudentsTaken, Rate";
					finalLines.add(sen);
					for (String yearWithSemester : sortedChoiceStudentNumByYearToSemester.keySet()) {
						String year = yearWithSemester.substring(0, 4);
						String semester = yearWithSemester.substring(5, 6);
						float entire = studentNumByYearToSemester.get(yearWithSemester).size();
						float select = sortedChoiceStudentNumByYearToSemester.get(yearWithSemester);
						float rate = Math.round(((select / entire) * 1000)) / 10F;
						String finalLine = year + ", " + semester + ", " + courseName + ", " + courseCode + ", "
								+ studentNumByYearToSemester.get(yearWithSemester).size() + ", "
								+ sortedChoiceStudentNumByYearToSemester.get(yearWithSemester) + ", " + rate;
						finalLines.add(finalLine);
					}
					Utils.writeAFile(finalLines, outputPath);
				}
			}

		}
	}

	private String findCourseName(String code, ArrayList<String[]> lines) {
		for (String[] line : lines) {
			if (code.equals(line[4])) {
				return line[5];
			}

		}
		return null;
	}

	private HashMap<String, ArrayList<String>> countAllStudents(ArrayList<String[]> checkLines) {
		HashMap<String, ArrayList<String>> numberOfStudents = new HashMap<String, ArrayList<String>>();
		ArrayList<String> studentName = new ArrayList<String>();
		boolean check;
		int count = 1;
		for (String[] line : checkLines) {
			String year = line[7];
			String semester = line[8];
			String yearToSemester = year + "-" + semester;
			if (numberOfStudents.containsKey(yearToSemester)) {
				if (studentName.contains(line[0])) {
					if (numberOfStudents.get(yearToSemester).contains(line[0])) {

					} else {
						numberOfStudents.get(yearToSemester).add(line[0]);
					}
				} else {
					studentName.add(line[0]);
					numberOfStudents.get(yearToSemester).add(line[0]);
				}
			} else {
				if (studentName.contains(line[0])) {
					ArrayList<String> New = new ArrayList<String>();
					numberOfStudents.put(yearToSemester, New);
					numberOfStudents.get(yearToSemester).add(line[0]);
				} else {
					studentName.add(line[0]);
					ArrayList<String> New = new ArrayList<String>();
					numberOfStudents.put(yearToSemester, New);
					numberOfStudents.get(yearToSemester).add(line[0]);
				}
			}
		}
		return numberOfStudents;
	}

	private HashMap<String, Integer> countListenStudents(String code, ArrayList<String[]> checkLines) {
		HashMap<String, Integer> choiceNumberOfStudents = new HashMap<String, Integer>();
		int count = 1;
		for (String[] line : checkLines) {
			if (line[4].equals(code)) {
				String year = line[7];
				String semester = line[8];
				String yearToSemester = year + "-" + semester;
				if (choiceNumberOfStudents.containsKey(yearToSemester)) {
					choiceNumberOfStudents.put(yearToSemester, choiceNumberOfStudents.get(yearToSemester) + 1);
				} else
					choiceNumberOfStudents.put(yearToSemester, count);
			}
		}
		return choiceNumberOfStudents;
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
		int TotalNumberOfSemestersRegistered, NumCoursesTakenInTheSemester, totalSemester;

		sentence = "StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester";
		result.add(sentence);
		for (String Id : sortedStudents.keySet()) {
			totalSemester = sortedStudents.get(Id).getSemestersByYearAndSemester().values().size();

			for (int semester = 1; semester < totalSemester + 1; semester++) {

				TotalNumberOfSemestersRegistered = sortedStudents.get(Id).getSemestersByYearAndSemester().size();
				NumCoursesTakenInTheSemester = sortedStudents.get(Id).getNumCourseInNthSementer(semester);
				fixedLine = Id + ", " + String.valueOf(TotalNumberOfSemestersRegistered) + ", "
						+ String.valueOf(semester) + ", " + String.valueOf(NumCoursesTakenInTheSemester);
				result.add(fixedLine);
			}

		}

		return result;
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			inputPath = cmd.getOptionValue("i");
			outputPath = cmd.getOptionValue("o");
			analysisOption = Integer.parseInt(cmd.getOptionValue("a"));
			if (analysisOption == 2) {
				courseCode = cmd.getOptionValue("c");
			}

			startYear = Integer.parseInt(cmd.getOptionValue("s"));
			endYear = Integer.parseInt(cmd.getOptionValue("e"));
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	private Options createOptions() {
		Options options = new Options();
		options.addOption(Option.builder("i").longOpt("input").desc("Set an input file path").hasArg()
				.argName("Input path").required().build());

		options.addOption(Option.builder("o").longOpt("output").desc("Set an output file path").hasArg()
				.argName("Output path").required().build());

		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year").hasArg()
				.argName("Analysis option").required().build());

		options.addOption(Option.builder("c").longOpt("coursecode").desc("Course code for '-a 2' option").hasArg()
				.argName("course code").build());

		options.addOption(Option.builder("s").longOpt("startyear").desc("Set the start year for analysis e.g., -s 2002")
				.hasArg().argName("Start year for analysis").required().build());

		options.addOption(Option.builder("e").longOpt("endyear").desc("Set the end year for analysis e.g., -e 2005")
				.hasArg().argName("End year for analysis").required().build());

		options.addOption(Option.builder("h").longOpt("help").desc("Show a Help page").build());

		return options;
	}

	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer = "";
		formatter.printHelp("HGU Course Analyzer", header, options, footer, true);
	}

}
