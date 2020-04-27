import java.time.LocalDate;

/**
 * Parameter to be used in {@code FilterCondition}.
 */
public enum FilterParameter implements FilterElement {
    /**
     * State name / State abbreviation
     */
    STATE("%state%"),
    /**
     * County name
     */
    COUNTY("%county%"),
    /**
     * Confirmed cases
     */
    CONFIRMED("%confirmed%"),
    /**
     * Fatal cases
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

    private final String syntax;

    /**
     * @param syntax expression syntax of an {@code Enum}.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSyntax() {
        return this.syntax;
    }

    /**
     * Cast {@code item} to {@code int}.
     *
     * @param item item to be casted
     * @param failReason failure reason to be attached if uncastable
     * @return casted {@code int}
     * @throws FilterSyntaxError thrown if uncastable
     */
    private int castInt(String item, FilterSyntaxErrorReason failReason) throws FilterSyntaxError {
        try {
            return Integer.parseInt(item);
        } catch (Exception ignored) {
            throw new FilterSyntaxError(failReason, item);
        }
    }

    /**
     * Cast {@code item} to {@code double}.
     *
     * @param item item to be casted
     * @param failReason failure reason to be attached if uncastable
     * @return casted {@code double}
     * @throws FilterSyntaxError thrown if uncastable
     */
    private double castDouble(String item, FilterSyntaxErrorReason failReason) throws FilterSyntaxError {
        try {
            return Double.parseDouble(item);
        } catch (Exception ignored) {
            throw new FilterSyntaxError(failReason, item);
        }
    }

    /**
     * Cast the item to the target type.
     *
     * @param item item to be casted
     * @return casted object
     * @throws InvalidStateNameException 
     */
    public Object cast(String item) throws FilterSyntaxError, InvalidStateNameException {
        switch (this) {
            case STATE:
                return UnitedStates.current.getState(item);

            case COUNTY:
                return UnitedStates.current.getCounty(item);

            case CONFIRMED:
            case FATAL:
                int caseNum = castInt(item, FilterSyntaxErrorReason.CASE_NUMBER_UNCASTABLE);
                if (caseNum < 0) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.CASE_NUMBER_NEGATIVE);
                }
                return caseNum;

            case ZIP_CODE:
                int zipCode = castInt(item, FilterSyntaxErrorReason.ZIP_CODE_UNCASTABLE);
                if (zipCode < 0 || zipCode > 99999) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.ZIP_CODE_INVALID, Integer.toString(zipCode));
                }
                return zipCode;

            case CONFIRMED_PER100K:
            case FATAL_PER100K:
                double per100K = castDouble(item, FilterSyntaxErrorReason.CASE_RATE_UNCASTABLE);
                if (per100K < 0) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.CASE_RATE_NEGATIVE, Double.toString(per100K));
                } else if (per100K > 100000) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.CASE_RATE_INVALID, Double.toString(per100K));
                }
                return per100K;

            case DEATH_RATE:
                double rate = castDouble(item, FilterSyntaxErrorReason.DEATH_RATE_UNCASTABLE);
                if (rate < 0) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.DEATH_RATE_NEGATIVE, Double.toString(rate));
                } else if (rate > 100) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.DEATH_RATE_INVALID, Double.toString(rate));
                }
                return rate;

            case LATITUDE:
                double lat = castDouble(item, FilterSyntaxErrorReason.LATITUDE_UNCASTABLE);
                if (lat < -90 || lat > 90) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.LATITUDE_OUT_OF_RANGE, Double.toString(lat));
                }
                return lat;

            case LONGITUDE:
                double lon = castDouble(item, FilterSyntaxErrorReason.LONGITUDE_UNCASTABLE);
                if (lon < -180 || lon > 180) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.LONGITUDE_OUT_OF_RANGE, Double.toString(lon));
                }
                return lon;

            case DATE:
                try {
                    return LocalDate.parse(item);
                } catch (Exception exception) {
                    throw new FilterSyntaxError(FilterSyntaxErrorReason.DATE_UNPARSABLE, exception.toString());
                }
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
