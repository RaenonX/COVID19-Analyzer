import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class to convert the state abbreviation to full name.
 */
public class StateNameConverter {
    private static final int IDX_ABBR = 0;
    private static final int IDX_NAME = 1;

    private Map<String, String> dict;  // abbr, full name

    public StateNameConverter(String filePath) throws IOException {
        this.dict = new TreeMap<>();

        Files.lines(Paths.get(filePath)).map(x -> x.split(",")).forEach(x -> {
            dict.put(x[IDX_ABBR], x[IDX_NAME]);
        });
    }

    /**
     * Get the full name of a state with {@code abbr}.
     * If the {@code abbr} is not present, return {@code abbr}.
     *
     * @param abbr state name abbreviation
     * @return full state name or {@code abbr}
     */
    public String getFullName(String abbr) {
        return this.dict.getOrDefault(abbr, abbr);
    }
}
