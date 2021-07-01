package main.window.reserve;

import utilities.window.PanelContent;

import javax.swing.*;

public class ReserveContent extends JPanel {

    public ReserveContent() {

        JPanel content = new JPanel();

        content.add (new JLabel ("Réserve"));

        add(content);
    }
}
