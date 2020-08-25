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
        if any([c <= n for c in self.coins]):
            self._break(n)
        return self.count

    def _break(self, n, i=0, calc=0):
        for j in range(i, self.v):
            if calc + self.coins[j] == n:
                self.count += 1
                return
            if calc + self.coins[j] > n:
                return
            self._break(n, j, calc + self.coins[j])
