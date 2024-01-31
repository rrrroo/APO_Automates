package app;

import automaton.Automaton;
import automaton.Dimension;
import ui.*;


/**
 * The Simulation class represents a simulation of an automaton.
 */
public class Simulation {
	/**
	 * Represents an automaton.
	 */
	private Automaton automaton;

	/**
	 * Represents the window used to display the automaton.
	 */
	private Window window;

	/**
	 * Constructs a Simulation object with the specified automaton.
	 * 
	 * @param automaton the automaton to be simulated
	 */
	public Simulation(Automaton automaton) {
		this.automaton = automaton;
		switch(automaton.getDimension()) {
			case ONE_D:
				this.window = new Window1D(automaton);
				break;
			case TWO_D:
				this.window = new Window2D(automaton);
				break;
			case THREE_D:
				this.window = new Window3D(automaton);
				break;
			case H:
				this.window = new WindowH(automaton);
				break;
			default:
				throw new IllegalArgumentException("Dimension non reconnue" + automaton.getDimension());
		}
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
	public void run() {
		boolean stop = false;
		int step = 0;

		
		if(this.automaton.getDimension() == Dimension.ONE_D)
			System.out.println("Numéro de la règle : " + this.automaton.getRuleNumber());
		display();

		while(!stop) {
			step();
			display();
			System.out.println(automaton);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			// condition d'arrêt temporaire
			step++;
			stop = (step > 10);
		}
	}

	private void step() {
		this.automaton.evaluate();
	}

	/**
	 * Displays the automaton.
	 */
	private void display() {
		this.window.display();
	}
}