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