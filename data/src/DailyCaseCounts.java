import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class represents the case counts of a single day.
 */
public class DailyCaseCounts implements IGUITableEntry {
    public static final Comparator<DailyCaseCounts> cmpDate = Comparator.comparing(DailyCaseCounts::getDate);
    public static final Comparator<DailyCaseCounts> cmpDateRev = (self, other) -> -self.getDate().compareTo(other.getDate());

    private final LocalDate date;
    private final int confirmed;
    private final int confirmedDiff;
    private final int fatal;
    private final int fatalDiff;
    private final double confirmedPer100K;
    private final double fatalPer100K;

    public DailyCaseCounts(LocalDate date, int confirmed, int confirmedDiff, int fatal, int fatalDiff, int population) {
        this.date = date;
        this.confirmed = confirmed;
        this.confirmedDiff = confirmedDiff;
        this.fatal = fatal;
        this.fatalDiff = fatalDiff;

        if (population == 0) {
            this.confirmedPer100K = 0;
            this.fatalPer100K = 0;
        } else {
            this.confirmedPer100K = confirmed / (double) population * 100000;
            this.fatalPer100K = fatal / (double) population * 100000;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getConfirmedDiff() {
        return confirmedDiff;
    }

    public int getFatal() {
        return fatal;
    }

    public int getFatalDiff() {
        return fatalDiff;
    }

    public double getConfirmedPer100K() {
        return confirmedPer100K;
    }

    public double getFatalPer100K() {
        return fatalPer100K;
    }

    /**
     * A {@code String} which is a line representing this {@code DailyCaseCounts} for text table.
     *
     * @return A {@code String} representing this {@code DailyCaseCounts}
     */
    public String toTextTableEntry() {
        Map<Integer, String> dataMap = new HashMap<>() {{
            put(
                    DailyStatsTableMaker.IDX_DATE,
                    String.format("%" + DailyStatsTableMaker.LEN_DATE + "s", getDate()));
            put(
                    DailyStatsTableMaker.IDX_CONFIRMED,
                    String.format("%" + DailyStatsTableMaker.LEN_CONFIRMED + "d", getConfirmed()));
            put(
                    DailyStatsTableMaker.IDX_CONFIRMED_DIFF,
                    String.format("%+" + DailyStatsTableMaker.LEN_CONFIRMED_DIFF + "d", getConfirmedDiff()));
            put(
                    DailyStatsTableMaker.IDX_CONFIRMED_PER_100K,
                    String.format("%" + DailyStatsTableMaker.LEN_CONFIRMED_PER_100K + ".2f", getConfirmedPer100K()));
            put(
                    DailyStatsTableMaker.IDX_FATAL,
                    String.format("%" + DailyStatsTableMaker.LEN_FATAL + "d", getFatal()));
            put(
                    DailyStatsTableMaker.IDX_FATAL_DIFF,
                    String.format("%+" + DailyStatsTableMaker.LEN_FATAL_DIFF + "d", getFatalDiff()));
            put(
                    DailyStatsTableMaker.IDX_FATAL_PER_100K,
                    String.format("%" + DailyStatsTableMaker.LEN_FATAL_PER_100K + ".2f", getFatalPer100K()));
        }};

        return IntStream
                .range(0, dataMap.size())
                .mapToObj(dataMap::get)
                .collect(Collectors.joining(DailyStatsTableMaker.TBL_SPLITTER));
    }
}
