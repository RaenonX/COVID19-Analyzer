import java.time.LocalDate;

public class DataEntryFileProcessor {
  // region Index of each Data Entry
  private static final int IDX_DATE = 0;
  private static final int IDX_COUNTY = 1;
  private static final int IDX_STATE = 2;
  private static final int IDX_CONFIRMED = 3;
  private static final int IDX_FATAL = 4;
  // end region

  /**
   * Parse the separated data entry to {@code DataEntry}.
   *
   * @param lineEntry - each is parsed to data entry
   * @return parsed data entry
   * @throws Exception 
   */
  public static DataEntry parse(String[] lineEntry) throws Exception {
    
    LocalDate date;
    int confirmed; 
    int fatal;
    
    County county = UnitedStates.current.getCounty(lineEntry[IDX_COUNTY], lineEntry[IDX_STATE]);
    State state = UnitedStates.current.getState(lineEntry[IDX_STATE]);

    try {
      // 如果要想 process different input formats of localDate params, 
      // https://www.geeksforgeeks.org/localdate-parse-method-in-java-with-examples/
      date = LocalDate.parse(lineEntry[IDX_DATE]);
      
    // validation check for parsing confirmed to int
      confirmed = Integer.parseInt(StringUtils.defaultEmptyString(lineEntry[IDX_CONFIRMED], "0"));
    }
    catch (java.time.format.DateTimeParseException e) {
      throw new InvalidDateException();
    }
    catch (NumberFormatException e) {
      throw new InvalidConfirmedCaseCountException();
    }
    
    // validation check for parsing fatal to int
    try {
      fatal = Integer.parseInt(StringUtils.defaultEmptyString(lineEntry[IDX_FATAL], "0"));
    }
    catch (NumberFormatException e) {
      throw new InvalidFatalCaseException();
    }
    
    return new DataEntry(date, state, county, confirmed, fatal);
  }
}
