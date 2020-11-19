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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Canvas extends JPanel implements Observer {
	
	
	
	Repository repo;
	public JMenuBar createUI() {
		JMenu fileMenu, projectMenu, aboutMenu;
		JMenuItem openMenuItem, saveMenuItem, runMenuItem, stopMenuItem, newMenuItem, aboutMenuItem;
		fileMenu = new JMenu("File");
		projectMenu = new JMenu("Project");
		aboutMenu = new JMenu("About");

		// create menuitems
		openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new OpenMenuListener());
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new SaveMenuListener());

		newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new NewMenuListener());
		runMenuItem = new JMenuItem("Run");
		runMenuItem.addActionListener(new RunMenuListener());
		stopMenuItem = new JMenuItem("Stop");
		stopMenuItem.addActionListener(new StopMenuListener());

		aboutMenuItem = new JMenuItem("About Team");
		aboutMenuItem.addActionListener(new AboutMenuListener());

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
		repo = Repository.getInstance();
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
		Double[][] coordinates = repo.getCoordinates();
		for (int i = 0; coordinates != null && i < coordinates.length; i++) {
			double x1 = coordinates[i][0];
			double y1 = coordinates[i][1];
			g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 5,5));
		}
		g1.setPaint(Color.RED);
		g1.setStroke(new BasicStroke(3));
		int i = 0;
		List<List<Line2D>> lines = repo.getTopPaths();
		int lineSize = lines==null? 0 : lines.size();
		Color[] colors = {Color.GREEN,Color.YELLOW,Color.PINK}; 
		while(i<lineSize && lines != null) {
			List<Line2D> setOfLines = lines.get(i);
			g1.setPaint(colors[i]);
			for(Line2D coOrd: setOfLines)
				{ g1.draw(coOrd); 
				System.out.println("coOrd of "+i+" "+ coOrd.getX1()+" "+coOrd.getY1()+"| "+
				coOrd.getX2()+" "+coOrd.getY2()); }
			i++;
		}
		Stack<Point> pointStack = repo.getPoints();
		if(pointStack != null)
		for (Point point : pointStack) {
			g1.fillOval(point.x, point.y, 5, 5);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Stack<Point> pointStack = repo.getPoints();
		Double[][] coordinates = repo.getCoordinates();
		if (pointStack != null || coordinates!=null)
			repaint();
	}

}
