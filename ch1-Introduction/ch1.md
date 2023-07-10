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

