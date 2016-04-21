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
Node *initialize_child(Node *parent, Node *child);
void build_base_parent_tree();
void free_tree(Node *head);
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
    free_tree(PARENT_TREE);
    return pedigrees;
}

void initialize_node(Node *node) {
    node->parent = NULL;
    node->left = NULL;
    node->right = NULL;
}

Node *initialize_child(Node *parent, Node *child) {
    child = malloc(sizeof(Node));
    initialize_node(child);
    child->parent = parent;
    return child;
}

Node *find_parent_with_empty_right_child(Node *head, int *height) {
    while (head->parent != NULL) {
        head = head->parent;
        *height = *height - 1;
        if (head->right == NULL) {
            break;
        }
    }
    return head;
}

void build_base_parent_tree() {
    int i = 0, height = 1;
    int head_can_create_left_child, head_can_create_right_child;
    Node *head = PARENT_TREE;

    for (i=1; i < PARENT_TREE_N; ++i) {
        head_can_create_left_child = height < K && head->left == NULL;
        head_can_create_right_child = height < K && head->right == NULL;

        if (head_can_create_left_child) {
            head->left = initialize_child(head, head->left);
            head = head->left;
            ++height;
        } else if (head_can_create_right_child) {
            head->right = initialize_child(head, head->right);
            head = head->right;
            ++height;
        } else {
            head = find_parent_with_empty_right_child(head, &height);
            --i; // Reset the counter - we need to retry inserting "this" node
        }
    }
}

int walk_parent_tree() {
    printf("\nPARENT_TREE=%d PARENT_TREE->parent=%d PARENT_TREE->left=%d PARENT_TREE->right=%d\n",
           (int)PARENT_TREE, (int)PARENT_TREE->parent, (int)PARENT_TREE->left, (int)PARENT_TREE->right);
    return 0;
}

void free_tree(Node *head) {
    if (head->left != NULL) {
        free_tree(head->left);
    } else if (head->right != NULL) {
        free_tree(head->right);
    } else if (head != NULL) {
        free(head);
    }
}
