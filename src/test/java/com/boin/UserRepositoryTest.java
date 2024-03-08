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
        System.out.println(res);
    }
}