package com.boin;

import com.boin.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllUserInfoTest() {
        var res = userRepository.getAllUsersInfo();
        System.out.println(res);
        Assertions.assertNotNull(res);
    }

    @Test
    void findByUsernameTest(){
        var res = userRepository.getUserByUserName("boin");
        Assertions.assertNotNull(res);
    }

    @Test
    void updateUserTest(){
        var res = userRepository.updateUserInfo("boin","aaa@gmail.com");
        System.out.println(res);
    }
}