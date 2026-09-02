package larry.gui;

import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Creates simple avatar images without requiring platform-specific image files.
 */
final class AvatarFactory {
    private static final double IMAGE_SIZE = 100.0;

    private AvatarFactory() {
    }

    /**
     * Creates the avatar shown beside user messages.
     *
     * @return User avatar image.
     */
    static Image createUserAvatar() {
        return createAvatar("YOU", Color.web("#0072b2"));
    }

    /**
     * Draws a circular avatar with centered identifying text.
     *
     * @param text Text displayed inside the avatar.
     * @param backgroundColor Avatar background color.
     * @return Rendered avatar image.
     */
    private static Image createAvatar(String text, Color backgroundColor) {
        Canvas canvas = new Canvas(IMAGE_SIZE, IMAGE_SIZE);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(backgroundColor);
        graphics.fillOval(0.0, 0.0, IMAGE_SIZE, IMAGE_SIZE);
        graphics.setFill(Color.WHITE);
        graphics.setFont(Font.font("Arial", FontWeight.BOLD, 24.0));
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setTextBaseline(VPos.CENTER);
        graphics.fillText(text, IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        return canvas.snapshot(parameters, null);
    }
}
