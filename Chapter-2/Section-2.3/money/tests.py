import unittest

from .money import MoneySystem


class MoneySystemsTestCase(unittest.TestCase):
    def test_there_are_no_ways_to_construct_a_value_when_all_coins_are_larger_than_the_target_value(self):
        coins = [10, 20, 30]
        target_value = 1
        money_sys = MoneySystem(target_value, len(coins), coins)
        self.assertEqual(0, money_sys.count_combinations())

    def test_sample_input(self):
        coins = [1, 2 ,5]
        target_value = 10
        money_sys = MoneySystem(target_value, len(coins), coins)
        self.assertEqual(10, money_sys.count_combinations())

    def test_simple_example(self):
        coins = [2, 10]
        target_value = 18
        money_sys = MoneySystem(target_value, len(coins), coins)
        self.assertEqual(2, money_sys.count_combinations())

    def test_one_coin_that_is_not_a_factor_of_the_target_value_cannot_produce_the_target_value(self):
        coins = [10]
        target_value = 18
        money_sys = MoneySystem(target_value, len(coins), coins)
        self.assertEqual(0, money_sys.count_combinations())

    def test_one_coin_that_is_a_factor_of_the_target_value_can_only_produce_the_value_in_one_way(self):
        coins = [10]
        for k in range(1, 10):
            money_sys = MoneySystem(k * coins[0], len(coins), coins)
            self.assertEqual(1, money_sys.count_combinations())

    def test_coins_that_share_no_factors_with_the_target_value_or_the_remainders_cannot_produce_the_target_value(self):
        coins = [2, 4, 6, 8, 10, 12, 14, 16]
        target_value = 17
        money_sys = MoneySystem(target_value, len(coins), coins)
        self.assertEqual(0, money_sys.count_combinations())

    #def test_upper_bound_variant(self):
    #    coins = [i for i in range(1, 26)]
    #    target_value = 10000
    #    money_sys = MoneySystem(target_value, len(coins), coins)
    #    money_sys.count_combinations()
