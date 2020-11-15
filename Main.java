package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	static Double[][] coordinates;
	private static List<DrawPath> shapes = new ArrayList<>();
	protected static JFrame frame = new JFrame();
	static double xmin = Double.MAX_VALUE;
	static double xmax = Double.MIN_VALUE;
	static double ymin = Double.MAX_VALUE;
	static double ymax = Double.MIN_VALUE;
	static ArrayList<Integer> tspNearestNodeList;

	static void readAndPopulate() throws IOException {
		File f = null;
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			f = chooser.getSelectedFile();
		}
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		int lineNum = 1, count = 0;
		String type = "";
		while (lineNum++ < 7) {
			String line = br.readLine();
			String[] params = line.split(":");
			if (params[0].trim().equals("DIMENSION")) {
				count = Integer.valueOf(params[1].trim());
			}
			if (params[0].trim().equals("TYPE")) {
				type = params[1].trim();
			}
		}
		if (type.equals("TSP")) {
			coordinates = new Double[count][2];
			populateTable(coordinates, br, count, false);
			TSPNearestNeighbour nn = new TSPNearestNeighbour();
			tspNearestNodeList = nn.generateAdjacencyMatrix(coordinates, count);
			fr.close();
		}
	}

	private static void populateTable(Double[][] coordinates, BufferedReader br, int count, boolean isAtsp) {
		try {
			String line;
			int idx = 0;
			if (!isAtsp) {
				while (idx < count && (line = br.readLine()) != null) {
					String[] values = line.trim().split(" +");
					if (values.length > 1) {
						coordinates[idx][0] = Double.valueOf(values[1]);
						coordinates[idx++][1] = Double.valueOf(values[2]);
						xmin = Math.min(Double.valueOf(values[1]), xmin);
						xmax = Math.max(Double.valueOf(values[1]), xmax);
						ymin = Math.min(Double.valueOf(values[2]), ymin);
						ymax = Math.max(Double.valueOf(values[2]), ymax);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(xmin + " " + ymin);
		System.out.println(xmax + " " + ymax);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setPreferredSize(new Dimension((int) xmax, (int) ymax));
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();
		g1.translate(width / 2, height / 2);
		g1.scale(-0.1, -0.1);
		g1.translate(-width / 2, -height / 2);
		g1.setPaint(Color.BLUE);
		for (int i = 0; coordinates != null && i < coordinates.length; i++) {
			double x1 = coordinates[i][0];
			double y1 = coordinates[i][1];
			g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 8, 8));
		}
		g1.setPaint(Color.RED);
		int i = 1;
		while (shapes.size() > i) {
			try {
				DrawPath coOrd = shapes.get(i);
				g1.draw(new Line2D.Double(coOrd.getX1(), coOrd.getY1(), coOrd.getX2(), coOrd.getY2()));
				frame.repaint();
				Thread.sleep(10);
				i++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String args[]) throws IOException {

		frame.pack();
		JButton run = new JButton("Run");
		JButton stop = new JButton("Stop");
		JButton open = new JButton("Open");
		open.setBounds(50, 50, 95, 30);
		run.setBounds(150, 50, 95, 30);
		stop.setBounds(150, 50, 95, 30);
		frame.add(open);
		frame.add(run);
		frame.add(stop);
		stop.setVisible(false);

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					readAndPopulate();
					frame.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop.setVisible(true);
				run.setVisible(false);
				for (int i = 0; i < tspNearestNodeList.size() - 1; i++) {
					Integer coord1 = tspNearestNodeList.get(i);
					Integer coord2 = tspNearestNodeList.get(i + 1);
					shapes.add(new DrawPath(coordinates[coord1][0], coordinates[coord1][1], coordinates[coord2][0],
							coordinates[coord2][1]));
				}
			}

		});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop.setVisible(false);
				run.setVisible(true);
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Main());
		frame.setSize(20184, 20184);
		frame.setLocation(2, 20);
		frame.setVisible(true);
	}
}
