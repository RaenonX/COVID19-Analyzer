public class InvalidLatitudeException extends Exception {
    public InvalidLatitudeException(double actual) {
        super(String.format("Latitude should be -90 <= lat <= 90. Actual: %f", actual));
    }
}
