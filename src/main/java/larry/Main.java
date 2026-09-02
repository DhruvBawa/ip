package larry;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import larry.gui.MainWindow;

/**
 * Provides Larry's graphical user interface using FXML.
 */
public class Main extends Application {
    private final Larry larry = new Larry();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane mainLayout = fxmlLoader.load();
            Scene scene = new Scene(mainLayout);

            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setLarry(larry);

            stage.setTitle("Larry");
            stage.setMinHeight(220.0);
            stage.setMinWidth(417.0);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load the main window.", e);
        }
    }
}
