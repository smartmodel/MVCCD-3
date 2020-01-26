package main.window.haut;

import utilities.window.PanelContent;

import javax.swing.*;

public class HautContent  extends PanelContent {

    private Haut haut ;
    public HautContent(Haut haut) {
        super(haut);

        JPanel content = new JPanel();
        JButton btnAdd = new JButton("Haut");
        content.add(btnAdd);

        super.addContent(content);
    }
}
