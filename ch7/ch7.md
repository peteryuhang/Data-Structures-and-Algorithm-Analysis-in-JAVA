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

## Shell sort

```java
public static <AnyType extends Comparable<? super AnyType>> void shellSort(AnyType[] a) {
  int j;
  for (int gap = a.length / 2; gap >= 1; gap /= 2) {
    for (int p = gap; p < a.length; p++) {
      Anytype tmp = a[p];
      for (j = p; j >= gap && tmp.compareTo(a[j-gap]) < 0; j -= gap) {
        a[j] = a[j - gap];
      }
      a[j] = tmp;
    }
  }
}
```

- The choice of increment sequence can drastically affect performance
- The worst-case running time of Shellsort, using Shell's incremental, is `O(N^2)`
- The worst-case running time of Shellsort, using Hibbard's incremental, is `O(N^(3/2))`