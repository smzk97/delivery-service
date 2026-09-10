package com.smzk.deliveryservicecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private Object data;
    private String msg;

    public static Result Success(){
        return new Result(1,null,"成功");
    }
    public static Result Success(Object data){
        return new Result(1,data,"成功");
    }
    public static Result Failed(String msg){
        return new Result(0,null,msg);
    }
}
