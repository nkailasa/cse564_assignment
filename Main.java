package com;

import java.awt.Dimension;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame {
	// menubar
	static JMenuBar menubar;

	// JMenu
	static JMenu fileMenu, projectMenu, aboutMenu;

	// Menu items
	static JMenuItem openMenuItem, saveMenuItem, runMenuItem, stopMenuItem, newMenuItem, aboutMenuItem;
	static Repository repo = Repository.getInstance();

	// create a frame
	public static JFrame frame;
	public static Double[][] coordinates;
	public static List<List<Line2D>> paths ;
	public static Canvas myPanel;
	static MenuOptionsController listenerObj;

	public static void main(String[] args) {

		// create a frame
		frame = new JFrame("CSE 564");
		// create a menubar
		menubar = new JMenuBar();

		// create a menu
		fileMenu = new JMenu("File");
		projectMenu = new JMenu("Project");
		aboutMenu = new JMenu("About");

		// create menuitems
		openMenuItem = new JMenuItem("Open");
		saveMenuItem = new JMenuItem("Save");

		newMenuItem = new JMenuItem("New");
		runMenuItem = new JMenuItem("Run");
		stopMenuItem = new JMenuItem("Stop");

		aboutMenuItem = new JMenuItem("About Team");

		listenerObj = new MenuOptionsController(frame, paths, openMenuItem, saveMenuItem, runMenuItem, stopMenuItem,
				newMenuItem, aboutMenuItem);

		// add menu items to menu
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);

		projectMenu.add(newMenuItem);
		projectMenu.add(runMenuItem);
		projectMenu.add(stopMenuItem);

		aboutMenu.add(aboutMenuItem);

		// add menu to menu bar
		menubar.add(fileMenu);
		menubar.add(projectMenu);
		menubar.add(aboutMenu);

		myPanel = new Canvas(Repository.getInstance());
		myPanel.setSize(500, 500);
		frame.getContentPane().add(myPanel);

		// add menubar to frame
		frame.setJMenuBar(menubar);
		// set the size of the frame
		frame.setSize(500, 500);
		frame.setVisible(true);

		new Reporter();

	}

	public static void repaintCanvas() {
		paths = repo.getTopPaths();
		coordinates = Repository.coordinates;
		myPanel.setValues(paths, coordinates);
		myPanel.repaint();
	}

	public static void refreshPanel() {
		myPanel.removeAll();
		myPanel.revalidate();
		myPanel.repaint();
	}
}
