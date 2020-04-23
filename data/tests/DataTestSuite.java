import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class DataTestSuite {
    @Nested
    @DisplayName("TestDataEntry")
    class TestA extends TestDataEntry {}

    @Nested
    @DisplayName("TestDataEntryParser")
    class TestB extends TestDataEntryParser {}

    @Nested
    @DisplayName("TestDataHolder")
    class TestC extends TestDataHolder {}
}
