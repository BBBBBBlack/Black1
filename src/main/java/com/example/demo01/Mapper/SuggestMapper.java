package com.example.demo01.Mapper;

import com.example.demo01.Domain.Suggest;
import com.example.demo01.Domain.VO.SuggestVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestMapper {
    List<SuggestVo> showSuggest(Long userId);
}
