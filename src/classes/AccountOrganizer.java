package classes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * AccountOrganizer utilizes the Account class to manipulate Account's members
 */
public class AccountOrganizer {
    private List <Account> accounts;

    public AccountOrganizer() {
        accounts = new ArrayList<Account>();
    }

    //account related
    public List<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account account) {
        accounts.add(account);
    }
    public void deleteAccount(String name) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountName().toLowerCase().equals(name.toLowerCase())) {
                accounts.remove(i);
                break;
            }
        }
    }
    public void updateAccount(Account account, String site, String name, String pass) {
        account.setAccountName(site);
        account.setUserName(name);
        account.setUserPassword(pass);
    }
    public void encryptAccount(Account account) throws NoSuchPaddingException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        String originalString = account.getUserPassword();
        String encryptedPassword = Encrypt.encrypt(originalString);

        account.setUserPassword(encryptedPassword);
    }
    public void decryptAccount(Account account) throws NoSuchPaddingException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            NullPointerException {
        String originalString = account.getUserPassword();
        String decryptedPassword = Encrypt.decrypt(originalString);
        account.setUserPassword(decryptedPassword);
    }

    //save and load related
    public void save() throws IOException {
        try {
            File createFile = new File("AccountInfo.json");
            if (createFile.createNewFile()) {}

        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(Paths.get("AccountInfo.json").toFile(), accounts);
    }
    public List<Account> load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        accounts = mapper.readValue(Paths.get("AccountInfo.json").toFile(), new TypeReference<List<Account>>(){});
        return accounts;
    }
}