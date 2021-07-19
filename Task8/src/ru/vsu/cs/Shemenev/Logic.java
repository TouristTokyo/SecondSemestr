package ru.vsu.cs.Shemenev;

import util.SwingUtils;

import java.util.ArrayList;
import java.util.List;

public class Logic {

    public static int checkPaintGraph(MySimpleGraph graph){
        List<Node> countBlueNodes = new ArrayList<>();
        List<Node> countRedNodes = new ArrayList<>();
        for (int i = 0; i < graph.nodes.size(); i++) {
            Node node = graph.nodes.get(i);
            if (node.color.equals("red")) {
                for (Node currNode : node.neighbors) {
                    if (currNode.color.equals("red")) {
                        return -1;
                    }
                }
            } else {
                node.color = "blue";
                node.visited = true;
                countBlueNodes.add(node);
                for (Node currNode : node.neighbors) {
                    currNode.color = "red";
                    if(!currNode.visited){
                        currNode.visited = true;
                        countRedNodes.add(currNode);
                    }
                }
            }
        }
        for(int i = 0; i<countBlueNodes.size();i++){
            Node node = countBlueNodes.get(i);
            if(node.neighbors.size()!=countRedNodes.size()){
                return 0;
            }
        }
        return 1;
    }
}
