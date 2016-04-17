/*
ID: jmg20482
LANG: C
TASK: nocows
*/
#include <stdio.h>
#include <stdlib.h>

int N, K, MAX_NODES;
typedef struct Nodes {
    struct Node *left;
    struct Node *right;
} Node;

int count_pedigrees();

int main() {
    Node *tree;
    FILE *infile, *outfile;

    infile = fopen("nocows.in", "r");
    fscanf(infile, "%d %d", &N, &K);
    fclose(infile);

    MAX_NODES = (1 << (K+1)) - 1;
    printf("N=%d, K=%d, MAX_NODES=%d\n", N, K, MAX_NODES);

    outfile = fopen("nocows.out", "w");
    fprintf(outfile, "%d\n", count_pedigrees());
    fclose(outfile);

    //free(tree);
    return 0;
}

int count_pedigrees() {
    int pedigrees = 0;
    if (N <= MAX_NODES) {
        
    }
    return pedigrees;
}
