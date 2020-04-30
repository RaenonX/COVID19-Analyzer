import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class PopulationDataParser {
	private static final int IDX_STATE = 0;
	private static final int IDX_COUNTY = 1;
	private static final int IDX_POPULATION = 2;
	private static final int IDX_LAT = 3;
	private static final int IDX_LON = 4;
	private static final int IDX_ZIPS = 5;

	/**
	 * Load the US population data to {@code UnitedStates.current}.
	 *
	 * @param path path of US population data file
	 * @param converter converter class to convert state name abbreviation to full name
	 * @throws IOException thrown if file not found
	 */
	public static void loadUsPopFile(String path, StateNameConverter converter) throws IOException {
		Map<String, List<County>> data = new HashMap<>();  // State abbr and list of counties

		Files.lines(Paths.get(path)).map(line -> line.split(",", 6)).forEach(lineEntry -> {
			if (lineEntry.length < 5) {
				return;
			}

			String stateAbbr = lineEntry[IDX_STATE];
			String countyName = lineEntry[IDX_COUNTY];
			int population = Integer.parseInt(lineEntry[IDX_POPULATION]);
			double latitude = Double.parseDouble(lineEntry[IDX_LAT]);
			double longitude = Double.parseDouble(lineEntry[IDX_LON]);

			String[] zipcodes;
			if (lineEntry.length > IDX_ZIPS) {
				zipcodes = lineEntry[IDX_ZIPS].split(" ");
			} else {
				zipcodes = new String[] {};
			}

			List<Integer> zips = Arrays.stream(zipcodes)
					.filter(codes -> codes.length() > 0)
					.map(Integer::valueOf)
					.collect(Collectors.toList());

			List<County> countyList = data.get(stateAbbr);

			if (!data.containsKey(stateAbbr)) {
				countyList = new ArrayList<>();
				data.put(stateAbbr, countyList);
			}
			try {
				countyList.add(new County(countyName, latitude, longitude, population, zips));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		List<State> usData = new ArrayList<>();

		data.keySet().forEach(abbr -> {
			List<County> counties = data.get(abbr);
			try {
				usData.add(new State(abbr, converter.getFullName(abbr), counties));
			} catch (InvalidStateNameException e) {
				e.printStackTrace();
			}
		});

		UnitedStates.load(usData);
	}
}
