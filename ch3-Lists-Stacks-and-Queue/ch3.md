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

### The Iterator and Java Nested and Inner Classes

- Nested class:
  ```java
  // outer class
  public class MyArrayList<AnyType> implements Iterable<AnyType> {
    private int theSize;
    private AnyType[] theItems;
    // ...
    public java.util.Iterator<AnyType> iterator() {
      return new ArrayListIterator<AnyType>(this);
    }

    /* 
     * nested class
     * - must use the word static to signify that it is nested, without it we will get inner class instead of nested class
     * - we can make the class private, so it is inaccessible except by the outer class
     */
    private static class ArrayListIterator<AnyType> implements java.util.Iterator<AnyType> {
      private int current = 0;
      private MyArrayList<AnyType> theList;
      // ...
      public ArrayListIterator(MyArrayList<AnyType> list) {
        thisList = list;
      }

      public boolean hasNext() {
        return current < theList.size();
      }

      public AnyType next() {
        return theList.theItems[current++];
      }
    }
  }
  ```
  - The disadvantage of nested class is that nested class have no direct access to field or function of outer class
- When you declare an inner class, the compiler adds an implicit reference to the outer class object that **caused the inner class object's construction**
  - If the name of the outer class is `Outer`, then the implicit reference is `Outer.this`
    ```java
    // outer class
    public class MyArrayList<AnyType> implements Iterable<AnyType> {
      private int theSize;
      private AnyType[] theItems;
      // ...
      public java.util.Iterator<AnyType> iterator() {
        return new ArrayListIterator<AnyType>();
      }

      /* 
      * inner class
      * - ArrayListIterator is implicitly generic, we don't have to say so
      */
      private class ArrayListIterator implements java.util.Iterator<AnyType> {
        private int current = 0;
  
        public boolean hasNext() {
          // MyArrayList.this.size() can be simply written as size()
          return current < size();
        }

        public AnyType next() {
          // MyArrayList.this.thisItems can be simply written as theItems
          return theItems[current++];
        }

        public void remove() {
          // since MyArrayList's remove would conflict with ArrayListIterator's remove
          // we have to use MyArrayList.this.remove
          return MyArrayList.this.remove(--current);
        }
      }
    }
    ```
    - The inner class is useful in a situation in which each inner class object is associated with exactly one instance of an outer class object


## The Stack ADT

### Applications
 
- Balancing Symbols
- Postfix Expressions
  - Also been called reverse Polish notation
  - When a number is seen, push to the stack, when an operator is seen, the operator is applied to the two numbers that are popped from the stack, and the result is pushed onto the stack
  - `((2 + 3) * 8 + 5 + 3) * 6` been written as postfix is `5 6 2 3 + 8 * + 3 + *`
  - The benifits for Postfix expressions is there is no need to know any precedence rules when performing calculating
- Infix to Postfix Conversion
  - Convert an expression in standard form into postfix

