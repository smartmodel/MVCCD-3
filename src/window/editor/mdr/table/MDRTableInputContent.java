package window.editor.mdr.table;

import m.MElement;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContEntities;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDContEntitiesService;
import mcd.services.MCDElementService;
import md.MDElement;
import mdr.MDRTable;
import preferences.Preferences;
import preferences.PreferencesManager;
import utilities.window.editor.PanelInputContent;
import utilities.window.editor.PanelInputContentId;
import utilities.window.scomponents.SCheckBox;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMPDR;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class MDRTableInputContent extends PanelInputContentIdMPDR {



    public MDRTableInputContent(MDRTableInput MDRTableInput)     {
        super(MDRTableInput);
    }

    public MDRTableInputContent(MVCCDElement element)     {
        super(element);
    }

    @Override
    public void createContentCustom() {
        super.createContentCustom();
        createPanelMaster();
    }

    private void createPanelMaster() {
        GridBagConstraints gbc = PanelService.createGridBagConstraints(panelInputContentCustom);

        gbc.gridwidth = 4;

        super.createPanelId();
        panelInputContentCustom.add(panelId, gbc);


        /*

        gbc.gridx = 0;
        gbc.gridy++;
        panelInputContentCustom.add(labelSource,gbc);
        gbc.gridx++;
        panelInputContentCustom.add(fieldSource, gbc);

         */

        this.add(panelInputContentCustom);
    }




    @Override
    public void loadDatas(MVCCDElement mvccdElementCrt) {
        MDRTable mdrTable = (MDRTable) mvccdElementCrt;
        super.loadDatas(mdrTable);
    }



}
