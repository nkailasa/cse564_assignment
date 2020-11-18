package com;

import java.util.PriorityQueue;
import java.util.SortedMap;

public class Professor {
	
	public  Professor() {
		int i=0;
		Repository repo = Repository.getInstance();
		SortedMap<Double, Integer> map = repo.getSortedSolution();
		for(Double dist : map.keySet()) {
			if(i++==3) break;
			System.out.println("Top 3 "+map.get(dist));
		}
	}
	

}
