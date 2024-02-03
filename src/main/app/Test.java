package main.app;

import java.util.Random;

import main.automaton.*;
import main.automaton.grid.*;

public class Test {
	public static void main(String[] args) {
		try {
			Simulation sim = new Simulation(new Automaton("data/configs/forestFire4Wind.json"));
			sim.getAutomaton().getGrid().setAllRandom(sim.getAutomaton().getAlphabet(), new Random());
			sim.runCLI(10);
		} catch (Exception e) {
			System.out.println("La création de la grille a échoué car " + e.getMessage());
		}
	}
}
