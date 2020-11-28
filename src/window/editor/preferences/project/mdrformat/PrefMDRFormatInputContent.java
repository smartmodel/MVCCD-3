package window.editor.preferences.project.mdrformat;

import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PrefMDRFormatInputContent extends PanelInputContent {

    //private JPanel panel = new JPanel();
    private JPanel panelConstraintsName = new JPanel ();
    private JLabel labelPKName ;
    private STextField fieldPKName;
    private JLabel labelFKName = new JLabel();
    private STextField fieldFKName;

    public PrefMDRFormatInputContent(PrefMDRFormatInput prefMDRFormatInput) {
        super(prefMDRFormatInput);
     }

    public void createContentCustom() {


        labelPKName = new JLabel("PK :");
        fieldPKName = new STextField(this, labelPKName);
        fieldPKName.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldPKName.getDocument().addDocumentListener(this);
        fieldPKName.addFocusListener(this);

        labelFKName = new JLabel("FK :");
        fieldFKName = new STextField(this, labelFKName);
        fieldFKName.setPreferredSize((new Dimension(600, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldFKName.getDocument().addDocumentListener(this);
        fieldFKName.addFocusListener(this);

        super.getSComponents().add(fieldPKName);
        super.getSComponents().add(fieldFKName);

        createPanelMaster();

    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        createPanelConstraintsName();

        panelInputContentCustom.add(panelConstraintsName, gbc);

    }


    private void createPanelConstraintsName() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelConstraintsName,
                "Nom des contraintes");

        gbcA.gridx = 0;
        gbcA.gridy = 0;
        gbcA.gridwidth = 1;
        gbcA.gridheight = 1;

        panelConstraintsName.add(labelPKName, gbcA);
        gbcA.gridx++ ;
        panelConstraintsName.add(fieldPKName, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++ ;
        panelConstraintsName.add(labelFKName, gbcA);
        gbcA.gridx++ ;
        panelConstraintsName.add(fieldFKName, gbcA);
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
        Preferences preferences = (Preferences) mvccdElement;
        loadDatasWithSource(mvccdElement, preferences);
        /*
        fieldPKName.setText(preferences.getMDR_PK_NAME_FORMAT());
        fieldFKName.setText(preferences.getMDR_FK_NAME_FORMAT());

         */
    }


    protected void reInitDatas(MVCCDElement mvccdElement){
        Preferences preferences ;
        if (PreferencesManager.instance().getProfilePref() != null) {
            preferences = PreferencesManager.instance().getProfilePref();
        } else {
            preferences = PreferencesManager.instance().getApplicationPref();
        }
        loadDatasWithSource(mvccdElement, preferences);
    }

    public void loadDatasWithSource(MVCCDElement mvccdElement, Preferences preferences) {
        fieldPKName.setText(preferences.getMDR_PK_NAME_FORMAT());
        fieldFKName.setText(preferences.getMDR_FK_NAME_FORMAT());
    }



    @Override
    protected void initDatas() {

    }




    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        Preferences preferences = (Preferences) mvccdElement;

        if (fieldPKName.checkIfUpdated()){
            preferences.setMDR_PK_NAME_FORMAT(fieldPKName.getText());
        }
        if (fieldFKName.checkIfUpdated()){
            preferences.setMDR_FK_NAME_FORMAT(fieldFKName.getText());
        }
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }



}

