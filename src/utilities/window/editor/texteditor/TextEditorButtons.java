package utilities.window.editor.texteditor;

import utilities.window.PanelBorderLayout;
import utilities.window.PanelBorderLayoutResizer;

public class TextEditorButtons extends PanelBorderLayout  {

    private TextEditorButtonsContent textEditorButtonsContent;
    private TextEditor textEditor;

    public TextEditorButtons(TextEditor textEditor, String borderLayoutPosition, PanelBorderLayoutResizer panelBLResizer) {
        super();
        super.setBorderLayoutPosition(borderLayoutPosition);
        super.setPanelBLResizer(panelBLResizer);
        this.textEditor = textEditor;
        startLayout();

        textEditorButtonsContent = new TextEditorButtonsContent(this);
        super.setPanelContent(textEditorButtonsContent);

    }

    public TextEditor getTextEditor() {
        return textEditor;
    }

    public TextEditorButtonsContent getTextEditorButtonsContent() {
        return textEditorButtonsContent;
    }


  }
