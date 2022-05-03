package window.editor.diagrammer.elements.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import preferences.Preferences;

public class NoteShape extends SquaredShape {

  private static final long serialVersionUID = -8239597996393623834L;
  private final int MARGIN = 20;

  @Override
  protected void defineBackgroundColor() {
    this.setBackground(null);
    this.setOpaque(false);
  }

  @Override
  protected void defineMinimumSize() {
    this.setMinimumSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_NOTE_WIDTH, Preferences.DIAGRAMMER_DEFAULT_NOTE_HEIGHT));
  }

  @Override
  protected void defineSize() {
    this.setSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_NOTE_WIDTH, Preferences.DIAGRAMMER_DEFAULT_NOTE_HEIGHT));
  }

  @Override
  protected void doDraw(Graphics graphics) {
    this.drawBase(graphics);
    this.drawCorner(graphics);
  }

  private void drawBase(Graphics graphics) {
    // Dessine la forme
    Polygon shape = new Polygon();
    Point p1 = new Point(0, 0);
    Point p2 = new Point(this.getWidth() - this.MARGIN, 0);
    Point p3 = new Point(this.getWidth(), this.MARGIN);
    Point p4 = new Point(this.getWidth(), this.getHeight());
    Point p5 = new Point(0, this.getHeight());

    shape.addPoint(p1.x, p1.y);
    shape.addPoint(p2.x, p2.y);
    shape.addPoint(p3.x, p3.y);
    shape.addPoint(p4.x, p4.y);
    shape.addPoint(p5.x, p5.y);

    graphics.setColor(Preferences.DIAGRAMMER_NOTE_DEFAULT_BACKGROUND_COLOR);
    graphics.fillPolygon(shape);

    // Dessine la bordure
    Polygon border = new Polygon();
    border.addPoint(p1.x, p1.y);
    border.addPoint(p2.x, p2.y);
    border.addPoint(p3.x - 1, p3.y - 1);
    border.addPoint(p4.x - 1, p4.y - 1);
    border.addPoint(p5.x, p5.y - 1);
    graphics.setColor(Color.BLACK);
    graphics.drawPolygon(border);
  }

  private void drawCorner(Graphics graphics) {
    // Dessine le petit triangle situé en haut à droite qui représente le pli
    Polygon triangle = new Polygon();
    Point p1 = new Point(this.getWidth() - this.MARGIN, 0);
    Point p2 = new Point(this.getWidth(), this.MARGIN);
    Point p3 = new Point(this.getWidth() - this.MARGIN, this.MARGIN);
    triangle.addPoint(p1.x, p1.y);
    triangle.addPoint(p2.x, p2.y);
    triangle.addPoint(p3.x, p3.y);
    graphics.setColor(Color.WHITE);
    graphics.fillPolygon(triangle);

    // Dessine la bordure
    graphics.setColor(Color.BLACK);
    graphics.drawPolygon(triangle);
  }

  private void drawCornerFakeBorder(Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.drawLine(this.getWidth() - this.MARGIN, 0, this.getWidth(), this.MARGIN);
  }
}