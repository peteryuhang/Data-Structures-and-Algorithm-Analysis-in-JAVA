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
- Mergesort uses the lowest number of comparisons of all the popular sorting algorithms, and thus is a good candidate for general-purpose sorting in java(moving/copying element is cheaper because they are reference assignments, but comparison might be expensive). Mergesort not good for C++, which copying object can be expensive if the object are large, while comparing is cheaper, Quicksort will be good candidate here


## Quick Sort

```java
private static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] a) {
  quickSort(a, 0, a.length - 1);
}

private static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] a, int left, int right) {
  if (left + CUTOFF <= right) {
    AnyType pivot = median3(a, left, right);

    int left, j = right - 1;
    for(;;) {
      // below 2 lines shows why quick sort is so fast, there is no extra juggling as there is in mergesort
      while (a[++i].compareTo(pivot) < 0) {}
      while (a[--j].compareTo(pivot) > 0) {}
      if (i < j) {
        swapReferences(a, i, j);
      } else {
        break;
      }
    }

    swapReferences(a, i, right - 1);   // restore pivot
    quickSort(a, left, i - 1);         // sort the small elements
    quickSort(a, i + 1, right);        // sort the large elements
  } else {
    insertionSort(a, left, right);
  }
}

private static <AnyType extends Comparable<? super AnyType>> void median3(AnyType[] a, int left, int right) {
  int center = (left + right) / 2;
  if (a[center].compareTo(a[left]) < 0) {
    swapReferences(a, left, center);
  }
  if (a[right].compareTo(a[left]) < 0) {
    swapReferences(a, left, right);
  }
  if (a[right].compareTo(a[center]) < 0) {
    swapReferences(a, center, right);
  }

  // place pivot at position right - 1
  swapReferences(a, center, right - 1);
  return a[right - 1];
}
```

- The pivot is critical, the common course is to use the median of the left, right, and center elements
- When there is duplicate, we need also consider to do swap instead of skip which has risk wildly uneven subarrays (consider array or subarray with all identical elements)
- For very small arrays (N <= 20), quicksort does not perform as well as insertion sort
- Wrost-case situation `O(N^2)`, average case `O(NlogN)`


### Quick Select

- Question discussed in ch6: The input is a list of `N` elements. Find the kth largest element
- In contrast to quicksort, quickselect makes only one recursive call instead of 2, the average time is `O(N)`

```java
// places the kth smallest item in a[k-1]
private static <AnyType extends Comparable<? super AnyType>> void quickSelect(AnyType[] a, int left, int right, int k) {
  if (left + CUTOFF <= right) {
    AnyType pivot = median3(a, left, right);
    
    int i = left, j = right - 1;
    for (;;) {
      while (a[++i].compareTo(pivot) < 0) {}
      while (a[--j].compareTo(pivot) > 0) {}
      if (i < j) {
        swapReferences(a, i, j);
      } else {
        break;
      }
    }

    swapReferences(a, i, right - 1);     // Restore pivot
    if (k <= i) {
      quickSelect(a, left, i - 1, k);
    } else if (k > i) {
      quickSelect(a, i + 1, right, k)
    }
  } else {
    insertionSort(a, left, right);
  }
}
```

## Linear-Time Sorts: Bucket Sort and Radix Sort

```java
// assume all are ASCII
// assume all have same length
public static void radixSortA(String[] arr, int stringLen) {
  final int BUCKETS = 256;
  ArrayList<String>[] buckets = new ArrayList<>[BUCKETS];

  for (int i = 0; i < BUCKETS; i++) {
    buckets[i] = new ArrayList<>();
  }

  for (int pos = stringLen - 1; pos >= 0; pos--) {
    for (String s : arr) {
      buckets[s.charAt(pos)].add(s);
    }

    int idx = 0;
    for (ArrayList<String> thisBucket : buckets) {
      for (String s : thisBucket) {
        arr[idx++] = s;
      }

      thisBucket.clear();
    }
  }
}
```

- radix sort for variable length strings

```java
// assume all are ASCII
// assume all have length bounded by maxLen
public static void radixSortA(String[] arr, int maxLen) {
  final int BUCKETS = 256;
  ArrayList<String>[] wordsByLength = new ArrayList<>[maxLen + 1];
  ArrayList<String>[] buckets = new ArrayList<>[BUCKETS];

  for (int i = 0; i < wordsByLength.length; i++) {
    wordsByLength[i] = new ArrayList<>();
  }

  for (int i = 0; i < BUCKETS; i++) {
    buckets[i] = new ArrayList<>();
  }

  for (String s : arr) {
    wordsByLength[s.length()].add(s);
  }

  int idx = 0;
  for (ArrayList<String> wordList : wordsByLength) {
    for (String s : wordList) {
      arr[idx++] = s;
    }
  }

  int startingIndex = arr.length;
  for (int pos = maxLen - 1; pos >= 0; pos--) {
    startingIndex -= wordsByLength[pos + 1].size();

    for (int i = startingIndex; i < arr.length; i++) {
      buckets[arr[i].charAt(pos)].add(arr[i]);
    }

    idx = startingIndex;
    for (ArrayList<String> thisBucket : buckets) {
      for (String s : thisBucket) {
        arr[idx++] = s;
      }

      thisBucket.clear();
    }
  }
}
```

- Running time of radix sort is linear in the total number of character in all the strings (each character appears exactly once)
- Radix sort perform well when the characters in the string are drawn from a reasonably small alphabet, and when the strings either are relatively short or are very similar


## External Sorting

- Designed to handle large inputs which is much too large to fit into memory


### Simple Algorithm

- Uses the merging algorithm from mergesort
- The algorithm require `ceil(log(N/M))` passes, where `N` is the total number of elements, `M` is elements that can be loaded into memory

### Multiway Merge

- If we have multiple tape, we can optimize the algorithm by using priority queue for merge `k` input tapes instead of 2
- The algorithm require `ceil(logk(N/M))` passes