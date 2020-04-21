import java.util.ArrayList;
import java.util.List;

/**
 * A class holding all `FilterConditionEntity`.
 *
 * This class will be used to get the condition to filter the data.
 */
public class FilterCondition {
    /**
     * All filter conditions.
     *
     * The relationship and the dimension is as below:
     * <ul>
     *     <li>1st dimension - OR</li>
     *     <li>2nd dimension - AND</li>
     * </ul>
     *
     * Example:
     * <ul>
     *     <li>
     *         Expression: {@code (%fatal% > 50 AND %confirmed% < 100) OR %state% = "WI"}
     *         <br>
     *         Structure: {@code [[(FATAL > 50), (CONFIRMED < 100)], [(STATE = "WI")]]}
     *     </li>
     * </ul>
     */
    List<List<FilterConditionEntity>> conditions;

    public FilterCondition() {
        this.conditions = new ArrayList<>();
    }

    /**
     * Push the conditions which have AND relationship.
     *
     * @param conditions conditions which have AND relationship
     */
    public void pushConditionsAND(List<FilterConditionEntity> conditions) {
        this.conditions.add(conditions);
    }

    public List<List<FilterConditionEntity>> getConditions() {
        return conditions;
    }
}
