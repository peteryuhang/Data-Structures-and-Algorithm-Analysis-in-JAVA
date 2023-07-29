public class AVLTree<AnyType extends Comparable<? super AnyType>> {
  private static class TreeNode<AnyType> {
    TreeNode<AnyType> left;
    TreeNode<AnyType> right;
    int height;
    AnyType val;
    TreeNode(AnyType val) {
      this(val, null, null);
    }
    TreeNode(AnyType val, TreeNode<AnyType> left, TreeNode<AnyType> right) {
      this.val = val;
      this.left = left;
      this.right = right;
      this.height = 0;
    }
  }

  private int height(TreeNode<AnyType> t) {
    return t == null ? -1 : t.height;
  }

  private static final int ALLOWED_IMBALANCE = 1;

  private TreeNode<AnyType> insert(AnyType x, TreeNode<AnyType> t) {
    if (t == null) {
      return new TreeNode<>(x, null, null);
    }

    int compareResult = x.compareTo(t.val);

    if (compareResult < 0) {
      t.left = insert(x, t.left);
    } else if (compareResult > 0) {
      t.right = insert(x, t.right);
    }

    return balance(t);
  }

  private TreeNode<AnyType> remove(AnyType x, TreeNode<AnyType> t) {
    if (t == null) {
      return t;
    }

    int compareResult = x.compareTo(t.val);

    if (compareResult < 0) {
      t.left = remove(x, t.left);
    } else if (compareResult > 0) {
      t.right = remove(x, t.right);
    } else if (t.left != null && t.right != null) {
      t.val = findMin(t.right).val;
      t.right = remove(t.val, t.right);
    } else {
      t = (t.left != null) ? t.left : t.right;
    }

    return balance(t);
  }

  private TreeNode<AnyType> balance(TreeNode<AnyType> t) {
    if (t == null) {
      return t;
    }

    if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
      if (height(t.left.left) >= height(t.left.right)) {
        t = rotateWithLeftChild(t);
      } else {
        t = doubleWithLeftChild(t);
      }
    } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
      if (height(t.right.right) >= height(t.right.left)) {
        t = rotateWithRightChild(t);
      } else {
        t = doubleWithRightChild(t);
      }
    }

    t.height = Math.max(height(t.left), height(t.right)) + 1;
    return t;
  }

  private TreeNode<AnyType> rotateWithLeftChild(TreeNode<AnyType> t) {
    TreeNode<AnyType> tl = t.left;
    t.left = tl.right;
    tl.right = t;
    t.height = Math.max(height(t.left), height(t.right)) + 1;
    tl.height = Math.max(height(t.left), t.height) + 1;
    return tl;
  }

  private TreeNode<AnyType> doubleWithLeftChild(TreeNode<AnyType> t) {
    t.left = rotateWithRightChild(t.left);
    return rotateWithLeftChild(t);
  }

  private TreeNode<AnyType> rotateWithRightChild(TreeNode<AnyType> t) {
    TreeNode<AnyType> tr = t.right;
    t.right = tr.left;
    tr.left = t;
    t.height = Math.max(height(t.right), height(t.left)) + 1;
    tr.height = Math.max(height(t.right), t.height) + 1;
    return tr;
  }

  private TreeNode<AnyType> doubleWithRightChild(TreeNode<AnyType> t) {
    t.right = rotateWithLeftChild(t.right);
    return rotateWithRightChild(t);
  }

  private TreeNode<AnyType> findMin(TreeNode<AnyType> t) {
    if (t == null) {
      return null;
    } else if (t.left == null) {
      return t;
    }
    return findMin(t.left);
  }

  public static void main(String[] args) {
    AVLTree<Integer> avl = new AVLTree();
    TreeNode root = new TreeNode(2, null, null);
    root.left = new TreeNode(1, null, null);
    root.right = new TreeNode(3, null, null);

    root = avl.insert(4, root);
    root = avl.insert(5, root);
    root = avl.insert(6, root);
    root = avl.insert(7, root);
    root = avl.insert(15, root);
    root = avl.insert(16, root);
    root = avl.insert(14, root);
    root = avl.insert(13, root);
    root = avl.insert(12, root);
    root = avl.insert(11, root);
    root = avl.insert(10, root);
    root = avl.insert(9, root);
    root = avl.insert(8, root);

    System.out.println(root.val);
    System.out.println(root.left.val);
    System.out.println(root.right.val);
  }
}
