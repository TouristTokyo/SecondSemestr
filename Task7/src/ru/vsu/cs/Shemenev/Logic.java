package ru.vsu.cs.Shemenev;

import java.util.List;

public class Logic {
    public static int checkTree(MySimpleGraph graphDefault){
        for(int i = 0; i<graphDefault.nodes.size(); i++){
            MySimpleGraph graph = graphDefault;
            Node node = graph.nodes.get(0);
            List<Node> neighborsNode = node.neighbors;
            graph.deleteNode(node);
            if(graph.countNode==graph.countEdge+1 && !graph.hasCycle(graph) && graph.componentCC(graph)==1){
                return node.value;
            }
            graph.restoreNode(node,neighborsNode,graph);
        }
        return -1;
    }
}
