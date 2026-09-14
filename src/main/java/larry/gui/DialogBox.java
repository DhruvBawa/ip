package larry.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.beans.binding.Bindings;
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
import javafx.scene.shape.Circle;

/**
 * Represents a user command or a response from Larry.
 */
public class DialogBox extends HBox {
    private static final double AVATAR_SIZE = 56.0;
    private static final double USER_MESSAGE_WIDTH_RATIO = 0.76;
    private static final double LARRY_MESSAGE_HORIZONTAL_SPACE = 84.0;

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
        displayPicture.setClip(new Circle(
                AVATAR_SIZE / 2.0, AVATAR_SIZE / 2.0, AVATAR_SIZE / 2.0));
    }

    /**
     * Creates a right-aligned dialog for a user's input.
     *
     * @param text User's input.
     * @return User dialog box.
     */
    public static DialogBox getUserDialog(String text) {
        DialogBox dialogBox = new DialogBox(text, null);
        dialogBox.displayPicture.setManaged(false);
        dialogBox.displayPicture.setVisible(false);
        dialogBox.dialog.maxWidthProperty().bind(
                Bindings.max(120.0, dialogBox.widthProperty().multiply(USER_MESSAGE_WIDTH_RATIO)));
        dialogBox.getStyleClass().add("user-dialog");
        return dialogBox;
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
        dialogBox.dialog.maxWidthProperty().bind(
                Bindings.max(120.0, dialogBox.widthProperty().subtract(LARRY_MESSAGE_HORIZONTAL_SPACE)));
        dialogBox.getStyleClass().add("larry-dialog");
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
        dialog.getStyleClass().add("larry-label");
    }

    /**
     * Applies a response color that communicates the result of the command.
     *
     * @param commandType Simple name of the command class, or {@code Error}.
     */
    private void changeDialogStyle(String commandType) {
        switch (commandType) {
            case "AddCommand" -> dialog.getStyleClass().add("success-label");
            case "MarkCommand", "UnmarkCommand" -> dialog.getStyleClass().add("status-label");
            case "DeleteCommand" -> dialog.getStyleClass().add("delete-label");
            case "Error" -> dialog.getStyleClass().add("error-label");
            default -> {
                // Keep the standard reply style for commands without a specialized color.
            }
        }
    }
}
