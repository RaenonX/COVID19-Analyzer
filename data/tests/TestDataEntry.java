import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataEntry {
    private static County county;
    private static State state;

    @BeforeAll
    static void prepare() {
        county = new County(
                "Dane", 47.3, -120.1, 10000,
                new ArrayList<>() {{ add(53714); add(53715); add(53716); }});
        state = new State("WI", "Wisconsin", new ArrayList<>() {{ add(county); }});
    }

    @Test
    void test_properties() {
        DataEntry e = new DataEntry(
                LocalDate.of(2020, Month.APRIL, 20), state, county, 100, 10);
        assertEquals(100, e.getConfirmed());
        assertEquals(1000, e.getConfirmedPer100K());
        assertEquals(10, e.getFatal());
        assertEquals(100, e.getFatalPer100K());
        assertEquals(county, e.getCounty());
        assertEquals(state, e.getState());
        assertEquals(LocalDate.of(2020, Month.APRIL, 20), e.getDate());
    }

    @Test
    void test_null_county() {
        // null county should pass because some data does not record county
        new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, null, 100, 10);
    }

    @Test
    void test_invalid_state() {
        // null state, should not pass
        assertThrows(
                InvalidStateException.class,
                () -> new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 20), null, county, 100, 10)
        );
    }

    @Test
    void test_invalid_date() {
        // null date
        assertThrows(
                InvalidDateException.class,
                () -> new DataEntry(
                        null, state, county, 100, 10)
        );
        // date in the future
        assertThrows(
                InvalidDateException.class,
                () -> new DataEntry(
                        LocalDate.of(2023, Month.APRIL, 20), state, county, 100, 10)
        );
    }

    @Test
    void test_invalid_confirmed() {
        // negative case count
        assertThrows(
                InvalidCountyException.class,
                () -> new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 20), null, county, -1, 10)
        );
        // case count > population
        assertThrows(
                InvalidCountyException.class,
                () -> new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 20), null, county, 10001, 10)
        );
    }

    @Test
    void test_invalid_fatal() {
        // negative case count
        assertThrows(
                InvalidCountyException.class,
                () -> new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 20), null, county, 100, -1)
        );
        // case count > population
        assertThrows(
                InvalidCountyException.class,
                () -> new DataEntry(
                        LocalDate.of(2020, Month.APRIL, 20), null, county, 100, 10001)
        );
    }
}
