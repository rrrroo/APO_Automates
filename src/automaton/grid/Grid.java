package automaton.grid;

import automaton.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a grid.
 */
public class Grid {
	// === ATTRIBUTES === //

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
	private static final int MAX_SIZE = 100;

	/**
	 * Represents a grid of cells.
	 */
	private List<Cell> cellList;


	// === CONSTRUCTORS === //

	/**
	 * Constructs a Grid object with the specified dimension and size.
	 *
	 * @param dimension the dimension of the grid
	 * @param size      the size of the grid
	 * @param initialState the initial state of the cells
	 * @throws IllegalArgumentException if the size or the dimension is invalid
	 */
	public Grid(Dimension dimension, int size, char initialState) throws IllegalArgumentException {
		this.dimension = dimension;

		if (size <= 0)
			throw new IllegalArgumentException("la taille de la grille ne peut pas être négative ou nulle.");
		if((size > Grid.MAX_SIZE && (dimension == Dimension.ONE_D || dimension == Dimension.TWO_D)) || (size > (Grid.MAX_SIZE / 2) && dimension == Dimension.H))
			throw new IllegalArgumentException("la taille de la grille est trop grande.");
		this.size = size;

		switch(dimension) {
			case ONE_D:
				this.cellList = new ArrayList<>(size);
				for(int i = 0; i < size; i++)
					this.cellList.add(new Cell(initialState));
				break;
			case TWO_D:
				this.cellList = new ArrayList<>(size * size);
				for(int i = 0; i < size * size; i++)
					this.cellList.add(new Cell(initialState));
				break;
			case H:
				this.cellList = new ArrayList<>(1 + 3 * size * (size - 1));
				for(int i = 0; i < 1 + 3 * size * (size - 1); i++)
					this.cellList.add(new Cell(initialState));
				break;
			default:
				throw new IllegalArgumentException("la dimension de la grille n'est pas valide.");
		}
	}

	public Grid(Grid grid) {
		this.dimension = grid.dimension;
		this.size = grid.size;
		this.cellList = new ArrayList<>(grid.cellList.size());
		for(int i = 0; i < grid.cellList.size(); i++)
			this.cellList.add(new Cell(grid.cellList.get(i).getState()));
	}


	// === GETTERS === //

	/**
	 * Returns the settings of the grid as a string.
	 *
	 * @return the settings (dimension and size) of the grid as a string
	 */
	public String getSettings() {
		StringBuilder settings = new StringBuilder();
		settings.append("Dimension: ").append(this.dimension).append("\n");
		settings.append("Size: ").append(this.size);
		return settings.toString();
	}

	/**
	 * Returns the size of the grid.
	 *
	 * @return the size of the grid
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Retrieves the cell at the specified coordinates.
	 * If the grid is one-dimensional, the y coordinate is ignored.
	 *
	 * @param x the x coordinate of the cell to retrieve
	 * @param y the y coordinate of the cell to retrieve
	 * @return the cell at the specified coordinates
	 * @throws IndexOutOfBoundsException if the coordinates are out of bounds
	 */
	public Cell getCell(int x, int y) throws IndexOutOfBoundsException {
		try {
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
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("les coordonnées (" + x + ", " + y + ") -> " + e.getMessage() + " sont hors de la grille.");
		}
	}

	/**
	 * Retrieves the cell at the specified index.
	 *
	 * @param i the index of the cell to retrieve
	 * @return the cell at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	private Cell getCell(int i) throws IndexOutOfBoundsException {
		try {
			return this.cellList.get(i);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(String.valueOf(i));
		}
	}

	/**
	 * Returns the state of the cell at the specified coordinates.
	 *
	 * @param x the x-coordinate of the cell
	 * @param y the y-coordinate of the cell
	 * @return the state of the cell
	 * @throws IndexOutOfBoundsException if the coordinates are out of bounds
	 */
	public char getCellState(int x, int y) throws IndexOutOfBoundsException {
		try {
			return this.getCell(x, y).getState();
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(e.getMessage());
		}
	}

	public char[] getNeighboursState(int x, int y, List<int[]> neighbourhood) {
		char[] neighbours = new char[neighbourhood.size()];
		for(int i = 0; i < neighbourhood.size(); i++) {
			try {
				switch(this.dimension) {
					case ONE_D:
						neighbours[i] = this.getCell(
							modulo(x + neighbourhood.get(i)[0], this.size)	// permet de gérer les bords
						).getState();
						break;
					case TWO_D:
						neighbours[i] = this.getCell(
							modulo(x + neighbourhood.get(i)[0], this.size),
							modulo(y + neighbourhood.get(i)[1], this.size)
						).getState();
						break;
					case H:
						neighbours[i] = this.getCell(
							modulo(x + neighbourhood.get(i)[0], this.size),
							modulo(y + neighbourhood.get(i)[1], this.size)
						).getState();
						break;
				}
			} catch (IndexOutOfBoundsException e) {
				neighbours[i] = ' ';
			}
		}
		return neighbours;
	}

	/**
	 * Return the positive modulo of two integers.
	 *
	 * @param a
	 * @param b
	 * @return the positive modulo of two integers
	 */
	private static int modulo(int a, int b) {
		return (a % b + b) % b;
	}


	// === SETTERS === //

	/**
	 * Sets the state of the cell at the specified index.
	 *
	 * @param x     the x coordinate of the cell to modify
	 * @param y     the y coordinate of the cell to modify
	 * @param state the state of the cell
	 * @throws IndexOutOfBoundsException if the coordinates are out of bounds
	 */
	public void setCellState(int x, int y, char state) throws IndexOutOfBoundsException {
		try {
			this.getCell(x, y).setState(state);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("la modification de la cellule a échoué car " + e.getMessage());
		}
	}

	/**
	 * Sets the state of all cells in the grid to a random character from the given alphabet.
	 * 
	 * @param alphabet the array of characters to choose from
	 */
	public void setAllRandom(char [ ] alphabet) {
		Random random = new Random();
		for(int i = 0; i < this.cellList.size(); i++)
			try {
				this.getCell(i).setState(alphabet[random.nextInt(alphabet.length)]);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("La modification de la cellule a échoué car " + e.getMessage());
			}
	}


	// === DISPLAY === //

	/**
	 * Displays the grid based on the dimension of the automaton.
	 * If the dimension is ONE_D, it calls the display1D() method.
	 * If the dimension is TWO_D, it calls the display2D() method.
	 * If the dimension is H, it calls the displayH() method.
	 */
	public void display() {
		try {
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
		} catch (IndexOutOfBoundsException e) {
			System.out.println("L'affichage de la grille a échoué à cause d'une erreur d'index.");
		}
	}

	/**
	 * Returns a string representation of the grid.
	 *
	 * @return a string representation of the grid
	 */
	@Override
	public String toString() {
		StringBuilder grid = new StringBuilder();
		try {
			switch (this.dimension) {
				case ONE_D:
					for (int i = 0; i < this.size; i++)
						grid.append(this.getCell(i).toString());
					break;
				case TWO_D:
					for (int i = 0; i < this.size; i++) {
						for (int j = 0; j < this.size; j++)
							grid.append(this.getCell(i, j).toString());
						grid.append("\n");
					}
					break;
				case H:
					for (int i = 0; i < this.size; i++) {
						printSpaces(this.size - i - 1);
						printCells(i, this.size + i);
						grid.append("\n");
					}
					for(int i = 1; i < this.size; i++) {
						printSpaces(i);
						printCells(this.size + i, 2 * this.size - i - 1);
						grid.append("\n");
					}
					break;
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("La conversion de la grille en chaîne de caractères a échoué à cause d'une erreur d'index.");
		}
		return grid.toString();
	}

	/**
	 * Displays the 1D representation of the grid.
	 *
	 * @throws IndexOutOfBoundsException if during the display, an index is out of
	 *                                   bounds
	 */
	private void display1D() throws IndexOutOfBoundsException {
		try {
			for (int i = 0; i < this.size; i++) {
				System.out.print(this.getCell(i).toString());
			}
			System.out.println();
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Displays the 2D grid representation of the automaton.
	 *
	 * @throws IndexOutOfBoundsException if during the display, an index is out of
	 *                                   bounds
	 */
	private void display2D() throws IndexOutOfBoundsException {
		try {
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++)
					System.out.print(this.getCell(i, j).toString());
				System.out.println();
			}
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Displays the 2D hexagonal grid representation of the automaton.
	 *
	 * @throws IndexOutOfBoundsException if during the display, an index is out of
	 *                                   bounds
	 */
	private void displayH() throws IndexOutOfBoundsException {
		try {
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
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Prints a specified number of spaces.
	 *
	 * @param count the number of spaces to print
	 * @throws IllegalArgumentException if the number of spaces to print is negative
	 */
	private void printSpaces(int count) throws IllegalArgumentException {
		if (count < 0)
			throw new IllegalArgumentException();

		for (int i = 0; i < count; i++)
			System.out.print("  ");
	}

	/**
	 * Prints the states of cells in a specific row.
	 *
	 * @param row   the row index of the cells to be printed
	 * @param count the number of cells to be printed
	 * @throws IndexOutOfBoundsException if the row index is out of bounds
	 * @throws IllegalArgumentException  if the number of cells to be printed is
	 *                                   negative
	 */
	private void printCells(int row, int count) throws IllegalArgumentException, IndexOutOfBoundsException {
		if (count < 0)
			throw new IllegalArgumentException();

		try {
			for (int i = 0; i < count; i++)
				System.out.print(this.getCell(row, i).toString());
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
	}
}