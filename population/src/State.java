public class State implements IPopulation {
    private String abbr;

    public State(String abbr) {
        this.abbr = abbr;
    }

    @Override
    public int getPopulation() {
        return 0;
    }
}
