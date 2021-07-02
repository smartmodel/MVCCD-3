package main.window.diagram;

import main.MVCCDManager;
import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;
import window.editor.diagrammer.drawpanel.DrawPanel;
import window.editor.diagrammer.drawpanel.DrawPanelComponent;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.*;

/**
 * La  lasse met en place le redimensionnement en faisant appel aux méthodes de son ancêtre PanelBorderLayout.
 * La classe crée ensuite le contenu et le place dans le panneau redimensionnable.
 */
public class WinDiagram extends JPanel {

    JPanel panelTitle = new JPanel();       //En-tête du diagrammeur
    PalettePanel panelPalette = new PalettePanel();     //Palette du diagrammeur
    DrawPanelComponent panelDraw;
    JPanel getPanelTitle = new JPanel();
    JPanel content = new JPanel();

    public WinDiagram(){
        this.setBackground(Color.ORANGE);
/*        content = new WinDiagramContent();*/
        this.setLayout(new BorderLayout());
/*        add(content, BorderLayout.CENTER);*/

        panelDraw = new DrawPanelComponent(DiagrammerService.getDrawPanel());
        //Création des panneaux du diagrammeur
        BorderLayout bl = new BorderLayout();

        //content.add(panelTitle, BorderLayout.NORTH);
        //Place le diagrammeur (JPanel content) dans son ancêtre PanelBorder
        add(DiagrammerService.getDrawPanel(), BorderLayout.CENTER);
        add(panelPalette, BorderLayout.WEST);
/*        content.add(drawPanel);
        content.add(panelPalette);
        content.add(panelTitle);*/
    }

    public JPanel getContent() {
        return content;
    }

    public JPanel getPanelTitle() {
        return panelTitle;
    }
}
