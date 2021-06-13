package window.editor.diagrammer.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import window.editor.diagrammer.utils.DiagrammerConstants;

public abstract class ClassShape extends SquaredShape {

  protected ClassShapeZone zoneEnTete = new ClassShapeZone();
  protected ClassShapeZone zoneProprietes = new ClassShapeZone();
  protected ClassShapeZone zoneOperations = new ClassShapeZone();
  protected ClassShapeZone zoneServices = new ClassShapeZone();

  public ClassShape() {
    super();
    this.setBounds(DiagrammerConstants.DIAGRAMMER_DEFAULT_ENTITY_POSITION_X,
        DiagrammerConstants.DIAGRAMMER_DEFAULT_ENTITY_POSITION_Y,
        DiagrammerConstants.DIAGRAMMER_DEFAULT_ENTITY_WIDTH,
        DiagrammerConstants.DIAGRAMMER_DEFAULT_ENTITY_HEIGHT);
    this.zoneEnTete.setBounds(0, 0, this.getWidth(), 30);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    this.setBackgroundColor();
    this.drawZoneEnTete(graphics2D);
    this.drawZoneProprietes(graphics2D);
    this.updateMinimumSize(graphics2D);

  }

  // Draw zones
  protected void drawZoneEnTete(Graphics2D graphics2D){
    int y = DiagrammerConstants.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();
    for (int i = 0; i < this.zoneEnTete.getElements().size(); i++) {
      if (i == 1){
        graphics2D.setFont(DiagrammerConstants.DIAGRAMMER_CLASS_NAME_FONT);
      } else{
        graphics2D.setFont(DiagrammerConstants.DIAGRAMMER_CLASS_FONT);
      }
      int x = this.getCenterTextPositionX(this.zoneEnTete.getElements().get(i), graphics2D);
      graphics2D.drawString(this.zoneEnTete.getElements().get(i), x, y);
      y += graphics2D.getFontMetrics().getHeight();
    }

    this.drawZoneEnTeteBorder(graphics2D);
  }
  protected void drawZoneProprietes(Graphics2D graphics2D){
    int y = this.getZoneMinHeight(graphics2D, this.zoneEnTete.getElements()) + DiagrammerConstants.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();
    this.drawElements(graphics2D, this.zoneProprietes.getElements(), y);
    this.drawZoneProprietesBorder(graphics2D);
  }

  // Size and position
  private int getCenterTextPositionX(String element, Graphics2D graphics2D){
    return this.getWidth() / 2 - graphics2D.getFontMetrics().stringWidth(element) / 2;
  }
  private int getZoneMinHeight(Graphics2D graphics2D, ArrayList<String> elements){
    int minHeight = DiagrammerConstants.DIAGRAMMER_CLASS_PADDING * 2;
    minHeight += graphics2D.getFontMetrics().getHeight() * elements.size();
    return minHeight;
  }

  // Draw border
  private void drawZoneEnTeteBorder(Graphics2D graphics2D){
    int height = this.getZoneMinHeight(graphics2D, this.zoneEnTete.getElements());

    graphics2D.drawRect(0, 0, this.getWidth() - 1, height);

  }
  private void drawZoneProprietesBorder(Graphics2D graphics2D){
    int height;
    if (this.zoneOperations.getElements().isEmpty() && this.zoneServices.getElements().isEmpty()){
       height = this.getHeight() - this.getZoneMinHeight(graphics2D, this.zoneEnTete.getElements());
    } else{
       height = DiagrammerConstants.DIAGRAMMER_CLASS_PADDING * 2 + this.zoneProprietes.getElements().size() * graphics2D.getFontMetrics().getHeight();
    }
    graphics2D.drawRect(0, this.getZoneMinHeight(graphics2D, this.zoneEnTete.getElements()), this.getWidth() - 1, height - 1);
  }

  // Set content
  protected abstract void setZoneEnTeteContent();
  protected abstract void setZoneProprietesContent();
  protected abstract void setBackgroundColor();

  private void drawElements(Graphics2D graphics2D, ArrayList<String> elements, int y){
    int x = DiagrammerConstants.DIAGRAMMER_CLASS_PADDING;
    for (String element : elements){
      graphics2D.drawString(element, x, y);
      y += graphics2D.getFontMetrics().getHeight();
    }
  }

  private void updateMinimumSize(Graphics2D graphics2D){
    int height = this.getZoneMinHeight(graphics2D, this.zoneEnTete.getElements()) + this.getZoneMinHeight(graphics2D, this.zoneProprietes.getElements());
    this.setMinimumSize(new Dimension(this.getWidth(), height));
  }
}
