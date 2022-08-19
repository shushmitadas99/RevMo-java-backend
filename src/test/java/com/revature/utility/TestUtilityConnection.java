package com.revature.utility;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;

    public class TestUtilityConnection {
        public TestUtilityConnection() {
        }

        @Test
        public void test_getConnection() throws SQLException {
            ConnectionUtility.createConnection();
        }
    }


