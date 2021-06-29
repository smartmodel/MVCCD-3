package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.ClassShapeListener;
import window.editor.diagrammer.services.DiagrammerService;

public abstract class ClassShape extends SquaredShape {

  protected ClassShapeZone zoneEnTete = new ClassShapeZone();
  protected ClassShapeZone zoneProprietes = new ClassShapeZone();
  protected ClassShapeZone zoneOperations = new ClassShapeZone();
  protected ClassShapeZone zoneServices = new ClassShapeZone();

  public ClassShape() {
    super();
    this.initUI();
    this.addListeners();

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    this.setBackgroundColor();
    this.drawZoneEnTete(graphics2D);
    this.drawZoneProprietes(graphics2D);
  }

  @Override
  public void drag(int differenceX, int differenceY) {
    super.drag(differenceX, differenceY);
    for (RelationShape relation : DiagrammerService.drawPanel.getRelationShapesByClassShape(this)) {
      if (relation.isReflexive()) {
        for (RelationPointAncrageShape pointAncrage : relation.getPointsAncrage()) {
          pointAncrage.setLocationDifference(differenceX, differenceY);
        }
      } else {
        if (relation.getSource() == this) {
          relation.getPointsAncrage().getFirst().setLocationDifference(differenceX, differenceY);
        } else if (relation.getDestination() == this) {
          relation.getPointsAncrage().getLast().setLocationDifference(differenceX, differenceY);
        }
      }
    }
  }

  private void initUI() {
    // Lorsque la ClassShape est créée, seule la zone d'en-tête est affichée
    this.setZoneEnTeteContent();
    this.setMinimumSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_ENTITY_WIDTH, Preferences.DIAGRAMMER_DEFAULT_ENTITY_HEIGHT));
    this.setSize(this.getMinimumSize());
  }

  private void addListeners() {
    ClassShapeListener listener = new ClassShapeListener();
    this.addMouseMotionListener(listener);
    this.addMouseListener(listener);
  }

  protected void drawZoneEnTete(Graphics2D graphics2D) {
    int y = Preferences.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();
    for (int i = 0; i < this.zoneEnTete.getElements().size(); i++) {
      if (i == 1) {
        this.setNameFont(graphics2D);
      } else {
        graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
      }
      int x = this.getCenterTextPositionX(this.zoneEnTete.getElements().get(i), graphics2D);
      graphics2D.drawString(this.zoneEnTete.getElements().get(i), x, y);
      y += graphics2D.getFontMetrics().getHeight();
    }
    this.drawZoneEnTeteBorder(graphics2D);
  }

  protected void drawZoneProprietes(Graphics2D graphics2D) {
    int y = this.getZoneMinHeight(this.zoneEnTete.getElements()) + Preferences.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();
    this.drawElements(graphics2D, this.zoneProprietes.getElements(), y);
    this.drawZoneProprietesBorder(graphics2D);
  }

  private int getCenterTextPositionX(String element, Graphics2D graphics2D) {
    return this.getWidth() / 2 - graphics2D.getFontMetrics().stringWidth(element) / 2;
  }

  private int getZoneMinHeight(ArrayList<String> elements) {
    FontMetrics fontMetrics = this.getFontMetrics(Preferences.DIAGRAMMER_CLASS_FONT);
    int minHeight = Preferences.DIAGRAMMER_CLASS_PADDING * 2;
    minHeight += fontMetrics.getHeight() * elements.size();
    return minHeight;
  }

  private void drawZoneEnTeteBorder(Graphics2D graphics2D) {
    int height = this.getZoneMinHeight(this.zoneEnTete.getElements());
    graphics2D.drawRect(0, 0, this.getWidth() - 1, height);

  }

  private void drawZoneProprietesBorder(Graphics2D graphics2D) {
    int height;
    if (this.zoneOperations.getElements().isEmpty() && this.zoneServices.getElements().isEmpty()) {
      height = this.getHeight() - this.getZoneMinHeight(this.zoneEnTete.getElements());
    } else {
      height = Preferences.DIAGRAMMER_CLASS_PADDING * 2 + this.zoneProprietes.getElements().size() * graphics2D.getFontMetrics().getHeight();
    }
    graphics2D.drawRect(0, this.getZoneMinHeight(this.zoneEnTete.getElements()), this.getWidth() - 1, height - 1);
  }

  public abstract void setZoneEnTeteContent();
  public abstract void setZoneProprietesContent();
  protected abstract void setBackgroundColor();

  private void drawElements(Graphics2D graphics2D, ArrayList<String> elements, int y) {
    graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
    int x = Preferences.DIAGRAMMER_CLASS_PADDING;
    for (String element : elements) {
      graphics2D.drawString(element, x, y);
      y += graphics2D.getFontMetrics().getHeight();
    }
  }

  protected Dimension calculateMinimumSize() {
    FontMetrics fontMetrics = this.getFontMetrics(Preferences.DIAGRAMMER_CLASS_FONT);
    int height = this.getZoneMinHeight(this.zoneEnTete.getElements()) + this.getZoneMinHeight(this.zoneProprietes.getElements());
    String longestProperty = this.getLongestProperty();
    int width = Preferences.DIAGRAMMER_DEFAULT_ENTITY_WIDTH;
    if (longestProperty != null) {
      if (longestProperty.isEmpty()) {
        width = Preferences.DIAGRAMMER_DEFAULT_ENTITY_WIDTH;
      } else {
        width = Preferences.DIAGRAMMER_CLASS_PADDING * 2 + fontMetrics.stringWidth(longestProperty);
      }
    }
    return new Dimension(width, height);
  }

  protected abstract String getLongestProperty();

  public void updateRelations() {
    for (RelationShape relation : DiagrammerService.drawPanel.getRelationShapes()) {
      if (relation.getSource() == this || relation.getDestination() == this) {
        relation.updateFirstAndLastPointsAncrage(this, true);
      }
    }
  }

  public abstract void setNameFont(Graphics2D graphics2D);

  protected void updateSizeAndMinimumSize() {
    Dimension minimumSize = this.calculateMinimumSize();
    this.setMinimumSize(minimumSize);
    this.setSize(minimumSize);
  }
}
