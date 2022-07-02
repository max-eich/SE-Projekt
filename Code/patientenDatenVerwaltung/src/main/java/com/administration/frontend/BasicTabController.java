package com.administration.frontend;

import com.administration.backend.User;

public abstract class BasicTabController extends BasicController{

    public abstract void setup(User u);

    public abstract void setup(User u, int pid);

    public void update(User u, int pid){
        setup(u,pid);
    }

    public void update(User u){
        setup(u);
    }

}
