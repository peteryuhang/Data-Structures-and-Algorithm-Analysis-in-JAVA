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

### Kruskal's Algorithm

- Continue to select the edges in order of smallest weight and accept an edge if it does not cause a cycle
- Union/Find algorithm will be used here 

```java
ArrayList<Edge> kruskal(List<Edge> edges, int numVertices) {
  DisjSets ds = new DisjSets(numVertices);
  PriorityQueue<Edge> pq = new PriorityQueue<>(edges);
  List<Edge> mst = new ArrayList<>();

  while (mst.size() != numVertices - 1) {
    Edge e = pq.deleteMin();
    SetType uset = ds.find(e.getu());
    SetType vset = ds.find(e.getv());

    if (uset != vset) {
      mst.add(e);
      ds.union(uset, vset);
    }
  }
  return mst;
}
```

- The worst-case running time is `O(Elog(E))`, since `E = O(V^2)`, so `O(Elog(V))`


## Applications of Depth-First Search

- Template for depth-first search

```java
void dfs(Vertex v) {
  v.visited = true;
  for each Vertex w adjacent to v
    if (!w.visited)
      dfs(w);
}
```
  - Total time for performing traversal is `O(E + V)`

### Undirected Graphs

- An undirected graph is connected if and only if a depth-first search starting from any node visited every node
- Some concept when we perform dfs on graph
  - **depth-first spanning tree**
  - **back edge**
  - **depth-first spanning forest**

### Biconnectivity

- A connected undirected graph is **biconnected** if there are no vertices whose removal disconnects the rest of the graph
- If the graph is not biconnected, the vertices whose removal would disconnect the graph are known as **articulation points**. These node are critical in many applications
- Process for finding articulation points:
```java
// performing preorder traversal to get preorder number and compute parent for each vertex
void assignNum(Vertex v) {
  v.num = counter++;
  v.visited = true;
  for each Vertex w adjacent to v
    if (!w.visited) {
      w.parent = v;
      assignNum(w);
    }
}

// postorder traversal to get lowest-number; also check for articulation points; doesn't consider the root special case

// Low(v) is the minimum of
// 1. Num(v)
// 2. The lowest Num(w) among all back edges (v,w)
// 3. The lowest Low(w) among all tree edges (v,w)

// The root is an articulation point if and only if it has more than one child
// Any other vertex v is an articulation point if and only if v has some child w such that Low(w) >= Num(v)
void assignLow(Vertex v) {
  v.low = v.num;                      // Rule 1
  for each Vertex w adjacent to v {
    if (w.num > v.num) {              // Forward edge
      assignLow(w);
      if (w.low >= v.num)
        System.out.println(v + " is an articulation point");
      v.low = min(v.low, w.low);      // Rule 3
    } else if (v.parent != w) {       // Back edge (ignore the edge which already been considered)
      v.low = min(v.low, w.num);      // Rule 2
    }
  }
}
```

- Combination of `assignNum` and `assignLow`
```java
void findArt(Vertex v) {
  v.visited = true;
  v.low = v.num = counter++;          // Rule 1
  for each Vertex w adjacent to v {
    if (!w.visited) {
      w.parent = v;
      findArt(w);
      if (w.low >= v.num)
        System.out.println(v + " is an articulation point");
      v.low = min(v.low, w.low);      // Rule 3
    } else if (v.parent != w) {       // Back edge
      v.low = min(v.low, w.num);      // Rule 2
    }
  }
}
```

### Euler Circuits

- Any connected graph, all of whose vertices have even degree, must have an **Euler circuit**
- Steps:
  - Find a circle path on the graph by using dfs
  - Find the first vertex on this path which has untraversed edge
  - Start from this vertex and find circle path which only contains untraversed edge
  - Splice the circle path on step 3 into circle path on step 1
  - Repeat the process above until we traverse all edges
- If appropriate data structures been used, the running time of the algorithm is `O(E + V)`
- **Hamiltonian cycle problem** is similar to this one, which is to find a simple cycle, in an undirected graph, that visited every vertex

### Directed Graphs

- If the graph is not **strongly connected**, we need to perfrom multiple dfs to visit all nodes on graph
- Three type nontree edges during traversal:
  - **Back Edges**: Lead from a tree node to a parent
  - **Forward Edges**: Lead from a tree node to a descendant
  - **Cross Edges**: Connect two tree nodes that are not directly related
- A directed graph is acyclic if and only if it has no back edges
  - **Topological sort** can also be used to determine whether a graph is acyclic
- Another way to perform topological sorting is to assign the vertices topological numbers `N, N - 1, ..., 1` by postorder traversal of the depth-first spanning forest