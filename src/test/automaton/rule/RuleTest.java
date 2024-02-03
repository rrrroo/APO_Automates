package test.automaton.rule;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.automaton.rule.Rule;

public class RuleTest {
	@Test
	public void testRuleConstructor() {
		Rule rule = new Rule('A', 'B', 0.5);
		assertEquals('A', rule.getState());
		assertEquals('B', rule.getResult());
		assertEquals(0.5, rule.getProbability(), 0.0001);
	}

	@Test
	public void testApplyRule() {
		Rule rule = new Rule('A', 'B', 0.5);
		char result = rule.apply('A', new char[]{'A', 'A', 'A'});
		assertEquals('A', result);
	}
}
