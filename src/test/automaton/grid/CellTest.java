package test.automaton.grid;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.automaton.grid.Cell;

public class CellTest {
	@Test
	public void testGetState() {
		Cell cell = new Cell('A');
		assertEquals('A', cell.getState());
	}

	@Test
	public void testSetState() {
		Cell cell = new Cell('A');
		cell.setState('B');
		assertEquals('B', cell.getState());
	}

	@Test
	public void testToString() {
		Cell cell = new Cell('A');
		assertEquals(" A ", cell.toString());
	}
}
