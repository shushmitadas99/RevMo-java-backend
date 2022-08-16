package com.revature.controller;

import com.revature.service.UserService;
import io.javalin.Javalin;

public class UserController implements Controller {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/user", ctx -> {

        });
    }
}
