/*
ID: jmg20482
LANG: C
TASK: nocows
*/
#include <stdio.h>
#include <stdlib.h>

/*
NOTES:

    N = # of nodes
    3 <= N <= 200

    K = height of the tree
    1 < K < 100

Instead of tracking all the nodes, we'll create a binary tree
where each node represents a node with children, aka, the
PARENT_TREE. This new binary tree will be limited to a height of K-1.

The number of nodes in the PARENT_TREE, PARENT_TREE_N = (N - 1) / 2.

The number of distinct node placements in the PARENT_TREE is the solution.

There is an upper bound, MAX_NODES, for counting pedigrees given
N nodes of height K: N >= 2^K cannot fit in a binary tree of height K.
*/

typedef struct Nodes {
    struct Nodes *parent;
    struct Nodes *left;
    struct Nodes *right;
} Node;
int N, K, MAX_NODES, PARENT_TREE_N;
Node *PARENT_TREE = NULL;

void initialize_node(Node *node);
void build_base_parent_tree();
int count_pedigrees();
int walk_parent_tree();

int main() {
    FILE *infile, *outfile;

    infile = fopen("nocows.in", "r");
    fscanf(infile, "%d %d", &N, &K);
    fclose(infile);

    MAX_NODES = (1 << (K+1)) - 1;
    PARENT_TREE_N = (N - 1) / 2;
    printf("N=%d, K=%d, MAX_NODES=%d, PARENT_TREE_N=%d\n",
           N, K, MAX_NODES, PARENT_TREE_N);

    outfile = fopen("nocows.out", "w");
    fprintf(outfile, "%d\n", count_pedigrees());
    fclose(outfile);

    return 0;
}

int count_pedigrees() {
    int pedigrees = 0;
    PARENT_TREE = malloc(sizeof(Node));
    initialize_node(PARENT_TREE);

    if (N <= MAX_NODES) {
        build_base_parent_tree();
        pedigrees = walk_parent_tree();
    }
    free(PARENT_TREE);
    return pedigrees;
}

void initialize_node(Node *node) {
    node->parent = NULL;
    node->left = NULL;
    node->right = NULL;
}

void build_base_parent_tree() {
    int i = 0, height = 0;
    Node *head = PARENT_TREE;
    for (i=0; i < PARENT_TREE_N; ++i) {
        if (height < K && head->left == NULL) {
            printf("i=%d, height=%d\n", i, height);
            head->left = malloc(sizeof(Node));
            initialize_node(head->left);
            head->left->parent = head;
            head = head->left;
            ++height;
        } else {
            while (head->parent != NULL) {
                head = head->parent;
                --height;
                if (head->right == NULL) {
                    break;
                }
            }
            printf("i=%d, height=%d\n", i, height);
            head->right = malloc(sizeof(Node));
            head->right->parent = head;
            head = head->right;
            ++height;
        }
    }
}

int walk_parent_tree(Node *parent_tree) {
    return 0;
}
