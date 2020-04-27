@SuppressWarnings("serial")
public class InvalidPopulationCount extends Exception {
    public InvalidPopulationCount(int actual) {
        super(String.format("Population should be a positive integer. Actual: %d", actual));
    }
}
