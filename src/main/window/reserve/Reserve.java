package main.window.reserve;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;
import java.awt.*;

public class Reserve extends JPanel {

    private ReserveContent content;

    public Reserve() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);
        content = new ReserveContent();
        add(content);
    }

    public ReserveContent getContent() {
        return content;
    }
}
