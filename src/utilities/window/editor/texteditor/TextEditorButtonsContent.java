package utilities.window.editor.texteditor;

import console.ViewLogsManager;
import exceptions.service.ExceptionService;
import preferences.Preferences;
import utilities.files.FileWrite;
import utilities.window.PanelContent;
import utilities.window.scomponents.SButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TextEditorButtonsContent extends PanelContent implements ActionListener {

    private TextEditorButtons textEditorButtons;

    private JPanel panelButtons;

    protected SButton btnOk ;
    protected SButton btnClose;
    protected SButton btnReInit;
    protected SButton btnApply ;
    protected SButton btnUndo;

    public TextEditorButtonsContent(TextEditorButtons textEditorButtons) {
        super(textEditorButtons);
        this.textEditorButtons = textEditorButtons;

        panelButtons = new JPanel();

        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        btnUndo = new SButton("Annuler");
        btnUndo.addActionListener(this);
        btnUndo.setToolTipText("Annuler la saisie effectuée depuis la dernière sauvegarde");
        btnUndo.setEnabled(false);
        panelButtons.add(btnUndo);

        btnReInit= new SButton("Réinitialiser");
        btnReInit.addActionListener(this);
        btnReInit.setToolTipText("Réinitiliser les valeurs");
        btnReInit.setVisible(false);
        panelButtons.add(btnReInit);
        panelButtons.add(Box.createGlue());

        btnOk = new SButton("Sauver");
        btnOk.addActionListener(this);
        btnOk.setToolTipText("Sauver et fermer la fenêtre");
        btnOk.setEnabled(false);
        panelButtons.add(btnOk);
        panelButtons.add(Box.createHorizontalStrut(Preferences.JPANEL_HGAP));

        btnClose = new SButton("Fermer");
        btnClose.addActionListener(this);
        btnClose.setToolTipText("Fermer la fenêtre sans enregistrer la saisie effectuée depuis le dernier enregistrement");
        panelButtons.add(btnClose);
        panelButtons.add(Box.createHorizontalStrut(Preferences.JPANEL_HGAP));

        btnApply = new SButton("Appliquer");
        btnApply.addActionListener(this);
        btnApply.setToolTipText("Sauver et garder la fenêtre ouverte");
        btnApply.setEnabled(false);
        panelButtons.add(btnApply);


        super.addContent(panelButtons);


    }

    public SButton getBtnOk() {
        return btnOk;
    }

    public SButton getBtnClose() {
        return btnClose;
    }

    public SButton getBtnReInit() {
        return btnReInit;
    }

    public SButton getBtnApply() {
        return btnApply;
    }

    public SButton getBtnUndo() {
        return btnUndo;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Il ne faut pas de Thread car l'action manipule les composants Swing du formulaire actif
        /*
        new Thread(new Runnable() {
            public void run() {
                actionPerformedThread(actionEvent);
            }
        }).start();

         */
        actionPerformedThread(actionEvent);

    }
    public void actionPerformedThread(ActionEvent actionEvent) {

        TextEditor textEditor = textEditorButtons.getTextEditor();

        ViewLogsManager.clear();
        String propertyMessage = "editor.text.file.btn.exception.new";
        String propertyAction = "";
        try {
            Object source = actionEvent.getSource();

            if (source == btnUndo) {
                propertyAction = "sqlviewer.sql.ddl.execute.btn.exception.test";
                textEditor.initDatas();
            }

            if (source == btnOk) {
                propertyAction = "sqlviewer.save.btn.exception.test";
                saveFile(textEditor);
                textEditor.dispose();
            }

            if (source == btnClose) {
                textEditor.confirmClose();
            }

            if (source == btnApply) {
                propertyAction = "sqlviewer.sql.dml.view.btn.exception.test";
                saveFile(textEditor);
                textEditor.initDatas(); // Corresond à une réinitialisation
            }

        } catch (Exception exception) {
            ExceptionService.exceptionUnhandled(exception, textEditor, textEditor.getFile(),
                    propertyMessage, propertyAction);
        }
    }

    public void saveFile(TextEditor textEditor){

        File file = textEditor.getFile();
        String toWrite = textEditor.getTextEditorText().getTextEditorTextContent().getTextArea().getText();
        FileWrite.writeFromString(toWrite, file);
    }


}
