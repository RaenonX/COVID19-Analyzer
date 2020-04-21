import java.util.ArrayList;
import java.util.List;

public class UnitedStates implements IPopulation {
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

    @Override
    public int getPopulation() {
        return 0;
    }
}
