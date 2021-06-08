package window.editor.diagrammer;

import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.elements.MCDEntityShape;
import window.editor.diagrammer.handlers.DrawPanelHandler;
import window.editor.diagrammer.listeners.DrawPanelListener;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.utils.GridUtils;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Représente la zone de dessin du diagrammeur. C'est sur ce composant que la grille sera dessinée et que les éléments graphiques sont ajoutés (entités, relations, ...)
 */
public class DrawPanel extends JLayeredPane {

    private DrawPanelHandler handler;
    private int gridSize = DiagrammerConstants.DEFAULT_GRID_SIZE;
    private Point origin;
    private List<IShape> elements;

    public DrawPanel() {

        this.elements = new LinkedList<>();
        this.origin = new Point();
        this.handler = new DrawPanelHandler(this);

        this.initUI();
        this.addListeners();


        MCDEntityShape entityComponent = new MCDEntityShape(this);
        MCDEntityShape entityComponent2 = new MCDEntityShape(this);
        MCDEntityShape entityComponent3 = new MCDEntityShape(this);
        entityComponent2.setLocation(560,340);
        entityComponent3.setLocation(300,230);

        this.addElement(entityComponent);
        this.addElement(entityComponent2);
        this.addElement(entityComponent3);

        this.repaint();
    }

    /**
     * Initialise l'aspect graphique de la zone de dessin.
     */
    private void initUI(){
        this.setName(DiagrammerConstants.DRAW_PANEL_NAME);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setOpaque(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        this.handler.drawGrid(graphics2D);
    }

    public void setGridAndZoom(int zoomFactor){

        int oldGridSize = this.gridSize;

       if (zoomFactor >= DiagrammerConstants.MINIMUM_ALLOWED_ZOOM && zoomFactor <= DiagrammerConstants.MAXIMUM_ALLOWED_ZOOM){
            this.setGridSize(zoomFactor);
        }

       this.handler.zoomElements(oldGridSize, this.gridSize);

        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DRAW_PANEL_CONTAINER_NAME, this);

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Mouse " + mouseLocation);
        Point viewportLocation = parent.getViewport().getLocationOnScreen();

        double x = mouseLocation.x - viewportLocation.x;
        double y = mouseLocation.y - viewportLocation.y;

        // And add any space on the upper left corner which is not visible but reachable by scrollbar
        x += parent.getViewport().getViewPosition().getX();
        y += parent.getViewport().getViewPosition().getY();

        // The result is the point where we want to center the zoom of the diagram
        double differenceX, differenceY;
        differenceX = x - x * gridSize / oldGridSize;
        differenceY = y - y * gridSize / oldGridSize;

        // AB: Move origin in opposite direction
        this.getHandler().moveOrigin(GridUtils.alignToGrid(-differenceX, this.gridSize), GridUtils.alignToGrid(-differenceY, this.gridSize));


        for (IShape e : this.getElements()) {
            e.setLocationDifference(GridUtils.alignToGrid(differenceX, this.gridSize), GridUtils.alignToGrid(differenceX, this.gridSize));
        }


        this.handler.updatePanelAndScrollbars();

    }

    public int getGridSize() {
        return gridSize;
    }

    public void addElement(IShape element){
        this.add(element.getComponent());
        this.elements.add(element);
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    private void addListeners(){
        DrawPanelListener listener = new DrawPanelListener(this);

        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.addMouseWheelListener(listener);
        this.addKeyListener(listener);
    }

    public List<IShape> getElements() {
        return elements;
    }

    public DrawPanelHandler getHandler() {
        return handler;
    }

    public Point getOrigin() {
        return origin;
    }
}
