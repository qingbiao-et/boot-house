package com.etoak.bean;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class House {
    private Integer id;

    @NotNull(message = "省份必填")
    private Integer province;
    @NotNull(message = "市必填")
    private Integer city;
    @NotNull(message = "所在区必填")
    private Integer area;
    private String  areaName;
    //租赁方式
    @NotNull(message="租赁方式必填")
    @NotBlank(message = "租赁方式不能为空")
    private String rentMode;
    //朝向
    private String orientation;
    //户型
    private String houseType;
    //地址
    @Size(min=1,max=10,message="地址长度介于1-10个字符")
    @NotBlank(message = "地址必填")
    private String address;
    //房屋图片
    private String pic;
    //租金
    @NotNull(message = "租金必填")
    @Max(value=100000,message = "租金不能超过10万")
    @Min(value= 100,message = "租金不能少于100")
    private Integer rental;
    //发布时间
    private String publishTime;
}
