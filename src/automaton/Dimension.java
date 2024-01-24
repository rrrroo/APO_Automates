package automaton;

/**
 * The Dimension enum represents the dimensions of an automaton.
 * It can be either 1D, 2D, 3D, or H (for higher dimensions).
 */
public enum Dimension {
	ONE_D	("1D"),
	TWO_D	("2D"),
	THREE_D	("3D"),
	H		("H");

	/**
	 * Represents a dimension value.
	 */
	private final String value;

	/**
	 * Creates a new Dimension enum.
	 *
	 * @param value the string value representing the dimension
	 */
	private Dimension(String value) {
		this.value = value;
	}

	/**
	 * Converts a string value to a Dimension enum.
	 *
	 * @param value the string value representing the dimension
	 * @return the corresponding Dimension enum
	 * @throws IllegalArgumentException if the provided value is invalid
	 */
	public static Dimension fromString(String value) throws IllegalArgumentException {
		for (Dimension dimension : Dimension.values()) {
			if (dimension.value.equals(value)) {
				return dimension;
			}
		}
		throw new IllegalArgumentException("la dimension est invalide " + value);
	}
}
