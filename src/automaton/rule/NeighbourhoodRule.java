package automaton.rule;

/**
 * Represents a rule that defines the behavior of an automaton based on the
 * state of its neighbors.
 */
public class NeighbourhoodRule extends Rule {
	/**
	 * The array of characters representing the neighbours of a cell.
	 */
	protected char[] neighbours;

	/**
	 * Constructs a NeighbourhoodRule object with the specified state, result, and
	 * neighbors.
	 * 
	 * @param state      the state of the automaton
	 * @param result     the resulting state after applying the rule
	 * @param neighbours the array of characters representing the neighbors of the
	 *                   automaton
	 */
	public NeighbourhoodRule(char state, char result, char[] neighbours) {
		super(state, result);
		this.neighbours = neighbours;
	}
}
