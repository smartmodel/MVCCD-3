package window.editor.diagrammer.drawpanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import main.MVCCDManager;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.DialogMessage;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.DrawPanelListener;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;

/**
 * Représente la zone de dessin du diagrammeur. C'est sur ce composant que la grille sera dessinée et que les éléments graphiques sont ajoutés (entités, relations, ...)
 */
public class DrawPanel extends JLayeredPane {

  private int gridSize = Preferences.DIAGRAMMER_DEFAULT_GRID_SIZE;
  private Point origin;
  private List<IShape> elements;
  private boolean showRelationProjectionLine = false;

  public DrawPanel() {
    this.elements = new LinkedList<>();
    this.origin = new Point();
    this.initUI();
    this.addListeners();
    this.repaint();
  }

  public int getGridSize() {
    return this.gridSize;
  }

  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
  }

  private void initUI() {
    this.setName(Preferences.DIAGRAMMER_DRAW_PANEL_NAME);
    this.setLayout(null);
    this.setBackground(Color.WHITE);
    this.setOpaque(true);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    System.out.println("On redessine le panel");
    if (PreferencesManager.instance().getApplicationPref().isDIAGRAMMER_SHOW_GRID()) {
      System.out.println("Dessine la grid");
      this.drawGrid(graphics2D);
    }
    this.drawRelations(graphics2D);
    if (this.isShowRelationProjectionLine()) {
      this.showRelationProjectionLine(graphics2D);
    }
  }

  public void zoom(int zoomFactor) {
    int oldGridSize = this.gridSize;
    if (zoomFactor >= Preferences.DIAGRAMMER_MINIMUM_ALLOWED_ZOOM && zoomFactor <= Preferences.DIAGRAMMER_MAXIMUM_ALLOWED_ZOOM) {
      this.setGridSize(zoomFactor);
    }
    this.zoomElements(oldGridSize, this.gridSize);
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
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
    this.moveOrigin(GridUtils.alignToGrid(-differenceX, this.gridSize), GridUtils.alignToGrid(-differenceY, this.gridSize));
    for (IShape e : this.getElements()) {
      e.setLocationDifference(GridUtils.alignToGrid(differenceX, this.gridSize), GridUtils.alignToGrid(differenceX, this.gridSize));
    }
    this.updatePanelAndScrollbars();

  }

  public void addElement(IShape element) {
    if (element != null) {
      this.add((JComponent) element);
      this.elements.add(element);
      this.repaint();
    } else {
      DialogMessage.showError(MVCCDManager.instance().getMvccdWindow(), "Impossible d'ajouter un élément null à la zone de dessin.");
    }
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

  public Point getOrigin() {
    return origin;
  }

  public void deleteElement(IShape shape) {
    this.remove((JComponent) shape);
    this.elements.remove(shape);
    this.repaint();
  }

  public void drawRelations(Graphics2D graphics2D) {
    graphics2D.setColor(Color.BLACK);
    for (RelationShape relationShape : this.getRelationShapes()) {
      relationShape.draw(graphics2D);
      if (relationShape.isSelected()) {
        relationShape.drawPointsAncrage(graphics2D);
      }
    }
  }

  public void drawGrid(Graphics2D graphics2D) {
    graphics2D.setColor(new Color(243, 243, 243));
    int width = this.getWidth();
    int height = this.getHeight();
    for (int i = 0; i < width; i += DiagrammerService.getDrawPanel().getGridSize()) {
      graphics2D.drawLine(i, 0, i, height);
    }
    for (int i = 0; i < height; i += DiagrammerService.getDrawPanel().getGridSize()) {
      graphics2D.drawLine(0, i, width, i);
    }
  }

  /**
   * Zoom les éléments du modèle.
   *
   * @param fromFactor facteur de zoom initial
   * @param toFactor   facteur de zoom final
   */
  public void zoomElements(int fromFactor, int toFactor) {
    if (this.zoomAllowed(toFactor)) {
      for (IShape element : this.getElements()) {
        element.zoom(fromFactor, toFactor);
      }
    }
    this.repaint();
  }

  public void moveOrigin(int differenceX, int differenceY) {
    this.getOrigin().translate(GridUtils.alignToGrid(differenceX, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(differenceY, DiagrammerService.getDrawPanel().getGridSize()));
  }

  /**
   * Vérifie que le zoom est autorisé. Exemple : Si le zoom maximal autorisé est de 25 et qu'un facteur de 26 est passé en paramètre, le zoom n'est pas autorisé.
   *
   * @param factor
   *
   * @return Si le zoom est autorisé
   */
  private boolean zoomAllowed(int factor) {
    return factor >= Preferences.DIAGRAMMER_MINIMUM_ALLOWED_ZOOM && factor <= Preferences.DIAGRAMMER_MAXIMUM_ALLOWED_ZOOM;
  }

  private void removeUnnecessaryWhitespaceAroundDiagram() {
    Rectangle contentBounds = getContentBounds(this.getElements(), 0);
    int newX = 0;
    int newY = 0;
    int newWidth = (int) this.getVisibleRect().getMaxX();
    // Si le max X de contentBounds est plus grand que le maxX de la view, on adapte la largeur
    if (contentBounds.getMaxX() > this.getVisibleRect().getMaxX()) {
      newWidth = (int) (contentBounds.getX() + contentBounds.getWidth());
    }
    int newHeight = (int) this.getVisibleRect().getHeight();
    // Si le maxY de contentBounds dépasse le maxY de la view, on adapte la hauteur
    if (contentBounds.getMaxY() > this.getVisibleRect().getMaxY()) {
      newHeight = (int) (contentBounds.getY() + contentBounds.getHeight());
    }
    this.moveOrigin(newX, newY);
    for (IShape element : this.getElements()) {
      element.setLocation(GridUtils.alignToGrid(element.getBounds().x - newX, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(element.getBounds().y - newY, DiagrammerService.getDrawPanel().getGridSize()));
    }
    // On change la position de la vue
    this.changeViewPosition(-newX, -newY);
    this.setPreferredSize(new Dimension(newWidth - newX, newHeight - newY));
    this.checkIfScrollbarsAreNecessary();
  }

  private boolean isHorizontalScrollbarVisible() {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    return parent.getHorizontalScrollBarPolicy() == ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
  }

  private boolean isVerticalScrollbarVisible() {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    return parent.getVerticalScrollBarPolicy() == ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
  }

  private void checkIfScrollbarsAreNecessary() {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    Rectangle diaWithoutWhite = getContentBounds(this.getElements(), 0);
    Dimension viewSize = getViewableDiagrampanelSize();
    boolean vertWasVisible = this.isVerticalScrollbarVisible();
    boolean horWasVisible = this.isHorizontalScrollbarVisible();
    int verSbWidth = 0;
    int horSbHeight = 0;
    if (vertWasVisible) {
      verSbWidth = parent.getVerticalScrollBar().getWidth();
    }
    if (horWasVisible) {
      horSbHeight = parent.getHorizontalScrollBar().getHeight();
    }
    // La scrollbar est inclut dans la zone visible -> on la masque
    if (parent.getHorizontalScrollBar().getValue() < DiagrammerService.getDrawPanel().getGridSize() && diaWithoutWhite.getX() + diaWithoutWhite.getWidth() <= viewSize.getWidth() + verSbWidth) {
      this.setHorizontalScrollbarVisibility(false);
    } else if (parent.getHorizontalScrollBar().getValue() < DiagrammerService.getDrawPanel().getGridSize() && getViewableDiagrampanelSize().width + parent.getHorizontalScrollBar().getValue() == diaWithoutWhite.getX() + diaWithoutWhite.getWidth()) {
      setHorizontalScrollbarVisibility(false);
    } else {
      setHorizontalScrollbarVisibility(true);
    }
    if (parent.getVerticalScrollBar().getValue() < DiagrammerService.getDrawPanel().getGridSize() && diaWithoutWhite.getY() + diaWithoutWhite.getHeight() <= viewSize.getHeight() + horSbHeight) {
      setVerticalScrollbarVisibility(false);
    } else if (parent.getVerticalScrollBar().getValue() < DiagrammerService.getDrawPanel().getGridSize() && getViewableDiagrampanelSize().height + parent.getVerticalScrollBar().getValue() == diaWithoutWhite.getY() + diaWithoutWhite.getHeight()) {
      setVerticalScrollbarVisibility(false);
    } else {
      setVerticalScrollbarVisibility(true);
    }
    int adjustedX = 0;
    int adjustedY = 0;
    if (parent.getHorizontalScrollBar().getValue() != 0 && vertWasVisible && !isVerticalScrollbarVisible()) {
      adjustedX = GridUtils.alignToGrid(horSbHeight, DiagrammerService.getDrawPanel().getGridSize());
    }
    if (parent.getVerticalScrollBar().getValue() != 0 && horWasVisible && !isHorizontalScrollbarVisible()) {
      adjustedY = GridUtils.alignToGrid(verSbWidth, DiagrammerService.getDrawPanel().getGridSize());
    }
    if (adjustedX != 0 || adjustedY != 0) {
      this.setPreferredSize(new Dimension((int) (this.getPreferredSize().getWidth() + adjustedX), (int) this.getPreferredSize().getHeight() + adjustedY));
      changeViewPosition(adjustedX, adjustedY);
    }
  }

  public Rectangle getContentBounds(Collection<IShape> elements, int border) {
    if (elements.size() == 0) {
      return new Rectangle(0, 0, 0, 0);
    }
    int minx = Integer.MAX_VALUE;
    int miny = Integer.MAX_VALUE;
    int maxx = 0;
    int maxy = 0;
    for (IShape element : elements) {
      if (element instanceof ClassShape) {
        minx = Math.min(minx, element.getBounds().x - border);
        miny = Math.min(miny, element.getBounds().y - border);
        maxx = Math.max(maxx, element.getBounds().x + element.getBounds().width + border);
        maxy = Math.max(maxy, element.getBounds().y + element.getBounds().height + border);
      }
    }
    return new Rectangle(minx, miny, maxx - minx, maxy - miny);
  }

  public Dimension getViewableDiagrampanelSize() {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    return parent.getVisibleRect().getSize();
  }

  public void changeViewPosition(int deltaX, int deltaY) {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    Point viewportPosition = parent.getViewport().getViewPosition();
    parent.getViewport().setViewSize(this.getPreferredSize());
    parent.getViewport().setViewPosition(new Point(viewportPosition.x + deltaX, viewportPosition.y + deltaY));
  }

  public void updatePanelAndScrollbars() {
    this.insertWhiteSpaceInUpperLeftCorner();
    //this.removeUnnecessaryWhitespaceAroundDiagram();
    this.repaint();
  }

  private void insertWhiteSpaceInUpperLeftCorner() {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    Rectangle diaWithoutWhite = getContentBounds(this.getElements(), 0);
    int adjustWidth = 0;
    if (diaWithoutWhite.getX() < 0) {
      adjustWidth = (int) diaWithoutWhite.getX();
    }
    int adjustHeight = 0;
    if (diaWithoutWhite.getY() < 0) {
      adjustHeight = (int) diaWithoutWhite.getY();
    }
    moveOrigin(adjustWidth, adjustHeight);
    for (IShape element : this.getElements()) {
      JComponent component = (JComponent) element;
      component.setLocation(GridUtils.alignToGrid(component.getX() - adjustWidth, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(component.getY() - adjustHeight, DiagrammerService.getDrawPanel().getGridSize()));
    }
    if (adjustWidth < 0) {
      this.setHorizontalScrollbarVisibility(true);
    }
    if (adjustHeight < 0) {
      this.setVerticalScrollbarVisibility(true);
    }
    int width = (int) (parent.getHorizontalScrollBar().getValue() + getViewableDiagrampanelSize().getWidth() - adjustWidth);
    int height = (int) (parent.getVerticalScrollBar().getValue() + getViewableDiagrampanelSize().getHeight() - adjustHeight);
    this.setPreferredSize(new Dimension(width, height));
    changeViewPosition(-adjustWidth, -adjustHeight);
  }

  private void setHorizontalScrollbarVisibility(boolean visible) {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    if (visible) {
      parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    } else {
      parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
  }

  private void setVerticalScrollbarVisibility(boolean visible) {
    DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    if (visible) {
      parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    } else {
      parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    }
  }

  public void scroll(int differenceX, int differenceY) {
    for (IShape element : this.getElements()) {
      element.setLocationDifference(differenceX, differenceY);
    }
  }

  public void endScroll() {
    // Mise à jour du panel et des scrollbars
    this.updatePanelAndScrollbars();
  }

  public ArrayList<RelationShape> getRelationShapes() {
    ArrayList relations = new ArrayList();
    for (IShape shape : this.elements) {
      if (shape instanceof RelationShape) {
        relations.add(shape);
      }
    }
    return relations;
  }

  public ArrayList<RelationShape> getRelationShapesByClassShape(ClassShape shape) {
    ArrayList relations = new ArrayList();
    for (RelationShape relation : this.getRelationShapes()) {
      if (relation.getSource() == shape || relation.getDestination() == shape) {
        relations.add(relation);
      }
    }
    return relations;
  }

  public ArrayList<ClassShape> getClassShapes() {
    ArrayList classes = new ArrayList();
    for (IShape shape : this.elements) {
      if (shape instanceof ClassShape) {
        classes.add(shape);
      }
    }
    return classes;
  }

  public void deselectAllShapes() {
    for (IShape shape : this.getElements()) {
      shape.setSelected(false);
    }
    DiagrammerService.drawPanel.repaint();
  }

  public void deselectAllOtherShape(IShape shapeToKeepSelected) {
    for (IShape shape : this.getElements()) {
      if (shape != shapeToKeepSelected) {
        shape.setSelected(false);
      }
    }
    DiagrammerService.drawPanel.repaint();
  }

  private void showRelationProjectionLine(Graphics2D graphics2D) {
    float[] dash = {2f, 0f, 2f};

    Graphics2D newGraphics2D = (Graphics2D) graphics2D.create();
    BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
    newGraphics2D.setStroke(stroke);
    newGraphics2D.setColor(Color.GRAY);

    Point mouse = MouseInfo.getPointerInfo().getLocation();
    Point converted = SwingUtilities.convertPoint(MVCCDManager.instance().getMvccdWindow(), mouse, DiagrammerService.drawPanel);

    newGraphics2D.drawLine(RelationCreator.source.getCenter().x, RelationCreator.source.getCenter().y, converted.x, converted.y);
  }

  public boolean isShowRelationProjectionLine() {
    return showRelationProjectionLine;
  }

  public void setShowRelationProjectionLine(boolean showRelationProjectionLine) {
    this.showRelationProjectionLine = showRelationProjectionLine;
    this.repaint();
  }
}
