package com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame {

	public static void main(String[] args) {

		JFrame frame = new JFrame("CSE 564");
		Canvas myPanel = new Canvas();
		myPanel.setSize(500, 500);

		JMenuBar menubar = myPanel.createUI();
		frame.addMouseListener(new Reporter());
		frame.getContentPane().add(myPanel);
		Repository.getInstance().attach(myPanel);
		frame.setJMenuBar(menubar);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
