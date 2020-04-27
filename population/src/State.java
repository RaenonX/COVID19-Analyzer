import java.util.List;
import java.util.function.Predicate;

public class State implements IPopulation, IPopulationCondition<County> {
	private String abbr;
	private String name;
	private List<County> counties;

	/**
	 * Construct a state.
	 *
	 * Both {@code abbr} and {@code name} should be alphabets only.
	 *
	 * @param abbr state abbreviation
	 * @param name state name
	 * @param counties list of counties which is in this state
	 */
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
		return counties.stream()				
				.mapToInt(x -> x.getPopulation())
				.sum();	}

	@Override
	public int getPopulation(Predicate<? super County> predicate) {
		return counties.stream()
				.filter(predicate)
				.mapToInt(x -> x.getPopulation())
				.sum();
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
