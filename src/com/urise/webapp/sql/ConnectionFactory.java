package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection getconnection() throws SQLException;
}
