package screens;

import javax.swing.*;
import java.awt.*;

public class BaseScreen extends JFrame {

    private boolean showErrorsPanel;
    private String title;
    private String description;

    public BaseScreen(String title, boolean showErrorsPanel, String title1, String description) throws HeadlessException {
        super(title);
        this.showErrorsPanel = showErrorsPanel;
        this.title = title1;
        this.description = description;
    }


}
