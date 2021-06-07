package main.window.diagram;

import main.MVCCDManager;
import main.MVCCDWindow;
import utilities.window.PanelContent;
import window.editor.diagrammer.DrawPanel;
import window.editor.diagrammer.DrawPanelComponent;

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
    DrawPanelComponent panelDraw = new DrawPanelComponent(new DrawPanel());        //Zone de dessin du diagrammeur

    JButton btnAdd;

    public WinDiagramContent(WinDiagram diagram) {
        super(diagram);
        mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        //Création des panneaux du diagrammeur
        BorderLayout bl = new BorderLayout();
        content.setLayout(bl);
        content.add(panelPalette, BorderLayout.WEST);
        content.add(panelDraw, BorderLayout.CENTER);
        content.add(panelTitle, BorderLayout.NORTH);
        //Place le diagrammeur (JPanel content) dans son ancêtre PanelBorder
        super.addContent(content);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println(panelDraw.getSize());
    }

    public JPanel getPanelTitle() {
        return panelTitle;
    }

    public JPanel getPanelPalette() {
        return panelPalette;
    }

    public Dimension  resizeContent(){
         return super.resizeContent();
    }

    public DrawPanelComponent getPanelDraw() {
        return panelDraw;
    }
}
