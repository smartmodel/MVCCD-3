package screens;

import java.util.ArrayList;
import java.util.List;

public abstract class ScreenModel {
    private List<String> errors;

    public ScreenModel() {
        this.errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }
}
