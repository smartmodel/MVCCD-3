package AttributesGrid;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.List;

public class Java2sAutoTextField extends JTextField {
    private static final long serialVersionUID = 7695414681311202338L;
    private Java2sAutoComboBox autoComboBox;
    private List dataList;

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
            if (s2 == null) {
                s2 = getMatch(s1);
                j--;
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
            if (autoComboBox != null && s != null)
                autoComboBox.setSelectedValue(s);
            try {
                setSelectionStart(k);
                setSelectionEnd(getLength());
            } catch (Exception ignored) {}
        }

    }

    Java2sAutoTextField(List list, Java2sAutoComboBox b) {
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
        if (dataList.size() > 0)
            setText(dataList.get(0).toString());
    }

    private String getMatch(String s) {
        for (Object o : dataList) {
            String s1 = o.toString();
            if (s1 != null & s1.toLowerCase().startsWith(s.toLowerCase()))
                return s1;
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
            } catch (Exception ignored) {}
    }

    public void setDataList(List list) {
        if (list == null)
            throw new IllegalArgumentException("values can not be null");
        else
            dataList = list;
    }
}