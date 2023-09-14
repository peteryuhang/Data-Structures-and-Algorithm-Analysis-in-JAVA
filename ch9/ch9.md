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

### Graphs with Negative Edge Costs

- If negative edge been allowed in the graph, then previous Dijkstra's algorithm won't work anymore. Because once the vertex been declare **known**, it is still possible to find a more negative/smaller path back to it
- A combination of weighted and unweighted algorithms will solve the problem

```java
void weightedNegative(Vertex s) {
  Queue<Vertex> q = new Queue<Vertex>();

  for each Vertex v
    v.dist = INFINITY;
  
  s.dist = 0;
  q.enqueue(s);
  while (!q.isEmpty()) {
    Vertex v = q.dequeue();
    for each Vertex w adjacent to v
      if (v.dist + cvw < w.dist) {
        w.dist = v.dist + cvw;
        w.path = v;
        if (w is not already in q)
          q.enqueue(w);
      }
  }
}
```

### Acyclic Graphs

- If the graph is known to be acyclic, we can improve Dijkstra's algorithm by selecting vertices in **topological order**, because for every selected vertex, there is no incoming edges so the distance can no longer be lower
- Important use of acyclic graphs - **critical path analysis**, some important concept:
  - event-node graph
  - earliest completion time
  - latest time
  - slack time

### Shortest-Path Example

- word ladders

```java
public static List<String> findChain(Map<String, List<String>> adjacentWords, String first, String second) {
  Map<String, String> previousWord = new HashMap<>();
  LinkedList<String> q = new LinkedList<>();

  q.addLast(first);
  while (!q.isEmpty()) {
    String current = q.removeFirst();
    List<String> adj = adjacentWords.get(current);
    if (adj != null) {
      for (String adjWord : adj) {
        if (previousWord.get(adjWord) == null) {
          previousWord.put(adjWord, current);
          q.addLast(adjWord);
        }
      }
    }
  }

  previousWord.put(first, null);
  return getChainFromPreviousMap(previousWord, first, second);
}

public static List<String> getChainFromPreviousMap(Map<String, List<String>> adjacentWords, String first, String second) {
  LinkedList<String> result = null;

  if (prev.get(second) != null) {
    result = new LinkedList<String>();
    for (String str = second; str != null; str = prev.get(str)) {
      result.addFist(str);
    }
  }

  return result;
}
```


## Network Flow Problems

- To determine the maximum amount of flow that can pass from `s` (source) to `t` (target/sink)
- The minimum cut capacity is exactly equal to the maximum flow
- We use **flow graph** and **residual graph** to help found augmenting path and to solve the problem

## Minimum Spanning Tree

- A minimum spanning tree of an undirected graph `G` is a tree formed from graph edges that connects all the vertices of `G` at lowest total cost
- The minimum spanning tree is a **tree** because it is acyclic, it is **spanning** becuase it covers every vertex, and it is **minimum** for the obvious reason
- Greed works for the minimum tree problem

### Prim's Algorithm

- Similar to Dijkstra's algorithm, every time we pick up a vertex, by choosing the edge `(u, v)` such that the cost of `(u, v)` is the smallest among all edges where `u` is in the tree and `v` is not. Beside the first pick, each step adds one edge and one vertex to the tree
- For each vertex we keep `dv`, which represent shortest edge connecting `v` to a **known** vertex (vertex in the tree) and `pv` is the connecting vertex in the tree. We also keep an indication of whether it is known or unknown
- The entire implementation of this algorithm is virtually identical to that of Dijkstra's algorithm, and everything that was said about the analysis of Dijkstra's algorithm applies here
