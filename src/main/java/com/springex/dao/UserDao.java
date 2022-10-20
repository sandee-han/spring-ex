package com.springex.dao;

import com.springex.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDao {
    private ConnectionMaker cm;

    public UserDao(ConnectionMaker cm) {
        this.cm = cm;
    }

    public UserDao() {
        this.cm = new AwsConnectionMaker();
    }

    public void add(User user) {
        Map<String, String> env = System.getenv();
        try {
            // DB접속 (ex sql workbeanch실행)
            Connection c = cm.makeConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속 (ex sql workbeanch실행)
            c = cm.makeConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if(rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }
            rs.close();
            pstmt.close();
            c.close();

            if(user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() throws  SQLException {
        Connection c = cm.makeConnection();

        PreparedStatement ps = c.prepareStatement("DELETE FROM users");
        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = cm.makeConnection();

        PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }


    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}
