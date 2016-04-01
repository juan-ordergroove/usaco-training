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

int find_unpaired_wormhole(Farm farm);
int find_intersecting_wormhole(Farm farm, int x, int y);
void generate_pairs(Farm farm, int to_pair_idx);
void analyze_farm(Farm farm);
void traverse_farm(Farm farm, int i);

int main() {
    int i;
    Farm farm;
    FILE *infile;

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

    generate_pairs(farm, 0);
    printf("%d\n", farm.loops);

    free(farm.wormholes);
    return 0;
}

void generate_pairs(Farm farm, int to_pair_idx) {
    int i, next_to_pair_idx;

    /* Break out state - all wormholes have been paired, analyze farm */
    if (to_pair_idx == -1) {
        analyze_farm(farm);
        return;
    }

    for (i=to_pair_idx+1; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].paired_with == -1) {
            farm.wormholes[i].paired_with = to_pair_idx;
            farm.wormholes[to_pair_idx].paired_with = i;

            generate_pairs(farm, find_unpaired_wormhole(farm));

            /* Reset the states */
            farm.wormholes[i].paired_with = -1;
            farm.wormholes[to_pair_idx].paired_with = -1;
        }
    }
}

int find_unpaired_wormhole(Farm farm) {
    int i;
    for (i=0; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].paired_with == -1) {
            return i;
        }
    }
    return -1;
}

void analyze_farm(Farm farm) {
    int i;
    for (i=0; i < farm.num_wormholes; ++i) {
        traverse_farm(farm, i);
        if (farm.loop_found == 1) {
            farm.loop_found = 0;
            return;
        }
    }
}

void traverse_farm(Farm farm, int i) {
    int next, x, y;
    if (farm.wormholes[i].seen == 1) {
        farm.loop_found = 1;
        ++farm.loops;
        return;
    }

    farm.wormholes[i].seen = 1;
    x = farm.wormholes[i].x;
    y = farm.wormholes[i].y;
    next = find_intersecting_wormhole(farm, x, y);
    if (next == -1) {
        return;
    }
    traverse_farm(farm, next);
}

int find_intersecting_wormhole(Farm farm, int x, int y) {
    int i;
    for (i=0; i < farm.num_wormholes; ++i) {
        if (farm.wormholes[i].y == y) {
            if (farm.wormholes[i].x > x) {
                return i;
            }
        }
    }
    return -1;
}
