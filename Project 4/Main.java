package rupizza.rupizzeria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class sets the main stage of our GUI. After everything is set, the GUI
 * is launched and then the user will be able to interact with it.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Main extends Application {

    /**
     * This method loads our .fxml file for the main menu and creates a new
     * scene. It then sets the title of our primary stage as "RUPizzeria.com".
     * This means that the GUI window will be titled as "RUPizzeria.com".
     * After setting the title, the primary stage will then set the scene and
     * then show everything.
     *
     * @param stage the main menu stage of our GUI
     *
     * @throws IOException if some Input/Output exception occurs, this is
     * thrown
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                   new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 700);
        stage.setTitle("RUPizzeria.com");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This main method of the class launches the GUI, letting the user start
     * interacting with it.
     *
     * @param args the command line arguments that the user inputs.
     */
    public static void main(String[] args) {
        launch();
    }
}
