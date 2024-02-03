package test.automaton.grid;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import main.automaton.Dimension;
import main.automaton.grid.Grid;


public class GridTest {
	@Test
	public void testGridConstructor() {
		Dimension dimension = Dimension.TWO_D;
		int size = 10;
		char initialState = 'A';

		Grid grid = new Grid(dimension, size, initialState);

		assertEquals(dimension, grid.getDimension());
		assertEquals(size, grid.getSize());
		assertEquals(initialState, grid.getCellState(0, 0, 0));
	}

	@Test
	public void testGridCopyConstructor() {
		Dimension dimension = Dimension.TWO_D;
		int size = 10;
		char initialState = 'A';

		Grid originalGrid = new Grid(dimension, size, initialState);
		Grid copiedGrid = new Grid(originalGrid);

		assertEquals(originalGrid.getDimension(), copiedGrid.getDimension());
		assertEquals(originalGrid.getSize(), copiedGrid.getSize());
		assertEquals(originalGrid.getCellState(0, 0, 0), copiedGrid.getCellState(0, 0, 0));
	}

	@Test
	public void testGridFromFileConstructor() {
		Dimension dimension = Dimension.TWO_D;
		String filename = "src/test/automaton/grid/grid.txt";
		char[] alphabet = {'A', 'B', 'C'};

		Grid grid = new Grid(dimension, filename, alphabet);

		assertEquals(dimension, grid.getDimension());
		assertEquals(3, grid.getSize());
		assertEquals('A', grid.getCellState(0, 0, 0));
	}

	@Test
	public void testGetNeighboursState() {
		Dimension dimension = Dimension.TWO_D;
		int size = 10;
		char initialState = 'A';

		Grid grid = new Grid(dimension, size, initialState);

		// Define the coordinates of the center cell
		int x = 5;
		int y = 5;
		int z = 0;

		// Define the neighborhood relative coordinates
		List<int[]> neighborhood = new ArrayList<>();
		neighborhood.add(new int[]{-1, -1, 0});
		neighborhood.add(new int[]{0, -1, 0});
		neighborhood.add(new int[]{1, -1, 0});
		neighborhood.add(new int[]{-1, 0, 0});
		neighborhood.add(new int[]{1, 0, 0});
		neighborhood.add(new int[]{-1, 1, 0});
		neighborhood.add(new int[]{0, 1, 0});
		neighborhood.add(new int[]{1, 1, 0});

		// Set the states of the neighboring cells
		grid.setCellState(x - 1, y - 1, z, 'B');
		grid.setCellState(x, y - 1, z, 'C');
		grid.setCellState(x + 1, y - 1, z, 'D');
		grid.setCellState(x - 1, y, z, 'E');
		grid.setCellState(x + 1, y, z, 'F');
		grid.setCellState(x - 1, y + 1, z, 'G');
		grid.setCellState(x, y + 1, z, 'H');
		grid.setCellState(x + 1, y + 1, z, 'I');

		// Get the states of the neighboring cells
		char[] neighborsState = grid.getNeighboursState(x, y, z, neighborhood);

		// Assert the expected states of the neighboring cells
		assertEquals('B', neighborsState[0]);
		assertEquals('C', neighborsState[1]);
		assertEquals('D', neighborsState[2]);
		assertEquals('E', neighborsState[3]);
		assertEquals('F', neighborsState[4]);
		assertEquals('G', neighborsState[5]);
		assertEquals('H', neighborsState[6]);
		assertEquals('I', neighborsState[7]);
	}

	@Test
	public void testToString() {
		Dimension dimension = Dimension.TWO_D;
		int size = 3;
		char initialState = 'A';

		Grid grid = new Grid(dimension, size, initialState);

		// Set the states of some cells
		grid.setCellState(0, 0, 0, 'B');
		grid.setCellState(1, 1, 0, 'C');
		grid.setCellState(2, 2, 0, 'D');

		// Define the expected string representation of the grid
		String expected = " B  A  A \n A  C  A \n A  A  D \n\n";

		// Assert the expected string representation
		assertEquals(expected, grid.toString());
	}
}
