package com;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class Main extends JFrame {

	Main() {
		JFrame frame = new JFrame();
		Canvas myPanel = new Canvas();
		Reporter reporter = new Reporter();
		JMenuBar menubar = myPanel.createUI();
		frame.addMouseListener(reporter);
		frame.getContentPane().add(myPanel, BorderLayout.CENTER);
		Repository.getInstance().attach(myPanel);
		frame.setJMenuBar(menubar);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		 new Main();

	}
}
