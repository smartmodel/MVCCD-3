package AttributesGrid;

import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class AutoComboBox extends JComboBox {

    private class AutoTextFieldEditor extends BasicComboBoxEditor {
        AutoTextFieldEditor(java.util.List list) {
            editor = new AutoTextField(list, AutoComboBox.this);
        }
    }

    public AutoComboBox(java.util.List list) {
        isFired = false;
        autoTextFieldEditor = new AutoTextFieldEditor(list);
        setEditable(true);
        setModel(new DefaultComboBoxModel(list.toArray()) {

            protected void fireContentsChanged(Object obj, int i, int j) {
                if (!isFired)
                    super.fireContentsChanged(obj, i, j);
            }

        });
        setEditor(autoTextFieldEditor);
    }

    void setSelectedValue(Object obj) {
        if (isFired) {
            return;
        } else {
            isFired = true;
            setSelectedItem(obj);
            fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder,
                    1));
            isFired = false;
            return;
        }
    }

    protected void fireActionEvent() {
        if (!isFired)
            super.fireActionEvent();
    }

    private AutoTextFieldEditor autoTextFieldEditor;

    private boolean isFired;

}