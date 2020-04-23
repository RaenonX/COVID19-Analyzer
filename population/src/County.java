import java.util.List;

/**
 * A class represents a county.
 */
public class County implements IPopulation {
    private String name;
    private int population;
    private double latitude;
    private double longitude;
    private List<Integer> zips;

    /**
     * Construct a county.
     *
     * @param name name of the county. should not be empty and alphabets only
     * @param latitude latitude of the county
     * @param longitude longitude of the county
     * @param population population of the county. 0 if no data
     * @param zips zip code(s) of the county
     */
    public County(String name, double latitude, double longitude, int population, List<Integer> zips) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.zips = zips;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    public List<Integer> getZips() {
        return zips;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
