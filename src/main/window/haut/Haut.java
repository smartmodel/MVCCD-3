package main.window.haut;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import diagram.Diagram;
import main.window.console.WinConsole;
import main.window.diagram.WinDiagram;
import main.window.repository.WinRepository;
import main.window.reserve.Reserve;
import repository.Repository;
import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Haut extends PanelBorderLayout {

    private HautContent content ;
    Boolean theme = true;
    JButton modeSombre = new JButton("Mode sombre");
    Color blanc = Color.WHITE;
    Color gris = Color.decode("#45494A");

    public Haut(String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer, JFrame frame, WinRepository repository, WinDiagram diagram, WinConsole winConsole, Reserve reserve, Reserve palette) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        super.startLayout();

        add(modeSombre);

        this.setResizable(false);
        content = new HautContent(this);
        super.setPanelContent(content);


        modeSombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (theme) {
                    try {
                        // Couleur pour le référentiel
                        repository.getContent().setBackground(gris);
                        repository.setBackground(gris);
                        // Couleur pour le diagramme
                        diagram.setBackground(gris);
                        // Couleur pour la console
                        winConsole.getContent().setBackground(gris);
                        winConsole.setBackground(gris);
                        // Couleur pour la reserve
                        reserve.setBackground(gris);
                        // Couleur pour la palette
                        palette.setBackground(gris);
                        UIManager.setLookAndFeel(new FlatDarculaLaf());

                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                } else {
                    try {
                        // Couleur pour le référentiel
                        repository.getContent().setBackground(blanc);
                        repository.setBackground(blanc);
                        // Couleur pour le diagramme
                        diagram.setBackground(blanc);
                        // Couleur pour la console
                        winConsole.getContent().setBackground(blanc);
                        winConsole.setBackground(blanc);
                        // Couleur pour la reserve
                        reserve.setBackground(blanc);
                        // Couleur pour la palette
                        palette.setBackground(blanc);
                        UIManager.setLookAndFeel(new FlatLightLaf());
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                }
                theme = !theme;
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });


    }




    @Override
    public void resizeContent() {

    }

    @Override
    public PanelContent getPanelContent() {
        return null;
    }


}
