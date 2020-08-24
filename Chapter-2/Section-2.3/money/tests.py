import unittest

from .money import MoneySystem


class MoneySystemsTestCase(unittest.TestCase):
    def test_there_are_ways_to_construct_a_value_when_all_coins_are_larger_than_the_target_value(self):
        coins = [10, 20, 30]
        target_value = 1
        money_sys = MoneySystem(target_value, coins)
        self.assertEqual(0, money_sys.count_combinations())
