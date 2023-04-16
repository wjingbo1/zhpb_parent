package com.stdu.zhpb.schedule;

import com.stdu.zhpb.model.Forecast;
import com.stdu.zhpb.schedule.mapper.ForecastMapper;
import com.stdu.zhpb.schedule.service.RuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountTest {

    @Resource
    private ForecastMapper forecastMapper;

    @Resource
    private RuleService ruleService;

    private Logger logger = LoggerFactory.getLogger(CountTest.class);

    @Test
    public void testCount() throws ParseException {
        String dateString = "2023/04/17";
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date date = dateFormat.parse(dateString);
        List<Integer> integers = ruleService.dayCount("1yVTqDvQU", dateString);
        logger.info("输出结果:"+integers.toString());
    }

    @Test
    public void updateSql(){
        for(int i = 754 ; i <= 1480 ; i++){
            Forecast forecast = new Forecast();
            forecast.setId(i);
            forecast.setStoreId("1yVTqDvQU");
            forecastMapper.updateById(forecast);
        }
    }
}
