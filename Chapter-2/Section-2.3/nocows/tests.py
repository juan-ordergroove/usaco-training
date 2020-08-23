import unittest

from .nocows import nocows, Tree, MAX_NODES


class NocowsTestCase(unittest.TestCase):
    def test_no_trees_of_zero_height_can_computed_with_more_nodes_than_zero(self):
        self.assertEqual(0, Tree(1, 0).count())
        self.assertEqual(0, Tree(2, 0).count())

    def test_there_is_only_one_tree_of_zero_height_with_zero_nodes(self):
        self.assertEqual(1, Tree(0, 0).count())

    def test_only_one_tree_exists_for_any_height_with_exactly_zero_nodes(self):
        self.assertEqual(1, Tree(0, 1).count())
        self.assertEqual(1, Tree(0, 2).count())

    def test_no_trees_exist_when_nodes_exceeds_max_capacity_for_a_given_height(self):
        k = 3
        self.assertEqual(0, Tree(MAX_NODES(k) + 4, k).count())

    def test_sample_use_cases(self):
        self.assertEqual(2, nocows(5, 3))
        self.assertEqual(6, nocows(9, 4))
        self.assertEqual(6, nocows(11, 4))
        self.assertEqual(5024, nocows(35, 7))
        self.assertEqual(0 , nocows(172, 44))
