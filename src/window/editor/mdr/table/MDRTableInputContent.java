package window.editor.mdr.table;

import main.MVCCDElement;
import mdr.MDRTable;
import utilities.window.services.PanelService;
import window.editor.mdr.utilities.PanelInputContentIdMDR;

import java.awt.*;

public class MDRTableInputContent extends PanelInputContentIdMDR {



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
