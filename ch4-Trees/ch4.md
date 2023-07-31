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

## The Search Tree ADT - Binary Search Trees

### Contains

```java
private boolean contains(AnyType x, BinaryNode<AnyType> t) {
  if (t == null) {
    return false;
  }

  int compareResult = x.compareTo(t.element)
  // tail recursion, can be easily removed with loop
  if (compareResult < 0) {
    return contains(x, t.left); 
  } else if (compareResult > 0) {
    return contains(x, t.right);
  }
  return true;
}
```

### findMin and findMax

```java
private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
  if (t == null) {
    return null;
  } else if (t.left == null) {
    return t;
  }
  return findMin(t.left);
}

private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
  if (t != null)
    while (t.right != null)
      t = t.right;

  return t;
}
```

### insert

```java
private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
  if (t == null) {
    return new BinaryNode<>(x, null, null);
  }

  int compareResult = x.compareTo(t.element);
  if (compareResult < 0) {
    t.left = insert(x, t.left);
  } else if (compareResult > 0) {
    t.right = insert(x, t.right);
  } // can add thing need to be done when elements are equal
  return t;
}
```

### remove

Consider 3 situations:

- If the node is a leaf, it can be deleted immediately
- If the node has one child, the node can be delete after its parent adjust a link to bypass the node
- If the node has two children, can find the smallest node on right tree, replace the value of this node with the found node, then delete the node in right tree

```java
private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
  if (t == null) {
    return null;
  }

  int compareResult = x.compareTo(t.element);
  if (compareResult < 0) {
    t.left = remove(x, t.left);
  } else if (compareResult > 0) {
    t.right = remove(x, t.right);
  }

  if (t.left != null && t.right != null) { // two child
    t.element = findMin(t.right).element;
    t.right = remove(t.element, t);
  } else {                                 // less than two child
    t = (t.left != null) ? t.left : t.right;
  }

  return t;
}
```

- **Lazy deletions**: When an element is to be deleted, it is left in the tree and merely marked as being deleted

### Average-Case Analysis

- **Internal Path Length**: The sum of the depth of all nodes in a tree
  - `D(N) = D(i) + D(N - i - 1) + N - 1` where `D(N)` is the internal path length of tree `T`, `D(i)` is the left subtree, `D(N - i - 1)` is the right subtree. For balance BST, finally we can get `D(N) = O(NlogN)`
- But based on the `remove` algorithm list above, the right subtree will be smaller than left. To optimize previous algorithm, we can randomly choose replace with the largest element on left subtree and the the smallest on the right subtree
- The difficulty is that defining what's average is very difficult andd sometime need assumption which might be invalid

- Ways for optimization:
  - **Balanced search tree**: eg. AVL tree
  - **Self-adjusting tree**: forgo the balance condition and allow the tree to be arbitrarily deep, but after every operation, a restructing rule is applied that trends to make future operations efficient. It not guarantee for every operation, but for M operation, the worst case will be `O(MlogN)` eg. splay tree

## AVL Trees

- An AVL tree is identical to a binary tree, except that for every node in the tree, the height of the left and right subtrees can differ by at most 1
- `S(h) = S(h - 1) + S(h - 2) + 1`, h is the tree height, `S(h)` is the minimum number of nodes
- After an insertion, only nodes that are on the path from the insertion point to the root might have their balance altered because only those nodes have their subtrees altered
- Assume the node which must been rebalanced is `a`, then a violation might occur in 4 cases
  - An insertion into the left subtree of the left child of `a`
    - fixed by **single rotation**
  - An insertion into the right subtree of the left child of `a`
    - fixed by **double rotation**
  - An insertion into the left subtree of the right child of `a`
    - fixed by **double rotation**
  - An insertion into the right subtree of the right child of `a`
    - fixed by **single rotation**

### Single Rotation

- For the case 1:
![](./Screen%20Shot%202023-07-27%20at%2010.04.09%20AM.png)

- For the case 4:
![](./Screen%20Shot%202023-07-27%20at%2010.05.07%20AM.png)

### Double Rotation

- For the case 2:
![](./Screen%20Shot%202023-07-27%20at%2010.17.58%20AM.png)

- For the case 3:
![](./Screen%20Shot%202023-07-27%20at%2010.18.26%20AM.png)


## Splay Trees

- Guarantees that any `M` consecutive tree operations starting from empty tree take at most `O(MlogN)` time. Thus, a splay tree has an `O(logN)` **amortized cost per operation**
- The basic idea of the splay tree is that after a node is accessed, it is pushed to the root by a series of AVL tree rotations

### Splaying

- Rotate bottom up along the access path, and there are 2 cases:
  - **zig-zag**: perform double rotation (same as AVL)
    ![](./Screen%20Shot%202023-07-29%20at%209.04.12%20AM.png)
  - **zig-zig**: do the transformation
    ![](./Screen%20Shot%202023-07-29%20at%209.04.26%20AM.png)


## Tree Traversals

- **Inorder Traversal**
  - Get ordering information of BST
- **Postorder Traversal**
  - Get the height of a node
- **Preorder Traversal**
  - Label each node with its depth
- **Level-order Traversal**
