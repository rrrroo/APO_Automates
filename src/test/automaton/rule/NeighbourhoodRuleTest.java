package test.automaton.rule;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.automaton.rule.NeighbourhoodRule;

public class NeighbourhoodRuleTest {
	@Test
	public void testNeighbourhoodRuleConstructor() {
		char state = 'A';
		char result = 'B';
		double probability = 0.5;
		char[] neighbours = {'X', 'Y', 'Z'};
		NeighbourhoodRule rule = new NeighbourhoodRule(state, result, probability, neighbours);
		assertEquals(state, rule.getState());
		assertEquals(result, rule.getResult());
		assertEquals(probability, rule.getProbability(), 0.0001);
		assertEquals(neighbours, rule.getNeighbours());
	}

	@Test
	public void testApplyRule() {
		// Define the rule
		char state = 'A';
		char result = 'B';
		double probability = 1;
		char[] neighbours = {'X', 'Y', 'Z'};
		NeighbourhoodRule rule = new NeighbourhoodRule(state, result, probability, neighbours);

		// Test applying the rule
		char cell = 'A';
		char[] inputNeighbours = {'X', 'Y', 'Z'};
		char expected = 'B';
		char actual = rule.apply(cell, inputNeighbours);
		assertEquals(expected, actual);
	}
}
