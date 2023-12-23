package automaton;

import automaton.grid.Coordinate;
import automaton.grid.Grid;
import java.util.List;

/**
 * This class represents a cellular automaton.
 */
public class Automaton {
	/**
	 * The dimension of the automaton.
	 */
	private Dimension dimension;

	/**
	 * The alphabet of the automaton.
	 */
	private char[] alphabet;

	/**
	 * The neighborhood of the automaton.
	 */
	private List<Coordinate> neighborhood;

	/**
	 * The grid of the automaton.
	 */
	private Grid grid;

	/**
	 * The rules of the automaton.
	 */
	private List<Rule> rules;

	/**
	 * The constructor of the class.
	 *
	 * @param filename the name of the file containing the automaton settings
	 */
	public Automaton(String filename) {
		// TODO
	}
}
