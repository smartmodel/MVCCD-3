package window.editor.project;

import main.MVCCDElement;
import mcd.services.MCDProjectService;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.ProfileManager;
import project.Project;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.SComboBox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ProjectInputContent extends PanelInputContent {

    //private JPanel panel = new JPanel();
    private STextField projectName = new STextField(this);
    private SComboBox<String> profileFileName = new SComboBox<>(this);
    private SCheckBox fieldModelsMany = new SCheckBox(this);
    private SCheckBox fieldPackagesAutorizeds = new SCheckBox(this);
    //private STextField profileFile = new STextField();
    //private JButton profileChoice = new JButton ("...");


    public ProjectInputContent(ProjectInput projectInput)     {
        super(projectInput);
        /*
        projectInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();

         */
    }



    public void createContentCustom() {
        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();

        projectName.setPreferredSize((new Dimension(300,20)));
        projectName.setCheckPreSave(true);
        //projectName.setText("");

        projectName.getDocument().addDocumentListener(this);
        projectName.addFocusListener(this);

        ArrayList<String> filesProfile = ProfileManager.instance().filesProfile();
        profileFileName.addItem(SComboBox.LINEDASH);
        for (String fileProfile : filesProfile){
            profileFileName.addItem(fileProfile);
        }
        profileFileName.addItemListener(this);
        profileFileName.addFocusListener(this);

        fieldModelsMany.setEnabled(applicationPref.getREPOSITORY_MCD_MODELS_MANY());
        fieldModelsMany.addItemListener(this);
        fieldModelsMany.addFocusListener(this);

        fieldPackagesAutorizeds.setEnabled(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
        fieldPackagesAutorizeds.addItemListener(this);
        fieldPackagesAutorizeds.addFocusListener(this);

        // Pas de modification pour l'instant Modèles et paquetages
        //TODO-1 A gérer complétement l'autorisation, son retrait et les conséquences
        if(getEditor().getMode().equals(DialogEditor.UPDATE)){
            fieldModelsMany.setEnabled(false);
            fieldPackagesAutorizeds.setEnabled(false);
        }

        super.getsComponents().add(projectName);
        super.getsComponents().add(profileFileName);
        super.getsComponents().add(fieldModelsMany);
        super.getsComponents().add(fieldPackagesAutorizeds);


        panelInputContentCustom.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panelInputContentCustom.add(new JLabel("Nom : "), gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(projectName, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Profil : "), gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(profileFileName, gbc);

/*
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Profil : "), gbc);
        gbc.gridx ++;
        panel.add(profileFile, gbc);
        gbc.gridx ++;
        panel.add(profileChoice, gbc);

 */

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Multiples modèles MCD : "), gbc);
        gbc.gridx ++;
        panelInputContentCustom.add(fieldModelsMany, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Paquetages MCD autorisés: "), gbc);
        gbc.gridx ++;
        panelInputContentCustom.add(fieldPackagesAutorizeds, gbc);

    }



    public JTextField getProjectName() {
        return projectName;
    }


    protected boolean changeField(DocumentEvent e) {
        boolean ok = true;
        if (projectName.getDocument() == e.getDocument()) {
            checkDatas(projectName);
        }
        return ok;
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
            checkDatas(projectName);
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }

    @Override
    public boolean checkDatasPreSave(SComponent sComponent) {

        boolean notBatch = panelInput != null;
        boolean unitaire;

        unitaire = notBatch && (sComponent == projectName);
        boolean ok = checkProjectName(unitaire) ;

        super.setPreSaveOk(ok);
        return ok;
    }



    public boolean checkDatas(SComponent sComponent){
        boolean ok = super.checkDatas(sComponent);
        return ok;
    }


    @Override
    protected void enabledContent() {

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
            profileFileName.setSelectedEmpty();
        }
        fieldModelsMany.setSelected(project.isModelsMany());
        fieldPackagesAutorizeds.setSelected(project.isPackagesAutorizeds());
    }

    @Override
    protected void initDatas() {
        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();

        fieldModelsMany.setSelected(applicationPref.getREPOSITORY_MCD_MODELS_MANY());
        fieldPackagesAutorizeds.setSelected(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
        profileFileName.setSelectedEmpty();
     }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Project project = (Project) mvccdElement;
        if (projectName.checkIfUpdated()){
            project.setName(projectName.getText());
        }
        if (profileFileName.checkIfUpdated()){
            if (! profileFileName.isSelectedEmpty()){
                project.setProfileFileName((String) profileFileName.getSelectedItem());
            } else {
                project.setProfileFileName(null);
            }
            if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
                // A déplacer dans Projet
                project.adjustProfile();
           }
        }
        if (fieldModelsMany.checkIfUpdated()){
            project.setModelsMany(fieldModelsMany.isSelected());
            if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
                // A déplacer dans Projet
                //project.adjustModelsMany();
            }
        }
        if (fieldPackagesAutorizeds.checkIfUpdated()){
            project.setPackagesAutorizeds(fieldPackagesAutorizeds.isSelected());
            if (getEditor().getMode().equals(DialogEditor.UPDATE)) {
                // A déplacer dans Projet
                //project.adjustPackagesAuthorizeds();
            }
        }
    }



}
