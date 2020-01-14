package window.editor.project;

import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.DialogMessage;
import utilities.window.EditorContent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class ProjectEditorContent extends EditorContent implements DocumentListener, FocusListener {

    private ProjectEditor projectEditor;
    private JPanel panel = new JPanel();
    private JTextField projectName = new JTextField();
    private JComboBox<String> profile = new JComboBox<>();


    public ProjectEditorContent(ProjectEditor projectEditor)     {
        super(projectEditor);
        this.projectEditor = projectEditor;
        createContent();
        projectName.getDocument().addDocumentListener(this);
        projectName.addFocusListener(this);
        super.setContent(panel);


    }

    private void createContent() {

        projectName.setPreferredSize((new Dimension(300,20)));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(new JLabel("Nom : "), gbc);
        gbc.gridx = 1;
        panel.add(projectName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Profil : "), gbc);
        gbc.gridx = 1;
        panel.add(profile, gbc);
    }

    public JTextField getProjectName() {
        return projectName;
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        if (projectName.getDocument() == e.getDocument()) {
            checkProjectName(true, true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (projectName.getDocument() == e.getDocument()) {
            checkProjectName(true, true);
        }

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (projectName.getDocument() == e.getDocument()) {
            checkProjectName(true, true);
        }

    }

    @Override
    public void focusGained(FocusEvent focusEvent) {

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

         Object source = focusEvent.getSource();
            if (source == projectName) {
                checkProjectName(false, true);
            }


    }

    public boolean checkDatas(){
        boolean resultat = checkProjectName(false, false);

        return resultat;
    }

    private boolean checkProjectName(boolean inDocument, boolean unitaire) {
        boolean ok = true;
        String message = "";
        String message1 = MessagesBuilder.getMessagesProperty("project.and.name");
        if (!projectName.getText().matches(Preferences.NAME_REGEXPR)){
            message = message + MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[] {message1, Preferences.NAME_REGEXPR});
           ok = false;
        }
        if (projectName.getText().length() > Preferences.PROJECT_NAME_LENGTH){
            message = message + "\r\n" +MessagesBuilder.getMessagesProperty("editor.length.error"
                    , new String[] {message1, String.valueOf(Preferences.PROJECT_NAME_LENGTH)});
            ok = false;
        }

        if ( ! ok ){
            projectName.setBorder(BorderFactory.createLineBorder(Color.RED));
            if ((! inDocument) && unitaire){
                DialogMessage.showError(projectEditor.getProjectWindow(), message);
            }
        } else {
            projectName.setBorder(BorderFactory.createLineBorder(Color.gray));
        }


        return ok;
    }


}
