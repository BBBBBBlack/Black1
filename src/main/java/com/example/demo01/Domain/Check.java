package com.example.demo01.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Check {
    private Long id;
    private Long goodsId;
    private Date checkTime;
    private Long userId;
    private int status;


}
