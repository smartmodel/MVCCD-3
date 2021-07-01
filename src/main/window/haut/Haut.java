package main.window.haut;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import diagram.Diagram;
import main.window.console.WinConsole;
import main.window.diagram.WinDiagram;
import main.window.repository.WinRepository;
import main.window.reserve.Reserve;
import preferences.Preferences;
import repository.Repository;
import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;
import utilities.window.PanelContent;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtonsContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Haut extends PanelBorderLayout {

    private HautContent content ;
    Boolean theme = true;
    JButton modeSombre = new JButton("Mode sombre");

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
                        repository.getContent().setBackground(Preferences.GRIS);
                        repository.setBackground(Preferences.GRIS);
                        // Couleur pour le diagramme
                        diagram.setBackground(Preferences.GRIS);
                        // Couleur pour la console
                        winConsole.getContent().setBackground(Preferences.GRIS);
                        winConsole.setBackground(Preferences.GRIS);
                        // Couleur pour la reserve
                        reserve.setBackground(Preferences.GRIS);
                        // Couleur pour la palette
                        palette.setBackground(Preferences.GRIS);
                        UIManager.setLookAndFeel(new FlatDarculaLaf());
                    } catch (Exception ex) {
                        System.err.println("Failed to initialize LaF");
                    }
                } else {
                    try {
                        // Couleur pour le référentiel
                        repository.getContent().setBackground(Preferences.BLANC);
                        repository.setBackground(Preferences.BLANC);
                        // Couleur pour le diagramme
                        diagram.setBackground(Preferences.BLANC);
                        // Couleur pour la console
                        winConsole.getContent().setBackground(Preferences.BLANC);
                        winConsole.setBackground(Preferences.BLANC);
                        // Couleur pour la reserve
                        reserve.setBackground(Preferences.BLANC);
                        // Couleur pour la palette
                        palette.setBackground(Preferences.BLANC);
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
