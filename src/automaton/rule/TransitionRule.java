package automaton.rule;

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
     * @param probability The probability of the transition.
     * @param neighbours The array of neighbor indices.
     * @param neighboursState The state of the neighbors.
     */
    public TransitionRule(char state, char result, double probability, int[] neighbours, char neighboursState) {
        super(state, result, probability);
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
                if(neighbours[i] == this.neighboursState){
                    n++;
                }
            if(Arrays.binarySearch(this.neighbours, n) >= 0) {
                double p = n * probability;
                if (n==0)
                    p=1;
                double r = Math.random();
                if(r < p)
                    return this.result;
                else
                    return this.state;
            }
        }
        return cell;
    }
}
