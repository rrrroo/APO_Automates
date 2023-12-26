package automaton.rule;

public class TransitionRule extends Rule {
    protected int[] neighbours;

    protected char neighboursState;

    public TransitionRule(char state, char result, int[] neighbours, char neighboursState) {
        super(state, result);
        this.neighbours = neighbours;
        this.neighboursState = neighboursState;
    }
}
