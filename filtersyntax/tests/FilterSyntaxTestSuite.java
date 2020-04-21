import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class FilterSyntaxTestSuite {
    @Nested
    @DisplayName("FilterComparatorParsingTest")
    class TestA extends FilterComparatorParsingTest {}

    @Nested
    @DisplayName("FilterParameterParsingTest")
    class TestB extends FilterParameterParsingTest {}

    @Nested
    @DisplayName("FilterQueryParsingTest")
    class TestC extends FilterQueryParsingTest {}
}
