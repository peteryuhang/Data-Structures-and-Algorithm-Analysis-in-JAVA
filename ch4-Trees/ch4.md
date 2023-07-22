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
  }
  if (t.element > 0) {
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
  if (t != null) {
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