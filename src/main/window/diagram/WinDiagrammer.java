package main.window.diagram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import main.MVCCDManager;
import utilities.window.PanelContent;
import window.editor.diagrammer.drawpanel.DrawPanelComponent;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

/**
 * La classe crée les panneaux du diagrammeur (en-tête, palette et zone de dessin). La classe ajoute
 * ensuite les panneaux comme contenu de son ancêtre PanelContent. L'ancêtre PanelContent crée la
 * barre de défilement si nécessaire (décision à prendre). Remarque: la palette et la zone de dessin
 * du diagrammeur seront créés spécifiquement pour le genre de modèle à réaliser (MCD, MLD-R, MPD-R
 * ou autres).
 */
public class WinDiagrammer extends PanelContent {

  JPanel content = new JPanel(); //Diagrammeur dans son ensemble
  JPanel panelTitle = new JPanel(); //En-tête du diagrammeur
  PalettePanel panelPalette = PalettePanel.instance(); //Palette du diagrammeur
  DrawPanelComponent panelDraw; //Zone de dessin du diagrammeur

  public WinDiagrammer(WinDiagram diagram) {
    super(diagram);

    this.setVisible(true);

    this.panelDraw = new DrawPanelComponent(DiagrammerService.getDrawPanel());
    layoutDiagrammerAll();
  }

  public void layoutDiagrammerAll() {
    //Création des panneaux du diagrammeur
    BorderLayout layout = new BorderLayout();
    this.content.setLayout(layout);
    this.content.add(this.panelPalette, BorderLayout.WEST);
    this.content.add(this.panelDraw, BorderLayout.CENTER);
    this.content.add(this.panelTitle, BorderLayout.NORTH);

    //Place le diagrammeur (JPanel content) dans son ancêtre PanelBorder
    super.addContent(this.content);
  }

  public void removePalette() {
    this.content.remove(this.panelPalette);
    this.repaint();
    MVCCDManager.instance().getMvccdWindow().adjustPanelRepository();
  }

  public void addPalette() {
    this.content.add(this.panelPalette, BorderLayout.WEST);
    this.repaint();
    MVCCDManager.instance().getMvccdWindow().adjustPanelRepository();
  }

  /**
   * Utile pour le print et l'export JPG & PNG
   *
   * @return Le Draw Panel comportant uniquement les entités
   */
  public DrawPanelComponent getPanelDraw() {
    return panelDraw;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  public JPanel getPanelTitle() {
    return this.panelTitle;
  }

  @Override
  public Dimension resizeContent() {
    return super.resizeContent();
  }

}
