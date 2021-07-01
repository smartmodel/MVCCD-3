package main.window.diagram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import utilities.window.PanelContent;
import window.editor.diagrammer.drawpanel.DrawPanelComponent;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

/**
 * La classe crée les panneaux du diagrammeur (en-tête, palette et zone de dessin). La classe ajoute ensuite les panneaux comme contenu de son ancêtre PanelContent. L'ancêtre PanelContent crée la barre de défilement si nécessaire (décision à prendre). Remarque: la palette et la zone de dessin du diagrammeur seront créés spécifiquement pour le genre de modèle à réaliser (MCD, MLD-R, MPD-R ou
 * autres).
 */
public class WinDiagramContent extends PanelContent {

  JPanel content = new JPanel(); //Diagrammeur dans son ensemble
  JPanel panelTitle = new JPanel(); //En-tête du diagrammeur
  PalettePanel panelPalette = new PalettePanel(); //Palette du diagrammeur
  DrawPanelComponent panelDraw; //Zone de dessin du diagrammeur

  public WinDiagramContent(WinDiagram diagram) {
    super(diagram);

    this.panelDraw = new DrawPanelComponent(DiagrammerService.getDrawPanel());

    //Création des panneaux du diagrammeur
    BorderLayout layout = new BorderLayout();
    this.content.setLayout(layout);
    this.content.add(this.panelPalette, BorderLayout.WEST);
    this.content.add(this.panelDraw, BorderLayout.CENTER);
    this.content.add(this.panelTitle, BorderLayout.NORTH);

    //Place le diagrammeur (JPanel content) dans son ancêtre PanelBorder
    super.addContent(this.content);

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    System.out.println(this.panelDraw.getSize());
  }

  public JPanel getPanelTitle() {
    return this.panelTitle;
  }

  @Override
  public Dimension resizeContent() {
    return super.resizeContent();
  }

}
