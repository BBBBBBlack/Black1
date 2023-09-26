package com.example.test01_coll.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtil {

    int code;

    String msg;

    Object data;

    public ResultUtil(int code, String msg){
        this.code=code;

        this.msg=msg;

        data=null;
    }

}
