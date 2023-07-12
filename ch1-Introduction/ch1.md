- Writing a working program is not good enough. If the program is to be run on a large data set, then the running time become an issue

## Mathematics Review

### Logarithms

- Definition 1.1. `X^A = B if and only if logx(B) = A`
  - Theorem 1: `logA(B) = logC(B) / logC(A)`
  - Theorem 2: `logAB = logA + logB`, `logA/B = logA - logB`, `log(A^B) = Blog(A)`

### Series

#### Geometric series

- Basic: `SUM(1 + 2 + 2^2 + ... + 2^N) = 2^(N+1) - 1`
- More general: `SUM(1 + A + A^2 + ... + A^N) = (A^(N+1) - 1) / (A - 1)`
- If `0 < A < 1` on above formula, then `SUM(1 + A + A^2 + ... + A^N) <= 1 / (1 - A)`, when N approach infinite, `SUM(1 + A + A^2 + ... + A^N)` approach `1 / (1 - A)`
  - Proof `S = 1 + A + A^2 + A^3 + ...`, then `A * S = A + A^2 + A^3 + ...`, so `S - AS = 1`, finally `S = 1 / (1 - A)`

#### Arithmetic series

- Basic: `SUM(1 + 2 + 3 + ... + N) = N * (N+1) / 2 ≈ N^2 / 2`
- Harmonic numbers: `HN = SUM(1 + 1/2 + 1/3 + ... + 1/N) ≈ loge(N)`, the error in the formula tends to `0.57721566`, which is known as **Euler's constant**

### Modular Arithmetic

- We say A is congruent to B modulo N, written `A ≡ B (mod N)` **if N divides `A - B`**
  - eg. `81 ≡ 61 ≡ 1 (mod 10)`
  - so, if `A ≡ B (mod N)`, then `A + C ≡ B + C (mod N)` and `A * D ≡ B * D (mod N)`
- If **N is prime**, there are three important theorems:
  1. `ab ≡ 0` is true if and only if `a ≡ 0 (mod N)` or `b ≡ 0 (mod N)`
  2. `a * x ≡ 1 (mod N)` has a unique solution `(mod N)` for all `0 < a < N`. This solution `0 < x < N` is the **multiplicative inverse**
  3. `x^2 ≡ a (mod N)` has either two solutions `(mod N)` for all `0 < a < N`, or no solutions

### The P word

- 3 ways of proving statements in data structure analysis
  - Proof by induction (eg. Fibonacci Number)
    1. Proving base case
    2. **Inductive hypothesis**: the theorem is assumed to be true for all cases up to some limit k
    3. Use this assumption, prove the theorem is then shown to be true for the next value, which is k + 1
  - Proof by contradiction (eg. infinite number of primes)
    - Assuming that the theorem is false and showing that this assumption implies that some known property is false
  - Proof by counterexample

## A Brief Introduction to Recursion

- A function that is defined in terms of itself is called **recursive**
- Recursion example
  ```java
  // f(x) = 2 * f(x - 1) + x^2
  public static int f(int x) {
    // base case
    if (x == 0)
      return 0;
    else 
      return 2 * f(x - 1) + x * x;
  }
  ```
  - Recursive calls will keep on being made until a base case is reached
- Four fundamental rules of recursion
  - **Base case**: Must always have some base cases, which can be solved without recursion
  - **Making progress**: The recusive call must always be to a case that makes progress toward a base case
  - **Design rule**: Assume that all the recursive calls work
  - **Compound interest rule**: Never duplicate work by solving the same instance of a problem in separate recursive calls

## Implementing Generic Components (Pre-Java 5)

### Using Object for Genericity

- To access a specific method of the object, we must **downcast** to the correct type
- Primitive type can not be used. Only reference type are compatible with Object

### Wrapper for Primitive Types

- Each wrapper object is immutable, meaning its state can never change

### Using Interface Types for Genericity

- Only objects that implement the `Comparable` interface can be passed as elements of the `Comparable`
- Objects that have a compareTo method but do not declare that they implement `Comparable` are not `Comparable`, and do not have the requisite IS-A relationship
- The wrapper class implemented `Comparable` interface
- Can consider use **function object** if it is impossible to declare that a class implements a needed interface

### Compatibility of Array Types

- Suppose that Employee IS-A Person, does it imply that Employee[] IS-A Person[]?
  ```java
  Person[] arr = new Employee[5];       // compiles: arrays are compatible
  arr[0] = new Student(...);            // compiles: Student IS-A Person
  ```
  - Alough both statements compile, we have type confusion, since Student is not Employee
  - The runtime system cannot throw a `ClassCastException` since there is no cast
- In Java the arrays are **type-compatible**, known as **covariant type array**
  - Each array keeps track of the type of object it is allowed to store. If an incompatible type is inserted into the array, the VM will throw an `ArrayStoreException`
  - The covariance of arrays was needed in earlier versions of Java


## Implementing Generic Components (Using Java 5 Generics)

- Example of generic version class:
  ```java
  public class GenericMemoryCell<AnyType> {
    public AnyType read() { return storedValue; }
    public void write(AnyType x) { storedValue = x; }

    private AnyType storedValue;
  }
  ```
- Example of generic version interface:
  ```java
  public interface Comparable<AnyType> {
    public int compareTo(AnyType other);
  }
  ```
- By making the class generic, many of the errors that were previously only reported at runtime become compile-time errors

### Autoboxing/Unboxing

- **Autoboxing**: If an `int` is passed in a place where an `Integer` is required, the compiler will insert a call to the Integer constructor behind the scenes
- **Unboxing**: If an `Integer` is passed in a place where an `int` is required, the compiler will insert a call to the intValue method behind the scenes
- Similar for other 7 primitive/wrapper pairs

### The Diamond Operator

- Java 7 adds a new language feature, known as the diamond operator, eg.
  ```java
  GenericMemoryCell<Integer> m = new GenericMemoryCell<>();
  ```
- The diamond operator simplifies the code, with no cost to the developer


### Wildcards with Bounds

- Arrays are covariant, but generic collection are not covariant
  - As a result, we can not pass a `Collection<SubType>` as a parameter to the method of `Collection<SuperType>`
- Java 5 makes up for this with `wildcards`, eg
  ```java
  // both Square and Shape are acceptable
  public static double totalArea( Collection<? extends Shape> arr ) {
    double total = 0;
    for (Shape s : arr)
      if (s != null)
        total += s.area();
    
    return total;
  }
  ```
