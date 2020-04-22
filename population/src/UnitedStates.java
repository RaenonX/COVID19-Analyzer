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
        return null;
    }

    public County getCounty(String countyState) {
        return null;
    }

    public County getCounty(String county, String state) {
        return null;
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
