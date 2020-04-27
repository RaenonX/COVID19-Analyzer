import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

            this.confirmedPer100K = confirmed / (double) countyPop * 100000;
            this.fatalPer100K = fatal / (double) countyPop * 100000;
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

    /**
     * Convert the data entry to a line of string following the convention in {@code DataEntryParser}.
     *
     * @return collated and prepared table entry string representing this data entry
     */
    public String toTableEntry() {
        Map<Integer, String> dataMap = new HashMap<>() {{
            put(
                    DataEntryFileProcessor.IDX_DATE,
                    String.format("%" + DataEntryFileProcessor.LEN_DATE + "s", getDate().toString()));
            put(
                    DataEntryFileProcessor.IDX_COUNTY,
                    String.format(
                            "%" + DataEntryFileProcessor.LEN_COUNTY + "s",
                            getCounty() == null ? "" : getCounty().getName()));
            put(
                    DataEntryFileProcessor.IDX_STATE,
                    String.format("%" + DataEntryFileProcessor.LEN_STATE + "s",
                            getState() == null ? "" : getState().getAbbr()));
            put(
                    DataEntryFileProcessor.IDX_CONFIRMED,
                    String.format("%" + DataEntryFileProcessor.LEN_CONFIRMED + "d", getConfirmed()));
            put(
                    DataEntryFileProcessor.IDX_FATAL,
                    String.format("%" + DataEntryFileProcessor.LEN_FATAL + "d", getFatal()));
            put(
                    DataEntryFileProcessor.IDX_CONFIRMED_PER_100K,
                    String.format(
                            "%" + DataEntryFileProcessor.LEN_CONFIRMED_PER_100K + "s",
                            getConfirmedPer100K() == -1 ? "-" : String.format("%.2f", getConfirmedPer100K())));
            put(
                    DataEntryFileProcessor.IDX_FATAL_PER_100K,
                    String.format(
                            "%" + DataEntryFileProcessor.LEN_FATAL_PER_100K + "s",
                            getFatalPer100K() == -1 ? "-" : String.format("%.2f", getFatalPer100K())));
        }};

        return IntStream
                .range(0, dataMap.size())
                .mapToObj(dataMap::get)
                .collect(Collectors.joining(DataEntryFileProcessor.TBL_SPLITTER));
    }
}