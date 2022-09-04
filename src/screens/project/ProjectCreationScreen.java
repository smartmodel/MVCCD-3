/*
 * Created by JFormDesigner on Mon Sep 05 00:31:11 CEST 2022
 */

package screens.project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Christophe Francillon
 */
public class ProjectCreationScreen extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel4;
    private JPanel informationsPanel;
    private JLabel label5;
    private JLabel label6;
    private JPanel fieldsPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel profilLabel;
    private JComboBox profilCombobox;
    private JLabel multiplesMCDModelsLabel;
    private JCheckBox multiplesMCDModelsCheckbox;
    private JLabel multiplesPackagesLabel;
    private JCheckBox multiplesPackagesCheckbox;
    private JPanel vSpacer2;
    private JPanel errorsPanel;
    private JPanel vSpacer3;
    private JPanel buttonsPanel;
    private JButton button1;
    private JButton createProjectButton;
    public ProjectCreationScreen() {
        this.initComponents();
    }

    public JTextField getNameTextField() {
        return this.nameTextField;
    }

    public JComboBox getProfilCombobox() {
        return this.profilCombobox;
    }

    public JCheckBox getMultiplesMCDModelsCheckbox() {
        return this.multiplesMCDModelsCheckbox;
    }

    public JCheckBox getMultiplesPackagesCheckbox() {
        return this.multiplesPackagesCheckbox;
    }

    public JPanel getErrorsPanel() {
        return this.errorsPanel;
    }

    public JButton getButton1() {
        return this.button1;
    }

    public JButton getCreateProjectButton() {
        return this.createProjectButton;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        this.panel4 = new JPanel();
        this.informationsPanel = new JPanel();
        this.label5 = new JLabel();
        this.label6 = new JLabel();
        this.fieldsPanel = new JPanel();
        this.nameLabel = new JLabel();
        this.nameTextField = new JTextField();
        this.profilLabel = new JLabel();
        this.profilCombobox = new JComboBox();
        this.multiplesMCDModelsLabel = new JLabel();
        this.multiplesMCDModelsCheckbox = new JCheckBox();
        this.multiplesPackagesLabel = new JLabel();
        this.multiplesPackagesCheckbox = new JCheckBox();
        this.vSpacer2 = new JPanel(null);
        this.errorsPanel = new JPanel();
        this.vSpacer3 = new JPanel(null);
        this.buttonsPanel = new JPanel();
        this.button1 = new JButton();
        this.createProjectButton = new JButton();

        //======== this ========
        this.setTitle("Cr\u00e9ation d'un projet");
        this.setMinimumSize(new Dimension(525, 0));
        this.setBackground(new Color(242, 242, 242));
        this.setResizable(false);
        var contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel4 ========
        {
            this.panel4.setBorder(new EmptyBorder(10, 10, 10, 10));
            this.panel4.setAlignmentY(4.5F);
            this.panel4.setPreferredSize(new Dimension(374, 370));
            this.panel4.setMinimumSize(new Dimension(400, 370));
            this.panel4.setLayout(new BoxLayout(this.panel4, BoxLayout.Y_AXIS));

            //======== informationsPanel ========
            {
                this.informationsPanel.setLayout(new GridLayout(2, 1));

                //---- label5 ----
                this.label5.setText("Cr\u00e9ation d'un projet");
                this.label5.setFont(this.label5.getFont().deriveFont(this.label5.getFont().getStyle() | Font.BOLD, this.label5.getFont().getSize() + 8f));
                this.informationsPanel.add(this.label5);

                //---- label6 ----
                this.label6.setText("Description de la page.");
                this.informationsPanel.add(this.label6);
            }
            this.panel4.add(this.informationsPanel);

            //======== fieldsPanel ========
            {
                this.fieldsPanel.setMinimumSize(new Dimension(354, 140));
                this.fieldsPanel.setPreferredSize(new Dimension(354, 105));
                this.fieldsPanel.setRequestFocusEnabled(false);
                this.fieldsPanel.setLayout(new GridLayout(0, 2, 0, 10));

                //---- nameLabel ----
                this.nameLabel.setText("Nom :");
                this.nameLabel.setFont(this.nameLabel.getFont().deriveFont(this.nameLabel.getFont().getStyle() & ~Font.BOLD));
                this.nameLabel.setIcon(new ImageIcon(this.getClass().getResource("/screens/icons/informations.png")));
                this.nameLabel.setIconTextGap(5);
                this.fieldsPanel.add(this.nameLabel);
                this.fieldsPanel.add(this.nameTextField);

                //---- profilLabel ----
                this.profilLabel.setText("Profil :");
                this.profilLabel.setFont(this.profilLabel.getFont().deriveFont(this.profilLabel.getFont().getStyle() & ~Font.BOLD));
                this.fieldsPanel.add(this.profilLabel);
                this.fieldsPanel.add(this.profilCombobox);

                //---- multiplesMCDModelsLabel ----
                this.multiplesMCDModelsLabel.setText("Multiples mod\u00e8les MCD :");
                this.multiplesMCDModelsLabel.setFont(this.multiplesMCDModelsLabel.getFont().deriveFont(this.multiplesMCDModelsLabel.getFont().getStyle() & ~Font.BOLD));
                this.fieldsPanel.add(this.multiplesMCDModelsLabel);
                this.fieldsPanel.add(this.multiplesMCDModelsCheckbox);

                //---- multiplesPackagesLabel ----
                this.multiplesPackagesLabel.setText("Multiples paquetages autoris\u00e9s :");
                this.multiplesPackagesLabel.setFont(this.multiplesPackagesLabel.getFont().deriveFont(this.multiplesPackagesLabel.getFont().getStyle() & ~Font.BOLD));
                this.fieldsPanel.add(this.multiplesPackagesLabel);
                this.fieldsPanel.add(this.multiplesPackagesCheckbox);
            }
            this.panel4.add(this.fieldsPanel);
            this.panel4.add(this.vSpacer2);

            //======== errorsPanel ========
            {
                this.errorsPanel.setPreferredSize(new Dimension(500, 60));
                this.errorsPanel.setBackground(Color.white);
                this.errorsPanel.setMinimumSize(new Dimension(500, 200));
                this.errorsPanel.setMaximumSize(new Dimension(500, 200));
                this.errorsPanel.setLayout(new FlowLayout());
            }
            this.panel4.add(this.errorsPanel);
            this.panel4.add(this.vSpacer3);

            //======== buttonsPanel ========
            {
                this.buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                //---- button1 ----
                this.button1.setText("Annuler");
                this.button1.setBorderPainted(false);
                this.buttonsPanel.add(this.button1);

                //---- createProjectButton ----
                this.createProjectButton.setText("Cr\u00e9er le projet");
                this.createProjectButton.setBackground(new Color(51, 153, 255));
                this.createProjectButton.setForeground(Color.white);
                this.createProjectButton.setMinimumSize(new Dimension(108, 30));
                this.createProjectButton.setPreferredSize(new Dimension(108, 30));
                this.createProjectButton.setBorderPainted(false);
                this.buttonsPanel.add(this.createProjectButton);
            }
            this.panel4.add(this.buttonsPanel);
        }
        contentPane.add(this.panel4);
        this.pack();
        this.setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
