package larry.gui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import larry.Larry;

/**
 * Controls Larry's main graphical interface.
 */
public class MainWindow extends AnchorPane {
    private static final String LARRY_IMAGE_PATH = "/images/EvilLarry.jpg";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Image userImage = AvatarFactory.createUserAvatar();
    private final Image larryImage = loadLarryImage();
    private Larry larry;

    /**
     * Loads Larry's avatar from the resources packaged with the application.
     *
     * @return Larry's avatar image.
     */
    private static Image loadLarryImage() {
        String imageUrl = Objects.requireNonNull(
                MainWindow.class.getResource(LARRY_IMAGE_PATH),
                "Missing Larry image: " + LARRY_IMAGE_PATH).toExternalForm();
        return new Image(imageUrl);
    }

    /**
     * Configures the dialog view to follow the latest message automatically.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the Larry instance that processes user commands.
     *
     * @param larry Larry instance shared by this window.
     */
    public void setLarry(Larry larry) {
        this.larry = larry;
    }

    /**
     * Displays the user's input and Larry's response, then clears the input field.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = larry.getResponse(input);
        String commandType = larry.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getLarryDialog(response, larryImage, commandType));
        userInput.clear();
    }
}
