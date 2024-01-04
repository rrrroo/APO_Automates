import automaton.grid.Grid;
import automaton.Dimension;

public class Test {
	public static void main(String[] args) {
		Grid grid1D = new Grid(Dimension.ONE_D, 10, '0');
		System.out.println(grid1D.getSettings());
		grid1D.display();
		try {
			grid1D.setCellState(10, 0, 'e');
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}

		Grid grid2D = new Grid(Dimension.TWO_D, 10, '0');
		System.out.println(grid2D.getSettings());
		grid2D.display();

		Grid gridH = new Grid(Dimension.H, 4, '0');
		System.out.println(gridH.getSettings());
		gridH.display();
	}
}
