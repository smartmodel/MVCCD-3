package window.editor.diagrammer.elements.shapes.classes;

import javax.swing.*;
import java.awt.*;

public class MPDRProcedureContainerShape extends SquaredShape {

    private Color backgroundColor = Color.decode("#F0E29F");

    public MPDRProcedureContainerShape() {
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

        graphics.fillRect(0, (int) (width * 0.10), width, height);
        graphics.fillRoundRect(0, 0, width, height, 40, 40);

        graphics.setColor(getForeground());
    }
}
