package com.stdu.zhpb.utils;

import java.util.Arrays;

/**
 * @Author 林健强
 * @Date 2023/4/10 9:27
 * @Description: TODO
 */
public class PersonTimeWeekend {
    public static int[][] generateSchedule(Integer[] n) {
        int[][] A = new int[30][30];
        int index = 0;
        for (int i = 0; i < n.length; i++) {
            for (int j = index; j < 20; j++) {
                for (int o = 0; o < 20; o++) {
                    if(i==0){

                    }
                    else if (A[o][i - 1] != 0 && A[o][i - 1] < 2) {
                        A[o][i] = A[o][i - 1] + 1;
                    }
                }

                int finalI = i;
                if (Arrays.stream(Arrays.copyOfRange(A, index, 20))
                        .mapToInt(row -> row[finalI] >= 1 ? 1 : 0)
                        .sum() < n[i]) {
                    if (i == 0) {
                        A[j][i] = 1;
                    } else {
                        if (A[j][i - 1] == 0 && Arrays.stream(A[j]).sum() != 0) {
                            A[j][i] = A[j][i];
                        } else {
                            A[j][i] = A[j][i - 1] + 1;
                        }
                        if (A[j][i] > 4) {
                            A[j][i] = 0;
                            index = index + 1;
                            continue;
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return A;
    }

    public static int[][][] convertToArr(int[][] A) {
        int[][][] Arr = new int[A[0].length + 4][A[0].length + 4][5];
        for (int i = 0; i < Arrays.stream(A).filter(row -> Arrays.stream(row).sum() > 0).count(); i++) {
            int pro = 0;
            int pre = 0;
            int ij = 0;
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] != 0) {
                    pro = j;
                    ij = j;
                    break;
                }
            }
            if (A[i][pro + 3] != 0) {
                pre = pro + 3;
            } else if (A[i][pro + 2] != 0) {
                pre = pro + 2;
            } else {
                pre = pro + 1;
            }
            Arr[pro][pre][pre - pro + 1] += 1;
        }
        return Arr;
    }
    public static int [][] getArrangeTime(Integer[] n) {
        int[][] A = generateSchedule(n);
        for (int i=0;i<=20;i++)
            for(int j=0;j<=20;j++)
            {
                System.out.print(A[i][j]+" ");
                if(j==20)
                    System.out.println();
            }
        int[][][] Arr = convertToArr(A);
        System.out.println(Arr[1][1][1]);
        //System.out.println(Arrays.deepToString(Arr));
        int f[][]=new int[10010][10010];
        for(int i=0;i<=28;i++)
            for(int j=0;j<=28;j++){
                for(int k=2;k<=4;k++){
                    if(Arr[i][j][k]>0)
                        f[i+8][j+8]=Arr[i][j][k];
                }
            }
        for(int i=0;i<150;i++)
            for(int j=0;j<150;j++){
                if(f[i][j]>0)
                    System.out.println("在 "+ String.valueOf(i)+"  "+String.valueOf(j)+" 时间段工作的人数有 "+String.valueOf(f[i][j])+"人");
            }
        return f;
    }
}
