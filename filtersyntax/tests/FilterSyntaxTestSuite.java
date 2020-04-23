import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class FilterSyntaxTestSuite {
    @Nested
    @DisplayName("TestFilterComparatorParsing")
    class TestA extends TestFilterComparatorParsing {}

    @Nested
    @DisplayName("TestFilterParameterParsing")
    class TestB extends TestFilterParameterParsing {}

    @Nested
    @DisplayName("TestFilterQueryParsing")
    class TestC extends TestFilterQueryParsing {}
}
