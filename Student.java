package com;

import java.util.ArrayList;
import java.util.List;

public class Student implements Runnable {
	Blackboard bb = new Blackboard();
	Double[][] coordinates = Blackboard.coordinates;
	int count = Blackboard.count;
	ArrayList<Integer> tspNearestNodeList;
	List<DrawPath> shapes = new ArrayList<>();
	Double[][] adjacencyMatrix;
	TSPNearestNeighbor nn = new TSPNearestNeighbor();

	private Thread t;
	private int threadName;

	public Student(int name) {
		threadName = name;
		tspNearestNodeList = new ArrayList<>();
		shapes = new ArrayList<DrawPath>();
		System.out.println("Creating " + threadName);
	}

	public void start() {
		System.out.println("Starting " + threadName);
		adjacencyMatrix = nn.generateAdjacencyMatrix(coordinates, count);
		if (t == null) {
			t = new Thread(this, String.valueOf(threadName));
			t.start();
		}
	}

	@Override
	public void run() {
		System.out.println("Running " + threadName);
		this.tspNearestNodeList = nn.tsp(Integer.valueOf(threadName));
		System.out.println(this.tspNearestNodeList);
		for (int i = 0; i < this.tspNearestNodeList.size() - 1; i++) {
			Integer coord1 = this.tspNearestNodeList.get(i);
			Integer coord2 = this.tspNearestNodeList.get(i + 1);
			this.shapes.add(new DrawPath(coordinates[coord1][0], coordinates[coord1][1], coordinates[coord2][0],
					coordinates[coord2][1]));
		}
		System.out.println(this.shapes);
	}

}
