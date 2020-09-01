import unittest

from .concom import ControllingCompanies


class ControllingCompaniesTestCase(unittest.TestCase):
    def test_no_control_state(self):
        cc = ControllingCompanies()
        cc.add_company_data(1, 2, 49)
        cc.add_company_data(2, 3, 49)
        self.assertEqual(0, len(cc.get_control_map()))

    def test_sample_input(self):
        cc = ControllingCompanies()
        cc.add_company_data(1, 2, 80)
        cc.add_company_data(2, 3, 80)
        cc.add_company_data(3, 1, 20)

        self.assertEqual(
            {
                1: [2, 3],
                2: [3]
            },
            cc.get_control_map()
        )

#    def test_multilayer_control(self):
#        cc = ControllingCompanies()
#        cc.add_company_data(1, 2, 30)
#        cc.add_company_data(2, 3, 52)
#        cc.add_company_data(3, 4, 51)
#        cc.add_company_data(4, 5, 70)
#        cc.add_company_data(5, 6, 70)
#        cc.add_company_data(6, 7, 70)
#
#        self.assertEqual(
#            {
#                2: {3, 4, 5, 6, 7},
#                3: {4, 5, 6, 7},
#                4: {5, 6, 7},
#                5: {6, 7},
#                6: {7}
#            },
#            cc.get_control_map()
#        )
#
#    def test_multilayer_control_over_multilayer_sums(self):
#        cc = ControllingCompanies()
#
#        # 1 controls {2}
#        cc.add_company_data(1, 2, 100)
#
#        # 2 controls {3}
#        # 1 controls {2, 3}
#        cc.add_company_data(2, 3, 51)
#
#        # 2 controls {3, 4}
#        # 1 contorls {2, 3, 4}
#        cc.add_company_data(2, 4, 34)
#        cc.add_company_data(3, 4, 34)
#
#        # 2 controls {3, 4, 5}
#        # 1 controls {2, 3, 4, 5}
#        cc.add_company_data(2, 5, 26)
#        cc.add_company_data(3, 5, 26)
#
#        # 2 controls {3, 4, 5, 6}
#        # 1 controls {2, 3, 4, 5, 6}
#        cc.add_company_data(2, 6, 21)
#        cc.add_company_data(3, 6, 21)
#        cc.add_company_data(4, 6, 21)
#
#        # 2 controls {3, 4, 5, 6, 7}
#        # 1 controls {2, 3, 4, 5, 6, 7}
#        cc.add_company_data(1, 7, 17)
#        cc.add_company_data(3, 7, 17)
#        cc.add_company_data(4, 7, 17)
#
#        self.assertEqual(
#            {
#                1: {2, 3, 4, 5, 6, 7},
#                2: {3, 4, 5, 6, 7}
#            },
#            cc.get_control_map()
#        )
