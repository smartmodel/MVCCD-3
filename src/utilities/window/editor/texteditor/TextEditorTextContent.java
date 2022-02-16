package utilities.window.editor.texteditor;

import utilities.window.PanelContent;
import utilities.window.editor.DialogEditor;
import utilities.window.scomponents.IPanelInputContent;
import utilities.window.scomponents.STextArea;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextEditorTextContent extends PanelContent implements IPanelInputContent, DocumentListener {

    private STextArea textArea;
    private TextEditorText textEditorText;


    private boolean dataInitialized;

    public TextEditorTextContent(TextEditorText textEditorText) {
        super(textEditorText);
        this.textEditorText = textEditorText;
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new STextArea(this);
        textArea.getDocument().addDocumentListener(this);
        super.addContent(textArea);
    }

    public STextArea getTextArea() {
        return textArea;
    }

    public void setDataInitialized(boolean dataInitialized) {
        this.dataInitialized = dataInitialized;
        // Le panneau n'hérite pas de PanelInputContent !
        if (dataInitialized){
            textArea.restartChange();
        }
    }

    @Override
    public boolean isDataInitialized() {
        return dataInitialized;
    }

    // pas utile car lié à l'utilisation de l'interface IPanelInputContent !!!
    @Override
    public DialogEditor getEditor() {
        return null;
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        textEditorText.getTextEditor().enabledButtons();
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        textEditorText.getTextEditor().enabledButtons();
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        textEditorText.getTextEditor().enabledButtons();
    }
}
