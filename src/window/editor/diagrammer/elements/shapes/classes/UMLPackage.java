package window.editor.diagrammer.elements.shapes.classes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class UMLPackage extends SquaredShape {

    private Color color = Color.decode("#BFF0F0");

    public UMLPackage() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        final double points3[][] = {
                {0, 0}, {width / 2, 0}, {width / 2, height / 6}, {width, height / 6}, {width, height}, {0, height}, {0, height / 6}, {width / 2, height / 6}, {0, height / 6}
        };

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(color);

        GeneralPath gp = new GeneralPath();
        gp.moveTo(points3[0][0], points3[0][1]);

        for (int k = 1; k < points3.length; k++) {
            gp.lineTo(points3[k][0], points3[k][1]);
        }
        gp.closePath();
        graphics.draw(gp);
        graphics.fillRect(0, 0, width / 2, height / 6);
        graphics.dispose();
    }
}
