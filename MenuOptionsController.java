package com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;



public class MenuOptionsController {
	Double[][] coordinates;
	int count = 0;
	ArrayList<Integer> tspNearestNodeList;
	List<List<Line2D>> lines = new ArrayList<>();
	JFrame currFrame = Main.frame;

	public MenuOptionsController() {
	}

	public MenuOptionsController(final JFrame frame, List<List<Line2D>> lines, JMenuItem openMenuItem,
			JMenuItem saveMenuItem, JMenuItem runMenuItem, JMenuItem stopMenuItem, JMenuItem newMenuItem,
			JMenuItem aboutMenuItem) {
		this.lines = lines;
		newMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Classroom.kill();
				Main.refreshPanel();
			}
		});
		runMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Classroom.init();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		stopMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Classroom.freeze();
			}
		});
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(frame,
						"Assignment 05 Team Members: \n Nevedita Kailasam \n Kanti Nizampatnam \n Saloni Chudgar");
			}
		});
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Repository.getInstance().savePointsToRepo(count);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();

					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(selectedFile));
						String currLine2D = br.readLine();
						while (currLine2D != null) {
							
							if (currLine2D.contains("DIMENSION")) {
								count = Integer.valueOf(currLine2D.split(":")[1].trim());
							}
							if (currLine2D.startsWith("1"))
								break;
							currLine2D = br.readLine();
						}
						coordinates = Repository.populateTable(new Double[count][2], currLine2D, br, count);
						for (int i = 0; i < coordinates.length; i++) {
							for (int j = 0; j < 2; j++) {
								System.out.print(coordinates[i][j] + " ");
							}
							System.out.println();
						}

//						TSPNearestNeighbour nn = new TSPNearestNeighbour();
//						tspNearestNodeList = nn.generateAdjacencyMatrix(coordinates, count);
//						for (int i = 0; i < tspNearestNodeList.size() - 1; i++) {
//							Integer coord1 = tspNearestNodeList.get(i);
//							Integer coord2 = tspNearestNodeList.get(i + 1);
//							lines.add(new DrawPath(coordinates[coord1][0], coordinates[coord1][1],
//									coordinates[coord2][0], coordinates[coord2][1]));
//						}

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Main.repaintCanvas();

			}
		});
	}

	public List<List<Line2D>> getlines() {
		// TODO Auto-generated method stub
		return lines;
	}

	public Double[][] getCoordinates() {
		// TODO Auto-generated method stub
		return coordinates;
	}
}
