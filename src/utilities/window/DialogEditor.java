package utilities.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DialogEditor extends JDialog implements WindowListener {

    public static final String NEW = "new";
    public static final String UPDATE = "update";

    private JPanel panel= new JPanel();;
    private PanelBorderLayoutResizer panelBLResizer ;
    private PanelBorderLayout editor ;
    private PanelBorderLayout buttons ;

    public DialogEditor(Frame owner) {
        super(owner);
        setModal(true);
        setLocation(100,100);

        getContentPane().add(panel);

    }

    public void start(){

        panelBLResizer = new PanelBorderLayoutResizer();
        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);
        String borderLayoutPositionEditor = BorderLayout.CENTER;
        String borderLayoutPositionButtons = BorderLayout.SOUTH;
        editor.setBorderLayoutPosition(borderLayoutPositionEditor);
        editor.setPanelBLResizer(panelBLResizer);
        buttons.setBorderLayoutPosition(borderLayoutPositionButtons);
        buttons.setPanelBLResizer(panelBLResizer);

        editor.start();
        buttons.start();

        panel.add(editor, borderLayoutPositionEditor);
        panel.add(buttons, borderLayoutPositionButtons);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                panelBLResizer.resizerContentPanels();
            }
        });

        this.addWindowListener(this);

    }

    public PanelBorderLayout getEditor() {
        return editor;
    }

    public void setEditor(PanelBorderLayout editor) {
        this.editor = editor;
    }

    public PanelBorderLayout getButtons() {
        return buttons;
    }

    public void setButtons(PanelBorderLayout buttons) {
        this.buttons = buttons;
    }

    public void setPanelBLResizer(PanelBorderLayoutResizer panelBLResizer) {
        this.panelBLResizer = panelBLResizer;
    }

    public PanelBorderLayoutResizer getPanelBLResizer() {
        return panelBLResizer;
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        this.dispose();
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
     }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
