package window.editor.diagrammer.elements.shapes.classes;

import mcd.MCDEntity;
import preferences.Preferences;
import window.editor.diagrammer.listeners.MCDEntityShapeListener;

import java.awt.*;

public class MCDEntityShape extends ClassShape {

  public MCDEntityShape(MCDEntity relatedRepositoryEntity) {
    super(relatedRepositoryEntity);
    this.addListeners();
  }

  public MCDEntityShape(int id, MCDEntity relatedRepositoryEntity) {
    super(relatedRepositoryEntity);
    this.id = id;
    this.addListeners();
  }

  public MCDEntityShape(int id) {
    super(id);
    this.addListeners();
  }

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
    if (this.getEntity() != null) {
      this.zoneEnTete.addElement(this.getEntity().getName());
      if (this.getEntity().isOrdered()) {
        this.zoneEnTete.addElement(Preferences.DIAGRAMMER_ENTITY_ORDERED_TEXT);
      }
      this.updateSizeAndMinimumSize();
    }
  }

  @Override
  public void setZoneProprietesContent() {
    if (this.getEntity() != null) {
      this.zoneProprietes.setElements(this.getEntity().getAttributesForMCDDisplay());
      this.updateSizeAndMinimumSize();
    }
  }

  @Override
  protected void setBackgroundColor() {
    this.setBackground(new Color(125, 200, 243));
  }

  @Override
  protected String getLongestProperty() {
    if (this.getEntity() != null) {
      String longestProperty = "";
      for (String property : this.getEntity().getAttributesForMCDDisplay()) {
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
    if (this.getEntity() != null) {
      if (this.getEntity().isEntAbstract()) {
        graphics2D.setFont(Preferences.DIAGRAMMER_ABSTRACT_CLASS_NAME_FONT);
      } else {
        graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_NAME_FONT);
      }
    }
  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_ENTITY_XML_TAG;
  }

  private void addListeners() {
    MCDEntityShapeListener listener = new MCDEntityShapeListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  public MCDEntity getEntity() {
    return (MCDEntity) this.getRelatedRepositoryElement();
  }

  public void setEntity(MCDEntity entity) {
    this.relatedRepositoryElement = entity;
    this.updateSizeAndMinimumSize();
  }
}