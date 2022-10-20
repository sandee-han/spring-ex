package com.springex.dao;

import com.springex.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    void addAndGet() throws SQLException {
        User user1 = new User("1", "ramen", "11213");
        User user2 = new User("2", "udon", "12412");
        User user3 = new User("3", "pasta", "1q2w3e");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        String id = "29";
        userDao.add(new User(id, "EternityHwan", "1234"));
        User user = userDao.findById(id);

        assertEquals("EternityHwan", user.getName());
        assertEquals("1234", user.getPassword());
    }

    @Test
    void count() throws SQLException {
        User user1 = new User("1", "ramen", "11213");
        User user2 = new User("2", "udon", "12412");
        User user3 = new User("3", "pasta", "1q2w3e");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());

    }

    @Test
    void findById() {
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.findById("30");
    }
}