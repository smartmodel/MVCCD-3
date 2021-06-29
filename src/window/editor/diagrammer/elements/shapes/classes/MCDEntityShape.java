package window.editor.diagrammer.elements.shapes.classes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import mcd.MCDEntity;
import preferences.Preferences;
import window.editor.diagrammer.listeners.MCDEntityShapeListener;

public class MCDEntityShape extends ClassShape {

  private MCDEntity entity;

  public MCDEntityShape() {
    super();
    this.addListeners();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  @Override
  public void drag(int differenceX, int differenceY) {
    super.drag(differenceX, differenceY);
  }

  @Override
  public void setZoneEnTeteContent() {
    this.zoneEnTete.getElements().clear();
    this.zoneEnTete.addElement(Preferences.DIAGRAMMER_ENTITY_STEREOTYPE_TEXT);
    if (this.entity != null) {
      this.zoneEnTete.addElement(this.entity.getName());
      if (this.entity.isOrdered()) {
        this.zoneEnTete.addElement(Preferences.DIAGRAMMER_ENTITY_ORDERED_TEXT);
      }
      this.updateSizeAndMinimumSize();
    }
  }

  @Override
  public void setZoneProprietesContent() {
    if (this.entity != null) {
      this.zoneProprietes.setElements(this.entity.getAttributesForMCDDisplay());
      this.updateSizeAndMinimumSize();
    }
  }

  @Override
  protected void setBackgroundColor() {
    this.setBackground(Preferences.DIAGRAMMER_ENTITY_DEFAULT_BACKGROUND_COLOR);
  }

  @Override
  protected String getLongestProperty() {
    if (this.entity != null) {
      String longestProperty = "";
      for (String property : this.entity.getAttributesForMCDDisplay()) {
        if (property.length() > longestProperty.length()) {
          longestProperty = property;
        }
      }
      return longestProperty;
    } else {
      return null;
    }
  }

  @Override
  public void setNameFont(Graphics2D graphics2D) {
    if (this.entity != null) {
      if (this.entity.isEntAbstract()) {
        graphics2D.setFont(Preferences.DIAGRAMMER_ABSTRACT_CLASS_NAME_FONT);
      } else {
        graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_NAME_FONT);
      }
    }
  }

  private void addListeners() {
    MCDEntityShapeListener listener = new MCDEntityShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  public MCDEntity getEntity() {
    return entity;
  }

  public void setEntity(MCDEntity entity) {
    this.entity = entity;
    this.updateSizeAndMinimumSize();
  }
}