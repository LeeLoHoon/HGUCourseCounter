package edu.handong.analysis.utils;

import java.util.ArrayList;
import java.io.*;

public class Utils {

	public static ArrayList<String> getLines(String file, boolean removeHeader) {
		BufferedReader br = null;
		ArrayList<String> startArray = new ArrayList();
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (removeHeader == true)
					removeHeader = false;
				else
					startArray.add(line);
			}
		} catch (IOException ioe) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
			}
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
