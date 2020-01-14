package window.preferences;

import preferences.Preferences;
import utilities.window.PanelBorderLayoutResizer;
import main.MVCCDWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PreferencesWindow extends JDialog {

    private JPanel panel= new JPanel();;
    private PrefStructure structure ;
    private PrefEditor editor ;
    private PrefButtons buttons ;
    private PanelBorderLayoutResizer panelBLResizer ;

    public PreferencesWindow(MVCCDWindow mvccdWindow)  {

        super(mvccdWindow);
        setTitle("Edition des préférences du projet");
        setSize(Preferences.PREF_WINDOW_WIDTH, Preferences.PREF_WINDOW_HEIGHT);
        setModal(true);
        getContentPane().add(panel);

        panelBLResizer = new PanelBorderLayoutResizer();

        String borderLayoutPositionStructure = BorderLayout.WEST;
        String borderLayoutPositionEditor = BorderLayout.CENTER;
        String borderLayoutPositionButtons = BorderLayout.SOUTH;

        structure = new PrefStructure(borderLayoutPositionStructure, panelBLResizer);
        structure.start();
        editor = new PrefEditor(borderLayoutPositionEditor, panelBLResizer);
        editor.start();
        buttons = new PrefButtons(borderLayoutPositionButtons, panelBLResizer);
        buttons.start();

        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);
        panel.add(structure, borderLayoutPositionStructure);
        panel.add(editor, borderLayoutPositionEditor);
        panel.add(buttons, borderLayoutPositionButtons);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                panelBLResizer.resizerContentPanels();
            }
        });

    }

 }
