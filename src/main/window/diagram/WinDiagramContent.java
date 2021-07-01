package main.window.diagram;

import main.MVCCDManager;
import main.MVCCDWindow;
import utilities.window.PanelContent;
import window.editor.diagrammer.drawpanel.DrawPanel;
import window.editor.diagrammer.drawpanel.DrawPanelComponent;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.*;

/**
 * La classe crée les panneaux du diagrammeur (en-tête, palette et zone de dessin).
 * La classe ajoute ensuite les panneaux comme contenu de son ancêtre PanelContent.
 * L'ancêtre PanelContent crée la barre de défilement si nécessaire (décision à prendre).
 * Remarque: la palette et la zone de dessin du diagrammeur seront créés spécifiquement pour le genre de modèle à
 * réaliser (MCD, MLD-R, MPD-R ou autres).
 */
public class WinDiagramContent extends JPanel {

    private MVCCDWindow mvccdWindow;
    JPanel content = new JPanel();          //Diagrammeur dans son ensemble
    JPanel panelTitle = new JPanel();       //En-tête du diagrammeur
    PalettePanel panelPalette = new PalettePanel();     //Palette du diagrammeur
    DrawPanelComponent panelDraw;        //Zone de dessin du diagrammeur

    public WinDiagramContent() {
        this.setBackground(Color.ORANGE);
        mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        DrawPanel drawPanel = new DrawPanel();
        DiagrammerService s = new DiagrammerService(drawPanel);
        panelDraw = new DrawPanelComponent(drawPanel);
        //Création des panneaux du diagrammeur
       BorderLayout bl = new BorderLayout();
        content.setLayout(bl);
        content.add(panelPalette, BorderLayout.WEST);
        content.add(panelDraw, BorderLayout.CENTER);
        //content.add(panelTitle, BorderLayout.NORTH);
        //Place le diagrammeur (JPanel content) dans son ancêtre PanelBorder
        add(content);
    }

    public JPanel getPanelTitle() {
        return panelTitle;
    }

    public JPanel getPanelPalette() {
        return panelPalette;
    }

    public DrawPanelComponent getPanelDraw() {
        return panelDraw;
    }
}
