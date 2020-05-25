package diagram;

import javax.swing.*;

public class CloseTitlePanel {

    private JPanel panelTitle;

    public CloseTitlePanel(JPanel panelTitle) {
        this.panelTitle = panelTitle;
    }

    public void getContent() {
        panelTitle.removeAll();
    }
}
