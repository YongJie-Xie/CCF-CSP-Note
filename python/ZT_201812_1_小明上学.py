r, y, g = map(int, input().split())
n = eval(input())
times = 0
for i in range(n):
    k, t = map(int, input().split())
    if k == 0 or k == 1:
        times += t
    elif k == 2:
        times += t + r
print(times)
