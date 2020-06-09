package main.window.haut;

import utilities.window.PanelContent;

import javax.swing.*;

public class HautContent  extends PanelContent {

    private Haut haut ;
    public HautContent(Haut haut) {
        super(haut);

        JPanel content = new JPanel();
        JLabel hautLabel = new JLabel("Haut");
        content.add(hautLabel);

        super.addContent(content);
    }
}
