package com.stdu.zhpb.schedule;

import com.stdu.zhpb.model.Duration;
import com.stdu.zhpb.schedule.service.DurationService;
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
public class DurationTest {

    @Autowired
    private DurationService durationService;

    @Test
    public void testGetAll(){
        List<Duration> durationAll = durationService.getDurationAll();
        System.out.println(durationAll);
    }

    @Test
    public void testUpdate(){
        boolean updateduration = durationService.updateduration(10, "1");
        System.out.println(updateduration);
    }
}
