package edu.handong.analysis.utils;

import java.util.ArrayList;
import java.io.*;
import au.com.bytecode.opencsv.CSVReader;

public class Utils {

	public static ArrayList<String[]> getLines(String file,int start, int end, boolean removeHeader) {
		ArrayList<String[]> startArray = new ArrayList();
		try {
			CSVReader reader= new CSVReader(new FileReader(file));
			String nextLine[] = null;
			while((nextLine=reader.readNext())!=null){
				int i =0;
				if (removeHeader == true)
					removeHeader = false;
				else if(Integer.parseInt(nextLine[7].trim())>=start && Integer.parseInt(nextLine[7].trim())<=end) {
					/*while(nextLine[i]!=null) {
						nextLine[i]=nextLine[i].trim();
						System.out.println(nextLine[i]);
						i++;
					}*/
					System.out.println(nextLine[1]);
					startArray.add(nextLine);
				}
			}
		} catch (IOException ioe) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		} catch (Exception e) {
			}
		return startArray;
	}

	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		File file = new File(targetFileName);
		File directory = new File(file.getParentFile().getAbsolutePath());
		directory.mkdirs();
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			for (String l : lines) {
				writer.write(l);
				writer.write("\n");
				writer.flush();
			}
		} catch (IOException ioe) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (Exception e) {
			}
		}
	}
}
