import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Layout class to be used if failed to load any file of the program.
 */
public class FileLoadFailedLayout {
    /**
     * Display the alert stating that the file was not found.
     */
    public static void displayAlert(IOException ioe) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Resource files failed to load");
        alert.setHeaderText(String.format("Failed to load the file %s.", ioe.getMessage()));
        alert.setContentText(String.format("Check if the path listed in `%s` is correct.", Config.PATH_CONFIG));
        alert.show();
    }
}
