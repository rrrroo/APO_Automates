package automaton.grid;

import automaton.Dimension;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

		// Initialisation de la taille de la grille
		if (size <= 0)
			throw new IllegalArgumentException("la taille de la grille ne peut pas être négative ou nulle.");
		if((size > Grid.MAX_SIZE && (dimension == Dimension.ONE_D || dimension == Dimension.TWO_D)) || (size > (Grid.MAX_SIZE / 2) && dimension == Dimension.H))
			throw new IllegalArgumentException("la taille de la grille est trop grande.");
		this.size = size;

		// Initialisation de la grille
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
			case THREE_D:
				this.cellList = new ArrayList<>(size * size * size);
				for(int i = 0; i < size * size * size; i++)
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

	/**
	 * Constructs a new grid by copying the given grid.
	 * 
	 * @param grid the grid to be copied
	 */
	public Grid(Grid grid) {
		this.dimension = grid.dimension;
		this.size = grid.size;
		this.cellList = new ArrayList<>(grid.cellList.size());
		for(int i = 0; i < grid.cellList.size(); i++)
			this.cellList.add(new Cell(grid.cellList.get(i).getState()));
	}

	/**
	 * Constructs a Grid object with the specified dimension and configuration.
	 * 
	 * @param dimension the dimension of the grid (1D, 2D, or 3D)
	 * @param filename  the name of the file containing the grid configuration
	 * @param alphabet  the alphabet of characters used in the grid
	 * @throws IllegalArgumentException if the filename is invalid, if the dimension
	 *                                  is invalid, or if the file cannot be read
	 */
	public Grid(Dimension dimension, String filename, char[] alphabet) throws IllegalArgumentException {
		// Vérification de la validité du nom de fichier
		if(filename == null || !filename.contains(".txt"))
			throw new IllegalArgumentException("le nom de fichier n'est pas valide.");

		this.dimension = dimension;
		this.size = 0;
		this.cellList = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(filename))) {
			switch (dimension) {
				case ONE_D:
					constructGrid1DFromFile(scanner, alphabet);
					break;
				case TWO_D:
					constructGrid2DFromFile(scanner, alphabet);
					break;
				case THREE_D:
					constructGrid3DFromFile(scanner, alphabet);
					break;
				case H:
					// TODO
				default:
					throw new IllegalArgumentException("la dimension de la grille n'est pas valide.");
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("le fichier n'a pas pu être lu car " + e.getMessage());
		} catch (Exception e) {
			throw new IllegalArgumentException("le fichier n'a pas pu être lu.");
		}
	} // TODO: dimension H + refactorisation

	/**
	 * Constructs a 1D grid from a file using the provided scanner and alphabet.
	 * Each character in the file represents a state in the grid.
	 * 
	 * @param scanner  the scanner used to read the file
	 * @param alphabet the valid characters representing the states in the grid
	 * @throws IllegalArgumentException if the file contains a character that is not
	 *                                  in the alphabet
	 */
	private void constructGrid1DFromFile(Scanner scanner, char[] alphabet) throws IllegalArgumentException {
		String line = scanner.nextLine();
		char state;

		// Boucle sur les caractères de la ligne
		for (int i = 0; i < line.length(); i++) {
			this.size++;
			state = line.charAt(i);

			// Vérification de la validité du caractère
			if (isElementInArray(state, alphabet))
				this.cellList.add(new Cell(state));
			else
				throw new IllegalArgumentException("le fichier contient un caractère qui n'est pas dans l'alphabet.");
		}
	}

	/**
	 * Constructs a 2D grid from a file using the provided scanner and alphabet.
	 * Each character in the file represents a state in the grid.
	 * The shape of the grid must be a square.
	 * 
	 * @param scanner  the scanner used to read the file
	 * @param alphabet the valid characters allowed in the grid
	 * @throws IllegalArgumentException if the file contains an invalid character or
	 *                                  if the grid dimensions are not valid
	 */
	private void constructGrid2DFromFile(Scanner scanner, char[] alphabet) throws IllegalArgumentException {
		String line;
		char state;
		int height = 0;
		List<Integer> widths = new ArrayList<>();
		int width;

		// Boucle sur les lignes du fichier
		while (scanner.hasNextLine()) {
			height++;
			width = 0;
			line = scanner.nextLine();

			// Boucle sur les caractères de la ligne
			for (int i = 0; i < line.length(); i++) {
				state = line.charAt(i);
				width++;

				// Vérification de la validité du caractère
				if (isElementInArray(state, alphabet))
					this.cellList.add(new Cell(state));
				else
					throw new IllegalArgumentException("le fichier contient un caractère qui n'est pas dans l'alphabet.");
			}

			widths.add(width);
		}

		// Vérification de la validité de la taille de la grille
		this.size = widths.get(0);
		for(int i = 1; i < widths.size(); i++)
			if(widths.get(i) != this.size)
				throw new IllegalArgumentException("la grille n'est pas rectangulaire.");
		if (height != this.size)
			throw new IllegalArgumentException("la grille n'est pas carrée.");
	}

	/**
	 * Constructs a 3D grid from a file using the provided scanner and alphabet.
	 * Each character in the file represents a state in the grid.
	 * Each empty line in the file represents a new layer in the grid.
	 * The shape of the grid must be a cube.
	 * 
	 * @param scanner  the scanner used to read the file
	 * @param alphabet the valid characters for the grid
	 * @throws IllegalArgumentException if the file contains an invalid character or
	 *                                  if the grid dimensions are not valid
	 */
	private void constructGrid3DFromFile(Scanner scanner, char[] alphabet) throws IllegalArgumentException {
		String line;
		char state;
		int depth = 0;
		List<Integer> heights = new ArrayList<>();
		int height = 0;
		List<Integer> widths = new ArrayList<>();
		int width;

		// Boucle sur les lignes du fichier
		while (scanner.hasNextLine()) {
			width = 0;
			line = scanner.nextLine();

			// Fin d'une couche
			if(line.length() == 0) {
				heights.add(height);
				depth++;
				height = 0;
				continue;
			}
			else
				height++;

			// Boucle sur les caractères de la ligne
			for (int i = 0; i < line.length(); i++) {
				state = line.charAt(i);
				width++;

				// Vérification de la validité du caractère
				if (isElementInArray(state, alphabet))
					this.cellList.add(new Cell(state));
				else
					throw new IllegalArgumentException("le fichier contient un caractère qui n'est pas dans l'alphabet.");
			}
			widths.add(width);
		}
		heights.add(height);

		// Vérification de la validité de la taille de la grille
		this.size = widths.get(0);
		for(int i = 1; i < widths.size(); i++)
			if(widths.get(i) != this.size)
				throw new IllegalArgumentException("une des couches n'est pas rectangulaire.");
		for(int i = 0; i < heights.size(); i++)
			if(heights.get(i) != this.size)
				throw new IllegalArgumentException("une des couches n'est pas carrée.");
		if ((depth + 1) != this.size)
			throw new IllegalArgumentException("la grille n'est pas cubique.");
	}

	/**
	 * Checks if an element is present in an array.
	 *
	 * @param state the element to check
	 * @param alphabet the array to search in
	 * @return true if the element is found, false otherwise
	 */
	private static boolean isElementInArray(char state, char[] alphabet) {
		for(int i = 0; i < alphabet.length; i++)
			if(state == alphabet[i])
				return true;
		return false;
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
	 * @param z the z coordinate of the cell to retrieve
	 * @return the cell at the specified coordinates
	 * @throws IndexOutOfBoundsException if the coordinates are out of bounds
	 */
	public Cell getCell(int x, int y, int z) throws IndexOutOfBoundsException {
		try {
			switch (this.dimension) {
				case ONE_D:
					return this.getCell(x);
				case TWO_D:
					return this.getCell(x + y * this.size);
				case THREE_D:
					return this.getCell(x + y * this.size + z * this.size * this.size);
				case H:
					return this.getCell(x + y * (y + 1) / 2);
				default:
					return null;
			}
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("les coordonnées (" + x + ", " + y + ", " + z + ") -> " + e.getMessage() + " sont hors de la grille.");
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
	 * @param z the z-coordinate of the cell
	 * @return the state of the cell
	 * @throws IndexOutOfBoundsException if the coordinates are out of bounds
	 */
	public char getCellState(int x, int y, int z) throws IndexOutOfBoundsException {
		try {
			return this.getCell(x, y, z).getState();
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(e.getMessage());
		}
	}

	/**
	 * Returns the state of the neighboring cells at the specified coordinates.
	 * 
	 * @param x the x-coordinate of the cell
	 * @param y the y-coordinate of the cell
	 * @param neighbourhood the list of relative coordinates of the neighboring cells
	 * @return an array of characters representing the states of the neighboring cells
	 * @throws IndexOutOfBoundsException if the coordinates are out of bounds
	 */
	public char[] getNeighboursState(int x, int y, int z, List<int[]> neighbourhood) throws IndexOutOfBoundsException {
		char[] neighbours = new char[neighbourhood.size()];
		for(int i = 0; i < neighbourhood.size(); i++) {
			try {
				switch(this.dimension) {
					case ONE_D:
						neighbours[i] = this.getCell(
							modulo(x + neighbourhood.get(i)[0], this.size),	// permet de gérer les bords
							0,
							0
						).getState();
						break;
					case TWO_D:
						neighbours[i] = this.getCell(
							modulo(x + neighbourhood.get(i)[0], this.size),
							modulo(y + neighbourhood.get(i)[1], this.size),
							0
						).getState();
						break;
					case THREE_D:
						neighbours[i] = this.getCell(
							modulo(x + neighbourhood.get(i)[0], this.size),
							modulo(y + neighbourhood.get(i)[1], this.size),
							modulo(z + neighbourhood.get(i)[2], this.size)
						).getState();
						break;
					case H:
						neighbours[i] = this.getCell(
							modulo(x + neighbourhood.get(i)[0], this.size),
							modulo(y + neighbourhood.get(i)[1], this.size),
							0
						).getState();
						break;
				}
			} catch (IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("le voisinnage n'a pas pu être récupéré car " + e.getMessage());
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
	public void setCellState(int x, int y, int z, char state) throws IndexOutOfBoundsException {
		try {
			this.getCell(x, y, z).setState(state);
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

	/**
	 * Sets the state of all cells in the grid to a random character from the given alphabet.
	 * none of the cells will be set to the 'burned' or 'on fire' state.
	 * a randomly chose cell will be set to the 'on fire' state.
	 *
	 * @param alphabet the array of characters to choose from
	 */
	public void setAllRandomForest(char [ ] alphabet) {
		Random random = new Random();
		try {
			for (int i = 0; i < this.cellList.size(); i++) {
				do {
					this.getCell(i).setState(alphabet[random.nextInt(alphabet.length)]);
				} while (this.getCell(i).getState() == '.');
				if (this.getCell(i).getState() == '0')
					this.getCell(i).setState('f');
			}
				int x = random.nextInt(this.size);
				int y = random.nextInt(this.size);
				setCellState(x, y, 0, '0');
		} catch (IndexOutOfBoundsException e) {
			System.out.println("La modification de la cellule a échoué car " + e.getMessage());
		}
	}


	// === DISPLAY === //

	/**
	 * Displays the grid based on the dimension of the automaton by calling the
	 * appropriate method.
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
				case THREE_D:
					display3D();
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
	 * Displays the 1D representation of the grid.
	 *
	 * @throws IndexOutOfBoundsException if during the display, an index is out of
	 *                                   bounds
	 */
	private void display1D() throws IndexOutOfBoundsException {
		try {
			for (int i = 0; i < this.size; i++) {
				System.out.print(" " + this.getCell(i, 0, 0).getState() + " ");
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
					System.out.print(" " + this.getCell(j, i, 0).getState() + " ");
				System.out.println();
			}
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Displays the 3D grid representation of the automaton.
	 *
	 * @throws IndexOutOfBoundsException if during the display, an index is out of
	 *                                   bounds
	 */
	private void display3D() throws IndexOutOfBoundsException {
		try {
			for (int i = 0; i < this.size; i++) {
				System.out.println("Couche " + i);
				for (int j = 0; j < this.size; j++) {
					for (int k = 0; k < this.size; k++)
						System.out.print(" " + this.getCell(k, j, i).getState() + " ");
					System.out.println();
				}
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
				System.out.print(" " + this.getCell(row, i, 0).getState() + "  ");
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
	}
}