package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.List;
import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.ClassShapeListener;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.UIUtils;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public abstract class ClassShape extends SquaredShape implements Serializable {

  private static final long serialVersionUID = -7287863958022851391L;
  protected ClassShapeZone zoneEnTete = new ClassShapeZone();
  protected ClassShapeZone zoneProprietes = new ClassShapeZone();
  protected ClassShapeZone zoneOperations = new ClassShapeZone();
  protected ClassShapeZone zoneServices = new ClassShapeZone();
  protected MDElement relatedRepositoryElement;

  public ClassShape(int id) {
    super(id);
    this.initUI();
    this.addListeners();
  }

  public ClassShape() {
    super();
    this.initUI();
    this.addListeners();
  }

  public ClassShape(MDElement relatedRepositoryElement, int id) {
    super(id);
    this.addListeners();
    this.relatedRepositoryElement = relatedRepositoryElement;
    this.initUI();
  }

  public ClassShape(MDElement relatedRepositoryElement) {
    super();
    this.addListeners();
    this.relatedRepositoryElement = relatedRepositoryElement;
    this.initUI();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    this.drawZoneEnTete(graphics2D);
    this.drawZoneProprietes(graphics2D);
  }

  public void refreshInformations() {
    setZoneEnTeteContent();
    setZoneProprietesContent();
    repaint();
  }


  private void initUI() {
    // Lorsque la ClassShape est créée, seule la zone d'en-tête est affichée
    this.setZoneEnTeteContent();
    this.setMinimumSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH, Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT));
  }

  private void addListeners() {
    ClassShapeListener listener = new ClassShapeListener(this);
    this.addMouseMotionListener(listener);
    this.addMouseListener(listener);
  }

  protected void drawZoneEnTete(Graphics2D graphics2D) {
    FontMetrics fontMetrics = graphics2D.getFontMetrics(UIUtils.getShapeFont());
    int y = (int) UIUtils.getClassPadding() + fontMetrics.getHeight();
    for (int i = 0; i < this.zoneEnTete.getElements().size(); i++) {
      graphics2D.setFont(UIUtils.getShapeFont());
      // Nom de la classe
      if (i == 1) {
        this.setNameFont(graphics2D);
      }
      int x = this.getCenterTextPositionX(this.zoneEnTete.getElements().get(i), graphics2D);
      graphics2D.drawString(this.zoneEnTete.getElements().get(i), x, y);
      y += graphics2D.getFontMetrics().getHeight();
    }
    this.drawZoneEnTeteBorder(graphics2D);
  }

  protected void drawZoneProprietes(Graphics2D graphics2D) {
    FontMetrics fontMetrics = graphics2D.getFontMetrics(UIUtils.getShapeFont());
    int y = (int) (this.getZoneMinHeight(this.zoneEnTete.getElements()) + UIUtils.getClassPadding() + fontMetrics.getHeight());
    this.drawElements(graphics2D, this.zoneProprietes.getElements(), y, UIUtils.getShapeFont());
    this.drawZoneProprietesBorder(graphics2D);
  }

  public int getCenterTextPositionX(String element, Graphics2D graphics2D) {
    return this.getWidth() / 2 - graphics2D.getFontMetrics().stringWidth(element) / 2;
  }

  public int getZoneMinHeight(List<String> elements) {
    FontMetrics fontMetrics = this.getFontMetrics(UIUtils.getShapeFont());
    double minHeight = UIUtils.getClassPadding() * 2;
    minHeight += fontMetrics.getHeight() * elements.size();
    return (int) minHeight;
  }

  private void drawZoneEnTeteBorder(Graphics2D graphics2D) {
    int height = this.getZoneMinHeight(this.zoneEnTete.getElements());
    graphics2D.drawRect(0, 0, this.getWidth() - 1, height);
  }

  private void drawZoneProprietesBorder(Graphics2D graphics2D) {
    double height;
    if (this.zoneOperations.getElements().isEmpty() && this.zoneServices.getElements().isEmpty()) {
      height = this.getHeight() - this.getZoneMinHeight(this.zoneEnTete.getElements());
    } else {
      height = UIUtils.getClassPadding() * 2 + this.zoneProprietes.getElements().size() * graphics2D.getFontMetrics().getHeight();
    }
    graphics2D.drawRect(0, this.getZoneMinHeight(this.zoneEnTete.getElements()), this.getWidth() - 1, (int) (height - 1));
  }

  public void drawElements(Graphics2D graphics2D, List<String> elements, int y, Font font) {
    graphics2D.setFont(font);
    double x = UIUtils.getClassPadding();
    for (String element : elements) {
      graphics2D.drawString(element, (int) x, y);
      y += graphics2D.getFontMetrics().getHeight();
    }
  }

  protected Dimension calculateMinimumSize() {
    FontMetrics fontMetrics = this.getFontMetrics(UIUtils.getShapeFont());
    int height = this.getZoneMinHeight(this.zoneEnTete.getElements()) + this.getZoneMinHeight(this.zoneProprietes.getElements());
    String longestProperty = this.getLongestProperty();
    double width = UIUtils.getClassShapeDefaultSize().width;
    if (longestProperty != null) {
      double newWidth = UIUtils.getClassPadding() * 2 + fontMetrics.stringWidth(longestProperty);
      if (longestProperty.isEmpty() || newWidth < UIUtils.getClassShapeDefaultSize().width) {
        width = UIUtils.getClassShapeDefaultSize().width;
      } else {
        width = UIUtils.getClassPadding() * 2 + fontMetrics.stringWidth(longestProperty);
      }
    }
    return new Dimension((int) width, height);
  }

  @Override
  protected void defineMinimumSize() {
    this.setMinimumSize(UIUtils.getClassShapeDefaultSize());
  }

  @Override
  protected void defineSizeAtDefaultZoom() {
    this.setMinimumSize(UIUtils.getClassShapeDefaultSize());
  }



  public void updateRelations() {
    for (RelationShape relation : DiagrammerService.getDrawPanel().getRelationShapes()) {
      if (relation.getSource() == this || relation.getDestination() == this) {
        relation.updateFirstAndLastAnchorPoint(this, true);
      }
    }
  }

  protected void updateSizeAndMinimumSize() {
    Dimension minimumSize = this.calculateMinimumSize();
    if (minimumSize.width >= UIUtils.getClassShapeDefaultSize().width && minimumSize.height >= UIUtils.getClassShapeDefaultSize().height) {
      this.setMinimumSize(minimumSize);
      this.setSize(minimumSize);
    }
  }

  protected abstract void setZoneEnTeteContent();

  protected abstract void setZoneProprietesContent();

  protected abstract String getLongestProperty();

  protected abstract void setNameFont(Graphics2D graphics2D);

  public MDElement getRelatedRepositoryElement() {

    return relatedRepositoryElement;
  }

  public abstract String getXmlTagName();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClassShape that = (ClassShape) o;
    return Objects.equals(zoneEnTete, that.zoneEnTete) && Objects.equals(
        zoneProprietes, that.zoneProprietes) && Objects.equals(zoneOperations,
        that.zoneOperations) && Objects.equals(zoneServices, that.zoneServices)
        && Objects.equals(getRelatedRepositoryElement(),
        that.getRelatedRepositoryElement());
  }

  @Override
  public int hashCode() {
    return Objects.hash(zoneEnTete, zoneProprietes, zoneOperations, zoneServices,
        getRelatedRepositoryElement());
  }
}