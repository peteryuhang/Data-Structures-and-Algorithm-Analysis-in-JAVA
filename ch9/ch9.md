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

## Shortest-Path Algorithms

- **Single-Source Shortest-Path Problem**
  - Given as input a weighted graph, `G = (V,E)`, and a distinguished vertex, `s`, find the shortest weight path from `s` to every other vertex in `G`
- Edges of negative cost will make **negative-cost cycle** which make the problem harder

### Unweighted Shortest Paths

- Pseudocode
```java
void unweighted(Vertex s) {
  Queue<Vertex> q = new Queue<Vertex>();

  for each Vertex v
    v.dist = INFINITY;
  
  s.dist = 0;
  q.enqueue(s);

  while (!q.isEmpty()) {
    Vertex v = q.dequeue();
    for each Vertex w adjacent to v
      if (w.dist == INFINITY) {
        w.dist = v.dist + 1;
        w.path = v;
        q.enqueue(w);
      }
  }
}
```
- Based on the **breadth-first search**. It operates by processing vertices in layers

### Dijkstra's Algorithm

- Prime example of a **greedy algorithm**

```java
class Vertex {
  public List adj;
  public boolean known;
  public DistType dist;
  public Vertex path;
}

void printPath(Vertex x) {
  if (v.path != null) {
    printPath(v.path);
    System.out.print(" to ");
  }
  System.out.print(v);
}

void dijkstra(Vertex s) {
  for each Vertex v {
    v.dist = INFINITY;
    v.known = false;
  }

  s.dist = 0;

  while (there is an unknown distance vertex) {
    Vertex v = smallest unknown distance vertex;

    v.known = true;
    for each Vertex w adjacent to v
      if (!w.known) {
        DisType cvw = cost of edge from v to w;

        if (v.dist + cvw < w.dist) {
          // update
          decrease(w.dist to v.dist + cvw);
          w.path = v;
        }
      }
  }
}
```

- The running depending on how the vertices are manipulated
  - If sequenfially scanning the vertices to found smallest, the running time will be `O(V^2)`
  - Using priority queue (2 ways)
    - `deleteMin` + `decreaseKey` -> `O(ElogV + VlogV) = O(ElogV)`
    - Insert every time, since `E <= V^2`, so `logE < 2logV` -> `O(ElogV)` (likely to be slower in pratice)