/*
 * Created by JFormDesigner on Tue May 17 11:40:18 CEST 2022
 */

package comparatorsql.viewer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class ListeOperationsViewer extends JFrame {
/*
    public void exectuteViewer(){
        JFrame frame = new JFrame("Liste des opérations");
        frame.setContentPane(new ListeOperationsViewer());
        frame.pack();
        frame.setVisible(true);
    }
*/
    public ListeOperationsViewer() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        panelDonneesConnexion2 = new JPanel();
        modeleSource2 = new JLabel();
        modeleSourceVar2 = new JTextField();
        nomSchema2 = new JLabel();
        nomSchemaVar2 = new JTextField();
        nomConnexion2 = new JLabel();
        nomConnexionVar2 = new JTextField();
        contentPanel = new JPanel();
        textComparaison = new JLabel();
        scrollPane1 = new JScrollPane();
        tableListeOperations = new JTable();
        buttonBar = new JPanel();
        textInformation = new JLabel();
        btnFermer = new JButton();
        button1 = new JButton();
        btnSuivant = new JButton();

        //======== this ========
        setTitle("Synchronisation de la base de donn\u00e9es - Liste des op\u00e9rations");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setPreferredSize(new Dimension(700, 700));
            dialogPane.setFont(new Font("Arial", Font.PLAIN, 13));
            dialogPane.setLayout(new BorderLayout());

            //======== panelDonneesConnexion2 ========
            {
                panelDonneesConnexion2.setBorder(LineBorder.createBlackLineBorder());
                panelDonneesConnexion2.setFont(new Font("Arial", Font.PLAIN, 13));
                panelDonneesConnexion2.setLayout(new GridBagLayout());
                ((GridBagLayout)panelDonneesConnexion2.getLayout()).columnWidths = new int[] {139, 203, 106, 209, 0};
                ((GridBagLayout)panelDonneesConnexion2.getLayout()).rowHeights = new int[] {40, 35, 0};
                ((GridBagLayout)panelDonneesConnexion2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panelDonneesConnexion2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                //---- modeleSource2 ----
                modeleSource2.setText("Mod\u00e8le source :");
                modeleSource2.setFont(new Font("Arial", Font.PLAIN, 13));
                panelDonneesConnexion2.add(modeleSource2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(5, 5, 5, 5), 0, 0));

                //---- modeleSourceVar2 ----
                modeleSourceVar2.setText("MPDR_perso");
                modeleSourceVar2.setFont(new Font("Arial", Font.BOLD, 13));
                modeleSourceVar2.setEditable(false);
                panelDonneesConnexion2.add(modeleSourceVar2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(5, 0, 5, 5), 0, 0));

                //---- nomSchema2 ----
                nomSchema2.setText("Nom du sch\u00e9ma :");
                nomSchema2.setFont(new Font("Arial", Font.PLAIN, 13));
                panelDonneesConnexion2.add(nomSchema2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(5, 0, 5, 5), 0, 0));

                //---- nomSchemaVar2 ----
                nomSchemaVar2.setText("VINCENT_CHASSOT");
                nomSchemaVar2.setFont(new Font("Arial", Font.BOLD, 13));
                nomSchemaVar2.setEditable(false);
                panelDonneesConnexion2.add(nomSchemaVar2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(5, 0, 5, 5), 0, 0));

                //---- nomConnexion2 ----
                nomConnexion2.setText("Nom de la connexion :");
                nomConnexion2.setFont(new Font("Arial", Font.PLAIN, 13));
                panelDonneesConnexion2.add(nomConnexion2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 5, 5), 0, 0));

                //---- nomConnexionVar2 ----
                nomConnexionVar2.setText("ECRISOFT");
                nomConnexionVar2.setFont(new Font("Arial", Font.BOLD, 13));
                nomConnexionVar2.setEditable(false);
                panelDonneesConnexion2.add(nomConnexionVar2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
            }
            dialogPane.add(panelDonneesConnexion2, BorderLayout.NORTH);

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {323, 37, 302, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 42, 270, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //---- textComparaison ----
                textComparaison.setText("Liste des op\u00e9rations");
                textComparaison.setFont(new Font("Arial", Font.PLAIN, 13));
                contentPanel.add(textComparaison, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollPane1 ========
                {

                    //---- tableListeOperations ----
                    tableListeOperations.setFont(new Font("Arial", Font.PLAIN, 13));
                    scrollPane1.setViewportView(tableListeOperations);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {391, 96, 87, 85, 0};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- textInformation ----
                textInformation.setText("*Information : Les packages et d\u00e9clencheurs ne sont pas list\u00e9s");
                textInformation.setFont(textInformation.getFont().deriveFont(textInformation.getFont().getStyle() | Font.ITALIC));
                buttonBar.add(textInformation, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnFermer ----
                btnFermer.setText("Fermer");
                buttonBar.add(btnFermer, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- button1 ----
                button1.setText("Retour");
                buttonBar.add(button1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnSuivant ----
                btnSuivant.setText("Suivant");
                buttonBar.add(btnSuivant, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel panelDonneesConnexion2;
    private JLabel modeleSource2;
    private JTextField modeleSourceVar2;
    private JLabel nomSchema2;
    private JTextField nomSchemaVar2;
    private JLabel nomConnexion2;
    private JTextField nomConnexionVar2;
    private JPanel contentPanel;
    private JLabel textComparaison;
    private JScrollPane scrollPane1;
    private JTable tableListeOperations;
    private JPanel buttonBar;
    private JLabel textInformation;
    private JButton btnFermer;
    private JButton button1;
    private JButton btnSuivant;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
