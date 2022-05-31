package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Dimension;
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
import window.editor.diagrammer.utils.UIUtils;

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

  public void refreshInformations() {
    this.setZoneEnTeteContent();
    this.setZoneProprietesContent();
    this.repaint();
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
    graphics2D.setFont(UIUtils.getShapeFont());
    int y = (int) (UIUtils.getClassPadding() + graphics2D.getFontMetrics().getAscent());
    for (int i = 0; i < this.zoneEnTete.getElements().size(); i++) {
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
    int y = (int) (this.getZoneMinHeight(this.zoneEnTete.getElements()) + UIUtils.getClassPadding() + graphics2D.getFontMetrics().getHeight());
    this.drawElements(graphics2D, this.zoneProprietes.getElements(), y);
    this.drawZoneProprietesBorder(graphics2D);
  }

  private int getCenterTextPositionX(String element, Graphics2D graphics2D) {
    return this.getWidth() / 2 - graphics2D.getFontMetrics().stringWidth(element) / 2;
  }

  private int getZoneMinHeight(List<String> elements) {
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

  private void drawElements(Graphics2D graphics2D, List<String> elements, int y) {
    graphics2D.setFont(UIUtils.getShapeFont());
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
    double width = Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH;
    if (longestProperty != null) {
      double newWidth = UIUtils.getClassPadding() * 2 + fontMetrics.stringWidth(longestProperty);
      if (longestProperty.isEmpty() || newWidth < Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH) {
        width = Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH;
      } else {
        width = UIUtils.getClassPadding() * 2 + fontMetrics.stringWidth(longestProperty);
      }
    }
    return new Dimension((int) width, height);
  }

  @Override
  protected void defineMinimumSize() {
    this.setMinimumSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH, Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT));
  }

  @Override
  protected void defineSizeAtDefaultZoom() {
    this.setMinimumSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH, Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    this.drawZoneEnTete(graphics2D);
    this.drawZoneProprietes(graphics2D);
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
    if (minimumSize.width >= Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH && minimumSize.height >= Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT) {
      this.setMinimumSize(minimumSize);
      this.setSize(minimumSize);
    }
  }

  protected abstract void setZoneEnTeteContent();

  protected abstract void setZoneProprietesContent();

  protected abstract String getLongestProperty();

  protected abstract void setNameFont(Graphics2D graphics2D);

  public MDElement getRelatedRepositoryElement() {

    return this.relatedRepositoryElement;
  }

  public abstract String getXmlTagName();
}