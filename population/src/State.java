import java.util.List;
import java.util.function.Predicate;

public class State implements IPopulation, IPopulationCondition<County> {
    private String abbr;
    private String name;
    private List<County> counties;

    public State(String abbr, String name, List<County> counties) {
        this.abbr = abbr;
        this.name = name;
        this.counties = counties;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getName() {
        return name;
    }

    public List<County> getCounties() {
        return counties;
    }

    @Override
    public int getPopulation() {
        // TODO: Sum of the population of all counties
        return 0;
    }

    @Override
    public int getPopulation(Predicate<? super County> predicate) {
        // TODO: Sum of the population of filtered `County`
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

    @Override
    public String toString() {
        return this.abbr;
    }
}
