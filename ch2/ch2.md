## Mathematical Background

- When we say that `T(N) = O(f(N))`, we are guaranteeing that the function `T(N)` grows at a rate no faster than `f(N)`; thus `f(N)` is an **upper bound** on `T(N)`
- If only a small amount of input is expected, it might be silly to expend a great deal of effort to design a clever algorithm

## Running Time Calculations

- A program shows a bad use of recursion:
  ```java
  public static long fib(int n) {
    if (n <= 1)
      return 1;
    else
      return fib(n-1) + fib(n-2);
  }
  ```
  - If input size `N` is small, `N = 0` or `N = 1`, then `T(0) = T(1) = 1`
  - For `N > 2`, `T(N) = T(N - 1) + T(N - 2) + 2 >= T(N - 1) + T(N - 2)`
  - By induction, can prove that for `N > 4`, `T(N - 1) + T(N - 2) >= (3/2)^N`
  - So the program grows **exponentially**
  - The reason is because there is a huge amount of redundant work being performed
    - `fib(n-1)` actually computes `fib(n-2)` at some point, but the answer been throwed away


### Solution for the Maximum Subsequence Sum Problem

- [First Version](./SubsequenceSum1.java)
  - The Big Oh time complexity is `O(N^3)`

- The inefficiency can be improved by noticing that `SUM(i + ... + j) = j + SUM(i + ... + (j - 1))`
- [Second Version](./SubsequenceSum2.java)

- Can consider divide and conquer strategy to do the improvement
  - Divide stage split the problem into 2 sub-problem, left half and right half in this case
  - Conquer stage patching togather the 2 solution, and arrival at final solution, the maximum subsequence sum can be in one of 3 places:
    - Occurs entirely in the left half of the input
    - Occurs entirely in the right half of the input
    - Largest sum in the left half that includes the last element + Largest sum in the right half that includes the first element
- [Third Version](./SubsequenceSum3.java)
  - The time complexity calculation
    - `T(1) = 1`
    - `T(N) = 2 * T(N / 2) + O(N)`
    - We can observe the pattern through:
      ```
      T(N) = 2 * T(N / 2) + O(N)
      T(N) = 2 * T(N / 2) + N
      T(N) = 2 * (2 * T(N/4) + N/2) + N
      T(N) = 2 * (2 * (2 * T(N/8) + N/4) + N/2) + N
      ...
      ```
    - `T(N) = NlogN + N`

- Observation is that if `a[i]` is negative, then it cannot possibly represent the start of the optimal sequence
  - Similarly, any negative subsequence cannot possibly be a prefix
- [Fourth Version](./SubsequenceSum4.java)
  - **Online algorithm**: Correctly give an answer to the problem for the data it has already read, no need to store the data (requires constant space)


### Logarithms in the Running Time

- An algorithm is `O(logN)` if it takes constant `O(1)` time to cut the problem size by a fraction (which is usually 1/2)

#### Binary Search

example:
```java
public static <AnyType extends Comparable<? super AnyType>> int binarySearch( AnyType [ ] a, AnyType x ) {
  int low = 0, high = a.length - 1;
  while( low <= high ) {
    int mid=(low+high)/2;
    if(a[ mid ].compareTo( x ) < 0 )
      low= mid + 1;
    else if( a[ mid ].compareTo( x ) > 0 )
      high = mid - 1;
    else
      return mid;

  }
 return NOT_FOUND;
}
```

#### Euclid's Algorithm

- Computing the greatest common divisor

```java
public static long gcd(long m, long n) {
  while (n != 0) {
    long rem = m % n;
    m = n;
    n = rem;
  }
  return m;
}
```

- At first glance, the time complexity is not obvious, but if `m > n`, then `m mod n < m/2`, which can roughly represent the time complexity

#### Exponentiation

```java
public static long pow(long x, int n) {
  if (n == 0)
    return 1;
  // if (n == 1)
  //   return x;
  if (isEven(n))
    return pow(x*x, n/2);
  else
    return pow(x*x, n/2) * x;
}
```