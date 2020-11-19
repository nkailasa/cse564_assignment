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

class OpenMenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			int count = 0;

			try {
				BufferedReader br = new BufferedReader(new FileReader(selectedFile));
				String currLine = br.readLine();
				while (currLine != null) {
					if (currLine.contains("DIMENSION")) {
						count = Integer.valueOf(currLine.split(":")[1].trim());
					}
					if (currLine.startsWith("1"))
						break;
					currLine = br.readLine();
				}
				Repository repo = Repository.getInstance();
				repo.setCount(count);
				repo.populateTable(new Double[count][2], currLine, br, count);
				AdjacencyMatrix am = new AdjacencyMatrix();
				repo.setAdjMatrix(am.generateGraph(repo.getCoordinates(), new Double[count][count], count));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}

class SaveMenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Repository.getInstance().savePointsToRepo();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class NewMenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		Classroom.kill();
	}
}

class RunMenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Classroom.init();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class StopMenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		Classroom.freeze();
	}
}

class AboutMenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(new JFrame(),
				"Assignment 05 Team Members: \n Nevedita Kailasam \n Kanti Nizampatnam \n Saloni Chudgar");
	}
}
