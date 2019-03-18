package cn.xieyongjie.ccf.csp;

import java.util.Scanner;

public class ZT_201812_2_小明放学 {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int r = scanner.nextInt();
        int y = scanner.nextInt();
        int g = scanner.nextInt();
        int n = scanner.nextInt();
        long times = 0;
        for (int i = 0; i < n; i++) {
            int k = scanner.nextInt();
            int t = scanner.nextInt();

            int kNow;
            long tNow;
            if (k == 0) { // 经过了一段道路
                kNow = k; // 现在仍然是经过了一段道路
                tNow = t; // 耗时 t 秒
            } else {
                long x = (t - times) % (r + y + g); // 放学后走到红绿灯处，红绿灯的相对变化时间
                if (x > 0) { // 走到红绿灯时本轮倒计时仍未走完
                    kNow = k; // 仍是倒计时未走完之前的状态
                    tNow = x; // 减去路上消耗的时间
                } else if (k == 1) { // 本是红灯，下一个是绿灯，再下一个是黄灯
                    if (g + x > 0) { // 判断是否循环到绿灯
                        kNow = 3; // 绿
                        tNow = g + x; // 绿灯其实可以不计算
                    } else if (g + y + x > 0) { // 判断是否循环到黄灯
                        kNow = 2; // 黄
                        tNow = g + y + x;
                    } else { // 不是绿和黄，则肯定是红灯
                        kNow = 1; // 红
                        tNow = g + y + r + x;
                    }
                } else if (k == 2) { // 本是黄灯，下一个是红灯，再下一个是绿灯
                    if (r + x > 0) {
                        kNow = 1; // 红
                        tNow = r + x;
                    } else if (r + g + x > 0) {
                        kNow = 3; // 绿
                        tNow = r + g + x; // 绿灯其实可以不计算
                    } else {
                        kNow = 2; // 黄
                        tNow = r + g + y + x;
                    }
                } else { // 本是绿灯，下一个是黄灯，再下一个是红灯
                    if (y + x > 0) {
                        kNow = 2; // 黄
                        tNow = y + x;
                    } else if (y + r + x > 0) {
                        kNow = 1; // 红
                        tNow = y + r + x;
                    } else {
                        kNow = 3; // 绿
                        tNow = y + r + g + x; // 绿灯其实可以不计算
                    }
                }
            }

            if (kNow == 0 || kNow == 1) {
                times += tNow;
            } else if (kNow == 2) {
                times += tNow + r;
            }
        }
        System.out.println(times);
    }
}
