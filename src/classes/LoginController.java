package classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * LoginController communicates with the module class updating the view class
 */
public class LoginController {
    Login login;

    /**
     * when save method is run for the first time, it creates a file (account), then acts as a save function.
     */
    public void save(String username, String password) throws IOException {
        String fileName = "LoginAccount.json";
        login = new Login(username, password);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(Paths.get(fileName).toFile(), login);
    }

    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        login = mapper.readValue(Paths.get("LoginAccount.json").toFile(), Login.class);
        System.out.println("Load: " + login.toString());
    }

    public boolean verifyLogin(String username, String password) {
        if (login.getUsername().equals(username) && login.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
