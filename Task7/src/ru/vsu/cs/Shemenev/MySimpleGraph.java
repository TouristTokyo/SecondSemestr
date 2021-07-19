package ru.vsu.cs.Shemenev;

import java.util.ArrayList;
import java.util.List;

public class MySimpleGraph {
    public List<Node> nodes = new ArrayList<>();
    public int countNode = 0;
    public int countEdge = 0;
    private boolean visit = false;

    public void addNode(int value) {
        nodes.add(new Node(value));
        countNode++;
    }

    public void addEdge(Node a, Node b) {
        a.neighbors.add(b);
        b.neighbors.add(a);
        countEdge++;
    }

    public void addEdgeOneNode(Node a, Node b){
        a.neighbors.add(b);
        countEdge++;
    }

    public void deleteEdge(Node a, Node b) {
        a.neighbors.remove(b);
        b.neighbors.remove(a);
        countEdge--;
    }

    private void deleteEdgeOneNode(Node a, Node b) {
        a.neighbors.remove(b);
        countEdge--;
    }

    public void deleteNode(Node a){
        for(Node currNode : a.neighbors){
            deleteEdgeOneNode(currNode,a);
        }
        nodes.remove(a);
        countNode--;
    }

    private void defaultGraph(MySimpleGraph graph){
        for(Node currNode: graph.nodes){
            currNode.visited = false;
        }
    }

    public boolean hasCycle(MySimpleGraph graph){
        if(visit) {
            defaultGraph(graph);
        }
        for(int i = 0; i<graph.nodes.size(); i++){
            visit = true;
            Node node = graph.nodes.get(i);
            if(!node.visited && cycleDfs(node,null)){
                return true;
            }
        }
        return false;
    }

    private static boolean cycleDfs(Node node, Node prev){
        if(node.visited){
            return true;
        }
        node.visited = true;
        for (Node currNode : node.neighbors){
            if(prev!=currNode && cycleDfs(currNode,node)){
                return true;
            }
        }
        return false;
    }
    public int componentCC(MySimpleGraph graph){
        if(visit) {
            defaultGraph(graph);
        }
        int cc = 0;
        for(int i = 0; i<graph.nodes.size(); i++){
            Node node = graph.nodes.get(i);
            if(!node.visited){
                cc++;
                dfs(node);
            }
        }
        return cc;
    }

    public void restoreNode(Node node, List<Node> neighbors, MySimpleGraph graph){
        graph.nodes.add(node);
        for(Node currNode : neighbors){
            graph.addEdgeOneNode(currNode,node);
        }
        countNode++;
    }
    /**
     * Обход графа рекурсивно
     */
    private void dfs(Node node){
        if(node.visited){
            return;
        }
        node.visited = true;
        for(Node currNode: node.neighbors){
            dfs(currNode);
        }
    }
}
