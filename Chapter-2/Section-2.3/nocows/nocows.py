"""
ID: jmg20482
LANG: PYTHON3
TASK: nocows
"""

MAX_NODES = lambda k: 2**k - 1


class Tree:
    def __init__(self, n, k, counts=None):
        self.n = n
        self.k = k
        self.counts = counts or {}

    def count(self, n=None, k=None):
        if n is None:
            n = self.n
        if k is None:
            k = self.k

        self._check_counter(n, k)
        if self.counts[n].get(k) is not None:
            return self.counts[n][k]

        if n == 0:
            self.counts[n][k] = 1
        elif n > MAX_NODES(k):
            self.counts[n][k] = 0
        else:
            self.counts[n][k] = sum([
                self.count(m, k - 1) * self.count(n - m - 1, k - 1)
                for m in range(n)
            ])
 
        return self.counts[n][k]

    def _check_counter(self, n, k):
        if self.counts.get(n) is None:
            self.counts[n] = {}


def nocows(n, k):
    # If all nodes have 0 or 2 children, the total number of
    # nodes must be odd
    if n % 2 == 0:
        return 0

    reduced_n = int((n - 1) / 2)
    reduced_k = k - 1

    # Tree counts with N nodes up to height K
    t1 = Tree(reduced_n, reduced_k)
    count1 = t1.count()

    # Tree counts with N nodes up to height K - 1
    t2 = Tree(reduced_n, reduced_k - 1, counts=t1.counts)
    count2 = t2.count()

    # Difference = Tree counts of Nodes with EXACTLY height K
    return (count1 - count2) % 9901


if __name__ == '__main__': # pragma: no cover
    n = k = 0
    with open('nocows.in') as f:
        data = f.readline().split(' ')
        n, k = int(data[0]), int(data[1])

    with open('nocows.out', 'w') as f:
        f.write(f'{nocows(n, k)}\n')
