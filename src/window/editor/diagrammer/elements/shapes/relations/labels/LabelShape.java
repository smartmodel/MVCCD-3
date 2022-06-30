package window.editor.diagrammer.elements.shapes.relations.labels;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import javax.swing.JPanel;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.LabelShapeListener;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.ShapeUtils;
import window.editor.diagrammer.utils.UIUtils;

public class LabelShape extends JPanel implements Serializable {

  private static final long serialVersionUID = 1000;
  private final int MARGIN = 20;
  private String text;
  private int distanceInXFromPointAncrage;
  private int distanceInYFromPointAncrage;
  private RelationAnchorPointShape pointAncrage;
  private RelationShape relationShape;
  private boolean isRole;
  private LabelType type;

  private LabelShape() {
    this.initUI();
    this.addListeners();
  }

  public LabelShape(RelationAnchorPointShape pointAncrage, String text, LabelType type, RelationShape relationShape, int distanceInXFromPointAncrage, int distanceInYFromPointAncrage) {
    this();

    this.text = text;
    this.type = type;
    this.pointAncrage = pointAncrage;
    this.relationShape = relationShape;
    this.isRole = (type == LabelType.DESTINATION_ROLE || type == LabelType.SOURCE_ROLE);

    this.distanceInXFromPointAncrage = distanceInXFromPointAncrage;
    this.distanceInYFromPointAncrage = distanceInYFromPointAncrage;

    Point initialLocation = this.calculateLocation(true);

    this.setBounds(initialLocation.x + distanceInXFromPointAncrage, initialLocation.y + distanceInYFromPointAncrage, 1, 1);

    this.repaint();
    DiagrammerService.getDrawPanel().repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    Dimension size = this.calculateSize(graphics2D);
    this.setFont(UIUtils.getCardinalityFont());
    this.setSize(size);
    graphics2D.drawString(this.text, this.calculateTextXCoordinate(graphics2D), this.calculateTextYCoordinate(graphics2D));
  }

  private void initUI() {
    this.setOpaque(false); // Pour la transparence
    this.setVisible(false);
  }

  private int calculateTextXCoordinate(Graphics2D graphics2D) {
    FontMetrics fontMetrics = graphics2D.getFontMetrics();
    return (this.getWidth() / 2) - (fontMetrics.stringWidth(this.text) / 2);
  }

  private int calculateTextYCoordinate(Graphics2D graphics2D) {
    FontMetrics fontMetrics = graphics2D.getFontMetrics();
    return (this.getHeight() / 2) + (fontMetrics.getAscent() / 2);
  }

  private void addListeners() {
    LabelShapeListener listener = new LabelShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  private Point calculateDestionationInformationsFirstDisplay() {
    int x;
    int y;
    SquaredShape nearestSquaredShape = this.relationShape.getNearestSquaredShape(this.pointAncrage);

    if (ShapeUtils.pointIsOnLeftSideOfBounds(this.pointAncrage, nearestSquaredShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté gauche de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      } else {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      }
    } else if (ShapeUtils.pointIsOnRightSideOfBounds(this.pointAncrage, nearestSquaredShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté droit de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      } else {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    } else if (ShapeUtils.pointIsOnBottomSideOfBounds(this.pointAncrage, nearestSquaredShape.getBounds())) {
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

    SquaredShape nearestSquaredShape = this.relationShape.getNearestSquaredShape(this.pointAncrage);

    if (ShapeUtils.pointIsOnLeftSideOfBounds(this.pointAncrage, nearestSquaredShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté gauche de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y + this.getHeight() + this.MARGIN;
      } else {
        x = this.pointAncrage.x - this.getWidth() - this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    } else if (ShapeUtils.pointIsOnRightSideOfBounds(this.pointAncrage,
        nearestSquaredShape.getBounds())) {
      // Le point d'ancrage est situé sur le côté droit de la ClassShape
      if (this.isRole) {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y + this.MARGIN;
      } else {
        x = this.pointAncrage.x + this.MARGIN;
        y = this.pointAncrage.y - this.getHeight() - this.MARGIN;
      }
    } else if (ShapeUtils.pointIsOnBottomSideOfBounds(this.pointAncrage, nearestSquaredShape.getBounds())) {
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

  private Dimension calculateSize(Graphics2D graphics2D) {
    int width = (int) (graphics2D.getFontMetrics().stringWidth(this.text) + 2 * UIUtils.getCardinalityPadding());
    int height = (int) (graphics2D.getFontMetrics().getHeight() + 2 * UIUtils.getCardinalityPadding());
    return new Dimension(width, height);
  }

  public void zoom(int fromFactor, int toFactor) {
    int newXPosition = GridUtils.alignToGrid((double) this.getX() * toFactor / fromFactor, toFactor);
    int newYPosition = GridUtils.alignToGrid((double) this.getY() * toFactor / fromFactor, toFactor);
    int newWidth = GridUtils.alignToGrid((double) this.getBounds().width * toFactor / fromFactor, toFactor);
    int newHeight = GridUtils.alignToGrid((double) this.getBounds().height * toFactor / fromFactor, toFactor);

    this.setSize(newWidth, newHeight);
    this.setLocation(newXPosition, newYPosition);
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

  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_LABEL_XML_TAG;
  }

  public RelationAnchorPointShape getPointAncrage() {
    return this.pointAncrage;
  }

  public LabelType getType() {
    return this.type;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }
}