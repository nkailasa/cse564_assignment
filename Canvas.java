package com;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JPanel;

public class Canvas extends JPanel implements Observer {
	Double[][] coordinates;
	List<List<Line2D>> lines;
	Stack<Point> pointStack;
	Repository repo;

	public Canvas(Repository repo) {
		this.repo = repo;
		this.repo.attach(this);
	}

	public Canvas(Double[][] coordinates, List<List<Line2D>> lines) {
		this.coordinates = coordinates;
		this.lines = lines;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();
		g1.translate(width / 2, height / 2);
		g1.scale(1,1);
		g1.translate(-width / 2, -height / 2);
		g1.setPaint(Color.BLUE);
		for (int i = 0; coordinates != null && i < coordinates.length; i++) {
			double x1 = coordinates[i][0];
			double y1 = coordinates[i][1];
			g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 5,5));
		}
		g1.setPaint(Color.RED);
		g1.setStroke(new BasicStroke(3));
		int i = 0;
		int lineSize = lines==null? 0 : lines.size();
		Color[] colors = {Color.GREEN,Color.YELLOW,Color.PINK}; 
		while(i<lineSize && lines != null) {
			List<Line2D> setOfLines = lines.get(i);
			g1.setPaint(colors[i]);
			for(Line2D coOrd: setOfLines)
				{ g1.draw(coOrd); 
				System.out.println("coOrd of "+i+" "+ coOrd); }
			i++;
		}
		if(pointStack != null)
		for (Point point : pointStack) {
			g1.fillOval(point.x, point.y, 5, 5);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		pointStack = this.repo.getPoints();
		if (pointStack != null)
			repaint();
	}

	public void setValues(List<List<Line2D>> paths, Double[][] coordinates2) {
		this.coordinates = coordinates2;
		this.lines = paths;
	}
}
