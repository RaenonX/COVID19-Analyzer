import java.time.LocalDate;

public class TempDataEntry implements IGUITableEntry {
    private final LocalDate date;
    private final String state;
    private final String county;
    private final int confirmed;
    private final int fatal;
    private final double confirmedPer100K;
    private final double fatalPer100K;

    public TempDataEntry(
            LocalDate date, String state, String county, int confirmed, int fatal, int population) {
        this.date = date;
        this.state = state;
        this.county = county;
        this.confirmed = confirmed;
        this.fatal = fatal;
        this.confirmedPer100K = confirmed / (double)population * 100000;
        this.fatalPer100K = fatal / (double)population * 100000;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getCounty() {
        return county;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getFatal() {
        return fatal;
    }

    public double getConfirmedPer100K() {
        return confirmedPer100K;
    }

    public double getFatalPer100K() {
        return fatalPer100K;
    }
}