package com;

import java.util.ArrayList;
import java.util.Stack;

public class TSPNearestNeighbor {

	public TSPNearestNeighbor() {
	}

	public ArrayList<Integer> tsp(int startCity, Double[][] adjacencyMatrix, Stack<Integer> stack,
			ArrayList<Integer> nnResult) {
		int numberOfNodes = adjacencyMatrix[1].length - 1;
		int[] visited = new int[numberOfNodes + 1];
		visited[1] = 1;
		stack.push(startCity);
		int element, dst = 0, i;
		double min = Double.MAX_VALUE;
		boolean minFlag = false;

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
				minFlag = false;
				continue;
			}
			stack.pop();
		}
		nnResult.add(0, startCity);
		return nnResult;
	}
}
