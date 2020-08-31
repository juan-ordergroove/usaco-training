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
