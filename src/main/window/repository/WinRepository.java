package main.window.repository;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

/**
 * La classe met en place le redimensionnement en faisant appel aux méthodes de son ancêtre PanelBorderLayout.
 * La classe crée ensuite le contenu et le place dans le panneau redimensionnable.
 */
public class WinRepository extends PanelBorderLayout {

    private WinRepositoryContent content;

    public WinRepository(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer){
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        content = new WinRepositoryContent(this);

        // Code ajouté par Antoine
        content.setBackground(Color.WHITE);

        super.setPanelContent(content);
    }

}
