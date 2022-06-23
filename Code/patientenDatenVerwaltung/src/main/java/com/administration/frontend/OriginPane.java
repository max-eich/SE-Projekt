package com.administration.frontend;

import com.administration.backend.Listener;
import com.administration.backend.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OriginPane {

    @FXML
    private Label label;

    private User user;

    public void setUser(User user) {
        this.user = user;
        label.setText(user.name);
    }


    @FXML
    public void initialize() {}

}
