package com.example.demo01.Controller;

import com.example.demo01.Domain.Comment;
import com.example.demo01.Domain.Result;
import com.example.demo01.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @RequestMapping("/getComments")
    public Result getComments(@RequestBody Map<String,Long> map){
        Long goodsId = map.get("goodsId");
        return commentService.getComments(goodsId);
    }
    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
