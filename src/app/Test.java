package app;

import automaton.*;
import automaton.grid.*;

public class Test {
	public static void main(String[] args) {
		try {
			Automaton automaton = new Automaton("data/gameOfLife.json");
			automaton.getGrid().setAllRandom(automaton.getAlphabet());
			System.out.println(automaton);
			automaton.save();
		} catch (Exception e) {
			System.out.println("La création de la grille a échoué car " + e.getMessage());
		}
	}
}
