package mode;

import graphview.VertexView;
import graphview.WorkingArea;
import constants.VertexConst;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MoveVertexListener implements MouseMotionListener {
    private VertexView vertex;
    private WorkingArea boxDrawing;
    private Point mouseOnBoxDrawing;

    public MoveVertexListener(VertexView vertexCopy,WorkingArea boxDrawingCopy){
        vertex = vertexCopy;
        boxDrawing = boxDrawingCopy;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        vertex.setIcon(VertexConst.GREEN_VERTEX_IMAGE);
        mouseOnBoxDrawing = boxDrawing.getMousePosition();
        if (null != mouseOnBoxDrawing) {
            mouseOnBoxDrawing.setLocation(mouseOnBoxDrawing.getX() - 9, mouseOnBoxDrawing.getY() - 9);
            vertex.setLocation(mouseOnBoxDrawing);
            mouseOnBoxDrawing.setLocation(mouseOnBoxDrawing.getX() + 18, mouseOnBoxDrawing.getY() + 12);
            vertex.getIdentifier().setLocation(mouseOnBoxDrawing);
            boxDrawing.drawVertexsEdges(vertex);
        }
        boxDrawing.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
