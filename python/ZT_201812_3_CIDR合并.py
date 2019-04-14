class CIDR:
    __slots__ = ('__address', '__mask')

    def __init__(self, address, mask):
        self.__address = address
        self.__mask = mask

    @property
    def address(self):
        return self.__address

    @address.setter
    def address(self, address):
        self.__address = address

    @property
    def mask(self):
        return self.__mask

    @mask.setter
    def mask(self, mask):
        self.__mask = mask

    def __lt__(self, other):
        if self.__address == other.__address:
            return self.__mask < other.__mask
        return self.__address < other.__address

    def __str__(self):
        return F"{(self.__address >> 24) & 0xFF}.{(self.__address >> 16) & 0xFF}.{(self.__address >> 8) & 0xFF}." \
            F"{self.__address & 0xFF}/{self.__mask}"


ips = []
for _ in range(int(input())):
    ip = input()
    count, address, mask, length = 0, 0, -1, len(ip)
    for i in range(length):
        if ip[i] == '.':
            if i + 2 < length and '0' <= ip[i + 2] <= '9':
                if i + 3 < length and '0' <= ip[i + 3] <= '9':
                    address |= (100 * int(ip[i + 1]) + 10 * int(ip[i + 2]) + int(ip[i + 3])) << (24 - 8 * count)
                else:
                    address |= (10 * int(ip[i + 1]) + int(ip[i + 2])) << (24 - 8 * count)
            else:
                address |= int(ip[i + 1]) << (24 - 8 * count)
            count += 1
        elif ip[i] == '/':
            if i + 2 < length:
                mask = 10 * int(ip[i + 1]) + int(ip[i + 2])
            else:
                mask = int(ip[i + 1])
        elif i == 0:
            if i + 1 < length and '0' <= ip[i + 1] <= '9':
                if i + 2 < length and '0' <= ip[i + 2] <= '9':
                    address |= (100 * int(ip[i]) + 10 * int(ip[i + 1]) + int(ip[i + 2])) << (24 - 8 * count)
                else:
                    address |= (10 * int(ip[i]) + int(ip[i + 1])) << (24 - 8 * count)
            else:
                address |= int(ip[i]) << (24 - 8 * count)
            count += 1
    if mask == -1:
        mask = count * 8
    ips.append(CIDR(address, mask))

ips.sort()

i, length = 0, len(ips) - 1
while i < length:
    offset = 32 - min(ips[i].mask, ips[i + 1].mask)
    mask = -1 << offset
    temp = int(ips[i].address & mask)
    if temp == ips[i + 1].address & mask:
        ips[i].address = temp
        ips[i].mask = 32 - offset
        ips.pop(i + 1)
        length -= 1
        continue
    i += 1

i = 0
while i < length:
    if ips[i].mask != ips[i + 1].mask:
        i += 1
        continue
    offset = 33 - ips[i].mask
    mask = -1 << offset
    temp = int(ips[i].address & mask)
    if temp == ips[i + 1].address & mask:
        ips[i].address = temp
        ips[i].mask = 32 - offset
        ips.pop(i + 1)
        length -= 1
        if i != 0:
            i -= 1
        continue
    i += 1

for ip in ips:
    print(ip)
