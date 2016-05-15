/*
ID: jmg20482
LANG: C
TASK: nocows
*/
#include <stdio.h>
#include <stdlib.h>

int **SOLUTIONS;
static const int MODULO_VALUE = 9901;

int count_pedigrees(int nodes_at_level, int level);

int main() {
    int i, j, n, k;
    int p1, p2, pedigrees;
    FILE *infile, *outfile;

    infile = fopen("nocows.in", "r");
    fscanf(infile, "%d %d", &n, &k);
    fclose(infile);

    printf("n=%d, k=%d\n", n, k);
    SOLUTIONS = malloc(sizeof(int*) * n+1);
    for (i=0; i <= n; ++i) {
        SOLUTIONS[i] = malloc(sizeof(int) * k+1);
        for (j=0; j <= k; ++j) {
            SOLUTIONS[i][j] = -1;
        }
    }
    n = n / 2;
    k = k - 1;
    p1 = count_pedigrees(n, k) % MODULO_VALUE;
    p2 = count_pedigrees(n, k-1) % MODULO_VALUE;
    printf("(n,k)=%d, (n,k-1)=%d, (n,k)-(n,k-1)=%d\n",
        p1, p2, p1 - p2);
    pedigrees = p1 - p2;

    outfile = fopen("nocows.out", "w");
    fprintf(outfile, "%d\n", pedigrees % MODULO_VALUE);
    fclose(outfile);

    free(SOLUTIONS);
    return 0;
}

int count_pedigrees(int nodes_at_level, int level) {
    int subtree_nodes, left_subtrees, right_subtrees, p = 0;
    if (SOLUTIONS[nodes_at_level][level] != -1) {
        return SOLUTIONS[nodes_at_level][level];
    }
    if (nodes_at_level == 0) {
        return 1;
    } else if (level == 0) {
        return 0;
    }

    for (subtree_nodes=0; subtree_nodes < nodes_at_level; ++subtree_nodes) {
        left_subtrees = count_pedigrees(subtree_nodes, level - 1);
        right_subtrees = count_pedigrees(nodes_at_level - subtree_nodes - 1, level - 1);
        p += (left_subtrees * right_subtrees);
    }
    SOLUTIONS[nodes_at_level][level] = p % MODULO_VALUE;
    return SOLUTIONS[nodes_at_level][level];
}
