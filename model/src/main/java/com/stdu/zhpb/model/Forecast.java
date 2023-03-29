package com.stdu.zhpb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * 客流量预测实体类
 */
@Data
public class Forecast extends BaseEntity{


    private int id;

    private String storeId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;

    private String beginTime ;

    private String endTime;

    private double customerFlow;
}
