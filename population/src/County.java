public class County implements IPopulation {
    private String name;
    private double latitude;
    private double longitude;

    public County(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int getPopulation() {
        return 0;
    }
}
