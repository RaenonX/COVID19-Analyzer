import java.time.LocalDate;

public class DataEntryParser {
    private static final int IDX_DATE = 0;
    private static final int IDX_COUNTY = 1;
    private static final int IDX_STATE = 2;
    private static final int IDX_CONFIRMED = 3 ;
    private static final int IDX_FATAL = 4;

    public static DataEntry parse(String[] lineEntry) {
        LocalDate date = LocalDate.parse(lineEntry[IDX_DATE]);
        County county = UnitedStates.current.getCounty(lineEntry[IDX_COUNTY], lineEntry[IDX_STATE]);
        State state = UnitedStates.current.getState(lineEntry[IDX_STATE]);
        int confirmed = Integer.parseInt(lineEntry[IDX_CONFIRMED]);
        int fatal = Integer.parseInt(lineEntry[IDX_FATAL]);
        return new DataEntry(date, state, county, confirmed, fatal);
    }
}
