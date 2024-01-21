package test.automaton.grid;

import main.automaton.Dimension;
import main.automaton.grid.Grid;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GridTest {
    // === Constructors ===

    // @Test
    // public void GridTest() {
    //     // TODO
    // }


    // === Getters ===

    @Test
    public void getSettingsTest() {
        Grid grid = new Grid(Dimension.TWO_D, 4, '0');
        String expected = "Dimension: 2D\nSize: 4";
        assertEquals(expected, grid.getSettings());
    }

    @Test
    public void getSizeTest() {
        Grid grid = new Grid(Dimension.TWO_D, 4, '0');
        assertEquals(4, grid.getSize());
    }

    @Test
    public void getCellTest() {
        // Valid
        Grid grid1 = new Grid(Dimension.ONE_D, 4, '0');
        assertEquals('0', grid1.getCell(1, 0, 0));
        Grid grid2 = new Grid(Dimension.TWO_D, 4, '0');
        assertEquals('0', grid2.getCell(1, 1, 0));
        Grid grid3 = new Grid(Dimension.THREE_D, 4, '0');
        assertEquals('0', grid3.getCell(1, 1, 1));

        // Invalid
        
    }
}
