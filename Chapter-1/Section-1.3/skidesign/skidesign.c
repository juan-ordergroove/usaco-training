/*
ID: jmg20482
LANG: C
TASK: skidesign
*/
#include <stdio.h>
#include <stdlib.h>

int N;
int *hills;
int HIGHEST_ELEVATION = 0;
const int MAX_ELEVATION_DIFFERENCE = 17;

int find_minimum_cost_elevation_change();
int cost_to_modify_hill(int hill_elevation, int shortest, int tallest);

int main() {
    int i;
    int min_cost = 0;
    FILE *infile;
    FILE *outfile;

    infile = fopen("skidesign.in", "r");
    fscanf(infile, "%d", &N);

    hills = malloc(N * sizeof(int));
    for (i=0; i < N; ++i) {
        fscanf(infile, "%d", &hills[i]);
        if (hills[i] > HIGHEST_ELEVATION) {
            HIGHEST_ELEVATION = hills[i];
        }
    }
    fclose(infile);

    min_cost = find_minimum_cost_elevation_change();

    outfile = fopen("skidesign.out", "w");
    fprintf(outfile, "%d\n", min_cost);
    fclose(outfile);

    free(hills);
    return 0;
}

int find_minimum_cost_elevation_change() {
    int i;
    int cost;
    int hill_elevation;
    int shortest = 0;
    int tallest = shortest + MAX_ELEVATION_DIFFERENCE;
    int min_cost = 1000000;

    while (tallest <= HIGHEST_ELEVATION) {
        cost = 0;
        for(i=0; i < N; ++i) {
            hill_elevation = hills[i];
            cost += cost_to_modify_hill(hill_elevation, shortest, tallest);
        }

        if (cost < min_cost) {
            min_cost = cost;
        }
        ++shortest;
        ++tallest;
    }
    return min_cost;
}

int cost_to_modify_hill(int hill_elevation, int shortest, int tallest) {
    int cost;
    if (hill_elevation >= shortest && hill_elevation <= tallest) {
        cost = 0;
    } else if (hill_elevation < shortest) {
        cost = shortest - hill_elevation;
    } else {
        cost = hill_elevation - tallest;
    }

    return cost * cost;
}
