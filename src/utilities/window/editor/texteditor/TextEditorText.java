package utilities.window.editor.texteditor;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class TextEditorText extends PanelBorderLayout {

    private TextEditorTextContent textEditorTextContent;
    private TextEditor textEditor;

    public TextEditorText(TextEditor textEditor, String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        this.textEditor = textEditor;
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        startLayout();

        textEditorTextContent = new TextEditorTextContent(this);
        super.setPanelContent(textEditorTextContent);

    }


    public TextEditorTextContent getTextEditorTextContent() {
        return textEditorTextContent;
    }

    public TextEditor getTextEditor() {
        return textEditor;
    }
}
