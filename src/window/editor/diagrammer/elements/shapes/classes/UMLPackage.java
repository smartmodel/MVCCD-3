package window.editor.diagrammer.elements.shapes.classes;

import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.listeners.MDTableShapeListener;
import window.editor.diagrammer.listeners.UMLPackageListener;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.Objects;

public class UMLPackage extends SquaredShape {

    private Color color = Color.decode("#BFF0F0");

    private String name;

    public UMLPackage(String name) {
        super();
        this.name = name;
        initUI();
        this.addListeners();
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
        graphics.setColor(color);
        graphics.drawRect(0, heightBigRectangle, width, height - heightBigRectangle);
        graphics.fillRect(0, 0, width / 2, height / 6);


        graphics.setColor(Color.black);
        graphics.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
        graphics.drawString("<<TAPIs>>", (int) (width / 6.5), (int) (height / 8));
    }


    private void initUI() {
        this.setMinimumSize(new Dimension(Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH * 2, Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT * 2));
        this.setSize(this.getMinimumSize());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void drag(int differenceX, int differenceY) {
        super.drag(differenceX, differenceY);
        for (RelationShape relation : DiagrammerService.getDrawPanel().getRelationShapesByClassShape(this)) {
            if (relation.isReflexive()) {
                for (RelationPointAncrageShape pointAncrage : relation.getPointsAncrage()) {
                    pointAncrage.setLocationDifference(differenceX, differenceY);
                }
            } else {
                RelationPointAncrageShape nearestPointAncrage = GeometryUtils.getNearestPointAncrage(this, relation);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UMLPackage that = (UMLPackage) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }


}
