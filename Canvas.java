package com;

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
	List<DrawPath> shapes;
	Stack<Point> pointStack;
	Repository repo;

	public Canvas(Repository repo) {
		this.repo = repo;
		this.repo.attach(this);
	}

	public Canvas(Double[][] coordinates, List<DrawPath> shapes) {
		this.coordinates = coordinates;
		this.shapes = shapes;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();
		g1.translate(width / 2, height / 2);
		g1.scale(-0.1, -0.1);
		g1.translate(-width / 2, -height / 2);
		g1.setPaint(Color.BLUE);
		for (int i = 0; coordinates != null && i < coordinates.length; i++) {
			double x1 = coordinates[i][0];
			double y1 = coordinates[i][1];
			g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 8, 8));
		}
		g1.setPaint(Color.RED);
//		int i = 1;
//		while (shapes != null && shapes.size() > i) {
//			DrawPath coOrd = shapes.get(i);
//			g1.draw(new Line2D.Double(coOrd.getX1(), coOrd.getY1(), coOrd.getX2(), coOrd.getY2()));
//			i++;
//		}
		if(pointStack != null)
		for (Point point : pointStack) {
			g1.fillOval(point.x, point.y, 50, 50);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		pointStack = this.repo.getPoints();
		if (pointStack != null)
			repaint();
	}

	public void setValues(List<DrawPath> shapes2, Double[][] coordinates2) {
		this.coordinates = coordinates2;
		this.shapes = shapes2;
	}
}
