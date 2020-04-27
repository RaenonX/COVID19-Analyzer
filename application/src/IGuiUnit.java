import javafx.scene.layout.Pane;

/**
 * Class which can be a GUI unit must implement this interface.
 */
public interface IGuiUnit {
    /**
     * Get the GUI element of this class.
     *
     * @return GUI element of this class
     */
    Pane getGuiElement();
}
