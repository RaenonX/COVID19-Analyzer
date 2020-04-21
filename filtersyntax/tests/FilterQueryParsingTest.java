import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterQueryParsingTest {
    @Test
    void test_parse_single() {
        try {
            FilterCondition cond = FilterQueryParser.parse("%state% = WI");
            assertEquals(new FilterConditionEntity(
                    FilterParameter.STATE, FilterComparator.EQ, "WI"), cond.getConditions().get(0).get(0));
        } catch (FilterSyntaxError filterSyntaxError) {
            filterSyntaxError.printStackTrace();
            fail("FilterSyntaxError raised");
        }
    }

    @Test
    void test_parse_single_and() {
        try {
            FilterCondition cond = FilterQueryParser.parse("%state% = WI & %confirmed% > 70");
            assertEquals(new FilterConditionEntity(
                    FilterParameter.STATE, FilterComparator.EQ, "WI"), cond.getConditions().get(0).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.CONFIRMED, FilterComparator.GT, "70"), cond.getConditions().get(0).get(1));
        } catch (FilterSyntaxError filterSyntaxError) {
            filterSyntaxError.printStackTrace();
            fail("FilterSyntaxError raised");
        }
    }

    @Test
    void test_parse_single_or() {
        try {
            FilterCondition cond = FilterQueryParser.parse("%state% = WI | %confirmed% > 70");
            assertEquals(new FilterConditionEntity(
                    FilterParameter.STATE, FilterComparator.EQ, "WI"), cond.getConditions().get(0).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.CONFIRMED, FilterComparator.GT, "70"), cond.getConditions().get(1).get(0));
        } catch (FilterSyntaxError filterSyntaxError) {
            filterSyntaxError.printStackTrace();
            fail("FilterSyntaxError raised");
        }
    }

    @Test
    void test_parse_complex1() {
        try {
            FilterCondition cond = FilterQueryParser.parse("%state% = WI | %confirmed% > 70 & %fatal% > 5");
            assertEquals(new FilterConditionEntity(
                    FilterParameter.STATE, FilterComparator.EQ, "WI"), cond.getConditions().get(0).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.CONFIRMED, FilterComparator.GT, "70"), cond.getConditions().get(1).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.FATAL, FilterComparator.GT, "5"), cond.getConditions().get(1).get(1));
        } catch (FilterSyntaxError filterSyntaxError) {
            filterSyntaxError.printStackTrace();
            fail("FilterSyntaxError raised");
        }
    }

    @Test
    void test_parse_complex2() {
        try {
            FilterCondition cond =
                    FilterQueryParser.parse("%state% = WI | %confirmed% > 70 & %fatal% > 5 | %zip% = 53714");
            assertEquals(new FilterConditionEntity(
                    FilterParameter.STATE, FilterComparator.EQ, "WI"), cond.getConditions().get(0).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.CONFIRMED, FilterComparator.GT, "70"), cond.getConditions().get(1).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.FATAL, FilterComparator.GT, "5"), cond.getConditions().get(1).get(1));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.ZIP_CODE, FilterComparator.EQ, "53714"), cond.getConditions().get(2).get(0));
        } catch (FilterSyntaxError filterSyntaxError) {
            filterSyntaxError.printStackTrace();
            fail("FilterSyntaxError raised");
        }
    }

    @Test
    void test_parse_complex3() {
        try {
            FilterCondition cond =
                    FilterQueryParser.parse("%state% = WI & %confirmed% > 70 | %fatal% > 5 & %zip% = 53714");
            assertEquals(new FilterConditionEntity(
                    FilterParameter.STATE, FilterComparator.EQ, "WI"), cond.getConditions().get(0).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.CONFIRMED, FilterComparator.GT, "70"), cond.getConditions().get(0).get(1));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.FATAL, FilterComparator.GT, "5"), cond.getConditions().get(1).get(0));
            assertEquals(new FilterConditionEntity(
                    FilterParameter.ZIP_CODE, FilterComparator.EQ, "53714"), cond.getConditions().get(1).get(1));
        } catch (FilterSyntaxError filterSyntaxError) {
            filterSyntaxError.printStackTrace();
            fail("FilterSyntaxError raised");
        }
    }

    @Test
    void test_parse_parentheses() {
        try {
            FilterQueryParser.parse("(%state% = WI | %confirmed% > 70)");
            fail("FilterSyntaxError not raised");
        } catch (FilterSyntaxError filterSyntaxError) {
            if (filterSyntaxError.getReason() != FilterSyntaxErrorReason.PARENTHESES_NOT_ALLOWED) {
                filterSyntaxError.printStackTrace();
                fail(String.format("FilterSyntaxError raised with unexpected reason: %s", filterSyntaxError.getReason()));
            }
        }

        try {
            FilterQueryParser.parse("%state% = WI | %confirmed% > 70)");
            fail("FilterSyntaxError not raised");
        } catch (FilterSyntaxError filterSyntaxError) {
            if (filterSyntaxError.getReason() != FilterSyntaxErrorReason.PARENTHESES_NOT_ALLOWED) {
                filterSyntaxError.printStackTrace();
                fail(String.format("FilterSyntaxError raised with unexpected reason: %s", filterSyntaxError.getReason()));
            }
        }

        try {
            FilterQueryParser.parse("(%state% = WI) | %confirmed% > 70");
            fail("FilterSyntaxError not raised");
        } catch (FilterSyntaxError filterSyntaxError) {
            if (filterSyntaxError.getReason() != FilterSyntaxErrorReason.PARENTHESES_NOT_ALLOWED) {
                filterSyntaxError.printStackTrace();
                fail(String.format("FilterSyntaxError raised with unexpected reason: %s", filterSyntaxError.getReason()));
            }
        }

        try {
            FilterQueryParser.parse("(%state% = WI | %confirmed% > 70");
            fail("FilterSyntaxError not raised");
        } catch (FilterSyntaxError filterSyntaxError) {
            if (filterSyntaxError.getReason() != FilterSyntaxErrorReason.PARENTHESES_NOT_ALLOWED) {
                filterSyntaxError.printStackTrace();
                fail(String.format("FilterSyntaxError raised with unexpected reason: %s", filterSyntaxError.getReason()));
            }
        }
    }
}