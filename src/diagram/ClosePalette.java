package diagram;

import javax.swing.*;

public class ClosePalette {

    private JPanel panelPalette;

    public ClosePalette(JPanel panelPalette) {
        this.panelPalette = panelPalette;
    }

    public void getContent() {
        panelPalette.removeAll();
    }
}




