/*
 * Created by JFormDesigner on Mon May 23 14:46:32 CEST 2022
 */

package consolidationMpdrDb.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class WaitingSyncViewer extends JFrame {
    public WaitingSyncViewer() {
        initComponents();
    }

    private void thisMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanelForImage = new JPanel();
        label1 = new JLabel();
        buttonBar = new JPanel();
        cancelButton = new JButton();

        //======== this ========
        setTitle("Synchronisation de la base de donn\u00e9es - En cours de chargement");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanelForImage ========
            {
                contentPanelForImage.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanelForImage.getLayout()).columnWidths = new int[] {66, 441, 0};
                ((GridBagLayout)contentPanelForImage.getLayout()).rowHeights = new int[] {120, 0};
                ((GridBagLayout)contentPanelForImage.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanelForImage.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("Synchronisation en cours de traitement");
                label1.setFont(new Font("Segoe UI", Font.BOLD, 12));
                label1.setIcon(new ImageIcon(getClass().getResource("/main/doc-files/synchronisation-des-donnees_60x60.png")));
                contentPanelForImage.add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanelForImage, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {80};

                //---- cancelButton ----
                cancelButton.setText("Annuler");
                buttonBar.add(cancelButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        setVisible(true);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanelForImage;
    private JLabel label1;
    private JPanel buttonBar;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
