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
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class Repository extends Observable {
	private Double[][] coordinates;
	private int count;
	private Double[][] adjMatrix;
	private List<Canvas> observers = new ArrayList<Canvas>();
	private static Repository repoObj;
	PriorityBlockingQueue<TopPaths> topPathQ = new PriorityBlockingQueue<>();
	private List<List<Line2D>> topstudentPaths = new ArrayList<>();
	String filename = "C:\\TSPOutputFile.txt";
	Stack<Point> pointStack = new Stack<Point>();
	Stack<Line2D> line2DStack = new Stack<Line2D>();

	double xmax = Double.MIN_VALUE;
	double ymax = Double.MIN_VALUE;
	double xmin = Double.MAX_VALUE;
	double ymin = Double.MAX_VALUE;

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

	Stack<Line2D> getLine2Ds() {
		return this.line2DStack;

	}

	void populateTable(Double[][] coordinates, String currLine2D, BufferedReader br, int count) {
		int idx = 0;
		try {
			String Line2D = currLine2D;
			repoObj.setCoordinates(coordinates);
			repoObj.setCount(count);
			File outputfile = new File(filename);
			FileOutputStream outstream = new FileOutputStream(outputfile);
			outstream.write("DIMENSION:".getBytes());
			outstream.write(String.valueOf(count).getBytes());
			while (idx < count && Line2D != null) {
				
				String[] values = Line2D.trim().split(" ");
				if (values.length > 1) {
					coordinates[idx][0] = Double.valueOf(values[1]);
					coordinates[idx++][1] = Double.valueOf(values[2]);
					xmax = Math.max(Double.valueOf(values[1]), xmax);
					ymax = Math.max(Double.valueOf(values[2]), ymax);
					xmin = Math.min(Double.valueOf(values[1]), xmin);
					ymin = Math.min(Double.valueOf(values[2]), ymin);
				}
				Line2D = br.readLine();
			}

			outstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(Math.max(xmax, ymax)>500) {
		Double widthOld = xmax - xmin;
		Double heightOld = ymax - ymin;
		System.out.println("count:" + this.count);
		idx = 0;
		for (; idx < this.count; idx++) {
			coordinates[idx][0] = (coordinates[idx][0] * 100) / widthOld;
			coordinates[idx][1] = (coordinates[idx][1] * 100) / heightOld;
		}
		}
		String outputStreamValues = "     \n";
		for (idx = 1; idx <= this.count; idx++) {
			outputStreamValues += ""+idx + " " + coordinates[idx-1][0] + " " + coordinates[idx-1][1] + "\n";
		}

System.out.println(outputStreamValues);
		try {
			Files.write(Paths.get(filename), outputStreamValues.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		Double[][] coordinatesCopy = new Double[count+1][];
		System.arraycopy(coordinates, 0, coordinatesCopy, 0, coordinates.length);
		coordinatesCopy[count] = new Double[2];
		coordinatesCopy[count][0]=(double) x;
		coordinatesCopy[count][1]=(double) y;
		count++;
		setAdjMatrix(new AdjacencyMatrix().generateGraph(coordinatesCopy, new Double[count][count], count));
		this.setCoordinates(coordinatesCopy);
		this.setCount(count);
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
		int len = coordinates.length -pointStack.size() ;
		RandomAccessFile f;
		try {
			f = new RandomAccessFile(new File(filename), "rw");
			f.seek(0); // to the beginning
			f.write("DIMENSION : ".getBytes());
			f.write(String.valueOf(coordinates.length).getBytes());
			f.write("\n".getBytes());
			f.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Point p : pointStack) {
			len++;
			newValues += ""+len + " " + p.x + " " + p.y + "\n";
		}
		try {
			Files.write(Paths.get(filename), newValues.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<List<Line2D>> getTopPaths() {
		return this.topstudentPaths;

	}

	public void setTopPaths(List<List<java.awt.geom.Line2D>> paths) {
		this.topstudentPaths = paths;
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
		this.topstudentPaths.clear();
		this.pointStack.clear();
		this.topPathQ.clear();
		notifyAllObservers();
	}

	public void setTopPathQ(TopPaths q) {
		topPathQ.add(q);
	}

	public PriorityBlockingQueue<TopPaths> getTopPathsQ() {
		return topPathQ;
	}
}
