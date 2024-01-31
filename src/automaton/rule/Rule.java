package automaton.rule;

public class Rule {
    protected char state;

    protected char result;

    protected double probability;

    public Rule(char state, char result, double probability) {
        this.state = state;
        this.result = result;
        this.probability = probability;
    }

    /**
     * Applies the rule to the given array of neighbours and returns the resulting character.
     *
     * @param neighbours the array of characters representing the neighbours
     * @return the resulting character after applying the rule
     */
    public char apply(char cell, char[] neighbours) {
        return this.state;
    }
}
