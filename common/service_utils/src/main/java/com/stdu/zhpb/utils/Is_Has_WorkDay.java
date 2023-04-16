package com.stdu.zhpb.utils;

import java.util.Arrays;

/**
 * @Author 林健强
 * @Date 2023/3/30 21:05
 * @Description: TODO
 */
public class Is_Has_WorkDay {
    public static boolean  is_Has_WorkDay(String str, String numStr) {
        String[] parts = str.split(",");
        return Arrays.stream(parts).anyMatch(n -> n.equals(numStr));
    }
}