package test.automaton;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.automaton.Dimension;

public class DimensionTest {

	@Test
	public void testFromStringValidValue() {
		Dimension dimension = Dimension.fromString("1D");
		assertEquals(Dimension.ONE_D, dimension);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromStringInvalidValue() {
		Dimension.fromString("4D");
	}
	
}
