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