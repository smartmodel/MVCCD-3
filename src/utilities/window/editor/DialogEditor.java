package utilities.window.editor;

import m.MElement;
import main.MVCCDElement;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import repository.editingTreat.EditingTreat;
import utilities.window.DialogMessage;
import utilities.window.PanelBorderLayoutResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class DialogEditor extends JDialog implements WindowListener, FocusListener {

    public static final String NEW = "new";
    public static final String UPDATE = "update";
    public static final String READ = "read";
    public static final String DELETE = "delete";

    public static final int SCOPE_NOTHING = 0;

    private Window owner;

    private JPanel panel= new JPanel();
    private PanelBorderLayoutResizer panelBLResizer ;
    private int widthInit; // Largeur initiale utile à dimensionner la boite des messages
    private PanelInput input;
    private PanelButtons buttons ;
    private String mode;  // Création ou modification
    private MVCCDElement mvccdElementParent = null;     // Parent pour la création
    private MVCCDElement mvccdElementCrt = null;     // lui-même pour la modification, suppression, lecture
    private MVCCDElement mvccdElementNew = null;     // lui-même pour la modification
    private MVCCDElement mvccdElementParentChoosed = null;     // la parent effectivement choisi lors de la saisie
    private boolean datasChanged = false;               // données modifiées par l'éditeur

    private boolean readOnly = false;

    protected int scope;
    private EditingTreat editingTreat = null;

    //#MAJ 2021-07-31 Spéficité d'un élément transitoire
    private boolean newElementTransitory = false;

    public DialogEditor(Window owner,
                        MVCCDElement mvccdElementParent,
                        MVCCDElement mvccdElementCrt,
                        String mode,
                        int scope,
                        EditingTreat editingTreat) {


        super(owner);
        this.owner = owner ;
        this.mode = mode;
        this.mvccdElementParent = mvccdElementParent;
        this.mvccdElementCrt = mvccdElementCrt;
        this.mvccdElementParentChoosed = mvccdElementParent;  // valeur par défaut
        this.scope = scope ;
        this.editingTreat = editingTreat;

        if (mode.equals(DialogEditor.READ)){
            this.setReadOnly(true);
        }
        if (mode.equals(DialogEditor.DELETE)){
            this.setReadOnly(true);
        }

        setModal(true);
        setLocation(100,100);

        getContentPane().add(panel);

        setSize(getSizeCustom());
        if (getLocationCustom() != null){
            setLocation(getLocationCustom());
        }
        setInput(getInputCustom());
        setButtons(getButtonsCustom());

        start();
    }


    protected abstract PanelButtons getButtonsCustom();

    protected abstract PanelInput getInputCustom();

    protected abstract Dimension getSizeCustom();
    protected abstract void setSizeCustom(Dimension dimension);
    protected abstract Point getLocationCustom();
    protected abstract void setLocationCustom(Point point);


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

        //TODO-1 A voir pour une spécialisation
        if ( input.getInputContent() instanceof PanelInputContentTable){
            buttons.getButtonsContent().btnUndo.setVisible(false);
            buttons.getButtonsContent().btnApply.setVisible(false);
            buttons.getButtonsContent().btnOk.setVisible(false);
        }

        panel.add(input, borderLayoutPositionEditor);
        panel.add(buttons, borderLayoutPositionButtons);

        startExtended();
        initialResize();

    }

    protected void startExtended(){

    }

    public void initialResize(){
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

    @Override
    public Window getOwner() {
        return owner;
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
        System.out.println("Ouvert");
            // Les données ont peut-être été ajustées par les méthodes changeXXX de l'éditeur
            // Changement avant que l'utilisateur ne fase quoi que ce soit
        if (! mode.equals(DialogEditor.NEW)) {
            if (input.getInputContent().datasChangedNow()  && (input != null)){
                String messageMode ;
                if (mode.equals(UPDATE)){
                    messageMode = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change.update");
                } else {
                    messageMode = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change.not.update");
                }
                String message = MessagesBuilder.getMessagesProperty("dialog.adjust.by.change",
                        new String[] {messageMode});
                DialogMessage.showOk(this, message);
                //#MAJ 2020-12-05 - Instruction manquante
                input.getInputContent().enabledButtons();
            }
        }
        //TODO-1 traiter le cas d'un ajustement de données avec un formulaire en lecture seule

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        confirmClose();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        //this.dispose();
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        //MVCCDManager.instance().setDatasEdited(true);
        //datasProjectElementEdited = true;
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

    // Ajouté pour les éditeurs spécialisés de connexion SGBD
    protected abstract String getTitleSpecialized ();

    private String getTitleByMode(String mode) {
        String title="";
        if (mode.equals(DialogEditor.NEW)){
            title = MessagesBuilder.getMessagesProperty(getPropertyTitleNew());
        }
        if (mode.equals(DialogEditor.UPDATE)){
            title = MessagesBuilder.getMessagesProperty(getPropertyTitleUpdate(), new String[]{
                    getElementNameTitle() });
        }
        if (mode.equals(DialogEditor.READ) ||
                mode.equals(DialogEditor.DELETE) ){
            title = MessagesBuilder.getMessagesProperty(getPropertyTitleRead(), new String[]{
                    getElementNameTitle() });
        }
        if (StringUtils.isNotEmpty(getTitleSpecialized())){
            title = title + getTitleSpecialized();
        }
        return title ;
    }

    protected abstract String getPropertyTitleNew();

    protected abstract String getPropertyTitleUpdate();

    protected abstract String getPropertyTitleRead();

    protected String getElementNameTitle(){
        if ( getMvccdElementCrt() instanceof MElement){
            //String treeNaming = PreferencesManager.instance().preferences().getMCD_TREE_NAMING_ASSOCIATION();
            //TODO-1 A paramétrer avec treeNaming
            return ((MElement) getMvccdElementCrt()).getNameTreePath();
        } else {
            return getMvccdElementCrt().getNameTree();
        }

    }

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

    public MVCCDElement getMvccdElementParentChoosed() {
        return mvccdElementParentChoosed;
    }

    public void setMvccdElementParentChoosed(MVCCDElement mvccdElementParentChoosed) {
        this.mvccdElementParentChoosed = mvccdElementParentChoosed;
    }


    public int getScope() {
        return scope;
    }



    public JPanel getPanel() {
        return panel;
    }

    public EditingTreat getEditingTreat() {
        return editingTreat;
    }

    void confirmClose() {

        //#MAJ 2021-06-05 Formulaire Read avec changement de valeurs de paramétrage - NamingInputContent

        if (getInput().getInputContent().datasChangedNow()){
            String message = MessagesBuilder.getMessagesProperty ("editor.close.change.not.saved");
            boolean confirm = DialogMessage.showConfirmYesNo_No(this, message) == JOptionPane.YES_OPTION;
            if (confirm){
                myDispose();
                //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//cancel
            }
        } else {
            myDispose();
        }
    }

    //TODO-2 J'ai du écrire de mon propre dispose et non surcharger car j'avais un appel parasite!
    public void myDispose(){
        setLocationCustom(getLocationOnScreen());
        setSizeCustom(getSize());
        super.dispose();
    }

    public boolean isNewElementTransitory() {
        return newElementTransitory;
    }

    public void setNewElementTransitory(boolean newElementTransitory) {
        this.newElementTransitory = newElementTransitory;
    }
}
