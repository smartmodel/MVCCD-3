package screens.application;

import main.window.repository.WinRepositoryTree;
import org.w3c.dom.ls.LSOutput;
import window.editor.diagrammer.drawpanel.DrawPanelComponent;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen() {
        super("ArcDataModeler 3.0");
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.initZones();
    }

    private void initZones(){
        this.initRepositoryZone();
    }

    private void initRepositoryZone(){
        JTree repository = new JTree();
        System.out.println(repository);

        // Crée le conteneur contenant la palette d'outils et le diagrammeur
        JPanel diagrammerPaletteContainer = new JPanel(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        diagrammerPaletteContainer.setBackground(Color.pink);

        // Création de la barre d'outils située au-dessus
        JPanel toolbar = new JPanel();
        toolbar.setPreferredSize(new Dimension(100, 45));
        toolbar.setBackground(Color.GRAY);
        constraint.gridwidth = 12;
        constraint.gridy = 0;
        constraint.gridx = 0;
        constraint.weighty = 0;
        constraint.weightx = 1;
        constraint.fill = GridBagConstraints.BOTH;
        diagrammerPaletteContainer.add(toolbar, constraint);

        // Création de la palette
        PalettePanel palette = new PalettePanel();
        palette.setBackground(Color.blue);
        constraint.gridy = 1;
        constraint.gridx = 0;
        constraint.weightx = 0;
        constraint.weighty = 0;
        constraint.anchor = GridBagConstraints.WEST;
        constraint.fill = GridBagConstraints.VERTICAL;

        diagrammerPaletteContainer.add(palette, constraint);

        // Création du Diagrammer
        JScrollPane diagrammer = new DrawPanelComponent(DiagrammerService.getDrawPanel());
        constraint.gridy = 1;
        constraint.gridx = 1;
        constraint.weightx = 0;
        constraint.weighty = 1;
        constraint.fill = GridBagConstraints.BOTH;

        diagrammerPaletteContainer.add(diagrammer, constraint);














        JPanel console = new JPanel();
        console.setBackground(Color.ORANGE);
        repository.setBackground(Color.YELLOW);
        JScrollPane repositoryPanel = new JScrollPane(repository);
        repositoryPanel.setPreferredSize(new Dimension(200, 0));

        JSplitPane diagrammerConsoleSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, diagrammerPaletteContainer, console);
        diagrammerConsoleSplitPane.setResizeWeight(0.80);

        JSplitPane repositoryDiagrammerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, repositoryPanel, diagrammerConsoleSplitPane);

        this.getContentPane().add(repositoryDiagrammerSplitPane);

    }





}
