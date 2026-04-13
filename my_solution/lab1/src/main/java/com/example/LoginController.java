package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {

    @FXML
    private AnchorPane loginScreen;

    @FXML
    private Label notext;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void onHelloButtonClick(ActionEvent event) {
        // כאן נכתוב בהמשך את הלוגיקה של בדיקת שם המשתמש והסיסמה
    }
}