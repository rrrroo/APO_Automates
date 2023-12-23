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
	public Grid(Dimension dimension, int size) throws IllegalArgumentException {
		this.dimension = dimension;

		if (size <= 0)
			throw new IllegalArgumentException("la taille de la grille ne peut pas être négative ou nulle.");
		if((size > MAX_SIZE && (dimension == Dimension.ONE_D || dimension == Dimension.TWO_D)) || (size > (MAX_SIZE / 2) && dimension == Dimension.H))
			throw new IllegalArgumentException("la taille de la grille est trop grande.");
		this.size = size;

		switch(dimension) {
			case ONE_D:
				this.grid = new ArrayList<>(size);
				break;
			case TWO_D:
				this.grid = new ArrayList<>(size * size);
				break;
			case H:
				this.grid = new ArrayList<>(4 * size * size);
				break;
		}
	}
}
