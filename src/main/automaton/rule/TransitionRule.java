package main.automaton.rule;

import java.util.Arrays;

/**
 * Represents a transition rule for an automaton.
 * Extends the base class Rule.
 */
public class TransitionRule extends Rule {
    /**
     * The array of integers representing the number of neighbors needed to apply
     * the rule.
     */
    protected int[] neighbours;

    /**
     * The state of the neighboring cells.
     */
    protected char neighboursState;

    /**
     * Constructs a TransitionRule object with the specified parameters.
     * 
     * @param state The current state of the automaton.
     * @param result The resulting state after the transition.
     * @param neighbours The array of neighbor indices.
     * @param neighboursState The state of the neighbors.
     */
    public TransitionRule(char state, char result, int[] neighbours, char neighboursState) {
        super(state, result);
        this.neighbours = neighbours;
        this.neighboursState = neighboursState;
    }

    /**
     * Applies the transition rule to a given cell and its neighbors.
     * If the cell's state matches the rule's state, it counts the number of neighbors with the specified state.
     * If the count matches one of the specified neighbor counts, it returns the result state.
     * Otherwise, it returns the original cell state.
     *
     * @param cell      the current cell state
     * @param neighbours an array of neighboring cell states
     * @return the new state of the cell after applying the transition rule
     */
    @Override
    public char apply(char cell, char[] neighbours) {
        if(cell == this.state) {
            int n = 0;
            for(int i = 0; i < neighbours.length; i++)
                if(neighbours[i] == this.neighboursState)
                    n++;
            if(Arrays.binarySearch(this.neighbours, n) >= 0)
                return this.result;
        }
        return cell;
    }
}
