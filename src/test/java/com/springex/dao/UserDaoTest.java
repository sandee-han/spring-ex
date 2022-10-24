package com.springex.dao;

import com.springex.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;
    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
    }

    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();
        // user 1, 2, 3 가 픽스처
        this.user1 = new User("1", "ramen", "11213");
        this.user2 = new User("2", "udon", "12412");
        this.user3 = new User("3", "pasta", "1q2w3e");

        String id = "29";
        userDao.add(new User(id, "EternityHwan", "1234"));
        User user = userDao.findById(id);

        assertEquals("EternityHwan", user.getName());
        assertEquals("1234", user.getPassword());
    }
    @Test
    void count() throws SQLException {
        this.user1 = new User("1", "ramen", "11213");
        this.user2 = new User("2", "udon", "12412");
        this.user3 = new User("3", "pasta", "1q2w3e");

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
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.findById("1");
        });
    }

    @Test
    @DisplayName("없을때 빈 리스트 리턴 하는지, 있을때 개수만큼 리턴 하는지")
    void getAllTest() throws SQLException {
        userDao.deleteAll();
        List<User> users = userDao.getAll();
        assertEquals(0, users.size());
    }

}