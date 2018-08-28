package org.wip.VF2.graph;

//import java.awt.Label;
import java.util.ArrayList;

/**
 * Graph Class, along with some methods to manipulate the graph.
 * @author luo123n
 */
public class Graph {
  
  public String name; // name of the graph
  public ArrayList<Node> nodes = new ArrayList<Node>(); // list of all nodes
  public ArrayList<Edge> edges = new ArrayList<Edge>(); // list of all edges
  
  //private int[][] adjacencyMatrix; // stores graph structure as adjacency matrix (-1: not adjacent, >=0: the edge label)
  private String[][] adjacencyMatrix; // stores graph structure as adjacency matrix (null: not adjacent, not null: the edge label)
  private boolean adjacencyMatrixUpdateNeeded = true; // indicates if the adjacency matrix needs an update
  
  public Graph(String name) {
    this.name = name;
  }
  
  public void addNode(int id, String label) {
    nodes.add(new Node(this, id, label));
    this.adjacencyMatrixUpdateNeeded = true;
  }
  
  public void addEdge(Node source, Node target, String label) {
    edges.add(new Edge(this, source, target, label));
    this.adjacencyMatrixUpdateNeeded = true;
  }
  
  public void addEdge(int sourceId, int targetId, String label) {
    this.addEdge(this.nodes.get(sourceId), this.nodes.get(targetId), label);
  }
  
  
  /**
   * Get the adjacency matrix
   * Reconstruct it if it needs an update
   * @return Adjacency Matrix
   */
  public String[][] getAdjacencyMatrix() {
    
    if (this.adjacencyMatrixUpdateNeeded) {
      
      int k = this.nodes.size();
      this.adjacencyMatrix = new String[k][k];  // node size may have changed
      for (int i = 0 ; i < k ; i++)      // initialize entries to -1  
        for (int j = 0 ; j < k ; j++)
          this.adjacencyMatrix[i][j] = null; //-1;
      
      for (Edge e : this.edges) {
        this.adjacencyMatrix[e.source.id][e.target.id] = e.label; // label must bigger than -1
      }
      this.adjacencyMatrixUpdateNeeded = false;
    }
    return this.adjacencyMatrix;
  }
  
  // prints adjacency matrix to console
  public void printGraph() {
    StringBuilder sb = new StringBuilder();
    String[][] a = this.getAdjacencyMatrix();
    int k = a.length;
    
    sb.append(this.name + " - Nodes: ");
    for (Node n : nodes) sb.append(n.id + " ");
    sb.append("\n");
    for (int i = 0 ; i < k ; i++) {
      for (int j = 0 ; j < k ; j++) {
        sb.append(a[i][j] + " ");
      }
      sb.append("\n");
    }
    consoleLog(sb.toString());
  }
  
  public native static void consoleLog(Object obj) /*-{
    $wnd.console.log(obj);
  }-*/;
  
}
