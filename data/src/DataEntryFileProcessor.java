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
    public static final int IDX_DEATH_RATE = 7;
    // endregion

    // region Length including padding of each Data Entry
    public static final int LEN_DATE = 10;
    public static final int LEN_COUNTY = 20;
    public static final int LEN_STATE = 20; // max(len("State"), <state name>)
    public static final int LEN_CONFIRMED = 9; // max(len("Confirmed"), <confirmed case count>)
    public static final int LEN_FATAL = 6;
    public static final int LEN_CONFIRMED_PER_100K = 9;
    public static final int LEN_FATAL_PER_100K = 9;
    public static final int LEN_DEATH_RATE = 7; // max(len("Death %"), <death rate % with 2 floating points>)
    // endregion

    public static final String TBL_SPLITTER = " | ";

    /**
     * A {@code String} represents the header of the table.
     *
     * @return table header {@code String}
     */
    public static String tableHeader() {
        Map<Integer, String> headerMap = new HashMap<>() {
            {
                put(DataEntryFileProcessor.IDX_DATE,
                        String.format("%" + DataEntryFileProcessor.LEN_DATE + "s", "Date"));
                put(DataEntryFileProcessor.IDX_COUNTY,
                        String.format("%" + DataEntryFileProcessor.LEN_COUNTY + "s", "County"));
                put(DataEntryFileProcessor.IDX_STATE,
                        String.format("%" + DataEntryFileProcessor.LEN_STATE + "s", "State"));
                put(DataEntryFileProcessor.IDX_CONFIRMED,
                        String.format("%" + DataEntryFileProcessor.LEN_CONFIRMED + "s", "Confirmed"));
                put(DataEntryFileProcessor.IDX_FATAL,
                        String.format("%" + DataEntryFileProcessor.LEN_FATAL + "s", "Fatal"));
                put(DataEntryFileProcessor.IDX_CONFIRMED_PER_100K,
                        String.format("%" + DataEntryFileProcessor.LEN_CONFIRMED_PER_100K + "s", "CC / 100K"));
                put(DataEntryFileProcessor.IDX_FATAL_PER_100K,
                        String.format("%" + DataEntryFileProcessor.LEN_FATAL_PER_100K + "s", "FC / 100K"));
                put(DataEntryFileProcessor.IDX_DEATH_RATE,
                        String.format("%" + DataEntryFileProcessor.LEN_DEATH_RATE + "s", "Death %"));
            }
        };

        return IntStream.range(0, headerMap.size()).mapToObj(headerMap::get)
                .collect(Collectors.joining(DataEntryFileProcessor.TBL_SPLITTER));
    }

    /**
     * Parse the splitted data entry to {@code DataEntry}.
     *
     * @param lineEntry parsed splitted data entry
     * @return parsed data entry
     */
    public static DataEntry parse(String[] lineEntry) throws Exception {

        LocalDate date;
        int confirmed;
        int fatal;

        County county = UnitedStates.current.getCounty(lineEntry[IDX_COUNTY], lineEntry[IDX_STATE]);
        State state = UnitedStates.current.getState(lineEntry[IDX_STATE]);

        try {
            // To process different input formats of localDate params,
            // https://www.geeksforgeeks.org/localdate-parse-method-in-java-with-examples/
            date = LocalDate.parse(lineEntry[IDX_DATE]);

            // validation check for parsing confirmed to int
            confirmed = Integer.parseInt(StringUtils.defaultEmptyString(lineEntry[IDX_CONFIRMED], "0"));
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidDateException();
        } catch (NumberFormatException e) {
            throw new InvalidConfirmedCaseCountException();
        }

        // validation check for parsing fatal to int
        try {
            fatal = Integer.parseInt(StringUtils.defaultEmptyString(lineEntry[IDX_FATAL], "0"));
        } catch (NumberFormatException e) {
            throw new InvalidFatalCaseException();
        }

        return new DataEntry(date, state, county, confirmed, fatal);
    }
}
