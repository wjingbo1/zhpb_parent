package com.stdu.zhpb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Store extends BaseEntity{

    //@TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;

    private String address;

    private double size;

    private int status;


}
