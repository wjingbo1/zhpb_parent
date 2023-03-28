package com.stdu.zhpb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
/**
 * 员工偏好实体类
 */
public class Predilection extends BaseEntity{

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String employeeId;

    private String employeeName;

    private String workDay;

    private String workHours;

    private int duration;

}
