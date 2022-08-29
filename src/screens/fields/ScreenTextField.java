package screens.fields;

import screens.project.validators.IFieldValidator;

import javax.swing.*;

public class ScreenTextField extends JTextField {

    private IFieldValidator validator;

    public ScreenTextField(IFieldValidator validator) {
        super();
        this.validator = validator;
    }

    public ScreenTextField() {
    }

    public IFieldValidator getValidator() {
        return this.validator;
    }

    public void setValidator(IFieldValidator validator) {
        this.validator = validator;
    }
}
