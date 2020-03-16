package window.editor.project;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.services.MCDProjectService;
import profile.ProfileManager;
import project.Project;
import utilities.window.scomponents.STextField;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComboBox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ProjectInputContent extends PanelInputContent  {

    private JPanel panel = new JPanel();
    private STextField projectName = new STextField(this);
    private SComboBox<String> profileFileName = new SComboBox<>(this);
    //private STextField profileFile = new STextField();
    //private JButton profileChoice = new JButton ("...");


    public ProjectInputContent(ProjectInput projectInput)     {
        super(projectInput);
        projectInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
    }



    private void createContent() {


        projectName.setPreferredSize((new Dimension(300,20)));
        projectName.setCheckPreSave(true);
        //projectName.setText("");

        projectName.getDocument().addDocumentListener(this);
        projectName.addFocusListener(this);

        ArrayList<String> filesProfile = ProfileManager.instance().filesProfile();
        profileFileName.addItem(SComboBox.LIGNETIRET);
        for (String fileProfile : filesProfile){
            profileFileName.addItem(fileProfile);
        }
        profileFileName.addItemListener(this);
        profileFileName.addFocusListener(this);

        super.getsComponents().add(projectName);
        super.getsComponents().add(profileFileName);

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
        panel.add(profileFileName, gbc);

/*
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Profil : "), gbc);
        gbc.gridx ++;
        panel.add(profileFile, gbc);
        gbc.gridx ++;
        panel.add(profileChoice, gbc);

 */

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
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

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
        return super.checkInput(projectName, unitaire, MCDProjectService.checkName(projectName.getText()));
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Project project = (Project) mvccdElement;
        projectName.setText(project.getName());
        if (project.getProfileFileName() != null) {
            profileFileName.setSelectedItem(project.getProfileFileName());
        } else {
            profileFileName.setSelectedItem(SComboBox.LIGNETIRET);
        }
    }

    @Override
    protected void initDatas(MVCCDElement mvccdElement) {
        profileFileName.setSelectedItem(SComboBox.LIGNETIRET);
     }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Project project = (Project) mvccdElement;
        if (projectName.checkIfUpdated()){
            project.setName(projectName.getText());
        }
        if (profileFileName.checkIfUpdated()){
            if (! profileFileName.getSelectedItem().equals(SComboBox.LIGNETIRET)){
                project.setProfileFileName((String) profileFileName.getSelectedItem());
            } else {
                project.setProfileFileName(null);
            }
            if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
                project.adjustProfile();
                MVCCDManager.instance().profileToRepository();
            }
        }
    }



}
