import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FilterParameterParsingTest {
    @Test
    void test_parse() {
        assertEquals(FilterParameter.STATE, FilterParameter.parse("%state%"));
        assertEquals(FilterParameter.COUNTY, FilterParameter.parse("%county%"));
        assertEquals(FilterParameter.CONFIRMED, FilterParameter.parse("%confirmed%"));
        assertEquals(FilterParameter.FATAL, FilterParameter.parse("%fatal%"));
        assertEquals(FilterParameter.CONFIRMED_PER100K, FilterParameter.parse("%confirmed_100k%"));
        assertEquals(FilterParameter.FATAL_PER100K, FilterParameter.parse("%fatal_100k%"));
        assertEquals(FilterParameter.DEATH_RATE, FilterParameter.parse("%death_rate%"));
        assertEquals(FilterParameter.LATITUDE, FilterParameter.parse("%lat%"));
        assertEquals(FilterParameter.LONGITUDE, FilterParameter.parse("%lon%"));
        assertEquals(FilterParameter.ZIP_CODE, FilterParameter.parse("%zip%"));
        assertEquals(FilterParameter.DATE, FilterParameter.parse("%date%"));
    }

    @Test
    void test_cast() throws FilterSyntaxError {
        assertNull(FilterParameter.STATE.cast("WI")); // TODO: Should not be null after #7 is completed
        assertNull(FilterParameter.STATE.cast("Wisconsin")); // TODO: Should not be null after #7 is completed
        assertNull(FilterParameter.COUNTY.cast("Dane, WI")); // TODO: Should not be null after #7 is completed

        assertEquals(70, FilterParameter.CONFIRMED.cast("70"));
        assertEquals(70, FilterParameter.FATAL.cast("70"));
        assertEquals(50.3, FilterParameter.CONFIRMED_PER100K.cast("50.3"));
        assertEquals(50.7, FilterParameter.FATAL_PER100K.cast("50.7"));
        assertEquals(1.3, FilterParameter.DEATH_RATE.cast("1.3"));
        assertEquals(50.9, FilterParameter.LATITUDE.cast("50.9"));
        assertEquals(-25.4, FilterParameter.LONGITUDE.cast("-25.4"));
        assertEquals(53714, FilterParameter.ZIP_CODE.cast("53714"));
        assertEquals(LocalDate.of(2020, Month.APRIL, 7), FilterParameter.DATE.cast("2020-04-07"));
    }

    @Test
    void test_parse_malformed() {
        assertNull(FilterParameter.parse(null));
        assertNull(FilterParameter.parse(""));
        assertNull(FilterParameter.parse("A"));
        assertNull(FilterParameter.parse("%"));
        assertNull(FilterParameter.parse("%%"));
        assertNull(FilterParameter.parse("-"));
        assertNull(FilterParameter.parse("456789"));
    }

    @Test
    void test_cast_malformed_state() throws FilterSyntaxError {
        assertNull(FilterParameter.STATE.cast("7"));
        assertNull(FilterParameter.STATE.cast("AMP"));
    }

    @Test
    void test_cast_malformed_county() throws FilterSyntaxError {
        assertNull(FilterParameter.COUNTY.cast("9"));
        assertNull(FilterParameter.COUNTY.cast("AMP"));
    }

    @Test
    void test_cast_malformed_confirmed() {
        assertEquals(
                FilterSyntaxErrorReason.CASE_NUMBER_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.CONFIRMED.cast("A"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_NUMBER_NEGATIVE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.CONFIRMED.cast("-7"))).getReason());
    }

    @Test
    void test_cast_malformed_fatal() {
        assertEquals(
                FilterSyntaxErrorReason.CASE_NUMBER_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.FATAL.cast("A"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_NUMBER_NEGATIVE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.FATAL.cast("-7"))).getReason());
    }

    @Test
    void test_cast_malformed_confirmed_per100K() {
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.CONFIRMED_PER100K.cast("A"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.FATAL_PER100K.cast("7%"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_INVALID,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.CONFIRMED_PER100K.cast("119898.6"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_NEGATIVE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.CONFIRMED_PER100K.cast("-7"))).getReason());

    }

    @Test
    void test_cast_malformed_fatal_per100K() {
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.FATAL_PER100K.cast("A"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.FATAL_PER100K.cast("7%"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_INVALID,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.FATAL_PER100K.cast("119898.6"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.CASE_RATE_NEGATIVE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.FATAL_PER100K.cast("-7"))).getReason());
    }

    @Test
    void test_cast_malformed_death_rate() {
        assertEquals(
                FilterSyntaxErrorReason.DEATH_RATE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.DEATH_RATE.cast("1.3%"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.DEATH_RATE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.DEATH_RATE.cast("A"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.DEATH_RATE_INVALID,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.DEATH_RATE.cast("101.58"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.DEATH_RATE_NEGATIVE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.DEATH_RATE.cast("-7"))).getReason());
    }

    @Test
    void test_cast_malformed_lat() {
        assertEquals(
                FilterSyntaxErrorReason.LATITUDE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.LATITUDE.cast("A"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.LATITUDE_OVER_RANGE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.LATITUDE.cast("91"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.LATITUDE_OVER_RANGE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.LATITUDE.cast("-91"))).getReason());
    }

    @Test
    void test_cast_malformed_lon() {
        assertEquals(
                FilterSyntaxErrorReason.LONGITUDE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.LONGITUDE.cast("A"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.LONGITUDE_OVER_RANGE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.LONGITUDE.cast("181"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.LONGITUDE_OVER_RANGE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.LONGITUDE.cast("-181"))).getReason());
    }

    @Test
    void test_cast_malformed_zip() {
        assertEquals(
                FilterSyntaxErrorReason.ZIP_CODE_INVALID,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.ZIP_CODE.cast("101011"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.ZIP_CODE_UNCASTABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.ZIP_CODE.cast("WI"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.ZIP_CODE_INVALID,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.ZIP_CODE.cast("-8"))).getReason());
    }

    @Test
    void test_cast_malformed_date() {
        assertEquals(
                FilterSyntaxErrorReason.DATE_UNPARSABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.DATE.cast("101010"))).getReason());
        assertEquals(
                FilterSyntaxErrorReason.DATE_UNPARSABLE,
                assertThrows(
                        FilterSyntaxError.class,
                        () -> assertNull(FilterParameter.DATE.cast("20200407"))).getReason());
    }
}