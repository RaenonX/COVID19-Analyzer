import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataEntryFileProcessor {
    // region Index of each Data Entry
    public static final int IDX_DATE = 0;
    public static final int IDX_COUNTY = 1;
    public static final int IDX_STATE = 2;
    public static final int IDX_CONFIRMED = 3;
    public static final int IDX_FATAL = 4;
    public static final int IDX_CONFIRMED_PER_100K = 5;
    public static final int IDX_FATAL_PER_100K = 6;
    // endregion

    // region Length including padding of each Data Entry
    public static final int LEN_DATE = 10;
    public static final int LEN_COUNTY = 20;
    public static final int LEN_STATE = 5; // max(len("State"), <actual state name length>)
    public static final int LEN_CONFIRMED = 9; // max(len("Confirmed"), <actual state name length>)
    public static final int LEN_FATAL = 6;
    public static final int LEN_CONFIRMED_PER_100K = 9;
    public static final int LEN_FATAL_PER_100K = 9;
    // endregion

    public static final String TBL_SPLITTER = " | ";

    /**
     * A {@code String} represents the header of the table.
     *
     * @return table header {@code String}
     */
    public static String tableHeader() {
        Map<Integer, String> headerMap = new HashMap<>() {{
            put(
                    DataEntryFileProcessor.IDX_DATE,
                    String.format("%" + DataEntryFileProcessor.LEN_DATE + "s", "Date"));
            put(
                    DataEntryFileProcessor.IDX_COUNTY,
                    String.format("%" + DataEntryFileProcessor.LEN_COUNTY + "s", "County"));
            put(
                    DataEntryFileProcessor.IDX_STATE,
                    String.format("%" + DataEntryFileProcessor.LEN_STATE + "s", "State"));
            put(
                    DataEntryFileProcessor.IDX_CONFIRMED,
                    String.format("%" + DataEntryFileProcessor.LEN_CONFIRMED + "s", "Confirmed"));
            put(
                    DataEntryFileProcessor.IDX_FATAL,
                    String.format("%" + DataEntryFileProcessor.LEN_FATAL + "s", "Fatal"));
            put(
                    DataEntryFileProcessor.IDX_CONFIRMED_PER_100K,
                    String.format("%" + DataEntryFileProcessor.LEN_CONFIRMED_PER_100K + "s", "CC / 100K"));
            put(
                    DataEntryFileProcessor.IDX_FATAL_PER_100K,
                    String.format("%" + DataEntryFileProcessor.LEN_FATAL_PER_100K + "s", "FC / 100K"));
        }};

        return IntStream
                .range(0, headerMap.size())
                .mapToObj(headerMap::get)
                .collect(Collectors.joining(DataEntryFileProcessor.TBL_SPLITTER));
    }

    /**
     * Parse the splitted data entry to {@code DataEntry}.
     *
     * @param lineEntry parsed splitted data entry
     * @return parsed data entry
     */
    public static DataEntry parse(String[] lineEntry) {
        LocalDate date = LocalDate.parse(lineEntry[IDX_DATE]);
        County county = UnitedStates.current.getCounty(lineEntry[IDX_COUNTY], lineEntry[IDX_STATE]);
        State state = UnitedStates.current.getState(lineEntry[IDX_STATE]);
        int confirmed = Integer.parseInt(StringUtils.defaultEmptyString(lineEntry[IDX_CONFIRMED], "0"));
        int fatal = Integer.parseInt(StringUtils.defaultEmptyString(lineEntry[IDX_FATAL], "0"));
        return new DataEntry(date, state, county, confirmed, fatal);
    }
}
