package window.editor.diagrammer.elements.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.Objects;
import mdr.MDRTable;
import mpdr.MPDRTable;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.listeners.MDTableShapeListener;

public class MDTableShape extends ClassShape implements Serializable {

  private final String name;
  private final Color COLOR = Color.decode("#A0F0CF");


  public MDTableShape(MDRTable mdrTable) {
    super(mdrTable);
    this.name = mdrTable.getName();
    this.addListeners();
    this.updateSizeAndMinimumSize();
  }

  @Override
  protected void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    graphics.setColor(COLOR);

    graphics.fillRoundRect(0, 0, width, height, 20, 20);

    graphics.setColor(Color.black);
    this.drawZoneEnTete(graphics);
    this.drawZoneProprietes(graphics);
    this.drawZoneOperations(graphics);
  }

  @Override
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
  }

  @Override
  protected void drawZoneProprietes(Graphics2D graphics2D) {
    int y =
        this.getZoneMinHeight(this.zoneEnTete.getElements()) + Preferences.DIAGRAMMER_CLASS_PADDING
            + graphics2D.getFontMetrics().getHeight();
    this.drawElements(graphics2D, this.zoneProprietes.getElements(), y);
  }

  private void drawZoneOperations(Graphics2D graphics2D) {
    this.setZoneOperationsContent();
    int y = this.getZoneMinHeight(this.zoneProprietes.getElements()) + this.getZoneMinHeight(
        this.zoneEnTete.getElements()) + Preferences.DIAGRAMMER_CLASS_PADDING
        + graphics2D.getFontMetrics().getHeight();
    this.drawElements(graphics2D, this.zoneOperations.getElements(), y);
    this.drawBorders(graphics2D);
  }


  @Override
  protected void initUI() {
    this.setZoneEnTeteContent();
    this.setZoneProprietesContent();
    this.setZoneOperationsContent();
    this.setMinimumSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH,
        Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT));
    this.setSize(this.getMinimumSize());
  }

  private void drawBorders(Graphics2D graphics2D) {
    graphics2D.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 20, 20);
    graphics2D.drawLine(0, this.getZoneMinHeight(this.zoneEnTete.getElements()), this.getWidth(),
        this.getZoneMinHeight(this.zoneEnTete.getElements()));

    int height = this.getZoneMinHeight(this.zoneProprietes.getElements()) + this.getZoneMinHeight(
        this.zoneEnTete.getElements());
    graphics2D.drawLine(0, height, this.getWidth(), height);
  }

  @Override
  public void refreshInformations() {
    setZoneEnTeteContent();
    setZoneProprietesContent();
    setZoneOperationsContent();
    repaint();
  }


  @Override
  protected void defineBackgroundColor() {
    this.setBackground(COLOR);
  }

  @Override
  protected void defineSize() {
    this.setSize(this.getMinimumSize());
  }

  private void addListeners() {
    MDTableShapeListener listener = new MDTableShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  @Override
  protected void doDraw(Graphics graphics) {

  }

  public MDRTable getEntity() {
    return (MDRTable) this.getRelatedRepositoryElement();
  }

  @Override
  protected void setZoneEnTeteContent() {
    this.zoneEnTete.getElements().clear();

    MPDRTable mpdrTable = (MPDRTable) this.relatedRepositoryElement;
    mpdrTable.getStereotypesString().forEach(
        e -> this.zoneEnTete.addElement(e)
    );
    this.zoneEnTete.addElement(mpdrTable.getName());
  }

  private void setZoneOperationsContent() {
    this.zoneOperations.getElements().clear();

    MPDRTable mpdrTable = (MPDRTable) this.relatedRepositoryElement;

    String columnsCommaSeparated = mpdrTable.getMPDRPK().getMDRColumnsString();

    zoneOperations.addElement(
        mpdrTable.getMPDRColumnPKProper().getStereotypesInLine() + " " + mpdrTable.getMPDRPK()
            .getName() + "(" + columnsCommaSeparated + ")"
    );

    mpdrTable.getMPDRFKsString().forEach(
        e -> zoneOperations.addElement(e)
    );

    mpdrTable.getMPDRUniquesString().forEach(
        e -> zoneOperations.addElement(e)
    );

    mpdrTable.getMPDRChecksString().forEach(
        e -> zoneOperations.addElement(e)
    );
  }

  @Override
  protected void setZoneProprietesContent() {
    this.zoneProprietes.getElements().clear();

    MPDRTable mpdrTable = (MPDRTable) this.relatedRepositoryElement;

    mpdrTable.getMPDRColumnsSortDefaultString().forEach(
        e -> zoneProprietes.addElement(e)
    );
  }


  @Override
  protected String getLongestProperty() {
    return null;
  }

  @Override
  protected void setNameFont(Graphics2D graphics2D) {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MDTableShape that = (MDTableShape) o;
    return getName().equals(that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  @Override
  public String getXmlTagName() {
    return null;
  }


  @Override
  public String getName() {
    return name;
  }
}