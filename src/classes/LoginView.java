package classes;

import javafx.geometry.Insets;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * LoginView is the view portion of the mvc
 */
public class LoginView {
    BorderPane mainPane;
    Button signInBtn;
    Button createAccountBtn;
    TextField usernameTF;
    TextField passwordTF;
    MainController mainController;
    Label errorLabel;

    public LoginView(MainController mainController) throws MalformedURLException {
        this.mainController = mainController;

        mainPane = new BorderPane();
        Pane leftPane = new Pane();
        Pane rightPane = new Pane();
        leftPane.setBackground(new Background(new BackgroundFill(Color.rgb(255,178,102),
                CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.setLeft(leftPane);
        mainPane.setRight(rightPane);

        Label introText = new Label();
        introText.setText("Welcome :)");
        introText.setFont(new Font("Georgia", 60));
        introText.setTextFill(Paint.valueOf("#2f8dff"));
        introText.setStyle("-fx-font-weight: bold");
        introText.setTranslateX(-50);
        introText.setTranslateY(100);
        rightPane.getChildren().add(introText);

        usernameTF = new TextField();
        usernameTF.setPromptText("username");
        usernameTF.setPrefWidth(250);
        usernameTF.setTranslateX(-45);
        usernameTF.setTranslateY(180);
        rightPane.getChildren().add(usernameTF);

        passwordTF = new TextField();
        passwordTF.setPromptText("password");
        passwordTF.setPrefWidth(250);
        passwordTF.setTranslateX(-45);
        passwordTF.setTranslateY(215);
        passwordTF.setAccessibleRole(AccessibleRole.PASSWORD_FIELD);
        rightPane.getChildren().add(passwordTF);

        signInBtn = new Button();
        signInBtn.setText("Sign in");
        signInBtn.setFont(new Font("Georgia", 15));

        // hover - changes shade
        String IDLE_BUTTON_STYLE1 = "-fx-background-color: #FFFFFF";
        String HOVERED_BUTTON_STYLE1 = "-fx-background-color: #EEEEEE";
        signInBtn.setStyle(IDLE_BUTTON_STYLE1);
        signInBtn.setOnMouseEntered(e -> signInBtn.setStyle(HOVERED_BUTTON_STYLE1));
        signInBtn.setOnMouseExited(e -> signInBtn.setStyle(IDLE_BUTTON_STYLE1));
        signInBtn.setTranslateX(-45);
        signInBtn.setTranslateY(250);
        rightPane.getChildren().add(signInBtn);

        createAccountBtn = new Button();
        createAccountBtn.setText("Create Account");
        createAccountBtn.setFont(new Font("Georgia", 15));
        String IDLE_BUTTON_STYLE2 = "-fx-background-color: #2f8dff;";
        String HOVERED_BUTTON_STYLE2 = "-fx-background-color: #2F77FF";
        signInBtn.setStyle(IDLE_BUTTON_STYLE2);
        signInBtn.setOnMouseEntered(e -> signInBtn.setStyle(HOVERED_BUTTON_STYLE2));
        signInBtn.setOnMouseExited(e -> signInBtn.setStyle(IDLE_BUTTON_STYLE2));
        createAccountBtn.setPrefWidth(175);
        createAccountBtn.setTranslateX(30);
        createAccountBtn.setTranslateY(250);
        rightPane.getChildren().add(createAccountBtn);

        errorLabel = new Label();
        errorLabel.setText("username or password does not match");
        errorLabel.setFont(new Font("Georgia", 15));
        errorLabel.setTextFill(Color.rgb(255,0,0));
        errorLabel.setTranslateX(-45);
        errorLabel.setTranslateY(290);

        rightPane.getChildren().addAll(errorLabel);
        errorLabel.setVisible(false);

        String leftPaneImagePath = this.getClass().getClassLoader().getResource("resources/locked.png").getPath();
        File imageFile = new File(leftPaneImagePath);
        ImageView iv = new ImageView(imageFile.toURI().toString());
        iv.setTranslateX(0);
        iv.setTranslateY(100);

        String leftPaneLogoPath = this.getClass().getClassLoader().getResource("resources/logo.png").getPath();
        File logoFile = new File(leftPaneLogoPath);
        Image logoImage = new Image(logoFile.toURI().toString(), 30, 30, false, false);
        Label companyName = new Label("Obscure", new ImageView(logoImage));
        companyName.setFont(new Font("Georgia",20));

        Label explanation = new Label();
        explanation.setFont(new Font("Georgia", 20));
        explanation.setTranslateX(10);
        explanation.setTranslateY(355);
        explanation.setText("\"One place \n      all protected\"");
        leftPane.getChildren().addAll(iv, companyName, explanation);

        // Interactions
        signInBtn.setOnAction(event -> {
            try {
                mainController.loadLoginInfo();
                if (mainController.verifyLogin(usernameTF.getText(), passwordTF.getText())) {
                    try {
                        mainController.loadAccountOrganizer();
                        mainController.createMainScene();
                    } catch (BadPaddingException
                            | NoSuchAlgorithmException
                            | IllegalBlockSizeException
                            | NoSuchPaddingException
                            | InvalidKeyException
                            | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorLabel.setVisible(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        createAccountBtn.setOnAction(event -> {
            try {
                mainController.switchToCreateAccountScene();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }
    public BorderPane getBorderPane() {
        return mainPane;
    }
}




