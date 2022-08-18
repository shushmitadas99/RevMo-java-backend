package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.service.AccountService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AccountController implements Controller{
    private AccountService accountService;

    public AccountController() {
        accountService = new AccountService();
    }
    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/accounts", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            if (myUser == null) {
                ctx.result("You are not logged in!");
                ctx.status(404);
            }else if (myUser.getUserRole().equals("employee")) {
                ObjectMapper om = new ObjectMapper();
                Map<String, String> newAccount = om.readValue(ctx.body(), Map.class);
                try {
                    ctx.json(accountService.openAccount(newAccount));
                    ctx.status(201);
                } catch (InvalidParameterException e) {
                    ctx.json(e.getMessages());
                    ctx.status(400);
                }
            }
        });

        app.put("/accounts/{aId}", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");
            int aId = Integer.parseInt(ctx.pathParam("aId"));
            int uId = myUser.getUserId();
            ctx.json(accountService.linkUserToAccount(aId, uId));
            ctx.status(200);
        });

        app.delete("/accounts/{aId}", ctx -> {
           HttpServletRequest req = ctx.req;
           HttpSession session = req.getSession();
           User myUser = (User) session.getAttribute("logged_in_user");
           int aId = Integer.parseInt(ctx.pathParam("aId"));
           int uId = myUser.getUserId();
           ctx.json(accountService.unlinkUserFromAccount(aId, uId));
           ctx.status(200);
        });

        app.delete("/accounts/{aId}", ctx -> {
           HttpServletRequest req = ctx.req;
           HttpSession session = req.getSession();
           User myUser = (User) session.getAttribute("logged_in_user");
           int aId = Integer.parseInt(ctx.pathParam("aId"));
           ctx.json(accountService.deleteAccount(aId));
           ctx.status(200);
        });

        app.get("/accounts", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            if (myUser == null) {
                ctx.result("You are not logged in!");
                ctx.status(404);
            } else {
                String email = myUser.getEmail();
                ctx.json(accountService.getAccountsByEmail(email));
                ctx.status(200);
            }
        });

        app.get("/accounts/{aId}", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");
            int aId = Integer.parseInt(ctx.pathParam("aId"));
            String email = myUser.getEmail();
            ctx.json(accountService.getAccountByEmailAndAccountId(email, aId));
            ctx.status(200);
        });
    }


}