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

- The `hashCode`function inside Java Standard lib
  ```java
  public final class String {
    // caching the hash code, help to reduce the times to execute hashCode
    private int hash = 0;

    public int hashCode() {
      if (hash != 0) {
        return hash;
      }

      for (int i = 0; i < length(); i++)
        hash = hash * 31 + (int) charAt(i);
      return hash;
    }
  }
  ```
  - `Caching the hash code`: works because `Strings` are immutable
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

### Double Hashing

- Formula `f(i) = i * hash(x)`, this has some requirements for hash function
  - `hash(x) = R - (x mod R)` with R a prime smaller than `TableSize` will work well
  - `TableSize` also important, need to be prime

## Rehashing

- Build another table that is about twice as big, and scan the old table to re-insert each element to new table
- Rehashing will take `O(N)` but it happen infrequently, it just add constant cost to each insertion
- Can base on the load factor to decide perform rehashing or not


## Hash Tables with Worst-Case O(1) Access

- **Perfect Hashing**: Hash link with hash
- **Cuckoo Hashing**: Maintain 2 or more hash tables, and each table with different hash function
- **Hopscotch Hashing**: Try to improve linear probing algorithm. It will keep certain distance by introducing hope info

## Extendible Hashing

- Same as B tree, in order to reduce the cost bring by disk memory access