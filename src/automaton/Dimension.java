package automaton;

public enum Dimension {
	ONE_D("1D"),
	TWO_D("2D"),
	H("H");

	private final String value;

	Dimension(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Dimension fromString(String value) throws IllegalArgumentException {
		for (Dimension dimension : Dimension.values()) {
			if (dimension.value.equals(value)) {
				return dimension;
			}
		}
		throw new IllegalArgumentException("la dimension est invalide " + value);
	}
}
