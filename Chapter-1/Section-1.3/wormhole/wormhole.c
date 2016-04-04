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
    int seen;
    int paired_with;
} Wormhole;

typedef struct Farms {
    Wormhole *wormholes;
    int num_wormholes;
    int loops;
    int loop_found;
} Farm;

Farm farm;

int find_unpaired_wormhole();
int find_intersecting_wormhole(long x, long y);
void generate_pairs(int to_pair_idx);
void analyze_farm();
void traverse_farm(int i);
void print_farm();

int main() {
    int i;
    FILE *infile;
    FILE *outfile;

    farm.loops = 0;
    farm.loop_found = 0;

    infile = fopen("wormhole.in", "r");
    fscanf(infile, "%d", &farm.num_wormholes);

    farm.wormholes = malloc(farm.num_wormholes * sizeof(Wormhole));
    for (i=0; i < farm.num_wormholes; ++i) {
        fscanf(infile, "%ld %ld", &farm.wormholes[i].x, &farm.wormholes[i].y);
        farm.wormholes[i].seen = 0;
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
    int i, next_to_pair_idx;

    for (i=to_pair_idx+1; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].paired_with == -1) {
            farm.wormholes[i].paired_with = to_pair_idx;
            farm.wormholes[to_pair_idx].paired_with = i;

            analyze_farm();
            if (farm.loop_found == 1) {
                ++farm.loops;
                farm.loop_found = 0;
                farm.wormholes[i].paired_with = -1;
                farm.wormholes[to_pair_idx].paired_with = -1;
                continue;
            }

            next_to_pair_idx = find_unpaired_wormhole();
            if (next_to_pair_idx != -1) {
                generate_pairs(next_to_pair_idx);
            }// else { analyze_farm(); }

            /* Reset the states */
            farm.wormholes[i].paired_with = -1;
            farm.wormholes[to_pair_idx].paired_with = -1;
        }
    }
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

void analyze_farm() {
    int i;
    print_farm();
    for (i=0; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].paired_with == -1) {
            continue;
        }

        printf("Starting at wormhole[%d] @ (%ld, %ld)\n", i, farm.wormholes[i].x, farm.wormholes[i].y);
        traverse_farm(i);
        if (farm.loop_found == 1) {
            printf("Loop found\n");
            printf("\n");
            return;
        }
    }
    printf("\n");
}

void traverse_farm(int i) {
    int next, paired_with;
    long x, y;
    if (farm.wormholes[i].seen == 1) {
        farm.wormholes[i].seen = 0;
        farm.loop_found = 1;
        return;
    }

    paired_with = farm.wormholes[i].paired_with;
    farm.wormholes[i].seen = 1;
    farm.wormholes[paired_with].seen = 1;
    x = farm.wormholes[paired_with].x;
    y = farm.wormholes[paired_with].y;
    next = find_intersecting_wormhole(x, y);
    printf("I just teleported from wormhole[%d] @ (%ld, %ld) to wormhole[%d] @ (%ld, %ld)\n",
           i, farm.wormholes[i].x, farm.wormholes[i].y,
           paired_with, farm.wormholes[paired_with].x, farm.wormholes[paired_with].y);
    if (next == -1) {
        farm.wormholes[i].seen = 0;
        farm.wormholes[paired_with].seen = 0;
        return;
    }
    printf("moving to wormhole[%d] @ (%ld, %ld)\n", next, farm.wormholes[next].x, farm.wormholes[next].y);
    traverse_farm(next);
    farm.wormholes[i].seen = 0;
    farm.wormholes[paired_with].seen = 0;
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
