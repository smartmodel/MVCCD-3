/*
 * Created by JFormDesigner on Tue May 17 10:05:25 CEST 2022
 */

package consolidationMpdrDb.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class ComparaisonViewer extends JFrame {
    public ComparaisonViewer() {
        initComponents();
    }

    private void tree_SGBDRMouseClicked(MouseEvent e) {
        //
    }

    private void tree_MPDRMouseClicked(MouseEvent e) {
        //
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panelDonneesConnexion = new JPanel();
        modeleSource = new JLabel();
        modeleSourceVar = new JTextField();
        nomSchema = new JLabel();
        nomSchemaVar = new JTextField();
        nomConnexion = new JLabel();
        nomConnexionVar = new JTextField();
        textComparaison = new JLabel();
        scrollPaneGauche = new JScrollPane();
        treeMPDR = new JTree();
        scrollPaneDroite = new JScrollPane();
        treeSGBDR = new JTree();
        buttonBar = new JPanel();
        textInformation = new JLabel();
        btnFermer = new JButton();
        btnSuivant = new JButton();

        //======== this ========
        setTitle("Synchronisation de la base de donn\u00e9es - Comparaison");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setPreferredSize(new Dimension(700, 700));
            dialogPane.setFont(new Font("Arial", Font.PLAIN, 13));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {323, 37, 302, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 42, 309, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //======== panelDonneesConnexion ========
                {
                    panelDonneesConnexion.setBorder(LineBorder.createBlackLineBorder());
                    panelDonneesConnexion.setFont(new Font("Arial", Font.PLAIN, 13));
                    panelDonneesConnexion.setLayout(new GridBagLayout());
                    ((GridBagLayout)panelDonneesConnexion.getLayout()).columnWidths = new int[] {139, 203, 106, 209, 0};
                    ((GridBagLayout)panelDonneesConnexion.getLayout()).rowHeights = new int[] {40, 35, 0};
                    ((GridBagLayout)panelDonneesConnexion.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                    ((GridBagLayout)panelDonneesConnexion.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                    //---- modeleSource ----
                    modeleSource.setText("Mod\u00e8le source :");
                    modeleSource.setFont(new Font("Arial", Font.PLAIN, 13));
                    panelDonneesConnexion.add(modeleSource, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 0, 0));

                    //---- modeleSourceVar ----
                    modeleSourceVar.setText("MPDR_perso");
                    modeleSourceVar.setFont(new Font("Arial", Font.BOLD, 13));
                    modeleSourceVar.setEditable(false);
                    panelDonneesConnexion.add(modeleSourceVar, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 0, 5, 5), 0, 0));

                    //---- nomSchema ----
                    nomSchema.setText("Nom du sch\u00e9ma :");
                    nomSchema.setFont(new Font("Arial", Font.PLAIN, 13));
                    panelDonneesConnexion.add(nomSchema, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 0, 5, 5), 0, 0));

                    //---- nomSchemaVar ----
                    nomSchemaVar.setText("VINCENT_CHASSOT");
                    nomSchemaVar.setFont(new Font("Arial", Font.BOLD, 13));
                    nomSchemaVar.setEditable(false);
                    panelDonneesConnexion.add(nomSchemaVar, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 0, 5, 5), 0, 0));

                    //---- nomConnexion ----
                    nomConnexion.setText("Nom de la connexion :");
                    nomConnexion.setFont(new Font("Arial", Font.PLAIN, 13));
                    panelDonneesConnexion.add(nomConnexion, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 5, 5, 5), 0, 0));

                    //---- nomConnexionVar ----
                    nomConnexionVar.setText("ECRISOFT");
                    nomConnexionVar.setFont(new Font("Arial", Font.BOLD, 13));
                    nomConnexionVar.setEditable(false);
                    panelDonneesConnexion.add(nomConnexionVar, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                }
                contentPanel.add(panelDonneesConnexion, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- textComparaison ----
                textComparaison.setText("Comparaison des structures de donn\u00e9es");
                textComparaison.setFont(new Font("Arial", Font.PLAIN, 13));
                contentPanel.add(textComparaison, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollPaneGauche ========
                {

                    //---- treeMPDR ----
                    treeMPDR.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            tree_MPDRMouseClicked(e);
                        }
                    });
                    scrollPaneGauche.setViewportView(treeMPDR);
                }
                contentPanel.add(scrollPaneGauche, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //======== scrollPaneDroite ========
                {

                    //---- treeSGBDR ----
                    treeSGBDR.setFont(new Font("Arial", Font.PLAIN, 13));
                    treeSGBDR.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            tree_SGBDRMouseClicked(e);
                        }
                    });
                    scrollPaneDroite.setViewportView(treeSGBDR);
                }
                contentPanel.add(scrollPaneDroite, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- textInformation ----
                textInformation.setText("*Information : les packages et les d\u00e9clencheurs sont tous reg\u00e9n\u00e9r\u00e9s");
                textInformation.setFont(textInformation.getFont().deriveFont(textInformation.getFont().getStyle() | Font.ITALIC));
                buttonBar.add(textInformation, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnFermer ----
                btnFermer.setText("Fermer");
                buttonBar.add(btnFermer, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnSuivant ----
                btnSuivant.setText("Suivant");
                buttonBar.add(btnSuivant, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
    private JPanel contentPanel;
    private JPanel panelDonneesConnexion;
    private JLabel modeleSource;
    private JTextField modeleSourceVar;
    private JLabel nomSchema;
    private JTextField nomSchemaVar;
    private JLabel nomConnexion;
    private JTextField nomConnexionVar;
    private JLabel textComparaison;
    private JScrollPane scrollPaneGauche;
    private JTree treeMPDR;
    private JScrollPane scrollPaneDroite;
    private JTree treeSGBDR;
    private JPanel buttonBar;
    private JLabel textInformation;
    private JButton btnFermer;
    private JButton btnSuivant;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
