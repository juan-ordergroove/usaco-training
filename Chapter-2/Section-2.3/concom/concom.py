"""
ID: jmg20482
LANG: PYTHON3
TASK: concom
"""

import collections


class ControllingCompanies:
    def __init__(self):
        self._company_data = collections.defaultdict(dict)
        self.control = collections.defaultdict(list)

    def add_company_data(self, controller, controlee, percentage):
        self._company_data[controller].update({controlee: percentage})

        if percentage > 50:
            self.control[controller].append(controlee)

    def get_control_map(self):
        control_map = collections.defaultdict(list)
        for controller, controlees in self.control.items():
            control_map[controller].extend(controlees)
            for controlee in controlees:
                control_map[controller].extend(self.control.get(controlee, []))
        return control_map


#if __name__ == '__main__': # pragma: no cover
#    cc = ControllingCompanies()
#
#    with open('concom.in') as f:
#        n = int(f.readline())
#
#        for i in range(n):
#            company_data = list(map(int, f.readline().split(' ')))
#            cc.add_company_data(company_data[0], company_data[1], company_data[2])
#
#    with open('concom.out', 'w') as f:
#        control_map = cc.get_control_map()
#        output_map = collections.OrderedDict(sorted(control_map.items()))
#        for controller, controlees in output_map.items():
#            controlees = list(controlees)
#            controlees.sort()
#            for c in controlees:
#                if c == controller:
#                    continue
#                f.write(f'{controller} {c}\n')
