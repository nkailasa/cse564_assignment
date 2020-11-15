package gui;

public class AdjacencyMatrix {

	public static void main(String[] args) {

	}

	public Double[][] generateGraph(Double[][] table, Double[][] graph, int count) {
		System.out.println("Computing. Please wait...");
		Double dist = (Double) 0.0;
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < count; j++) {
				dist = 9999.00;
				if (i != j) {
					dist = findDistance(table[i], table[j]);
				}
				graph[i][j] = dist;
				graph[j][i] = dist;
			}
		}
		return graph;
	}

	static Double findDistance(Double[] table, Double[] table2) {
		Double x1 = table[0], y1 = table[1], x2 = table2[0], y2 = table2[1];
		if (x1 == null || x2 == null || y1 == null || y2 == null)
			return (Double) 0.0;
		return (Double) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
}
