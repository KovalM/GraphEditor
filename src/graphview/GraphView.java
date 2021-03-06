package graphview;

import graph.GraphModel;

import javax.management.monitor.Monitor;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GraphView implements Runnable{
    private GraphModel graphRoot;
    private Thread thread;
    public List<VertexView> getVertexesGraph() {
        return vertexesGraph;
    }

    public void setVertexesGraph(List<VertexView> vertexesGraph) {
        this.vertexesGraph = vertexesGraph;
    }

    private List<VertexView> vertexesGraph;
    private WorkingArea boxDrawing;
    private VertexView start;

    public GraphView(WorkingArea boxDrawing){
        this.boxDrawing = boxDrawing;
        graphRoot = new GraphModel();
        vertexesGraph = new ArrayList<VertexView>();
        start = null;
        thread = new Thread(this);
    }

    public void addVertex(VertexView newVertex){
        vertexesGraph.add(newVertex);
    }

    public void removeVertex(VertexView oldVertex){
        vertexesGraph.remove(oldVertex);
    }

    public GraphModel getGraphRoot() {
        return graphRoot;
    }

    public void setGraphRoot(GraphModel graphRoot) {
        this.graphRoot = graphRoot;
    }

    public void showNumberVertex(){
        JOptionPane.showMessageDialog(boxDrawing,boxDrawing.getGraphView().getVertexesGraph().size());
    }

    public void showNumberEdge(){
        JOptionPane.showMessageDialog(boxDrawing,boxDrawing.getAllEdges().getNumberAllEdges());
    }

    private int minPath;
    private EdgesList answerPath;
    private EdgesList currentPath;
    private VertexView startAlg;
    private VertexView finishAlg;

    public void showMinPath(VertexView start,VertexView finish){
        startAlg = start;
        finishAlg = finish;
        thread.start();
    }

    private void dfs(VertexView currentVertex,VertexView finish,int lengthPath){
        if (currentVertex.equals(finish)){
            if (lengthPath < minPath){
                minPath = lengthPath;
                if (answerPath != null){
                    int count = answerPath.getNumberAllEdges();
                    boxDrawing.setColorBuffer(Color.black);
                    System.out.println("Erase old path " + count);
                    for (int i = 0; i < count; i++) {
                        boxDrawing.drawEdge(answerPath.getEdgesAtIndex(i));
                    }
                    answerPath = null;
                }

                int count = currentPath.getNumberAllEdges();
                answerPath = new EdgesList();
                for (int i = 0; i < count; i++) {
                    answerPath.addEdges(currentPath.getEdgesAtIndex(i));
                }

                count = answerPath.getNumberAllEdges();
                System.out.println("Create new path " + count);
                boxDrawing.setColorBuffer(Color.green);
                for (int i = 0; i < count; i++) {
                    boxDrawing.drawEdge(answerPath.getEdgesAtIndex(i));
                }
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                boxDrawing.setColorBuffer(Color.black);
            }
        }
        else{
            int n = currentVertex.getNumberEdges();
            for (int i = 0; i < n; i++) {
                EdgeView currentEdge = currentVertex.getEdgesAtIndex(i);
                if (currentEdge.getStart().equals(currentVertex)){
                    VertexView candidate = currentEdge.getFinish();

                    int m = currentPath.getNumberAllEdges();
                    boolean flag = false;

                    for (int j = 0; j < m; j++){
                        if (currentPath.getEdgesAtIndex(j).getStart().equals(candidate) ||
                                currentPath.getEdgesAtIndex(j).getFinish().equals(candidate)){
                            flag = true;
                            break;
                        }
                    }
                    if (!flag){
                        currentPath.addEdges(currentEdge);
                        System.out.println("draw in yellow");
                        boxDrawing.setColorBuffer(Color.yellow);
                        boxDrawing.drawEdge(currentEdge);
                        boxDrawing.setColorBuffer(Color.black);
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        dfs(candidate, finish, lengthPath + currentEdge.getEdgeRoot().getWeight());

                        flag = false;
                        if (answerPath != null) {
                            int count = answerPath.getNumberAllEdges();
                            for (int j = 0; j < count; j++) {
                                if (answerPath.getEdgesAtIndex(j).equals(currentEdge)) {
                                    flag = true;
                                    break;
                                }
                            }
                        }

                        if (!flag){
                            System.out.println("draw in black");
                            boxDrawing.setColorBuffer(Color.black);
                            boxDrawing.drawEdge(currentEdge);
                        }
                        currentPath.removeEdge(currentEdge);
                    }
                }
            }
        }
    }

    public VertexView getStart() {
        return start;
    }

    public void setStart(VertexView start) {
        this.start = start;
    }

    @Override
    public void run() {
        answerPath = null;
        currentPath = new EdgesList();
        minPath = 99999999;
        boxDrawing.setDoubleBuffered(false);

        dfs(startAlg,finishAlg,0);

        boxDrawing.setDoubleBuffered(true);
        if (minPath == 99999999){
            JOptionPane.showMessageDialog(boxDrawing,"Minimum path does not exist");
        } else{
            JOptionPane.showMessageDialog(boxDrawing,minPath);
        }

        if (null != answerPath) {
            int count = answerPath.getNumberAllEdges();
            boxDrawing.setColorBuffer(Color.black);
            for (int i = 0; i < count; i++) {
                boxDrawing.drawEdge(answerPath.getEdgesAtIndex(i));
            }
        }
    }
}
