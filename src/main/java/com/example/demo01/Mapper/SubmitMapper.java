package com.example.demo01.Mapper;

import com.example.demo01.Domain.Submit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubmitMapper {
    List<Submit> showYourSubmit(Long userId);
    int findSubmitted(@Param("goodsId") Long goodsId, @Param("userId") Long userId);
    //找到可以修改的提交
    int findAbleSubmitted(@Param("goodsId") Long goodsId, @Param("userId") Long userId);
    void delSubmitted(@Param("goodsId") Long goodsId, @Param("userId") Long userId);
}
