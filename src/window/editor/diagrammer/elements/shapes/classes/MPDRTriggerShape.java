package window.editor.diagrammer.elements.shapes.classes;

import javax.swing.*;
import java.awt.*;

public class MPDRTriggerShape extends SquaredShape {

    private Color backgroundColor = Color.decode("#F09396");

    public MPDRTriggerShape() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(backgroundColor);

        graphics.fillRect(0, 0, width - 1, height - 1);
        graphics.setColor(Color.WHITE);
        graphics.fillArc(-17, 0, 30, height - 1, -90, 180);

        graphics.setColor(getForeground());
    }
}
