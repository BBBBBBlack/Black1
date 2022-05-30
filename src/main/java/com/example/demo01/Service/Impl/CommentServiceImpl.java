package com.example.demo01.Service.Impl;

import com.example.demo01.Domain.Comment;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.VO.CommentVo;
import com.example.demo01.Mapper.CommentMapper;
import com.example.demo01.Service.CommentService;
import com.example.demo01.Util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public Result getComments(Long goodsId){
        //查询根评论
        List<CommentVo> rootComments = commentMapper.getComments(-1L,goodsId);
        for (CommentVo rootComment : rootComments) {
            List<CommentVo> comments = commentMapper.getComments(rootComment.getCommentId(), goodsId);
            rootComment.setCommentVos(comments);
        }
        return new Result(200,"评论查询成功",rootComments);
    }

    @Override
    public Result addComment(Comment comment) {
        Long userId = SecurityUtil.getNowUserId();
        comment.setCreateBy(userId);
        commentMapper.addComment(comment);
        return new Result(200,"评论添加成功");
    }
}
