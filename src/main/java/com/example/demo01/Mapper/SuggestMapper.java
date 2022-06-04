package com.example.demo01.Mapper;

import com.example.demo01.Domain.Suggest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestMapper {
    List<Suggest> showSuggest(Long goodsId);
}
