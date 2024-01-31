package app;

import automaton.*;
import automaton.grid.*;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		try {
			Simulation sim = new Simulation(new Automaton("data/majorityH.json"));
			// Grid grid = sim.getAutomaton().getGrid();
			// for(int i = 0; i < grid.getSize(); i++)
			// 	for(int j = 0; j < grid.getSize(); j++)
			// 		grid.setCellState(j, i, 0, ("" + j).charAt(0));
					
			// grid.setCellState(2, 2, 0, 'X');
			// System.out.println(grid);
			// char[] tab = sim.getAutomaton().getGrid().getNeighboursState(2, 2, 0, sim.getAutomaton().getNeighbourhood());
			// for(int i = 0; i < tab.length; i++)
			// 	System.out.println(tab[i]);

			sim.getAutomaton().getGrid().setAllRandom(sim.getAutomaton().getAlphabet());
			sim.run();
		} catch (Exception e) {
			System.out.println("La création de la grille a échoué car " + e.getMessage());
		}
	}

	private static void debugNeighborhood(Simulation sim, int x, int y) {
		Grid grid = sim.getAutomaton().getGrid();
		List<int[]> neighborhood = sim.getAutomaton().getNeighbourhood();

		grid.setCellState(x, y, 0, 'X');

		for(int i = 0; i < neighborhood.size(); i++) {
			int[] coords = neighborhood.get(i);
			int offset = 0;
			if(y % 2 != 0)
				offset = 1;
			grid.setCellState(x + offset + coords[0], y + coords[1], 0, '!');
		}

		int count = 0;
		for(int i = 0; i < neighborhood.size(); i++)
			for(int j = 0; j < neighborhood.size(); j++)
				if(grid.getCellState(j, i, 0) == 'X')
					count++;
		System.out.println(count);

		System.out.println(grid);
	}
}
