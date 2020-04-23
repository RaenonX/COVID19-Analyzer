import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class TestStateNameConverter {
    @Test
    void test_state_name() throws IOException {
        StateNameConverter converter = new StateNameConverter("res/states.csv");

        assertEquals("Washington", converter.getFullName("WA"));
        assertEquals("Wisconsin", converter.getFullName("WI"));
        assertEquals("X", converter.getFullName("X"));
    }
}
