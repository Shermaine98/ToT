package com.example.atayansy.tot.java;

/**
 * @author shermaine sy
 * @author geraldine atayan
 */
public class User {

    private int userID;
    private String email;
    private String userName;
    private String password;

    public User(int userID, String email, String userName, String password) {
        this.userID = userID;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User() {

    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
