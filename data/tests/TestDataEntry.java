import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;


/**
 * JUnit test class for DataEntry Object
 *
 */
public class TestDataEntry {
  private static County county;
  private static State state;

  @SuppressWarnings("serial")
  @BeforeAll
  static void prepare() {

    // String name, double latitude, double longitude, int population, List<Integer> zipcodes
    county = new County("Dane", 47.3, -120.1, 10000, new ArrayList<>() {
      {
        add(53714);
        add(53715);
        add(53716);
      }
    });

    // String abbr, String name, List<County> counties
    state = new State("WI", "Wisconsin", new ArrayList<>() {
      {
        add(county);
      }
    });
  }

  /**
   * 1. constructor initialization of private fields 
   * 2. accessors 
   *    a. getDate() 
   *    b. getState() 
   *    c. getCounty() 
   *    d. getConfirmed() 
   *    e. getFatal() 
   *    f. getConfirmedPer100K() 
   *    g. getFatalPer100K()
   */
  @Test
  void test_DataEntry_001_accessors() {
    try {
      // with county != null
      DataEntry data1 = new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, county, 100, 10);
      assertEquals(LocalDate.of(2020, Month.APRIL, 20), data1.getDate());
      assertEquals(state, data1.getState());
      assertEquals(county, data1.getCounty());
      assertEquals(100, data1.getConfirmed());
      assertEquals(10, data1.getFatal());
      assertEquals(1000, data1.getConfirmedPer100K());
      assertEquals(100, data1.getFatalPer100K());
      
      // with county == null
      DataEntry data2 = new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, null, 100, 10);
      assertEquals(-1.0, data2.getConfirmedPer100K());
      assertEquals(-1.0, data2.getFatalPer100K());
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntry_001_accessors: %s", e));
      e.printStackTrace();
    }
  }

  /**
   * no exception is thrown when passing a null value to county parameter because some data
   * does not record county
   */
  @Test
  void test_DataEntry_002_null_county() {
    try {
      new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, null, 100, 10);
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntry_002_null_county: %s", e));
      e.printStackTrace();
    }

  }

  /**
   * InvalidStateException is thrown when passing an null value to state parameter
   */
  @Test
  void test_DataEntry_003_null_state() {
    try {
      new DataEntry(LocalDate.of(2020, Month.APRIL, 20), null, county, 100, 10);
      fail();
    } 
    catch (InvalidStateException e) {
      assertThrows(InvalidStateException.class,
          () -> new DataEntry(LocalDate.of(2020, Month.APRIL, 20), null, county, 100, 10));
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntry_003_null_state: %s", e));
      e.printStackTrace();
    }
  }

  /**
   * InvaliDateException is thrown when passing a null value to LocalData parameter
   */
  @Test
  void test_DataEntry_004_null_date() {
    try {
      // null date
      new DataEntry(null, state, county, 100, 10);
      fail();
    } 
    catch (InvalidDateException e) {
      assertThrows(InvalidDateException.class, () -> new DataEntry(null, state, county, 100, 10));
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test004_null_date: %s", e));
      e.printStackTrace();
    }
  }

  /**
   * InvaliDateException is thrown when passing an invalid value to LocalData parameter
   */
  @Test
  void test_DataEntry_005_invalid_date() {
    try {
      // invalid date
      new DataEntry(LocalDate.of(2023, Month.APRIL, 20), state, county, 100, 10);
      fail();
    } 
    catch (InvalidDateException e) {
      assertThrows(InvalidDateException.class,
          () -> new DataEntry(LocalDate.of(2023, Month.APRIL, 20), state, county, 100, 10));
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test005_invalid_date: %s", e));
      e.printStackTrace();
    }
    // date in the future
    assertThrows(InvalidDateException.class,
        () -> new DataEntry(LocalDate.of(2023, Month.APRIL, 20), state, county, 100, 10));
  }

  /**
   * InvalidConfirmedCaseCountException is thrown when a negative integer values is passed to
   * confirmed
   */
  @Test
  void test_DataEntry_006_negative_confirmed() {
    try {
      // negative case count
      new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, null, -1, 10);
      fail();
    } 
    catch (InvalidConfirmedCaseCountException e) {
      assertThrows(InvalidConfirmedCaseCountException.class,
          () -> new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, null, -1, 10));
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test005_invalid_date: %s", e));
      e.printStackTrace();
    }
  }
  
  /**
   * InvalidConfirmedCaseCountException is thrown when the value passing to confirmed is
   * greater than the total population in that county
   */
  @Test
  void test_DataEntry_007_invalid_confirmed() {
    try {
      // case count > population
      new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, county, 10001, 10);
      fail();
    } 
    catch (InvalidConfirmedCaseCountException e) {
      assertThrows(InvalidConfirmedCaseCountException.class,
          () -> new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, county, 10001, 10));
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test005_invalid_date: %s", e));
      e.printStackTrace();
    }
  }

  /**
   * InvalidFatalCaseException is thrown when a negative integer value is passed to fatal
   */
  @Test
  void test_DataEntry_008_negative_fatal_count() {
    try {
      // negative fatal case count
      new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, null, 100, -1);
      fail();
    } 
    catch (InvalidFatalCaseException e) {
      assertThrows(InvalidFatalCaseException.class,
          () -> new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, null, 100, -1));
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test005_invalid_date: %s", e));
      e.printStackTrace();
    }
  }
  
  /**
   * InvalidFatalCaseException is thrown when the value passed to fatal is greater than confirmed 
   * case counts 
   */
  @Test
  void test_DataEntry_009_invalid_fatal_count() {
    
    try {
      // case count > population
      new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, county, 100, 10001);
      fail();
    } 
    catch (InvalidFatalCaseException e) {
      assertThrows(InvalidFatalCaseException.class,
          () -> new DataEntry(LocalDate.of(2020, Month.APRIL, 20), state, county, 100, 10001));
    } 
    catch (Exception e) {
      fail(String.format("Unexpected exception in test005_invalid_date: %s", e));
      e.printStackTrace();
    }
  }
}

