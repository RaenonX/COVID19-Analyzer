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
        return (str != null) && (!str.equals("")) && (str.matches("^[a-zA-Z '.-]*$"));
    }

    private static final String[] numUnit = new String[] {"", "K", "M", "G", "T"};

    /**
     * Simplify a number to be at most 3 digit (if possible) and 1 decimal place with the unit (if applicable).<br>
     * <br>
     * Available units are:
     * <ul>
     *     <li>K (1000)</li>
     *     <li>M (1000000)</li>
     *     <li>G (1000000000)</li>
     *     <li>T (1000000000000)</li>
     * </ul>
     * Examples:
     * <ul>
     *     <li>{@code +367} to {@code +367}</li>
     *     <li>{@code 5007} to {@code 5.0K}</li>
     *     <li>{@code 105467} to {@code 105.5K}</li>
     *     <li>{@code 105547878} to {@code 105.5M}</li>
     * </ul>
     *
     * @param num number to be formatted
     * @param plusSign attach plus sign if {@code num} is positive
     * @param attachActual attach actual number
     * @param comma comma formatted for actual number
     * @return simplified number string
     */
    public static String simplifyNumber(Number num, boolean plusSign, boolean attachActual, boolean comma) {
        double val = num.doubleValue();
        int divCount = 0;

        while (val > 1000 && divCount < numUnit.length) {
            val /= 1000;
            divCount++;
        }

        String commaFmt = comma ? "," : "";
        String str = String.format("%" + (plusSign ? "+" : "") + commaFmt + ".1f" + numUnit[divCount], val);

        if (attachActual) {
            str += String.format(" (%"+ commaFmt + "d)", num.intValue());
        }

        return str;
    }

    /**
     * Simplify a number to be at most 3 digit (if possible) and 1 decimal place with the unit (if applicable).<br>
     * Call {@code simplifyNumber()} with all the parameters set to {@code true} except {@code plusSign}.<br>
     * <br>
     * Available units are:
     * <ul>
     *     <li>K (1000)</li>
     *     <li>M (1000000)</li>
     *     <li>G (1000000000)</li>
     *     <li>T (1000000000000)</li>
     * </ul>
     * Examples:
     * <ul>
     *     <li>{@code +367} to {@code +367}</li>
     *     <li>{@code 5007} to {@code 5.0K}</li>
     *     <li>{@code 105467} to {@code 105.5K}</li>
     *     <li>{@code 105547878} to {@code 105.5M}</li>
     * </ul>
     *
     * @param num number to be formatted
     * @return simplified number string
     */
    public static String simplifyNumber(Number num) {
        return simplifyNumber(num, false, true, true);
    }
}
