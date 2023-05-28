package window.editor.diagrammer.drawpanel;

import console.ViewLogsManager;
import console.WarningLevel;
import diagram.Diagram;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.DialogMessage;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.interfaces.UMLPackageIntegrableShapes;
import window.editor.diagrammer.elements.shapes.MDTableShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.LinkShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.DrawPanelListener;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;
import window.editor.diagrammer.utils.ShapeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Représente la zone de dessin du diagrammeur. C'est sur ce composant que la grille sera dessinée
 * et que les éléments graphiques sont ajoutés (entités, relations, ...)
 */
public class DrawPanel extends JLayeredPane implements Serializable {

    private static final long serialVersionUID = 1000;
    private final Point origin;
    private List<IShape> shapes;
    private boolean isManualScrolling = false;
    private int gridSize = Preferences.DIAGRAMMER_DEFAULT_GRID_SIZE;

    public DrawPanel() {
        this.shapes = new LinkedList<>();
        this.origin = new Point();
        this.initUI();
        this.addListeners();
    }

    public double getZoomFactor() {
        return this.gridSize / (double) Preferences.DIAGRAMMER_DEFAULT_GRID_SIZE;
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

        // Si l'option "Afficher la grille" est cochée dans les préférences, on affiche la grille dans le diagrammeur
        if (PreferencesManager.instance().getApplicationPref().isDIAGRAMMER_SHOW_GRID()) {
            this.drawGrid(graphics2D);
        }
        this.drawRelations(graphics2D);

    // Si on crée une association, une ligne de projection est reliée de l'entité source au curseur de la souris
    if (RelationCreator.isCreating) {
      this.showRelationProjectionLine(graphics2D);
    }

    // Pour afficher le label du diagramme ouvert en haut à gauche du diagramme
    if (MVCCDManager.instance().getCurrentDiagram() != null) {
      this.drawDiagramLabel(graphics2D);
    }

  }

  public void drawDiagramLabel(Graphics2D graphics2D) {
    Diagram currentDiagram = MVCCDManager.instance().getCurrentDiagram();
    MVCCDElement abstractionLevel = currentDiagram.getParent().getParent();
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics2D.drawString(abstractionLevel + " " + currentDiagram.getName(), 0, 10);
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

    private boolean isZoomAllowed(int zoomFactor) {
        return zoomFactor >= Preferences.DIAGRAMMER_MINIMUM_ALLOWED_ZOOM && zoomFactor <= Preferences.DIAGRAMMER_MAXIMUM_ALLOWED_ZOOM;
    }

    public void zoom(int zoomFactor) {
        int oldGridSize = this.gridSize;

        if (this.isZoomAllowed(zoomFactor)) {
            this.setGridSize(zoomFactor);
        }

        DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        Point viewportLocation = parent.getViewport().getLocationOnScreen();

        double x = mouseLocation.x - viewportLocation.x;
        double y = mouseLocation.y - viewportLocation.y;

        // Ajoute l'espace accessible par la scrollbar
        x += parent.getViewport().getViewPosition().getX();
        y += parent.getViewport().getViewPosition().getY();

        // Le résultat est là où le zoom s'effectuera
        double differenceX;
        double differenceY;
        differenceX = x - x * this.gridSize / oldGridSize;
        differenceY = y - y * this.gridSize / oldGridSize;

        // On déplace l'origine
        this.moveOrigin(GridUtils.alignToGrid(-differenceX, this.gridSize), GridUtils.alignToGrid(-differenceY, this.gridSize));

        this.zoomElements(oldGridSize, this.gridSize);

        this.updatePanelAndScrollbars();
        this.repaint();
    }

  public void addShape(IShape element) {
    if (element != null) {
      this.add((JComponent) element);
      this.shapes.add(element);
      this.repaint();
    } else {
      DialogMessage.showError(MVCCDManager.instance().getMvccdWindow(),
          MessagesBuilder.getMessagesProperty("diagrammer.error.add.null.element"));
    }
  }

  public void loadShapes(List<IShape> shapes) {
    this.shapes.addAll(shapes);
    for (IShape shape : shapes) {
      this.add((JComponent) shape);
    }
    this.revalidate();
    this.repaint();
    ViewLogsManager.printMessage(shapes.size() + " formes ont été ajoutées à la zone de dessin.",
        WarningLevel.INFO);
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
    }

    public void unloadAllShapes() {
        this.removeAll();
        this.shapes.clear();
        this.revalidate();
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
     *
     * @param fromFactor facteur de zoom initial
     * @param toFactor   facteur de zoom final
     */
    public void zoomElements(int fromFactor, int toFactor) {
        if (this.zoomAllowed(toFactor)) {
            for (IShape element : this.getShapes()) {
                element.zoom(fromFactor, toFactor);
            }
        }
    }

    public void moveOrigin(int differenceX, int differenceY) {
        this.getOrigin().translate(GridUtils.alignToGrid(differenceX, this.gridSize), GridUtils.alignToGrid(differenceY, this.gridSize));
    }

    /**
     * Vérifie que le zoom est autorisé. Exemple : Si le zoom maximal autorisé est de 25 et qu'un facteur de 26 est passé en paramètre, le zoom n'est pas autorisé.
     *
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

    private boolean isHorizontalScrollbarVisible() {
        final DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
        return parent.getHorizontalScrollBarPolicy() == ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
    }

    private boolean isVerticalScrollbarVisible() {
        final DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
        return parent.getVerticalScrollBarPolicy() == ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
    }

    private void checkIfScrollbarsAreNecessary() {
        final DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
        final Rectangle diaWithoutWhite = this.getContentBounds(this.getShapes(), 0);
        final Dimension viewSize = this.getViewableDiagrampanelSize();
        final boolean vertWasVisible = this.isVerticalScrollbarVisible();
        final boolean horWasVisible = this.isHorizontalScrollbarVisible();
        int verticalScrollbarWidth = 0;
        int horizontalScrollBarHeight = 0;
        if (vertWasVisible) {
            verticalScrollbarWidth = parent.getVerticalScrollBar().getWidth();
        }
        if (horWasVisible) {
            horizontalScrollBarHeight = parent.getHorizontalScrollBar().getHeight();
        }
        // La scrollbar est inclut dans la zone visible -> on la masque
        if (parent.getHorizontalScrollBar().getValue() < this.gridSize && diaWithoutWhite.getX() + diaWithoutWhite.getWidth() <= viewSize.getWidth() + verticalScrollbarWidth) {
            this.setHorizontalScrollbarVisibility(false);
        } else if (parent.getHorizontalScrollBar().getValue() < this.gridSize && this.getViewableDiagrampanelSize().width + parent.getHorizontalScrollBar().getValue() == diaWithoutWhite.getX() + diaWithoutWhite.getWidth()) {
            this.setHorizontalScrollbarVisibility(false);
        } else {
            this.setHorizontalScrollbarVisibility(true);
        }
        if (parent.getVerticalScrollBar().getValue() < this.gridSize && diaWithoutWhite.getY() + diaWithoutWhite.getHeight() <= viewSize.getHeight() + horizontalScrollBarHeight) {
            this.setVerticalScrollbarVisibility(false);
        } else if (parent.getVerticalScrollBar().getValue() < this.gridSize && this.getViewableDiagrampanelSize().height + parent.getVerticalScrollBar().getValue() == diaWithoutWhite.getY() + diaWithoutWhite.getHeight()) {
            this.setVerticalScrollbarVisibility(false);
        } else {
            this.setVerticalScrollbarVisibility(true);
        }
        int adjustedX = 0;
        int adjustedY = 0;
        if (parent.getHorizontalScrollBar().getValue() != 0 && vertWasVisible && !this.isVerticalScrollbarVisible()) {
            adjustedX = GridUtils.alignToGrid(horizontalScrollBarHeight, this.gridSize);
        }
        if (parent.getVerticalScrollBar().getValue() != 0 && horWasVisible && !this.isHorizontalScrollbarVisible()) {
            adjustedY = GridUtils.alignToGrid(verticalScrollbarWidth, this.gridSize);
        }
        if (adjustedX != 0 || adjustedY != 0) {
            this.setPreferredSize(new Dimension((int) (this.getPreferredSize().getWidth() + adjustedX), (int) this.getPreferredSize().getHeight() + adjustedY));
            this.changeViewPosition(adjustedX, adjustedY);
        }
    }

  public void resizeShapesBeforeExport(Collection<ClassShape> elements) {
    elements.stream()
        .filter(e -> !(e instanceof UMLPackageIntegrableShapes))
        .forEach(e ->
            e.setSize((e.getBounds().width + 7), e.getBounds().height)
        );
  }

  public void resizeShapesAfterExport(Collection<ClassShape> elements) {
    elements.stream()
        .filter(e -> !(e instanceof UMLPackageIntegrableShapes))
        .forEach(e ->
            e.setSize((e.getBounds().width - 7), e.getBounds().height)
        );
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
      if (element != null) {
        minx = Math.min(minx, element.getBounds().x - borderWidth);
        miny = Math.min(miny, element.getBounds().y - borderWidth);
        maxx = Math.max(maxx, element.getBounds().x + element.getBounds().width + borderWidth);
        maxy = Math.max(maxy, element.getBounds().y + element.getBounds().height + borderWidth);
      }
    }
    return new Rectangle(minx, miny, maxx - minx, maxy - miny);
  }


  public Dimension getViewableDiagrampanelSize() {
    final DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(
        Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    return parent.getVisibleRect().getSize();
  }

  public void changeViewPosition(int deltaX, int deltaY) {
    final DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(
        Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
    final Point viewportPosition = parent.getViewport().getViewPosition();
    parent.getViewport().setViewSize(this.getPreferredSize());
    parent.getViewport()
        .setViewPosition(new Point(viewportPosition.x + deltaX, viewportPosition.y + deltaY));
  }

    public void updatePanelAndScrollbars() {
        this.insertWhiteSpaceInUpperLeftCorner();
        //this.removeUnnecessaryWhitespaceAroundDiagram();
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
        final DrawPanelComponent parent = (DrawPanelComponent) SwingUtilities.getAncestorNamed(Preferences.DIAGRAMMER_DRAW_PANEL_CONTAINER_NAME, this);
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

    public void drag(int differenceX, int differenceY) {
        for (IShape shape : this.shapes) {
            shape.drag(differenceX, differenceY);
        }
    }

    public void endScroll() {
        // Mise à jour du panel et des scrollbars
        this.updatePanelAndScrollbars();
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

    public List<RelationShape> getRelationShapesWithoutLinks() {
        return this.getRelationShapes().stream().filter(relation -> !(relation instanceof LinkShape)).collect(Collectors.toList());
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

    public List<LinkShape> getLinkShapes() {
        List<LinkShape> linkShapes = new ArrayList<>();
        for (IShape shape : this.shapes) {
            if (shape instanceof LinkShape) {
                linkShapes.add((LinkShape) shape);
            }
        }
        return linkShapes;
    }

    public List<RelationAnchorPointShape> getFloatingLinkAnchorPoints() {
        List<RelationAnchorPointShape> anchorPointsOnRelation = this.getLinkAnchorPointsOnRelation();
        List<RelationAnchorPointShape> floatingAnchorPoints = new ArrayList<>();
        for (RelationAnchorPointShape anchorPoint : anchorPointsOnRelation) {
            boolean isFloating = true;
            for (RelationShape relation : this.getRelationShapesWithoutLinks()) {
                for (Line2D segment : relation.getSegments()) {
                    if (ShapeUtils.getDistanceBetweenLineAndPoint(segment, anchorPoint) == 0) {
                        isFloating = false;
                    }
                }
            }
            if (isFloating) {
                floatingAnchorPoints.add(anchorPoint);
            }
        }
        return floatingAnchorPoints;
    }

    public RelationShape getRelationContainingLinkAnchorPoint(RelationAnchorPointShape linkAnchorPoint) {
        for (RelationShape relation : this.getRelationShapesWithoutLinks()) {
            if (relation.contains(linkAnchorPoint.x, linkAnchorPoint.y)) {
                return relation;
            }
        }
        return null;
    }


    public List<RelationAnchorPointShape> getLinkAnchorPointsOnRelation() {
        List<RelationAnchorPointShape> linkAnchorPoints = new ArrayList<>();
        for (LinkShape link : this.getLinkShapes()) {
            if (link.getDestination() instanceof RelationShape) {
                linkAnchorPoints.add(link.getLastPoint());
            }
        }
        return linkAnchorPoints;
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

    public boolean isManualScrolling() {
        return this.isManualScrolling;
    }

    public void setManualScrolling(boolean manualScrolling) {
        this.isManualScrolling = manualScrolling;
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
