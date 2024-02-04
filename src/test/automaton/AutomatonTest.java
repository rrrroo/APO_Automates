package test.automaton;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import main.automaton.Automaton;
import main.automaton.Dimension;


public class AutomatonTest {

	@Test
	public void testAutomatonConstructorWithFilename() {
		try {
			Automaton automaton = new Automaton("src/test/automaton/config.json");

			assertEquals(Dimension.TWO_D, automaton.getDimension());
			assertEquals(3, automaton.getGrid().getSize());
			assertEquals(true, Arrays.equals(new char[] { 'A', 'B', 'C' }, automaton.getAlphabet()));
			List<int[]> expectedNeighbourhood = new ArrayList<>();
			expectedNeighbourhood.add(new int[] {-1, 0});
			expectedNeighbourhood.add(new int[] {1, 0});
			expectedNeighbourhood.add(new int[] {0, -1});
			expectedNeighbourhood.add(new int[] {0, 1});
			assertEquals(true, Arrays.deepEquals(expectedNeighbourhood.toArray(), automaton.getNeighbourhood().toArray()));
			assertEquals(1, automaton.getRules().size());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testAutomatonConstructorWithRuleNb() {
		try {
			Automaton automaton = new Automaton(3);

			assertEquals(Dimension.ONE_D, automaton.getDimension());
			assertEquals(3, automaton.getGrid().getSize());
			assertEquals(true, Arrays.equals(new char[] { 'A', 'B', 'C' }, automaton.getAlphabet()));
			List<int[]> expectedNeighbourhood = new ArrayList<>();
			expectedNeighbourhood.add(new int[] {-1, 0});
			expectedNeighbourhood.add(new int[] {1, 0});
			expectedNeighbourhood.add(new int[] {0, -1});
			expectedNeighbourhood.add(new int[] {0, 1});
			assertEquals(true, Arrays.deepEquals(expectedNeighbourhood.toArray(), automaton.getNeighbourhood().toArray()));
			assertEquals(1, automaton.getRules().size());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
