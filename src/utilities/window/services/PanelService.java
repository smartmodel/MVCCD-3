package utilities.window.services;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanelService {

    public static GridBagConstraints createGridBagConstraints(JPanel panel) {
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        return gbc;
    }


    public static GridBagConstraints createSubPanelGridBagConstraints(JPanel panel, String title) {

        Border border = BorderFactory.createLineBorder(Color.black);

        TitledBorder titleBorder = BorderFactory.createTitledBorder(border, title);
        panel.setBorder(titleBorder);

        return createGridBagConstraints(panel);
    }
}
