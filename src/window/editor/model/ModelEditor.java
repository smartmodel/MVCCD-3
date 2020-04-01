package window.editor.model;

import mcd.MCDElement;
import mcd.MCDModel;
import mcd.MCDModels;
import mcd.interfaces.IMCDTraceability;
import newEditor.DialogEditor;
import preferences.Preferences;

import java.awt.*;

public class ModelEditor extends DialogEditor {
    public static final int MODEL = 1 ;
    public static final int PACKAGE = 2 ;

    private int scope;

    public ModelEditor(Window owner,
                       MCDElement parent,
                       MCDElement mcdElement,
                       String mode,
                       int scope            )  {
        super(owner, parent, mcdElement, mode);
        this.scope = scope;

        System.out.println("Initialisation de scope :  " + scope);

        super.setSize(Preferences.MODEL_WINDOW_WIDTH, Preferences.MODEL_WINDOW_HEIGHT);
        super.setInput(new ModelInput(this));
        super.setButtons (new ModelButtons(this));

        super.start();
    }

    @Override
    protected String getPropertyTitleNew() {
        System.out.println(scope);
        if (scope == MODEL) {
            return "editor.model.new";
        }
        if (scope == PACKAGE) {
            return "editor.package.new";
        }

        return null;
    }

    @Override
    protected String getPropertyTitleUpdate() {
        if (scope == MODEL) {
            return "editor.model.update";
        }
        if (scope == PACKAGE) {
            return "editor.package.update";
        }
        return null;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
