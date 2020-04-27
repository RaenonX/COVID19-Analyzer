import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for DataEntryFileProcessor object
 *
 */
public class TestDataEntryFileProcessor {
  @BeforeAll
  static void prepare() throws IOException {
    PopulationDataParser.loadUsPopFile("res/pops.csv", new StateNameConverter("res/states.csv"));
  }

  /**
   * should not throw any exception when input entry is valid
   */
  @Test
  void test_DataEntryFileProcessor_001_process_normal() {
    String[] entry = new String[] {"2020-01-28", "Dane", "WI", "1", "0"};
    try {
      DataEntry e = DataEntryFileProcessor.parse(entry);
      assertEquals(LocalDate.of(2020, Month.JANUARY, 28), e.getDate());
      
      County c = e.getCounty();
      assertEquals("Dane", c.getName());

      State s = e.getState();
      assertEquals("WI", s.getAbbr());

      assertEquals(1, e.getConfirmed());
      assertEquals(0, e.getFatal());
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_001_process_normal: %s", e));
      e.printStackTrace();
    }
  }

  /**
   * processing empty or null entry should work because county arg is optional
   */
  @Test
  void test_DataEntryFileProcessor_002_process_empty_county() {
    String[] entry1 = new String[] {"2020-01-28", "", "WI", "1", "0"};
    String[] entry2 = new String[] {"2020-01-28", null, "WI", "1", "0"};
    try {
      /* empty county arg */
      DataEntry e = DataEntryFileProcessor.parse(entry1);
      assertEquals(LocalDate.of(2020, Month.JANUARY, 28), e.getDate());
  
      assertNull(e.getCounty());
  
      State s = e.getState();
      assertEquals("WI", s.getAbbr());
  
      assertEquals(1, e.getConfirmed());
      assertEquals(0, e.getFatal());
      
      /* null county arg */
      e = DataEntryFileProcessor.parse(entry2);
      assertEquals(LocalDate.of(2020, Month.JANUARY, 28), e.getDate());
  
      assertNull(e.getCounty());
  
      s = e.getState();
      assertEquals("WI", s.getAbbr());
  
      assertEquals(1, e.getConfirmed());
      assertEquals(0, e.getFatal());
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_002_process_empty_county: %s", e));
      e.printStackTrace();
    }
  }

  /**
   * InvalidStateException is thrown when the value of the received state argument is null
   */
  @Test
  void test_DataEntryFileProcessor_003_process_invalid_null_state() {
    try {
      DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", null, "1", "0"});
      fail();
    }
    catch (InvalidStateException e) {
      assertThrows(InvalidStateException.class,
          () -> DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", null, "1", "0"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_003_process_invalid_null_state: %s", e));
      e.printStackTrace();
    }
  }
  
  /**
   * InvalidStateException is thrown when the value of the received state argument is an empty string
   */
  @Test
  void test_DataEntryFileProcessor_004_process_invalid_empty_state() {
    // test for ""
    try {
      DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "", "1", "0"});
    }
    catch (InvalidStateException e) {
      assertThrows(InvalidStateException.class,
          () -> DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "", "1", "0"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_004_process_invalid_empty_state"
          + "part 1: %s", e));
      e.printStackTrace();
    }
    
    // test for " "
    try {
      DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", " ", "1", "0"});
    }
    catch (InvalidStateException e) {
      assertThrows(InvalidStateException.class,
          () -> DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", " ", "1", "0"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_004_process_invalid_empty_state"
          + "part 2: %s", e));
      e.printStackTrace();
    }
  }

  /**
   * InvalidConfirmedCaseCountException is thrown when the value of the received confirmed argument
   * is a negative number
   */
  @Test
  void test_DataEntryFileProcessor_005_process_invalid_confirmed() {
    try {
      DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "-1", "0"});
    } 
    catch (InvalidConfirmedCaseCountException e) {
      assertThrows(InvalidConfirmedCaseCountException.class,
          () -> DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "-1", "0"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_005_1_process_invalid_confirmed"
          + ": %s", e));
      e.printStackTrace();
    }
  }
  
  /**
   * InvalidConfirmedCaseCountException is thrown when the value of the received confirmed argument
   * is a alphabetic character
   */
  @Test
  void test_DataEntryFileProcessor_006_process_invalid_confirmed() {
    try {
      DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "A", "0"});
    } 
    catch (InvalidConfirmedCaseCountException e) {
      assertThrows(InvalidConfirmedCaseCountException.class,
          () -> DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "A", "0"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_005_2_process_invalid_confirmed"
          + ": %s", e));
      e.printStackTrace();
    }
    
  }

  /**
   * InvalidFatalCaseException is thrown when the value of the received confirmed argument
   * is a negative number
   */
  @Test
  void test_DataEntryFileProcessor_007_process_invalid_fatal() {
    try {
      DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "1", "-1"});
    } 
    catch (InvalidFatalCaseException e) {
      assertThrows(InvalidFatalCaseException.class,
          () -> DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "1", "-1"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_006_1_process_invalid_fatal"
          + ": %s", e));
      e.printStackTrace();
    }
  }
  
  /**
   * InvalidFatalCaseException is thrown when the value of the received confirmed argument
   * is a alphabetic character
   */
  @Test
  void test_DataEntryFileProcessor_008_process_invalid_fatal() {
    try {
      DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "1", "A"});
    } 
    catch (InvalidFatalCaseException e) {
    assertThrows(InvalidFatalCaseException.class,
        () -> DataEntryFileProcessor.parse(new String[] {"2020-01-28", "Dane", "WI", "1", "A"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_006_2_process_invalid_fatal"
          + ": %s", e));
      e.printStackTrace();
    }
  }

  /**
   * InvalidDateException is thrown when the value of the received date argument is of invalid
   * format.
   */
  @Test
  void test_DataEntryFileProcessor_009_process_invalid_date() {
    
    try {
      DataEntryFileProcessor.parse(new String[] {"A", "Dane", "WI", "1", "1"});
    } 
    catch (InvalidDateException e) {
    assertThrows(InvalidDateException.class,
        () -> DataEntryFileProcessor.parse(new String[] {"A", "Dane", "WI", "1", "1"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_007_1_process_invalid_date"
          + ": %s", e));
      e.printStackTrace();
    }
  }
  
  /**
   * InvalidDateException is thrown when the value of the received date argument is of invalid year
   */
  @Test
  void test_DataEntryFileProcessor_010_process_invalid_date() {
    
    try {
      DataEntryFileProcessor.parse(new String[] {"2023-01-28", "Dane", "WI", "1", "1"});
    } 
    catch (InvalidDateException e) {
    assertThrows(InvalidDateException.class,
        () -> DataEntryFileProcessor.parse(new String[] {"2023-01-28", "Dane", "WI", "1", "1"}));
    }
    catch (Exception e) {
      fail(String.format("Unexpected exception in test_DataEntryFileProcessor_007_2_process_invalid_date"
          + ": %s", e));
      e.printStackTrace();
    }
  }
}
