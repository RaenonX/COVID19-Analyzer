import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TestState {
    @Test
    void test_state_properties() throws InvalidCountyNameException, InvalidLatitudeException, InvalidLongitudeException, InvalidPopulationCount {
        County dane = new County(
                "Dane", 47.0, -120.0, 50000, Arrays.asList(53714, 53715, 53716));
        County laCrosse = new County(
                "La Crosse", 43.8, -91.4, 70000, Arrays.asList(53711, 53712, 53713));

        State state = new State("WI", "Wisconsin", new ArrayList<>() {{
            add(dane);
            add(laCrosse);
        }});

        assertEquals("WI", state.getAbbr());
        assertEquals("Wisconsin", state.getName());
        assertEquals(120000, state.getPopulation());
        assertEquals(50000, state.getPopulation(x -> x.getName().equals("Dane")));
        assertArrayEquals(new County[]{dane, laCrosse}, state.getCounties().toArray());
    }

    @Test
    void test_state_invalid_name() {
        assertThrows(
                InvalidStateNameException.class,
                () -> new State("WI", "", Collections.emptyList())
        );
        assertThrows(
                InvalidStateNameException.class,
                () -> new State(" ", "", Collections.emptyList())
        );
        assertThrows(
                InvalidStateNameException.class,
                () -> new State("%*()#%&^", "", Collections.emptyList())
        );
        assertThrows(
                InvalidStateNameException.class,
                () -> new State("", "Wisconsin", Collections.emptyList())
        );
        assertThrows(
                InvalidStateNameException.class,
                () -> new State("", " ", Collections.emptyList())
        );
        assertThrows(
                InvalidStateNameException.class,
                () -> new State("", "%*()#^", Collections.emptyList())
        );
    }
}
