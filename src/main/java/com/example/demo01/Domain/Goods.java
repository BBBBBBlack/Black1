package com.example.demo01.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private Long id;//商品id
    private String goodsName;//商品名
    private String introduction;//商品描述
    private Integer price;//商品价格
    private Integer number;//商品数量
    private String goodsPicture;//商品图片
    private Integer goodsStatus;//商品状态：1为正常，0为未审核，-1为审核未通过
}
