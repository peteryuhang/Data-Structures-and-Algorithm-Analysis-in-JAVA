## Insertion Sort

```java
public static <AnyType extends Comparable<? super AnyType>> void insertionSort(AnyType[] a) {
  int j;
  for (int p = 1; p < a.length; p++) {
    Anytype tmp = a[p];
    for (j = p; j > 0 && tmp.compareTo(a[j-1]) < 0; j--) {
      a[j] = a[j - 1];
    }
    a[j] = tmp;
  }
}
```

- The time complexity will be `O(N^2)`. The average case is also `O(N^2)`
- If the input is almost sorted, insertion sort will run quickly