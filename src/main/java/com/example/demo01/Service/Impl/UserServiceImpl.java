package com.example.demo01.Service.Impl;
import com.example.demo01.Domain.LoginUser;
import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.User;
import com.example.demo01.Mapper.UserMapper;
import com.example.demo01.Service.UserService;
import com.example.demo01.Util.JwtUtil;
import com.example.demo01.Util.RedisCache;
import com.example.demo01.Util.SecurityUtil;
import com.example.demo01.Util.UpLoadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Value("${file.real-path}")
    private String realPath;
    @Value("${file.vir-path}")
    private String virPath;
    @Override
    public Result login(User user){
        //封装Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authentication)){
            throw new RuntimeException("用户名或密码错误");
        }

        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        if(loginUser.getUser().getStatus()==0){
            return new Result(HttpStatus.UNAUTHORIZED.value(),"账号已封禁，请联系管理员");
        }
        String userId=loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject("login:"+userId,loginUser);
        Map<String,String> map=new HashMap<>();
        map.put("token",jwt);
        return new Result(200,"登录成功",map);
    }

    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("login:"+userId);
        return new Result(200,"退出成功");
    }

    @Override
    public Result register(String userName, String password) {
        Integer count = userMapper.ifUserExist(userName);
        if(count!=0){
            return new Result(200,"用户名已存在");
        }
        String newPassword = bCryptPasswordEncoder.encode(password);
        userMapper.addUser(userName,newPassword);
        User user = userMapper.findUserByUserName(userName);
        Long userId = user.getId();
        userMapper.addRole(userId);
        return new Result(200,"注册成功，请重新登录");
    }

    @Override
    public Result showUserMessage() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        String userName = loginUser.getUser().getUserName();
        String nickName = loginUser.getUser().getNickName();
        String phoneNumber = loginUser.getUser().getPhoneNumber();
        String picture = loginUser.getUser().getPicture();
        User user=new User(null,userName,nickName,null,phoneNumber,picture,null);
        return  new Result(200,"返回用户信息成功",user);
    }

    @Override
    public Result addProfilePicture(MultipartFile file) {
        if(file.isEmpty()){
            return new Result(200,"文件为空");
        }
        String virUrl = UpLoadFileUtil.upLoad(file, realPath, virPath);
        if(virUrl==null||virUrl.length()<=0){
            return new Result(500,"上传失败");
        }
        Long userId = SecurityUtil.getNowUserId();
        userMapper.addProfilePicture(userId,virUrl);
        Map<String,String> map=new HashMap<>();
        map.put("proFileFileUrl",virUrl);
        return new Result(200,"头像添加成功",map);
    }
    @Override
    public Result addUserMessage(User user) {
        String nickName = user.getNickName();
        String phoneNumber = user.getPhoneNumber();
        Long userId = SecurityUtil.getNowUserId();
        if(nickName!=null&&nickName.length()!=0){
            userMapper.upDateNickName(nickName,userId);
        }
        if(phoneNumber!=null&&phoneNumber.length()!=0){
            userMapper.upDatePhoneNumber(phoneNumber,userId);
        }
        return new Result(200,"修改成功");
    }

}
