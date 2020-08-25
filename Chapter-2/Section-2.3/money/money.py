"""
ID: jmg20482
LANG: PYTHON3
TASK: money
"""


class MoneySystem:
    def __init__(self, n, v, coins):
        self.n = n
        self.v = v
        self.counter = 0

        self.coins = coins
        self.coins.sort()

    def count_combinations(self):
        if not all([c > self.n for c in self.coins]):
            self._iter()
        return self.counter

    def _iter(self, i=0, calc=0):
        for j in range(i, self.v):
            if calc + self.coins[j] == self.n:
                self.counter += 1
                return
            if calc + self.coins[j] > self.n:
                return
            self._iter(j, calc + self.coins[j])
