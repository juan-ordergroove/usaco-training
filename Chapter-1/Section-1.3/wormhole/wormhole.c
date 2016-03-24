#include <stdio.h>
#include <stdlib.h>

typedef struct Points {
    int x;
    int y;
} Point;

int main() {
    int i, j;
    Point *wormholes;
    FILE *infile;

    infile = fopen("wormhole.in", "r");
    fscanf(infile, "%d", &i);
    wormholes = malloc(i * sizeof(Point));

    for (j=0; j < i; ++j) {
        fscanf(infile, "%d %d", &wormholes[j].x, &wormholes[j].y);
    }

    fclose(infile);

    printf("%d\n", i);
    for (j=0; j < i; ++j) {
        printf("%d %d\n", wormholes[j].x, wormholes[j].y);
    }
    free(wormholes);
    return 0;
}
