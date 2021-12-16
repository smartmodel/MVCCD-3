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
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;

public class LabelShape extends JLabel {

  private final int MARGIN = 20;
  private int distanceInXFromPointAncrage;
  private int distanceInYFromPointAncrage;
  private RelationPointAncrageShape pointAncrage;
  private RelationShape relationShape;
  private boolean isRole;
  private boolean firstDisplay;
  private LabelType type;

  private LabelShape() {
    this.initUI();
    this.addListeners();
  }

  public LabelShape(RelationPointAncrageShape pointAncrage, LabelType type, RelationShape relationShape, int distanceInXFromPointAncrage, int distanceInYFromPointAncrage) {
    this();

    this.type = type;
    this.pointAncrage = pointAncrage;
    this.relationShape = relationShape;
    this.isRole = (type == LabelType.DESTINATION_ROLE || type == LabelType.SOURCE_ROLE);

    this.distanceInXFromPointAncrage = distanceInXFromPointAncrage;
    this.distanceInYFromPointAncrage = distanceInYFromPointAncrage;

    Point initialLocation = this.calculateLocation(true);

    this.setBounds(initialLocation.x + distanceInXFromPointAncrage, initialLocation.y + distanceInYFromPointAncrage, 110, 30);

    DiagrammerService.getDrawPanel().add(this);

    repaint();
    DiagrammerService.getDrawPanel().repaint();
  }



  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    Dimension size = this.calculateSize(graphics2D);


    this.setBounds(pointAncrage.getBounds().x + distanceInXFromPointAncrage, pointAncrage.getBounds().y + distanceInYFromPointAncrage, size.width, size.height);

  }

  private void initUI() {
    this.setOpaque(false); // Pour la transparence
    this.setVisible(false);
    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.setVerticalAlignment(SwingConstants.CENTER);
    this.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
  }


  private void addListeners() {
    LabelShapeListener listener = new LabelShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  private Point calculateDestionationInformationsFirstDisplay() {
    int x;
    int y;
    ClassShape nearestClassShape = this.relationShape.getNearestClassShape(this.pointAncrage);

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

    ClassShape nearestClassShape = this.relationShape.getNearestClassShape(this.pointAncrage);

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

  private Dimension calculateSize(Graphics2D graphics2D) {
    int width = graphics2D.getFontMetrics().stringWidth(this.getText());
    int height = graphics2D.getFontMetrics().getHeight();
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
      if (this.relationShape.isFirstPoint(this.pointAncrage))
        return this.calculateSourceInformationsFirstDisplay();
      else if (this.relationShape.isLastPoint(this.pointAncrage))
        return this.calculateDestionationInformationsFirstDisplay();
      else
        return this.relationShape.getCenter();
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

  public String getXmlTagName(){
    return Preferences.DIAGRAMMER_LABEL_XML_TAG;
  }

  public RelationPointAncrageShape getPointAncrage() {
    return pointAncrage;
  }

  public LabelType getType() {
    return type;
  }
}
