package window.editor.diagrammer.listeners;

import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.NoteShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.palette.PaletteButtonType;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;
import window.editor.diagrammer.utils.ResizableBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class SquaredShapeListener extends MouseAdapter implements Serializable {

    private static final long serialVersionUID = 1000;
    private final SquaredShape shape;
    private int cursor;
    private Point startPoint = null;

    public SquaredShapeListener(SquaredShape shape) {
        this.shape = shape;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        this.shape.setFocused(true);
        DiagrammerService.getDrawPanel().deselectAllOtherShape(this.shape);
        this.moveComponentToFront();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.shape.setFocused(true);
        DiagrammerService.getDrawPanel().deselectAllOtherShape(this.shape);
        this.moveComponentToFront();
        ResizableBorder resizableBorder = new ResizableBorder();
        this.cursor = resizableBorder.getCursor(mouseEvent);
        this.startPoint = mouseEvent.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        this.startPoint = null;

        this.shape.setResizing(false);
        this.shape.setFocused(false);

        if (RelationCreator.isCreating) {
            Point converted = SwingUtilities.convertPoint(this.shape, e.getPoint(), DiagrammerService.getDrawPanel());
            Component componentFound = DiagrammerService.getDrawPanel().findComponentAt(converted);

            // Vérifie que le composant sur lequel le clic est relaché n'est pas la zone de dessin
            if (componentFound != DiagrammerService.getDrawPanel()) {
                IShape shapeReleasedOn = (IShape) componentFound;
                if (shapeReleasedOn != null) {
                    RelationCreator.setDestination(shapeReleasedOn);
                } else {
                    RelationCreator.resetSourceAndDestination();
                }
            } else {
                RelationCreator.resetSourceAndDestination();
            }
        }

        // Création
        if (RelationCreator.source != null && RelationCreator.destination != null) {
            RelationCreator.createRelation();
            PalettePanel.setActiveButton(null);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        this.shape.grabFocus();
        this.shape.setFocused(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        this.shape.setFocused(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (PalettePanel.activeButton == null) {
            // Aucun bouton de la palette n'est sélectionné
            this.handleMovements(e.getPoint());
            DiagrammerService.getDrawPanel().updatePanelAndScrollbars();
        } else {
            // Un bouton de la palette est sélectionné
            // Création d'une relation
            if (PalettePanel.activeButton.getType() == PaletteButtonType.RELATION_CREATION) {
                DiagrammerService.getDrawPanel().repaint();
                if (RelationCreator.source == null) {
                    Point converted = SwingUtilities.convertPoint(this.shape, e.getPoint(), DiagrammerService.getDrawPanel());
                    Component componentFound = DiagrammerService.getDrawPanel().findComponentAt(converted);
                    System.out.println(componentFound instanceof NoteShape);
                    RelationCreator.setSource(this.shape);
                }
            }
        }
        DiagrammerService.getDrawPanel().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ResizableBorder resizableBorder = new ResizableBorder();
        if (resizableBorder.isOneRectangleHovered(e)) {
            // Redimensionnement en cours
            this.shape.setBorder(resizableBorder);
            resizableBorder.setVisible(true);
            this.shape.setCursor(Cursor.getPredefinedCursor(resizableBorder.getCursor(e)));
        } else {
            this.shape.setFocused(true);
            this.shape.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }

    private void handleMovements(Point mouseClick) {
        if (this.cursor == Cursor.MOVE_CURSOR) {
            // On déplace l'élément
            this.handleDrag(mouseClick);
        } else {
            // On redimensionne l'élément
            this.handleResize(mouseClick);
        }
    }

    private void handleResize(Point mouseClick) {
        this.shape.setResizing(true);
        if (this.startPoint != null) {
            int x = this.shape.getX();
            int y = this.shape.getY();
            int width = this.shape.getWidth();
            int height = this.shape.getHeight();
            int newX;
            int newY;
            int newWidth;
            int newHeight;
            int differenceX = GridUtils.alignToGrid(mouseClick.x - this.startPoint.x, DiagrammerService.getDrawPanel().getGridSize());
            int differenceY = GridUtils.alignToGrid(mouseClick.y - this.startPoint.y, DiagrammerService.getDrawPanel().getGridSize());
            switch (this.cursor) {
                case Cursor.N_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x, DiagrammerService.getDrawPanel().getGridSize());
                    newY = GridUtils.alignToGrid(y + differenceY, DiagrammerService.getDrawPanel().getGridSize());
                    newWidth = GridUtils.alignToGrid(width, DiagrammerService.getDrawPanel().getGridSize());
                    newHeight = GridUtils.alignToGrid(height - differenceY, DiagrammerService.getDrawPanel().getGridSize());
                    Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);
                    // On vérifie que la nouvelle hauteur est plus grande ou égale à la taille minimale du composant
                    if (newBounds.height >= this.shape.getMinimumSize().height) {
                        this.shape.resize(newBounds);
                    }
                    break;
                }
                case Cursor.S_RESIZE_CURSOR: {
                    newX = x;
                    newY = y;
                    newWidth = width;
                    newHeight = GridUtils.alignToGrid(height + differenceY, DiagrammerService.getDrawPanel().getGridSize());
                    Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);
                    // On vérifie que la nouvelle hauteur est plus grande ou égale à la taille minimale du composant
                    if (newBounds.height >= this.shape.getMinimumSize().height) {
                        this.shape.resize(newBounds);
                    }
                    // On met à jour le point de départ
                    this.startPoint = new Point(mouseClick.x, newHeight);
                    break;
                }
                case Cursor.W_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x + differenceX, DiagrammerService.getDrawPanel().getGridSize());
                    newY = y;
                    newWidth = GridUtils.alignToGrid(width - differenceX, DiagrammerService.getDrawPanel().getGridSize());
                    newHeight = height;
                    Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);
                    // On vérifie que la nouvelle largeur est plus grande ou égale à la taille minimale du composant
                    if (newBounds.width >= this.shape.getMinimumSize().width) {
                        this.shape.resize(newBounds);
                    }
                    break;
                }
                case Cursor.E_RESIZE_CURSOR: {
                    newX = x;
                    newY = y;
                    newWidth = GridUtils.alignToGrid(width + differenceX, DiagrammerService.getDrawPanel().getGridSize());
                    newHeight = height;
                    Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);
                    // On vérifie que la nouvelle largeur est plus grande ou égale à la taille minimale du composant
                    if (newBounds.width >= this.shape.getMinimumSize().width) {
                        this.shape.resize(newBounds);
                    }
                    // On met à jour le point de départ
                    this.startPoint = new Point(newWidth, mouseClick.y);
                    break;
                }
            }
            this.shape.setCursor(Cursor.getPredefinedCursor(this.cursor));

        }
    }

    private void handleDrag(Point mouseClick) {
        int differenceX = mouseClick.x - this.startPoint.x;
        int differenceY = mouseClick.y - this.startPoint.y;
        this.shape.drag(differenceX, differenceY);
    }

    private void moveComponentToFront() {
        DiagrammerService.getDrawPanel().moveToFront(this.shape);
    }
}