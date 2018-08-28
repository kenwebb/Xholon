package org.wip.VF2.runner;

import java.util.ArrayList;
import org.wip.VF2.core.State;
import org.wip.VF2.core.VF2;
import org.wip.VF2.graph.Graph;

/**
 * Tests:
$wnd.xh.matchGraph("one","two", ",");
 * 
var graphStr = "t # 0,v 0 2,v 1 2,v 2 2,v 3 3,v 4 2,v 5 4,v 6 2,v 7 3,v 8 3,v 9 3,v 10 2,v 11 2,v 12 2,v 13 2,v 14 2,v 15 2,v 16 2,v 17 2,v 18 2,e 0 1 2,e 0 2 2,e 2 3 3,e 2 4 2,e 3 5 2,e 4 6 2,e 5 7 2,e 5 8 2,e 5 9 2,e 6 10 2,e 6 9 3,e 7 11 3,e 8 12 3,e 10 13 2,e 11 14 2,e 11 15 2,e 12 16 2,e 12 15 2,e 14 17 2,e 16 18 2,t # -1 ";

// the following does not match
var subgraphStr = "t # 0,v 0 2,v 1 2,v 2 2,v 3 2,v 4 2,v 5 2,v 6 2,v 7 2,v 8 6,v 9 2,v 10 2,v 11 2,v 12 3,v 13 3,v 14 3,v 15 2,v 16 2,v 17 5,v 18 2,v 19 2,e 0 1 2,e 0 2 2,e 0 3 3,e 1 4 5,e 1 5 5,e 2 6 5,e 3 7 2,e 4 8 2,e 4 9 5,e 5 10 5,e 6 11 5,e 8 12 3,e 8 13 3,e 8 14 2,e 9 15 5,e 10 15 5,e 11 16 5,e 16 17 2,e 17 18 2,e 17 19 2,t # -1";

// the following should match, but it doesn't
var subgraphStr = "t # 0,v 0 2,v 1 2,v 2 2,v 3 3,v 4 2,e 0 1 2,e 0 2 2,e 2 3 3,e 2 4 2,t # -1";

var result = $wnd.xh.matchGraph(graphStr,subgraphStr, ",");
$wnd.console.log(result);

// this matches (it only contains vertices)
var result = xh.matchGraph("t # 0,v 0 2,v 1 3,t # -1", "t # 0,v 0 2,t # -1", ",");
// Found 1 maps for: Query 0
// (0-0)

// this matches (it only contains vertices)
var graphStr = "t # 0,v 0 2,v 1 3,t # -1";
var subgraphStr = "t # 0,v 0 2,t # 1,v 1 3,t # -1";
var result = xh.matchGraph(graphStr, subgraphStr, ",");
console.log(result);

// this matches
var graphStr = "t # 0,v 0 2,v 1 3,t # -1";
var subgraphStr = "t # 0,v 0 2,v 1 3,t # -1";
var result = xh.matchGraph(graphStr, subgraphStr, ",");
console.log(result);
// Found 1 maps for: Query 0
// 
// Maps for: Query 0
// In: Graph 0
// (0-0) (1-1) 

// this has an edge and does not match
var graphStr = "t # 0,v 0 2,v 1 3,e 0 1 9,t # -1";
var subgraphStr = "t # 0,v 0 2,v 1 3,t # -1";
var result = xh.matchGraph(graphStr, subgraphStr, ",");
console.log(result);

// this fails
// java.lang.NumberFormatException: For input string: "zero"
var graphStr = "t # 0,v 0 zero,v 1 one,t # -1";
var subgraphStr = "t # 0,v 0 zero,v 1 one,t # -1";
var result = xh.matchGraph(graphStr, subgraphStr, ",");
console.log(result);

 */
public class App {
  
  protected static final String DEFAULT_SEPARATOR = "\n";
  
  public static String matchGraph(String graphStr, String subgraphStr, String separator) {
    if ((graphStr == null) || (subgraphStr == null)) {
      printUsage();
      consoleLog("Warning: no arguments given");
      return null;
    }
    if (separator == null) {
      separator = DEFAULT_SEPARATOR;
    }
    StringBuilder outSb = new StringBuilder();

    ArrayList<Graph> graphSet = loadGraphSetFromStr(graphStr, "Graph ", separator);
    ArrayList<Graph> querySet = loadGraphSetFromStr(subgraphStr, "Query ", separator);
    //consoleLog("Loading Done!");

    VF2 vf2= new VF2();
    //int queryCnt = 0;
    for (Graph queryGraph : querySet) {
      //queryCnt++;
      ArrayList<State> stateSet = vf2.matchGraphSetWithQuery(graphSet, queryGraph);
      if (stateSet.isEmpty()){
        //consoleLog("Cannot find a map for: " + queryGraph.name);
        outSb.append("Cannot find a map for: " + queryGraph.name + "\n\n");
      } else {
        //consoleLog("Found " + stateSet.size() + " maps for: " + queryGraph.name);
        outSb.append("Maps for: " + queryGraph.name + "\n");
        for (State state : stateSet){
          outSb.append("In: " + state.targetGraph.name + "\n");
          state.writeMapping(outSb);
        }    
        outSb.append("\n");
      }
    }
    return outSb.toString();
  }
  
  /**
   * Load graph set from String
   * @param instr Input String
   * @param namePrefix The prefix of the names of graphs
   * @return Graph Set
   */
  private static ArrayList<Graph> loadGraphSetFromStr(String instr, String namePrefix, String separator) {
    ArrayList<Graph> graphSet = new ArrayList<Graph>();
    String[] instrArr = instr.split(separator);
    Graph graph = null;
    for (int i = 0; i < instrArr.length; i++) {
      String line = instrArr[i].trim();
      //consoleLog(line);
      if (line.equals("")){
        continue;
      } else if (line.startsWith("t")) {
        String graphId = line.split(" ")[2];
        if (graph != null){
          graphSet.add(graph);
        }
        graph = new Graph(namePrefix + graphId);
      } else if (line.startsWith("v")) {
        String[] lineSplit = line.split(" ");
        int nodeId = Integer.parseInt(lineSplit[1]);
        //int nodeLabel = Integer.parseInt(lineSplit[2]);
        String nodeLabel = lineSplit[2];
        graph.addNode(nodeId, nodeLabel);
      } else if (line.startsWith("e")) {
        String[] lineSplit = line.split(" ");
        int sourceId = Integer.parseInt(lineSplit[1]);
        int targetId = Integer.parseInt(lineSplit[2]);
        //int edgeLabel = Integer.parseInt(lineSplit[3]);
        String edgeLabel = lineSplit[3];
        graph.addEdge(sourceId, targetId, edgeLabel);
      }
    }
    return graphSet;
  }
  
  private static void printUsage(){
    consoleLog("Usage: TODO");
  }
  
  private native static void consoleLog(Object obj) /*-{
    $wnd.console.log(obj);
  }-*/;
  
}
