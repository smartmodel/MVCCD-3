package window.editor.mdr.mpdr.model;

import console.IConsoleContentFrontEnd;
import window.editor.mdr.model.MDRModelButtonContent;

import javax.swing.*;

public class MPDRModelButtonContent extends MDRModelButtonContent implements IConsoleContentFrontEnd {

    public MPDRModelButtonContent(MPDRModelButtons mpdrModelButtons) {
        super(mpdrModelButtons);
    }

    @Override
    public JTextArea getTextArea() {
        return getMessages();
    }
}
