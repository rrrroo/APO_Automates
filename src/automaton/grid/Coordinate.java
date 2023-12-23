package automaton.grid;

/**
 * Represents a coordinate in a grid.
 */
public class Coordinate {
	/**
	 * Represents the coordinates of a point in a grid.
	 */
	private int[] coordinates;

	/**
	 * Constructs a Coordinate object with the given coordinates.
	 * 
	 * @param coordinates the array of coordinates
	 */
	public Coordinate(int[] coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Returns the coordinates of the Coordinate object.
	 * 
	 * @return the coordinates
	 */
	public int[] getCoordinates() {
		return this.coordinates;
	}
}
