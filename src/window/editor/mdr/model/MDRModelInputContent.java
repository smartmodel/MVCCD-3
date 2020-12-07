package window.editor.mdr.model;

import main.MVCCDElement;
import mdr.MDRModel;
import mdr.MDRNamingLength;
import utilities.Trace;
import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;
import utilities.window.scomponents.services.SComboBoxService;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import javax.swing.*;
import java.awt.*;

public class MDRModelInputContent extends PanelInputContentIdMDR {


    private JPanel panelNamingLength = new JPanel ();

    private JLabel labelNamingLengthFuture;
    private SComboBox fieldNamingLengthFuture;
    private JLabel labelNamingLengthActual;
    private STextField fieldNamingLengthActual;

    //TODO-1 Ajouter un bouton commande de réaffection des noms si Actel <> Future
    // Gérer une transaction pour annuler la réaffectation si les données du formulaire
    // ne sont pas sauvegardées


    public MDRModelInputContent(MDRModelInput MDRModelInput)     {
        super(MDRModelInput);
    }

    public MDRModelInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();


        labelNamingLengthActual = new JLabel("Actuel");
        fieldNamingLengthActual = new STextField (this, labelNamingLengthActual);
        fieldNamingLengthActual.getDocument().addDocumentListener(this);
        fieldNamingLengthActual.addFocusListener(this);
        fieldNamingLengthActual.setReadOnly(true);
        
        labelNamingLengthFuture = new JLabel("Futur");
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

        fieldNamingLengthFuture.setToolTipText("Taillle maximales des noms de tous les objets du modèle");
        fieldNamingLengthFuture.addItemListener(this);
        fieldNamingLengthFuture.addFocusListener(this);

        super.getSComponents().add(fieldNamingLengthActual);
        super.getSComponents().add(fieldNamingLengthFuture);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);

        createPanelNamingLength();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(panelNamingLength, gbc);
        
        this.add(panelInputContentCustom);
    }

    private void createPanelNamingLength() {
        GridBagConstraints gbcA = PanelService.createSubPanelGridBagConstraints(panelNamingLength,
                "Taille de nommage ");

        panelNamingLength.add(labelNamingLengthActual, gbcA);
        gbcA.gridx++;
        panelNamingLength.add(fieldNamingLengthActual, gbcA);

        gbcA.gridx++;
        panelNamingLength.add(labelNamingLengthFuture, gbcA);
        gbcA.gridx++;
        panelNamingLength.add(fieldNamingLengthFuture, gbcA);

    }


    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRModel mdrModel = (MDRModel) mvccdElementCrt;
        super.loadDatas(mdrModel);

        fieldNamingLengthActual.setText(mdrModel.getNamingLengthActual().getText());
        SComboBoxService.selectByText(fieldNamingLengthFuture, mdrModel.getNamingLengthFuture().getText());

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
            if (text.equals(MDRNamingLength.LENGTH60.getText())){
                mdrModel.setNamingLengthFuture(MDRNamingLength.LENGTH60);
            }
        }
    }




}
