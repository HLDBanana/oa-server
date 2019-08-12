package com.hanergy.out.utils;

import com.hanergy.out.vo.OaMeetingDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ListUtils
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/14 9:07)
 */
public class ListUtils {

    //获取集合差集
    public static List<Long> chaji(List<Long> list1,List<Long> list2){
        if (list2 == null){
            return  null;
        }
        List<Long> list = new ArrayList<>();
        list.addAll(list1);
        for (int i = 0; i < list2.size(); i++){
            if (list1.contains(list2.get(i))){
                list.remove(list2.get(i));
            }
        }
        return list;
    }

    /*
     *  string[] 集合转换为List集合
     */
    public static List<String> stringToList(String[] s){
        List<String> list = new ArrayList<>();
        if (s != null && s.length > 0) {
            for (int i = 0; i < s.length; i++) {
                list.add(s[i]);
            }
        }
        return list;
    }

    /*
     *  string[] 集合转换为List集合
     */
    public static List<Long> stringToLongList(String[] s){
        List<Long> list = new ArrayList<>();
        if (s != null && s.length > 0) {
            for (int i = 0; i < s.length; i++) {
                if (!"".equals(s[i])) {
                    list.add(Long.parseLong(s[i]));
                }
            }
        }
        return list;
    }
    /*
     *  string[] 集合转换为List集合
     */
    public static List<String> stringToStringList(String[] s){
        List<String> list = new ArrayList<>();
        if (s != null && s.length > 0) {
            for (int i = 0; i < s.length; i++) {
                if (!"".equals(s[i])) {
                    list.add(s[i]);
                }
            }
        }
        return list;
    }

    public static boolean absoluteDiff(Long a,Long b){
        if (Math.abs(a.intValue() - b.intValue()) > 2){
            return false;
        }
        return true;
    }

    // 合并占用会议室数据
    public static List<OaMeetingDetail> dataIntegration(Map<String,List<String>> map){
        List<OaMeetingDetail> detailList = new ArrayList<>();
        for (String key:map.keySet()){
            List<String> values = map.get(key);
            Collections.sort(values);
            if (values !=null && values.size() > 0){
                String beginValue = values.get(0);
                String beginTime = beginValue.split("___")[1];
                String beginMeetingId = beginValue.split("___")[0];

                OaMeetingDetail detail = new OaMeetingDetail();
                detail.setBeginTime(beginTime);
                detail.setEndTime(DateUtils.dateToString(DateUtils.addDateMinutes(DateUtils.stringToDate(beginTime),30)));
                detail.setMeetingId(beginMeetingId);
                detail.setAppointmentsId(Long.parseLong(key));

                //计算时长
                int duration = 0;
                duration = (int) DateUtils.getMinutebetweenDate(detail.getBeginTime(),detail.getEndTime());
                detail.setDuration(duration);
                // 如果只有一个元素，直接添加到集合中去
                if (values.size() == 1){
                    detailList.add(detail);
                }
                for (int i = 1; i < values.size(); i++) {
                    String endTime = values.get(i).split("___")[1];
                    String endMeetingId = values.get(i).split("___")[0];
                    //String meetingId = beginValue.split("_")[0];
                    if (beginMeetingId.compareTo(endMeetingId) == 0 &&
                            detail.getEndTime().compareTo(endTime) == 0){ //0:meetingid  1:time
                        detail.setEndTime(DateUtils.dateToString(DateUtils.addDateMinutes(DateUtils.stringToDate(endTime),30)));
                        //计算时长
                        duration = (int) DateUtils.getMinutebetweenDate(detail.getBeginTime(),detail.getEndTime());
                        detail.setDuration(duration);
                    } else {
                        beginMeetingId = endMeetingId;
                        detailList.add(detail);
                        detail = new OaMeetingDetail();
                        detail.setBeginTime(endTime);
                        detail.setEndTime(DateUtils.dateToString(DateUtils.addDateMinutes(DateUtils.stringToDate(endTime),30)));
                        detail.setMeetingId(endMeetingId);
                        detail.setAppointmentsId(Long.parseLong(key));
                        //计算时长
                        duration = (int) DateUtils.getMinutebetweenDate(detail.getBeginTime(),detail.getEndTime());
                        detail.setDuration(duration);
                    }

                    if (i == values.size()-1){
                        detailList.add(detail);
                    }
                }
            }
        }
        return detailList;
    }



}
