package window.editor.diagrammer.elements.shapes.classes;

import mcd.MCDEntity;
import preferences.Preferences;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MDTableShape extends ClassShape {

    private Color backgroundColor = Color.decode("#A0F0CF");
    private String name;

    public MDTableShape(String name) {
        super();
        this.name = name;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(backgroundColor);

        graphics.fillRoundRect(0, 0, width, height, 20, 20);

        graphics.setColor(Color.black);

        graphics.setColor(getForeground());
        this.drawZoneEnTete(graphics);
        this.drawZoneProprietes(graphics);
    }

    protected void drawZoneEnTete(Graphics2D graphics2D) {
        setZoneEnTeteContent();
        int y = Preferences.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();
        for (int i = 0; i < this.zoneEnTete.getElements().size(); i++) {
            if (i == 1) {
                this.setNameFont(graphics2D);
            } else {
                graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
            }
            int x = this.getCenterTextPositionX(this.zoneEnTete.getElements().get(i), graphics2D);
            graphics2D.drawString(this.zoneEnTete.getElements().get(i), x, y);
            y += graphics2D.getFontMetrics().getHeight();
        }
        this.drawZoneEnTeteBorder(graphics2D);
    }

    private int getCenterTextPositionX(String element, Graphics2D graphics2D) {
        return this.getWidth() / 2 - graphics2D.getFontMetrics().stringWidth(element) / 2;
    }

    private void drawZoneEnTeteBorder(Graphics2D graphics2D) {
        final int height = this.getZoneMinHeight(this.zoneEnTete.getElements());
        graphics2D.drawRoundRect(0, 0, this.getWidth() - 1, height, 20, 20);
    }

    private void drawElements(Graphics2D graphics2D, List<String> elements, int y) {
        graphics2D.setFont(Preferences.DIAGRAMMER_CLASS_FONT);
        final int x = Preferences.DIAGRAMMER_CLASS_PADDING;
        for (String element : elements) {
            graphics2D.drawString(element, x, y);
            y += graphics2D.getFontMetrics().getHeight();
        }
    }

    private void drawZoneProprietesBorder(Graphics2D graphics2D) {
        int height;
        if (this.zoneOperations.getElements().isEmpty() && this.zoneServices.getElements().isEmpty()) {
            height = this.getHeight() - this.getZoneMinHeight(this.zoneEnTete.getElements());
        } else {
            height = Preferences.DIAGRAMMER_CLASS_PADDING * 2 + this.zoneProprietes.getElements().size() * graphics2D.getFontMetrics().getHeight();
        }
        graphics2D.drawRect(0, 0, this.getWidth() - 1, height - 1);
    }

    protected void drawZoneProprietes(Graphics2D graphics2D) {
        int y = this.getZoneMinHeight(this.zoneEnTete.getElements()) + Preferences.DIAGRAMMER_CLASS_PADDING + graphics2D.getFontMetrics().getHeight();
        this.drawElements(graphics2D, this.zoneProprietes.getElements(), y);
        this.drawZoneProprietesBorder(graphics2D);
    }

    private int getZoneMinHeight(List<String> elements) {
        final FontMetrics fontMetrics = this.getFontMetrics(Preferences.DIAGRAMMER_CLASS_FONT);
        int minHeight = Preferences.DIAGRAMMER_CLASS_PADDING * 2;
        minHeight += fontMetrics.getHeight() * elements.size();
        return minHeight;
    }

    @Override
    protected void setZoneEnTeteContent() {
        this.zoneEnTete.getElements().clear();
        this.zoneEnTete.addElement(Preferences.DIAGRAMMER_TABLE_STEREOTYPE_TEXT);
        this.zoneEnTete.addElement(name);
    }


    public MCDEntity getEntity() {
        return (MCDEntity) this.getRelatedRepositoryElement();
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