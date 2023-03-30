package com.stdu.zhpb.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stdu.zhpb.model.Forecast;
import com.stdu.zhpb.model.Rule;
import com.stdu.zhpb.schedule.mapper.ForecastMapper;
import com.stdu.zhpb.schedule.mapper.RuleMapper;
import com.stdu.zhpb.schedule.service.RuleService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper,Rule> implements RuleService {
    @Resource
    private ForecastMapper forecastMapper;

    @Override
    public List<Integer> dayCount(String storeId , Date date) {
        List<Integer> res = new ArrayList<>();
        QueryWrapper<Rule> wrapper = new QueryWrapper<>();
        wrapper.eq("store_id",storeId);
        Rule rule = baseMapper.selectOne(wrapper);
        //查询开店规则
        String open = rule.getOpen();
        JSONObject jsonObject = JSON.parseObject(open);
        int time = Integer.parseInt((String)jsonObject.get("time")) ;
        String rule1 = (String)jsonObject.get("rule");
        //计算人数
        String[] arr = rule1.split("/");
        double size = Double.parseDouble(arr[0]);
        double devisor = Double.parseDouble(arr[1]);
        Double hc = Math.ceil(size / devisor);
        //添加到返回list
        int hcOpen = (int) Math.round(hc);;
        for(int i = time ; i > 0 ; i--){
            res.add(hcOpen);
        }
        //查询客流规则
        // 查询客流表得到每小时的客流量
        QueryWrapper<Forecast> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("date_time",date);

        List<Forecast> forecasts = forecastMapper.selectList(wrapper1);
        for(Forecast forecast : forecasts){
            System.out.println(forecast.toString());
        }
        int fSize = forecasts.size();
        Double passengerFlow = rule.getPassengerFlow();

        for(int i = 0 ; i < fSize - 1; i = i + 2){
            Double count = Math.max(Math.ceil(forecasts.get(i).getCustomerFlow() / passengerFlow) , 1.0);
            count = Math.max(count,Math.ceil(forecasts.get(i+1).getCustomerFlow() / passengerFlow));
            //Double count =Math.ceil((forecasts.get(i).getCustomerFlow() + forecasts.get(i+1).getCustomerFlow()) / passengerFlow);
            res.add((int) Math.round(count));
        }
        //关店规则
        String close = rule.getClose();
        JSONObject jsonObject1 = JSON.parseObject(close);
        int closeTime = Integer.parseInt((String)jsonObject1.get("time")) ;
        String closerule = (String)jsonObject1.get("rule");
        //计算人数
        String[] closeArr = closerule.split("/");
        double closeSize = Double.parseDouble(closeArr[0]);
        double closeDevisor = Double.parseDouble(closeArr[1]);
        Double closeHc = Math.ceil(closeSize / closeDevisor);
        //添加到返回list
        System.out.println("closeTime = " + closeTime);
        int hcClose =  (int) Math.round(closeHc);
        for(int i = closeTime ; i > 0 ; i--){
            res.add(hcClose);
        }
        return res;
    }
}
