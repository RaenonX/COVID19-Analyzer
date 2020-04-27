import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Util class to make the text table of {@code DailyCaseStats}.
 */
public class DailyStatsTableMaker {
    // region Index of each Data Entry
    public static final int IDX_DATE = 0;
    public static final int IDX_CONFIRMED = 1;
    public static final int IDX_CONFIRMED_DIFF = 2;
    public static final int IDX_CONFIRMED_PER_100K = 3;
    public static final int IDX_FATAL = 4;
    public static final int IDX_FATAL_DIFF = 5;
    public static final int IDX_FATAL_PER_100K = 6;
    // endregion

    // region Length including padding of each Data Entry
    public static final int LEN_DATE = 10;
    public static final int LEN_CONFIRMED = 9; // max(len("Confirmed"), <confirmed case count>)
    public static final int LEN_CONFIRMED_DIFF = 9;
    public static final int LEN_CONFIRMED_PER_100K = 9;
    public static final int LEN_FATAL = 9; // conform to `LEN_CONFIRMED`
    public static final int LEN_FATAL_DIFF = 9;
    public static final int LEN_FATAL_PER_100K = 9;
    // endregion

    public static final String TBL_SPLITTER = " | ";

    /**
     * A {@code String} represents the header of the table.
     *
     * @return table header {@code String}
     */
    private static String tableHeader() {
        Map<Integer, String> headerMap = new HashMap<>() {{
            put(
                    DailyStatsTableMaker.IDX_DATE,
                    String.format("%" + DailyStatsTableMaker.LEN_DATE + "s", "Date"));
            put(
                    DailyStatsTableMaker.IDX_CONFIRMED,
                    String.format("%" + DailyStatsTableMaker.LEN_CONFIRMED + "s", "Confirmed"));
            put(
                    DailyStatsTableMaker.IDX_CONFIRMED_DIFF,
                    String.format("%" + DailyStatsTableMaker.LEN_CONFIRMED_DIFF + "s", "+/-"));
            put(
                    DailyStatsTableMaker.IDX_CONFIRMED_PER_100K,
                    String.format("%" + DailyStatsTableMaker.LEN_CONFIRMED_PER_100K + "s", "Per 100K"));
            put(
                    DailyStatsTableMaker.IDX_FATAL,
                    String.format("%" + DailyStatsTableMaker.LEN_FATAL + "s", "Fatal"));
            put(
                    DailyStatsTableMaker.IDX_FATAL_DIFF,
                    String.format("%" + DailyStatsTableMaker.LEN_FATAL_DIFF + "s", "+/-"));
            put(
                    DailyStatsTableMaker.IDX_FATAL_PER_100K,
                    String.format("%" + DailyStatsTableMaker.LEN_FATAL_PER_100K + "s", "Per 100K"));
        }};

        return IntStream
                .range(0, headerMap.size())
                .mapToObj(headerMap::get)
                .collect(Collectors.joining(DailyStatsTableMaker.TBL_SPLITTER));
    }

    /**
     * Construct a table with header using the given stats.
     *
     * @return {@code StringBuilder} containing the constructed text table
     */
    public static StringBuilder tableString(DailyCaseStats stats) {
        StringBuilder sb = new StringBuilder();

        sb.append(DailyStatsTableMaker.tableHeader()).append("\n");
        sb.append(
                stats.getTableDataEntry()
                        .stream()
                        .sorted(DailyCaseCounts.cmpDate)
                        .map(DailyCaseCounts::toTextTableEntry)
                        .collect(Collectors.joining("\n"))
        );

        return sb;
    }
}
