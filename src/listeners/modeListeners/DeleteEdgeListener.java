package listeners.modeListeners;

import model.Edge;
import model.WorkingArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.toDegrees;

/**
 * Created by Михаил on 06.04.2015.
 */
public class DeleteEdgeListener implements MouseListener,MouseMotionListener {
    private WorkingArea boxDrawing;
    private Edge farEdge;

    public DeleteEdgeListener(WorkingArea boxDrawing){
        this.boxDrawing = boxDrawing;
        farEdge = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (boxDrawing.getGreenEdge() != null){
            boxDrawing.getGreenEdge().getIdentifier().setVisible(false);
            boxDrawing.getGreenEdge().getStart().removeEdge(boxDrawing.getGreenEdge());
            boxDrawing.getGreenEdge().getFinish().removeEdge(boxDrawing.getGreenEdge());
            boxDrawing.getAllEdges().removeEdge(boxDrawing.getGreenEdge());
            boxDrawing.drawAllEdge();
            boxDrawing.setGreenEdge(null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double xMouse,yMouse,rast,x1,x2,y1,y2;
        double min = 9999999999.0;
        Edge  currentEdge;

        xMouse = boxDrawing.getMousePosition().getX();
        yMouse = boxDrawing.getMousePosition().getY();

        int n = boxDrawing.getAllEdges().getNumberAllEdges();

        for (int i=0; i<n; i++){
            currentEdge = boxDrawing.getAllEdges().getEdgesOfNumber(i);
            x1 = currentEdge.getStart().getX() + 9;
            y1 = currentEdge.getStart().getY() + 9;
            x2 = currentEdge.getFinish().getX() + 9;
            y2 = currentEdge.getFinish().getY() + 9;

            rast = Math.pow((x1-xMouse)*(x1-xMouse) + (y1-yMouse)*(y1-yMouse),0.5) +
                    Math.pow((xMouse-x2)*(xMouse-x2) + (yMouse-y2)*(yMouse-y2),0.5) -
                    Math.pow((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2),0.5);
            if (rast < min) {
                min = rast;
                farEdge = currentEdge;
            }
        }

        if (min < 0.4) {
            boxDrawing.setColorBuffer(Color.green);
            boxDrawing.drawEdge(farEdge);
            boxDrawing.setGreenEdge(farEdge);
            boxDrawing.setColorBuffer(Color.black);
        } else {
            if (boxDrawing.getGreenEdge() != null){
                boxDrawing.setColorBuffer(Color.black);
                boxDrawing.drawEdge(boxDrawing.getGreenEdge());
            }
            boxDrawing.setGreenEdge(null);
        }
    }
}