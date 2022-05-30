package window.editor.diagrammer.elements.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Objects;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.UMLPackageIntegrableShapes;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.UMLPackageListener;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;

public class UMLPackage extends SquaredShape {

  private Color COLOR = Color.decode("#BFF0F0");

  private List<UMLPackageIntegrableShapes> tapisElements;
  private String name;

  public UMLPackage(String name, List<UMLPackageIntegrableShapes> tapisElements) {
    super();
    this.name = name;
    this.tapisElements = tapisElements;

    initUI();
    this.addListeners();
  }

  public List<UMLPackageIntegrableShapes> getTapisElements() {
    return tapisElements;
  }

  @Override
  protected void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    int heightBigRectangle = height / 6;

    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    graphics.setStroke(new BasicStroke(3));
    graphics.setColor(Color.white);
    graphics.fillRect(0, heightBigRectangle, width, height - heightBigRectangle);
    graphics.setColor(COLOR);
    graphics.drawRect(0, heightBigRectangle, width, height - heightBigRectangle);
    graphics.fillRect(0, 0, width / 2, height / 6);

    graphics.setColor(Color.black);
    graphics.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
    graphics.drawString("<<TAPIs>>", (int) (width / 6.5), (int) (height / 8));
  }


  @Override
  protected void defineBackgroundColor() {

  }

  @Override
  protected void defineMinimumSize() {

  }

  @Override
  protected void defineSize() {

  }

  private void initUI() {
    this.setMinimumSize(new Dimension((int) (Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH * 2.5),
        (int) (Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT * 2.5)));
    this.setSize(this.getMinimumSize());
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void drag(int differenceX, int differenceY) {
    super.drag(differenceX, differenceY);
    for (RelationShape relation : DiagrammerService.getDrawPanel()
        .getRelationShapesBySquaredShape(this)) {
      if (relation.isReflexive()) {
        for (RelationAnchorPointShape pointAncrage : relation.getAnchorPoints()) {
          pointAncrage.setLocationDifference(differenceX, differenceY);
        }
      } else {
        RelationAnchorPointShape nearestPointAncrage = GeometryUtils.getNearestPointAncrage(this,
            relation);
        nearestPointAncrage.setLocationDifference(differenceX, differenceY);
      }
    }
  }

  private void addListeners() {
    UMLPackageListener listener = new UMLPackageListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  @Override
  protected void doDraw(Graphics graphics) {

  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UMLPackage that = (UMLPackage) o;
    return getName().equals(that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }


}
