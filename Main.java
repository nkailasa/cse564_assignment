package com;
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

	// create a frame 
	static JFrame frame; 

	public static void main(String[] args) 
	{ 

		// create a frame 
		frame = new JFrame("CSE 564"); 

		// create a menubar 
		menubar = new JMenuBar(); 

		// create a menu 
		fileMenu = new JMenu("Menu"); 
		projectMenu = new JMenu("Project"); 
		aboutMenu = new JMenu("About"); 

		// create menuitems 
		openMenuItem = new JMenuItem("Open"); 
		saveMenuItem = new JMenuItem("Save");
		
		newMenuItem = new JMenuItem("New"); 
		runMenuItem = new JMenuItem("Run"); 
		stopMenuItem = new JMenuItem("Stop"); 
		
		aboutMenuItem = new JMenuItem("About Team");
		
		MenuListener listenerObj = new MenuListener(frame,openMenuItem, saveMenuItem, runMenuItem, stopMenuItem, newMenuItem, aboutMenuItem);

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

		// add menubar to frame 
		frame.setJMenuBar(menubar); 

		// set the size of the frame 
		frame.setSize(500, 500); 
		frame.setVisible(true); 
	} 
} 
