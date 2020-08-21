package classes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * MainController is the controller section in teh mvc design
 */
public class MainController {
    Stage stage;
    LoginController loginController;
    AccountOrganizer ao;
    private String user = "ahmin";
    private String pass = "1234";

    public MainController(Stage stage) {
        loginController = new LoginController();
        ao = new AccountOrganizer();
        this.stage = stage;
    }

    public void start() throws MalformedURLException {
        createLoginScene();
    }

    //scene related
    public void createLoginScene() throws MalformedURLException {
        LoginView login = new LoginView(this);
        Scene scene = new Scene(login.getBorderPane(), 750, 500);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public void createAccountScene() throws MalformedURLException {
        CreateAccountView createAccountView = new CreateAccountView(this);
        Scene scene = new Scene(createAccountView.getBorderPane(), 750, 500);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public void createMainScene() throws BadPaddingException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            NoSuchPaddingException,
            InvalidKeyException,
            IOException {
        MainView mainView = new MainView(this);
        Scene scene = new Scene(mainView.getBorderPane(), 1000, 600);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    //scene switching related
    public void switchToCreateAccountScene() throws MalformedURLException {
        createAccountScene();
    }
    public void switchToLoginScene() throws MalformedURLException {
        createLoginScene();
    }

    //extra helper related
    public boolean verifyLogin(String username, String password) {
        return loginController.verifyLogin(username, password);
    }
    public void save() {
        try {
            ao.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load related
    public void loadLoginInfo() throws IOException {
        loginController.load();
    }
    public void loadAccountOrganizer() throws IOException {
        ao.load();
    }
    public void loadMainScene() throws IOException {
        ao.load();
    }

    //account related
    public List<Account> getAccounts() {
        return ao.getAccounts();
    }
    public void createAccount(String username, String password) throws IOException {
        loginController.save(username,password);
    }
    public void deleteAccount(String name) {
        ao.deleteAccount(name);
    }
    public void editAccount(Account a, String site, String name, String pass) {
        ao.updateAccount(a, site, name, pass);
    }
    public void addAccount(Account a) {
        ao.addAccount(a);
    }
    public void encryptAccount(Account account) throws NoSuchPaddingException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        ao.encryptAccount(account);
    }
    public void decryptAccount(Account account) throws NoSuchPaddingException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            NullPointerException {
        ao.decryptAccount(account);
    }
}
