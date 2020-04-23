import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class PopulationTestSuite {
    @Nested
    @DisplayName("TestCounty")
    class TestA extends TestCounty {}

    @Nested
    @DisplayName("TestState")
    class TestB extends TestState {}

    @Nested
    @DisplayName("TestUnitedStates")
    class TestC extends TestUnitedStates {}

    @Nested
    @DisplayName("TestStateNameConverter")
    class TestD extends TestStateNameConverter {}

    @Nested
    @DisplayName("TestPopulationDataParser")
    class TestE extends TestPopulationDataParser {}
}
