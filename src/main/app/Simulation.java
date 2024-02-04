package main.app;

import main.automaton.Automaton;
import main.automaton.Dimension;
import main.ui.*;


/**
 * The Simulation class represents a simulation of an automaton.
 */
public class Simulation {
	/**
	 * Represents an automaton.
	 */
	private Automaton automaton;

	/**
	 * Window used to display the automaton.
	 */
	private Window window;

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

		print();
		while(!stop) {
			this.step();
			print();

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
	 * Makes the automaton calculate its next state.
	 */
	private void step() {
		this.automaton.evaluate();
	}

	/**
	 * Prints the actual state of the automaton
	 * in the console.
	 */
	private void print() {
		System.out.println(this.automaton);
	}
}