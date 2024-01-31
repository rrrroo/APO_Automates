package automaton.rule;

import java.util.Arrays;

public class ForetRule extends TransitionRule {
    protected int ventVertical;
    protected int ventHorizontal;

    /**
     * Constructs a ForetRule object with the specified parameters.
     * @param state The current state of the automaton.
     * @param result The resulting state after the transition.
     * @param probability The probability of the transition.
     * @param neighbours The array of neighbor indices.
     * @param neighboursState The state of the neighbors.
     * @param ventVertical The value of the vertical wind.
     * @param ventHorizontal The value of the horizontal wind.
     */
    public ForetRule(char state, char result, double probability, int[] neighbours, char neighboursState, int ventVertical, int ventHorizontal) {
        super(state, result, probability, neighbours, neighboursState);
        this.ventVertical = ventVertical;
        this.ventHorizontal = ventHorizontal;
    }

    @Override
    public char apply(char cell, char[] neighbours) {
        if(cell == this.state) {
            int n = 0;
            for(int i = 0; i < neighbours.length; i++)
                if(neighbours[i] == this.neighboursState)
                    n++;
            if(Arrays.binarySearch(this.neighbours, n) >= 0) {
                double p = n * probability;
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
