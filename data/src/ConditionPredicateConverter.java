import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Predicate;

public class ConditionPredicateConverter {
    /**
     * Method to be called when comparing integers.
     *
     * @param comparator comparator
     * @param onData value on the data to compare
     * @param other value of {@code FilterConditionEntity}
     * @return result of the comparison
     */
    private static boolean compare(FilterComparator comparator, int onData, int other) {
        return compare(comparator, (double)onData, other);
    }

    /**
     * Method to be called when comparing doubles.
     *
     * @param comparator comparator
     * @param onData value on the data to compare
     * @param other value of {@code FilterConditionEntity}
     * @return result of the comparison
     */
    private static boolean compare(FilterComparator comparator, double onData, double other) {
        switch (comparator) {
            case GT:
                return onData > other;
            case GTE:
                return onData >= other;
            case LT:
                return onData < other;
            case LTE:
                return onData <= other;
            case EQ:
                return onData == other;
            default:
                return false;
        }
    }

    /**
     * Method to be called when comparing strings.
     *
     * @param comparator comparator
     * @param onData value on the data to compare
     * @param other value of {@code FilterConditionEntity}
     * @return result of the comparison
     */
    private static boolean compare(FilterComparator comparator, String onData, String other) {
        switch (comparator) {
            case GT:
                return onData.compareToIgnoreCase(other) > 0;
            case GTE:
                return onData.compareToIgnoreCase(other) >= 0;
            case LT:
                return onData.compareToIgnoreCase(other) < 0;
            case LTE:
                return onData.compareToIgnoreCase(other) <= 0;
            case EQ:
                return onData.equalsIgnoreCase(other);
            default:
                return true;
        }
    }

    /**
     * Method to be called when comparing dates.
     *
     * @param comparator comparator
     * @param onData value on the data to compare
     * @param other value of {@code FilterConditionEntity}
     * @return result of the comparison
     */
    private static boolean compare(FilterComparator comparator, LocalDate onData, LocalDate other) {
        switch (comparator) {
            case GT:
                return onData.compareTo(other) > 0;
            case GTE:
                return onData.compareTo(other) >= 0;
            case LT:
                return onData.compareTo(other) < 0;
            case LTE:
                return onData.compareTo(other) <= 0;
            case EQ:
                return onData.compareTo(other) == 0;
            default:
                return true;
        }
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.STATE} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertStateEntity(FilterConditionEntity entity) {
        FilterComparator fc = entity.getComparator();

        return e -> {
            State state = e.getState();
            State val = (State)entity.getVal();

            if (state == null) {
                return false;
            }

            return compare(fc, state.getAbbr(), val.getAbbr()) || compare(fc, state.getName(), val.getName());
        };
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.COUNTY} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertCountyEntity(FilterConditionEntity entity) {
        return e -> {
            County county = e.getCounty();
            County val = (County)entity.getVal();

            if (county == null || val == null) {
                return false;
            }

            return compare(entity.getComparator(), county.getName(), val.getName());
        };
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.CONFIRMED} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertConfirmedEntity(FilterConditionEntity entity) {
        return e -> compare(entity.getComparator(), e.getConfirmed(), (int)entity.getVal());
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.FATAL} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertFatalEntity(FilterConditionEntity entity) {
        return e -> compare(entity.getComparator(), e.getFatal(), (int)entity.getVal());
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.CONFIRMED_PER100K} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertConfirmedPer100KEntity(FilterConditionEntity entity) {
        return e -> compare(entity.getComparator(), e.getConfirmedPer100K(), (double)entity.getVal());
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.FATAL_PER100K} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertFatalPer100KEntity(FilterConditionEntity entity) {
        return e -> compare(entity.getComparator(), e.getFatalPer100K(), (double)entity.getVal());
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.DEATH_RATE} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertDeathRateEntity(FilterConditionEntity entity) {
        return e -> compare(entity.getComparator(), e.getDeathRatePercent(), (double)entity.getVal());
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.LATITUDE} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertLatitudeEntity(FilterConditionEntity entity) {
        return e -> {
            County county = e.getCounty();

            if (county == null) {
                return false;
            }

            return compare(entity.getComparator(), county.getLatitude(), (double)entity.getVal());
        };
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.LONGITUDE} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertLongitudeEntity(FilterConditionEntity entity) {
        return e -> {
            County county = e.getCounty();

            if (county == null) {
                return false;
            }

            return compare(entity.getComparator(), county.getLongitude(), (double)entity.getVal());
        };
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.ZIP_CODE} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertZipCodeEntity(FilterConditionEntity entity) {
        return e -> {
            County county = e.getCounty();
            int zipCode = (int)entity.getVal();

            if (county == null) {
                return false;
            }

            return county.getZips().contains(zipCode);
        };
    }

    /**
     * Convert a {@code FilterConditionEntity} which the parameter
     * is {@code FilterParameter.DATE} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertDateEntity(FilterConditionEntity entity) {
        return e -> {
            LocalDate dateOnData = e.getDate();
            LocalDate dateToCompare;
            try {
                dateToCompare = LocalDate.parse((String)entity.getVal());
            } catch (DateTimeParseException ignored) {
                return false;
            }

            return compare(entity.getComparator(), dateOnData, dateToCompare);
        };
    }

    /**
     * Convert a {@code FilterConditionEntity} to {@code Predicate}.
     *
     * @param entity {@code FilterConditionEntity} to be converted
     * @return {@code Predicate} to be used to filter data
     */
    private static Predicate<DataEntry> convertEntity(FilterConditionEntity entity) throws FilterSyntaxError {
        FilterParameter fp = entity.getParameter();

        switch (fp) {
            case STATE:
                return convertStateEntity(entity);
            case COUNTY:
                return convertCountyEntity(entity);
            case CONFIRMED:
                return convertConfirmedEntity(entity);
            case FATAL:
                return convertFatalEntity(entity);
            case CONFIRMED_PER100K:
                return convertConfirmedPer100KEntity(entity);
            case FATAL_PER100K:
                return convertFatalPer100KEntity(entity);
            case DEATH_RATE:
                return convertDeathRateEntity(entity);
            case LATITUDE:
                return convertLatitudeEntity(entity);
            case LONGITUDE:
                return convertLongitudeEntity(entity);
            case ZIP_CODE:
                return convertZipCodeEntity(entity);
            case DATE:
                return convertDateEntity(entity);
            default:
                throw new FilterSyntaxError(FilterSyntaxErrorReason.FILTER_PARAMETER_NOT_HANDLED, fp.toString());
        }
    }

    /**
     * Convert the {@code FilterCondition} to be a {@code Predicate} which can be used to filter.
     *
     * @param condition condition to filter the data
     * @return a {@code Predicate} ready to be used to filter the data
     */
    public static Predicate<DataEntry> convert(FilterCondition condition) throws FilterSyntaxError {
        Predicate<DataEntry> predicateOR = e -> false;

        for (List<FilterConditionEntity> entitiesOR : condition.getConditions()) {
            Predicate<DataEntry> predicateAND = e -> true;

            for (FilterConditionEntity entitiesAND : entitiesOR) {
                predicateAND = predicateAND.and(convertEntity(entitiesAND));
            }

            predicateOR = predicateOR.or(predicateAND);
        }

        return predicateOR;
    }
}
