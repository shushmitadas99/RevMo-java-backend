package com.revature.utility;

import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
    public static Connection createConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();

        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        String url = dotenv.get("db_url");
        String username = dotenv.get("db_username");
        String password = dotenv.get("db_password");

<<<<<<< HEAD
        //System.out.println(url + " " + username + " " + password);
        //System.out.println(DriverManager.getConnection(url, username, password));
=======
>>>>>>> main
        return DriverManager.getConnection(url, username, password);
    }

}
