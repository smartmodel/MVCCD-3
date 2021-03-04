package window.editor.project;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.services.MCDProjectService;
import preferences.Preferences;
import preferences.PreferencesManager;
import profile.ProfileManager;
import project.Project;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;

public class ProjectInputContent extends PanelInputContent {

    //private JPanel panel = new JPanel();
    private STextField projectName = new STextField(this);
    private STextField projectVersion = new STextField(this);
    private STextField projectFileName = new STextField(this);
    private SComboBox<String> profileFileName = new SComboBox<>(this);
    private SCheckBox fieldModelsMany = new SCheckBox(this);
    private SCheckBox fieldPackagesAutorizeds = new SCheckBox(this);
    //private STextField profileFile = new STextField();
    //private JButton profileChoice = new JButton ("...");


    public ProjectInputContent(ProjectInput projectInput)     {
        super(projectInput);
     }



    public void createContentCustom() {
        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();

        projectName.setPreferredSize((new Dimension(300,Preferences.EDITOR_FIELD_HEIGHT)));
        projectName.setCheckPreSave(true);
        //projectName.setText("");
        projectName.getDocument().addDocumentListener(this);
        projectName.addFocusListener(this);

        projectVersion.setPreferredSize((new Dimension(100,Preferences.EDITOR_FIELD_HEIGHT)));
        projectVersion.setVisible(!getEditor().getMode().equals(DialogEditor.NEW));
        projectVersion.setReadOnly(true);

        //TODO-1 Traiter le cas du nom complet qui dépasse la taille de la zone d'affichage
        projectFileName.setPreferredSize((new Dimension(500,Preferences.EDITOR_FIELD_HEIGHT)));
        projectFileName.setVisible(!getEditor().getMode().equals(DialogEditor.NEW));
        projectFileName.setReadOnly(true);

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

        super.getSComponents().add(projectName);
        super.getSComponents().add(projectVersion);
        super.getSComponents().add(profileFileName);
        super.getSComponents().add(fieldModelsMany);
        super.getSComponents().add(fieldPackagesAutorizeds);


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
        panelInputContentCustom.add(new JLabel("Version : "), gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(projectVersion, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(new JLabel("Fichier : "), gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(projectFileName, gbc);

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
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }

    @Override
    public boolean checkDatasPreSave(SComponent sComponent) {

        boolean  ok = super.checkDatasPreSave(sComponent);
        boolean notBatch = panelInput != null;
        boolean unitaire;

        unitaire = notBatch && (sComponent == projectName);
        ok = checkProjectName(unitaire) && ok ;


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
    protected void initDatas() {
        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();

        // Initialisation sans passer par un objet transitoire car la création d'un projet
        // déclenche trop d'automatisme qu'il est certainement difficile de stopper pour un objet transitoire
        projectName.setText("");
        projectVersion.setText(Preferences.APPLICATION_VERSION);
        projectFileName.setText("");
        fieldModelsMany.setSelected(applicationPref.getREPOSITORY_MCD_MODELS_MANY());
        fieldPackagesAutorizeds.setSelected(applicationPref.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
        profileFileName.setSelectedEmpty();
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Project project = (Project) mvccdElement;
        projectName.setText(project.getName());
        projectVersion.setText(project.getVersion());
        String filePath = "";
        File file = MVCCDManager.instance().getFileProjectCurrent();
        if (file != null){
            filePath = file.getPath();
        }
        projectFileName.setText(filePath);
        if (project.getProfileFileName() != null) {
            profileFileName.setSelectedItem(project.getProfileFileName());
        } else {
            profileFileName.setSelectedEmpty();
        }
        fieldModelsMany.setSelected(project.isModelsMany());
        fieldPackagesAutorizeds.setSelected(project.isPackagesAutorizeds());
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
