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

