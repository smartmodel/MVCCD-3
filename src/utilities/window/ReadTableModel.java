package utilities.window;

import javax.swing.table.DefaultTableModel;

public class ReadTableModel extends DefaultTableModel {

    public ReadTableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
