## Binary Heap

- A heap is binary tree that is completely filled, with the possible exeception of the bottom level, which is filled from left to right (complete binary tree)
- Heap can be represent by array, for any element in array, the left child is in position `2*i`, the right child is in the cell after the left child `2*i+1`, and the parent is in position `floor(i/2)`
- **Heap-order property**: min heap as example, node should be smaller than all of its descendants