package automaton.rule;

/**
 * Represents a rule for an automaton.
 */
public class Rule {
    // === Attributes ===
    /**
     * The current state of the cell.
     */
    protected char state;

    /**
     * The result of the rule.
     */
    protected char result;


    // === Constructor ===

    /**
     * Constructs a Rule object with the specified state and result.
     *
     * @param state  the state of the rule
     * @param result the result of applying the rule
     */
    public Rule(char state, char result) {
        this.state = state;
        this.result = result;
    }


    // === Getters ===

    /**
     * Returns the result of the rule.
     *
     * @return the result of the rule
     */
    public char getResult() {
        return this.result;
    }


    // === Methods ===

    /**
     * Applies the rule to the given array of neighbours and returns the resulting character.
     *
     * @param cell      the current cell
     * @param neighbours the array of characters representing the neighbours
     * @return the resulting character after applying the rule
     */
    public char apply(char cell, char[] neighbours) {
        return this.state;
    }
}
