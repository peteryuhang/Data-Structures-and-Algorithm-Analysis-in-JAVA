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

## Heap Sort

```java
private static int leftChild(int i) {
  return 2 * i + 1;
}

private static <AnyType extends Comparable<? super AnyType>> void percDown(AnyType[] a, int i, int n) {
  int child;
  AnyType tmp;
  for (tmp = a[i]; leftChild(i) < n; i = child) {
    child = leftChild(i);
    if (child != n - 1 && a[child].compareTo(a[child+1]) < 0) {
      child++;
    }
    if (tmp.compareTo(a[child]) < 0) {
      a[i] = a[child];
    } else {
      break;
    }
  }
  a[i] = tmp;
}

private static <AnyType extends Comparable<? super AnyType>> void heapsort(AnyType[] a) {
  for (int i = a.length/2 - 1; i >= 0; i--) {
    percDown(a, i, a.length);                 // buildHeap
  }
  for (int i = a.length - 1; i < 0; i--) {
    swapReferences(a, 0, i);                  // deleteMax
    percDown(a, 0, i);
  }
}
```

- The cell that was last in the heap can be used to store the element that was just deleted
- Performance of heapsort is extremely consistent
- Time complexity is `O(NlogN)`

## Merge Sort

```java
private static <AnyType extends Comparable<? super AnyType>> void merge(AnyType[] a, AnyType[] tmpArray, int leftPos, int rightPos, int rightEnd) {
  int leftEnd = rightPos - 1;
  int tmpPos = leftPos;
  int numElements = rightEnd - leftPos + 1;

  while (leftPos <= leftEnd && rightPos <= rightEnd) {
    if (a[leftPos].compareTo(a[rightPos]) <= 0) {
      tmpArray[tmpPos++] = a[leftPos++];
    } else {
      tmpArray[tmpPos++] = a[rightPos++];
    }
  }

  while (leftPos <= leftEnd) {
    tmpArray[tmpPos++] = a[leftPos++];
  }

  while (rightPos <= rightEnd) {
    tmpArray[tmpPos++] = a[rightPos++];
  }

  for (int i = 0; i < numElements; i++, rightEnd--) {
    a[rightEnd] = tmpArray[rightEnd];
  }
}

private static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] a, AnyType[] tmpArray, int left, int right) {
  if (left < right) {
    int center = (left + right) / 2;
    mergeSort(a, tmpArray, left, center);
    mergeSort(a, tmpArray, center + 1, right);
    merge(a, tmpArray, left, center + 1, right)
  }
}

private static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] a) {
  AnyType[] tmpArray = (AnyType[]) new Comparable[a.length];
  mergeSort(a, tmpArray, 0, a.length - 1);
}
```

- Although mergesort's running time is `O(NlogN)`, it has the significant problem that merging two sorted lists uses linear extra memory
- Mergesort uses the lowest number of comparisons of all the popular sorting algorithms, and thus is a good candidate for general-purpose sorting in java(moving/copying element is cheaper, but comparison might be expensive)
