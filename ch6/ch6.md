## Binary Heap

- A heap is binary tree that is completely filled, with the possible exeception of the bottom level, which is filled from left to right (complete binary tree)
- Heap can be represent by array, for any element in array, the left child is in position `2*i`, the right child is in the cell after the left child `2*i+1`, and the parent is in position `floor(i/2)`
- **Heap-order property**: min heap as example, node should be smaller than all of its descendants

### Operations

- **insert**: general strategy is **percolate up**, the new element is percolate up the heap until the correct location is found
- **deleteMin**: general strategy is **percolate down**, which is similar as percolate up
- **decreaseKey**: lowers the value of the item at position `p` by a positive amount, will use percolate up to fix and maintain the order property
- **increaseKey**: increase the value of the item at position `p` by a positive amount, will use percolate down to fix and maintain the order property
- **delete**: removes the node at position `p` from the heap, this is down by first performing decreaseKey(p, Inf) and then perfroming deleteMin
- **buildHeap**: the general algorithm is to place the N items into the tree in any order, then perform percolate down
  - **Theorem**: For the perfect binary tree of height `h` containing `2^(h+1) - 1` nodes, the heights of the nodes is `2^(h+1) - 1 - (h+1)`
  - Since a complete tree has between `2^h` and `2^(h+1)` nodes, then theorem implies that this operation cost is `O(N)`, where `N` is the number of nodes

## Application of Priority Queues

### The selection problem

- The input is a list of `N` elements. Find the kth largest element

#### Solution 1

- Read `N` elements into an array, and perform **buildHeap** algorithm to this array. Finally, perform `k` **deleteMax**. The last element extracted from heap is our answer
- The total running time is `O(N + klogN)`

#### Solution 2

- Read `k` element into min heap. And continue to read remained `N - k` element, each time compare with the minimum in the heap, if the new element larger than remove the minimum one, insert the new one into heap. Finally the minimum element in the heap is our answer

- The total running time is `O(k + (N - k)logk) = O(Nlogk)`

- In the chapter 7 and chapter 10, we will see algorithm to solve this problem in `O(N)` avg and worst-case time respectively


### Event Simulation

- Event include start and end time. But we only have `k` processors to handle the envent. We need to know the waiting time of each event and how long the event list gonna be
- We only need to take action when one event start or one event end, just need to find the things that happens nearest in the future and process that event. So the waited event can be implemented as priority queue, the action we need to consider is either the next closed start or end

## d-Heaps

- A simple generalization is a **d-heap**, which is exactly like a binary heap except that all nodes have d children
- d-Heaps is efficient for situation that `insertions` is much greater than the number of `deleteMins`
- There is evidence suggesting that 4-heaps may outperform binary heaps in practice

## Leftist Heaps

- A data structure that efficiently support merging