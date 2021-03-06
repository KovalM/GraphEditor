package graphview;

import constants.EdgeConst;
import constants.VertexConst;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class WorkingArea extends JPanel {
    private EdgeView currentEdge;
    private EdgesList allEdges;
    private BufferedImage buffer;
    private BufferedImage temp;
    private Color colorOfLine;
    private EdgeView greenEdge;
    private GraphView graphView;

    public void setColorBuffer(Color clr){
        colorOfLine = clr;
    }

    public WorkingArea(){
        super();
        temp = new BufferedImage(1500,1500,BufferedImage.TYPE_INT_ARGB);
        buffer = new BufferedImage(1500,1500,BufferedImage.TYPE_INT_ARGB);
        allEdges = new EdgesList();
        colorOfLine = Color.black;
        graphView = new GraphView(this);
    }

    public EdgeView getCurrentEdge() {
        return currentEdge;
    }

    public void setCurrentEdge(EdgeView currentEdge) {
        this.currentEdge = currentEdge;
    }

    public void drawLoop(EdgeView current){
        Graphics pain = buffer.getGraphics();
        Graphics2D painter = (Graphics2D)pain;
        int x1,x2,x3,y1,y2,y3;

        painter.setStroke(new BasicStroke(4));
        painter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        painter.setBackground(new Color(255,255,255,0));
        painter.setColor(colorOfLine);

        x1 = current.getStart().getX() + VertexConst.VERTEX_SIZE_X / 2;
        y1 = current.getStart().getY() + VertexConst.VERTEX_SIZE_Y / 2;
        x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
        y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 - 25;
        x3 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
        y3 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 + 25;
        painter.drawLine(x1,y1,x2,y2);
        painter.drawLine(x2,y2,x3,y3);
        painter.drawLine(x1,y1,x3,y3);
        drawTip(painter,x2,y2,x1,y1);
        repaint();
    }

    public void drawEdge(EdgeView current){
        Graphics pain = buffer.getGraphics();
        Graphics2D painter = (Graphics2D)pain;
        int x1,x2,y1,y2;

        painter.setStroke(new BasicStroke(4));
        painter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        painter.setBackground(new Color(255,255,255,0));
        painter.setColor(colorOfLine);

        x1 = current.getStart().getX() + VertexConst.VERTEX_SIZE_X / 2;
        y1 = current.getStart().getY() + VertexConst.VERTEX_SIZE_Y / 2;
        x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2;
        y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2;
        painter.drawLine(x1,y1,x2,y2);
        drawTip(painter,x1,y1,x2,y2);
        repaint();
    }

    public void drawEdgeTemp(int x1, int y1, int x2, int y2){
        Graphics pain = temp.getGraphics();
        Graphics2D painter = (Graphics2D)pain;

        painter.setStroke(new BasicStroke(4));
        painter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        painter.setBackground(new Color(255, 255, 255, 0));
        painter.setColor(Color.black);
        painter.clearRect(0, 0, 1500, 1500);
        painter.drawLine(x1, y1, x2, y2);
        drawTip(painter, x1, y1, x2, y2);
        repaint();
    }

    public void drawVertexsEdges(VertexView vertex){
        Graphics pain = buffer.getGraphics();
        Graphics2D painter = (Graphics2D)pain;

        painter.setStroke(new BasicStroke(4));
        painter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        painter.setBackground(new Color(255,255,255,0));
        painter.setColor(Color.black);
        painter.clearRect(0, 0, 1500, 1500);

        int n = vertex.getNumberEdges();
        EdgeView current;
        int x1 = vertex.getX() + VertexConst.VERTEX_SIZE_X / 2;
        int y1 = vertex.getY() + VertexConst.VERTEX_SIZE_Y / 2;
        int x2,y2;

        for (int i = 0; i<n; i++){
            current = vertex.getEdgesAtIndex(i);
            if (current.getStart().equals(vertex)){
                x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2;
                y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2;
                if (x1 == x2 && y1 == y2){
                    x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                    y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 - 25;
                    int x3 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                    int y3 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 + 25;
                    painter.drawLine(x1,y1,x2,y2);
                    painter.drawLine(x2,y2,x3,y3);
                    painter.drawLine(x1,y1,x3,y3);
                    drawTip(painter,x2,y2,x1,y1);
                    current.getIdentifier().setBounds(x1 + 50, y1, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
                }else{
                    painter.drawLine(x1, y1, x2, y2);
                    drawTip(painter,x1,y1,x2,y2);
                    int x = Math.min(x1,x2) + Math.abs(x1-x2) / 2;
                    int y = Math.min(y1, y2) + Math.abs(y1 - y2) / 2;
                    current.getIdentifier().setBounds(x, y, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
                }
            } else {
                x2 = current.getStart().getX() + VertexConst.VERTEX_SIZE_X / 2;
                y2 = current.getStart().getY() + VertexConst.VERTEX_SIZE_Y / 2;
                if (x1 == x2 && y1 == y2){
                    x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                    y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 - 25;
                    int x3 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                    int y3 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 + 25;
                    painter.drawLine(x1,y1,x2,y2);
                    painter.drawLine(x2,y2,x3,y3);
                    painter.drawLine(x1,y1,x3,y3);
                    drawTip(painter,x2,y2,x1,y1);

                    current.getIdentifier().setBounds(x1 + 50, y1, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
                }else{
                    painter.drawLine(x2, y2, x1, y1);
                    drawTip(painter,x2,y2,x1,y1);
                    int x = Math.min(x1,x2) + Math.abs(x1-x2) / 2;
                    int y = Math.min(y1, y2) + Math.abs(y1 - y2) / 2;
                    current.getIdentifier().setBounds(x, y, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
                }
            }
        }

        n = allEdges.getNumberAllEdges();

        for (int i = 0; i<n; i++){
            current = allEdges.getEdgesAtIndex(i);
            if (!current.getStart().equals(vertex) && !current.getFinish().equals(vertex)){
                x1 = current.getStart().getX() + VertexConst.VERTEX_SIZE_X / 2;
                y1 = current.getStart().getY() + VertexConst.VERTEX_SIZE_Y / 2;
                x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2;
                y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2;

                if (x1 == x2 && y1 == y2){
                    x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                    y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 - 25;
                    int x3 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                    int y3 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 + 25;
                    painter.drawLine(x1,y1,x2,y2);
                    painter.drawLine(x2,y2,x3,y3);
                    painter.drawLine(x1,y1,x3,y3);
                    drawTip(painter,x2,y2,x1,y1);

                    current.getIdentifier().setBounds(x1 + 50, y1, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
                }else{
                    int x = Math.min(x1,x2) + Math.abs(x1-x2) / 2;
                    int y = Math.min(y1,y2) + Math.abs(y1-y2) / 2;

                    current.getIdentifier().setBounds(x, y, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
                    painter.drawLine(x1,y1,x2,y2);
                    drawTip(painter,x1,y1,x2,y2);
                }
            }
        }
        repaint();
    }

    public void drawAllEdge(){
        Graphics pain = buffer.getGraphics();
        Graphics2D painter = (Graphics2D)pain;

        painter.setStroke(new BasicStroke(4));
        painter.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        painter.setBackground(new Color(255,255,255,0));
        painter.setColor(Color.black);
        painter.clearRect(0, 0, 1500, 1500);

        EdgeView current;
        int x1,y1,x2,y2;
        int n = allEdges.getNumberAllEdges();

        for (int i = 0; i<n; i++){
            current = allEdges.getEdgesAtIndex(i);
            x1 = current.getStart().getX() + VertexConst.VERTEX_SIZE_X / 2;
            y1 = current.getStart().getY() + VertexConst.VERTEX_SIZE_Y / 2;
            x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2;
            y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2;

            if (x1 == x2 && y1 == y2){
                x2 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                y2 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 - 25;
                int x3 = current.getFinish().getX() + VertexConst.VERTEX_SIZE_X / 2 + 50;
                int y3 = current.getFinish().getY() + VertexConst.VERTEX_SIZE_Y / 2 + 25;
                painter.drawLine(x1,y1,x2,y2);
                painter.drawLine(x2,y2,x3,y3);
                painter.drawLine(x1,y1,x3,y3);
                drawTip(painter,x2,y2,x1,y1);

                current.getIdentifier().setBounds(x1 + 50, y1, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
            }else{
                int x = Math.min(x1,x2) + Math.abs(x1-x2) / 2;
                int y = Math.min(y1,y2) + Math.abs(y1-y2) / 2;

                current.getIdentifier().setBounds(x, y, VertexConst.FONT_SIZE * 4, VertexConst.FONT_SIZE);
                painter.drawLine(x1,y1,x2,y2);
                drawTip(painter,x1,y1,x2,y2);
            }
        }
        repaint();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(buffer, null,null);
        if (currentEdge != null) g2.drawImage(temp, null,null);
    }

    protected void drawTip(Graphics2D painter,int x1, int y1, int x2,int y2){
        if (x1!=x2 && y1!=y2) {
            double d, alpha, n, a, b, c, xLeft, yLeft, xRight, yRight;

            d = EdgeConst.EDGE_TIP;
            n = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
            alpha = (x1 * x1 - x2 * x2 + y1 * y1 - y2 * y2 - n * n + 2 * Math.sqrt(3) * d * n) / (2 * (x1 - x2));
            a = Math.pow((y2 - y1), 2) / Math.pow(x1 - x2, 2) + 1;
            b = (2 * (y2 - y1) * (alpha - x2)) / (x1 - x2) - 2 * y2;
            c = Math.pow(alpha - x2, 2) - 4 * d * d + y2 * y2;

            yLeft = (Math.sqrt(b * b - 4 * a * c) - b) / (2 * a);
            xLeft = yLeft * (y2 - y1) / (x1 - x2) + alpha;
            yRight = ((-1) * Math.sqrt(b * b - 4 * a * c) - b) / (2 * a);
            xRight = yRight * (y2 - y1) / (x1 - x2) + alpha;

            painter.drawLine(x2, y2, (int) xLeft, (int) yLeft);
            painter.drawLine(x2, y2, (int) xRight, (int) yRight);

        }
    }

    public EdgesList getAllEdges() {
        return allEdges;
    }

    public void setAllEdges(EdgesList allEdges) {
        this.allEdges = allEdges;
    }

    public EdgeView getGreenEdge() {
        return greenEdge;
    }

    public void setGreenEdge(EdgeView greenEdge) {
        this.greenEdge = greenEdge;
    }

    public GraphView getGraphView() {
        return graphView;
    }

    public void setGraphView(GraphView graphView) {
        this.graphView = graphView;
    }
}
