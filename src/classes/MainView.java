package classes;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MainView is the view section of the mvc design
 */
public class MainView {
    BorderPane mainPane;
    Pane centerPane;
    TableView<Account> tableView;
    TableColumn siteCol;
    TableColumn usernameCol;
    TableColumn passwordCol;
    TableColumn imageWarningCol;
    HBox hbox;
    Button detailsBtn;
    Button addBtn;
    Button removeBtn;
    Button editBtn;
    Button nextBtn;
    Button cancelBtn;
    Button backBtn;
    Button safeQuitBtn;
    MainController mainController;
    TextField userTF;
    TextField passwordTF;
    TextField siteTF;
    Account selectedAccount;

    int x = 165;
    int y = 50;

    public MainView(MainController mainController) throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        this.mainController = mainController;
        selectedAccount = null;
        mainPane = new BorderPane();
        centerPane = new Pane();
        mainController.loadMainScene();
        tableView = new TableView<Account>();
        mainPane.setCenter(tableView);

        siteCol = new TableColumn("Site");
        siteCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountName"));
        usernameCol = new TableColumn("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("userName"));
        passwordCol = new TableColumn("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<Account, String>("userPassword"));
        imageWarningCol = new TableColumn("<warnings>");

        siteCol.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        usernameCol.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        passwordCol.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        imageWarningCol.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        tableView.getColumns().addAll(siteCol, usernameCol, passwordCol, imageWarningCol);

        hbox = new HBox();
        mainPane.setBottom(hbox);

        String warningImagePath = this.getClass().getClassLoader().getResource("resources/warning.png").getPath();
        File logoFile = new File(warningImagePath);
        Image warningImage = new Image(logoFile.toURI().toString(), 25, 25, false, false);
        Label warning = new Label("Password easily guessed or reused", new ImageView(warningImage));

        detailsBtn = new Button();
        detailsBtn.setText("Details...");
        detailsBtn.setMaxWidth(300);

        addBtn = new Button();
        addBtn.setText("Add");
        addBtn.setMaxWidth(300);

        removeBtn = new Button();
        removeBtn.setText("Remove");
        removeBtn.setMaxWidth(300);

        editBtn = new Button();
        editBtn.setText("Edit");
        editBtn.setMaxWidth(300);

        safeQuitBtn = new Button("Log Out");

        hbox.setHgrow(detailsBtn, Priority.ALWAYS);
        hbox.setHgrow(addBtn, Priority.ALWAYS);
        hbox.setHgrow(removeBtn, Priority.ALWAYS);
        hbox.setHgrow(editBtn, Priority.ALWAYS);
        hbox.getChildren().addAll(warning, detailsBtn, addBtn, removeBtn, editBtn, safeQuitBtn);

        /**
         * initializations above
         */

        load();

        /**
         * event handlers
         */
        detailsBtn.setOnAction(event -> {
            Account account = tableView.getSelectionModel().getSelectedItem();

            if (account!=null) {
                decrypt(account);
                alternativeScene("detail");
                changeAccountText(account);

                backBtn.setOnAction(event13 -> {
                    encryptAndChangeScene(account);
                });
            }
        });

        editBtn.setOnAction(event -> {
            Account account = tableView.getSelectionModel().getSelectedItem();

            if (account != null) {
                decrypt(account);
                alternativeScene("edit");
                changeAccountText(account);

                cancelBtn.setOnAction(event1 -> {
                    encryptAndChangeScene(account);
                });

                nextBtn.setOnAction(event12 -> {
                    mainController.editAccount(account, siteTF.getText(), userTF.getText(), passwordTF.getText());
                    mainController.save();
                    mainPane.setCenter(tableView);
                    switchToMainScreen();
                    encrypt(account);
                });

            }
        });

        addBtn.setOnAction(event -> {
            alternativeScene("add");
            cancelBtn.setOnAction(event1 -> {
                switchToMainScreen();
            });

            nextBtn.setOnAction(event12 -> {
                Account account = new Account(siteTF.getText(), userTF.getText(), passwordTF.getText());
                encrypt(account);
                mainController.addAccount(account);
                mainController.save();
                mainPane.setCenter(tableView);
                switchToMainScreen();
            });
        });

        removeBtn.setOnAction(event -> {
            Account account = tableView.getSelectionModel().getSelectedItem();
            if (account != null) {
                mainController.deleteAccount(account.getAccountName());
                tableView.getItems().remove(account);
                mainController.save();
            }
        });

        safeQuitBtn.setOnAction(event -> {
            mainController.save();
            try {
                mainController.switchToLoginScene();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        tableView.setOnMousePressed(event -> {
            Account account = tableView.getSelectionModel().getSelectedItem();
            try {
                mainController.decryptAccount(account);
                selectedAccount = account;
                updateTable();
            } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | NullPointerException e) {
                e.printStackTrace();
            }
        });

        tableView.setOnMouseReleased(event -> {
            Account account = tableView.getSelectionModel().getSelectedItem();
            try {
                mainController.encryptAccount(account);
                updateTable();
            } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
            }
        });
        
    }

    public BorderPane getBorderPane() {
        return mainPane;
    }
    public void setTable() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        for (Account account : mainController.getAccounts()) {
            tableView.getItems().add(account);
        }
    }
    public void updateTable() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        int i = 0;
        for (Account account : mainController.getAccounts()) {
            if (account == selectedAccount) {
                tableView.getItems().set(i, selectedAccount);
                break;
            }
            i++;
        }
    }
    public void load() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        mainController.loadMainScene();
        setTable();
    }
    public void alternativeScene(String buttonType) {
        mainPane.setCenter(centerPane);
        centerPane.setMinSize(250, 100);
        centerPane.setMaxSize(500, 350);
        centerPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 178, 102), CornerRadii.EMPTY, Insets.EMPTY)));

        siteTF = new TextField();
        siteTF.setPromptText("site");
        siteTF.setTranslateX(x);
        siteTF.setTranslateY(y);

        userTF = new TextField();
        userTF.setPromptText("username");
        userTF.setTranslateX(x);
        userTF.setTranslateY(y + 30);

        passwordTF = new TextField();
        passwordTF.setPromptText("password");
        passwordTF.setTranslateX(x);
        passwordTF.setTranslateY(y + 60);

        nextBtn = new Button("Next");
        nextBtn.setTranslateX(x + 60);
        nextBtn.setPrefWidth(100);
        nextBtn.setTranslateY(y + 90);
        nextBtn.setVisible(true);

        cancelBtn = new Button("Cancel");
        cancelBtn.setTranslateX(x);
        cancelBtn.setTranslateY(y + 90);
        cancelBtn.setVisible(true);

        backBtn = new Button("back");
        backBtn.setTranslateX(x);
        backBtn.setTranslateY(y + 90);
        backBtn.setPrefWidth(160);
        backBtn.setVisible(false);

        if (buttonType.equals("detail")) {
            nextBtn.setVisible(false);
            cancelBtn.setVisible(false);
            backBtn.setVisible(true);
        }

        centerPane.getChildren().addAll(siteTF, userTF, passwordTF, nextBtn, cancelBtn, backBtn);
    }
    public void decrypt(Account account) {
        try {
            mainController.decryptAccount(account);
        } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
    public void encrypt(Account account) {
        try {
            mainController.encryptAccount(account);
        } catch (NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * three methods below are used in various buttons to improve the efficiency
     */
    public void switchToMainScreen() {
        try {
            mainController.createMainScene();
        } catch (BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
    }
    public void changeAccountText(Account account) {
        siteTF.setText(account.getAccountName());
        userTF.setText(account.getUserName());
        passwordTF.setText(account.getUserPassword());
    }
    public void encryptAndChangeScene(Account account) {
        switchToMainScreen();
        encrypt(account);
    }
}