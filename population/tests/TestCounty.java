import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TestCounty {
    @Test
    void test_county_properties() throws InvalidLatitudeException, InvalidLongitudeException, InvalidPopulationCount, InvalidCountyNameException {
        County county = new County(
                "Dane", 47.36, -120.88, 10000, Arrays.asList(53714, 53715, 53716));
        assertEquals("Dane", county.getName());
        assertEquals(47.36, county.getLatitude());
        assertEquals(-120.88, county.getLongitude());
        assertEquals(10000, county.getPopulation());
        assertArrayEquals(new Integer[] {53714, 53715, 53716}, county.getZips().toArray());
    }

    @Test
    void test_county_invalid_lat() {
        assertThrows(
                InvalidLatitudeException.class,
                () -> new County(
                        "Dane", 90.36, -120.88, //-90~90 ; -180~180
                        10000, Arrays.asList(53714, 53715, 53716)
                )
        );
        assertThrows(
                InvalidLatitudeException.class,
                () -> new County(
                        "Dane", -90.36, -120.88,
                        10000, Arrays.asList(53714, 53715, 53716)
                )
        );
    }

    @Test
    void test_county_invalid_lon() {
        assertThrows(
                InvalidLongitudeException.class,
                () -> new County(
                        "Dane", 0.36, -1120.88,
                        10000, Arrays.asList(53714, 53715, 53716)
                )
        );
        assertThrows(
                InvalidLongitudeException.class,
                () -> new County(
                        "Dane", 0.36, 1120.88,
                        10000, Arrays.asList(53714, 53715, 53716)
                )
        );
    }

    @Test
    void test_county_invalid_population() {
        assertThrows(
                InvalidPopulationCount.class,
                () -> new County(
                        "Dane", 0.36, -120.88,
                        -1, Arrays.asList(53714, 53715, 53716)
                )
        );
    }

    @Test
    void test_county_empty_zip_codes() throws InvalidLatitudeException, InvalidLongitudeException, InvalidPopulationCount, InvalidCountyNameException {
        new County("Dane", 0.36, 120.88, 1, Collections.emptyList());
    }

    @Test
    void test_county_invalid_county_name() {
        assertThrows(
                InvalidCountyNameException.class,
                () -> new County(
                        null, 0.36, -120.88, //cant be null
                        1000, Arrays.asList(53714, 53715, 53716)
                )
        );
        assertThrows(
                InvalidCountyNameException.class,
                () -> new County(
                        "", 0.36, -120.88,
                        1000, Arrays.asList(53714, 53715, 53716)
                )
        );
        assertThrows(
                InvalidCountyNameException.class,
                () -> new County(
                        " ", 0.36, -120.88,
                        1000, Arrays.asList(53714, 53715, 53716)
                )
        );
        assertThrows(
                InvalidCountyNameException.class,
                () -> new County(
                        "%&(#^%", 0.36, -120.88,
                        1000, Arrays.asList(53714, 53715, 53716)
                )
        );
    }
}
