public enum FilterSyntaxErrorReason {
    /**
     * Parentheses not allowed in the filter query.
     */
    PARENTHESES_NOT_ALLOWED,

    /**
     * Filter query expression is incomplete.
     */
    INCOMPLETE_EXPRESSION,

    /**
     * Failed to cast the value of a {@code FilterConditionEntity}.
     */
    PARAMETER_VALUE_UNCASTABLE,

    /**
     * Case count number uncastable.
     */
    CASE_NUMBER_UNCASTABLE,
    /**
     * Case count number is negative.
     */
    CASE_NUMBER_NEGATIVE,

    /**
     * Case rate number uncastable.
     */
    CASE_RATE_UNCASTABLE,
    /**
     * Case rate number is negative.
     */
    CASE_RATE_NEGATIVE,
    /**
     * Case rate number is invalid (possibly out of range).
     */
    CASE_RATE_INVALID,

    /**
     * Death rate number uncastable.
     */
    DEATH_RATE_UNCASTABLE,
    /**
     * Death rate number is negative.
     */
    DEATH_RATE_NEGATIVE,
    /**
     * Death rate number is invalid (possibly out of range).
     */
    DEATH_RATE_INVALID,

    /**
     * ZIP code is invalid (possibly out of range).
     */
    ZIP_CODE_INVALID,
    /**
     * ZIP code is uncastable.
     */
    ZIP_CODE_UNCASTABLE,

    /**
     * Latitude number out of range (valid -90~90).
     */
    LATITUDE_OUT_OF_RANGE,
    /**
     * Latitude number uncastable.
     */
    LATITUDE_UNCASTABLE,

    /**
     * Longitude number out of range (valid -180~180).
     */
    LONGITUDE_OUT_OF_RANGE,
    /**
     * Longitude number uncastable.
     */
    LONGITUDE_UNCASTABLE,

    /**
     * Date string unparsable.
     */
    DATE_UNPARSABLE
}
