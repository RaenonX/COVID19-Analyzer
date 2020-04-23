import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStringUtils {
    @Test
    void test_default_string() {
        assertEquals("X", StringUtils.defaultEmptyString("X", "Y"));
        assertEquals("Y", StringUtils.defaultEmptyString("", "Y"));
        assertEquals(" ", StringUtils.defaultEmptyString(" ", "Y"));
    }

    @Test
    void test_is_alphabet() {
        assertTrue(StringUtils.isAlphabets("X"));
        assertTrue(StringUtils.isAlphabets("EEGSdf"));
        assertFalse(StringUtils.isAlphabets("-"));
        assertFalse(StringUtils.isAlphabets(" "));
        assertFalse(StringUtils.isAlphabets(""));
        assertFalse(StringUtils.isAlphabets("R#%"));
    }
}
