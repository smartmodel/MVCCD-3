package window.editor.diagrammer;

import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.elements.MCDEntityShape;
import window.editor.diagrammer.handlers.DrawPanelHandler;
import window.editor.diagrammer.listeners.DrawPanelListener;
import window.editor.diagrammer.utils.DiagrammerConstants;

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

        this.handler = new DrawPanelHandler(this);
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
        //this.setPreferredSize(new Dimension(3000, 2000));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        this.handler.drawGrid(graphics2D);
        graphics2D.setColor(Color.orange);
        graphics2D.fill(this.handler.getContentBounds(this.elements, 0));

    }

    public void setGridAndZoom(int zoomFactor){
       if (zoomFactor >= DiagrammerConstants.MINIMUM_ALLOWED_ZOOM && zoomFactor <= DiagrammerConstants.MAXIMUM_ALLOWED_ZOOM){
            this.setGridSize(zoomFactor);
        }
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
