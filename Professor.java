package com;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class Professor implements Runnable {


	
//	public void start() {
//		// System.out.println("Starting " + threadName);
//		if (t == null) {
//			t = new Thread(this, String.valueOf("prof"));
//			
//		}t.start();
//	}

	@Override
	public void run() {
		int i = 0;
		Repository repo = Repository.getInstance();
		List<List<Line2D>> paths = new ArrayList<>();
		Map<Integer, List<Line2D>> pathMap = repo.getStudentPathMap();
		SortedMap<Double, Integer> map = repo.getSortedSolution();
		for (Double dist : map.keySet()) {
			if (i++ == 3)
				break;
			System.out.print("\nTop three " + map.get(dist));
			paths.add(pathMap.get(map.get(dist)));
		}
		repo.setTopPaths(paths);
	}

	

}
