package app;

import automaton.*;

public class Test {
	public static void main(String[] args) {
		try {
			Simulation simulation = new Simulation(new Automaton("data/1D.json"));
			simulation.getAutomaton().getGrid().setAllRandom(simulation.getAutomaton().getAlphabet());
			simulation.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
