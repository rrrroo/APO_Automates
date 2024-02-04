package main.app;

import main.automaton.Automaton;
import main.automaton.Dimension;


/**
 * The Simulation class represents a simulation of an automaton.
 */
public class Simulation {
	// === ATTRIBUTES === //

	/**
	 * Represents an automaton.
	 */
	private Automaton automaton;


	// === CONSTRUCTORS === //

	/**
	 * Constructs a Simulation object with the specified automaton.
	 * 
	 * @param automaton the automaton to be simulated
	 */
	public Simulation(Automaton automaton) {
		this.automaton = automaton;
	}


	// === GETTERS === //

	/**
	 * Returns the automaton.
	 * 
	 * @return the automaton
	 */
	public Automaton getAutomaton() {
		return this.automaton;
	}


	// === METHODS === //

	/**
	 * Runs the simulation in the CLI mode.
	 */
	public void runCLI(int steps) {
		boolean stop = false;
		int step = 0;
		
		if(this.automaton.getDimension() == Dimension.ONE_D)
			System.out.println("Numéro de la règle : " + this.automaton.getRuleNumber());

		System.out.println(this.automaton);
		while(!stop) {
			this.step();
			System.out.println(this.automaton);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			step++;
			stop = (step >= steps);
		}
	}

	/**
	 * Executes a single step in the simulation by evaluating the automaton.
	 */
	private void step() {
		this.automaton.evaluate();
	}
}