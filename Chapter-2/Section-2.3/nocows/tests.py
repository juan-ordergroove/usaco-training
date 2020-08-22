import unittest

from .nocows import nocows


class NocowsTestCase(unittest.TestCase):
    def test_no_trees_of_zero_height_can_computed_with_more_nodes_than_zero(self):
        self.assertEqual(0, nocows(1, 0))
        self.assertEqual(0, nocows(2, 0))

    def test_there_is_only_one_tree_of_zero_height_with_zero_nodes(self):
        self.assertEqual(1, nocows(0, 0))

    def test_only_one_tree_exists_for_any_height_with_exactly_zero_nodes(self):
        self.assertEqual(1, nocows(0, 1))
        self.assertEqual(1, nocows(0, 2))
