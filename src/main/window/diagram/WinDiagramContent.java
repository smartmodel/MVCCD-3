package main.window.diagram;

import main.MVCCDManager;
import main.MVCCDWindow;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

/**
 * La classe crée les panneaux du diagrammeur (en-tête, palette et zone de dessin).
 * La classe ajoute ensuite les panneaux comme contenu de son ancêtre PanelContent.
 * L'ancêtre PanelContent crée la barre de défilement si nécessaire (décision à prendre).
 * Remarque: la palette et la zone de dessin du diagrammeur seront créés spécifiquement pour le genre de modèle à
 * réaliser (MCD, MLD-R, MPD-R ou autres).
 */
public class WinDiagramContent extends PanelContent {

    private MVCCDWindow mvccdWindow;
    JPanel content = new JPanel();          //Diagrammeur dans son ensemble
    JPanel panelTitle = new JPanel();       //En-tête du diagrammeur
    JPanel panelPalette = new JPanel();     //Palette du diagrammeur
    JPanel panelDraw = new JPanel();        //Zone de dessin du diagrammeur

    JButton btnAdd;

    public WinDiagramContent(WinDiagram diagram) {
        super(diagram);
        mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        //Création des panneaux du diagrammeur
        BorderLayout bl = new BorderLayout(5,5);
        content.setLayout(bl);
        content.add(panelPalette, BorderLayout.WEST);
        content.add(panelDraw, BorderLayout.CENTER);
        content.add(panelTitle, BorderLayout.NORTH);
        //Place le diagrammeur (JPanel content) dans son ancêtre PanelBorder
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
