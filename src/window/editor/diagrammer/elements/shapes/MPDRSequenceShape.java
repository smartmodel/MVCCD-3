package window.editor.diagrammer.elements.shapes;

import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.UMLPackageIntegrableShapes;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class MPDRSequenceShape extends SquaredShape implements UMLPackageIntegrableShapes {

    private Color COLOR = Color.decode("#B4C0ED");

    public MPDRSequenceShape() {
        super();
    }

    @Override
    public void initUI() {
        this.setMinimumSize(new Dimension((int) (Preferences.DIAGRAMMER_DEFAULT_CLASS_WIDTH / 1.5), Preferences.DIAGRAMMER_DEFAULT_CLASS_HEIGHT));
        this.setSize(this.getMinimumSize());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        final double points[][] = {
                {0, 0}, {0, height}, {height, width * 0.85}, {width, height * 0.85}, {width, height * 0.15}, {width * 0.85, 0}
        };

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(COLOR);

        GeneralPath gp = new GeneralPath();
        gp.moveTo(points[0][0], points[0][1]);

        for (int k = 1; k < points.length; k++) {
            gp.lineTo(points[k][0], points[k][1]);
        }

        gp.closePath();
        graphics.fill(gp);
        this.setBackgroundColor();
    }

    private void setBackgroundColor() {
        this.setBackground(new Color(255, 255, 255));
    }

}