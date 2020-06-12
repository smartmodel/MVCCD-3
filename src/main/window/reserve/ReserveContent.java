package main.window.reserve;

import utilities.window.PanelContent;

import javax.swing.*;

public class ReserveContent extends PanelContent {

    public ReserveContent(Reserve reserve) {
        super(reserve);

        JPanel content = new JPanel();

        content.add (new JLabel ("Réserve"));

        super.addContent(content);

    }
}
