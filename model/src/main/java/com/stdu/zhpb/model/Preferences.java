package com.stdu.zhpb.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author 林健强
 * @Date 2023/3/30 15:57
 * @Description: TODO
 */
@Data
public class Preferences {
    private String id;
    private String name;
    private String position;
    private String storeId;
    private String workdayPreference; //工作日偏好
    private String workingPreferences;//工作时间偏好
    private Double durationPreference ; //工作时长偏好，仅限设置2 ,3, 4小时

    @TableField(exist = false)
    private Double workingWeight;
}
