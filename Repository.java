package com;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

import javax.sound.sampled.Line;

public class Repository extends Observable {
	private List<Canvas> observers = new ArrayList<Canvas>();
	private static Repository repoObj;
	//private static PriorityQueue<Double> pq = new PriorityQueue<Double>((a,b) -> {return (int) (a-b);});
	private static SortedMap<Double, Integer> distanceMap  = new TreeMap<>();
	Point point;
	Line line;
	Stack<Point> pointStack = new Stack<Point>();
	Stack<Line> lineStack = new Stack<Line>();

	private void Repository() {
	}

	public static Repository getInstance() {
		if (repoObj == null)
			repoObj = new Repository();
		return repoObj;
	}
 
	public void setLines(Line l) {
		lineStack.add(l);
	}

	Stack<Point> getPoints() {
		return pointStack;

	}
	
	SortedMap<Double, Integer> getSortedSolution() {
		return distanceMap;

	}

	Stack<Line> getLines() {
		return lineStack;

	}

	public void setPoints(int x, int y) {
		pointStack.add(new Point(x,y));
		notifyAllObservers();
	}
	 
	public void notifyAllObservers(){
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
			System.out.println(pointStack.size());
			System.out.println(count);
			
			int size = Math.addExact(pointStack.size(), count);
			System.out.println(size);
			f = new RandomAccessFile(new File("C:\\MyOutputFile.txt"), "rw");
			f.seek(0); // to the beginning
			f.write("DIMENSION : ".getBytes());
			f.write(String.valueOf(size).getBytes());
			f.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Point p: pointStack) {
			count++;
			newValues+=count+" "+p.x+" "+p.y+"\n";
		}
		
		try {
			Files.write(Paths.get("C:\\MyOutputFile.txt"), newValues.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 	
}
