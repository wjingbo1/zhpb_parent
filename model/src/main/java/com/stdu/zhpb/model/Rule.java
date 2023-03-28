package com.stdu.zhpb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
/**
 * 排版规则实体类
 */
public class Rule {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String storeId;

    private String storeName;

    private String open;

    private String close;

    private String passenger_flow;
}
