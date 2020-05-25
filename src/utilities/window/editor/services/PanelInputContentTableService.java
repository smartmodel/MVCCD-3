package utilities.window.editor.services;

import preferences.PreferencesManager;
import utilities.window.scomponents.services.STableService;
import window.editor.operation.OperationParamTableColumn;

import javax.swing.*;
import java.util.Arrays;

public class PanelInputContentTableService {

    public static void genericColumnsDisplay(JTable table) {
        int sizeDebug = 0;
        if (PreferencesManager.instance().preferences().isDEBUG() &&
                PreferencesManager.instance().preferences().isDEBUG_SHOW_TABLE_COL_HIDDEN()) {
            sizeDebug = 30;
        }

        //Id
        table.getColumnModel().getColumn(STableService.IDINDEX).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(STableService.IDINDEX).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(STableService.IDINDEX).setMaxWidth(sizeDebug);

        //Transitory
        table.getColumnModel().getColumn(STableService.TRANSITORYINDEX).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(STableService.TRANSITORYINDEX).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(STableService.TRANSITORYINDEX).setMaxWidth(sizeDebug);

        //Order
        table.getColumnModel().getColumn(STableService.ORDERINDEX).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(STableService.ORDERINDEX).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(STableService.ORDERINDEX).setMaxWidth(sizeDebug);

    }

    public static String[] genericColumnsNames() {
        return  new String[]{
        OperationParamTableColumn.ID.getLabel(),
                OperationParamTableColumn.TRANSITORY.getLabel(),
                OperationParamTableColumn.ORDER.getLabel()
        };
    }

    public static String[] columnsNames(String[] specificColumnsNames) {

        String[] columnsNamesGeneric = genericColumnsNames();
        String[] columnsNamesSpecific = specificColumnsNames;

        String[] resultat = Arrays.copyOf(columnsNamesGeneric,
                columnsNamesGeneric.length + columnsNamesSpecific.length);

        System.arraycopy(columnsNamesSpecific, 0, resultat, columnsNamesGeneric.length, columnsNamesSpecific.length);

        return resultat;
    }




    }
