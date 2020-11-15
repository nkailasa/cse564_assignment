package gui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Stack;

public class TSPNearestNeighbour {
	private int numberOfNodes;
	private Stack<Integer> stack;
	private Double[][] adjacencyMatrix;
	private static ArrayList<Integer> nnResult = new ArrayList<Integer>();

	public TSPNearestNeighbour() {
		stack = new Stack<Integer>();
	}

	public ArrayList<Integer> generateAdjacencyMatrix(Double[][] coordinates, int count) {
		AdjacencyMatrix am = new AdjacencyMatrix();
		adjacencyMatrix = am.generateGraph(coordinates, new Double[count][count], count);
		tsp();
		return nnResult;
	}

	public void tsp() {
		numberOfNodes = adjacencyMatrix[1].length - 1;
		int[] visited = new int[numberOfNodes + 1];
		visited[1] = 1;
		stack.push(1);
		int element, dst = 0, i;
		double min = Double.MAX_VALUE;
		boolean minFlag = false;
		System.out.print(1 + "\t");

		while (!stack.isEmpty()) {
			element = stack.peek();
			i = 1;
			min = Integer.MAX_VALUE;
			while (i <= numberOfNodes) {
				double curr = adjacencyMatrix[element][i];
				if (curr > 1.00 && visited[i] == 0) {
					if (min > curr) {
						min = curr;
						dst = i;
						minFlag = true;
					}
				}
				i++;
			}
			if (minFlag) {
				visited[dst] = 1;
				stack.push(dst);
				nnResult.add(dst);
				System.out.print(dst + "\t");
				minFlag = false;
				continue;
			}
			stack.pop();
		}
	}

	public static void main(String... arg) {

		try {
			System.out.println("the citys are visited as follows");

		} catch (InputMismatchException inputMismatch) {
			System.out.println("Wrong Input format");
		}
	}
}
