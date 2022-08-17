package com.revature.controller;

import com.revature.exceptions.InvalidLoginException;
import com.revature.model.User;
import com.revature.service.UserService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class UserController implements Controller {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/login", ctx -> {
            User user = ctx.bodyAsClass(User.class);

            String email = user.getEmail();
            String pass = user.getPass();
            try {
                User loggedInUser = userService.login(email, pass);

                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                session.setAttribute("logged_in_user", loggedInUser);

                ctx.json(loggedInUser);
            } catch (InvalidLoginException | SQLException e) {
                ctx.result(e.getMessage());
                ctx.status(400);
            }

        });

        app.post("/logout", ctx -> {
            HttpServletRequest req = ctx.req;

            HttpSession session = req.getSession();
            ctx.result("Successfully logged out");
            session.invalidate();
        });

        app.get("/logged-in-user", ctx -> {
            HttpServletRequest req = ctx.req;

            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            if (myUser == null) {
                ctx.result("You are not logged in!");
                ctx.status(404);
            } else {
                ctx.json(myUser);
                ctx.status(200);
            }
        });
    }


}
