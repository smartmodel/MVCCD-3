package utilities.window.editor.services;

import preferences.PreferencesManager;
import window.editor.operation.OperationParamTableColumn;

import javax.swing.*;
import java.util.Arrays;

public class PanelInputContentTableService {

    public static void genericColumnsDisplay(JTable table) {
        int sizeDebug = 0;
        if (PreferencesManager.instance().preferences().isDEBUG() &&
                PreferencesManager.instance().preferences().isDEBUG_SHOW_TABLE_COL_HIDDEN()) {
            sizeDebug = 20;
        }

        //Id
        table.getColumnModel().getColumn(0).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(0).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(0).setMaxWidth(sizeDebug);

        //Transitory
        table.getColumnModel().getColumn(1).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(1).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(1).setMaxWidth(sizeDebug);

        //Order
        table.getColumnModel().getColumn(2).setPreferredWidth(sizeDebug);
        table.getColumnModel().getColumn(2).setMinWidth(sizeDebug);
        table.getColumnModel().getColumn(2).setMaxWidth(sizeDebug);

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
