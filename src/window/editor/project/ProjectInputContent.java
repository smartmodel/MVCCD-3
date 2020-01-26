package window.editor.project;

import mcd.services.MCDProjectService;
import preferences.Preferences;
import utilities.window.STextField;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ProjectInputContent extends PanelInputContent  {

    private JPanel panel = new JPanel();
    private STextField projectName = new STextField();
    private JComboBox<String> profile = new JComboBox<>();


    public ProjectInputContent(ProjectInput projectInput)     {
        super(projectInput);
        createContent();
        super.addContent(panel);
    }

    private void createContent() {

        projectName.setPreferredSize((new Dimension(300,20)));
        projectName.getDocument().addDocumentListener(this);
        projectName.addFocusListener(this);

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


    protected void changeField(DocumentEvent e) {
        if (projectName.getDocument() == e.getDocument()) {
            checkProjectName(true);
        }
    }



    @Override
    public void focusGained(FocusEvent focusEvent) {
        super.focusGained(focusEvent);
        Object source = focusEvent.getSource();
        if (source == projectName) {
            checkProjectName(true);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }

    @Override
    public boolean checkDatasPreSave() {
        return false;
    }


    public boolean checkDatas(){
        return checkProjectName(false);
    }

    private boolean checkProjectName(boolean unitaire) {
        ArrayList<String> messagesErrors = MCDProjectService.checkName(projectName.getText());

        if (unitaire){
            super.showCheckResultat(projectName, messagesErrors);
        }
        return messagesErrors.size() == 0;
    }




}
