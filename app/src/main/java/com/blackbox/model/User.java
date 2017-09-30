package com.blackbox.model;

import java.util.Date;

/**
 * Created by Arnaud on 30/09/2017.
 */

public class User {

    private int _id;
    private String name;
    private String firstname;
    private String email;
    private String password;
    private Boolean isPremiumUser;
    private Date premiumDateOfExpiration;
    private int storageSpace;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPremiumUser() {
        return isPremiumUser;
    }

    public void setPremiumUser(Boolean premiumUser) {
        isPremiumUser = premiumUser;
    }

    public Date getPremiumDateOfExpiration() {
        return premiumDateOfExpiration;
    }

    public void setPremiumDateOfExpiration(Date premiumDateOfExpiration) {
        this.premiumDateOfExpiration = premiumDateOfExpiration;
    }

    public int getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(int storageSpace) {
        this.storageSpace = storageSpace;
    }
}
