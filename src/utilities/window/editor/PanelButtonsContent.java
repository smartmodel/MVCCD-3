package utilities.window.editor;

import console.ViewLogsManager;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.Project;
import utilities.Trace;
import utilities.files.UtilFiles;
import utilities.window.DialogMessage;
import utilities.window.PanelContent;
import utilities.window.scomponents.SButton;
import utilities.window.services.ComponentService;
import window.help.HelpWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class PanelButtonsContent extends PanelContent
        implements IAccessDialogEditor, ActionListener {

    private JPanel panel = new JPanel();
    protected SButton btnOk ;
    protected SButton btnClose;
    protected SButton btnApply ;
    protected SButton btnUndo;
    private SButton btnHelp ;
    protected SButton btnReInit ;
    private JTextArea messages ;
    private JScrollPane messagesScroll;
    private PanelButtons panelButtons;
    private Box bVer ;
    protected Box btns ;


    public PanelButtonsContent(PanelButtons panelButtons) {
        super(panelButtons);
        this.panelButtons = panelButtons;

        //TODO-0 à adapter pour la customization
        createContent();
        super.addContent(panel, false);


    }

    protected void createContent() {

        bVer = Box.createVerticalBox();
        //colorBVer();

        messages = new JTextArea();
        messages.setText("");
        messages.setEditable(false);
        //messages.setComponentPopupMenu(new ButtonsContentMessagesPopupMenu());
        messagesScroll = new JScrollPane(messages);
        messagesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        messagesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //messagesScroll.setPreferredSize( new Dimension(getWidthWindow(), Preferences.PANEL_BUTTONS_MESSAGES_HEIGHT));
        messagesScroll.setPreferredSize(
                new Dimension(getEditor().getWidthInit(), Preferences.PANEL_BUTTONS_MESSAGES_HEIGHT));

        bVer.add(messagesScroll);
        bVer.add(Box.createVerticalStrut(Preferences.JPANEL_VGAP));
        btns = Box.createHorizontalBox();

        btnUndo = new SButton("Annuler");
        btnUndo.addActionListener(this);
        btnUndo.setToolTipText("Annuler la saisie effectuée depuis le dernier enregistrement");
        btnUndo.setEnabled(false);
        btns.add(btnUndo);

        btnReInit= new SButton("Réinitialiser");
        btnReInit.addActionListener(this);
        //TODO-1 Affiner le message selon qu'un profil est existant ou pas
        // Depuis le profil s'il existe, sinon depuis les préférences de l'application
        btnReInit.setToolTipText("Réinitiliser les valeurs");
        btnReInit.setVisible(false);
        btns.add(btnReInit);
        btns.add(Box.createGlue());

        btnOk = new SButton("Ok");
        btnOk.addActionListener(this);
        btnOk.setToolTipText("Enregistrer la saisie et fermer la fenêtre");
        btnOk.setEnabled(false);
        btns.add(btnOk);
        btns.add(Box.createHorizontalStrut(Preferences.JPANEL_HGAP));
        btnClose = new SButton("Fermer");
        btnClose.addActionListener(this);
        btnClose.setToolTipText("Fermer la fenêtre sans enregistrer la saisie effectuée depuis le dernier enregistrement");
        btns.add(btnClose);
        btns.add(Box.createHorizontalStrut(Preferences.JPANEL_HGAP));
        btnApply = new SButton("Appliquer");
        btnApply.addActionListener(this);
        btnApply.setToolTipText("Enregistrer la saisie et garder la fenêtre ouverte");

        /*
        if (getEditor().getMode().equals(DialogEditor.NEW)){
            btnApply.setVisible(false);
        }

         */


        btnApply.setEnabled(false);
        btns.add(btnApply);
        btns.add(Box.createGlue());
        btnHelp = new SButton("Aide");
        btnHelp.addActionListener(this);
        btns.add(btnHelp);

        bVer.add(btns);
        BorderLayout bl = new BorderLayout(0,0);
        panel.setLayout(bl);
        panel.add(bVer);


        colorDebug();
    }

    //public abstract Integer getWidthWindow() ;

    protected abstract String getHelpFileName();


    public Dimension resizeContent(){

        Dimension dimensionBL = super.resizeContent();

        ComponentService.changeWidth(messagesScroll, panel.getWidth() /*- 2 *Preferences.JPANEL_HGAP */);
        ComponentService.changePreferredWidth(messagesScroll, panel.getWidth() /*- 2 *Preferences.JPANEL_VGAP */);

        //ComponentService.changeWidth(btns, panel.getWidth() - 2 *Preferences.JPANEL_HGAP );
        //ComponentService.changePreferredWidth(btns, panel.getWidth() - 2 *Preferences.JPANEL_VGAP );

        return dimensionBL;
    }

    public JButton getBtnUndo() {
        return btnUndo;
    }

    public JButton getBtnOk() {
        return btnOk;
    }

    public JButton getBtnClose() {
        return btnClose;
    }

    public JButton getBtnApply() {
        return btnApply;
    }

    public void addIfNotExistMessage(String message){
        if (StringUtils.indexOf(messages.getText(), message, 0) < 0) {
            if (StringUtils.isNotEmpty(messages.getText())) {
                messages.append("\r\n");
            }
            messages.append(message);
        }
    }

    public void clearMessages(){
        messages.setText("");
     }


    public DialogEditor getEditor(){
        return panelButtons.getEditor();
    }

    public PanelInput getInput(){
        return getEditor().getInput();
    }

    public PanelInputContent getInputContent(){
        return getInput().getInputContent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String property = "";
        MVCCDElement mvccdElementForCatchException = null;
        try {
            Object source = e.getSource();
            if (source == btnOk) {
                if (getEditor().getMode().equals(DialogEditor.NEW)) {
                    property = "editor.btn.exception.ok.new";
                    mvccdElementForCatchException = getEditor().getMvccdElementParent();
                    treatCreate();
                }
                if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
                    property = "editor.btn.exception.ok.update";
                    mvccdElementForCatchException = getEditor().getMvccdElementCrt();
                    treatUpdate();
                }
                getEditor().myDispose();
            }
            if (source == btnApply) {
                MVCCDElement mvccdElementForUpate = null;
                if (getEditor().getMode().equals(DialogEditor.NEW)) {
                    property = "editor.btn.exception.apply.new";
                    mvccdElementForCatchException = getEditor().getMvccdElementParent();
                    treatCreate();
                    mvccdElementForUpate = getEditor().getMvccdElementNew();
                }
                if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
                    property = "editor.btn.exception.ok.update";
                    mvccdElementForCatchException = getEditor().getMvccdElementCrt();
                    treatUpdate();
                    mvccdElementForUpate = getEditor().getMvccdElementCrt();
                }
                getEditor().myDispose();
                if (getEditor().getEditingTreat() != null){
                    getEditor().getEditingTreat().treatUpdate(getEditor().getOwner(), mvccdElementForUpate);
                }
            }
            if (source == btnUndo) {
                // Seulement en update
                property = "editor.btn.exception.apply.undo";
                mvccdElementForCatchException = getEditor().getMvccdElementCrt();
                treatReset();
            }
            if (source == btnReInit) {
                // Seulement en update
                property = "editor.btn.exception.apply.reinit";
                mvccdElementForCatchException = getEditor().getMvccdElementCrt();
                treatReInit();
            }
            if (source == btnClose) {
                if (getEditor().getMode().equals(DialogEditor.NEW)) {
                    property = "editor.btn.exception.apply.close.new";
                    mvccdElementForCatchException = getEditor().getMvccdElementParent();
                } else {
                    property = "editor.btn.exception.apply.close.not.new";
                    mvccdElementForCatchException = getEditor().getMvccdElementCrt();
                }
                getEditor().confirmClose();
            }
            if (source == btnHelp) {
                if (getEditor().getMode().equals(DialogEditor.NEW)) {
                    property = "editor.btn.exception.apply.help.new";
                    mvccdElementForCatchException = getEditor().getMvccdElementParent();
                } else {
                    property = "editor.btn.exception.apply.help.not.new";
                    mvccdElementForCatchException = getEditor().getMvccdElementCrt();
                }
                treatHelp();
            }

        } catch (Exception exception) {
            exceptionUnhandled(exception, mvccdElementForCatchException, property);
        }
    }

    protected void treatHelp(){
        String helpText = UtilFiles.fileTextToString(getHelpFileName());
        HelpWindow fen = new HelpWindow(getEditor());
        fen.setVisible(true);
        fen.setHelpText(helpText);

    }


    protected void treatReset(){
        getInputContent().resetDatas();
        // Effacement des éventuels anciens messages
        clearMessages();
        getInputContent().enabledButtons();
    }


    protected void treatReInit(){
        getInputContent().reInitDatas(getEditor().getMvccdElementCrt());
        // Effacement des éventuels anciens messages
        clearMessages();
        getInputContent().enabledButtons();
    }

    public void treatUpdate(){
        saveDatas(getEditor().getMvccdElementCrt());
        getEditor().setDatasChanged(true);
        getInputContent().restartChange();
        getInputContent().enabledButtons();
        getEditor().adjustTitle();
        DialogMessage.showOk(null, "Test");
        MVCCDManager.instance().showMVCCDElementInRepository(getEditor().getMvccdElementCrt());
    }

    public void  treatCreate() {
        MVCCDElement newMVCCDElement = createNewMVCCDElement(getEditor().getMvccdElementParentChoosed());
        saveDatas(newMVCCDElement);
        getEditor().setMvccdElementNew(newMVCCDElement);
        getEditor().setDatasChanged(true);

        if (!(newMVCCDElement instanceof Project)){
           // L'ajout doit se faire ici car si l'ajout est réalisé par Apply
           // le module appelant (editingTreat) ne reprend pas la main et la mise à jour n'est pas faite.
           MVCCDManager.instance().addNewMVCCDElementInRepository(newMVCCDElement);
        }
    }

    protected void saveDatas(MVCCDElement mvccdElement) {
        getEditor().getInput().getInputContent().saveDatas(mvccdElement);

    }

    protected abstract MVCCDElement createNewMVCCDElement(MVCCDElement parent);


    private void colorDebug(){
        if (PreferencesManager.instance().preferences().isDEBUG()) {
            if (PreferencesManager.instance().preferences().isDEBUG_BACKGROUND_PANEL()) {
                btns.setOpaque(true);
                btns.setBackground(Color.BLACK);

                bVer.setOpaque(true);
                bVer.setBackground(Color.YELLOW);

                panel.setBackground(Color.BLUE);
            }
        }
    }


    public void setButtonsReadOnly(boolean readOnly) {
        if (readOnly){
            btnOk.setReadOnly(readOnly);
            btnApply.setReadOnly(readOnly);
            btnUndo.setReadOnly(readOnly);
         }
    }
    private void exceptionUnhandled(Exception e,
                                    MVCCDElement mvccdElement,
                                    String property) {

        String action = MessagesBuilder.getMessagesProperty(property);
        String nameMVCCDElement = mvccdElement.getNameTree();
        String nameClassMVCCDElement = mvccdElement.getClass().getName();
        String message = MessagesBuilder.getMessagesProperty("editor.btn.exception",
                new String[] {action, nameMVCCDElement, nameClassMVCCDElement} );
        ViewLogsManager.catchException(e, getEditor(), message);
    }



}
