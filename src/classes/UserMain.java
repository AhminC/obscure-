package classes;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * UserMain launches the program
 */
public class UserMain extends Application {
    private static MainController mainController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        mainController = new MainController(stage);
        mainController.start();
    }
}
