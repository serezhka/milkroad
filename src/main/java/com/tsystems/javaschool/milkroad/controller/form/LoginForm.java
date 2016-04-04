package com.tsystems.javaschool.milkroad.controller.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Sergey on 24.03.2016.
 */
public class LoginForm implements Serializable {
    private String email;
    private String pass;

    @Size(min = 1, max = 130, message = "Invalid email")
    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(final String pass) {
        this.pass = pass;
    }
}