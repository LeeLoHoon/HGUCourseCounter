package edu.handong.analysis.utils;

import java.util.ArrayList;

public class Utils {
	
	
	public static ArrayList<String> getLines(String file,boolean removeHeader){
	//주어진 file path로 csv 파일 읽어서 ArrayList<String> 객체 리턴 즉, 각 원소가 line string	
	//두번째 arg는 true 인 경우 첫라인 포함 x (첫라인은 목록이기 때문)
		return null;
	}
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
	//파라미터로 받은 ArrayList를 targetFileName 파일에 저장
    //(이때, path중 존재하지 않는 디렉터리있으면 FileNotFoundException 발생하는데 directory 자동 생성 논리필요)
	//https://stackoverflow.com/questions/6666303/javas-createnewfile-will-it-also-create-directories
	}
}
