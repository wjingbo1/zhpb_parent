package com.stdu.zhpb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Employee extends BaseEntity{

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;

    private String email;

    private String position;

    private String storeId;

    private String store;

    private int status;
}
