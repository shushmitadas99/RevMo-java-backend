package com.revature.driver;

import com.revature.controller.AccountController;
import com.revature.controller.Controller;
import com.revature.controller.TransactionController;
import com.revature.controller.UserController;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class Driver {
    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins);

        Controller[] controllers = { new UserController(), new TransactionController(), new AccountController()};

        for (Controller controller : controllers) {
            controller.mapEndpoints(app);
        }

        app.start(8080);
    }
}
