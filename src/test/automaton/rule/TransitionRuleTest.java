package test.automaton.rule;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.automaton.rule.TransitionRule;

public class TransitionRuleTest {
	@Test
	public void testTransitionRuleConstructor() {
		char state = 'A';
		char result = 'B';
		double probability = 0.5;
		int[] neighbours = {1, 2, 3};
		char neighboursState = 'X';
		TransitionRule rule = new TransitionRule(state, result, probability, neighbours, neighboursState);

		assertEquals(state, rule.getState());
		assertEquals(result, rule.getResult());
		assertEquals(probability, rule.getProbability(), 0.0001);
		assertEquals(neighbours, rule.getNeighbours());
		assertEquals(neighboursState, rule.getNeighboursState());
	}

	@Test
	public void testApplyRule() {
		char state = 'A';
		char result = 'B';
		double probability = 1;
		int[] neighbours = {1, 2, 3};
		char neighboursState = 'X';
		TransitionRule rule = new TransitionRule(state, result, probability, neighbours, neighboursState);

		char cell = 'A';
		char[] inputNeighbours = {'X', 'Y', 'Z'};
		char expected = 'B';
		char actual = rule.apply(cell, inputNeighbours);
		assertEquals(expected, actual);
	}
}
