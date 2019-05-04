package com.programan.cm.web.util;
import com.programan.cm.web.model.LatestTrend;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticsUtil {

    public static Map<String, Integer> calculateDayTotal(List<LatestTrend> list){
        if(list == null) {
            return null;
        }
        Map<String, Integer> result = new TreeMap<>(new Comparator<String>(){
            public int compare(String o1,String o2){
                return  o1.compareTo(o2); //用正负表示大小值
            }
        });
        String minDate = list.get(list.size() - 1).getVisitDate();
        String maxDate = list.get(0).getVisitDate();
        List<String> days = StatisticsUtil.getDays(minDate, maxDate);
        for (String day:days) {
            result.put(day, 0);
        }

        for(LatestTrend latestTrend: list) {
            String dataDate = latestTrend.getVisitDate().substring(0,10);
            result.put(dataDate, result.get(dataDate) + 1);
        }
        return result;
    }

    public static Map<String, Integer> calculateHoursTotal(List<LatestTrend> list){
        if(list == null) {
            return null;
        }
        Map<String, Integer> result = new TreeMap<>(new Comparator<String>(){
            public int compare(String o1,String o2){
                return  o1.compareTo(o2); //用正负表示大小值
            }
        });
        String[] totalhour = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
        for(int i = 0;i < totalhour.length - 1; i++){
            result.put(totalhour[i] + "-" + totalhour[i + 1], 0);
        }
        for(LatestTrend latestTrend: list) {
            String dataHour = latestTrend.getVisitDate().substring(11,13);
            String dataDate = latestTrend.getVisitDate().substring(0,10);
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd" );
            Date date = new Date();
            String nowDate = sdf.format(date);
            if(!nowDate.equals(dataDate)) {
                continue;
            }
            String key = dataHour + "-" + totalhour[StatisticsUtil.printArray(totalhour, dataHour) + 1];
            result.put(key, result.get(key) + 1);
        }
        return result;
    }

    public static List<String> getDays(String startTime, String endTime) { // 返回的日期集合
         List<String> days = new ArrayList<String>();
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
         try {
             Date start = dateFormat.parse(startTime);
             Date end = dateFormat.parse(endTime);
             Calendar tempStart = Calendar.getInstance();
             tempStart.setTime(start);
             Calendar tempEnd = Calendar.getInstance();
             tempEnd.setTime(end);
             tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
             while (tempStart.before(tempEnd)) {
                 days.add(dateFormat.format(tempStart.getTime()));
                 tempStart.add(Calendar.DAY_OF_YEAR, 1);
             }
         } catch (ParseException e) {
             e.printStackTrace(); } return days;
    }

    public static int printArray(String[] array, String value){
        for(int i = 0;i<array.length;i++){
            if(array[i].equals(value)){
                return i;
            }
        }
        return -1;//当if条件不成立时，默认返回一个负数值-1
    }

    public static void main(String[] args) {
        List<String> days = StatisticsUtil.getDays("2019/04/25", "2019/05/07");
        System.out.println(days);
    }


}
