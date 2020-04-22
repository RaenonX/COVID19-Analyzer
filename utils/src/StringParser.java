public class StringParser {
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
}
