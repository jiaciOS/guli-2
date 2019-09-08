package com.atguigu.guli.statistics.service.impl;

import com.atguigu.guli.client.UcenterClient;
import com.atguigu.guli.statistics.entity.Daily;
import com.atguigu.guli.statistics.mapper.DailyMapper;
import com.atguigu.guli.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2019-09-04
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    private final UcenterClient ucenterClient;

    @Autowired
    public DailyServiceImpl(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }


    @Override
    public void createStatisticsByDay(String day) {
        //删除已存在的统计对象
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);


        //获取统计信息
        Integer registerNum = (Integer) ucenterClient.registerCount(day).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        // 根据起始时间和结束时间,以及字段类型,查找这些天对应的数据
        // 封装成一个map, key1: dateList, key2: dataList
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        // 指定: 只查询这两列
        queryWrapper.select(type, "date_calculated");
        queryWrapper.between("date_calculated", begin, end);
        List<Daily> dailies = baseMapper.selectList(queryWrapper);

        // 封装
        Map<String, Object> map = new HashMap<>();
        List<String> dateList = new LinkedList<>();
        List<Integer> dataList = new LinkedList<>();


        for (Daily daily : dailies) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        map.put("dateList", dateList);
        map.put("dataList", dataList);
        return map;
    }
}