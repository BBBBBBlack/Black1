package com.example.demo01.Mapper;

import com.example.demo01.Domain.Comment;
import com.example.demo01.Domain.VO.CommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    List<CommentVo> getComments(@Param("rootId") Long rootId,@Param("goodsId") Long goodsId);
    void addComment(Comment comment);
}
