"""
ID: jmg20482
LANG: PYTHON3
TASK: concom
"""

import collections


class ControllingCompanies:
    def __init__(self):
        self._company_data = collections.defaultdict(dict)
        self.controlled_by = collections.defaultdict(list)

    def add_company_data(self, controller, controlee, percentage):
        self._company_data[controller].update({controlee: percentage})
        self._add_percentage_to_controlling_companies(controller, controlee, percentage)

        if percentage > 50:
            self.controlled_by[controlee].append(controller)

    def _add_percentage_to_controlling_companies(self, controller, controlee, percentage):
        for parent_controller in self.controlled_by.get(controller, []):
            p = self._company_data[parent_controller].get(controlee, 0)
            self._company_data[parent_controller].update({controlee: p + percentage})
            self._add_percentage_to_controlling_companies(parent_controller, controlee, percentage)

    def get_control_map(self):
        control_map = collections.defaultdict(set)
        for i in sorted(self._company_data.keys()):
            for j in sorted(self._company_data[i].keys()):
                if self._company_data[i][j] > 50:
                    control_map[i].add(j)
        return control_map


if __name__ == '__main__': # pragma: no cover
    cc = ControllingCompanies()

    with open('concom.in') as f:
        n = int(f.readline())

        for i in range(n):
            company_data = list(map(int, f.readline().split(' ')))
            cc.add_company_data(company_data[0], company_data[1], company_data[2])

    import pprint
    pprint.pprint(cc._company_data)
    with open('concom.out', 'w') as f:
        control_map = cc.get_control_map()
        output_map = collections.OrderedDict(sorted(control_map.items()))
        for controller, controlees in output_map.items():
            controlees = list(controlees)
            controlees.sort()
            for c in controlees:
                if c == controller:
                    continue
                f.write(f'{controller} {c}\n')
