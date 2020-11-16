package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Blackboard {
	static Double[][] coordinates;
	private static List<DrawPath> shapes = new ArrayList<>();
	protected static JFrame frame = new JFrame();
	static double xmin = Double.MAX_VALUE;
	static double xmax = Double.MIN_VALUE;
	static double ymin = Double.MAX_VALUE;
	static double ymax = Double.MIN_VALUE;
	static ArrayList<Integer> tspNearestNodeList;

	static Double[][] populateTable(Double[][] coordinates, String currLine, BufferedReader br, int count) {
		int idx = 0;
		try {
			String line = currLine;
			File outputfile = new File("C:\\MyOutputFile.txt");
			FileOutputStream outstream = new FileOutputStream(outputfile);
			

			while (idx < count && line != null) {
				outstream.write(line.getBytes());
				outstream.write(Byte.valueOf((byte) '\n'));
				String[] values = line.trim().split(" +");
				if (values.length > 1) {
					coordinates[idx][0] = Double.valueOf(values[1]);
					coordinates[idx++][1] = Double.valueOf(values[2]);
					xmax = Math.max(Double.valueOf(values[1]), xmax);
					ymax = Math.max(Double.valueOf(values[2]), ymax);
				}
				line = br.readLine();
			}

			outstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Double factor = 50.00;
		System.out.println("Dividing factor:" + factor);
		idx = 0;
		for (; idx < count; idx++) {
			for (int j = 0; j < 2; j++) {
				coordinates[idx][j] = coordinates[idx][j] / factor;

			}
		}
		return coordinates;
	}

}
