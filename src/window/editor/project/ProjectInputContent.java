package window.editor.project;

import main.MVCCDElement;
import mcd.MCDEntity;
import mcd.services.MCDEntityService;
import mcd.services.MCDProjectService;
import preferences.Preferences;
import project.Project;
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
        projectName.setCheckPreSave(true);
        //projectName.setText("");

        projectName.getDocument().addDocumentListener(this);
        projectName.addFocusListener(this);

        super.getsComponents().add(projectName);

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
        boolean resultat = checkProjectName(false);
        return resultat;
    }


    public boolean checkDatas(){
        return checkProjectName(false);
    }

    private boolean checkProjectName(boolean unitaire) {
        return super.checkInput(projectName, unitaire, MCDEntityService.checkName(projectName.getText()));
    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        saveDatas((Project) mvccdElement);
    }

    public void saveDatas(Project project) {
        System.out.println("save...");
        if (projectName.checkIfUpdated()){
            project.setName(projectName.getText());
        }
    }


}
