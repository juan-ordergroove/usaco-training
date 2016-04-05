/*
ID: jmg20482
LANG: C
TASK: skidesign
*/
#include <stdio.h>
#include <stdlib.h>

int main() {
    int i, n;
    int *hills;
    FILE *infile;
    FILE *outfile;

    infile = fopen("skidesign.in", "r");
    fscanf(infile, "%d", &n);
    fclose(infile);

    hills = malloc(n * sizeof(int));
    for (i=0; i < n; ++i) {
        fscanf(infile, "%d", &hills[i]);
    }

    outfile = fopen("skidesign.out", "w");
    fclose(outfile);

    free(hills);
    return 0;
}
