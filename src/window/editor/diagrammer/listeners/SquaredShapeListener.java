package window.editor.diagrammer.listeners;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.panels.DrawPanel;
import window.editor.diagrammer.elements.SquaredShape;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.ResizableBorder;

public class SquaredShapeListener extends MouseAdapter {

  private int cursor;
  private SquaredShape component;
  private Point startPoint = null;

  public SquaredShapeListener(SquaredShape component) {
    this.component = component;
  }

  @Override
  public void mouseDragged(MouseEvent mouseEvent) {
    this.handleMovements(mouseEvent.getPoint());

    // On met à jour le drawPanel
    DrawPanel drawPanel = (DrawPanel) SwingUtilities.getAncestorNamed(DiagrammerConstants.DIAGRAMMER_DRAW_PANEL_NAME, this.component);
    drawPanel.getHandler().updatePanelAndScrollbars();
  }

  @Override
  public void mousePressed(MouseEvent mouseEvent) {
    this.moveComponentToFront(mouseEvent);

    ResizableBorder resizableBorder = (ResizableBorder) this.component.getBorder();
    cursor = resizableBorder.getCursor(mouseEvent);
    this.startPoint = mouseEvent.getPoint();

    this.component.requestFocus();
    this.component.repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    this.moveComponentToFront(e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
      ResizableBorder resizableBorder = (ResizableBorder) this.component.getBorder();
      this.component.setCursor(Cursor.getPredefinedCursor(resizableBorder.getCursor(e)));
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    this.startPoint = null;
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

    if (startPoint != null) {

      int x = this.component.getX();
      int y = this.component.getY();
      int width = this.component.getWidth();
      int height = this.component.getHeight();

      int newX;
      int newY;
      int newWidth;
      int newHeight;

      int differenceX = GridUtils.alignToGrid(mouseClick.x - startPoint.x, DrawPanel.getGridSize());
      int differenceY = GridUtils.alignToGrid(mouseClick.y - startPoint.y, DrawPanel.getGridSize());
      System.out.println();

      switch (cursor) {
        case Cursor.N_RESIZE_CURSOR: {
          newX = GridUtils.alignToGrid(x, DrawPanel.getGridSize());
          newY = GridUtils.alignToGrid(y + differenceY, DrawPanel.getGridSize());
          newWidth = GridUtils.alignToGrid(width, DrawPanel.getGridSize());
          newHeight = GridUtils.alignToGrid(height - differenceY, DrawPanel.getGridSize());
          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle hauteur est plus grande ou égale à la taille minimale du composant
          if (newBounds.height >= this.component.getMinimumSize().height) {
            this.component.resize(newBounds);
          }

          break;
        }
        case Cursor.S_RESIZE_CURSOR: {
          newX = x;
          newY = y;
          newWidth = width;
          newHeight = GridUtils.alignToGrid(height + differenceY, DrawPanel.getGridSize());
          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle hauteur est plus grande ou égale à la taille minimale du composant
          if (newBounds.height >= this.component.getMinimumSize().height) {
            this.component.resize(newBounds);
          }

          // On met à jour le point de départ
          this.startPoint = new Point(mouseClick.x, newHeight);
          break;
        }
        case Cursor.W_RESIZE_CURSOR: {
          newX = GridUtils.alignToGrid(x + differenceX, DrawPanel.getGridSize());
          newY = y;
          newWidth = GridUtils.alignToGrid(width - differenceX, DrawPanel.getGridSize());
          newHeight = height;
          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle largeur est plus grande ou égale à la taille minimale du composant
          if (newBounds.width >= this.component.getMinimumSize().width) {
            this.component.resize(newBounds);
          }

          break;
        }
        case Cursor.E_RESIZE_CURSOR: {
          newX = x;
          newY = y;
          newWidth = GridUtils.alignToGrid(width + differenceX, DrawPanel.getGridSize());
          newHeight = height;
          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle largeur est plus grande ou égale à la taille minimale du composant
          if (newBounds.width >= this.component.getMinimumSize().width) {
            this.component.resize(newBounds);
          }

          // On met à jour le point de départ
          this.startPoint = new Point(newWidth, mouseClick.y);

          break;
        }
        case Cursor.NW_RESIZE_CURSOR: {
          newX = GridUtils.alignToGrid(x + differenceX, DrawPanel.getGridSize());
          newY = GridUtils.alignToGrid(y + differenceY, DrawPanel.getGridSize());
          newWidth = GridUtils.alignToGrid(width - differenceX, DrawPanel.getGridSize());
          newHeight = GridUtils.alignToGrid(height - differenceY, DrawPanel.getGridSize());
          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle largeur et la nouvelle hauteur sont plus grandes ou égales à la taille minimale du composant
          if (newBounds.width >= this.component.getMinimumSize().width
              && newBounds.height >= this.component.getMinimumSize().height) {
            this.component.resize(newBounds);
          }

          break;
        }
        case Cursor.NE_RESIZE_CURSOR: {
          newX = x;
          newY = GridUtils.alignToGrid(y + differenceY, DrawPanel.getGridSize());
          newWidth = GridUtils.alignToGrid(width + differenceX, DrawPanel.getGridSize());
          newHeight = GridUtils.alignToGrid(height - differenceY, DrawPanel.getGridSize());
          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle largeur et la nouvelle hauteur sont plus grandes ou égales à la taille minimale du composant
          if (newBounds.width >= this.component.getMinimumSize().width
              && newBounds.height >= this.component.getMinimumSize().height) {
            this.component.resize(newBounds);
          }

          // On met à jour le point de départ
          this.startPoint = new Point(newWidth, 0);

          break;
        }
        case Cursor.SW_RESIZE_CURSOR: {
          newX = GridUtils.alignToGrid(x + differenceX, DrawPanel.getGridSize());
          newY = y;
          newWidth = GridUtils.alignToGrid(width - differenceX, DrawPanel.getGridSize());
          newHeight = GridUtils.alignToGrid(height + differenceY, DrawPanel.getGridSize());
          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle largeur et la nouvelle hauteur sont plus grandes ou égales à la taille minimale du composant
          if (newBounds.width >= this.component.getMinimumSize().width
              && newBounds.height >= this.component.getMinimumSize().height) {
            this.component.resize(newBounds);
          }

          // On met à jour le point de départ
          this.startPoint = new Point(0, newHeight);

          break;
        }
        case Cursor.SE_RESIZE_CURSOR: {
          newX = x;
          newY = y;
          newWidth = GridUtils.alignToGrid(width + differenceX, DrawPanel.getGridSize());
          newHeight = GridUtils.alignToGrid(height + differenceY, DrawPanel.getGridSize());

          Rectangle newBounds = new Rectangle(newX, newY, newWidth, newHeight);

          // On vérifie que la nouvelle largeur et la nouvelle hauteur sont plus grandes ou égales à la taille minimale du composant
          if (newBounds.width >= this.component.getMinimumSize().width
              && newBounds.height >= this.component.getMinimumSize().height) {
            this.component.resize(newBounds);
          }

          // Mise à jour du point de départ
          this.startPoint = new Point(newWidth, newHeight);

          break;
        }

      }
      this.component.setCursor(Cursor.getPredefinedCursor(cursor));

    }
  }

  private void handleDrag(Point mouseClick) {
    int differenceX = GridUtils.alignToGrid(mouseClick.x - startPoint.x, DrawPanel.getGridSize());
    int differenceY = GridUtils.alignToGrid(mouseClick.y - startPoint.y, DrawPanel.getGridSize());
    this.component.drag(differenceX, differenceY);
  }

  private void moveComponentToFront(MouseEvent event){
    SquaredShape shape = (SquaredShape) event.getSource();
    DrawPanel drawPanel = (DrawPanel) SwingUtilities.getAncestorNamed(DiagrammerConstants.DIAGRAMMER_DRAW_PANEL_NAME, shape);
    drawPanel.moveToFront(shape);
  }
}
