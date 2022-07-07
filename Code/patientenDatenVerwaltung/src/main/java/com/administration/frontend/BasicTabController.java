package com.administration.frontend;

import com.administration.backend.Patient;
import com.administration.backend.User;

public abstract class BasicTabController extends BasicController{

    public abstract void setup(User u);

    public abstract void setup(User u, Patient pid);

    public void update(User u, Patient pid){
        setup(u,pid);
    }

    public void update(User u){
        setup(u);
    }

}
