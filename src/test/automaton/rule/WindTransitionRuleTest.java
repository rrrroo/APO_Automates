package test.automaton.rule;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.automaton.rule.WindTransitionRule;

public class WindTransitionRuleTest {
	@Test
	public void testTransitionRuleConstructor() {
		char state = 'A';
		char result = 'B';
		double probability = 0.5;
		int[] neighbours = {1, 2, 3};
		char neighboursState = 'X';
		double[] wind = {1, 2, 3};
		WindTransitionRule rule = new WindTransitionRule(state, result, probability, neighbours, neighboursState, wind);

		assertEquals(state, rule.getState());
		assertEquals(result, rule.getResult());
		assertEquals(probability, rule.getProbability(), 0.0001);
		assertEquals(neighbours, rule.getNeighbours());
		assertEquals(neighboursState, rule.getNeighboursState());
		assertEquals(wind, rule.getWind());
	}

	@Test
	public void testApplyRule() {
		char state = 'A';
		char result = 'B';
		double probability = 1;
		int[] neighbours = {1, 2, 3};
		char neighboursState = 'X';
		double[] wind = {1, 2, 3};
		WindTransitionRule rule = new WindTransitionRule(state, result, probability, neighbours, neighboursState, wind);

		char cell = 'A';
		char[] inputNeighbours = {'X', 'Y', 'Z'};
		char expected = 'B';
		char actual = rule.apply(cell, inputNeighbours);
		assertEquals(expected, actual);
	}
}
