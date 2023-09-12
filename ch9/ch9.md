## Definition

- A **simple path** is a path such that all vertices are distinct, except that the first and last could be the same
- A directed acyclic graph is sometimes referred to by its abbreviation, **DAG**
- An undirected graph is **connected** if there is a path from every vertex to every other vertex
  - A directed graph with this property is called **strongly connected**
  - A directed graph is not strongly connected, but the underlying graph (without direction to the arcs) is connected, then the graph is said to be **weakly connected**
  - A **complete graph** is a graph in which there is an edge between every pair of vertices
- Example can be represented by graph:
  - airport system
  - Traffic flow

- If the graph is dense, we can consider using **adjacency matrix** representation
- If the graph is sparse, a better solution is an **adjacency list** representation

## Topological Sort

- A topological ordering is not possible if the graph has a cycle
- Initial program
```java
void topsort() throws CycleFoundException {
  for (int counter = 0; counter < NUM_VERTICES; counter++) {
    Vertex v = findNewVertexOfIndegreeZero();
    if (v == null) {
      throw new CycleFoundException();
    }
    v.topNum = counter;
    for each Vertex w adjacent to v
      w.indegree--;
  }
}
```
  - The running time is `O(V^2)`, beacuse we scan the array of vertex everytime
- Program after optimized
```java
void topsort() throws CycleFoundException {
  Queue<Vertex> q = new Queue<>();
  int counter = 0;
  for each Vertex v
    if (v.indegree == 0) {
      q.enqueue(v);
    }

  while (!q.isEmpty()) {
    Vertex v = q.dequeue();
    v.topNum = ++counter;
    for each Vertex w adjacent to v
      if (--w.indegree == 0) {
        q.enqueue(w);
      }
  }
  if (counter != NUM_VERTICES) {
    throw new CycleFoundException();
  }
}
```
  - Running is `O(V + E)`