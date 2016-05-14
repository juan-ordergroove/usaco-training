/*
ID: jmg20482
LANG: C
TASK: nocows
*/
#include <stdio.h>
#include <stdlib.h>

static const int MODULO_VALUE = 9901;

int count_pedigrees(int nodes_at_level, int level);

int main() {
    int n, k, pedigrees;
    FILE *infile, *outfile;

    infile = fopen("nocows.in", "r");
    fscanf(infile, "%d %d", &n, &k);
    fclose(infile);

    n = n / 2;
    k = k - 1;
    printf("n=%d, k=%d\n", n, k);
    pedigrees = count_pedigrees(n, k) - count_pedigrees(n, k-1);

    outfile = fopen("nocows.out", "w");
    fprintf(outfile, "%d\n", pedigrees % MODULO_VALUE);
    fclose(outfile);
    return 0;
}

int count_pedigrees(int nodes_at_level, int level) {
    int subtree_nodes, p = 0;
    if (nodes_at_level == 0) {
        return 1;
    } else if (level == 1) {
        return (nodes_at_level == 1);
    }
    printf("level=%d\n", level);
    for (subtree_nodes=0; subtree_nodes < nodes_at_level; ++subtree_nodes) {
        p += count_pedigrees(subtree_nodes, level - 1) *
            count_pedigrees(nodes_at_level - subtree_nodes - 1, level - 1);
    }
    return p;
}
