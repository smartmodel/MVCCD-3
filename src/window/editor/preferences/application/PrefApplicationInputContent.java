package window.editor.preferences.application;

import main.MVCCDElement;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import preferences.PreferencesSaver;
import utilities.window.DialogMessage;
import utilities.window.SCheckBox;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;

public class PrefApplicationInputContent extends PanelInputContent  {
    private JPanel panel = new JPanel();

    private SCheckBox debug = new SCheckBox("Debug");
    private JPanel debugSubPanel = new JPanel();

    private SCheckBox debugPrintMVCCDElement = new SCheckBox("MVCCD Element");
    private SCheckBox debugBackgroundPanel = new SCheckBox("Background Panel");

    public PrefApplicationInputContent(PrefApplicationInput prefApplicationInput) {
        super(prefApplicationInput);
        prefApplicationInput.setPanelContent(this);
        createContent();
        super.addContent(panel);
        super.initOrLoadDatas();
    }

    private void createContent() {

        debug.setSubPanel(debugSubPanel);
        debug.setRootSubPanel(true);
        debug.addChangeListener(this);
        debug.addFocusListener(this);
        debugPrintMVCCDElement.addChangeListener(this);
        debugPrintMVCCDElement.addFocusListener(this);
        debugBackgroundPanel.addChangeListener(this);
        debugBackgroundPanel.addFocusListener(this);

        //debugBackgroundPanel.addChangeListener(this);

        super.getsComponents().add(debug);
        super.getsComponents().add(debugPrintMVCCDElement);
        super.getsComponents().add(debugBackgroundPanel);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(debug ,gbc);
        gbc.gridx++;
        panel.add(debugSubPanel, gbc);

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


        debugSubPanel.add(debugPrintMVCCDElement, gbcDebug);
        gbcDebug.gridy++;
        debugSubPanel.add(debugBackgroundPanel, gbcDebug);

    }

    @Override
    protected boolean checkDatas() {
        return true;
    }

    @Override
    public boolean checkDatasPreSave() {
        return true;
    }

    @Override
    protected void changeField(DocumentEvent e) {

    }

    @Override
    protected void changeField(ChangeEvent e) {

    }

    @Override
    protected void changeField(ItemEvent e) {

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElement) {
        Preferences preferences = PreferencesManager.instance().getApplicationPref();
        debug.setSelected(preferences.getDEBUG());
        debugPrintMVCCDElement.setSelected(preferences.getDEBUG_PRINT_MVCCDELEMENT());
        debugBackgroundPanel.setSelected(preferences.getDEBUG_BACKGROUND_PANEL());

        super.enableSubPanels();
    }

    @Override
    protected void initDatas(MVCCDElement mvccdElement) {

    }

    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences applicationPref = PreferencesManager.instance().getApplicationPref();
        if (debug.checkIfUpdated()){
            applicationPref.setDEBUG(debug.isSelected());
        }
        if (debugPrintMVCCDElement.checkIfUpdated()){
            applicationPref.setDEBUG_PRINT_MVCCDELEMENT(debugPrintMVCCDElement.isSelected());
        }
        if (debugBackgroundPanel.checkIfUpdated()){
            applicationPref.setDEBUG_BACKGROUND_PANEL(debugBackgroundPanel.isSelected());
        }
        // Copie danms les préférences de pojet
        if (PreferencesManager.instance().getProjectPref() != null) {
            PreferencesManager.instance().copyApplicationPref();
        }
        // Sauvegarde (fichier) des préférences d'application
        PreferencesSaver saver = new PreferencesSaver();
        saver.save(new File(Preferences.FILE_APPLICATION_PREF_NAME), applicationPref);

        String message = MessagesBuilder.getMessagesProperty ("preferences.application.saved",
                new String[] {Preferences.APPLICATION_NAME });
        new DialogMessage().showOk(getEditor(), message);
    }


}

