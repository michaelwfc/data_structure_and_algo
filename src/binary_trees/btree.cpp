/**
A B-tree is a generalization of a 2–3 tree designed to keep a search tree short and wide.

The main idea is:
Instead of storing only one key in each node, store many sorted keys in one node, and use links between keys to guide the search.

This is especially useful for databases and filesystems because one node can fit into one disk page.


B-tree. Generalize 2-3 trees by allowing up to M - 1 key-link pairs per node.
(choose M as large as possible so that M links fit in a page, e.g., M = 1024)
・At least 2 key-link pairs at root.
・At least M / 2 key-link pairs in other nodes： Keep every non-root node at least half full.
・External nodes contain client keys.
・Internal nodes contain copies of keys to guide search.

The key property is:
All external nodes are at the same depth.
Every search travels through approximately the same number of nodes.
This is particularly important for disk-based storage because each tree level may require a disk-page read.

key-link pair：
A B-tree node can be represented as:
keys:    K1    K2    K3
links:  L0    L1    L2    L3

More precisely:

          K1       K2       K3
        /   \    /   \    /   \
       L0   L1  L1   L2  L2   L3

Conceptually, the links divide the key space into ranges:
L0: keys < K1
L1: K1 < keys < K2
L2: K2 < keys < K3
L3: keys > K3

A cleaner representation is:

        [ K1 | K2 | K3 ]
       /    |    |    \
     L0    L1   L2     L3

For M = 4:
maximum keys = M - 1 = 3
maximum children = M = 4
This is a 2–3–4 tree.

*/