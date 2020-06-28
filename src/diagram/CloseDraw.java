package diagram;

import javax.swing.*;

public class CloseDraw {
    private JPanel panelZoneDessin;

    public CloseDraw (JPanel panelPalette) {
        this.panelZoneDessin = panelPalette;
    }

    public void getContent() {
        panelZoneDessin.removeAll();
    }
}
