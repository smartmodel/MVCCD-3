package window.editor.mdr.mpdr.view;

import main.MVCCDElement;
import mpdr.tapis.MPDRView;
import utilities.window.scomponents.STextArea;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import javax.swing.*;
import java.awt.*;

public class MPDRViewInputContent extends PanelInputContentIdMDR {

    private JLabel labelSQLDDL;
    private STextArea fieldSQLDDL;
    
    
    public MPDRViewInputContent(MPDRViewInput MPDRViewInput)     {
        super(MPDRViewInput);
    }

    public MPDRViewInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();

        labelSQLDDL = new JLabel("SQL-DDL : ");
        fieldSQLDDL = new STextArea(this, labelSQLDDL);
        //fieldSQLDDL.setPreferredSize((new Dimension(400, Preferences.EDITOR_FIELD_HEIGHT)));

        super.getSComponents().add(fieldSQLDDL);
        
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
        panelInputContentCustom.add(labelSQLDDL,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldSQLDDL, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        this.add(panelInputContentCustom);
    }



    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MPDRView mpdrView = (MPDRView) mvccdElementCrt;
        super.loadDatas(mpdrView);

        //fieldSQLDDL.restartChange();
        fieldSQLDDL.setText(mpdrView.generateSQLDDL());
        //fieldSQLDDL.setText("");

    }



}
