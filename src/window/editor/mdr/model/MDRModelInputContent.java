package window.editor.mdr.model;

import main.MVCCDElement;
import mdr.MDRCaseFormat;
import mdr.MDRModel;
import mdr.MDRNamingLength;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;

public abstract class MDRModelInputContent extends PanelInputContent  {



    private JLabel labelName ;
    private STextField fieldName ;

    private JPanel panelNaming = new JPanel ();

    private JLabel labelNamingLengthFuture;
    private SComboBox fieldNamingLengthFuture;
    private JLabel labelNamingLengthActual;
    private STextField fieldNamingLengthActual;

    private JLabel labelNamingFormatFuture;
    protected SComboBox fieldNamingFormatFuture;
    private JLabel labelNamingFormatActual;
    private STextField fieldNamingFormatActual;

    private JLabel labelLastIteration;
    private STextField fieldLastIteration;



    public MDRModelInputContent(MDRModelInput MDRModelInput)     {
        super(MDRModelInput);
    }


    @Override
    protected void enabledContent() {

    }

    @Override
    public void createContentCustom() {


        labelName = new JLabel("Nom : ");
        fieldName = new STextField(this, labelName);
        fieldName.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldName.setReadOnly(true);

        // Nombre de caractères
        labelNamingLengthActual = new JLabel("Nbre de caractères en préférences");
        fieldNamingLengthActual = new STextField (this, labelNamingLengthActual);
        fieldNamingLengthActual.getDocument().addDocumentListener(this);
        fieldNamingLengthActual.addFocusListener(this);
        fieldNamingLengthActual.setReadOnly(true);

        labelNamingLengthFuture = new JLabel("Nbre de caractères propre au modèle");
        fieldNamingLengthFuture = new SComboBox(this, labelNamingLengthFuture);
        if (MDRNamingLength.LENGTH30.isRequired()){
            fieldNamingLengthFuture.addItem(MDRNamingLength.LENGTH30.getText());
        }
        if (MDRNamingLength.LENGTH60.isRequired()){
            fieldNamingLengthFuture.addItem(MDRNamingLength.LENGTH60.getText());
        }
        if (MDRNamingLength.LENGTH120.isRequired()){
            fieldNamingLengthFuture.addItem(MDRNamingLength.LENGTH120.getText());
        }

        fieldNamingLengthFuture.setToolTipText("Nombre de caractère maximale appliqué à tous les objets du modèle");
        fieldNamingLengthFuture.addItemListener(this);
        fieldNamingLengthFuture.addFocusListener(this);
        
        
        // Casse
        labelNamingFormatActual = new JLabel("Casse de caractères en préférence");
        fieldNamingFormatActual = new STextField (this, labelNamingFormatActual);
        fieldNamingFormatActual.getDocument().addDocumentListener(this);
        fieldNamingFormatActual.addFocusListener(this);
        fieldNamingFormatActual.setReadOnly(true);

        labelNamingFormatFuture = new JLabel("Casse de caractères propre au modèle");
        fieldNamingFormatFuture = new SComboBox(this, labelNamingFormatFuture);
        fieldNamingFormatFuture.addItem(MDRCaseFormat.NOTHING.getText());
        fieldNamingFormatFuture.addItem(MDRCaseFormat.UPPERCASE.getText());
        fieldNamingFormatFuture.addItem(MDRCaseFormat.LOWERCASE.getText());
        fieldNamingFormatFuture.addItem(MDRCaseFormat.CAPITALIZE.getText());


        fieldNamingFormatFuture.setToolTipText("Casse de caractères appliqué à tous les objets du modèles");
        fieldNamingFormatFuture.addItemListener(this);
        fieldNamingFormatFuture.addFocusListener(this);

        labelLastIteration = new JLabel("Dernière itération");
        fieldLastIteration = new STextField (this, labelLastIteration);
        fieldLastIteration.setReadOnly(true);
        fieldLastIteration.setToolTipText("Dernière itération de transformation");



        super.getSComponents().add(fieldName);
        super.getSComponents().add(fieldNamingLengthActual);
        super.getSComponents().add(fieldNamingLengthFuture);
        super.getSComponents().add(fieldNamingFormatActual);
        super.getSComponents().add(fieldNamingFormatFuture);
        super.getSComponents().add(fieldLastIteration);

    }

    @Override
    protected boolean changeField(DocumentEvent e) {
        return false;
    }

    @Override
    protected void changeFieldSelected(ItemEvent e) {

    }

    @Override
    protected void changeFieldDeSelected(ItemEvent e) {

    }

    protected void createPanelMaster(GridBagConstraints gbc) {

        panelInputContentCustom.add(labelName, gbc);
        gbc.gridx = 1;
        panelInputContentCustom.add(fieldName, gbc);

        createPanelNaming();

        gbc.gridwidth = 6;

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(panelNaming, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelLastIteration, gbc);
        gbc.gridx++ ;
        panelInputContentCustom.add(fieldLastIteration, gbc);


        this.add(panelInputContentCustom);
    }

    private void createPanelNaming() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelNaming,
                "Taille et écriture des noms des objets créés");

        panelNaming.add(labelNamingLengthActual, gbcA);
        gbcA.gridx++;
        panelNaming.add(fieldNamingLengthActual, gbcA);

        gbcA.gridx++;
        panelNaming.add(labelNamingLengthFuture, gbcA);
        gbcA.gridx++;
        panelNaming.add(fieldNamingLengthFuture, gbcA);

        gbcA.gridx = 0;
        gbcA.gridy++;
        panelNaming.add(labelNamingFormatActual, gbcA);
        gbcA.gridx++;
        panelNaming.add(fieldNamingFormatActual, gbcA);

        gbcA.gridx++;
        panelNaming.add(labelNamingFormatFuture, gbcA);
        gbcA.gridx++;
        panelNaming.add(fieldNamingFormatFuture, gbcA);
    }


    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRModel mdrModel = (MDRModel) mvccdElementCrt;

        fieldName.setText(mdrModel.getName());
        fieldNamingLengthActual.setText(mdrModel.getNamingLengthActual().getText());
        SComboBoxService.selectByText(fieldNamingLengthFuture, mdrModel.getNamingLengthFuture().getText());
        fieldNamingFormatActual.setText(mdrModel.getNamingFormatActual().getText());
        SComboBoxService.selectByText(fieldNamingFormatFuture, mdrModel.getNamingFormatFuture().getText());
        fieldLastIteration.setText(mdrModel.getIteration().intValue());
    }

    @Override
    protected void initDatas() {

    }


    @Override
    public void saveDatas(MVCCDElement mvccdElement) {
        MDRModel mdrModel = (MDRModel) mvccdElement;

        if (fieldNamingLengthFuture.checkIfUpdated()){
            String text = (String) fieldNamingLengthFuture.getSelectedItem();
            if (text.equals(MDRNamingLength.LENGTH30.getText())){
                mdrModel.setNamingLengthFuture(MDRNamingLength.LENGTH30);
            }
            if (text.equals(MDRNamingLength.LENGTH60.getText())){
                mdrModel.setNamingLengthFuture(MDRNamingLength.LENGTH60);
            }
            if (text.equals(MDRNamingLength.LENGTH120.getText())){
                mdrModel.setNamingLengthFuture(MDRNamingLength.LENGTH120);
            }
            // Conservation de la valeur des préférences
            //mdrModel.setNamingLengthActual(mdrModel.getNamingLengthFuture());
        }

        if (fieldNamingFormatFuture.checkIfUpdated()){
            String text = (String) fieldNamingFormatFuture.getSelectedItem();
            if (text.equals(MDRCaseFormat.NOTHING.getText())){
                mdrModel.setNamingFormatFuture(MDRCaseFormat.NOTHING);
            }
            if (text.equals(MDRCaseFormat.UPPERCASE.getText())){
                mdrModel.setNamingFormatFuture(MDRCaseFormat.UPPERCASE);
            }
            if (text.equals(MDRCaseFormat.LOWERCASE.getText())){
                mdrModel.setNamingFormatFuture(MDRCaseFormat.LOWERCASE);
            }
            if (text.equals(MDRCaseFormat.CAPITALIZE.getText())){
                mdrModel.setNamingFormatFuture(MDRCaseFormat.CAPITALIZE);
            }
            if (text.equals(MDRCaseFormat.LIKEBD.getText())){
                mdrModel.setNamingFormatFuture(MDRCaseFormat.LIKEBD);
            }
            // Conservation de la valeur des préférences
            //mdrModel.setNamingFormatActual(mdrModel.getNamingFormatFuture());
        }


        boolean c1 = fieldNamingLengthFuture.checkIfUpdated();
        boolean c2 = fieldNamingFormatFuture.checkIfUpdated();

        if (c1 || c2){
            mdrModel.adjustNaming();
        }


    }

    @Override
    public void loadSimulationChange(MVCCDElement mvccdElementCrt) {

    }

}
