"""
ID: jmg20482
LANG: PYTHON3
TASK: money
"""


class MoneySystem:
    def __init__(self, target_value, coins):
        self.target = target_value
        self.coins = coins

    def count_combinations(self):
        if all([c > self.target for c in self.coins]):
            return 0
