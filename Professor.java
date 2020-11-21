package com;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.PriorityBlockingQueue;

public class Professor implements Runnable {
boolean isRun=true;

public void setRun(boolean bool) {
	this.isRun = bool;
}
	@Override
	public void run() {
		Repository repo = Repository.getInstance();
		while (isRun) {
			System.out.println("Professor calculates current top distances:");
			List<List<Line2D>> paths = new ArrayList<>();
			PriorityBlockingQueue<TopPaths> top = repo.getTopPathsQ();
			int i = 0;
			for (TopPaths t : top) {
				if (i++ == 3)
					break;
				paths.add(t.getPath());
				System.out.print(" " + t.getDist());
			}
			if(top.size()==repo.getCount()) isRun=false;
			repo.setTopPaths(paths);
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}

	}

}
