package app;

import automaton.*;
import automaton.grid.*;

public class Test {
	public static void main(String[] args) {
		try {
			Simulation simulation = new Simulation(new Automaton("data/majority2D.json"));
			simulation.getAutomaton().getGrid().setAllRandom(simulation.getAutomaton().getAlphabet());
			// simulation.getAutomaton().getGrid().setCellState(0, 1, '1');
			simulation.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
