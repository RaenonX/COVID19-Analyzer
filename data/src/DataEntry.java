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

  /**
   * 
   * @param date - 
   * @param state
   * @param county
   * @param confirmed - confirmed case count
   * @param fatal
   * @throws InvalidStateException
   * @throws InvalidDateException 
   * @throws InvalidCountyException
   * @throws InvalidConfirmedCaseCountException 
   */
  public DataEntry(LocalDate date, State state, County county, int confirmed, int fatal)
      throws Exception {

    // date validation check
    if (date == null || date.compareTo(LocalDate.now()) > 0)
      throw new InvalidDateException();
    
    // state validation check
    // 如果有 invalid state name 要throw InvalidStateException吧? -- andy
    if (state == null)
      throw new InvalidStateException();
    
    // confirmed validation check -- 1
    if (confirmed < 0)
      throw new InvalidConfirmedCaseCountException();
    
    // if county != null
    if (county != null) {
      int countyPop = county.getPopulation();
      // confirmed validation check -- 2
      if (confirmed > countyPop)
        throw new InvalidConfirmedCaseCountException();
      
      if (countyPop == 0) {
        this.confirmedPer100K = 0;
        this.fatalPer100K = 0;
      } else {
        this.confirmedPer100K = confirmed / (double)countyPop * 100000;
        this.fatalPer100K = fatal / (double)countyPop * 100000;
      }
    } 
    
    // if count == null
    else {
      this.confirmedPer100K = -1.0;
      this.fatalPer100K = -1.0;
    }
    
    // fatal validation check
    if (fatal > confirmed || fatal < 0)
      throw new InvalidFatalCaseException();
    
    // initialization of private fields
    this.date = date;
    this.state = state;
    this.county = county;
    this.confirmed = confirmed;
    this.fatal = fatal;
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
