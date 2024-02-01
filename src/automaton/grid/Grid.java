package automaton.grid;

import automaton.Dimension;
import java.io.File;
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
		if(size > Grid.MAX_SIZE)
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
			case H:
				this.cellList = new ArrayList<>(size * size);
				for(int i = 0; i < size * size; i++)
					this.cellList.add(new Cell(initialState));
				break;

			case THREE_D:
				this.cellList = new ArrayList<>(size * size * size);
				for(int i = 0; i < size * size * size; i++)
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
					addLine(scanner.nextLine(), alphabet);
					break;

				case TWO_D:
				case H:
					addLayer(scanner, alphabet);
					break;

				case THREE_D:
					add3DGrid(scanner, alphabet);
					break;

				default:
					throw new IllegalArgumentException("la dimension de la grille n'est pas valide.");
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("le fichier n'a pas pu être lu car " + e.getMessage());
		} catch (Exception e) {
			throw new IllegalArgumentException("le fichier n'a pas pu être lu.");
		}
	}

	/**
	 * Adds a line to the grid.
	 * Each character in the file represents a state in the grid.
	 * 
	 * @param line     the line containing the states of the cells
	 * @param alphabet the valid characters representing the states in the grid
	 * @throws IllegalArgumentException if the file contains a character that is not
	 *                                  in the alphabet
	 */
	private void addLine(String line, char[] alphabet) throws IllegalArgumentException {
		char state;

		// Boucle sur x
		for (int i = 0; i < line.length(); i++) {
			state = line.charAt(i);

			// Vérification de la validité du caractère
			if (isElementInArray(state, alphabet))
				this.cellList.add(new Cell(state));
			else
				throw new IllegalArgumentException("le fichier contient un caractère qui n'est pas dans l'alphabet.");
		}

		// Vérification de la validité de la taille de la grille
		if (this.size == 0)
			this.size = line.length();
		else if(this.size != line.length())
			throw new IllegalArgumentException(" une des lignes n'est pas de la bonne taille.");
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

	/**
	 * Adds a layer to the grid.
	 * The shape of the grid must be a square.
	 * 
	 * @param scanner  the scanner used to read the file
	 * @param alphabet the valid characters allowed in the grid
	 * @throws IllegalArgumentException if the grid dimensions are not valid
	 */
	private void addLayer(Scanner scanner, char[] alphabet) throws IllegalArgumentException {
		String line;
		int height = 0;

		// Boucle sur y
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			line = line.replace(" ", "");

			// Fin de la couche
			if(line.length() == 0)
				return;

			height++;

			// Boucle sur x
			addLine(line, alphabet);
		}

		// Vérification de la validité de la taille de la grille
		if(this.size != height)
			throw new IllegalArgumentException("une des couches n'est pas carrée.");
	}

	/**
	 * Adds a 3D grid to the grid.
	 * Each empty line in the file represents a new layer in the grid.
	 * The shape of the grid must be a cube.
	 * 
	 * @param scanner  the scanner used to read the file
	 * @param alphabet the valid characters for the grid
	 * @throws IllegalArgumentException if the grid dimensions are not valid
	 */
	private void add3DGrid(Scanner scanner, char[] alphabet) throws IllegalArgumentException {
		int depth = 0;

		// Boucle sur les lignes du fichier
		while (scanner.hasNextLine()) {
			depth++;

			// Boucle sur une couche
			addLayer(scanner, alphabet);
		}

		// Vérification de la validité de la taille de la grille
		if(this.size != depth)
			throw new IllegalArgumentException("la grille n'est pas cubique.");
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
				case H:
					return this.getCell(x + y * this.size);

				case THREE_D:
					return this.getCell(x + y * this.size + z * this.size * this.size);

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
	public Cell getCell(int i) throws IndexOutOfBoundsException {
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
						int offset = 0;
						if(neighbourhood.get(i)[1] != 0 && y % 2 != 0)
							offset = 1;
						neighbours[i] = this.getCell(
							modulo(x + offset + neighbourhood.get(i)[0], this.size),
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
	public void setAllRandom(char[] alphabet, Random random) {
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
	public void setAllRandomForest(char[] alphabet, Random random) { // TODO: utiliser l'alphabet de l'automate
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


	// === TOSTRING === //

	/**
	 * Returns a string representation of the grid.
	 *
	 * @return a string representation of the grid
	 */
	@Override
	public String toString() throws IllegalArgumentException, IndexOutOfBoundsException {
		try {
			switch (this.dimension) {
				case ONE_D:
					return this.toString1D(0, 0);
				case TWO_D:
					return this.toString2D(0);
				case THREE_D:
					return this.toString3D();
				case H:
					return this.toStringH();
				default:
					throw new IllegalArgumentException("la dimension de la grille n'est pas valide.");
			}
		} catch (Exception e) {
			System.out.println("L'affichage de la grille a échoué car " + e.getMessage());
		}
		return "";
	}

	/**
	 * Returns a string representation of the specified row in the 1D grid.
	 * 
	 * @param y the index of the row
	 * @param z the index of the layer
	 * @return a string representation of the specified row
	 * @throws IllegalArgumentException if the index of the row or the layer is invalid
	 * @throws IndexOutOfBoundsException if the index of the row is out of bounds
	 */
	private String toString1D(int y, int z) throws IllegalArgumentException, IndexOutOfBoundsException {
		if(y < 0 || y >= this.size || z < 0 || z >= this.size)
			throw new IllegalArgumentException("l'index de la ligne est invalide.");
		if (this.dimension == Dimension.H)
			throw new IllegalArgumentException("la dimension de la grille n'est pas la bonne.");

		StringBuilder str = new StringBuilder();
		try {
			for (int i = 0; i < this.size; i++)
				str.append(" " + this.getCell(i, y, z).getState() + " ");
			str.append("\n");
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
		return str.toString();
	}

	/**
	 * Returns a string representation of the 2D grid at the specified layer.
	 * 
	 * @param z the layer index
	 * @return a string representation of the 2D grid at the specified layer
	 * @throws IllegalArgumentException if the layer index is invalid
	 * @throws IndexOutOfBoundsException if the grid dimension is not bidimensional
	 */
	private String toString2D(int z) throws IllegalArgumentException, IndexOutOfBoundsException {
		if (z < 0 || z >= this.size)
			throw new IllegalArgumentException("l'index de la couche est invalide.");
		if (this.dimension != Dimension.TWO_D && this.dimension != Dimension.THREE_D)
			throw new IllegalArgumentException("la dimension de la grille n'est pas bidimensionnelle.");

		StringBuilder str = new StringBuilder();
		try {
			for (int i = 0; i < this.size; i++)
				str.append(this.toString1D(i, z));
			str.append("\n");
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
		return str.toString();
	}

	/**
	 * Returns a string representation of a 3D grid.
	 * 
	 * @return the string representation of a 3D grid
	 * @throws IndexOutOfBoundsException if the grid dimension is not three-dimensional
	 */
	private String toString3D() throws IndexOutOfBoundsException {
		if (this.dimension != Dimension.THREE_D)
			throw new IllegalArgumentException("la dimension de la grille n'est pas tridimensionnelle.");

		StringBuilder str = new StringBuilder();
		try {
			for (int i = 0; i < this.size; i++) {
				str.append("Couche " + i);
				str.append(this.toString2D(i));
			}
			str.append("\n");
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
		return str.toString();
	}

	/**
	 * Returns a string representation of an hexagonal grid.
	 * 
	 * @return the string representation of an hexagonal grid
	 * @throws IllegalArgumentException if the dimension of the grid is not hexagonal
	 * @throws IndexOutOfBoundsException if the grid indices are out of bounds
	 */
	private String toStringH() throws IllegalArgumentException, IndexOutOfBoundsException {
		if (this.dimension != Dimension.H)
			throw new IllegalArgumentException("la dimension de la grille n'est pas hexagonale.");

		StringBuilder str = new StringBuilder();
		try {
			for (int i = 0; i < this.size; i++) {
				if(i % 2 != 0)
					str.append("  ");
				for (int j = 0; j < this.size; j++)
					str.append(" " + this.getCell(j, i, 0).getState() + "  ");
				str.append("\n");
			}
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
		return str.toString();
	}
}