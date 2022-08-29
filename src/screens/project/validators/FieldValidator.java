package screens.project.validators;

import screens.fields.ScreenTextField;

public abstract class FieldValidator implements IFieldValidator {

    protected ScreenTextField field;

    public FieldValidator(ScreenTextField field) {
        this.field = field;
    }

    @Override
    public abstract boolean isValid();

}
