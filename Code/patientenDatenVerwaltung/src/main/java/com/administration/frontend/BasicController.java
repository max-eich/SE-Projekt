package com.administration.frontend;

import com.administration.backend.Patient;
import com.administration.backend.User;
import org.jetbrains.annotations.NotNull;

public abstract class BasicController {

    private User user=new User();

    public User getUser(){
        return this.user;
    }

    public void setUser(@NotNull User user){this.user =user;};

    private Patient patient=new Patient();

    public Patient getPatient() {return this.patient;}

    public void setPatient(@NotNull Patient patient){this.patient=patient;}

}
