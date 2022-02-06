package window.editor.mdr.check;

import main.MVCCDElement;
import mdr.MDRCheck;
import preferences.Preferences;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import javax.swing.*;
import java.awt.*;

public class MDRCheckInputContent extends PanelInputContentIdMDR {

    private JLabel labelExpression;
    private STextField fieldExpression;
    
    public MDRCheckInputContent(MDRCheckInput MDRCheckInput)     {
        super(MDRCheckInput);
    }

    public MDRCheckInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        labelExpression = new JLabel("Expression: ");
        fieldExpression = new STextField(this, labelExpression);
        fieldExpression.setPreferredSize((new Dimension(500, Preferences.EDITOR_FIELD_HEIGHT)));
        fieldExpression.setToolTipText("Expression de la contrainte de CHECK...");
        
        super.getSComponents().add(fieldExpression);
        
        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelExpression,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldExpression, gbc);
        
        this.add(panelInputContentCustom);
    }




    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRCheck mdrCheck = (MDRCheck) mvccdElementCrt;
        super.loadDatas(mdrCheck);
         fieldExpression.setText(mdrCheck.getMDRParameter().getValue());
    }



}
