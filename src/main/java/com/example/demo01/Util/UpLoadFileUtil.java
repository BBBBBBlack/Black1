package com.example.demo01.Util;

import com.example.demo01.Domain.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UpLoadFileUtil {
    public static String upLoad(MultipartFile file, String realPath, String virPath){
        String originalFilename = file.getOriginalFilename();
        //后缀名
        String prefix = originalFilename.substring(originalFilename.lastIndexOf(".") );
        //新文件名
        String newFileName= UUID.randomUUID().toString().replace("-","")+prefix;
        String realUrl=realPath+newFileName;
        String virUrl=virPath+newFileName;
        File newFile=new File(realUrl);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return virUrl;
    }
}
