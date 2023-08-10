## General Idea

- Important points for hashing
  - Choosing a hash function 
  - Deal with hash collision
  - Deciding on the table size

## Hash Function

- `Key` mode `TableSize` is generally a reasonable strategy. It is often a good idea to ensure that the table size is prime
- A simple hash function
  ```java
  public static int hash(String key, int tableSize) {
    int hashVal = 0;

    for (int i = 0; i < key.length(); i++) {
      hashVal += key.charAt(i);
    }
    return hashVal % tableSize;
  }
  ```
  - If the `tableSize` is large, the function does not distribute the keys well
- Another possible hash function
  ```java
  public static int hash(String key, int tableSize) {
    return (
      key.charAt(0) + 27 * key.charAt(1) + 729 * key.charAt(2)
    ) % tableSize;
  }
  ```
  - Only check first 3 character, the overlapping(collision) will be large
- Good hash function based on Horner's rule
  ```java
  public static int hash(String key, int tableSize) {
    int hashVal = 0;

    for (int i = 0; i < key.length(); i++) {
      hashVal = 37 * hashVal + key.charAt(i);
    }

    hashVal %= tableSize;
    if (hashVal < 0) {
      hashVal += tableSize;
    }

    return hashVal;
  }
  ```
  - If the key is very long, can choose to not compute for all character

## Separate Chaining

- Hash table supported objects which implement **equals** method and a **hashCode** method that returns an int
- Load factor `λ`: The ratio of the number of elements in tha hash table to the table size
  - A successful search require that about `1+(λ/2)` links be traversed
  - General rule is to make `λ` as large as the number of elements expected
- The table size is not really important, but the load factor is

## Hash Tables Without Linked Lists

- Try alternative cells until an empty cell is found
  - Cells `h0(x),h1(x),h2(x),...` are tried in succession
  - `hi(x)=(hash(x)+f(i)) mod TableSize` with `f(0) = 0`, the function `f` is the collision resolution strategys
- Generally, the load factor should be below `λ = 0.5` for a hash table that doesn't use separate chaining
- This kind of hash tables be called **probing hash tables**
- Standard deletion cannot be performed in a probing hash table, because this will potentially make next `contains` operation have problem to locate element. So probing hash tables require lazy deletion

### Linear Probing

- In lineaer probing, `f` is a linear function of `i`, eg. `f(i) = i`
- **Primary Cluster**: cluster is the block of occupied cells. primary cluster means tha any key that hashes into the cluster will require several attempts to resolve the collision, and then it will add into the clusters

### Quadratic Probing

- Quadratic Probing is a collision resolution method that eliminates the primary clustering problem of linear probing
- **Theorem**(Can proved by contradiction): If quadratic probing is used, and the table size is prime, then a new element can always be inserted if the table is at least half empty
- **Secondary clustering**: Elements that hash to the same position will probe the same alternative cells

