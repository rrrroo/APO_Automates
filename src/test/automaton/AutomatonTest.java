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
			assertEquals(50, automaton.getGrid().getSize());
			assertEquals(true, Arrays.equals(new char[] { '.', '#' }, automaton.getAlphabet()));
			List<int[]> expectedNeighbourhood = new ArrayList<>();
			expectedNeighbourhood.add(new int[] {-1});
			expectedNeighbourhood.add(new int[] {1});
			assertEquals(true, Arrays.deepEquals(expectedNeighbourhood.toArray(), automaton.getNeighbourhood().toArray()));
			assertEquals(8, automaton.getRules().size());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testAutomatonConstructorWithFilenameAndSaveGrid() {
		try {
			Automaton automaton = new Automaton("src/test/automaton/config.json", "src/test/automaton/grid/grid.txt");

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
			assertEquals('A', automaton.getGrid().getCellState(0, 0, 0));
			assertEquals('A', automaton.getGrid().getCellState(0, 1, 0));
			assertEquals('A', automaton.getGrid().getCellState(0, 2, 0));
			assertEquals('B', automaton.getGrid().getCellState(1, 0, 0));
			assertEquals('B', automaton.getGrid().getCellState(1, 1, 0));
			assertEquals('B', automaton.getGrid().getCellState(1, 2, 0));
			assertEquals('C', automaton.getGrid().getCellState(2, 0, 0));
			assertEquals('C', automaton.getGrid().getCellState(2, 1, 0));
			assertEquals('C', automaton.getGrid().getCellState(2, 2, 0));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testAutomatonGetRuleNumber() {
		try {
			int a = 150;
			Automaton automaton = new Automaton(a);
			assertEquals(a, automaton.getRuleNumber());
			Automaton automaton2 = new Automaton("data/configs/1D.json");
			assertEquals(170, automaton2.getRuleNumber());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testAutomatonToString(){
		try {
			Automaton automaton = new Automaton("src/test/automaton/config.json", "src/test/automaton/grid/grid.txt");
			assertEquals(" A  B  C \n A  B  C \n A  B  C \n\n\n", automaton.toString());
			Automaton automaton2 = new Automaton(150);
			char [] grid = new char[50];
			for(int i = 0; i < 50; i++){
				grid[i] = automaton2.getGrid().getCellState(i, 0, 0);
			}
			String gridString = new String();
			for(int i = 0; i < 50; i++){
				gridString += " " + grid[i] + " ";
			}
			String expected = "Règle n°150\n"+gridString+"\n\n";
			assertEquals(expected, automaton2.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testAutomatonSaveWithFileName(){
		try {
			Automaton automaton = new Automaton("src/test/automaton/config.json", "src/test/automaton/grid/grid.txt");
			automaton.save("src/test/automaton/grid/grid2.txt");
			Automaton automaton2 = new Automaton("src/test/automaton/config.json", "src/test/automaton/grid/grid2.txt");
			assertEquals(" A  B  C \n A  B  C \n A  B  C \n\n\n", automaton2.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
