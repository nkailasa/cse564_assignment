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
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class MenuItemListener implements ActionListener {

	String TEAM_MEMBERS = "Assignment 05 Team Members: \n Nevedita Kailasam \n Kanti Nizampatnam \n Saloni Chudgar";

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		Repository repo = Repository.getInstance();
		Classroom classroom = new Classroom();
		switch (action) {

		case "Save":
			try {
				repo.savePointsToRepo();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case "Run":
			classroom.init();
			break;
		case "Stop":
			classroom.freeze();
			break;
		case "New":
			classroom.clearAll();
			break;
		case "About Team":
			JOptionPane.showMessageDialog(new JFrame(), TEAM_MEMBERS);
			break;
		case "Open":
			openFile();
			break;
		default:
			break;

		}
		;

	}

	public void openFile() {
		Repository repo = Repository.getInstance();
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
