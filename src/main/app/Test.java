package main.app;

import java.util.Random;

import main.automaton.*;
import main.automaton.grid.*;
import test.automaton.AutomatonTest;


public class Test {
	public static void main(String[] args) {
		try {
			AutomatonTest test = new AutomatonTest();
			test.testAutomatonConstructorWithRuleNb();
		} catch (Exception e) {
			System.out.println("La création de la grille a échoué car " + e.getMessage());
		}
	}
}
