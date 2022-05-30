package com.example.demo01.Service.Impl;

import com.example.demo01.Domain.LoginUser;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.Submit;
import com.example.demo01.Mapper.SubmitMapper;
import com.example.demo01.Service.SubmitService;
import com.example.demo01.Util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    SubmitMapper submitMapper;
    @Override
    public Result showYourSubmit() {
        Long userId = SecurityUtil.getNowUserId();
        List<Submit> submitList = submitMapper.showYourSubmit(userId);
        return new Result(200,"查询提交商品成功",submitList);
    }
}
