package com.example.demo01;

import com.example.demo01.Domain.Bill;
import com.example.demo01.Domain.User;
import com.example.demo01.Mapper.BillMapper;
import com.example.demo01.Mapper.MenuMapper;
import com.example.demo01.Mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootTest
class Demo01ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private BillMapper billMapper;
    @Test
    void contextLoads() {
//        User user = userMapper.findUserByUserName("abc");
//        System.out.println(user);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("abcdef");管理员密码
//        System.out.println(encode);
        boolean matches = bCryptPasswordEncoder.matches
                ("abcdef","$2a$10$a4d9WFnytsKcvOBSOBYrfO3vgsUisAxeoPlz3E9BSu4MQYmwEGBcy");
        System.out.println(matches);
    }

}
