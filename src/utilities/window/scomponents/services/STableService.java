package utilities.window.scomponents.services;

import utilities.window.scomponents.STable;

import javax.swing.*;


public class STableService {

    public static final int IDINDEX = 0;
    public static final int TRANSITORYINDEX = 1;
    public static final int ORDERINDEX = 2;

    public static Object[] getRecord(JTable table, int line){
        Object [] row = new Object[table.getColumnCount()];
        for (int col = 0 ; col <= table.getColumnCount() -1 ; col++){
            row[col] = table.getValueAt(line, col);
        }
        return row;
    }

    //TODO-1 Faire appel à findIndexRecordById
    public static Object[] findRecordById(STable table, int id){
        Object [] row = new Object[table.getRowCount()];
        for (int line = 0 ; line <= table.getRowCount() -1 ; line++){
            row = getRecord(table, line);
            if ((int) row[IDINDEX] == id){
                return row;
            }
        }
        return null;
    }

    public static Integer findIndexRecordById(STable table, int id){
        Object [] row = new Object[table.getRowCount()];
        for (int line = 0 ; line < table.getRowCount() ; line++){
            row = getRecord(table, line);
            if ((int) row[IDINDEX] == id){
                return line;
            }
        }
        return null;
    }



    public static boolean existRecordById(STable table, int id){
        return findRecordById(table, id) != null;
    }
}
