package app;

import automaton.*;
import automaton.grid.*;
import java.util.Random;

public class Test {
	public static void main(String[] args) {
		try {
			Simulation sim = new Simulation(new Automaton("data/forestFireH.json"));
			sim.getAutomaton().getGrid().setAllRandomForest(sim.getAutomaton().getAlphabet(), new Random());
			sim.run();
		} catch (Exception e) {
			System.out.println("La création de la grille a échoué car " + e.getMessage());
		}
	}
}
