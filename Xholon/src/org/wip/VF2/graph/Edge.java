package org.wip.VF2.graph;

public class Edge {
  
  public Graph graph; // the graph to which the edge belongs
  
  public Node source; // the source / origin of the edge
  public Node target; // the target / destination of the edge 
  public String label; // the label of this edge
  
  // creates new edge
  public Edge(Graph g, Node source, Node target, String label) {
    this.graph = g;
    this.source = source; // store source
    source.outEdges.add(this); // update edge list at source
    this.target = target; // store target
    target.inEdges.add(this); // update edge list at target
    this.label = label;
  }
  
}
