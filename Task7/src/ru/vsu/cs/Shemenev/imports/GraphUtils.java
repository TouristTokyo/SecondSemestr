package ru.vsu.cs.Shemenev.imports;

import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import ru.vsu.cs.Shemenev.MySimpleGraph;
import ru.vsu.cs.Shemenev.Node;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Утилиты работы с графами
 */
public class GraphUtils {


    public static Graph fromStr(String str, Class clz) throws IOException, InstantiationException, IllegalAccessException {
        Graph graph = (Graph) clz.newInstance();
        Map<String, Integer> names = new HashMap<>();
        int vertexCount = 0;
        if (Pattern.compile("^\\s*(strict\\s+)?(graph|digraph)\\s*\\{").matcher(str).find()) {
            // dot-формат
            MutableGraph g = new Parser().read(str);
            vertexCount = g.nodes().size();
            graph.addAdge(vertexCount - 1, vertexCount - 1);
            graph.removeAdge(vertexCount - 1, vertexCount - 1);

            // проверка, являются ли все вершины целыми (-2 - не являются)
            Pattern intPattern = Pattern.compile("^\\d+$");
            int maxVertex = -1;
            for (Link l : g.links()) {
                String fromStr = l.from().toString();
                if (intPattern.matcher(fromStr).matches()) {
                    maxVertex = Math.max(maxVertex, Integer.parseInt(fromStr));
                } else {
                    maxVertex = -2;
                    break;
                }
                String toStr = l.from().toString();
                if (intPattern.matcher(toStr).matches()) {
                    maxVertex = Math.max(maxVertex, Integer.parseInt(toStr));
                } else {
                    maxVertex = -2;
                    break;
                }
            }
            vertexCount = 0;
            for (Link l : g.links()) {
                String fromStr = l.from().toString();
                Integer from = null;
                if (maxVertex == -2) {
                    from = names.get(fromStr);
                    if (from == null) {
                        from = vertexCount;
                        names.put(fromStr, from);
                        vertexCount++;
                    }
                } else {
                    from = Integer.parseInt(fromStr);
                }
                String toStr = l.to().toString();
                Integer to = null;
                if (maxVertex == -2) {
                    to = names.get(toStr);
                    if (to == null) {
                        to = vertexCount;
                        names.put(toStr, to);
                        vertexCount++;
                    }
                } else {
                    to = Integer.parseInt(toStr);
                }
                graph.addAdge(from, to);
            }
        } else if (Pattern.compile("^\\s*\\d+").matcher(str).find()) {
            Scanner scanner = new Scanner(str);
            vertexCount = scanner.nextInt();
            int edgeCount = scanner.nextInt();
            for (int i = 0; i < edgeCount; i++) {
                graph.addAdge(scanner.nextInt(), scanner.nextInt());
            }
        } else {
            Scanner scanner = new Scanner(str);
            vertexCount = scanner.nextInt();
            while (scanner.hasNext()) {
                String fromStr = scanner.next();
                Integer from = names.get(fromStr);
                if (from == null) {
                    from = vertexCount;
                    names.put(fromStr, from);
                    vertexCount++;
                }
                String toStr = scanner.next();
                Integer to = names.get(toStr);
                if (to == null) {
                    to = vertexCount;
                    names.put(toStr, to);
                    vertexCount++;
                }
                graph.addAdge(from, to);
            }
        }

        return graph;
    }

    public static int[][] makeGraph(int vertexCount) {
        Random rnd = new Random(System.currentTimeMillis());

        int edgeCount = rnd.nextInt(((vertexCount*(vertexCount-1))/2 - vertexCount) + 1) + vertexCount;
        System.out.println(edgeCount);
        LinkedList<Integer> listOfVertex = new LinkedList<>();
        for (int i = 1; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if((i-j<0) || (j==0)) {
                    listOfVertex.add(i * 10 + j);
                }
            }
        }

        int[][] arrOfEdges = new int[edgeCount][2];
        arrOfEdges[0][0] = listOfVertex.getFirst()/10;
        arrOfEdges[0][1] = listOfVertex.getFirst()%10;
        listOfVertex.removeFirst();
        for (int i = 1; i < vertexCount - 1; i++){
            int index = listOfVertex.indexOf(11*i + 1);
            arrOfEdges[i][0] =  listOfVertex.get(index)/10;
            arrOfEdges[i][1] =  listOfVertex.get(index)%10;
            listOfVertex.remove(index);
        }
        arrOfEdges[vertexCount - 1][0] = listOfVertex.get(listOfVertex.indexOf(10*(vertexCount - 1)))/10;
        arrOfEdges[vertexCount - 1][1] = listOfVertex.get(listOfVertex.indexOf(10*(vertexCount - 1)))%10;
        listOfVertex.remove(listOfVertex.indexOf(10*(vertexCount - 1)));

        if (edgeCount > vertexCount){
            for (int i = 0; i < edgeCount - vertexCount; i++){
                int index = (int) (Math.random() * listOfVertex.size());
                arrOfEdges[i + vertexCount][0] = listOfVertex.get(index)/10;
                arrOfEdges[i + vertexCount][1] = listOfVertex.get(index)%10;
                listOfVertex.remove(index);

            }
        }
            return arrOfEdges;
        }

        public static int[][] createMatrix(MySimpleGraph graph){
            int[][] matrix = new int[graph.countEdge][2];
            int index = 0;
            for(Node currNode : graph.nodes){
                for(Node other : currNode.neighbors) {
                    if (currNode.setNeighborsVisited.contains(other)) {
                        continue;
                    }
                    matrix[index][0] = currNode.value;
                    matrix[index++][1] = other.value;
                    currNode.setNeighborsVisited.add(other);
                    other.setNeighborsVisited.add(currNode);
                }
            }
            return matrix;
        }

        public static Graph fromArr(int[][] arr){
            Graph graph = new AdjMatrixGraph();
            for (int i = 0; i < arr.length; i++) {
                graph.addAdge(arr[i][0], arr[i][1]);
            }
            return graph;
        }

        public static MySimpleGraph createGraph(int[] input){
            MySimpleGraph graph = new MySimpleGraph();
            int n  = input[0]; // Кол-во вершин
            int m = input[1]; // Кол-во ребер
            for(int i =0; i<n;i++){
                graph.addNode(i+1);
            }
            int index = 2;
            for(int i = 0; i<m;i++){
                int u = input[index++];
                int v = input[index++];
                Node node1 = graph.nodes.get(u-1);
                Node node2 = graph.nodes.get(v-1);
                graph.addEdge(node1,node2);
            }
            return graph;
        }

}

