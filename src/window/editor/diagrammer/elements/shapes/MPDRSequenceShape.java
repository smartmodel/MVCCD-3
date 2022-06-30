package window.editor.diagrammer.elements.shapes;

import static preferences.Preferences.DIAGRAMMER_MPDRSEQUENCE_DEFAULT_BACKGROUND_COLOR;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import mdr.MDRTable;
import mpdr.MPDRSequence;
import mpdr.MPDRTable;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.UMLPackageIntegrableShapes;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.utils.UIUtils;

public class MPDRSequenceShape extends ClassShape implements UMLPackageIntegrableShapes {

  private UMLPackage parentUMLPackage = null;

  public MPDRSequenceShape(MDRTable mdrTable) {
    super(mdrTable);
  }

  @Override
  protected void defineBackgroundColor() {
    this.setBackground(DIAGRAMMER_MPDRSEQUENCE_DEFAULT_BACKGROUND_COLOR);
    this.setOpaque(false);
  }

  @Override
  protected void setZoneEnTeteContent() {
    this.zoneEnTete.getElements().clear();

    MPDRTable mpdrTable = (MPDRTable) this.relatedRepositoryElement;
    String technoBD = mpdrTable.getStereotypesString().get(mpdrTable.getStereotypes().size() - 1);
    MPDRSequence mpdrSequence = mpdrTable.getMPDRColumnPKProper().getMPDRSequence();

    this.zoneEnTete.addElement(
        "<<" + Preferences.STEREOTYPE_SEQUENCE_NAME + ">>");
    this.zoneEnTete.addElement(technoBD);
    this.zoneEnTete.addElement(mpdrSequence.getNames().getName30());
    this.updateSizeAndMinimumSize();
  }

  @Override
  protected void setZoneProprietesContent() {
    this.zoneProprietes.getElements().clear();

    // Valeur de Cache par défaut
    this.zoneProprietes.addElement("Cache = 20");
    this.updateSizeAndMinimumSize();
  }


  @Override
  protected String getLongestProperty() {
    return null;
  }


  @Override
  protected void setNameFont(Graphics2D graphics2D) {

  }

  @Override
  public String getXmlTagName() {
    return null;
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
    final double points[][] = {
        {0, 0}, {0, height}, {width * 0.85, height}, {width, height * 0.85}, {width, height * 0.15},
        {width * 0.85, 0}
    };

    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    graphics.setColor(super.getBackground());

    GeneralPath gp = new GeneralPath();
    gp.moveTo(points[0][0], points[0][1]);

    for (int k = 1; k < points.length; k++) {
      gp.lineTo(points[k][0], points[k][1]);
    }

    // Dessin de la forme et remplissage de couleur
    gp.closePath();
    graphics.fill(gp);

    graphics.setColor(Color.black);
    this.drawZoneEnTete(graphics);
    this.drawZoneProprietes(graphics);
  }

  @Override
  protected void drawZoneEnTete(Graphics2D graphics2D) {
    int y = (int) UIUtils.getClassPadding() + graphics2D.getFontMetrics().getAscent();
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

    // Dessine une ligne séparatrice entre l'entête et les propriétés
    graphics2D.drawLine(0, this.getZoneMinHeight(this.zoneEnTete.getElements()), this.getWidth(),
        this.getZoneMinHeight(this.zoneEnTete.getElements()));
  }

  @Override
  protected void drawZoneProprietes(Graphics2D graphics2D) {
    this.setZoneProprietesContent();
    int y =
        this.getZoneMinHeight(this.zoneEnTete.getElements()) + getYSize(graphics2D);

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

  private int getYSize(Graphics2D graphics2D) {
    return (int) (UIUtils.getClassPadding() + graphics2D.getFontMetrics().getHeight());
  }

  @Override
  public void setParentUMLPackage(UMLPackage parentUMLPackage) {
    this.parentUMLPackage = parentUMLPackage;
  }

  @Override
  public UMLPackage getParentUMLPackage() {
    return parentUMLPackage;
  }
}