package screens.old.validators;

import screens.utils.RegexMatcher;

import javax.swing.*;

public class ProjectNameFieldValidator extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String regex = "^[a-zA-Z]{1,1}[a-zA-Z0-9_]*([a-zA-Z]{1,1})$";
        String projectName = ((JTextField) input).getText();

        return RegexMatcher.matchesRegex(regex, projectName);
    }
}
