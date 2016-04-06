/*
ID: jmg20482
LANG: C
TASK: skidesign
*/
#include <stdio.h>
#include <stdlib.h>

int N;
int *hills;

void print_hills() {
    int i;
    for (i=0; i < N; ++i) {
        printf("%d\n", hills[i]);
    }
}

int cmpfunc (const void * a, const void * b) {
   return ( *(int*)a - *(int*)b );
}

int main() {
    int i;
    FILE *infile;
    FILE *outfile;

    infile = fopen("skidesign.in", "r");
    fscanf(infile, "%d", &N);

    hills = malloc(N * sizeof(int));
    for (i=0; i < N; ++i) {
        fscanf(infile, "%d", &hills[i]);
    }
    fclose(infile);

    qsort(hills, N, sizeof(int), cmpfunc);
    print_hills();

    outfile = fopen("skidesign.out", "w");
    fclose(outfile);

    free(hills);
    return 0;
}
