/***
 * Cette classe peut être utilisée en l'état actuel. Elle représente l'objet graphique d'une entité MCD
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.elements.shapes.classes.mcd;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import mcd.MCDEntity;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.listeners.MCDEntityShapeListener;
import window.editor.diagrammer.utils.UIUtils;

public class MCDEntityShape extends ClassShape implements Serializable {

  private static final long serialVersionUID = -2773299390415893067L;

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
        graphics2D.setFont(UIUtils.getAbstracClassFont());
      } else {
        graphics2D.setFont(UIUtils.getClassNameFont());
      }
    }
  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_ENTITY_XML_TAG;
  }

  @Override
  protected void defineBackgroundColor() {
    this.setBackground(Preferences.DIAGRAMMER_ENTITY_DEFAULT_BACKGROUND_COLOR);
  }

  @Override
  protected void defineSize() {
    this.setSize(UIUtils.getClassShapeDefaultSize());
  }

  @Override
  protected void doDraw(Graphics graphics) {

  }

  private void addListeners() {
    MCDEntityShapeListener listener = new MCDEntityShapeListener(this);
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