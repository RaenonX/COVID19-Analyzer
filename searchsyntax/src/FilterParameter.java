import java.time.LocalDate;

/**
 * Parameter to be used in {@code FilterCondition}.
 */
public enum FilterParameter implements FilterElement {
    /**
     * State name / State abbreviation
     */
    STATE("%state%", State.class),
    /**
     * County name
     */
    COUNTY("%city%", County.class),
    /**
     * Confirmed cases
     */
    CONFIRMED("%confirmed%", int.class),
    /**
     * Death cases
     */
    FATAL("%fatal%", int.class),
    /**
     * Confirmed cases per 100K population
     */
    CONFIRMED_PER100K("%confirmed_100k%", double.class),
    /**
     * Fatal cases per 100K population
     */
    FATAL_PER100K("%fatal_100k%", double.class),
    /**
     * Death Rate (%)
     */
    DEATH_RATE("%death_rate%", double.class),
    /**
     * Latitude
     */
    LATITUDE("%lat%", double.class),
    /**
     * Longitude
     */
    LONGITUDE("%lon%", double.class),
    /**
     * ZIP code of the city
     */
    ZIP_CODE("%zip%", LocalDate.class),
    /**
     * Date of the data entry
     */
    DATE("%date%", LocalDate.class);

    private final String syntax;
    private final Class<?> type;

    FilterParameter(String syntax, Class<?> type) {
        this.syntax = syntax;
        this.type = type;
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

    /**
     * Cast the item to the target type.
     *
     * @param item item to be casted
     * @return casted object
     */
    public Object cast(String item) {
        return this.type.cast(item);
    }
}
