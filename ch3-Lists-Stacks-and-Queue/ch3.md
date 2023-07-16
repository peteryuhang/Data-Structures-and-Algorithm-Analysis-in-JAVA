## Abstract Data Types (ADTs)

- An abstract data type (ADT) is a set of objects together with a set of operations

## Lists in the Java Collections API

### Collection Interface

- Most important parts of `Collection` interface
  ```java
  public interface Collection<AnyType> extends Iterable<AnyType> {
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(AnyType x);
    boolean add(AnyType x);
    boolean remove(AnyType x);
    java.util.Iterator<AnyType> iterator();
  }
  ```
- Classes that implement the `Iterable` interface can have the **enhanced for loop** used on them to view all their items

### Iterators

- Collections that implement the iterable interface must provide a method named iterator that returns an object of type `Iterator`
  ```java
  public interface Iterator<AnyType> {
    boolean hasNext();
    AnyType next();
    void remove();
  }
  ```
- When the compiler sees an enhanced for loop being used on an object that is `Iterable`, it mechanically replaces the enhanced for loop with calls to the iterator method to obtain an `Iterator` and then calls to `next` and `hasNext`
- **Fundamental Rule**: If you make a structural change to the collection being iterated(eg. add, remove, clear...), then the iterator is no longer valid (and a `ConcurrentModificationException` is thrown on subsequent attempts to use the iterator)
- If the iterator invokes its remove method, then the iterator is still valid


### List Interface, ArrayList and LinkedList

- Most important part of `List` Interface
  ```java
  public interface List<AnyType> extends Collection<AnyType> {
    AnyType get(int idx);
    AnyType set(int idx, AnyType newVal);
    void add(int idx, AnyType x);
    void remove(int idx);

    ListIterator<AnyType> listIterator(int pos);
  }
  ```
- `ensureCapacity` and `trimToSize` can be used on `ArrayList` to efficiently set capacity (underlying array size) and avoid wasted space
- Removes the even numbers in a list; quadratic on ArrayList, but linear time for LinkedList
  ```java
  public static void removeEvensVer3(List<Integer> lst) {
    Iterator<Integer> itr = lst.iterator();    // guarantee that traverse the list is O(N)

    while (itr.hasNext()) {
      if (itr.next()%2 == 0) {
        itr.remove();                          // safe to remove the element returned by next in O(1)
      }
    }
  }
  ```

### ListIterators

- Subset of the `ListIterator` interface in package `java.util`
  ```java
  public interface ListIterator<AnyType> extends Iterator<AnyType> {
    boolean hasPrevious();
    AnyType previous();

    void add(AnyType x);
    void set(AnyType newval);
  }
  ```
  - `hasPrevious` and `previous` function allow traversal of the list from the back to front
  - `add` places a new item into the list in the current position(position between the element returned by next and element returned by previous)
  - `set` change the last value seen by the iterator