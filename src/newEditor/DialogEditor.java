package newEditor;

import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class DialogEditor extends JDialog implements WindowListener, FocusListener {

    public static final String NEW = "new";
    public static final String UPDATE = "update";
    public static final String READ = "read";
    public static final String DELETE = "delete";

    private JPanel panel= new JPanel();
    private PanelBorderLayoutResizer panelBLResizer ;
    private int widthInit; // Largeur initiale utile à dimensionner la boite des messages
    private PanelInput input;
    private PanelButtons buttons ;
    private String mode;  // Création ou modification
    private MVCCDElement mvccdElementParent = null;     // Parent pour la création
    private MVCCDElement mvccdElementCrt = null;     // lui-même pour la modification, suppression, lecture
    private MVCCDElement mvccdElementNew = null;     // lui-même pour la modification
    private boolean datasChanged = false;     // données modifiées

    private boolean readOnly = false;

    public DialogEditor(Window owner, MVCCDElement mvccdElementParent, MVCCDElement mvccdElementCrt, String mode) {
        super(owner);
        this.mode = mode;
        this.mvccdElementParent = mvccdElementParent;
        this.mvccdElementCrt = mvccdElementCrt;

        if (mode.equals(DialogEditor.READ)){
            this.setReadOnly(true);
        }
        if (mode.equals(DialogEditor.DELETE)){
            this.setReadOnly(true);
        }

        setModal(true);
        setLocation(100,100);

        getContentPane().add(panel);

    }



    public void start(){
        setTitle (getTitleByMode(mode));

        panelBLResizer = new PanelBorderLayoutResizer();
        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);
        String borderLayoutPositionEditor = BorderLayout.CENTER;
        String borderLayoutPositionButtons = BorderLayout.SOUTH;
        input.setBorderLayoutPosition(borderLayoutPositionEditor);
        input.setPanelBLResizer(panelBLResizer);
        buttons.setBorderLayoutPosition(borderLayoutPositionButtons);
        buttons.setPanelBLResizer(panelBLResizer);

        input.startLayout();
        buttons.startLayout();
        input.getInputContent().setComponentsReadOnly(readOnly);
        buttons.getButtonsContent().setButtonsReadOnly(readOnly);


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
        MVCCDManager.instance().setDatasProjectEdited(true);
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }


    @Override
    public void focusGained(FocusEvent focusEvent) {
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

    public MVCCDElement getMvccdElementParent() {
        return mvccdElementParent;
    }

    public void setMvccdElementParent(MVCCDElement mvccdElementParent) {
        this.mvccdElementParent = mvccdElementParent;
    }

    public MVCCDElement getMvccdElementCrt() {
        return mvccdElementCrt;
    }

    public void setMvccdElementCrt(MVCCDElement mvccdElementCrt) {
        this.mvccdElementCrt = mvccdElementCrt;
    }

    public MVCCDElement getMvccdElementNew() {
        return mvccdElementNew;
    }

    public void setMvccdElementNew(MVCCDElement mvccdElementNew) {
        this.mvccdElementNew = mvccdElementNew;
    }

    public boolean isDatasChanged() {
        return datasChanged;
    }

    public void setDatasChanged(boolean datasChanged) {
        this.datasChanged = datasChanged;
    }

    public void adjustTitle() {
       String title = MessagesBuilder.getMessagesProperty(getPropertyTitleUpdate(), new String[]{
                getMvccdElementCrt().getName()});
        super.setTitle(title);
    }

    private String getTitleByMode(String mode) {
        String title="";
        if (mode.equals(DialogEditor.NEW)){
            System.out.println (getPropertyTitleNew());
            title = MessagesBuilder.getMessagesProperty(getPropertyTitleNew());
        }
        if (mode.equals(DialogEditor.UPDATE) ||
                mode.equals(DialogEditor.READ) ||
                mode.equals(DialogEditor.DELETE) ){
            title = MessagesBuilder.getMessagesProperty(getPropertyTitleUpdate(), new String[]{
                    getMvccdElementCrt().getName() });
        }
        return title;
    }

    protected abstract String getPropertyTitleNew();

    protected abstract String getPropertyTitleUpdate();

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
     }

    public void setSize(int width, int height ) {
        this.widthInit = width;
        super.setSize(width, height);
    }

    public int getWidthInit() {
        return widthInit;
    }
}
