package larry.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a message containing the speaker's avatar and dialog text.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load a dialog box.", e);
        }

        dialog.setText(text);
        displayPicture.setImage(image);
    }

    /**
     * Creates a right-aligned dialog for a user's input.
     *
     * @param text User's input.
     * @param image User's avatar.
     * @return User dialog box.
     */
    public static DialogBox getUserDialog(String text, Image image) {
        return new DialogBox(text, image);
    }

    /**
     * Creates a left-aligned, command-styled dialog for Larry's response.
     *
     * @param text Larry's response.
     * @param image Larry's avatar.
     * @param commandType Type of command that produced the response.
     * @return Larry dialog box.
     */
    public static DialogBox getLarryDialog(String text, Image image, String commandType) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.flip();
        dialogBox.changeDialogStyle(commandType);
        return dialogBox;
    }

    /**
     * Places the avatar on the left and points the reply bubble toward it.
     */
    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Applies a response color that communicates the result of the command.
     *
     * @param commandType Simple name of the command class, or {@code Error}.
     */
    private void changeDialogStyle(String commandType) {
        switch (commandType) {
            case "AddCommand" -> dialog.getStyleClass().add("add-label");
            case "MarkCommand", "UnmarkCommand" -> dialog.getStyleClass().add("marked-label");
            case "DeleteCommand" -> dialog.getStyleClass().add("delete-label");
            case "Error" -> dialog.getStyleClass().add("error-label");
            default -> {
                // Keep the standard reply style for commands without a specialized color.
            }
        }
    }
}
