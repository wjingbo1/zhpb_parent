package com.stdu.zhpb.utils;

/**
 * @Author 林健强
 * @Date 2023/3/30 17:11
 * @Description: TODO
 */
public class ArrayUtils {
    /**
     * 判断三维数组是否为0
     * @param matrix 二维数组
     * @return true：数组全为0，false：数组中有非0元素
     */
    public static boolean isZeroMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                if (element != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
