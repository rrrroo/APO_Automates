package main.automaton.grid;

/**
 * Represents a cell in a grid.
 */
public class Cell {
	/**
	 * The state of the cell.
	 */
	private char state;

	/**
	 * Constructs a Cell object with the specified state.
	 *
	 * @param state the state of the cell
	 */
	public Cell(char state) {
		this.state = state;
	}

	/**
	 * Returns the state of the cell.
	 *
	 * @return the state of the cell
	 */
	public char getState() {
		return this.state;
	}

	/**
	 * Sets the state of the cell.
	 *
	 * @param state the state of the cell
	 */
	public void setState(char state) {
		this.state = state;
	}
}
