import java.util.List;

public class State implements IPopulation {
    private String abbr;
    private String name;
    private List<County> counties;

    public State(String abbr, String name, List<County> counties) {
        this.abbr = abbr;
        this.name = name;
        this.counties = counties;
    }

    @Override
    public int getPopulation() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (!abbr.equals(state.abbr)) return false;
        return name.equals(state.name);
    }

    @Override
    public int hashCode() {
        int result = abbr.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
