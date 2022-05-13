package window.editor.diagrammer.elements.shapes.classes;

import preferences.Preferences;

import javax.swing.*;
import java.awt.*;

public class MDTableShape extends ClassShape {

    private Color backgroundColor = Color.decode("#A0F0CF");

    public MDTableShape() {
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

        graphics.fillRoundRect(0, 0, width, height,20,20);

        graphics.setColor(getForeground());
        graphics.dispose();
    }


    @Override
    protected void setZoneEnTeteContent() {
    }

    @Override
    protected void setZoneProprietesContent() {

    }

    @Override
    protected void setBackgroundColor() {

    }

    @Override
    protected String getLongestProperty() {
        return null;
    }

    @Override
    protected void setNameFont(Graphics2D graphics2D) {

    }

    @Override
    public String getXmlTagName() {
        return null;
    }
}