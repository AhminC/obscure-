package classes;

/**
 * Account class comprises of a site, username, and password. Basic getters/setters are implemented
 */
public class Account {
    private String accountName, userName, userPassword;

    /**
     * default constructor needed for mapper to avoid null value exception
     */
    public Account() {}
    public Account(String accountName, String userName, String userPassword){
        this.accountName = accountName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getAccountName() {
        return this.accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

