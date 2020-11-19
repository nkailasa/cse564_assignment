package com;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Stack;

public class Student implements Runnable {
	Repository repo = Repository.getInstance();
	Double[][] coordinates = repo.getCoordinates();
	int count = repo.getCount();
	ArrayList<Integer> tspNearestNodeList;
	List<Line2D> lines = new ArrayList<Line2D>();
	
	Double totalDistance;
	SortedMap<Double, Integer> sortedDistanceMap = repo.getSortedSolution();
	Map<Integer, List<Line2D>> pathMap = repo.getStudentPathMap();
	private Thread t;
	private int threadName;
	private volatile boolean isStopped = false;
	private Line2D line = new Line2D.Double();

	public Student(int name) {
		threadName = name;
		tspNearestNodeList = new ArrayList<>();
		lines = new ArrayList<Line2D>();
		totalDistance = 0.0;
		// isStopped = true;
		// System.out.println("Creating " + threadName);
	}

//	public void start() {
//		// System.out.println("Starting " + threadName);
//		if (t == null) {
//			t = new Thread(this, String.valueOf(threadName));
//			t.start();
//		}
//	}

	@Override
	public void run() {

			tspNearestNodeList = new TSPNearestNeighbor().tsp(Integer.valueOf(threadName), repo.getAdjMatrix(),
					new Stack<Integer>(), tspNearestNodeList);
			int i = 0;
			while (i < tspNearestNodeList.size() - 1 && !isStopped) {
				Integer coord1 = tspNearestNodeList.get(i);
				Integer coord2 = tspNearestNodeList.get(i + 1);
				Double x1 = coordinates[coord1][0];
				Double y1 = coordinates[coord1][1];
				Double x2 = coordinates[coord2][0];
				Double y2 = coordinates[coord2][1];
				line.setLine(x1, y1, x2, y2);
				lines.add(line);
				System.out.println(threadName + " totalDistance  " + totalDistance);
				totalDistance += Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
				i++;
			}

			if (i >= tspNearestNodeList.size() - 1) {
				sortedDistanceMap.put(totalDistance, threadName);
				pathMap.put(threadName, lines);
				repo.setPathMap(pathMap);
				System.out.println(threadName + " totalDistance  " + totalDistance);
			}
		
	}

	public void freeze() {
		System.out.print("freeze" + threadName);
		isStopped = false;
	}

	public void stop() {
		isStopped = true;
	}
//	public void stop() {
////		t.stop();
////		System.out.print(threadName + "thread " + t.isAlive());
////	}

}
