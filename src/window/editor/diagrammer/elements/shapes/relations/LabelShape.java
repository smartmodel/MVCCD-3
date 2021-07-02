package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.listeners.LabelShapeListener;
import window.editor.diagrammer.utils.GeometryUtils;

public class LabelShape extends JLabel {

  private final int MARGIN = 10;
  private int distanceInXFromPointAncrage = 0;
  private int distanceInYFromPointAncrage = 0;
  private RelationPointAncrageShape pointAncrage;
  private RelationShape relationShape;
  private boolean isRole;
  private boolean firstDisplay;

  public LabelShape() {
    this.initUI();
    this.addListeners();
  }

  public LabelShape(RelationPointAncrageShape pointAncrage, RelationShape relationShape, boolean isRole) {
    this();
    this.pointAncrage = pointAncrage;
    this.relationShape = relationShape;
    this.isRole = isRole;
    this.firstDisplay = true;
    final Point location = this.calculateLocation(this.firstDisplay);
    this.setBounds(location.x, location.y, 110, 30);
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    final Graphics2D graphics2D = (Graphics2D) g;
    final Point location = this.calculateLocation(this.firstDisplay);
    final Dimension size = this.calculateSize(graphics2D);
    this.setBounds(location.x, location.y, size.width, size.height);
  }

  private void addListeners() {
    LabelShapeListener listener = new LabelShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  private Point calculateDestionationInformationsFirstDisplay() {
    int x;
    int y;
    final ClassShape nearestClassShape = this.relationShape.getNearestClassShape(this.pointAncrage);

    if (GeometryUtils.pointIsOnLeftSideOfBounds(this.pointAncrage, nearestClassShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté gauche de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      } else {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      }
    } else if (GeometryUtils.pointIsOnRightSideOfBounds(this.pointAncrage, nearestClassShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté droit de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      } else {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    } else if (GeometryUtils.pointIsOnBottomSideOfBounds(this.pointAncrage, nearestClassShape.getBounds())) {
      // Le point d'ancrage est situé sur le bas de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      } else {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      }
    } else {
      // Le point d'ancrage est situé sur le haut de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      } else {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    }
    return new Point(x, y);
  }

  private Point calculateSourceInformationsFirstDisplay() {
    int x;
    int y;
    final ClassShape nearestClassShape = this.relationShape.getNearestClassShape(this.pointAncrage);

    if (GeometryUtils.pointIsOnLeftSideOfBounds(this.pointAncrage, nearestClassShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté gauche de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y + this.getHeight() + this.MARGIN;
      } else {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    } else if (GeometryUtils.pointIsOnRightSideOfBounds(this.pointAncrage, nearestClassShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté droit de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      } else {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    } else if (GeometryUtils.pointIsOnBottomSideOfBounds(this.pointAncrage, nearestClassShape.getBounds())) {
      // Le point d'ancrage est situé sur le bas de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      } else {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      }
    } else {
      // Le point d'ancrage est situé sur le haut de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      } else {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    }
    return new Point(x, y);
  }

  private void initUI() {
    this.setOpaque(false); // Pour la transparence
    this.setVisible(false);
    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.setVerticalAlignment(SwingConstants.CENTER);
    this.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
  }

  private Dimension calculateSize(Graphics2D graphics2D) {
    final int width = graphics2D.getFontMetrics().stringWidth(this.getText());
    final int height = graphics2D.getFontMetrics().getHeight();
    return new Dimension(width, height);
  }

  public Point calculateLocation(boolean firstDisplay) {
    if (!firstDisplay) {
      // S'il s'agit du nom d'association
      if (!this.relationShape.isFirstOrLastPoint(this.pointAncrage)) {
        return new Point(this.relationShape.getCenter().x + this.distanceInXFromPointAncrage, this.relationShape.getCenter().y + this.distanceInYFromPointAncrage);
      } else {
        return new Point(this.pointAncrage.x + this.distanceInXFromPointAncrage, this.pointAncrage.y + this.distanceInYFromPointAncrage);
      }
    } else {
      if (this.relationShape.isFirstPoint(this.pointAncrage)) {
        return this.calculateSourceInformationsFirstDisplay();
      } else if (this.relationShape.isLastPoint(this.pointAncrage)) {
        return this.calculateDestionationInformationsFirstDisplay();
      } else {
        return this.relationShape.getCenter();
      }
    }
  }

  public int getDistanceInXFromPointAncrage() {
    return this.distanceInXFromPointAncrage;
  }

  public void setDistanceInXFromPointAncrage(int distanceInXFromPointAncrage) {
    this.distanceInXFromPointAncrage = distanceInXFromPointAncrage;
  }

  public int getDistanceInYFromPointAncrage() {
    return this.distanceInYFromPointAncrage;
  }

  public void setDistanceInYFromPointAncrage(int distanceInYFromPointAncrage) {
    this.distanceInYFromPointAncrage = distanceInYFromPointAncrage;
  }

  public void setFirstDisplay(boolean firstDisplay) {
    this.firstDisplay = firstDisplay;
  }
}
