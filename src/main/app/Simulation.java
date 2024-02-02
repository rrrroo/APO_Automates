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
	 * Runs the simulation.
	 */
	public void run(int steps) {
		boolean stop = false;
		int step = 0;

		switch(this.automaton.getDimension()) {
			case ONE_D:
				this.window = new Window1D(this.automaton);
				break;
			case TWO_D:
				this.window = new Window2D(this.automaton);
				break;
			case THREE_D:
				this.window = new Window3D(this.automaton);
				break;
			case H:
				this.window = new WindowH(this.automaton);
				break;
		}

		
		if(this.automaton.getDimension() == Dimension.ONE_D)
			System.out.println("Numéro de la règle : " + this.automaton.getRuleNumber());

		printAndDisplay();
		while(!stop) {
			this.step();
			printAndDisplay();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			// condition d'arrêt temporaire
			step++;
			stop = (step > steps);
		}
	}

	private void step() {
		this.automaton.evaluate();
	}

	private void printAndDisplay() {
		System.out.println(this.automaton);
		this.window.display();
	}
}