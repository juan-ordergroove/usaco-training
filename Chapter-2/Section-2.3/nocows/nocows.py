"""
ID: jmg20482
LANG: PYTHON3
TASK: nowcows
"""

MAX_NODES = lambda k: 2**k - 1


class Tree:
    def __init__(self, n, k):
        self.n = n
        self.k = k
        self.counts = {}

    def count(self, n=None, k=None):
        if not n:
            n = self.n
        if not k:
            k = self.k

        self._check_counter(n, k)
        if self.counts[n].get(k) is not None:
            return self.counts[n][k]

        if n == 0:
            self.counts[n][k] = 1
        elif k == 0 or (n > MAX_NODES(k)):
            self.counts[n][k] = 0
        return self.counts[n][k]

    def _check_counter(self, n, k):
        if self.counts.get(n) is None:
            self.counts[n] = {}


def nocows(n, k):
    reduced_n = int((n - 1) / 2)
    reduced_k = k - 1
    #return tree_count(reduced_n, reduced_k) - tree_count(reduced_n, reduced_k - 1)
