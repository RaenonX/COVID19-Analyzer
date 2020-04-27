import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPopulationDataParser {
    private static StateNameConverter converter;

    @BeforeAll
    static void prepare() throws IOException {
        converter = new StateNameConverter("res/states.csv");
    }

    @Test
    void test_parse_pop_file() throws IOException, InvalidStateNameException, InvalidLatitudeException, InvalidLongitudeException {
        PopulationDataParser.loadUsPopFile("res/pops.csv", converter);

        State wa = UnitedStates.current.getState("WA");
        assertNotNull(wa);
        assertEquals("WA", wa.getAbbr());
        assertEquals("Washington", wa.getName());

        State wi = UnitedStates.current.getState("WI");
        assertNotNull(wi);
        assertEquals("WI", wi.getAbbr());
        assertEquals("Wisconsin", wa.getName());

        County pierce = UnitedStates.current.getCounty("Pierce, WA");
        assertNotNull(pierce);
        assertTrue(wa.getCounties().contains(pierce));
        assertEquals("Pierce", pierce.getName());
        assertEquals(809950, pierce.getPopulation());
        assertEquals(47.16, pierce.getLatitude());
        assertEquals(-122.41, pierce.getLongitude());
        assertEquals(new Integer[] {98580, 98387, 98338, 98332, 98335}, pierce.getZips().toArray());

        County kittitas = UnitedStates.current.getCounty("Kittitas, WA");
        assertNotNull(kittitas);
        assertTrue(wa.getCounties().contains(kittitas));
        assertEquals("Kittitas", kittitas.getName());
        assertEquals(31466, kittitas.getPopulation());
        assertEquals(47.15, kittitas.getLatitude());
        assertEquals(-120.83, kittitas.getLongitude());
        assertEquals(new Integer[] {98941, 98068, 98925, 98934, 98926}, kittitas.getZips().toArray());

        County dane = UnitedStates.current.getCounty("Dane, WI");
        assertNotNull(pierce);
        assertEquals("Dane", dane.getName());
        assertTrue(wi.getCounties().contains(dane));
        assertEquals(30445, dane.getPopulation());
        assertEquals(47.15, dane.getLatitude());
        assertEquals(-120.83, dane.getLongitude());
        assertEquals(new Integer[] {}, dane.getZips().toArray());
    }
}
