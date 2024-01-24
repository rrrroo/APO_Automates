package app;

import automaton.*;
import automaton.grid.*;

public class Test {
	public static void main(String[] args) {
		try {
			Grid g = new Grid(Dimension.THREE_D, "data/test.txt", new char[] {'.', '#'});
			g.display();
		} catch (Exception e) {
			System.out.println("La création de la grille a échoué car " + e.getMessage());
		}
	}
}
