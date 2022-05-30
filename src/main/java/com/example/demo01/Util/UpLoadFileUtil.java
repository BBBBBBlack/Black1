package com.example.demo01.Util;

import com.example.demo01.Domain.Result;
import com.example.demo01.MyException.NullFileException;
import com.example.demo01.MyException.WrongTypeException;
import com.example.demo01.MyException.WrongUrlException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Component
public class UpLoadFileUtil {
    private static String realPath;
    private static String virPath;
    @Value("${file.real-path}")
    public void setRealPath(String realPath) {
        UpLoadFileUtil.realPath = realPath;
    }
    @Value("${file.vir-path}")
    public void setVirPath(String virPath) {
        UpLoadFileUtil.virPath = virPath;
    }
    private static String getPrefix(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        //后缀名
        String prefix = originalFilename.substring(originalFilename.lastIndexOf(".") );
        return prefix;
    }
    private static String upLoad(MultipartFile file){
        String prefix = getPrefix(file);
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
    public static String upLoadProImag(MultipartFile file) throws Exception{
        if(file.isEmpty()){
            throw new NullFileException("文件为空");
        }
        String prefix = getPrefix(file);
        if(!prefix.equals(".jpg")&&!prefix.equals(".png")){
            throw new WrongTypeException(prefix+"不是允许上传的文件类型");
        }
        String virUrl = upLoad(file);
        if(virUrl==null||virUrl.length()<=0){
            throw new WrongUrlException("url值错误，上传失败");
        }
        return virUrl;
    }
}
