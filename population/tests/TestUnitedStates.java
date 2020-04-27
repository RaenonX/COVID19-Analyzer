import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitedStates {
    private static State wi;
    private static State wa;

    private static County dane;
    private static County laCrosse;
    private static County seattle;

    @BeforeAll
    static void load_data() throws InvalidCountyNameException, InvalidLatitudeException, InvalidLongitudeException, InvalidPopulationCount {
        dane = new County(
                "Dane", 47.0, -120.0, 50000, Arrays.asList(53714, 53715, 53716));
        laCrosse = new County(
                "La Crosse", 43.8, -91.4, 70000, Arrays.asList(53711, 53712, 53713));

        wi = new State("WI", "Wisconsin", new ArrayList<>() {{
            add(dane);
            add(laCrosse);
        }});

        seattle = new County(
                "Seattle", 45.8, -121.4, 30000, Arrays.asList(90173, 90174, 90175));

        wa = new State("WA", "Washington", new ArrayList<>() {{
            add(seattle);
        }});

        UnitedStates.load(new ArrayList<>() {{
            add(wi);
            add(wa);
        }});
    }

    @Test
    void test_properties() {
        assertArrayEquals(new State[] {wi, wa}, UnitedStates.current.getStates().toArray());
    }

    @Test
    void test_get_state() {
        assertNull(UnitedStates.current.getState(""));
        assertNull(UnitedStates.current.getState("SS"));
        assertEquals(wi, UnitedStates.current.getState("WI"));
        assertEquals(wa, UnitedStates.current.getState("WA"));
    }

    @Test
    void test_get_county() throws InvalidStateNameException {
        assertNull(UnitedStates.current.getCounty(""));
        assertNull(UnitedStates.current.getCounty("Seattle"));
        assertNull(UnitedStates.current.getCounty("Seattle, WI"));
        assertNull(UnitedStates.current.getCounty("Seattle, WA, US"));
        assertEquals(dane, UnitedStates.current.getCounty("Dane, WI"));
        assertEquals(dane, UnitedStates.current.getCounty("Dane, Wisconsin"));
        assertEquals(dane, UnitedStates.current.getCounty("Dane", "WI"));
        assertEquals(dane, UnitedStates.current.getCounty("Dane", "Wisconsin"));
        assertEquals(seattle, UnitedStates.current.getCounty("Seattle, WA"));
        assertEquals(laCrosse, UnitedStates.current.getCounty("La Crosse, WI"));
    }

    @Test
    void test_population() {
        assertEquals(150000, UnitedStates.current.getPopulation());
        assertEquals(
                120000,
                UnitedStates.current.getPopulation(state -> state.getAbbr().equalsIgnoreCase("WI")));
        assertEquals(
                30000,
                UnitedStates.current.getPopulation(state -> state.getAbbr().equalsIgnoreCase("WA")));
    }
}
