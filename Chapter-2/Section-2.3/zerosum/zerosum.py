"""
ID: jmg20482
LANG: PYTHON3
TASK: zerosum
"""


class Zerosum:
    OPS = ['+', '-', ' ']

    def __init__(self, n):
        self.n = n
        self.solutions = []

    def solve(self, a=2, comp_seq='1', disp_seq='1'):
        if a > self.n:
            if eval(comp_seq) == 0:
                self.solutions.append(disp_seq)
            return

        for op in self.OPS:
            comp_seq_next = f'{comp_seq}{a}' if op == ' ' else f'{comp_seq}{op}{a}'
            self.solve(a + 1, comp_seq_next, f'{disp_seq}{op}{a}')

    def get_solutions(self):
        self.solutions.sort()
        return self.solutions


if __name__ == '__main__':
    n = None
    with open('zerosum.in') as f:
        n = int(f.readline())

    with open('zerosum.out', 'w') as f:
        z = Zerosum(n)
        z.solve()
        for s in z.get_solutions():
            f.write(f'{s}\n')
