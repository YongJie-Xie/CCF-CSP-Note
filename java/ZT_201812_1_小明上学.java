package cn.xieyongjie.ccf.csp;

import java.util.Scanner;

public class ZT_201812_1_小明上学 {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int r = scanner.nextInt();
        int y = scanner.nextInt();
        int g = scanner.nextInt();
        int n = scanner.nextInt();
        int times = 0;
        for (int i = 0; i < n; i++) {
            int k = scanner.nextInt();
            int t = scanner.nextInt();
            if (k == 0 || k == 1) {
                times += t;
            } else if (k == 2) {
                times += t + r;
            }
        }
        System.out.println(times);
    }
}
