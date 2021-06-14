package window.editor.diagrammer.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.elements.MCDEntityShape;
import window.editor.diagrammer.handlers.DrawPanelHandler;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.listeners.DrawPanelListener;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.utils.GridUtils;

/**
 * Représente la zone de dessin du diagrammeur. C'est sur ce composant que la grille sera dessinée
 * et que les éléments graphiques sont ajoutés (entités, relations, ...)
 */
public class DrawPanel extends JLayeredPane {

  private static int gridSize = DiagrammerConstants.DIAGRAMMER_DEFAULT_GRID_SIZE;
  private DrawPanelHandler handler;
  private Point origin;
  private List<IShape> elements;

  public DrawPanel() {
    
    this.elements = new LinkedList<>();
    this.origin = new Point();
    this.handler = new DrawPanelHandler(this);

    this.initUI();
    this.addListeners();

    MCDEntityShape entityComponent = new MCDEntityShape();
    MCDEntityShape entityComponent2 = new MCDEntityShape();
    MCDEntityShape entityComponent3 = new MCDEntityShape();
    entityComponent2.setLocation(560, 340);
    entityComponent3.setLocation(300, 230);

    this.addElement(entityComponent);
    this.addElement(entityComponent2);
    this.addElement(entityComponent3);

    this.repaint();
  }

  public static int getGridSize() {
    return gridSize;
  }

  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
  }

  /**
   * Initialise l'aspect graphique de la zone de dessin.
   */
  private void initUI() {
    this.setName(DiagrammerConstants.DIAGRAMMER_DRAW_PANEL_NAME);
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

  public void zoom(int zoomFactor) {

    int oldGridSize = this.gridSize;

    if (zoomFactor >= DiagrammerConstants.DIAGRAMMER_MINIMUM_ALLOWED_ZOOM
        && zoomFactor <= DiagrammerConstants.DIAGRAMMER_MAXIMUM_ALLOWED_ZOOM) {
      this.setGridSize(zoomFactor);
    }

    this.handler.zoomElements(oldGridSize, this.gridSize);

    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(DiagrammerConstants.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);

    Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    Point viewportLocation = parent.getViewport().getLocationOnScreen();

    double x = mouseLocation.x - viewportLocation.x;
    double y = mouseLocation.y - viewportLocation.y;

    // Ajoute l'espace accessible par la scrollbar
    x += parent.getViewport().getViewPosition().getX();
    y += parent.getViewport().getViewPosition().getY();

    // Le résultat est là où le zoom s'effectuera
    double differenceX, differenceY;
    differenceX = x - x * gridSize / oldGridSize;
    differenceY = y - y * gridSize / oldGridSize;

    // On déplace l'origine
    this.getHandler().moveOrigin(GridUtils.alignToGrid(-differenceX, this.gridSize),
        GridUtils.alignToGrid(-differenceY, this.gridSize));

    for (IShape e : this.getElements()) {
      e.setLocationDifference(GridUtils.alignToGrid(differenceX, this.gridSize),
          GridUtils.alignToGrid(differenceX, this.gridSize));
    }

    this.handler.updatePanelAndScrollbars();

  }

  public void addElement(IShape element) {
    this.add((JComponent) element);
    this.elements.add(element);
  }

  private void addListeners() {
    DrawPanelListener listener = new DrawPanelListener();

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

  public void deleteElement(IShape shape) {
    this.remove((JComponent) shape);
    this.elements.remove(shape);
  }
}
