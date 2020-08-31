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
                1: {2, 3},
                2: {3}
            },
            cc.get_control_map()
        )

    def test_multilayer_control(self):
        cc = ControllingCompanies()
        cc.add_company_data(1, 2, 30)
        cc.add_company_data(2, 3, 52)
        cc.add_company_data(3, 4, 51)
        cc.add_company_data(4, 5, 70)
        cc.add_company_data(5, 6, 70)
        cc.add_company_data(6, 7, 70)

        print(cc.get_control_map())
        self.assertEqual(
            {
                2: {3, 4, 5, 6, 7},
                3: {4, 5, 6, 7},
                4: {5, 6, 7},
                5: {6, 7},
                6: {7}
            },
            cc.get_control_map()
        )
