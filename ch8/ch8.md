## Equivalence Relations

- An equivalence relation is a relation `R` that satisfies three properties
  1. (Reflexive) `a R a` for all `a in S`
  2. (Symmetic) `a R b` if and only if `b R a`
  3. (Transitive) `a R b` and `b R c` implies that `a R c`
- eg. `<=` relationship is not an equivalence relation; **Electrical connectivity** is an equivalence relation

## Basic Data Structure

- We use forest to represent each set, and array as the data structure since we just need the set's name which can represent by array index and value

```java
public class DisjSets {
  private int[] s;

  public DisjSets(int numElements) {
    s = new int[numElements];
    for (int i = 0; i < s.length; i++) {
      s[i] = -1;
    }
  }

  public void union(int root1, int root2) {
    s[root2] = root1;
  }

  public int find(int x) {
    if (s[x] < 0) {
      return x;
    }
    return find(s[x]);
  }
}
```

- The problem with this implementation is that the worst case running times still be `O(M*N)`, where `M` is operation time

## Smart Union Algorithms

- **union by size**: when doing union, add the smaller tree to the larger tree instead of reverse.
  - We can simply prove that the depth of any node is never more than `logN`, so we can decrease the running time to `O(MlogN)`
  - For implementation, we can have the array entry of each root contains the **negative** of the size of its tree, then we no need extra space at all
  - It has been shown that a sequence of `M` operations requires `O(M)` average time
- **union by height**: a trivial modification of union by size, we replace size with height, the height of a tree increases only when two equally deep trees are joined


```java
// union two disjoint sets using the height heuristic
public void union(int root1, int root2) {
  if (s[root2] < s[root1]) { // root2 is deeper
    s[root1] = root2;        // make root2 as the new root for root1
  } else {                   // same or root1 is deeper
    if (s[root1] == s[root2]) {
      s[root1]--;            // update the height (height goes up by one)
    }
    s[root2] = root1;        // make root1 as the new root for root2
  }
}
```

## Path Compression

- Performed during a find operation and is independent of the strategy used to perform union
- Every node on the path from `x` to the root has its parent changed to the root
- It has been proven that when path compression is done, a sequence of `M` operations requires at most `O(MlogN)` time
- Path compression is perfectly compatible with **union by size**, and thus both routines can be implemented at the same time
- Path compression can also been used with **union by height**, and the height/rank become estimated

```java
public int find(int x) {
  if (s[x] < 0) {
    return x;
  }
  return s[x] = find(s[x]);
}
```

## Application

- Using union/find to generate a maze, start with all walls up and continue knocking down walls until every cell is reachable from every other cell