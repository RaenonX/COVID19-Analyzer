import java.time.LocalDate;

public class DataEntryParser {
    // region Index of each Data Entry
    private static final int IDX_DATE = 0;
    private static final int IDX_COUNTY = 1;
    private static final int IDX_STATE = 2;
    private static final int IDX_CONFIRMED = 3;
    private static final int IDX_FATAL = 4;
    // endregion

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
