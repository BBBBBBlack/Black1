package com.example.test01_coll.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.test01_coll.mapper.GroupMapper;
import com.example.test01_coll.mapper.UserMapper;
import com.example.test01_coll.pojo.Group;
import com.example.test01_coll.pojo.User;
import com.example.test01_coll.utils.EmailUtil;
import com.example.test01_coll.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    GroupMapper groupMapper;

    public boolean loginConfirm(String email,String password){

        User user = userMapper.selectById(email);

        if(user==null){
            return false;
        }

        return user.getPassword().equals(SaSecureUtil.md5(password));

    }

    public ResultUtil login(String email, String password){
        ResultUtil resultUtil =new ResultUtil(200,"登录成功");

        Map<String,Object> map=new HashMap<>();

        boolean isSuccess=loginConfirm(email,password);

        if(isSuccess){
            StpUtil.login(email,true);

            SaTokenInfo info = StpUtil.getTokenInfo();

            User user = userMapper.selectById(email);

            map.put("tokenValue",info.tokenValue);

            map.put("userMail",user.getEmail());

            map.put("userName",user.getName());

        }else{
            resultUtil.setCode(500);

            resultUtil.setMsg("登录失败,账号或密码错误");
        }

        resultUtil.setData(map);

        return resultUtil;
    }

    public ResultUtil logon(String email, String password, String code){
        if(userMapper.selectById(email)!=null){
            return new ResultUtil(500,"该邮箱已注册");
        }
        Jedis jedis = new Jedis("47.113.216.236",6379);

        jedis.auth("147258");

        String msg = jedis.get(email);

        if(msg==null){
            return new ResultUtil(500,"验证码已过期");
        }else{
            if(!msg.equals(code)) {
                return new ResultUtil(500,"验证码错误");
            }
        }

        userMapper.insert(new User(email, SaSecureUtil.md5(password),email,false));

        File file=new File("/buckets/"+email);

        boolean mkdirs = file.mkdirs();

        file.setWritable(true,false);

        file.setReadable(true,false);

        file.setExecutable(true,false);

        System.out.println("个人bucket仓库建立"+mkdirs);

        return new ResultUtil(200,"注册成功");
    }

    public boolean updatePassword(String oldPassword, String newPassword){
        String email=StpUtil.getLoginId().toString();

        if(loginConfirm(email,oldPassword)){
            UpdateWrapper<User> wrapper=new UpdateWrapper<>();

            wrapper.set("password",SaSecureUtil.md5(newPassword))
                    .eq("email",email);

            userMapper.update(null,wrapper);

            return true;
        }else{
            return false;
        }
    }

    public void updateName(String newName){
        UpdateWrapper<User> wrapper=new UpdateWrapper<>();

        wrapper.set("name",newName)
                .eq("email",StpUtil.getLoginId().toString());

        userMapper.update(null,wrapper);
    }
    public ResultUtil sendEmail(String email){
        boolean isOk=true;

        Jedis jedis = new Jedis("47.113.216.236",6379);

        jedis.auth("147258");

        StringBuilder nums = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            Random random = new Random();

            int i1 = random.nextInt(10);

            nums.append(i1);
        }

        try {
            jedis.setex(email,600, nums.toString());

            isOk= EmailUtil.emailSend(email, "您正在使用文件管理系统，验证码为："+nums.toString()+"，10分钟后失效");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int code=200;

        String msg="验证码已发送";
        if(!isOk){
            msg="此邮箱不存在";

            code=500;
        }

        return new ResultUtil(code,msg);
    }


    public ResultUtil changeEmail(String newEmail, String code){
        if(userMapper.selectById(newEmail)!=null){
            return new ResultUtil(500,"该邮箱已被使用");
        }
        Jedis jedis = new Jedis("47.113.216.236",6379);

        jedis.auth("147258");

        String msg = jedis.get(StpUtil.getLoginId().toString());

        if(msg==null){
            return new ResultUtil(500,"验证码已过期");
        }else{
            if(!msg.equals(code)) {
                return new ResultUtil(500,"验证码错误");
            }
        }

        UpdateWrapper<User> wrapper=new UpdateWrapper<>();

        wrapper.set("email",newEmail)
                .eq("email",StpUtil.getLoginId().toString());

        userMapper.update(null,wrapper);

        File fileName=new File("/buckets/"+StpUtil.getLoginId().toString());

        fileName.renameTo(new File("/buckets/"+newEmail));

        return new ResultUtil(200,"修改成功");
    }

    public ResultUtil judgeUser(int bucketId, String email){
        User user = userMapper.selectById(email);

        if(user!=null){
            QueryWrapper<Group> wrapper=new QueryWrapper<>();

            wrapper.eq("bucket_id",bucketId)
                    .eq("user_id",email);

            Group group = groupMapper.selectOne(wrapper);

            if(group!=null){
                return new ResultUtil(500,"该用户已于此bucket中");
            }

            Map<String,String> map=new HashMap<>();

            map.put("userMail",user.getEmail());

            map.put("userName",user.getName());

            ResultUtil resultUtil =new ResultUtil(200,"改用户存在，且未添加");

            resultUtil.setData(map);

            return resultUtil;
        }else{
            return new ResultUtil(500,"该用户不存在");
        }
    }
}
