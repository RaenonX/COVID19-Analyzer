/**
 * Comparator to be used in {@code FilterCondition}.
 */
public enum FilterComparator implements FilterElement {
    /**
     * Greater than (>)
     */
    GT(">"),
    /**
     * Greater than or equals (>=)
     */
    GTE(">="),
    /**
     * Less than (<)
     */
    LT("<"),
    /**
     * Less than or equals (<=)
     */
    LTE("<="),
    /**
     * Equals (=)
     */
    EQ("=");

    private String syntax;

    FilterComparator(String syntax) {
        this.syntax = syntax;
    }

    /**
     * Parse the given word to the corresponding {@code FilterComparator}.
     *
     * This parsing is case-insensitive.
     *
     * @param word word to be parsed
     * @return corresponding {@code FilterParameter} of the {@code FilterComparator}.
     * {@code null} if not found.
     */
    public static FilterComparator parse(String word) {
        for (FilterComparator fc : FilterComparator.values()) {
            if (fc.getSyntax().equalsIgnoreCase(word)) {
                return fc;
            }
        }
        return null;
    }

    @Override
    public String getSyntax() {
        return this.syntax;
    }
}
