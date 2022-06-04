package com.example.demo01.Controller;

import com.example.demo01.Domain.Result;
import com.example.demo01.Service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/submit")
@RestController
public class SubmitController {
    @Autowired
    SubmitService submitService;
    //展示当前用户提交的商品
    @RequestMapping("/showYourSubmit")
    @PreAuthorize("hasAuthority('common:show_submit')")
    public Result showYourSubmit(){
        return submitService.showYourSubmit();
    }
}
