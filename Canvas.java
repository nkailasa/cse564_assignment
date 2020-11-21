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
import java.util.Random;
import java.util.Stack;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Canvas extends JPanel implements Observer {

	public JMenuBar createUI() {
		JMenu fileMenu, projectMenu, aboutMenu;
		JMenuItem openMenuItem, saveMenuItem, runMenuItem, stopMenuItem, newMenuItem, aboutMenuItem;
		fileMenu = new JMenu("File");
		projectMenu = new JMenu("Project");
		aboutMenu = new JMenu("About");

		MenuItemListener menuListener = new MenuItemListener();
		// create menuitems
		openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(menuListener);
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(menuListener);

		newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(menuListener);
		runMenuItem = new JMenuItem("Run");
		runMenuItem.addActionListener(menuListener);
		stopMenuItem = new JMenuItem("Stop");
		stopMenuItem.addActionListener(menuListener);

		aboutMenuItem = new JMenuItem("About Team");
		aboutMenuItem.addActionListener(menuListener);

		// add menu items to menu
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);

		projectMenu.add(newMenuItem);
		projectMenu.add(runMenuItem);
		projectMenu.add(stopMenuItem);

		aboutMenu.add(aboutMenuItem);
		JMenuBar menubar = new JMenuBar();
		// add menu to menu bar
		menubar.add(fileMenu);
		menubar.add(projectMenu);
		menubar.add(aboutMenu);

		return menubar;
	}

	public Canvas() {

	}

	protected void paintComponent(Graphics g) {
		Repository repo = Repository.getInstance();
		super.paintComponent(g);
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();
		g1.translate(width / 2, height / 2);
		g1.scale(1, 1);
		g1.translate(-width / 2, -height / 2);
		g1.setPaint(Color.BLUE);
		Double[][] coordinates = repo.getCoordinates();
		for (int i = 0; coordinates != null && i < coordinates.length; i++) {
			double x1 = coordinates[i][0];
			double y1 = coordinates[i][1];
			g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 3, 3));
		}
		
		g1.setStroke(new BasicStroke(1));
		List<List<Line2D>> lines = repo.getTopPaths();
		Color[] colors = { Color.RED, Color.GREEN, Color.PINK ,Color.CYAN };
		for (List<Line2D> list : lines) {
			g1.setPaint(colors[new Random().nextInt(2)]);
			for (Line2D line : list) {
				g1.draw(line);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Repository repo = Repository.getInstance();
		Stack<Point> pointStack = repo.getPoints();
		Double[][] coordinates = repo.getCoordinates();
		if (pointStack != null || coordinates != null)
			repaint();
	}

}
