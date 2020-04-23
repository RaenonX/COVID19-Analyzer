import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFilterComparatorParsing {
    @Test
    void test_parse() {
        assertEquals(FilterComparator.GT, FilterComparator.parse(">"));
        assertEquals(FilterComparator.GTE, FilterComparator.parse(">="));
        assertEquals(FilterComparator.LT, FilterComparator.parse("<"));
        assertEquals(FilterComparator.LTE, FilterComparator.parse("<="));
        assertEquals(FilterComparator.EQ, FilterComparator.parse("="));
    }
}