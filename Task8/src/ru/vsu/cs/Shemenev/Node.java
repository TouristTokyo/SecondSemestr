package ru.vsu.cs.Shemenev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    public int value;
    public boolean visited = false;
    public String color = "";
    public List<Node> neighbors = new ArrayList<>();
    public List<Node> visitNeighbors = new ArrayList<>();


    public Node(int value){
        this.value = value;
    }
}
