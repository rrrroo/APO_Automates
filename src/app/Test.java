package app;

import automaton.*;
import automaton.grid.*;
import java.util.Random;

public class Test {
	public static void main(String[] args) {
		try {
			Automaton automaton = new Automaton("data/configs/forestFireH.json");
			automaton.getGrid().setAllRandom(automaton.getAlphabet(), new Random());
			System.out.println(automaton);
			automaton.save();
			// Grid grid = new Grid(Dimension.H, "data/configs/test.txt", new char[]{'.', 'T', 'F', '-'});
			// System.out.println(grid);
		} catch (Exception e) {
			System.out.println("La création de la grille a échoué car " + e.getMessage());
		}
	}
}
