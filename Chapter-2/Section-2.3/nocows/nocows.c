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
N nodes of height K: N >= 2^(K+1) cannot fit in a binary tree of height K.
*/

typedef struct Nodes {
    struct Nodes *parent;
    struct Nodes *left;
    struct Nodes *right;
} Node;

typedef struct Trees {
    struct Nodes *head;
    int parent_n;
    int n;
    int k;
    int max_nodes;
} Tree;

Tree PARENT_TREE;
static const int MODULO_VALUE = 9901;

void initialize_node(Node *node);
Node *initialize_child(Node *parent, Node *child);
void build_base_parent_tree();
void free_tree(Node *head);
int count_pedigrees();
int walk_parent_tree();

int main() {
    int n, k;
    FILE *infile, *outfile;

    infile = fopen("nocows.in", "r");
    fscanf(infile, "%d %d", &n, &k);
    fclose(infile);

    PARENT_TREE.n = n;
    PARENT_TREE.parent_n = (n - 1) / 2;
    PARENT_TREE.k = k;
    PARENT_TREE.max_nodes = (1 << (k+1)) - 1;

    printf("N=%d, K=%d, MAX_NODES=%d, PARENT_TREE_N=%d\n",
           PARENT_TREE.n, PARENT_TREE.k, PARENT_TREE.max_nodes, PARENT_TREE.parent_n);

    outfile = fopen("nocows.out", "w");
    fprintf(outfile, "%d\n", count_pedigrees() % MODULO_VALUE);
    fclose(outfile);

    return 0;
}

int count_pedigrees() {
    int pedigrees = 0;
    PARENT_TREE.head = malloc(sizeof(Node));
    initialize_node(PARENT_TREE.head);

    if (PARENT_TREE.n <= PARENT_TREE.max_nodes) {
        build_base_parent_tree();
        pedigrees = walk_parent_tree();
    }

    free_tree(PARENT_TREE.head);
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

int get_new_height_and_head_from_parent_with_empty_right_child(Node **head, int current_height) {
    while ((*head)->parent != NULL) {
        *head = (*head)->parent;
        current_height = current_height - 1;
        if ((*head)->right == NULL) {
            break;
        }
    }
    return current_height;
}

int head_can_create_child(Node *child, int current_height) {
    if (current_height < PARENT_TREE.k && child == NULL) {
        return 1;
    }
    return 0;
}

void build_base_parent_tree() {
    int i = 0, current_height = 1;
    int head_can_create_left_child, head_can_create_right_child;
    Node *head = PARENT_TREE.head;

    for (i=1; i < PARENT_TREE.parent_n; ++i) {
        if (head_can_create_child(head->left, current_height)) {
            head->left = initialize_child(head, head->left);
            head = head->left;
            ++current_height;
        } else if (head_can_create_child(head->right, current_height)) {
            head->right = initialize_child(head, head->right);
            head = head->right;
            ++current_height;
        } else {
            current_height = get_new_height_and_head_from_parent_with_empty_right_child(&head, current_height);
            --i; // Reset the counter - we need to retry inserting "this" node
        }
    }
}

int walk_parent_tree() {
    int pedigrees = 1;
    Node *head = PARENT_TREE.head;
    printf("head->parent=%d head=%d head->left=%d head->right=%d\n",
           (int)head->parent, (int)head, (int)head->left, (int)head->right);
    // While there is a node on the left side of the tree:
    //      What are all the positions to its right it can occupy,
    //      END STATE: the node reaches the right-most position
    return pedigrees;
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
