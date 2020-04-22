import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UnitedStates implements IPopulation, IPopulationCondition<State> {
    public static UnitedStates current = new UnitedStates(new ArrayList<>());

    private List<State> states;

    public UnitedStates(List<State> states) {
        this.states = states;
    }

    public State getState(String state) {
        return states.stream()
                .filter(x -> x.getAbbr().equalsIgnoreCase(state) || x.getName().equalsIgnoreCase(state))
                .findFirst()
                .orElse(null);
    }

    public County getCounty(String countyState) {
        // TODO: Handle malformed string / not exists
        String[] str = countyState.split(", ");
        String countyStr = str[0];
        String stateStr = str[1];

        return getCounty(countyStr, stateStr);
    }

    public County getCounty(String county, String state) {
        // TODO: Handle malformed string
        State stateObj = getState(state);
        if (stateObj == null) {
            return null;
        }

        return stateObj.getCounties()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase(county))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getPopulation() {
        // TODO: Sum of all population of all states
        return 0;
    }

    @Override
    public int getPopulation(Predicate<? super State> predicate) {
        // TODO: Sum of all population of filtered states
        return 0;
    }
}
