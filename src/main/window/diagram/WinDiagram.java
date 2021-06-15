package main.window.diagram;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;

/**
 * La  lasse met en place le redimensionnement en faisant appel aux méthodes de son ancêtre PanelBorderLayout.
 * La classe crée ensuite le contenu et le place dans le panneau redimensionnable.
 */
public class WinDiagram extends JPanel {

    private WinDiagramContent content;

    public WinDiagram(){

        content = new WinDiagramContent();
        add(content);
    }

    public WinDiagramContent getContent() {
        return content;
    }


}
