import java.util.ArrayList;
import java.util.List;

public class FilterCondition {
    List<List<FilterConditionEntity>> conditions;

    public FilterCondition() {
        this.conditions = new ArrayList<>();
    }

    public void pushConditionsAND(List<FilterConditionEntity> entities) {
        this.conditions.add(entities);
    }

    public List<List<FilterConditionEntity>> getConditions() {
        return conditions;
    }
}
