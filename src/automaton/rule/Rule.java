package automaton.rule;

public class Rule {
    protected char state;

    protected char result;

    public Rule(char state, char result) {
        this.state = state;
        this.result = result;
    }
}
