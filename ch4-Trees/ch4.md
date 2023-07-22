## Preliminaries

- A tree consists of a distinguished node `r`, called the **root**, and zero or more nonempty **(sub)trees** `T1, T2, T3 ... Tk`, each of whose roots are connected by a directed edge from r
- In a tree there is exactly one path from the root to each node, the length of the path is the number of edges on the path
- For any node `ni`, the **depth** of `ni` is the length of the unique path from the root to `ni`. Thus, the root is at depth 0. The **depth of a tree** is equal to the depth of the deepest leaf, this is always equal to the height of the tree
- The **height** of `ni` is the length of the longest path from `ni` to a leaf. Thus all leaves are at height 0
- If there is a path from `n1` to `n2`, then `n1` is an **ancestor** of `n2` and `n2` is a **descendant** of `n1`. If `n1 != n2`, then `n1` is a **proper ancestor** of `n2` and `n2` is a **proper descendant** of `n1`

## Binary Tree

- Every binary tree with `N` nodes would require `N + 1` null links

### An Example: Expression Trees

- The leaves of an expression tree are operands, and the other nodes contains operators
- Using inorder traversal, we can print standard formula (infix) based on the expression tree
- Using postorder traversal, we can print postfix representation

- We can use the postfix representation to construct expression trees (we can first convert infix to postfix by using stack)