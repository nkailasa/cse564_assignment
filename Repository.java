package com;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

import javax.sound.sampled.Line;

public class Repository extends Observable {
	private List<Canvas> observers = new ArrayList<Canvas>();
	private static Repository repoObj;
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

	public void savePointsToRepo(int count) {
		String newValues = "";
		for(Point p: pointStack) {
			newValues+=count+" "+p.x+" "+p.y+"\n";
			count++;
		}
		
		try {
			Files.write(Paths.get("C:\\MyOutputFile.txt"), newValues.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 	
}
