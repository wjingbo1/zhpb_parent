package com.stdu.zhpb.model;

import lombok.Data;

/**
 * @Author 林健强
 * @Date 2023/3/30 19:37
 * @Description: TODO
 */
@Data
public class EmployeeSchedulingResult {
    private int id;
    private String employeeId;
    private String name;
    private String position;
    private String time;//格式2023-03-30-9:00-12:00
    private double singleDuration;//单次工作时长
    private double currentDayDuration;
    private String status;
}
