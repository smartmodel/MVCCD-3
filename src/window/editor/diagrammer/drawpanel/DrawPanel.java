package window.editor.diagrammer.drawpanel;

import console.ViewLogsManager;
import console.WarningLevel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.DialogMessage;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.DrawPanelListener;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;

/**
 * Représente la zone de dessin du diagrammeur. C'est sur ce composant que la grille sera dessinée et que les éléments graphiques sont ajoutés (entités, relations, ...)
 */
public class DrawPanel extends JLayeredPane implements Serializable {

  private static final long serialVersionUID = 1000;
  private final Point origin;
  private List<IShape> shapes;
  private int gridSize = Preferences.DIAGRAMMER_DEFAULT_GRID_SIZE;
  private double zoomFactor = 1;
  private JScrollPane scrollPane;

  public DrawPanel() {
    this.shapes = new LinkedList<>();
    this.origin = new Point();
    this.initUI();
    this.addListeners();
    this.repaint();
    this.scrollPane = new JScrollPane();
    this.scrollPane.setViewportView(this);
    this.scrollPane.getHorizontalScrollBar().setUnitIncrement(50); // Using mousewheel on bar or click on arrow
    this.scrollPane.getHorizontalScrollBar().setSize(0, 15);
    this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.scrollPane.getVerticalScrollBar().setUnitIncrement(50);
    this.scrollPane.getVerticalScrollBar().setSize(15, 0);
    this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

  }

  public int getGridSize() {
    return this.gridSize;
  }

  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
  }

  private void initUI() {
    this.setName(Preferences.DIAGRAMMER_DRAW_PANEL_NAME);
    this.setBackground(Color.WHITE);
    this.setOpaque(true);
    this.setLayout(null);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    // Si l'option "Afficher la grille" est cochée dans les préférences, on affiche la grille dans le diagrammeur
    if (PreferencesManager.instance().getApplicationPref().isDIAGRAMMER_SHOW_GRID()) {
      this.drawGrid(graphics2D);
    }
    this.drawRelations(graphics2D);

    // Si on crée une association, une ligne de projection est reliée de l'entité source au curseur de la souris
    if (RelationCreator.isCreating) {
      this.showRelationProjectionLine(graphics2D);
    }
    // this.updatePanelAndScrollbars();
  }

  public MCDEntityShape getMcdEntityShapeById(int id) {
    for (ClassShape shape : this.getClassShapes()) {
      if (shape instanceof MCDEntityShape) {
        if (shape.getId() == id) {
          return (MCDEntityShape) shape;
        }
      }
    }
    return null;
  }

  public void addShape(IShape element) {
    if (element != null) {
      this.add((JComponent) element);
      this.shapes.add(element);
      this.repaint();
    } else {
      DialogMessage.showError(MVCCDManager.instance().getMvccdWindow(), MessagesBuilder.getMessagesProperty("diagrammer.error.add.null.element"));
    }
  }

  public void loadShapes(List<IShape> shapes) {
    this.shapes.addAll(shapes);
    for (IShape shape : shapes) {
      this.add((JComponent) shape);
    }
    this.revalidate();
    this.repaint();
    ViewLogsManager.printMessage(shapes.size() + " formes ont été ajoutées à la zone de dessin.", WarningLevel.INFO);
  }

  private void addListeners() {
    DrawPanelListener listener = new DrawPanelListener();

    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.addMouseWheelListener(listener);
    this.addKeyListener(listener);
  }

  public List<IShape> getShapes() {
    return this.shapes;
  }

  public Point getOrigin() {
    return this.origin;
  }

  public void deleteShape(IShape shape) {
    this.remove((JComponent) shape);
    this.shapes.remove(shape);
    this.revalidate();
    this.repaint();
  }

  public void unloadAllShapes() {
    this.removeAll();
    this.shapes.clear();
    this.revalidate();
    this.repaint();
  }

  public void drawRelations(Graphics2D graphics2D) {
    graphics2D.setColor(Color.BLACK);
    for (RelationShape relationShape : this.getRelationShapes()) {
      relationShape.draw(graphics2D);

    }
  }

  public void drawGrid(Graphics2D graphics2D) {
    graphics2D.setColor(new Color(243, 243, 243));
    int width = this.getWidth();
    int height = this.getHeight();
    for (int i = 0; i < width; i += this.gridSize) {
      graphics2D.drawLine(i, 0, i, height);
    }
    for (int i = 0; i < height; i += this.gridSize) {
      graphics2D.drawLine(0, i, width, i);
    }
  }

  /**
   * Zoom les éléments du modèle.
   * @param fromFactor facteur de zoom initial
   * @param toFactor facteur de zoom final
   */
  public void zoomElements(int fromFactor, int toFactor) {
    if (this.zoomAllowed(toFactor)) {
      for (IShape element : this.getShapes()) {
        element.zoom(fromFactor, toFactor);
      }
    }
    this.repaint();
  }

  public void moveOrigin(int differenceX, int differenceY) {
    this.origin.translate(GridUtils.alignToGrid(differenceX, this.gridSize), GridUtils.alignToGrid(differenceY, this.gridSize));
  }

  /**
   * Vérifie que le zoom est autorisé. Exemple : Si le zoom maximal autorisé est de 25 et qu'un facteur de 26 est passé en paramètre, le zoom n'est pas autorisé.
   * @param factor facteur de zoon à vérifier
   * @return Si le zoom est autorisé
   */
  private boolean zoomAllowed(int factor) {
    return factor >= Preferences.DIAGRAMMER_MINIMUM_ALLOWED_ZOOM && factor <= Preferences.DIAGRAMMER_MAXIMUM_ALLOWED_ZOOM;
  }

  private void removeUnnecessaryWhitespaceAroundDiagram() {
    Rectangle contentBounds = this.getContentBounds(this.getShapes(), 0);
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
    for (IShape element : this.getShapes()) {
      element.setLocation(GridUtils.alignToGrid(element.getBounds().x - newX, this.gridSize), GridUtils.alignToGrid(element.getBounds().y - newY, this.gridSize));
    }
    // On change la position de la vue
    this.changeViewPosition(-newX, -newY);
    this.setPreferredSize(new Dimension(newWidth - newX, newHeight - newY));
    this.checkIfScrollbarsAreNecessary();
  }

  private void insertUpperLeftWhitespaceIfNeeded() {

    Rectangle diaWithoutWhite = this.getContentBounds(this.getShapes(), 0);
    // We must adjust the components and the view by a certain factor
    int adjustWidth = 0;
    if (diaWithoutWhite.getX() < 0) {
      adjustWidth = (int) diaWithoutWhite.getX();
    }

    int adjustHeight = 0;
    if (diaWithoutWhite.getY() < 0) {
      adjustHeight = (int) diaWithoutWhite.getY();
    }

    this.moveOrigin(adjustWidth, adjustHeight);

    // If any adjustment is needed we move the components and increase the view position
    if (adjustWidth != 0 || adjustHeight != 0) {
      for (int i = 0; i < this.getComponents().length; i++) {
        Component c = this.getComponent(i);
        c.setLocation(GridUtils.alignToGrid(c.getX() - adjustWidth, this.gridSize), GridUtils.alignToGrid(c.getY() - adjustHeight, this.gridSize));
      }
    }

    if (adjustWidth < 0) {
      this.setHorizontalScrollbarVisibility(true);
    }
    if (adjustHeight < 0) {
      this.setVerticalScrollbarVisibility(true);
    }

    int width = (int) (this.scrollPane.getHorizontalScrollBar().getValue() + this.getViewableDiagrampanelSize().getWidth() - adjustWidth);
    int height = (int) (this.scrollPane.getVerticalScrollBar().getValue() + this.getViewableDiagrampanelSize().getHeight() - adjustHeight);
    this.setPreferredSize(new Dimension(width, height));

    this.changeViewPosition(-adjustWidth, -adjustHeight);
  }

  public void updatePanelAndScrollbars() {
    this.insertUpperLeftWhitespaceIfNeeded();
    this.removeUnnecessaryWhitespaceAroundDiagram();
  }

  private boolean isHorizontalScrollbarVisible() {
    return this.scrollPane.getHorizontalScrollBarPolicy() == ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
  }

  private boolean isVerticalScrollbarVisible() {
    return this.scrollPane.getVerticalScrollBarPolicy() == ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
  }

  private void checkIfScrollbarsAreNecessary() {
    Rectangle diagramWithoutWhiteSpaces = this.getContentBounds(this.getShapes(), 0);
    Dimension viewSize = this.getViewableDiagrampanelSize();
    boolean verticalScrollbarWasVisible = this.isVerticalScrollbarVisible();
    boolean horizontalScrollbarWasVisible = this.isHorizontalScrollbarVisible();
    int verticalScrollbarWidth = 0;
    int horizontalScrollBarHeight = 0;

    if (verticalScrollbarWasVisible) {
      verticalScrollbarWidth = this.scrollPane.getVerticalScrollBar().getWidth();
    }

    if (horizontalScrollbarWasVisible) {
      horizontalScrollBarHeight = this.scrollPane.getHorizontalScrollBar().getHeight();
    }

    // La scrollbar est inclut dans la zone visible -> on la masque
    if (this.scrollPane.getHorizontalScrollBar().getValue() < this.gridSize && diagramWithoutWhiteSpaces.getX() + diagramWithoutWhiteSpaces.getWidth() <= viewSize.getWidth() + verticalScrollbarWidth) {
      this.setHorizontalScrollbarVisibility(false);
    } else if (this.scrollPane.getHorizontalScrollBar().getValue() < this.gridSize && this.getViewableDiagrampanelSize().width + this.scrollPane.getHorizontalScrollBar().getValue() == diagramWithoutWhiteSpaces.getX() + diagramWithoutWhiteSpaces.getWidth()) {
      this.setHorizontalScrollbarVisibility(false);
    } else {
      this.setHorizontalScrollbarVisibility(true);
    }

    if (this.scrollPane.getVerticalScrollBar().getValue() < this.gridSize && diagramWithoutWhiteSpaces.getY() + diagramWithoutWhiteSpaces.getHeight() <= viewSize.getHeight() + horizontalScrollBarHeight) {
      this.setVerticalScrollbarVisibility(false);
    } else if (this.scrollPane.getVerticalScrollBar().getValue() < this.gridSize && this.getViewableDiagrampanelSize().height + this.scrollPane.getVerticalScrollBar().getValue() == diagramWithoutWhiteSpaces.getY() + diagramWithoutWhiteSpaces.getHeight()) {
      this.setVerticalScrollbarVisibility(false);
    } else {

      this.setVerticalScrollbarVisibility(true);
    }
    int adjustedX = 0;
    int adjustedY = 0;
    if (this.scrollPane.getHorizontalScrollBar().getValue() != 0 && verticalScrollbarWasVisible && !this.isVerticalScrollbarVisible()) {
      adjustedX = GridUtils.alignToGrid(horizontalScrollBarHeight, this.gridSize);
    }
    if (this.scrollPane.getVerticalScrollBar().getValue() != 0 && horizontalScrollbarWasVisible && !this.isHorizontalScrollbarVisible()) {
      adjustedY = GridUtils.alignToGrid(verticalScrollbarWidth, this.gridSize);
    }
    if (adjustedX != 0 || adjustedY != 0) {
      this.setPreferredSize(new Dimension((int) (this.getPreferredSize().getWidth() + adjustedX), (int) this.getPreferredSize().getHeight() + adjustedY));
      this.changeViewPosition(adjustedX, adjustedY);
    }
  }

  public Rectangle getContentBounds(Collection<IShape> elements, int borderWidth) {
    if (elements.size() == 0) {
      return new Rectangle(0, 0, 0, 0);
    }
    int minx = Integer.MAX_VALUE;
    int miny = Integer.MAX_VALUE;
    int maxx = 0;
    int maxy = 0;
    for (IShape element : elements) {
      if (element instanceof ClassShape) {
        minx = Math.min(minx, element.getBounds().x - borderWidth);
        miny = Math.min(miny, element.getBounds().y - borderWidth);
        maxx = Math.max(maxx, element.getBounds().x + element.getBounds().width + borderWidth);
        maxy = Math.max(maxy, element.getBounds().y + element.getBounds().height + borderWidth);
      }
    }
    return new Rectangle(minx, miny, maxx - minx, maxy - miny);
  }

  public Dimension getViewableDiagrampanelSize() {
    return this.scrollPane.getVisibleRect().getSize();
  }

  public void changeViewPosition(int deltaX, int deltaY) {
    Point viewportPosition = this.scrollPane.getViewport().getViewPosition();
    this.scrollPane.getViewport().setViewSize(this.getPreferredSize());
    this.scrollPane.getViewport().setViewPosition(new Point(viewportPosition.x + deltaX, viewportPosition.y + deltaY));
  }

  private void insertWhiteSpaceInUpperLeftCorner() {
    final DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    final Rectangle diaWithoutWhite = this.getContentBounds(this.getShapes(), 0);
    int adjustWidth = 0;
    if (diaWithoutWhite.getX() < 0) {
      adjustWidth = (int) diaWithoutWhite.getX();
    }
    int adjustHeight = 0;
    if (diaWithoutWhite.getY() < 0) {
      adjustHeight = (int) diaWithoutWhite.getY();
    }
    this.moveOrigin(adjustWidth, adjustHeight);
    for (IShape element : this.getShapes()) {
      JComponent component = (JComponent) element;
      component.setLocation(GridUtils.alignToGrid(component.getX() - adjustWidth, this.gridSize), GridUtils.alignToGrid(component.getY() - adjustHeight, this.gridSize));
    }
    if (adjustWidth < 0) {
      this.setHorizontalScrollbarVisibility(true);
    }
    if (adjustHeight < 0) {
      this.setVerticalScrollbarVisibility(true);
    }
    int width = (int) (parent.getHorizontalScrollBar().getValue() + this.getViewableDiagrampanelSize().getWidth() - adjustWidth);
    int height = (int) (parent.getVerticalScrollBar().getValue() + this.getViewableDiagrampanelSize().getHeight() - adjustHeight);
    this.setPreferredSize(new Dimension(width, height));
    this.changeViewPosition(-adjustWidth, -adjustHeight);
  }

  private void setHorizontalScrollbarVisibility(boolean visible) {
    if (visible) {
      this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    } else {
      this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
  }

  private void setVerticalScrollbarVisibility(boolean visible) {
    if (visible) {
      this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    } else {
      this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    }
  }

  public void scroll(int amount, boolean isHorizontal) {
    JScrollBar scrollBar = isHorizontal ? this.scrollPane.getHorizontalScrollBar() : this.scrollPane.getVerticalScrollBar();
    int increment = scrollBar.getUnitIncrement();
    scrollBar.setValue(scrollBar.getValue() + amount * increment);
    System.out.println("scroll");
  }

  public List<RelationShape> getRelationShapes() {
    List<RelationShape> relations = new ArrayList<>();
    for (IShape shape : this.shapes) {
      if (shape instanceof RelationShape) {
        relations.add((RelationShape) shape);
      }
    }
    return relations;
  }

  public List<SquaredShape> getSquaredShapes() {
    List<SquaredShape> squaredShapes = new ArrayList<>();
    for (IShape shape : this.shapes) {
      if (shape instanceof SquaredShape) {
        squaredShapes.add((SquaredShape) shape);
      }
    }
    return squaredShapes;
  }

  public List<ClassShape> getClassShapes() {
    List<ClassShape> classShapes = new ArrayList<>();
    for (IShape shape : this.shapes) {
      if (shape instanceof ClassShape) {
        classShapes.add((ClassShape) shape);
      }
    }
    return classShapes;
  }

  public List<RelationShape> getRelationShapesByClassShape(ClassShape shape) {
    List<RelationShape> relations = new ArrayList<>();
    for (RelationShape relation : this.getRelationShapes()) {
      if (relation.getSource() == shape || relation.getDestination() == shape) {
        relations.add(relation);
      }
    }
    return relations;
  }

  public List<RelationShape> getRelationShapesBySquaredShape(SquaredShape shape) {
    List<RelationShape> relations = new ArrayList<>();
    for (RelationShape relation : this.getRelationShapes()) {
      if (relation.getSource() == shape || relation.getDestination() == shape) {
        relations.add(relation);
      }
    }
    return relations;
  }

  public void deselectAllShapes() {
    for (IShape shape : this.getShapes()) {
      shape.setFocused(false);
    }
    this.repaint();
  }

  public void deselectAllOtherShape(IShape shapeToKeepSelected) {
    for (IShape shape : this.getShapes()) {
      if (shape != shapeToKeepSelected) {
        shape.setFocused(false);
      }
    }
    this.repaint();
  }

  private void showRelationProjectionLine(Graphics2D graphics2D) {
    final float[] dash = {2f, 0f, 2f};
    final Graphics2D newGraphics2D = (Graphics2D) graphics2D.create();
    final BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);

    newGraphics2D.setStroke(stroke);
    newGraphics2D.setColor(Color.GRAY);

    Point mouse = MouseInfo.getPointerInfo().getLocation();
    Point converted = SwingUtilities.convertPoint(MVCCDManager.instance().getMvccdWindow(), mouse, this);

    newGraphics2D.drawLine(RelationCreator.source.getCenter().x, RelationCreator.source.getCenter().y, converted.x, converted.y);
  }

}