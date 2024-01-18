package main.automaton.rule;

public class Rule {
    protected char state;

    protected char result;

    public Rule(char state, char result) {
        this.state = state;
        this.result = result;
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
