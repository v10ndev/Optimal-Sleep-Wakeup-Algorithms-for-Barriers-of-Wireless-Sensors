package sensornetwork;

import java.util.*;

public class FordFulkerson {
    public List<List<Integer>> maxFlow(int graph[][], int source, int sink){

        int residualgraph[][] = new int[graph.length][graph[0].length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                residualgraph[i][j] = graph[i][j];
            }
        }

        Map<Integer,Integer> parent = new HashMap<>();
        
        while(BFS(residualgraph, parent, source, sink)){
            int flow = Integer.MAX_VALUE;
                       
            int v = sink;
            while(v != source){
                int u = parent.get(v);
                if (flow > residualgraph[u][v]) {
                    flow = residualgraph[u][v];
                }
                v = u;
            }
                     
            v = sink;
            while(v != source){
                int u = parent.get(v);
                residualgraph[u][v] -= flow;
                residualgraph[v][u] += flow;
                v = u;
            }
        }
        
        
        int finishGraph[][] = new int[residualgraph.length][residualgraph[0].length];
        for (int i = 0; i < residualgraph.length; i++) {
            for (int j = 0; j < residualgraph[0].length; j++) {
            	if((residualgraph[i][j]!=graph[i][j]))
                finishGraph[j][i] = residualgraph[i][j];
            }
        }      
        	
  	
  	List<List<Integer>> disjointPaths = new ArrayList<>();
  	
  	
      while(BFS(finishGraph, parent, source, sink)){
    	        
          List<Integer> disjointPath = new ArrayList<>();
     	  
    	  int v = sink;
          while(v != source){
        	  disjointPath.add(v); 
              int u = parent.get(v);
              finishGraph[u][v]=0;
              v = u;   
          }          
          disjointPath.add(source); 
          Collections.reverse(disjointPath);
          disjointPaths.add(disjointPath);             
    }
       
      return disjointPaths;
}
  
    /**
     * Breadth first search để tìm các đường tăng luồng
     */
    private boolean BFS(int[][] residualgraph, Map<Integer,Integer> parent,
            int source, int sink){
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        boolean foundAugmentedPath = false;
        while(!queue.isEmpty()){
            int u = queue.poll();
            for(int v = 0; v < residualgraph.length; v++){
                if(!visited.contains(v) &&  residualgraph[u][v] > 0){
                    parent.put(v, u);
                    visited.add(v);
                    queue.add(v);
                    if ( v == sink) {
                        foundAugmentedPath = true;
                        break;
                    }
                }
            }
        }
        return foundAugmentedPath;
    }
    
    public FordFulkerson() {}
       
}
