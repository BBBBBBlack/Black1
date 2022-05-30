package com.example.demo01.Mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MenuMapper {
    List<String> findPermsByUserId(Long userId);
}
