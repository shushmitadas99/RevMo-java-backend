package com.revature.controller;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.revature.model.User;
import com.revature.utility.EmailUtility;
import io.jsonwebtoken.Jwts;
import java.lang.Exception;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;



import com.revature.exception.InvalidLoginException;
import com.revature.model.User;
import com.revature.service.UserService;
import io.javalin.Javalin;
import org.eclipse.jetty.security.authentication.AuthorizationService;
import org.json.JSONObject;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

import com.revature.model.Account;
import com.revature.service.AccountService;

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

        app.post("/login", ctx -> {
            User user = ctx.bodyAsClass(User.class);

            String email = user.getEmail();
            String pass = user.getPassword();
            try {
                User loggedInUser = userService.login(email, pass);

                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                session.setAttribute("userId", loggedInUser.getUserId());
                session.setAttribute("email", loggedInUser.getEmail());
                session.setAttribute("userRole", loggedInUser.getUserRole());

                ctx.json(loggedInUser);
            } catch (InvalidLoginException | SQLException e) {
                ctx.result(e.getMessage());
                ctx.status(400);
            }

        });

        app.post("/logout", ctx -> {
//            System.out.println("logout");

            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            ctx.result("Successfully logged out");
            session.invalidate();
            ctx.status(201);
        });

        app.get("/logged-in-user", ctx -> {
                    HttpServletRequest req = ctx.req;

                    HttpSession session = req.getSession();
                    User myUser = (User) session.getAttribute("logged_in_user");

                    if (myUser == null) {
                        ctx.result("You are not logged in!");
                        ctx.status(404);
                    } else {

                    }

                });

        // returns currently logged in user's info
        app.get("/user", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");


            //TODO undo when can login!
//            myUser = new User(1, "Bob", "Smith", "jd80@a.ca", "foobar", "666-123-4562", "user");

            if (email == null) {

                ctx.result("You are not logged in!");
                ctx.status(404);
            } else {
                User myUser = userService.getUserByEmail(email);
                List<Account> userAccounts = accountService.getAccountsByEmail(email);

                myUser.setAccounts(userAccounts);

                ctx.json(myUser);
                ctx.status(200);
            }
        });

        app.put("/resetpassword", ctx -> {

            try {
                String token = ctx.req.getParameter("token");

                DecodedJWT jwt = JWT.decode(token);
                if (jwt.getExpiresAt().before(new Date())) {
                    ctx.status(404);
                    throw new RuntimeException("Reset Link Expired. Please try again");
                } else {
                    boolean validateToken = UserService.validateToken(token); // we need to write a code to verify the token validity
                    if (validateToken) {
                        JSONObject newPassword = new JSONObject(ctx.body());
                        UserService.updatePassword(newPassword.getString("newpassword"), token);
                        UserService.deleteToken(token);
                        // redirect user to setup a new password page
                    } else {
                        ctx.status(404);
                        throw new RuntimeException("OOPS something went wrong. Reset Link Expired");
                        // return user a message with invalid token
                    }
                }
            }catch (Exception e){
                ctx.status(404);
                throw new RuntimeException("Reset Link Expired. Please try again");
            }
        });

        app.post("/forgotpassword", ctx->{
            //String email="";

            JSONObject inputEmail = new JSONObject(ctx.body());
            //System.out.println(UserService.getUserEmailByEmail(inputEmail.getString("email")));
            try {
                if (UserService.getUserEmailByEmail(inputEmail.getString("email"))) {

                    User currUser = new User();

                    currUser = UserService.getUserByInputEmail(inputEmail.getString("email"));

                    String jwtToken = Jwts.builder().claim("last_name", currUser.getLastName()).claim("userId", currUser.getUserId()).claim("email", currUser.getEmail()).setSubject(currUser.getFirstName()).setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(Instant.now())).setExpiration(Date.from(Instant.now().plus(5L, ChronoUnit.MINUTES))).compact();

                    UserService.sendToken(jwtToken, currUser.getUserId());

                    System.out.println(jwtToken);

                    String addressUrl =  "http://localhost:8080/resetpassword?token="+jwtToken;
                    int status = EmailUtility.email(inputEmail.getString("email"), "Reset your RevMo password", addressUrl);
                    if (status == 202) {
                        System.out.println("Please Check Your Email!");
                    }else{
                        ctx.status(404);
                        throw new RuntimeException("The email pertaining to the account has been sent an email. Please check email for reset link.");
                    }
                } else {
                    ctx.status(404);
                    System.out.println("The email pertaining to the account has been sent an email. Please check email for reset link.");
                }
            }catch (Exception e){
                ctx.status(404);
                throw new RuntimeException("The email pertaining to the account has been sent an email. Please check email for reset link.");
            }
        } );


    }
}
