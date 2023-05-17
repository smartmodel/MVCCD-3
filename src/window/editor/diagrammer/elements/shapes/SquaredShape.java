/***
 * Cette classe peut être utilisée en l'état actuel. Elle représente l'objet graphique d'une forme carrée dans ArcDataModeler.
 * Par exemple : les classes UML, les entités, les tables, les packages, les triggers et procédures, etc.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */


package window.editor.diagrammer.elements.shapes;

import window.editor.diagrammer.elements.interfaces.IResizable;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.listeners.SquaredShapeListener;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.IDManager;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public abstract class SquaredShape extends JPanel implements IShape, IResizable, Serializable {

  private static final long serialVersionUID = 1000;
  protected int id;
  protected boolean isFocused = false;
  protected boolean isResizing = false;

  public SquaredShape(int id) {
    this();
    this.id = id;
  }

  public SquaredShape() {
    super();
    this.id = IDManager.generateId();
    this.addListeners();
    this.initUI();
  }

  protected abstract void defineBackgroundColor();

  protected abstract void defineMinimumSize();

  protected abstract void defineSize();

  private void initUI() {
    this.defineMinimumSize();
    this.defineBackgroundColor();
    this.defineSize();
  }

  private void addListeners() {
    SquaredShapeListener listener = new SquaredShapeListener(this);
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  protected abstract void doDraw(Graphics graphics);

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK, this.isFocused || this.isResizing ? 0 : 0));
    this.doDraw(graphics);
  }

  @Override
  public void resize(Rectangle newBounds) {
    this.setBounds(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public Point getCenter() {
    return new Point((int) this.getBounds().getCenterX(), (int) this.getBounds().getCenterY());
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {

    int newXPosition = this.getBounds().x * toFactor / fromFactor;
    int newYPosition = this.getBounds().y * toFactor / fromFactor;
    int newWidth = this.getBounds().width * toFactor / fromFactor;
    int newHeight = this.getBounds().height * toFactor / fromFactor;

    // Set la nouvelle position, la nouvelle taille de l'élément et met à jour la nouvelle taille minimale de l'élément
    this.setSize(GridUtils.alignToGrid(newWidth, toFactor), GridUtils.alignToGrid(newHeight, toFactor));
    this.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
    this.setLocation(GridUtils.alignToGrid(newXPosition, toFactor), GridUtils.alignToGrid(newYPosition, toFactor));
  }

  @Override
  public void drag(int differenceX, int differenceY) {
    Rectangle bounds = this.getBounds();
    bounds.translate(differenceX, differenceY);
    this.setBounds(bounds);
    this.repaint();
  }

  @Override
  public boolean isFocused() {
    return this.isFocused;
  }

  @Override
  public void setFocused(boolean selected) {
    this.isFocused = selected;
    this.repaint();
  }

  @Override
  public String toString() {
    return "SquaredShape{" + "id=" + this.id + '}';
  }

  public void setResizing(boolean resizing) {
    this.isResizing = resizing;
  }
}