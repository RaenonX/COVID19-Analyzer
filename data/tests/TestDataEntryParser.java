import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataEntryParser {
    @BeforeAll
    static void prepare() throws IOException {
        PopulationDataParser.loadUsPopFile("res/pops.csv", new StateNameConverter("res/states.csv"));
    }

    @Test
    void parse_normal() {
        String[] entry = new String[] {"2020-01-28", "Dane", "WI", "1", "0"};

        DataEntry e = DataEntryParser.parse(entry);
        assertEquals(LocalDate.of(2020, Month.JANUARY, 28), e.getDate());

        County c = e.getCounty();
        assertEquals("Dane", c.getName());

        State s = e.getState();
        assertEquals("WI", s.getAbbr());

        assertEquals(1, e.getConfirmed());
        assertEquals(0, e.getFatal());
    }

    @Test
    void parse_empty_county() {
        String[] entry = new String[] {"2020-01-28", "", "WI", "1", "0"};

        DataEntry e = DataEntryParser.parse(entry);
        assertEquals(LocalDate.of(2020, Month.JANUARY, 28), e.getDate());

        assertNull(e.getCounty());

        State s = e.getState();
        assertEquals("WI", s.getAbbr());

        assertEquals(1, e.getConfirmed());
        assertEquals(0, e.getFatal());
    }

    @Test
    void parse_empty_state() {
        assertThrows(
                InvalidStateException.class,
                () -> DataEntryParser.parse(new String[] {"2020-01-28", "Dane", "", "1", "0"}));
        assertThrows(
                InvalidStateException.class,
                () -> DataEntryParser.parse(new String[] {"2020-01-28", "Dane", " ", "1", "0"}));
    }

    @Test
    void parse_invalid_confirmed() {
        assertThrows(
                InvalidConfirmedCaseException.class,
                () -> DataEntryParser.parse(new String[] {"2020-01-28", "Dane", "WI", "-1", "0"}));
        assertThrows(
                InvalidConfirmedCaseException.class,
                () -> DataEntryParser.parse(new String[] {"2020-01-28", "Dane", "WI", "A", "0"}));
    }

    @Test
    void parse_invalid_fatal() {
        assertThrows(
                InvalidFatalCaseException.class,
                () -> DataEntryParser.parse(new String[] {"2020-01-28", "Dane", "WI", "1", "-1"}));
        assertThrows(
                InvalidFatalCaseException.class,
                () -> DataEntryParser.parse(new String[] {"2020-01-28", "Dane", "WI", "1", "A"}));
    }

    @Test
    void parse_invalid_date() {
        assertThrows(
                InvalidFatalCaseException.class,
                () -> DataEntryParser.parse(new String[] {"A", "Dane", "WI", "1", "1"}));
        assertThrows(
                InvalidFatalCaseException.class,
                () -> DataEntryParser.parse(new String[] {"2023-01-28", "Dane", "WI", "1", "1"}));
    }
}
