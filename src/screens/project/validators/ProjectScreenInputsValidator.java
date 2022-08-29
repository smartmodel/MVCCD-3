package screens.project.validators;

import preferences.Preferences;
import screens.fields.ScreenTextField;
import screens.utils.RegexMatcher;

public class ProjectScreenInputsValidator extends FieldValidator {


    public ProjectScreenInputsValidator(ScreenTextField field) {
        super(field);
    }

    @Override
    public boolean isValid() {
        String projectName = this.field.getText();
        String regex = Preferences.UI_SCREEN_CREATE_PROJECT_NAME_INPUT_REGEX;

        return RegexMatcher.matchesRegex(regex, projectName);
    }
}
