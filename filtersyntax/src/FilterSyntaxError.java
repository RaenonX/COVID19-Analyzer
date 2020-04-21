public class FilterSyntaxError extends Exception {
    public final FilterSyntaxErrorReason reason;

    public FilterSyntaxError(FilterSyntaxErrorReason reason, String message) {
        super(String.format("Reason: %s - %s", reason, message));
        this.reason = reason;
    }

    public FilterSyntaxError(FilterSyntaxErrorReason reason) {
        this(reason, "");
    }

    public FilterSyntaxErrorReason getReason() {
        return reason;
    }
}
