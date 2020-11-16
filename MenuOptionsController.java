package com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	List<DrawPath> shapes = new ArrayList<>();
	JFrame currFrame = Main.frame;

	public MenuOptionsController() {
	}

	public MenuOptionsController(final JFrame frame, List<DrawPath> shapes, JMenuItem openMenuItem,
			JMenuItem saveMenuItem, JMenuItem runMenuItem, JMenuItem stopMenuItem, JMenuItem newMenuItem,
			JMenuItem aboutMenuItem) {
		this.shapes = shapes;
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(frame,
						"Assignment 05 Team Members: \n Nevedita Kailasam \n Kanti Nizampatnam \n Saloni");
			}
		});
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Repository.getInstance().savePointsToRepo(count);
				
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
						String currLine = br.readLine();
						while (currLine != null) {
							currLine = br.readLine();
							if (currLine.contains("DIMENSION")) {
								count = Integer.valueOf(currLine.split(":")[1].trim());
							}
							if (currLine.startsWith("1"))
								break;
						}
						coordinates = Blackboard.populateTable(new Double[count][2], currLine, br, count);
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
//							shapes.add(new DrawPath(coordinates[coord1][0], coordinates[coord1][1],
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

	public List<DrawPath> getShapes() {
		// TODO Auto-generated method stub
		return shapes;
	}

	public Double[][] getCoordinates() {
		// TODO Auto-generated method stub
		return coordinates;
	}
}
