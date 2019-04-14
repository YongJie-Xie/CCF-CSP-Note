package cn.xieyongjie.ccf.csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ZT_201812_3_CIDR合并4 {

    private static Scanner scanner = new Scanner(System.in);
    private static List<CIDR> cidrs = new ArrayList<>();

    public static void main(String[] args) {
        int i, count, max = scanner.nextInt(); // 下标，统计，最大值
        long startTime = System.currentTimeMillis();
        while (max-- > 0) {
            count = 0; // 清空
            char[] value = scanner.next().toCharArray();
            long address = 0L;
            int mask = -1;
            for (i = 0; i < value.length; i++) {
                if (value[i] == '.') {
                    if (i + 2 < value.length && '0' <= value[i + 2] && value[i + 2] <= '9') {
                        if (i + 3 < value.length && '0' <= value[i + 3] && value[i + 3] <= '9') {
                            address |= (long) (100 * (value[i + 1] - '0') + 10 * (value[i + 2] - '0') + (value[i + 3] - '0')) << (24 - 8 * count);
                            i += 2;
                        } else {
                            address |= (long) (10 * (value[i + 1] - '0') + (value[i + 2] - '0')) << (24 - 8 * count);
                            i += 1;
                        }
                    } else {
                        address |= (long) (value[i + 1] - '0') << (24 - 8 * count);
                    }
                    count++;
                } else if (value[i] == '/') {
                    if (i + 2 < value.length) {
                        mask = 10 * (value[i + 1] - '0') + (value[i + 2] - '0');
                    } else {
                        mask = (value[i + 1] - '0');
                    }
                    break;
                } else if (i == 0) {
                    if (i + 1 < value.length && '0' <= value[i + 1] && value[i + 1] <= '9') {
                        if (i + 2 < value.length && '0' <= value[i + 2] && value[i + 2] <= '9') {
                            address |= (long) (100 * (value[i] - '0') + 10 * (value[i + 1] - '0') + (value[i + 2] - '0')) << (24 - 8 * count);
                            i += 2;
                        } else {
                            address |= (long) (10 * (value[i] - '0') + (value[i + 1] - '0')) << (24 - 8 * count);
                            i += 1;
                        }
                    } else {
                        address |= (long) (value[i] - '0') << (24 - 8 * count);
                    }
                    count++;
                }
            }
            if (mask == -1) {
                mask = count * 8;
            }
            cidrs.add(new CIDR(address, mask));
        }
        Collections.sort(cidrs);

        // 合并与删除，90分
        count = cidrs.size() - 1;
        long a, mask;

        for (i = 0; i < count; ) {
            max = 32 - Math.min(cidrs.get(i).getMask(), cidrs.get(i + 1).getMask());
            mask = -1L << max; // 11111111 11111111 11111100 00000000 类似这种
            a = cidrs.get(i).getAddress() & mask; // 与运算
            if (a == (cidrs.get(i + 1).getAddress() & mask)) { // mask 的非零部分是否相同
                // 这两步有点6，输入值不规范也处理成规范的，若不操作，耗时时间会增加很多
                cidrs.get(i).setAddress(a);
                cidrs.get(i).setMask(32 - max);
                cidrs.remove(i + 1);
                count--;
                continue;
            }
            i++;
        }

        for (i = 0; i < count; ) {
            if (cidrs.get(i).getMask() != cidrs.get(i + 1).getMask()) {
                i++;
                continue;
            }
            max = 33 - cidrs.get(i).getMask();
            mask = -1L << max;
            a = cidrs.get(i).getAddress() & mask;
            if (a == (cidrs.get(i + 1).getAddress() & mask)) {
                cidrs.get(i).setAddress(a);
                cidrs.get(i).setMask(32 - max);
                cidrs.remove(i + 1);
                count--;
                if (i != 0) {
                    i--;
                }
                continue;
            }
            i++;
        }

        scanner.close();
        for (CIDR cidr : cidrs) {
            System.out.println(cidr);
        }
        System.out.println("run time = " + (System.currentTimeMillis() - startTime));
    }
}

class CIDR implements Comparable<CIDR> {
    private long address;
    private int mask;

    CIDR(long address, int mask) {
        this.address = address;
        this.mask = mask;
    }

    long getAddress() {
        return address;
    }

    void setAddress(long address) {
        this.address = address;
    }

    int getMask() {
        return mask;
    }

    void setMask(int mask) {
        this.mask = mask;
    }

    @Override
    public int compareTo(CIDR cidr) {
        if (this.address == cidr.address) {
            return Integer.compare(this.mask, cidr.mask);
        } else {
            return Long.compare(this.address, cidr.address);
        }
    }

    @Override
    public String toString() {
        return ((this.address >> 24) & 0xFF) + "." + ((this.address >> 16) & 0xFF) + "." + ((this.address >> 8) & 0xFF) + "." + (this.address & 0xFF) + "/" + this.mask;
    }
}
