package com;

import java.awt.geom.Line2D;
import java.util.List;

public class TopPaths implements Comparable<TopPaths> {
	private Double dist;
	private List<Line2D> path;

	public TopPaths(Double d, List<Line2D> p) {
		super();
		this.dist = d;
		this.path = p;
	}

	@Override
	public int compareTo(TopPaths o) {
		// TODO Auto-generated method stub
		return this.getDist().compareTo(o.getDist());
	}
	
	public List<Line2D> getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	public Double getDist() {
		// TODO Auto-generated method stub
		return this.dist;
	}
}