package com.example.tea_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tea_demo.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userId);
}