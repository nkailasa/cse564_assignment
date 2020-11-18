package com;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

public class Repository extends Observable {
	public static Double[][] coordinates;
	public static int count;
	static double xmin = Double.MAX_VALUE;
	static double xmax = Double.MIN_VALUE;
	static double ymin = Double.MAX_VALUE;
	static double ymax = Double.MIN_VALUE;
	private List<Canvas> observers = new ArrayList<Canvas>();
	private static Repository repoObj;
	// private static PriorityQueue<Double> pq = new PriorityQueue<Double>((a,b) ->
	// {return (int) (a-b);});
	private static SortedMap<Double, Integer> distanceMap = new TreeMap<>();
	private static Map<Integer, List<Line2D>> pathMap = new HashMap<>();
	private static List<List<Line2D>> studentPaths;
	Point point;
	Line2D Line2D;
	Stack<Point> pointStack = new Stack<Point>();
	Stack<Line2D> Line2DStack = new Stack<Line2D>();

	private void Repository() {
	}

	public static Repository getInstance() {
		if (repoObj == null)
			repoObj = new Repository();
		return repoObj;
	}

	public void setLine2Ds(Line2D l) {
		Line2DStack.add(l);
	}

	Stack<Point> getPoints() {
		return pointStack;

	}

	SortedMap<Double, Integer> getSortedSolution() {
		return distanceMap;

	}

	Stack<Line2D> getLine2Ds() {
		return Line2DStack;

	}

	static Double[][] populateTable(Double[][] coordinates, String currLine2D, BufferedReader br, int count) {
		int idx = 0;
		try {
			String Line2D = currLine2D;
			Repository.count = count;
			Repository.coordinates = coordinates;
			File outputfile = new File("C:\\MyOutputFile.txt");
			FileOutputStream outstream = new FileOutputStream(outputfile);
			outstream.write("DIMENSION:".getBytes());
			outstream.write(String.valueOf(count).getBytes());
			outstream.write("    \n".getBytes());
			while (idx < count && Line2D != null) {
				outstream.write(Line2D.getBytes());
				outstream.write(Byte.valueOf((byte) '\n'));
				String[] values = Line2D.trim().split(" +");
				if (values.length > 1) {
					coordinates[idx][0] = Double.valueOf(values[1]);
					coordinates[idx++][1] = Double.valueOf(values[2]);
					xmax = Math.max(Double.valueOf(values[1]), xmax);
					ymax = Math.max(Double.valueOf(values[2]), ymax);
				}
				Line2D = br.readLine();
			}

			outstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Double factor = xmax > 1500 ?  500 : xmax;
		System.out.println("Dividing factor:" + factor);
		System.out.println("count:" + count);
		idx = 0;
		for (; idx < count; idx++) {
			for (int j = 0; j < 2; j++) {
				coordinates[idx][j] = coordinates[idx][j] / factor;

			}
		}
		return coordinates;
	}

	public void setPoints(int x, int y) {
		pointStack.add(new Point(x, y));
		notifyAllObservers();
	}

	public void notifyAllObservers() {
		for (Canvas observer : observers) {
			observer.update(Repository.getInstance(), observer);
		}
	}

	public void attach(Canvas observer) {
		observers.add(observer);
	}

	public void savePointsToRepo(int count) throws IOException {
		String newValues = "";
		RandomAccessFile f;
		try {
			int size = Math.addExact(pointStack.size(), count);
			coordinates = new Double[size][];
			System.out.println(size);
			f = new RandomAccessFile(new File("C:\\MyOutputFile.txt"), "rw");
			f.seek(0); // to the beginning
			f.write("DIMENSION : ".getBytes());
			f.write(String.valueOf(size).getBytes());
			f.write("\n".getBytes());
			f.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Point p : pointStack) {
			count++;
			newValues += count + " " + p.x + " " + p.y + "\n";
		}

		try {
			Files.write(Paths.get("C:\\MyOutputFile.txt"), newValues.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<Integer, List<Line2D>> getStudentPathMap() {
		// TODO Auto-generated method stub
		return pathMap;
	}

	public void setPathMap(Map<Integer, List<Line2D>> pathMap) {
		Repository.pathMap = pathMap;

	}

	public List<List<Line2D>> getTopPaths() {
		return Repository.studentPaths;

	}

	public void setTopPaths(List<List<java.awt.geom.Line2D>> paths) {
		Repository.studentPaths = paths;
		notifyAllObservers();
	}

	public Double[][] getCoordinates() {
		// TODO Auto-generated method stub
		return coordinates;
	}
}
