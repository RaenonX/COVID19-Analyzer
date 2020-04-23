public class InvalidLongitudeException extends Exception {
    public InvalidLongitudeException(double actual) {
        super(String.format("Longitude should be -180 <= lat <= 180. Actual: %f", actual));
    }
}
