package AttributesGrid;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ErrorCellRenderer extends DefaultTableCellRenderer {
    JComponent component = new JTextField();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        // Insère la valeur introduite dans le champs de texte.
        ((JTextField) component).setText((String) value);
        // Remet la propriété JComponent.outline a null.
        component.putClientProperty("JComponent.outline", null);
        // Si rien n'est introduit alors met la propriété JComponent.outline a error
        if (value.equals(""))
            component.putClientProperty("JComponent.outline", "error");
        return component;
    }
}
