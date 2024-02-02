package main.automaton.grid;

/**
 * Represents a cell in a grid.
 */
public class Cell {
	// === ATTRIBUTES === //

	/**
	 * The state of the cell.
	 */
	private char state;


	// === CONSTRUCTORS === //

	/**
	 * Constructs a Cell object with the specified state.
	 *
	 * @param state the state of the cell
	 */
	public Cell(char state) {
		this.state = state;
	}


	// === GETTERS === //

	/**
	 * Returns the state of the cell.
	 *
	 * @return the state of the cell
	 */
	public char getState() {
		return this.state;
	}


	// === SETTERS === //

	/**
	 * Sets the state of the cell.
	 *
	 * @param state the state of the cell
	 */
	public void setState(char state) {
		this.state = state;
	}


	// === METHODS === //

	/**
	 * Returns a string representation of the cell.
	 * 
	 * @return a string representation of the cell
	 */
	@Override
	public String toString() {
		return " " + this.state + " ";
	}
}
