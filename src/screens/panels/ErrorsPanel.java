package screens.panels;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorsPanel extends JPanel {

    List<InputError> errors;

    public ErrorsPanel() {
        super();
        this.errors = new ArrayList<>();
    }

    public void addError(InputError error) {
        this.errors.add(error);
        this.displayErrorsAsBulletList();
    }

    public void deleteError(String text) {
        this.errors = this.errors.stream().filter(e -> !e.getText().equals(text)).collect(Collectors.toList());
        this.displayErrorsAsBulletList();
    }

    public void displayErrorsAsBulletList() {
        // Crée une liste à puce contenant les erreurs
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        this.errors.forEach(e -> {
            sb.append("<li>");
            sb.append(e.getText());
            sb.append("</li>");
        });
        sb.append("</html>");

        // Recompose la liste à puce dans le panel
        this.removeAll();
        this.add(new JLabel(sb.toString()));
    }
}
