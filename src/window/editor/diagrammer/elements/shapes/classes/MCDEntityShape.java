package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import mcd.MCDEntity;
import window.editor.diagrammer.listeners.MCDEntityShapeListener;
import window.editor.diagrammer.utils.DiagrammerConstants;

public class MCDEntityShape extends ClassShape {

  MCDEntity entity;

  public MCDEntityShape() {
    super();
    this.addListeners();
    this.setMinimumSize(new Dimension(DiagrammerConstants.DIAGRAMMER_DEFAULT_ENTITY_WIDTH, DiagrammerConstants.DIAGRAMMER_DEFAULT_ENTITY_HEIGHT));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.setZoneEnTeteContent();
    this.setZoneProprietesContent();
  }

  @Override
  protected void setBackgroundColor() {
    this.setBackground(DiagrammerConstants.DIAGRAMMER_ENTITY_DEFAULT_BACKGROUND_COLOR);
  }

  @Override
  public void setNameFont(Graphics2D graphics2D) {
    if (this.entity != null){
      if (this.entity.isEntAbstract()){
        graphics2D.setFont(DiagrammerConstants.DIAGRAMMER_ABSTRACT_CLASS_NAME_FONT);
      } else{
        graphics2D.setFont(DiagrammerConstants.DIAGRAMMER_CLASS_NAME_FONT);
      }
    }
  }

  @Override
  protected void setZoneEnTeteContent() {
    this.zoneEnTete.getElements().clear();
    this.zoneEnTete.addElement(DiagrammerConstants.DIAGRAMMER_ENTITY_STEREOTYPE_TEXT);
    if (this.entity != null){
      this.zoneEnTete.addElement(this.entity.getName());
      if (this.entity.isOrdered()){
        this.zoneEnTete.addElement(DiagrammerConstants.DIAGRAMMER_ENTITY_ORDERED_TEXT);
      }
    }
  }

  @Override
  protected void setZoneProprietesContent() {
    if (this.entity != null){
      this.zoneProprietes.setElements(this.entity.getAttributesForMCDDisplay());
    }
  }

  private void addListeners() {
    MCDEntityShapeListener listener = new MCDEntityShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  public void setEntity(MCDEntity entity) {
    this.entity = entity;
    this.repaint();
  }

  public MCDEntity getEntity() {
    return entity;
  }
}