package window.editor.mdr.mpdr.sequence;

import main.MVCCDElement;
import mpdr.MPDRSequence;
import preferences.Preferences;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import javax.swing.*;
import java.awt.*;

public class MPDRSequenceInputContent extends PanelInputContentIdMDR {

    private JLabel labelColumnParent;
    private STextField fieldColumnParent;
    private JLabel labelMinValue;
    private STextField fieldMinValue;

    public MPDRSequenceInputContent(MPDRSequenceInput MPDRSequenceInput)     {
        super(MPDRSequenceInput);
    }

    public MPDRSequenceInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        labelColumnParent = new JLabel("Colonne parent : ");
        fieldColumnParent = new STextField(this, labelColumnParent);
        fieldColumnParent.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));

        labelMinValue = new JLabel("Valeur minimale : ");
        fieldMinValue = new STextField(this, labelMinValue);
        fieldMinValue.setPreferredSize((new Dimension(50, Preferences.EDITOR_FIELD_HEIGHT)));


        super.getSComponents().add(fieldColumnParent);
        super.getSComponents().add(fieldMinValue);

        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 6;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);


        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelColumnParent,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldColumnParent, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelMinValue,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldMinValue, gbc);

        this.add(panelInputContentCustom);
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MPDRSequence mpdrSequence= (MPDRSequence) mvccdElementCrt;
        super.loadDatas(mpdrSequence);

        String datatypeName = "";
        fieldColumnParent.setText(mpdrSequence.getMPDRColumnParent().getNamePath());
        fieldMinValue.setText(mpdrSequence.getMinValue());

    }



}
