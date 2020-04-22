import java.util.List;

public class County implements IPopulation {
    private String name;
    private int population;
    private double latitude;
    private double longitude;
    private List<Integer> zips;

    public County(String name, double latitude, double longitude, int population, List<Integer> zips) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.zips = zips;
    }

    @Override
    public int getPopulation() {
        return population;
    }
}
