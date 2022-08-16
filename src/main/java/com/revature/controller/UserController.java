package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Account;
import com.revature.model.User;
import com.revature.service.AccountService;
import com.revature.service.UserService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserController implements Controller {
    private UserService userService;
    private AccountService accountService;

    public UserController() {
        this.userService = new UserService();
        this.accountService = new AccountService();
    }

    @Override
    public void mapEndpoints(Javalin app) {

        // returns currently logged in user's info
        app.get("/user", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");


            //TODO undo when can long in!
            myUser = new User(1, "Bob", "Smith", "hello@world.net", "foobar", "666-123-4562", "user");

            if (myUser == null) {
                ctx.result("You are not logged in!");
                ctx.status(404);
            } else {

                List<Account> userAccounts = accountService.getAccountsByUserId(myUser.getUserId());
                myUser.setAccounts(userAccounts);
                ctx.json(myUser);
                ctx.status(200);
            }
        });
    }
}
