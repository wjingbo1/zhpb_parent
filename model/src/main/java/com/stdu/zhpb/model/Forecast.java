package com.stdu.zhpb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
/**
 * 客流量预测实体类
 */
public class Forecast extends BaseEntity{

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String storeId;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date dateTime;

    private String beginTime ;

    private String endTime;

    private int customerFlow;
}
