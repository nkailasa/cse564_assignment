package com;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Student implements Runnable {
	private int id;
	Double totalDistance;
	int extraThreads = 0;
	boolean isRun = true;

	public Student() {
	}

	public Student(int idx, int n) {
		id = idx;
		extraThreads = n;
		totalDistance = 0.0;
	}

	@Override
	public void run() {
		Repository repo = Repository.getInstance();
		Double[][] coordinates = repo.getCoordinates();
		int startId = id, endId = startId + 10,count=repo.getCount();
		if (extraThreads > 0)
			endId = startId + extraThreads;
		while ((startId < endId) && startId<count) {
			if (!isRun) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {

				}
			}

			int i = 1;
			ArrayList<Integer> tspNearestNodeList = new TSPNearestNeighbor().tsp(Integer.valueOf(startId),
					repo.getAdjMatrix(), new Stack<Integer>(), new ArrayList<>());
			List<Line2D> lines = new ArrayList<Line2D>();
			while (i < tspNearestNodeList.size() - 1) {
				Integer coord1 = tspNearestNodeList.get(i);
				Integer coord2 = tspNearestNodeList.get(i + 1);
				Double x1 = coordinates[coord1][0];
				Double y1 = coordinates[coord1][1];
				Double x2 = coordinates[coord2][0];
				Double y2 = coordinates[coord2][1];
				Line2D newline = new Line2D.Double();
				newline.setLine(x1, y1, x2, y2);
				lines.add(newline);
				totalDistance += Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
				i++;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
			if (i >= tspNearestNodeList.size() - 1) {
				repo.setTopPathQ(new TopPaths(totalDistance, lines));
				System.out.println("Total Distance when started at city " + startId + ": " + totalDistance);
			}
			startId++;
		}
	}

	public void setRun(boolean b) {
		// TODO Auto-generated method stub
		isRun = false;
	}
}