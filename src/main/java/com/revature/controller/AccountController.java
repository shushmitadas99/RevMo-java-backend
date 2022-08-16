package com.revature.controller;

import com.revature.model.User;
import com.revature.service.AccountService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AccountController implements Controller{
    private AccountService accountService;

    public AccountController() {
        accountService = new AccountService();
    }
    @Override
    public void mapEndpoints(Javalin app) {


        app.get("/user-accounts", ctx -> {

            HttpServletRequest req = ctx.req;

            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            //TODO undo when can log in!
            myUser = new User(1, "Bob", "Smith", "hello@world.net", "foobar", "666-123-4562", "user");

            if (myUser == null) {
                ctx.result("You are not logged in!");
                ctx.status(404);
            } else {

                ctx.json(accountService.getAccountsByUserId(myUser.getUserId()));
                ctx.status(200);
            }

        });
    }
}
