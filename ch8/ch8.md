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

- The problem with this implementation is that the worst case running times still be `O(N^2)`