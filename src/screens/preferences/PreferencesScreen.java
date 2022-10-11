/*
 * Created by JFormDesigner on Tue Sep 13 09:35:11 CEST 2022
 */

package screens.preferences;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Christophe Francillon
 */
public class PreferencesScreen extends JFrame{
    public PreferencesScreen() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        tabbedPanel = new JTabbedPane();
        panel3 = new JPanel();
        panel2 = new JPanel();
        vSpacer1 = new JPanel(null);
        vSpacer2 = new JPanel(null);
        label2 = new JLabel();
        checkBox1 = new JCheckBox();
        label3 = new JLabel();
        checkBox2 = new JCheckBox();
        label4 = new JLabel();
        comboBox1 = new JComboBox();
        label5 = new JLabel();
        comboBox2 = new JComboBox();

        //======== this ========
        setTitle("Pr\u00e9f\u00e9rences");
        setResizable(false);
        setMinimumSize(new Dimension(550, 400));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

                //======== tabbedPanel ========
                {

                    //======== panel3 ========
                    {
                        panel3.setLayout(new GridLayout(0, 2));
                    }
                    tabbedPanel.addTab("Projet", panel3);

                    //======== panel2 ========
                    {
                        panel2.setLayout(new GridLayout(0, 2));
                        panel2.add(vSpacer1);
                        panel2.add(vSpacer2);

                        //---- label2 ----
                        label2.setText("Afficher la grille :");
                        panel2.add(label2);
                        panel2.add(checkBox1);

                        //---- label3 ----
                        label3.setText("Sauvegarde s\u00e9rialis\u00e9e :");
                        panel2.add(label3);
                        panel2.add(checkBox2);

                        //---- label4 ----
                        label4.setText("Niveau de message de la console :");
                        panel2.add(label4);
                        panel2.add(comboBox1);

                        //---- label5 ----
                        label5.setText("Mode de connexion base de donn\u00e9es :");
                        panel2.add(label5);
                        panel2.add(comboBox2);
                    }
                    tabbedPanel.addTab("Application", panel2);
                }
                contentPanel.add(tabbedPanel);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JTabbedPane tabbedPanel;
    private JPanel panel3;
    private JPanel panel2;
    private JPanel vSpacer1;
    private JPanel vSpacer2;
    private JLabel label2;
    private JCheckBox checkBox1;
    private JLabel label3;
    private JCheckBox checkBox2;
    private JLabel label4;
    private JComboBox comboBox1;
    private JLabel label5;
    private JComboBox comboBox2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
