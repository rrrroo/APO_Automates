package test.automaton;

import main.automaton.Dimension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DimensionTest {
    @Test
    public void testFromString() {
        // Valid
        assertEquals(Dimension.ONE_D, Dimension.fromString("1D"));
        assertEquals(Dimension.TWO_D, Dimension.fromString("2D"));
        assertEquals(Dimension.THREE_D, Dimension.fromString("3D"));
        assertEquals(Dimension.H, Dimension.fromString("H"));

        // Invalid
        assertThrows(IllegalArgumentException.class, () -> Dimension.fromString("4D"));
        assertThrows(IllegalArgumentException.class, () -> Dimension.fromString(""));
    }
}
