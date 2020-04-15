/**
 * Parameter to be used in {@code FilterCondition}.
 */
public enum FilterParameter implements FilterElement {
    /**
     * State name / State abbreviation
     */
    STATE("%state%"),
    /**
     * City name
     */
    CITY("%city%"),
    /**
     * Confirmed cases
     */
    CONFIRMED("%confirmed%"),
    /**
     * Death cases
     */
    FATAL("%fatal%"),
    /**
     * Confirmed cases per 100K population
     */
    CONFIRMED_PER100K("%confirmed_100k%"),
    /**
     * Fatal cases per 100K population
     */
    FATAL_PER100K("%fatal_100k%"),
    /**
     * Death Rate (%)
     */
    DEATH_RATE("%death_rate%"),
    /**
     * Latitude
     */
    LATITUDE("%lat%"),
    /**
     * Longitude
     */
    LONGITUDE("%lon%"),
    /**
     * ZIP code of the city
     */
    ZIP_CODE("%zip%"),
    /**
     * Date of the data entry
     */
    DATE("%date%");

    private String syntax;

    FilterParameter(String syntax) {
        this.syntax = syntax;
    }

    /**
     * Parse the given word to the corresponding {@code FilterParameter}.
     *
     * This parsing is case-insensitive.
     *
     * @param word word to be parsed
     * @return corresponding {@code FilterParameter} of the {@code FilterParameter}.
     * {@code null} if not found.
     */
    public static FilterParameter parse(String word) {
        for (FilterParameter fp : FilterParameter.values()) {
            if (fp.getSyntax().equalsIgnoreCase(word)) {
                return fp;
            }
        }
        return null;
    }

    @Override
    public String getSyntax() {
        return this.syntax;
    }
}
