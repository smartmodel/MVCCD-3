package window.editor.preferences.application;

import main.MVCCDElement;
import messages.MessagesBuilder;
import utilities.window.editor.PanelInputContent;
import preferences.Preferences;
import preferences.PreferencesManager;
import preferences.PreferencesSaver;
import project.Project;
import utilities.window.DialogMessage;
import utilities.window.scomponents.SCheckBox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;

public class PrefApplicationInputContent extends PanelInputContent {
    //private JPanel panel = new JPanel();

    private SCheckBox debug = new SCheckBox(this);
    private JPanel debugSubPanel = new JPanel();

    private SCheckBox debugPrintMVCCDElement = new SCheckBox(this);
    private SCheckBox debugBackgroundPanel = new SCheckBox(this );
    private SCheckBox debugJTableShowHidden = new SCheckBox(this );
    private SCheckBox debugJTreeInspectObject = new SCheckBox(this );

    private SCheckBox fieldRepMCDModelsMany = new SCheckBox(this);
    private SCheckBox fieldRepMCDPackagesAuthorizeds = new SCheckBox(this );



    public PrefApplicationInputContent(PrefApplicationInput prefApplicationInput) {
        super(prefApplicationInput);
        /*
        prefApplicationInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();

         */
    }

    public void createContentCustom() {

        debug.setSubPanel(debugSubPanel);
        debug.setRootSubPanel(true);
        debug.addItemListener(this);
        debug.addFocusListener(this);
        debugPrintMVCCDElement.addItemListener(this);
        debugPrintMVCCDElement.addFocusListener(this);
        debugBackgroundPanel.addItemListener(this);
        debugBackgroundPanel.addFocusListener(this);
        debugJTableShowHidden.addItemListener(this);
        debugJTableShowHidden.addFocusListener(this);
        debugJTreeInspectObject.addItemListener(this);
        debugJTreeInspectObject.addFocusListener(this);

        fieldRepMCDModelsMany.addItemListener(this);
        fieldRepMCDModelsMany.addFocusListener(this);
        fieldRepMCDPackagesAuthorizeds.addItemListener(this);
        fieldRepMCDPackagesAuthorizeds.addFocusListener(this);

        //debugBackgroundPanel.addChangeListener(this);


        //debugBackgroundPanel.addChangeListener(this);

        super.getsComponents().add(debug);
        super.getsComponents().add(debugPrintMVCCDElement);
        super.getsComponents().add(debugBackgroundPanel);
        super.getsComponents().add(debugJTableShowHidden);
        super.getsComponents().add(debugJTreeInspectObject);
        super.getsComponents().add(fieldRepMCDModelsMany);
        super.getsComponents().add(fieldRepMCDPackagesAuthorizeds);


        panelInputContentCustom.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.anchor = GridBagConstraints.BELOW_BASELINE;
        gbc.insets = new Insets(10, 10, 0, 0);

        panelInputContentCustom.setAlignmentX(LEFT_ALIGNMENT);
        panelInputContentCustom.setAlignmentY(TOP_ALIGNMENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panelInputContentCustom.add(new JLabel ("Debug"));
        gbc.gridx++;
        panelInputContentCustom.add(debug ,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(debugSubPanel, gbc);

        debugSubPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        debugSubPanel.setAlignmentX(LEFT_ALIGNMENT);
        debugSubPanel.setLayout(new GridBagLayout());


        GridBagConstraints gbcDebug = new GridBagConstraints();
        gbcDebug.gridx = 0;
        gbcDebug.gridy = 0;
        gbcDebug.gridwidth = 1;
        gbcDebug.gridheight = 1;
        gbcDebug.anchor = GridBagConstraints.WEST;
        gbcDebug.insets = new Insets(5,5,5,5);


        debugSubPanel.add(new JLabel ("MVCCD Element"), gbcDebug);
        gbcDebug.gridx++;
        debugSubPanel.add(debugPrintMVCCDElement, gbcDebug);

        gbcDebug.gridy++ ;
        gbcDebug.gridx = 0;
        debugSubPanel.add(new JLabel ("Background Panel"), gbcDebug);
        gbcDebug.gridx++;
        debugSubPanel.add(debugBackgroundPanel, gbcDebug);

        gbcDebug.gridy++ ;
        gbcDebug.gridx = 0;
        debugSubPanel.add(new JLabel ("Id et Order dans JTable"), gbcDebug);
        gbcDebug.gridx++;
        debugSubPanel.add(debugJTableShowHidden, gbcDebug);

        gbcDebug.gridy++ ;
        gbcDebug.gridx = 0;
        debugSubPanel.add(new JLabel ("Inspecteur objet dans JTree"), gbcDebug);
        gbcDebug.gridx++;
        debugSubPanel.add(debugJTreeInspectObject, gbcDebug);

        gbc.gridy++ ;
        gbc.gridx = 0;
        panelInputContentCustom.add(new JLabel ("Modèles multiples autorisés"), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldRepMCDModelsMany, gbc);

        gbc.gridy++ ;
        gbc.gridx = 0;
        panelInputContentCustom.add(new JLabel ("Paquetages autorisés"), gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldRepMCDPackagesAuthorizeds, gbc);
    }


    @Override
    protected void enabledContent() {

    }

    @Override
    protected boolean changeField(DocumentEvent e) {
        return true;
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }





    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = PreferencesManager.instance().getApplicationPref();
        debug.setSelected(preferences.isDEBUG());
        debugPrintMVCCDElement.setSelected(preferences.isDEBUG_PRINT_MVCCDELEMENT());
        debugBackgroundPanel.setSelected(preferences.isDEBUG_BACKGROUND_PANEL());
        debugJTableShowHidden.setSelected(preferences.isDEBUG_SHOW_TABLE_COL_HIDDEN());
        debugJTreeInspectObject.setSelected(preferences.getDEBUG_INSPECT_OBJECT_IN_TREE());
        fieldRepMCDModelsMany.setSelected(preferences.getREPOSITORY_MCD_MODELS_MANY());
        fieldRepMCDPackagesAuthorizeds.setSelected(preferences.getREPOSITORY_MCD_PACKAGES_AUTHORIZEDS());
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        boolean restart = false;
        boolean reloadProject = false;
        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
        if (debug.checkIfUpdated()){
            applicationPref.setDEBUG(debug.isSelected());
        }
        if (debugPrintMVCCDElement.checkIfUpdated()){
            applicationPref.setDEBUG_PRINT_MVCCDELEMENT(debugPrintMVCCDElement.isSelected());
        }
        if (debugBackgroundPanel.checkIfUpdated()){
            applicationPref.setDEBUG_BACKGROUND_PANEL(debugBackgroundPanel.isSelected());
            restart = true;
        }
        if (debugJTableShowHidden.checkIfUpdated()){
            applicationPref.setDEBUG_SHOW_TABLE_COL_HIDDEN(debugJTableShowHidden.isSelected());
        }
        if (debugJTreeInspectObject.checkIfUpdated()){
            applicationPref.setDEBUG_INSPECT_OBJECT_IN_TREE(debugJTreeInspectObject.isSelected());
        }

        // Copie dans les préférences de pojet
        if (PreferencesManager.instance().getProjectPref() != null) {
            PreferencesManager.instance().copyApplicationPref(Project.EXISTING);
        }


        if (fieldRepMCDModelsMany.checkIfUpdated()){
            applicationPref.setREPOSITORY_MCD_MODELS_MANY(fieldRepMCDModelsMany.isSelected());
        }
        if (fieldRepMCDPackagesAuthorizeds.checkIfUpdated()){
            applicationPref.setREPOSITORY_MCD_PACKAGES_AUTHORIZEDS(fieldRepMCDPackagesAuthorizeds.isSelected());
            if (!restart){
                reloadProject = true;
            }
        }


        // Sauvegarde (fichier) des préférences d'application

        PreferencesManager.instance().createApplicationPref();
        PreferencesSaver saver = new PreferencesSaver();
        saver.save(new File(Preferences.FILE_APPLICATION_PREF_NAME), applicationPref);

        String message = MessagesBuilder.getMessagesProperty ("preferences.application.saved",
                new String[] {Preferences.APPLICATION_NAME });
        if (restart){
            message = message + MessagesBuilder.getMessagesProperty ("preferences.application.saved.restart");
        }
        if (reloadProject) {
            message = message + MessagesBuilder.getMessagesProperty ("preferences.application.saved.reload.project");
        }

        new DialogMessage().showOk(getEditor(), message);
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }


}

