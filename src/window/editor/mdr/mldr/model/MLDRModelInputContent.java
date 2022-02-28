package window.editor.mdr.mldr.model;

import main.MVCCDElement;
import mdr.MDRCaseFormat;
import mldr.MLDRModel;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;
import window.editor.mdr.model.MDRModelInputContent;

import javax.swing.*;
import java.awt.*;

public class MLDRModelInputContent extends MDRModelInputContent {


    private JPanel panelReservedWords = new JPanel ();
    private JLabel labelReservedWordsFormatFuture;
    protected SComboBox fieldReservedWordsFormatFuture;
    private JLabel labelReservedWordsFormatActual;
    private STextField fieldReservedWordsFormatActual;

    public MLDRModelInputContent(MLDRModelInput mldrModelInput) {
        super(mldrModelInput);
    }


    @Override
    public void createContentCustom() {
        super.createContentCustom();

        // Casse
        labelReservedWordsFormatActual = new JLabel("Casse de caractères en préférence");
        fieldReservedWordsFormatActual = new STextField (this, labelReservedWordsFormatActual);
        fieldReservedWordsFormatActual.getDocument().addDocumentListener(this);
        fieldReservedWordsFormatActual.addFocusListener(this);
        fieldReservedWordsFormatActual.setReadOnly(true);

        labelReservedWordsFormatFuture = new JLabel("Casse de caractères propre au modèle");
        fieldReservedWordsFormatFuture = new SComboBox(this, labelReservedWordsFormatFuture);
        fieldReservedWordsFormatFuture.addItem(MDRCaseFormat.NOTHING.getText());
        fieldReservedWordsFormatFuture.addItem(MDRCaseFormat.UPPERCASE.getText());
        fieldReservedWordsFormatFuture.addItem(MDRCaseFormat.LOWERCASE.getText());


        fieldReservedWordsFormatFuture.setToolTipText("Casse de caractères appliqué à tous les objets du modèle");
        fieldReservedWordsFormatFuture.addItemListener(this);
        fieldReservedWordsFormatFuture.addFocusListener(this);

        super.getSComponents().add(fieldReservedWordsFormatActual);
        super.getSComponents().add(fieldReservedWordsFormatFuture);

        createPanelMaster();
    }

    protected void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);
        super.createPanelMaster(gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        createPanelReservedWords();
        panelInputContentCustom.add(panelReservedWords, gbc);
        gbc.gridwidth = 1;

        this.add(panelInputContentCustom);
    }



    private void createPanelReservedWords() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelReservedWords,
                "Ecriture des mots réservés du modèle logique");

        panelReservedWords.add(labelReservedWordsFormatActual, gbcA);
        gbcA.gridx++;
        panelReservedWords.add(fieldReservedWordsFormatActual, gbcA);

        gbcA.gridx++;
        panelReservedWords.add(labelReservedWordsFormatFuture, gbcA);
        gbcA.gridx++;
        panelReservedWords.add(fieldReservedWordsFormatFuture, gbcA);
    }

    public void loadDatas(MVCCDElement mvccdElementCrt) {
        super.loadDatas(mvccdElementCrt);
        MLDRModel mldrModel = (MLDRModel) mvccdElementCrt;

        fieldReservedWordsFormatActual.setText(mldrModel.getReservedWordsFormatActual().getText());
        SComboBoxService.selectByText(fieldReservedWordsFormatFuture, mldrModel.getReservedWordsFormatActual().getText());
    }

    public void saveDatas(MVCCDElement mvccdElement) {
        super.saveDatas(mvccdElement);
        MLDRModel mldrModel = (MLDRModel) mvccdElement;

        if (fieldReservedWordsFormatFuture.checkIfUpdated()) {
            String text = (String) fieldReservedWordsFormatFuture.getSelectedItem();
            for (MDRCaseFormat mdrCaseFormat : MDRCaseFormat.values()) {
                if (text.equals(mdrCaseFormat.getText())) {
                    mldrModel.setReservedWordsFormatFuture(mdrCaseFormat);
                }
            }
        }
    }

}
