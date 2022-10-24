package com.springex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SelectStrategy implements StatementStrategy{
    String id;

    public SelectStrategy(String id) {
        this.id = id;
    }


    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE id =?");
        pstmt.setString(1, id);
        return pstmt;
    }
}
