package AttributesGrid;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.List;

public class AutoTextField extends JTextField {

    private static final long serialVersionUID = 7695414681311202338L;

    class AutoDocument extends PlainDocument {

        public void replace(int i, int j, String s, AttributeSet attributeset)
                throws BadLocationException {
            super.remove(i, j);
            insertString(i, s, attributeset);
        }

        public void insertString(int i, String s, AttributeSet attributeset)
                throws BadLocationException {
            if (s == null || "".equals(s))
                return;
            String s1 = getText(0, i);
            String s2 = getMatch(s1 + s);
            int j = (i + s.length()) - 1;
            if (isStrict && s2 == null) {
                s2 = getMatch(s1);
                j--;
            } else if (!isStrict && s2 == null) {
                super.insertString(i, s, attributeset);
                return;
            }
            if (autoComboBox != null && s2 != null)
                autoComboBox.setSelectedValue(s2);
            super.remove(0, getLength());
            super.insertString(0, s2, attributeset);
            setSelectionStart(j + 1);
            setSelectionEnd(getLength());
        }

        public void remove(int i, int j) throws BadLocationException {
            int k = getSelectionStart();
            if (k > 0)
                k--;
            String s = getMatch(getText(0, k));
            if (!isStrict && s == null) {
                super.remove(i, j);
            } else {
                super.remove(0, getLength());
                super.insertString(0, s, null);
            }
            if (autoComboBox != null && s != null)
                autoComboBox.setSelectedValue(s);
            try {
                setSelectionStart(k);
                setSelectionEnd(getLength());
            } catch (Exception exception) {
            }
        }
    }

    AutoTextField(List list, AutoComboBox b) {
        isCaseSensitive = false;
        isStrict = true;
        autoComboBox = null;
        if (list == null) {
            throw new IllegalArgumentException("values can not be null");
        } else {
            dataList = list;
            autoComboBox = b;
            init();
        }
    }

    private void init() {
        setDocument(new AutoDocument());
        if (isStrict && dataList.size() > 0)
            setText(dataList.get(0).toString());
    }

    private String getMatch(String s) {
        for (int i = 0; i < dataList.size(); i++) {
            String s1 = dataList.get(i).toString();
            if (s1 != null) {
                if (!isCaseSensitive
                        && s1.toLowerCase().startsWith(s.toLowerCase()))
                    return s1;
                if (isCaseSensitive && s1.startsWith(s))
                    return s1;
            }
        }

        return null;
    }

    public void replaceSelection(String s) {
        AutoDocument _lb = (AutoDocument) getDocument();
        if (_lb != null)
            try {
                int i = Math.min(getCaret().getDot(), getCaret().getMark());
                int j = Math.max(getCaret().getDot(), getCaret().getMark());
                _lb.replace(i, j - i, s, null);
            } catch (Exception exception) {
            }
    }

    private List dataList;

    private boolean isCaseSensitive;

    private boolean isStrict;

    private AutoComboBox autoComboBox;

}