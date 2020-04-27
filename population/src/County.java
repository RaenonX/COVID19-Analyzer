import java.util.List;

/**
 * A class represents a county.
 */
public class County implements IPopulation {
	private final String name;
	private final int population;
	private final double latitude;
	private final double longitude;
	private final List<Integer> zips;

	/**
	 * Construct a county.
	 *
	 * @param name name of the county. should not be empty and alphabets only
	 * @param latitude latitude of the county
	 * @param longitude longitude of the county
	 * @param population population of the county. 0 if no data
	 * @param zips zip code(s) of the county
	 * @throws InvalidLatitudeException thrown when {@code latitude} is not in the range of -90~90
	 * @throws InvalidLongitudeException thrown when {@code longitude} is not in the range of -180~180
	 * @throws InvalidPopulationCount thrown when {@code population} is a negative integer
	 * @throws InvalidCountyNameException thrown when {@code name} is invalid
	 */
	public County(String name, double latitude, double longitude, int population, List<Integer> zips) throws InvalidLatitudeException, InvalidLongitudeException, 
	InvalidPopulationCount, InvalidCountyNameException {
		this.name = name;
		if (name == null || name.strip().equals("") || !StringUtils.isAlphabets(name))  {
 			throw new InvalidCountyNameException();
		}
		this.latitude = latitude;
		if (latitude < -90 || latitude > 90) {
			throw new InvalidLatitudeException(latitude);
		}
		this.longitude = longitude;
		if (longitude < -180 || longitude > 180) {
			throw new InvalidLongitudeException(longitude);
		}
		this.population = population;
		if (population < 0) {
			throw new InvalidPopulationCount(population);
		}
		this.zips = zips;
	}

	public String getName() {
		return name;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
	public int getPopulation(){

		return population;
	}

	public List<Integer> getZips() {
		return zips;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		County county = (County) o;

		if (population != county.population) return false;
		if (Double.compare(county.latitude, latitude) != 0) return false;
		if (Double.compare(county.longitude, longitude) != 0) return false;
		if (!name.equals(county.name)) return false;
		return zips.equals(county.zips);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = name.hashCode();
		result = 31 * result + population;
		temp = Double.doubleToLongBits(latitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + zips.hashCode();
		return result;
	}
}
