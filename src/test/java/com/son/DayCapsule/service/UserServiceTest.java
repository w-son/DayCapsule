package com.son.DayCapsule.service;

import com.son.DayCapsule.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() throws Exception {
        // given
        User user = new User();
        user.setUsername("홍길동");
        user.setPassword(passwordEncoder.encode("1234"));
        // when
        Long id = userService.signup(user);
        // then
        User findUser = userService.findOne(id);
        assertEquals(user, findUser);
    }

}