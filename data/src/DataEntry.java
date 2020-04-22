import java.time.LocalDate;

/**
 * Represents a single data entry.
 */
public class DataEntry implements IGUITableEntry {
    private final LocalDate date;
    private final State state;
    private final County county;
    private final int confirmed;
    private final int fatal;
    private final double confirmedPer100K;
    private final double fatalPer100K;

    public DataEntry(
            LocalDate date, State state, County county, int confirmed, int fatal) {
        this.date = date;
        this.state = state;
        this.county = county;
        this.confirmed = confirmed;
        this.fatal = fatal;

        if (county != null) {
            int countyPop = county.getPopulation();

            this.confirmedPer100K = confirmed / (double)county.getPopulation() * 100000;
            this.fatalPer100K = fatal / (double)county.getPopulation() * 100000;
        } else {
            this.confirmedPer100K = -1;
            this.fatalPer100K = -1;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public State getState() {
        return state;
    }

    public County getCounty() {
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