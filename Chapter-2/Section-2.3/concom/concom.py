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
        return self.control
#        self.compute_implicit_control()
#        x = collections.defaultdict(list)
#        for controller, controlees in self.control.items():
#            x[controller].extend(self.control.get(controller, []))
#            for controlee in controlees:
#                x[controller].extend(self.control.get(controlee, []))
#
#            x[controller].sort()
#            x[controller] = set(x[controller])
#        return x
#
#    def compute_implicit_control(self):
#        implied_control = collections.defaultdict(set)
#        for controller, controlees in self.control.items():
#            for controlee in controlees:
#                for b, perc in self._company_data[controlee].items():
#                    controller_b_perc = self._company_data[controller].get(b, 0)
#                    self._company_data[controller].update({b: perc + controller_b_perc})
#
#                    if perc + controller_b_perc > 50:
#                        implied_control[controller].add(b)
#
#        for implied_controller, controlees in implied_control.items():
#            for c in controlees:
#                self.control[implied_controller].append(c)


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
