"""
ID: jmg20482
LANG: PYTHON3
TASK: money
"""


class MoneySystem:
    def __init__(self, coins):
        self.v = len(coins)
        self.count = 0
        self.coins = coins
        self.coins.sort()

    def count_constructions(self, n):
        change_counter = [0] * (n + 1)
        change_counter[0] = 1

        for c in self.coins:
            for i in range(c, n + 1):
                change_counter[i] += change_counter[i - c]

        return change_counter[n]


if __name__ == '__main__': # pragma: no cover
    coins = []
    v = n = 0
    with open('money.in') as f:
        data = f.readline().split(' ')
        v, n = int(data[0]), int(data[1])

        coin_data = f.readline()
        while coin_data:
            coins.extend(map(int, coin_data.split(' ')))
            coin_data = f.readline()

    money_sys = MoneySystem(coins)
    with open('money.out', 'w') as f:
        f.write(f'{money_sys.count_constructions(n)}\n')
