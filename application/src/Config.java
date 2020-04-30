import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the config file of the application.
 * <p>
 * This currently can only be updated externally.
 */
public class Config {
    // region Keys of the config file
    public static String KEY_DATA = "data";
    public static String KEY_US_POPS = "uspops";
    public static String KEY_US_STATES = "usstates";
    public static String KEY_FILTER_DOC = "filterdoc";
    public static String KEY_STYLE = "style";
    // endregion

    // region Default paths of the config file
    public static String PATH_DATA = ".res/data/data.csv";
    public static String PATH_US_POPS = ".res/data/uspops.csv";
    public static String PATH_US_STATES = ".res/data/usstates.csv";
    public static String PATH_FILTER_DOC = ".res/filterdoc.html";
    public static String PATH_STYLE = ".res/main.css";
    // endregion

    public static String PATH_CONFIG = "config.cfg";

    private static String toAbsolutePath(String relativePath) {
        return new File(relativePath).getAbsolutePath();
    }

    /**
     * Write the config to config file at `config.cfg`.
     */
    private void writeConfigFile() {
        try {
            Files.deleteIfExists(Paths.get(PATH_CONFIG));
        } catch (IOException e) {
            System.out.println("Failed delete the original config file. Exiting the application.");
            System.exit(1);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("config.cfg")))) {
            writer.append(toStringBuilder());
        } catch (IOException e) {
            System.out.println("Failed to create config file. Exiting the application.");
            System.exit(1);
        }
    }

    /**
     * Parse the config file at {@code filePath} to be a {@code Config}.<br>
     * If any error occurred during the parsing,
     * a default {@code Config} will be created and overwrite the original one.
     *
     * @param filePath file path of the config file.
     * @return {@code Config} that is finally stored and used
     */
    public static Config parse(String filePath) {
        try {
            Map<String, String> configPaths = new HashMap<>(); // Key, Path

            Files.lines(Paths.get(filePath)).map(line -> line.split(",", 2)).forEach(line -> {
                if (line.length < 2) {
                    return;
                }

                String key = line[0];
                String path = line[1];

                configPaths.put(key, path);
            });

            return new Config(
                    toAbsolutePath(configPaths.getOrDefault(KEY_DATA, PATH_DATA)),
                    toAbsolutePath(configPaths.getOrDefault(KEY_US_POPS, PATH_US_POPS)),
                    toAbsolutePath(configPaths.getOrDefault(KEY_US_STATES, PATH_US_STATES)),
                    toAbsolutePath(configPaths.getOrDefault(KEY_FILTER_DOC, PATH_FILTER_DOC)),
                    toAbsolutePath(configPaths.getOrDefault(KEY_STYLE, PATH_STYLE)));
        } catch (IOException e) {
            System.out.println(
                    "IOException occurs during the config file parsing. Creating the new config file and overwrite it.");

            Config cfg = new Config();
            cfg.writeConfigFile();

            return cfg;
        }
    }

    private String dataPath;
    private String usPopsPath;
    private String usStatesPath;
    private String filterDocPath;
    private String stylePath;

    /**
     * Constructor of default config.
     */
    private Config() {
        this.dataPath = toAbsolutePath(PATH_DATA);
        this.usPopsPath = toAbsolutePath(PATH_US_POPS);
        this.usStatesPath = toAbsolutePath(PATH_US_STATES);
        this.filterDocPath = toAbsolutePath(PATH_FILTER_DOC);
        this.stylePath = toAbsolutePath(PATH_STYLE);
    }

    /**
     * Constructor of given config properties.
     *
     * @param dataPath      path of the COVID-19 case data file
     * @param usPopsPath    path of the US population data file
     * @param usStatesPath  path of the US states name conversion file
     * @param filterDocPath path of the filter syntax documentation file
     * @param stylePath     path of the css stylesheet file
     */
    private Config(String dataPath, String usPopsPath, String usStatesPath, String filterDocPath, String stylePath) {
        this.dataPath = dataPath;
        this.usPopsPath = usPopsPath;
        this.usStatesPath = usStatesPath;
        this.filterDocPath = filterDocPath;
        this.stylePath = stylePath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public String getUsPopsPath() {
        return usPopsPath;
    }

    public String getUsStatesPath() {
        return usStatesPath;
    }

    public String getFilterDocPath() {
        return filterDocPath;
    }

    public String getStylePath() {
        return stylePath;
    }

    /**
     * Convert this {@code Config} to {@code StringBuilder} which contains the content of this config.
     *
     * @return {@code StringBuilder} containing the content of this config that can be parsed back to {@code Config}
     */
    private StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", KEY_DATA, this.dataPath)).append("\n");
        sb.append(String.join(",", KEY_US_POPS, this.usPopsPath)).append("\n");
        sb.append(String.join(",", KEY_US_STATES, this.usStatesPath)).append("\n");
        sb.append(String.join(",", KEY_FILTER_DOC, this.filterDocPath)).append("\n");
        sb.append(String.join(",", KEY_STYLE, this.stylePath)).append("\n");

        return sb;
    }
}
