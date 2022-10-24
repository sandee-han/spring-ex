package com.springex.dao;

import com.springex.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDao {
    private ConnectionMaker cm;

    private DataSource dataSource;  // DataSoucre를 의존하게 변경
    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource, JdbcContext jdbcContext) {
        this.dataSource = dataSource;   // 생성자도 변경
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserDao() {
        this.cm = new AwsConnectionMaker();
    }


    public void add(final User user) throws SQLException {
        jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?);",
                user.getId(), user.getName(), user.getPassword());
    }

    public User findById(String id) {
        String sql = "select * from users where id = ?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteAll() throws SQLException {
        //  Query만 넘기게 함
        jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("select count(*) from users;", Integer.class);
    }

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"));
            return user;

        }
    };

    public List<User> getAll() {
        String sql = "select * from users order by id";
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.findById("5");
        System.out.println(user.getName());
    }
}
