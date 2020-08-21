package classes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * CreateAccountView creates scene when user selects "create account" button
 */
public class CreateAccountView {
    BorderPane mainPane;
    TextField usernameTF;
    TextField passwordTF;
    TextField passwordConfirmTF;
    Button backBtn;
    Button nextBtn;
    MainController mainController;
    int x = 25;
    int y = 50;

    public CreateAccountView(MainController mainController) throws MalformedURLException {
        this.mainController = mainController;

        mainPane = new BorderPane();
        Pane centerPane = new Pane();

        mainPane.setCenter(centerPane);
        mainPane.setBackground(new Background(new BackgroundFill(Color.rgb(255,178,102),
                CornerRadii.EMPTY, Insets.EMPTY)));

        usernameTF = new TextField();
        usernameTF.setPromptText("username");
        usernameTF.setTranslateX(x);
        usernameTF.setTranslateY(y);

        passwordTF = new TextField();
        passwordTF.setPromptText("password");
        passwordTF.setTranslateX(x);
        passwordTF.setTranslateY(y+30);

        passwordConfirmTF = new TextField();
        passwordConfirmTF.setPromptText("confirm password");
        passwordConfirmTF.setTranslateX(x);
        passwordConfirmTF.setTranslateY(y+60);

        backBtn = new Button("Back");
        backBtn.setTranslateX(x);
        backBtn.setTranslateY(y+90);

        nextBtn = new Button("Next");
        nextBtn.setPrefWidth(110);
        nextBtn.setTranslateX(x+50);
        nextBtn.setTranslateY(y+90);

        String logoImagePath = this.getClass().getClassLoader().getResource("resources/logo.png").getPath();
        File logoFile = new File(logoImagePath);
        Image logoImage = new Image(logoFile.toURI().toString(), 30, 30, false, false);
        Label companyName = new Label("Obscure", new ImageView(logoImage));
        companyName.setTranslateX(x);
        companyName.setTranslateY(450);

        String exampleImagePath = this.getClass().getClassLoader().getResource("resources/example.jpg").getPath();
        File imageFile = new File(exampleImagePath);
        ImageView iv = new ImageView(imageFile.toURI().toString());
        iv.setTranslateX(x + 200);
        iv.setTranslateY(y);
        iv.setFitWidth(500);
        iv.setFitHeight(400);
        centerPane.getChildren().addAll(usernameTF, passwordTF, passwordConfirmTF, backBtn, nextBtn, companyName, iv);

        nextBtn.setOnAction(event -> {
            try {
                mainController.createAccount(usernameTF.getText(), passwordTF.getText());
                if (passwordTF.getText().equals(passwordConfirmTF.getText())) {
                    mainController.save();
                    mainController.switchToLoginScene();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        backBtn.setOnAction(event -> {
            try {
                mainController.switchToLoginScene();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }
    public BorderPane getBorderPane() {
        return mainPane;
    }
}
