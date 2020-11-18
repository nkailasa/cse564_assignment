package com;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Stack;

public class Student implements Runnable {
	Double[][] coordinates = Repository.coordinates;
	int count = Repository.count;
	ArrayList<Integer> tspNearestNodeList;
	List<DrawPath> shapes = new ArrayList<>();
	Double[][] adjacencyMatrix = new TSPNearestNeighbor().generateAdjacencyMatrix(coordinates, count);;
	Double totalDistance;
	SortedMap<Double,Integer> pq = Repository.getInstance().getSortedSolution();
	private Thread t;
	private int threadName;
	boolean isRunning;
	public Student(int name) {
		threadName = name;
		tspNearestNodeList = new ArrayList<>();
		shapes = new ArrayList<DrawPath>();
		totalDistance = 0.0;
		isRunning = true;
		//System.out.println("Creating " + threadName);
	}

	public void start() {
		//System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, String.valueOf(threadName));
			t.start();
		}
	}
	
	

	@Override
	public void run() {
		isRunning = true;
		tspNearestNodeList = new TSPNearestNeighbor().tsp(Integer.valueOf(threadName), adjacencyMatrix, new Stack<Integer>(), tspNearestNodeList);
		System.out.println(tspNearestNodeList);
		for (int i = 0; isRunning && i < tspNearestNodeList.size() - 1; i++) {
			Integer coord1 = tspNearestNodeList.get(i);
			Integer coord2 = tspNearestNodeList.get(i + 1);
			DrawPath path = new DrawPath(coordinates[coord1][0], coordinates[coord1][1], coordinates[coord2][0],
					coordinates[coord2][1]);
			shapes.add(path);
			totalDistance+=path.getDistance();
		}
		pq.put(totalDistance,threadName);
		System.out.println(threadName+" totalDistance  "+ totalDistance);
	}

	public void freeze() {
		System.out.print("freeze"+threadName);
		isRunning = false;
	}

	public void stop() {
		t.stop();
		System.out.print(threadName+"thread "+t.isAlive());
	}

}
