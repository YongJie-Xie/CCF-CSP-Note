r, y, g = map(int, input().split())
n = eval(input())
times = 0
for i in range(n):
    k, t = map(int, input().split())

    kNow = 0
    tNow = 0
    if k == 0:
        kNow = k
        tNow = t
    else:
        x = (t - times) % (r + y + g) if t - times > 0 else (t - times) % (r + y + g) - (r + y + g)
        print('x', x)
        if x > 0:
            kNow = k
            tNow = x
        elif k == 1:
            if g + x > 0:
                kNow = 3
                tNow = g + x
            elif g + y + x > 0:
                kNow = 2
                tNow = g + y + x
            else:
                kNow = 1
                tNow = g + y + r + x
        elif k == 2:
            if r + x > 0:
                kNow = 1
                tNow = r + x
            elif r + g + x > 0:
                kNow = 3
                tNow = r + g + x
            else:
                kNow = 2
                tNow = r + g + y + x
        else:
            if y + x > 0:
                kNow = 2
                tNow = y + x
            elif y + r + x > 0:
                kNow = 1
                tNow = y + r + x
            else:
                kNow = 3
                tNow = y + r + g + x

    if kNow == 0 or kNow == 1:
        times += tNow
    elif kNow == 2:
        times += tNow + r
print(times)
