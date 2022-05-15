package AttributesGrid;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class AttributesGridFrame extends JFrame {
    // Tableau contenant les types de données
    final private String[] types = {"word", "varchar2", "nonPositiInteger", "nonPositiDecimal",
            "nonNegatiInteger", "nonNegatiDecimal", "token"};
    // Tableau contenant des tableaux d'attributs
    private static Object[][] attributesTab;
    // Tableau contenant les attributs passés en paramètre avant changements
    private static Object[][] startTab;
    // Liste contenant les attributs
    private List<Attribute> attributesList;
    // Initialisation de la JTable
    private final JTable table  = new JTable();
    // Variable contenant l'index de la ligne sélectionnée
    private int selectedRow = -2;
    // Variable contenant l'index de l'identifiant artificiel (-1 veut dire l'absence d'un aid)
    private int aidRow = -1;

    /**
     * Constructeur AttributesFrame reçoit un tableau d'attributs en paramètre
     *
     */
    public AttributesGridFrame(Object[][] startAttributes) {
        // Crée une fenêtre dont le titre est "Grille des attributs"
        super("Grille des attributs");
        // La fenêtre ne se ferme pas en cliquant sur la croix rouge et affiche boite de dialogue
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        closeButton();
        // Choisir la taille de la fenêtre et la centrer par rapport à écran
        this.setSize(600, 250);
        this.setLocationRelativeTo(null);

        // Récupérer liste dans la liste locale
        attributesList = toList(startAttributes);
        // Stocker liste de départ pour l'utiliser en cas d'annulation des changements
        startTab = startAttributes;
        // Créer le modèle de table
        setUpTable();

        // Création du conteneur qui va contenir tous nos composants
        JPanel contentPane = (JPanel) this.getContentPane();
        // Creation d'un border layout pour bien placer les composants
        contentPane.setLayout(new BorderLayout());

        // Ajout de la JTable
        contentPane.add(table, BorderLayout.NORTH);
        // Creation et ajout de la barre pour défiler les lignes du JTable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        // Ajout des boutons Ajouter, Supprimer, Annuler et Appliquer au bas de la fenêtre
        contentPane.add(addButtons(), BorderLayout.SOUTH);
        // Ajout d'une barre latérale à droite avec les boutons haut et bas et de la case aid
        contentPane.add(addSideBar(), BorderLayout.EAST);

        // Chaque fois qu'une ligne est sélectionnée son index sera stockée dans selectedRow
        table.getSelectionModel().addListSelectionListener( (e) ->  selectedRow = table.getSelectedRow());
    }

    /**
     * Méthode qui affiche la barre du bas avec les boutons d'ajout et de suppression d’un attribut et les
     * boutons pour annuler et appliquer.
     */
    public JToolBar addButtons() {
        // Creation de la barre et du FlowLayout pour la mise en page
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout());

        // Creation et ajout du bouton pour ajouter un nouvel attribut
        JButton buttonAjouter = new JButton("Ajouter nouvel attribut");
        toolBar.add(buttonAjouter);
        buttonAjouter.addActionListener( (e) -> {
            // Ajout d'un nouvel attribut dans la liste des attributs
            attributesList.add(new Attribute(valueOf(attributesList.size() + 1), "", "", false));
            // Rafraichir le modèle de table et la fenêtre
            setUpTable();
            this.repaint();
        });

        // Creation et ajout du bouton pour ajouter un nouvel attribut
        JButton buttonSupprimer = new JButton("Supprimer attribut");
        toolBar.add(buttonSupprimer);
        buttonSupprimer.addActionListener( (e) -> {
            try {
                // Suppression depuis la liste l'attribut sélectionné
                attributesList.remove(selectedRow);
                // Rafraichir les Ids, le modèle de table et la fenêtre
                refreshIds();
                setUpTable();
                this.repaint();

            } catch(Exception exception){
                anySelectedRowException();
            }
        });

        // Creation et ajout du bouton pour ajouter un nouvel attribut
        JButton buttonAnnuler = new JButton("Annuler");
        toolBar.add(buttonAnnuler);
        buttonAnnuler.addActionListener( (e) -> {
            // Afficher une boite de dialogue avec oui/non comme choix
            int clickedButton = JOptionPane.showConfirmDialog(null,
                    "Etes vous sur de vouloir annuler les changements ?"
                    , "Erreur", JOptionPane.YES_NO_OPTION);
            // Si oui annuler les changements
            if (clickedButton == JOptionPane.YES_OPTION)
                cancelChanges();
        });

        // Creation et ajout du bouton pour appliquer les changements
        JButton buttonAppliquer = new JButton("Appliquer");
        toolBar.add(buttonAppliquer);
        buttonAppliquer.addActionListener( (e) -> applyChanges());

        return toolBar;
    }

    /**
     * Methode permet d’ajouter la barre latérale contenant la case à cocher aid et les boutons pour
     * faire monter et descendre un attribut.
     */
    public JToolBar addSideBar() {
        // Creation de la barre et d'un GridLayout 1 ligne et 3 colonnes pour la mise en page
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new GridLayout(3, 1));

        // Ajout de la case à cocher
        JCheckBox checkAid = new JCheckBox("aid");
        toolBar.add(checkAid);
        checkAid.addActionListener( (e) -> addCheckAid(checkAid));

        // Ajout du bouton Haut
        JButton buttonHaut = new JButton("^");
        toolBar.add(buttonHaut);
        buttonHaut.addActionListener( (e) -> upDownButtons(-1));

        // Ajout du bouton Bas
        JButton buttonBas = new JButton("v");
        toolBar.add(buttonBas);
        buttonBas.addActionListener( (e) -> upDownButtons(1));

        return toolBar;
    }

    /**
     * Méthode qui va permettre de :
     * o Si aucune ligne n'est sélectionnée alors l'exception est levée et un message d'erreur s'affiche
     * o Si aucun aid n’existe alors l’attribut va devenir identifiant artificiel.
     * o Si la ligne sélectionnée est un identifiant artificiel alors la contrainte aid sera supprimée.
     * o Sinon un identifiant artificiel existe déjà et un message d’erreur s'affiche
     * Ensuite elle décoche la case aid et rafraichit le modèle de table et la fenêtre.
     */
    public void addCheckAid(JCheckBox checkAid) {
        try {
            // Si aucun aid n’existe alors l’attribut va devenir identifiant artificiel.
            if (aidRow == -1) {
                attributesList.get(selectedRow).setNum("aid");
                // aidRow prend la valeur de la ligne sélectionnée
                aidRow = selectedRow;
            }
            // Si la ligne sélectionnée est un identifiant artificiel alors la contrainte aid sera supprimée.
            else if (aidRow == selectedRow) {
                attributesList.get(selectedRow).setNum(valueOf(selectedRow + 1));
                // aidRow reprend sa valeur initiale -1
                aidRow = -1;
            }
            // Sinon un identifiant artificiel existe déjà et un message d’erreur s'affiche.
            else
                JOptionPane.showMessageDialog(null,
                        "Vous avez deja choisi un aid", "Erreur", JOptionPane.ERROR_MESSAGE);

        } catch(Exception exception){
            // Si aucune ligne n'est sélectionnée alors affiche message d'erreur
            anySelectedRowException();
        }

        // Décocher la case a cocher
        checkAid.setSelected(false);

        // Rafraichir le modèle de table et la fenêtre
        setUpTable();
        this.repaint();
    }

    /**
     * Méthode qui va remonter et descendre d'une ligne un attribut, en le supprimant puis l’insérant
     * à la position souhaitée. Elle reçoit la variable signe en paramètre :
     * o Si signe égal à -1 alors l'attribut monte d'une ligne
     * o Si signe égal à 1 alors l'attribut descends d'une ligne
     * Faire monter la première ligne la remet à la dernière et descendre la dernière va la remettre à la première.
     * Si aucune ligne n'est sélectionnée alors un message d'erreur s'affiche
     */
    public void upDownButtons(int signe) {
        try {
            // Variable contenant la ligne à laquelle on déplace l'attribut
            int rowDest = selectedRow + signe;
            // Si dans les limites du tableau alors remis au début ou à la fin
             if (rowDest == attributesList.size())
                rowDest = 0;
             else if (rowDest == -1)
                rowDest = attributesList.size() - 1;

            // Variable contenant l'attribut à déplacer
            Attribute attribut = attributesList.get(selectedRow);
            // Suppression de la ligne à déplacer
            attributesList.remove(selectedRow);
            // Ajout de la ligne à la position d'arrivée
            attributesList.add(rowDest, attribut);

            // Rafraichir les Ids, le modèle de table et la fenêtre
            refreshIds();
            setUpTable();
            this.repaint();

            // Remettre la selection sur la ligne déplacée
            table.setRowSelectionInterval(rowDest,rowDest);
        } catch(Exception exception){
            // Si aucune ligne n'est sélectionnée alors affiche message d'erreur
            anySelectedRowException();
        }
    }

    /**
     * Méthode qui permet de rafraichir les id après suppression ou modification de ligne du tableau.
     */
    public void refreshIds() {
        int id = 1;
        for (Attribute attribut: attributesList)
            attribut.setNum(valueOf(id++));
    }

    /**
     * Méthode qui affiche une boite de dialogue pour dire qu'aucune ligne n'est sélectionnée.
     * Elle est utilisée lorsqu'une exception est levée.
     */
    public void anySelectedRowException() {
        JOptionPane.showMessageDialog(null,
                "Vous devez sélectionner une ligne", "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Méthode qui permet d’afficher une boite de dialogue lorsque la croix rouge est appuyée, proposant à
     * l’utilisateur de quitter sans appliquer ou en appliquant les changements.
     */
    public void closeButton() {
        // Lorsque la fenêtre se ferme
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Afficher une boite de dialogue avec oui/non comme choix
                int clickedButton = JOptionPane.showConfirmDialog(null,
                        "Voulez vous appliquer vos modifications avant de quitter ?"
                        , "Quitter", JOptionPane.YES_NO_OPTION);
                if (clickedButton == JOptionPane.NO_OPTION) {
                    // Si non, annuler les changements et fermer la fenêtre
                    cancelChanges();
                    dispose();
                } else {
                    // Si oui, appliquer les changements et fermer la fenêtre
                    applyChanges();
                    dispose();
                }
            }
        });
    }

    /**
     * Méthode qui annule toutes les modifications effectuées
     */
    public void cancelChanges() {
        // Récupère les données de départ dans le tableau
        attributesTab = startTab;
        // Met à jour la liste des attributs
        attributesList = toList(attributesTab);
        // Met à jour le modèle de table
        setUpTable();
    }

    /**
     * Méthode qui applique toutes les modifications effectuées et ferme la fenêtre si toutes les modifications
     * ont été enregistrés.
     */
    public void applyChanges() {
        // Vérifie si aucun champ nom n'est vide si oui message d'erreur
        for (Attribute attribute: attributesList)
            if (attribute.getNom().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Certains attributs ne sont pas corrects", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        dispose();
    }

    /**
     * Méthode qui retourne le tableau des attributs. Si les changements ont été enregistrés avant de quitter
     * la fenêtre alors le tableau contiendra les attributs mis à jour, sinon ce sera le tableau passé en
     * paramètre en créant la fenêtre.
     */
    public Object[][] getTab() {
        return attributesTab;
    }

    /**
     * Methode qui transforme un tableau à deux dimensions en une liste contenant des éléments de type Attribute.
     */
    public List<Attribute> toList(Object[][] dataT) {
        List<Attribute> newList = new ArrayList<>();
        // Pour chaque élément du tableau récupère les cellules du tableau et crée un attribut
        for(int i = 0; i < dataT.length; i++)
            newList.add( new Attribute(valueOf(i + 1), dataT[i][1].toString(), dataT[i][2].toString(),
                    (Boolean) dataT[i][3]));
        return newList;
    }

    /**
     * Cette méthode permet de :
     * o Transformer de la liste des attributs en tableau
     * o Rafraichir le modèle de la JTable
     * o Fixer la largeur de la colonne « Num »
     * o Colorer en rouge la bordure des cellules de la colonne « nom » si le champ est vide.
     * o Transformer les cellules de la colonne type en cellules éditables, avec auto-complétions et une liste
     * déroulante avec les types de données.
     */
    public void setUpTable() {
        // Transformation de la liste des attributs en tableau
        Object[][] newTab = new Object[attributesList.size()][];
        int i = 0;
        // Pour chaque élément de la liste stocke les données d'un attribut dans cellules de tableau
        for (Attribute attribut: attributesList)
            newTab[i++] = new Object[]{attribut.getNum(), attribut.getNom(), attribut.getType(),
                    attribut.isObligatoire()};
        attributesTab = newTab;

        // Redefinition de la méthode setValueAt() pour la mise à jour après chaque modification des données.
        table.setModel(new AttributesTableModel(attributesTab) {
            public void setValueAt(Object value, int rowIndex, int columnIndex) {
                //Mettre a jour le tableau et la liste des attributs après toute modification
                attributesTab[rowIndex][columnIndex] = value;
                attributesList = toList(attributesTab);
            }
        });

        // Fixer la largeur de la colonne num a 10
        table.getColumnModel().getColumn(0).setPreferredWidth(10);

        // Changer la couleur de la bordure en rouge si les cellules la colonne « Nom » sont vides en utilisant
        // la propriété JComponent.outline de la librairie Flatlaf
        table.getColumnModel().getColumn(1).setCellRenderer(new ErrorCellRenderer());

        // Création d'une liste déroulante de type AutoComboBox et ajout du tableau des types
        AutoComboBox comboBox = new AutoComboBox(List.of(types));
        // Nombre de données affichées dans la liste déroulante
        comboBox.setMaximumRowCount(6);
        // Insertion de la liste déroulante dans chaque cellule de la colonne
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));
        // Création et ajout du renderer pour que l'autocomplétion fonctionne
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        table.getColumnModel().getColumn(2).setCellRenderer(renderer);
    }
}