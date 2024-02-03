package main.automaton.rule;

import java.util.Arrays;

public class WindTransitionRule extends TransitionRule {
    // === Attributes ===

    /**
     * The value of the wind from each direction.
     */
    protected double[] vent;


    // === Constructors ===

    /**
     * Constructs a ForetRule object with the specified parameters.
     * @param state The current state of the automaton.
     * @param result The resulting state after the transition.
     * @param probability The probability of the transition.
     * @param neighbours The array of neighbor indices.
     * @param neighboursState The state of the neighbors.
     * @param vent The value of the wind from each direction.
     */
    public WindTransitionRule(char state, char result, double probability, int[] neighbours, char neighboursState, double[] vent) {
        super(state, result, probability, neighbours, neighboursState);
        this.vent = vent;
    }


    // === Getters ===

    /**
     * Returns the value of the wind from each direction.
     *
     * @return the value of the wind from each direction
     */
    public double[] getWind() {
        return this.vent;
    }


    // === Methods ===

    @Override
    public char apply(char cell, char[] neighbours) {
        if(cell == this.state) {
            int n = 0;
            double v=0;
            for(int i = 0; i < neighbours.length; i++)
                if(neighbours[i] == this.neighboursState) {
                    n ++;
                    v += this.vent[i];
                }
            if(Arrays.binarySearch(this.neighbours, n) >= 0) { // si le n est dans le tableau
                double p = n * probability + v;
                if (n==0)
                {p=1;}
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
