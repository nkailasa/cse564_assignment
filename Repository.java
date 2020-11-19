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
	private Double[][] coordinates;
	private int count;
	private Double[][] adjMatrix;
	private List<Canvas> observers = new ArrayList<Canvas>();
	private static Repository repoObj;
	private SortedMap<Double, Integer> distanceMap = new TreeMap<>();
	private Map<Integer, List<Line2D>> pathMap = new HashMap<>();
	private List<List<Line2D>> studentPaths;
	Point point;
	Line2D Line2D;
	Stack<Point> pointStack = new Stack<Point>();
	Stack<Line2D> line2DStack = new Stack<Line2D>();

	private void Repository() {
		
	}

	public static Repository getInstance() {
		if (repoObj == null)
			repoObj = new Repository();
		return repoObj;
	}

	public void setLine2Ds(Line2D l) {
		line2DStack.add(l);
	}

	Stack<Point> getPoints() {
		return this.pointStack;

	}

	SortedMap<Double, Integer> getSortedSolution() {
		return this.distanceMap;

	}

	Stack<Line2D> getLine2Ds() {
		return this.line2DStack;

	}

	void populateTable(Double[][] coordinates, String currLine2D, BufferedReader br, int count) {
		int idx = 0;
		double xmax = Double.MIN_VALUE;
		double ymax = Double.MIN_VALUE;
		try {
			String Line2D = currLine2D;
			repoObj.setCoordinates(coordinates);
			repoObj.setCount(count);
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
		Double factor = xmax > 1500 ? 500 : xmax;
		System.out.println("Dividing factor:" + factor);
		System.out.println("count:" + this.count);
		idx = 0;
		for (; idx < this.count; idx++) {
			for (int j = 0; j < 2; j++) {
				this.coordinates[idx][j] = this.coordinates[idx][j] / factor;

			}
		}
		notifyAllObservers();
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return this.count;
	}

	public void setPoints(int x, int y) {
		this.pointStack.add(new Point(x, y));
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

	public void savePointsToRepo() throws IOException {
		String newValues = "";
		int len = coordinates.length;
		RandomAccessFile f;
		try {
			int size = Math.addExact(pointStack.size(), len);
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
			len++;
			newValues += len + " " + p.x + " " + p.y + "\n";
		}

		try {
			Files.write(Paths.get("C:\\MyOutputFile.txt"), newValues.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<Integer, List<Line2D>> getStudentPathMap() {
		return this.pathMap;
	}

	public void setPathMap(Map<Integer, List<Line2D>> pathMap) {
		this.pathMap = pathMap;

	}

	public List<List<Line2D>> getTopPaths() {
		return this.studentPaths;

	}

	public void setTopPaths(List<List<java.awt.geom.Line2D>> paths) {
		this.studentPaths = paths;
		notifyAllObservers();
	}

	public Double[][] getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(Double[][] coOr) {
		this.coordinates = coOr;
	}
	
	public Double[][] getAdjMatrix() {
		return this.adjMatrix;
	}

	public void setAdjMatrix(Double[][] adjMatrix) {
		this.adjMatrix = adjMatrix;
	}

	public void clearAll() {
		this.coordinates = null;
		this.pathMap = new HashMap<>();
		this.studentPaths = null;
		this.distanceMap = new TreeMap<>();
		this.pointStack  = new Stack<Point>();
		notifyAllObservers();
	}
}
