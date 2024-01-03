package automaton.grid;

import automaton.Dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a grid.
 */
public class Grid {
	/**
	 * The dimension of the grid.
	 */
	private Dimension dimension;

	/**
	 * The size of the grid.
	 */
	private int size;

	/**
	 * The maximum size of the grid.
	 */
	private final int MAX_SIZE = 100;

	/**
	 * Represents a grid of cells.
	 */
	private List<Cell> grid;

	/**
	 * Constructs a Grid object with the specified dimension and size.
	 *
	 * @param dimension the dimension of the grid
	 * @param size      the size of the grid
	 */
	public Grid(Dimension dimension, int size, char initialState) throws IllegalArgumentException {
		this.dimension = dimension;

		if (size <= 0)
			throw new IllegalArgumentException("la taille de la grille ne peut pas être négative ou nulle.");
		if((size > MAX_SIZE && (dimension == Dimension.ONE_D || dimension == Dimension.TWO_D)) || (size > (MAX_SIZE / 2) && dimension == Dimension.H))
			throw new IllegalArgumentException("la taille de la grille est trop grande.");
		this.size = size;

		switch(dimension) {
			case ONE_D:
				this.grid = new ArrayList<>(size);
				for(int i = 0; i < size; i++)
					this.grid.add(new Cell(initialState));
				break;
			case TWO_D:
				this.grid = new ArrayList<>(size * size);
				for(int i = 0; i < size * size; i++)
					this.grid.add(new Cell(initialState));
				break;
			case H:
				this.grid = new ArrayList<>(1 + 3 * size * (size - 1));
				for(int i = 0; i < 1 + 3 * size * (size - 1); i++)
					this.grid.add(new Cell(initialState));
				break;
		}
	}


	// === GETTERS === //

	/**
	 * Returns the settings of the grid as a string.
	 *
	 * @return the settings of the grid as a string
	 */
	public String getSettings() {
		StringBuilder settings = new StringBuilder();
		settings.append("Dimension: ").append(this.dimension).append("\n");
		settings.append("Size: ").append(this.size);
		return settings.toString();
	}

	/**
	 * Retrieves the cell at the specified coordinates.
	 * If the grid is one-dimensional, the y coordinate is ignored.
	 *
	 * @param x the x coordinate of the cell to retrieve
	 * @param y the y coordinate of the cell to retrieve
	 * @return the cell at the specified coordinates
	 */
	public Cell getCell(int x, int y) {
		switch (this.dimension) {
			case ONE_D:
				return this.getCell(x);
			case TWO_D:
				return this.getCell(x + y * this.size);
			case H:
				return this.getCell(x + y * (y + 1) / 2);
			default:
				return null;
		}
	}

	/**
	 * Retrieves the cell at the specified index.
	 *
	 * @param i the index of the cell to retrieve
	 * @return the cell at the specified index
	 */
	private Cell getCell(int i) {
		return this.grid.get(i);
	}


	// === SETTERS === //

	/**
	 * Sets the state of the cell at the specified index.
	 *
	 * @param index the index of the cell
	 * @param state the state of the cell
	 */
	public void setCellState(int index, char state) {
		this.getCell(index).setState(state);
	}


	// === DISPLAY === //

	/**
	 * Displays the grid based on the dimension of the automaton.
	 * If the dimension is ONE_D, it calls the display1D() method.
	 * If the dimension is TWO_D, it calls the display2D() method.
	 * If the dimension is H, it calls the displayH() method.
	 */
	public void display() {
		switch (this.dimension) {
			case ONE_D:
				display1D();
				break;
			case TWO_D:
				display2D();
				break;
			case H:
				displayH();
				break;
		}
	}

	/**
	 * Displays the 1D representation of the grid.
	 */
	private void display1D() {
		for (int i = 0; i < this.size; i++) {
			System.out.print(" " + this.getCell(i).getState() + " ");
		}
		System.out.println();
	}

	/**
	 * Displays the 2D grid representation of the automaton.
	 */
	private void display2D() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++)
				System.out.print(" " + this.getCell(i, j).getState() + " ");
			System.out.println();
		}
	}

	/**
	 * Displays the 2D hexagonal grid representation of the automaton.
	 */
	private void displayH() {
		for (int i = 0; i < this.size; i++) {
			printSpaces(this.size - i - 1);
			printCells(i, this.size + i);
			System.out.println();
		}
		for(int i = 1; i < this.size; i++) {
			printSpaces(i);
			printCells(this.size + i, 2 * this.size - i - 1);
			System.out.println();
		}
	}

	/**
	 * Prints a specified number of spaces.
	 *
	 * @param count the number of spaces to print
	 */
	private void printSpaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print("  ");
	}

	private void printCells(int row, int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" " + this.getCell(row, i).getState() + "  ");
	}
}