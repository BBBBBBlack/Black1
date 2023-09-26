package com.example.test01_coll.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test01_coll.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
