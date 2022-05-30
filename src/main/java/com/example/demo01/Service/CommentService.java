package com.example.demo01.Service;

import com.example.demo01.Domain.Comment;
import com.example.demo01.Domain.Result;

public interface CommentService {
    Result getComments(Long goodsId);
    Result addComment(Comment comment);
}
