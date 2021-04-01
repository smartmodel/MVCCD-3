package window.editor.mdr.utilities;

import main.MVCCDElement;
import mcd.MCDElement;
import md.MDElement;
import md.interfaces.IMDElementWithSource;
import mdr.MDRElement;
import messages.MessagesBuilder;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class PanelInputContentIdMDR extends PanelInputContent {

    protected JPanel panelId = new JPanel ();

    private JLabel labelName ;
    private STextField fieldName ;
    private JLabel labelName30 ;
    private STextField fieldName30 ;
    private JLabel labelName60 ;
    private STextField fieldName60 ;
    private JLabel labelName120 ;
    private STextField fieldName120 ;

    private JLabel labelSource;
    private STextField fieldSource ;


    //private JComboBox<String> profile = new JComboBox<>();


    public PanelInputContentIdMDR(PanelInput panelInput)     {
        super(panelInput);
    }

    public PanelInputContentIdMDR(MVCCDElement element)     {
        super(null);
        elementForCheckInput = element;
    }

    @Override
    public void createContentCustom() {

        labelName = new JLabel("Nom : ");
        fieldName = new STextField(this, labelName);
        fieldName.setPreferredSize((new Dimension(700, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName.setReadOnly(true);

        labelName30 = new JLabel(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_30));
        fieldName30 = new STextField(this, labelName);
        fieldName30.setPreferredSize((new Dimension(300, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName30.setReadOnly(true);

        labelName60 = new JLabel(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_60));
        fieldName60 = new STextField(this, labelName);
        fieldName60.setPreferredSize((new Dimension(500, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName60.setReadOnly(true);

        labelName120 = new JLabel(MessagesBuilder.getMessagesProperty(Preferences.MDR_NAMING_LENGTH_120));
        fieldName120 = new STextField(this, labelName);
        fieldName120.setPreferredSize((new Dimension(700, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName120.setReadOnly(true);

        labelSource = new JLabel("Source :");
        fieldSource = new STextField(this, labelSource);
        fieldSource.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldSource.setReadOnly(true);

        super.getSComponents().add(fieldName);
        super.getSComponents().add(fieldName30);
        super.getSComponents().add(fieldName60);
        super.getSComponents().add(fieldName120);
        super.getSComponents().add(fieldSource);

        //createPanelMaster();
    }

    protected GridBagConstraints createPanelId() {
        GridBagConstraints gbc = PanelService.createSubPanelGridBagConstraints(panelId, "Identification");

        panelId.add(labelName, gbc);
        gbc.gridx = 1;
        panelId.add(fieldName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelId.add(labelName30, gbc);
        gbc.gridx = 1;
        panelId.add(fieldName30, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelId.add(labelName60, gbc);
        gbc.gridx = 1;
        panelId.add(fieldName60, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelId.add(labelName120, gbc);
        gbc.gridx = 1;
        panelId.add(fieldName120, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelId.add(labelSource,gbc);
        gbc.gridx++;
        panelId.add(fieldSource, gbc);

        this.add(panelId);
        return gbc;
    }




    protected boolean changeField(DocumentEvent e) {
        boolean ok = true;
        SComponent sComponent = null;

        Document doc = e.getDocument();

        // Autres champs que les champs Id
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
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {
    }

    @Override
    protected void initDatas() {
    }

    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRElement mdrElement = (MDRElement) mvccdElementCrt;
        fieldName.setText(mdrElement.getName());
        if (mdrElement.getNames() != null) {
            fieldName30.setText(mdrElement.getNames().getName30());
            fieldName60.setText(mdrElement.getNames().getName60());
            fieldName120.setText(mdrElement.getNames().getName120());
        }

        if (mdrElement instanceof IMDElementWithSource) {
            MDElement mdElementSource = ((IMDElementWithSource)mdrElement).getMdElementSource();
            String sourceName = (mdElementSource != null) ? mdElementSource.getName() : "";
            if (mdElementSource instanceof MCDElement) {
                //sourceName = ((MCDElement) mdElementSource).getNamePath(MElement.SCOPESHORTNAME);
                sourceName = ((MCDElement) mdElementSource).getNameSource();
            }
            String sourceClassName = (mdElementSource != null) ? mdElementSource.getClass().getSimpleName() : "";
            fieldSource.setText(sourceClassName + " :  " + sourceName);
        }
    }

    @Override
    protected void saveDatas(MVCCDElement mvccdElement) {
        MDRElement mdrElement = (MDRElement) mvccdElement;
        mdrElement.setName(fieldName.getText());
    }

    @Override
    protected void enabledContent() {
        Preferences preferences = PreferencesManager.instance().preferences();
    }

}
