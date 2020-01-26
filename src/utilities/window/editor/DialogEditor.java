package utilities.window.editor;

import main.MVCCDElement;
import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;

public abstract class DialogEditor extends JDialog implements WindowListener, FocusListener {

    public static final String NEW = "new";
    public static final String UPDATE = "update";

    private JPanel panel= new JPanel();
    private PanelBorderLayoutResizer panelBLResizer ;
    private PanelInput input;
    private PanelButtons buttons ;
    private String mode;  // Création ou modification
    private DefaultMutableTreeNode node;   // Parent pour la création et lui-même pour la modification
    private MVCCDElement mvccdElement;     // Parent pour la création et lui-même pour la modification


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
        input.setBorderLayoutPosition(borderLayoutPositionEditor);
        input.setPanelBLResizer(panelBLResizer);
        buttons.setBorderLayoutPosition(borderLayoutPositionButtons);
        buttons.setPanelBLResizer(panelBLResizer);

        input.start();
        buttons.start();

        panel.add(input, borderLayoutPositionEditor);
        panel.add(buttons, borderLayoutPositionButtons);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                panelBLResizer.resizerContentPanels();
            }
        });

        this.addWindowListener(this);

        // Pour que l'ajustement automatique de positionnement des boutons se fasse
        setSize(getWidth(), getHeight() - 1);
        setSize(getWidth(), getHeight() + 1);

    }

    public PanelInput getInput() {
        return input;
    }

    public void setInput(PanelInput input) {
        this.input = input;
    }

    public PanelButtons getButtons() {

        return buttons;
    }

    public void setButtons(PanelButtons buttons) {
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


    @Override
    public void focusGained(FocusEvent focusEvent) {
        // Au cas où la fenêtre d'aide est présente
        System.out.println( "Focus obtenu Dialog Editor");
        //this.setAlwaysOnTop(true);
        //this.toFront();
        //this.setAlwaysOnTop(false);
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }


    public String getMode() {
        return mode;
    }

    protected void setMode(String mode) {
        this.mode = mode;
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

    public void setNode(DefaultMutableTreeNode node) {
        this.node = node;
    }

    public MVCCDElement getMvccdElement() {
        return mvccdElement;
    }

    public void setMvccdElement(MVCCDElement mvccdElement) {
        this.mvccdElement = mvccdElement;
    }

    public abstract void adjustTitle() ;
}
