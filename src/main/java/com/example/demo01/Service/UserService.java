package com.example.demo01.Service;

import com.example.demo01.Domain.Result;
import com.example.demo01.Domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Result login(User user);
    Result logout();
    Result register(String userName,String password);
    Result showUserMessage();
    Result addProfilePicture(MultipartFile file);
    Result addUserMessage(User user);
}
