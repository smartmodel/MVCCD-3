package main.window.diagram;

import main.MVCCDManager;
import main.MVCCDWindow;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class WinDiagramContent extends PanelContent {

    private MVCCDWindow mvccdWindow;
    JPanel content = new JPanel();
    JPanel panelTitle = new JPanel();
    JPanel panelPalette = new JPanel();
    JPanel panelDraw = new JPanel();

    JButton btnAdd;

    public WinDiagramContent(WinDiagram diagram) {
        super(diagram);
        mvccdWindow = MVCCDManager.instance().getMvccdWindow();

        BorderLayout bl = new BorderLayout(5,5);
        content.setLayout(bl);
        content.add(panelPalette, BorderLayout.WEST);
        content.add(panelDraw, BorderLayout.CENTER);
        content.add(panelTitle, BorderLayout.NORTH);

        super.addContent(content);


    }

    public JPanel getPanelTitle() {
        return panelTitle;
    }

    public JPanel getPanelPalette() {
        return panelPalette;
    }

    public JPanel getPanelDraw() {
        return panelDraw;
    }

    public Dimension  resizeContent(){
         return super.resizeContent();
    }
}
