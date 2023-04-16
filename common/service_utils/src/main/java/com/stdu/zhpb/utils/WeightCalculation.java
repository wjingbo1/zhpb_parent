package com.stdu.zhpb.utils;

/**
 * 获取字符串一和字符串二的重叠部分在2中的比例
 */
public class WeightCalculation {
    public static double getWeight(String str1, String str2) {
        // 解析字符串为数字范围
        int[] range1 = parseRange(str1);
        int[] range2 = parseRange(str2);
        // 计算重叠部分的长度
        int overlap = Math.min(range1[1], range2[1]) - Math.max(range1[0], range2[0]) + 1;
        int len_r1=range1[1]-range1[0]+1;
        int len_r2=range2[1]-range2[0]+1;
        //System.out.println(len_r1+" egg "+len_r2);
        double ratio = overlap / (double)len_r2;
//        System.out.println("数字范围1: " + range1[0] + "-" + range1[1]);
//        System.out.println("数字范围2: " + range2[0] + "-" + range2[1]);
//        System.out.println("重叠部分长度: " + overlap);
//        System.out.println("比例为: " + ratio);
        return ratio;
    }
    public static int[] parseRange(String str) {
        String[] parts = str.split("-");
        int start = Integer.parseInt(parts[0]);
        int end = Integer.parseInt(parts[1]);
        return new int[]{start, end};
    }
}