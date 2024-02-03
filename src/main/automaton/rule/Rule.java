package main.automaton.rule;

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

    /**
    * The probability associated with this rule.
    */
    protected double probability;


    // === Constructor ===

    /**
     * Constructs a Rule object with the specified state and result.
     *
     * @param state  the state of the rule
     * @param result the result of applying the rule
     * @param probability the probability of the rule
     */
    public Rule(char state, char result, double probability) {
        this.state = state;
        this.result = result;
        this.probability = probability;
    }


    // === Getters ===

    /**
     * Returns the state of the rule.
     *
     * @return the state of the rule
     */
    public char getState() {
        return this.state;
    }

    /**
     * Returns the result of the rule.
     *
     * @return the result of the rule
     */
    public char getResult() {
        return this.result;
    }

    /**
     * Returns the probability of the rule.
     *
     * @return the probability of the rule
     */
    public double getProbability() {
        return this.probability;
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
