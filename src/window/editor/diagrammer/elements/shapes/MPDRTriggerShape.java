package window.editor.diagrammer.elements.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import mdr.MDRTable;
import mpdr.MPDRTable;
import mpdr.tapis.MPDRContTAPIs;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.UMLPackageIntegrableShapes;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;

public class MPDRTriggerShape extends ClassShape implements UMLPackageIntegrableShapes {

  private final Color COLOR = Color.decode("#F09396");
  private UMLPackage parentUMLPackage = null;

  public MPDRTriggerShape(MDRTable mdrTable) {
    super(mdrTable);
  }

  @Override
  protected void defineBackgroundColor() {
    this.setBackground(null);
    this.setOpaque(false);
  }

  @Override
  protected void defineSize() {
    this.setSize(this.getMinimumSize());
  }

  @Override
  public void initUI() {
    this.defineMinimumSize();
    this.defineBackgroundColor();
    this.defineSize();
  }


  @Override
  protected void doDraw(Graphics graphics) {

  }

  @Override
  public void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    graphics.setColor(COLOR);

    graphics.fillRect(0, 0, width - 1, height - 1);
    graphics.setColor(Color.WHITE);
    graphics.fillArc(-17, 0, 30, height - 1, -90, 180);

    graphics.setColor(Color.black);
    this.drawZoneEnTete(graphics);
    this.drawZoneProprietes(graphics);
  }

  @Override
  protected void setZoneEnTeteContent() {
    this.zoneEnTete.getElements().clear();

    MPDRTable mpdrTable = (MPDRTable) this.relatedRepositoryElement;
    String technoBD = mpdrTable.getStereotypesString().get(mpdrTable.getStereotypes().size() - 1);

    this.zoneEnTete.addElement(
        "<<" + Preferences.STEREOTYPE_TRIGGERS_NAME + ">>");
    this.zoneEnTete.addElement(technoBD);
    this.zoneEnTete.addElement(mpdrTable.getNames().getName30());
    this.updateSizeAndMinimumSize();
  }

  @Override
  protected void setZoneProprietesContent() {
    this.zoneProprietes.getElements().clear();

    MPDRTable mpdrTable = (MPDRTable) this.relatedRepositoryElement;

    MPDRContTAPIs mpdrContTAPIs = mpdrTable.getMPDRContTAPIs();

    mpdrContTAPIs.getMPDRBoxTriggers().getAllTriggers().forEach(e ->
        this.zoneProprietes.addElement(e.getNames().getName30())
    );
    this.updateSizeAndMinimumSize();
  }

  @Override
  protected void drawZoneEnTete(Graphics2D graphics2D) {
    this.setZoneEnTeteContent();
    int y = Preferences.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();
    for (int i = 0; i < this.zoneEnTete.getElements().size(); i++) {
      if (i == 1) {
        this.setNameFont(graphics2D);
      } else {
        graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
      }
      int x = this.getCenterTextPositionX(this.zoneEnTete.getElements().get(i), graphics2D);
      graphics2D.drawString(this.zoneEnTete.getElements().get(i), x + 5, y);
      y += graphics2D.getFontMetrics().getHeight();
    }

    // Dessine une ligne séparatrice entre l'entête et les propriétés
    graphics2D.drawLine((int) (this.getWidth() * 0.07),
        this.getZoneMinHeight(this.zoneEnTete.getElements()),
        this.getWidth(),
        this.getZoneMinHeight(this.zoneEnTete.getElements()));
  }

  @Override
  protected void drawZoneProprietes(Graphics2D graphics2D) {
    this.setZoneProprietesContent();
    int y = this.getZoneMinHeight(this.zoneEnTete.getElements())
        + Preferences.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();

    for (int i = 0; i < this.zoneProprietes.getElements().size(); i++) {
      if (i == 1) {
        this.setNameFont(graphics2D);
      } else {
        graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
      }
      int x = this.getCenterTextPositionX(this.zoneProprietes.getElements().get(i), graphics2D);
      graphics2D.drawString(this.zoneProprietes.getElements().get(i), x, y);
      y += graphics2D.getFontMetrics().getHeight();
    }
  }


  @Override
  protected String getLongestProperty() {
    return null;
  }

  @Override
  protected void setNameFont(Graphics2D graphics2D) {

  }

  @Override
  public void setParentUMLPackage(UMLPackage parentUMLPackage) {
    this.parentUMLPackage = parentUMLPackage;
  }

  @Override
  public UMLPackage getParentUMLPackage() {
    return parentUMLPackage;
  }

  @Override
  public String getXmlTagName() {
    return null;
  }

}
