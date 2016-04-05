/*
ID: jmg20482
LANG: C
TASK: wormhole
*/
#include <stdio.h>
#include <stdlib.h>

typedef struct Wormholes{
    long x;
    long y;
    int paired_with;
} Wormhole;

typedef struct Farms {
    Wormhole *wormholes;
    int num_wormholes;
    int loops;
} Farm;

Farm farm;

int find_unpaired_wormhole();
int find_intersecting_wormhole(long x, long y);
int analyze_farm();
void generate_pairs(int to_pair_idx);
void print_farm();

int main() {
    int i;
    FILE *infile;
    FILE *outfile;

    farm.loops = 0;

    infile = fopen("wormhole.in", "r");
    fscanf(infile, "%d", &farm.num_wormholes);

    farm.wormholes = malloc(farm.num_wormholes * sizeof(Wormhole));
    for (i=0; i < farm.num_wormholes; ++i) {
        fscanf(infile, "%ld %ld", &farm.wormholes[i].x, &farm.wormholes[i].y);
        farm.wormholes[i].paired_with = -1;
    }
    fclose(infile);

    generate_pairs(0);

    outfile = fopen("wormhole.out", "w");
    fprintf(outfile, "%d\n", farm.loops);
    fclose(outfile);

    free(farm.wormholes);
    return 0;
}

void generate_pairs(int to_pair_idx) {
    int i;

    if (to_pair_idx == -1) {
        print_farm();
        farm.loops += analyze_farm();
        printf("\n");
        return;
    }

    for (i=to_pair_idx+1; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].paired_with == -1) {
            farm.wormholes[i].paired_with = to_pair_idx;
            farm.wormholes[to_pair_idx].paired_with = i;

            generate_pairs(find_unpaired_wormhole());

            /* Reset the states */
            farm.wormholes[i].paired_with = -1;
            farm.wormholes[to_pair_idx].paired_with = -1;
        }
    }
}

int analyze_farm() {
    long x, y;
    int i, start, next;
    for (start=0; start < farm.num_wormholes; ++start) {
        x = farm.wormholes[start].x;
        y = farm.wormholes[start].y;
        next = farm.wormholes[start].paired_with;
        printf("From %d -> %d\n", start, next);

        for (i=0; i <= farm.num_wormholes; ++i) {
            x = farm.wormholes[next].x;
            y = farm.wormholes[next].y;
            next = find_intersecting_wormhole(x, y);

            if (next == -1) {
                printf("Exiting...\n");
                break;
            }
            printf("From %d -> %d\n", next, farm.wormholes[next].paired_with);
            next = farm.wormholes[next].paired_with;
        }
        if (next != -1) {
            return 1;
        }
    }
    return 0;
}

int find_unpaired_wormhole() {
    int i;
    for (i=0; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].paired_with == -1) {
            return i;
        }
    }
    return -1;
}

int find_intersecting_wormhole(long x, long y) {
    int i, next_i = -1;
    long max_x = 1000000000;
    for (i=0; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].y == y) {
            if (farm.wormholes[i].x > x && farm.wormholes[i].x < max_x) {
                next_i = i;
                max_x = farm.wormholes[i].x;
            }
        }
    }
    return next_i;
}

void print_farm() {
    int i, paired_with;
    for (i=0; i < farm.num_wormholes; ++i) {
        paired_with = farm.wormholes[i].paired_with;
        printf("wormhole[%d] @ (%ld, %ld) is paired with wormhole[%d] @ (%ld, %ld)\n",
               i, farm.wormholes[i].x, farm.wormholes[i].y,
               paired_with, farm.wormholes[paired_with].x, farm.wormholes[paired_with].y);
    }
}
