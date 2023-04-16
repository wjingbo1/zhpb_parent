package com.stdu.zhpb.schedule;

import com.stdu.zhpb.model.EmployeeSchedulingResult;
import com.stdu.zhpb.schedule.mapper.SchedulingResultMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description:
 * @author: w_jingbo
 * @date: 2023/4/10
 * @Copyright: 博客：http://coisini.wang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedulingResultTest {

    @Autowired
    private SchedulingResultMapper schedulingResultMapper;

    @Test
    public void test1(){
        String date = "2023/03/20";
        List<EmployeeSchedulingResult> byCurrentDate = schedulingResultMapper.getByCurrentDate(date);
        System.out.println(byCurrentDate);
    }

    @Test
    public void test2(){
        String date = "2023/03/20";
        List<EmployeeSchedulingResult> byEmployeeId = schedulingResultMapper.getByEmployeeId("9", date);
        System.out.println(byEmployeeId);
    }

    @Test
    public void test3(){
        EmployeeSchedulingResult result = new EmployeeSchedulingResult();
        result.setName("12312das");
        schedulingResultMapper.addSchedulingResult(result);

    }

    @Test
    public void test4(){
       schedulingResultMapper.updateCurrentDayDuration("9",100L,"2023/03/20");

    }
}
