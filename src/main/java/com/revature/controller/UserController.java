package com.revature.controller;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.revature.model.User;
import io.jsonwebtoken.Jwts;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import com.revature.service.UserService;
import io.javalin.Javalin;
import org.eclipse.jetty.security.authentication.AuthorizationService;
import org.json.JSONObject;

import java.sql.SQLException;

public class UserController implements Controller {
    private UserService userService;
    String token;
    public UserController() {
        this.userService = new UserService();
    }

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/user", ctx -> {

        });

        //TODO: Implement resetpassword URL
        app.get("/resetpassword/", ctx -> {
            System.out.println(token);
        });

        app.post("/forgotpassword", ctx->{
            //String email="";

            JSONObject inputEmail = new JSONObject(ctx.body());
            //System.out.println(UserService.getUserEmailByEmail(inputEmail.getString("email")));
            try {
                if (UserService.getUserEmailByEmail(inputEmail.getString("email"))) {

                    User currUser = UserService.getUserByEmail(inputEmail.getString("email"));

                    String jwtToken = Jwts.builder().claim("last_name", currUser.getLastName()).claim("userId", currUser.getUserId()).claim("email", currUser.getEmail()).setSubject(currUser.getFirstName()).setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(Instant.now())).setExpiration(Date.from(Instant.now().plus(5L, ChronoUnit.MINUTES))).compact();

                    UserService.sendToken(jwtToken, currUser.getUserId());
                    System.out.println(jwtToken);
                    token = jwtToken;

                    //UserService.updatePassword(password, userId);
                } else {
                    System.out.println("Invalid email");
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        } );


    }
}
