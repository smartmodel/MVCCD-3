package diagram;

import javax.swing.*;

/**
 * Pour l'instant, cette classe ne fait que d'effacer le contenu d'un panneau passé en paramètre.
 */
public class CloseTitlePanel {

    private JPanel panelTitle;

    public CloseTitlePanel(JPanel panelTitle) {
        this.panelTitle = panelTitle;
    }

    public void getContent() {
        panelTitle.removeAll();
    }
}
