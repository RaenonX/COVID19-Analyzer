public class StringUtils {
    /**
     * Return {@code str} if not empty. Otherwise, return {@code defaultStr}.
     *
     * @param str string to be returned if not empty
     * @param defaultStr string to be returned if {@code str} is empty
     * @return {@code str} if not empty, else {@code defaultStr}
     */
    public static String defaultEmptyString(String str, String defaultStr) {
        if (str.isEmpty()) {
            return defaultStr;
        } else {
            return str;
        }
    }

    /**
     * Test if the given {@code str} is alphabets only.
     *
     * Return {@code false} on empty string.
     *
     * @param str str to be checked
     * @return if {@code str} only contains alphabets
     */
    public static boolean isAlphabets(String str) {
        // TODO: Could be used for checking both state and county names
        return false;
    }
}
