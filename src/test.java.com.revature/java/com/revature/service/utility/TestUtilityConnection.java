package com.revature.service.utility;
import com.revature.utility.ConnectionUtility;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


public class TestUtilityConnection {

        @Test
        public void test_getConnection() throws SQLException {
            ConnectionUtility.createConnection(); // If something is wrong with getting a connection,
            // a SQLException will be thrown
            // If any exception occurs inside of a test case, but it is not being handled, then the test automatically
            // fails
        }
}

