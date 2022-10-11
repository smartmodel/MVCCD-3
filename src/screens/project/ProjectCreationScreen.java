/*
 * Created by JFormDesigner on Mon Sep 12 21:17:27 CEST 2022
 */

package screens.project;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Christophe Francillon
 */
public class ProjectCreationScreen extends JFrame{
    public ProjectCreationScreen() {
        initComponents();
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JComboBox getProfilCombobox() {
        return profilCombobox;
    }

    public JCheckBox getMultiplesMCDModelsCheckbox() {
        return multiplesMCDModelsCheckbox;
    }

    public JCheckBox getMultiplesPackagesCheckbox() {
        return multiplesPackagesCheckbox;
    }

    public JPanel getErrorsPanel() {
        return errorsPanel;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getCreateProjectButton() {
        return createProjectButton;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        content = new JPanel();
        informationsPanel = new JPanel();
        title = new JLabel();
        description = new JLabel();
        verticalSpacerInformations = new JPanel(null);
        fieldsPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        profilLabel = new JLabel();
        profilCombobox = new JComboBox();
        multiplesMCDModelsLabel = new JLabel();
        multiplesMCDModelsCheckbox = new JCheckBox();
        multiplesPackagesLabel = new JLabel();
        multiplesPackagesCheckbox = new JCheckBox();
        verticalSpacerErrors = new JPanel(null);
        errorsPanel = new JPanel();
        verticalSpacerButtons = new JPanel(null);
        buttonsPanel = new JPanel();
        cancelButton = new JButton();
        createProjectButton = new JButton();

        //======== this ========
        setTitle("Cr\u00e9ation d'un projet");
        setMinimumSize(new Dimension(525, 0));
        setBackground(new Color(242, 242, 242));
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== content ========
        {
            content.setBorder(new EmptyBorder(10, 10, 10, 10));
            content.setAlignmentY(4.5F);
            content.setPreferredSize(new Dimension(374, 370));
            content.setMinimumSize(new Dimension(400, 370));
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

            //======== informationsPanel ========
            {
                informationsPanel.setLayout(new GridLayout(2, 1));

                //---- title ----
                title.setText("Cr\u00e9ation d'un projet");
                title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD, title.getFont().getSize() + 8f));
                informationsPanel.add(title);

                //---- description ----
                description.setText("Description de la page.");
                informationsPanel.add(description);
            }
            content.add(informationsPanel);
            content.add(verticalSpacerInformations);

            //======== fieldsPanel ========
            {
                fieldsPanel.setMinimumSize(new Dimension(354, 140));
                fieldsPanel.setPreferredSize(new Dimension(354, 105));
                fieldsPanel.setRequestFocusEnabled(false);
                fieldsPanel.setLayout(new GridLayout(0, 2, 0, 10));

                //---- nameLabel ----
                nameLabel.setText("Nom :");
                nameLabel.setFont(nameLabel.getFont().deriveFont(nameLabel.getFont().getStyle() & ~Font.BOLD));
                nameLabel.setIcon(new ImageIcon(getClass().getResource("/screens/icons/informations.png")));
                nameLabel.setIconTextGap(5);
                fieldsPanel.add(nameLabel);
                fieldsPanel.add(nameTextField);

                //---- profilLabel ----
                profilLabel.setText("Profil :");
                profilLabel.setFont(profilLabel.getFont().deriveFont(profilLabel.getFont().getStyle() & ~Font.BOLD));
                fieldsPanel.add(profilLabel);
                fieldsPanel.add(profilCombobox);

                //---- multiplesMCDModelsLabel ----
                multiplesMCDModelsLabel.setText("Multiples mod\u00e8les MCD :");
                multiplesMCDModelsLabel.setFont(multiplesMCDModelsLabel.getFont().deriveFont(multiplesMCDModelsLabel.getFont().getStyle() & ~Font.BOLD));
                fieldsPanel.add(multiplesMCDModelsLabel);
                fieldsPanel.add(multiplesMCDModelsCheckbox);

                //---- multiplesPackagesLabel ----
                multiplesPackagesLabel.setText("Multiples paquetages autoris\u00e9s :");
                multiplesPackagesLabel.setFont(multiplesPackagesLabel.getFont().deriveFont(multiplesPackagesLabel.getFont().getStyle() & ~Font.BOLD));
                fieldsPanel.add(multiplesPackagesLabel);
                fieldsPanel.add(multiplesPackagesCheckbox);
            }
            content.add(fieldsPanel);
            content.add(verticalSpacerErrors);

            //======== errorsPanel ========
            {
                errorsPanel.setPreferredSize(new Dimension(525, 75));
                errorsPanel.setBackground(Color.white);
                errorsPanel.setMinimumSize(new Dimension(525, 200));
                errorsPanel.setMaximumSize(new Dimension(525, 200));
                errorsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            }
            content.add(errorsPanel);
            content.add(verticalSpacerButtons);

            //======== buttonsPanel ========
            {
                buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                //---- cancelButton ----
                cancelButton.setText("Annuler");
                buttonsPanel.add(cancelButton);

                //---- createProjectButton ----
                createProjectButton.setText("Cr\u00e9er le projet");
                createProjectButton.setBackground(new Color(146, 218, 245));
                buttonsPanel.add(createProjectButton);
            }
            content.add(buttonsPanel);
        }
        contentPane.add(content);
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel content;
    private JPanel informationsPanel;
    private JLabel title;
    private JLabel description;
    private JPanel verticalSpacerInformations;
    private JPanel fieldsPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel profilLabel;
    private JComboBox profilCombobox;
    private JLabel multiplesMCDModelsLabel;
    private JCheckBox multiplesMCDModelsCheckbox;
    private JLabel multiplesPackagesLabel;
    private JCheckBox multiplesPackagesCheckbox;
    private JPanel verticalSpacerErrors;
    private JPanel errorsPanel;
    private JPanel verticalSpacerButtons;
    private JPanel buttonsPanel;
    private JButton cancelButton;
    private JButton createProjectButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
