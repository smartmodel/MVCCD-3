package main.window.reserve;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;

public class Reserve extends JPanel {

    private ReserveContent content;

    public Reserve() {

        content = new ReserveContent();
        add(content);
    }

    public ReserveContent getContent() {
        return content;
    }
}
