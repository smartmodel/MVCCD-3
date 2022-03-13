package AttributesGrid;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class AttributesGridFrame extends JFrame {
    //Tableau contenant les types de données
    final private String[] types = {"word", "varchar2", "nonPositiInteger", "nonPositiDecimal",
            "nonNegatiInteger", "nonNegatiDecimal", "token"};
    //Tableau contenant des tableaux d'attributs
    private static Object[][] data;
    //Tableau contenant les attributs passés en paramètre avant changements
    private static Object[][] startData;
    //Liste contenant les attributs
    private ArrayList<Attribute> list;
    //Initialisation de la JTable
    private final JTable table  = new JTable();
    //Variable contenant l'index de la ligne sélectionnée
    private int selectedRow = -2;
    //Variable contenant l'index de la ligne sélectionnée
    private int aid;

    /**
     * Constructeur AttributesFrame reçoit une liste en paramètre
     *
     */
    public AttributesGridFrame(ArrayList<Attribute> startList) {
        //Crée une fenêtre dont le titre est "Grille des attributs"
        super("Grille des attributs");
        //La fenêtre ne se ferme pas en cliquant sur la croix rouge et affiche boite de dialogue
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        closeButton();
        //Choix taille de la fenêtre
        this.setSize(600, 250);
        //Centrer le fenêtre par rapport à écran
        this.setLocationRelativeTo(null);

        //Récupérer liste dans la liste locale
        this.list = startList;
        //Stocker liste de départ
        startData = (Object[][]) transformToTab(startList);
        //Rafraichir le modèle de la JTable
        setTableModel();

        //Création du conteneur qui va contenir tous nos composants
        JPanel contentPane = (JPanel) this.getContentPane();
        //Creation d'un border layout pour bien placer les composants
        contentPane.setLayout(new BorderLayout());

        //Ajout de la JTable
        contentPane.add(table, BorderLayout.NORTH);
        //Creation et ajout de la barre pour défiler les lignes du JTable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        //Ajout des boutons Ajouter, Supprimer, Annuler et Appliquer au bas de la fenêtre
        contentPane.add(addButtons(), BorderLayout.SOUTH);
        //Ajout d'une barre latérale à droite avec les boutons haut et bas et de la case aid
        contentPane.add(addSideBar(), BorderLayout.EAST);

        //Chaque fois qu'une ligne est sélectionnée elle sera stockée dans selectedRow
        table.getSelectionModel().addListSelectionListener( (e) -> selectedRow = table.getSelectedRow());
    }

    /**
     * Méthode qui affiche la barre du bas avec les boutons d'ajout et de suppression d’un attribut et les
     * boutons pour annuler et appliquer.
     */
    public JToolBar addButtons() {
        //Creation de la barre et du FlowLayout pour la mise en page
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout());

        //Creation et ajout du bouton pour ajouter un nouvel attribut
        JButton buttonAjouter = new JButton("Ajouter nouvel attribut");
        toolBar.add(buttonAjouter);
        buttonAjouter.addActionListener( (e) -> {
            //Ajout d'un nouvel attribut dans la liste des attributs
            list.add(new Attribute(list.size() + 1, "", "word", false, false));
            //Rafraichir le modèle de la JTable
            setTableModel();
            //Rafraichir la fenêtre
            this.repaint();
        });

        //Creation et ajout du bouton pour ajouter un nouvel attribut
        JButton buttonSupprimer = new JButton("Supprimer attribut sélectionné");
        toolBar.add(buttonSupprimer);
        buttonSupprimer.addActionListener( (e) -> {
            try {
                //Suppression depuis la liste l'attribut sélectionné
                list.remove(selectedRow);
                //Rafraichir les num des attributs
                refreshIds();
                //Rafraichir le modèle de la JTable
                setTableModel();
                //Rafraichir la fenêtre
                this.repaint();
            } catch(Exception ignored){}
        });

        //Creation et ajout du bouton pour ajouter un nouvel attribut
        JButton buttonAnnuler = new JButton("Annuler");
        toolBar.add(buttonAnnuler);
        buttonAnnuler.addActionListener( (e) -> {
            cancel();
            //Afficher une boite de dialogue avec oui/non comme choix
            int clickedButton = JOptionPane.showConfirmDialog(null,
                    "Etes vous sur de vouloir annuler les changements et quitter ?"
                    , "Quitter", JOptionPane.YES_NO_OPTION);
            //Si oui fermer la fenêtre
            if (clickedButton == JOptionPane.YES_OPTION)
                dispose();
        });

        //Creation et ajout du bouton pour ajouter un nouvel attribut
        JButton buttonAppliquer = new JButton("Appliquer");
        toolBar.add(buttonAppliquer);
        buttonAppliquer.addActionListener( (e) -> apply());

        return toolBar;
    }

    /**
     * Méthode qui permet de rafraichir les id après suppression ou modification de ligne du tableau.
     */
    public void refreshIds() {
        int id = 1;
        for (Attribute attribut: list)
            attribut.setId(id++);
    }

    /**
     * Methode permet d’ajouter la barre latérale contenant la case à cocher aid et les boutons pour
     * faire monter et descendre un attribut.
     */
    public JToolBar addSideBar() {
        //Creation de la barre et d'un GridLayout 1 ligne et 3 colonnes pour la mise en page
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new GridLayout(3, 1));

        //Ajout de la case à cocher
        JCheckBox checkAid = new JCheckBox("aid");
        toolBar.add(checkAid);
        checkAid.addActionListener( (e) -> addCheckAid(checkAid));

        //Ajout du bouton Haut
        JButton buttonHaut = new JButton("^");
        toolBar.add(buttonHaut);
        buttonHaut.addActionListener( (e) -> upDownButtons(-1));

        //Ajout du bouton Bas
        JButton buttonBas = new JButton("v");
        toolBar.add(buttonBas);
        buttonBas.addActionListener( (e) -> upDownButtons(1));

        return toolBar;
    }

    /**
     * Méthode qui cherche s’il y’a un attribut dans la liste qui est identifiant artificiel :
     * o Si aucun aid n’existe alors l’attribut va devenir identifiant artificiel.
     * o Si la ligne sélectionnée est un identifiant artificiel alors la contrainte aid sera supprimée.
     * o Si un aid existe déjà alors un message d’erreur sera affiché.
     * Ensuite décocher la case aid et rafraichir le modèle de table.
     */
    public void addCheckAid(JCheckBox checkAid) {
        //Check s'il y'a deja un aid
        //Si c'est la cas la variable isAid passe a true et la variable aid prend la valeur de la ligne
        boolean isAid = false;
        for (Attribute attribute: list) {
            if (attribute.isAid()) {
                isAid = true;
                aid = attribute.getId() - 1;
            }
        }

        //Si aucun aid n’existe alors l’attribut va devenir identifiant artificiel.
        //Si la ligne sélectionnée est un identifiant artificiel alors la contrainte aid sera supprimée.
        //Si un aid existe déjà alors un message d’erreur sera affiché.
        try {
            if (!isAid) {
                list.get(selectedRow).setAid(true);
                aid = selectedRow;
            } else if (aid == selectedRow)
                list.get(selectedRow).setAid(false);
            else
                JOptionPane.showMessageDialog(null,
                        "Vous avez deja choisi un aid", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch(Exception ignored){}

        //Décocher la case a cocher
        checkAid.setSelected(false);

        //Rafraichir le modèle de table et la fenêtre
        setTableModel();
        this.repaint();
    }

    /**
     * Methode qui va remonter et descendre d'une ligne un attribut, en le supprimant puis l’insérant
     * à la position souhaitée. Elle reçoit la variable signe en paramètre :
     * o Si signe égal à -1 alors l'attribut monte d'une ligne
     * o Si signe égal à 1 alors l'attribut descends d'une ligne
     * Monter la première ligne la remet à la dernière et descendre la dernière ligne va la remettre à la première.
     */
    public void upDownButtons(int signe) {
        try {
            //Variable qui va contenir la ligne à laquelle on déplace l'attribut
            int rowDest = selectedRow + signe;
            //Si dans les limites du tableau alors remis au début ou a la fin
             if (rowDest == list.size())
                rowDest = 0;
             else if (rowDest == -1)
                rowDest = list.size() - 1;

            //Variable dans laquelle est stocké l'attribut à déplacer
            Attribute attribut = list.get(selectedRow);
            //Suppression de la ligne à déplacer
            list.remove(selectedRow);
            //Ajout de la ligne à la position d'arrivée
            list.add(rowDest, attribut);

            //Rafraichir les Ids, le modèle de table et la fenêtre
            refreshIds();
            setTableModel();
            this.repaint();

            //Remettre la selection sur la ligne déplacée
            table.setRowSelectionInterval(rowDest,rowDest);
        } catch(Exception ignored){}
    }

    /**
     * Transformer une liste en un tableau d'objets
     */
    public Object[] transformToTab(ArrayList<Attribute> liste) {
        Object[] newData = new Object[liste.size()][];
        int i = 0;
        //Pour chaque élément de la liste
        for (Attribute attribut: liste)
            //Stocke les données d'un attribut dans cellules de tableau
            newData[i++] = new Object[]{attribut.getNum(), attribut.getNom(),
                    attribut.getType(), attribut.isObligatoire()};
        return newData;
    }

    /**
     * Transformer un tableau d'objets en une liste
     */
    public ArrayList<Attribute> transformToList(Object[][] dataT) {
        ArrayList<Attribute> newList = new ArrayList<>();
        //Pour chaque élément du tableau
        for(int i = 0; i < dataT.length; i++)
            //Récupère les cellules du tableau et crée un attribut
            newList.add( new Attribute(i+1, dataT[i][1].toString(), dataT[i][2].toString(),
                        (Boolean) dataT[i][3], dataT[i][0].equals("aid")));
        return newList;
    }

    /**
     * Méthode qui transforme les cellules de la colonne type en cellules éditables, avec auto-complétion et avec
     * encore une liste déroulante avec les types de données.
     * Elle est inspirée de la méthode setUpSportColumn du TableRendererDemoProject de chez Oracle (Source).
     * Elle va d’abord créer une liste déroulante JComboBox de type Java2sAutoConboBox (Classe du projet gcode
     * publié sur Github par brainsqueezer) qui fait l’auto-complétion.
     */
    public void setUpTypeColumn(TableColumn column) {
        //Création d'une liste déroulante et ajout du tableau des types
        Java2sAutoComboBox comboBox = new Java2sAutoComboBox(List.of(types));
        //Nombre de lignes affichées de la liste
        comboBox.setMaximumRowCount(5);
        //Insertion de la liste déroulante dans chaque cellule de la colonne
        column.setCellEditor(new DefaultCellEditor(comboBox));
        //Création et ajout du renderer pour que l'autocomplétion fonctionne
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        column.setCellRenderer(renderer);
    }

    /**
     * Cette méthode sert à colorer le fond des cellules de la colonne nom en rouge si le champs nom est vide.
     * Elle utilise la méthode getTableCellRendererComponent de la classe DefaultTableCellRebderer pour
     * modifier le cellBackgrou. Cette méthode est inspirée du commentaire de RodolphEst en 2015 sur le
     * site stackOverflow
     */
    public void setUpErrorColor(TableColumn column) {
        //Ajout du rendu des cellules de la colonne passée en paramètre
        column.setCellRenderer( new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                //Récupère la cellule dans cell
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //Si le champs nom est vide alors le fond de la cellule sera coloré en rouge
                if(table.getValueAt(row, 1).equals("")){
                    cell.setBackground(new Color(255, 50, 50));
                    table.setValueAt(table.getValueAt(row, column).toString(), row, 1);
                }
                return cell;
            }
        });
    }

    /**
     * Méthode qui permet d’afficher une boite de dialogue lorsque la croix rouge est appuyée, proposant à
     * l’utilisateur de quitter sans appliquer ou en appliquant les changements.
     */
    public void closeButton() {
        //Lorsque la fenêtre se ferme
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //afficher une boite de dialogue avec oui/non comme choix
                int clickedButton = JOptionPane.showConfirmDialog(null,
                        "Voulez vous appliquer vos modifications avant de quitter ?"
                        , "Quitter", JOptionPane.YES_NO_OPTION);
                //Si oui appliquer et quitter Sinon annuler et quitter
                if (clickedButton == JOptionPane.NO_OPTION) {
                    cancel();
                    dispose();
                }
                else
                    apply();
            }
        });
    }

    /**
     * Méthode qui annule toutes les modifications effectuées
     */
    public void cancel() {
        //Récupère les données de départ dans la liste
        list = transformToList(startData);
        //Mettre à jour le modèle de table
        setTableModel();
    }

    /**
     * Méthode qui annule toutes les modifications effectuées et ferme la fenêtre.
     */
    public void apply() {
        //Vérifie si aucun champs nom n'est vide si oui message d'erreur
        for (Attribute attribute: list)
            if (attribute.getNom().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Certains attributs ne sont pas corrects", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        //Sinon ferme la fenêtre
        this.dispose();
    }

    /**
     * Méthode qui retourne la liste des attributs. Si les changements ont été enregistrés avant de quitter
     * la fenêtre alors la liste contiendra les attributs mis à jour, sinon ce sera la liste passée en paramètre
     * en créant la fenêtre.
     */
    public ArrayList<Attribute> getList() {
        return transformToList(data);
    }

    /**
     * Cette méthode permet de :
     * o Mettre à jour le tableau à partie de la liste
     * o Rafraichir le modèle de la JTable
     * o Redéfinir la largeur de la colonne « Num »
     * o Mettre à jour la couleur de fond des cellules de la colonne « Nom » si le nom n’a pas été introduit.
     * o Mettre à jour la colonne « Type » en y insérant la liste déroulante.
     */
    public void setTableModel() {
        //Transformation de la liste en tableau
        data = (Object[][]) transformToTab(list);

        //Redefinition de la méthode setValueAt() pour mettre a jour la liste et le
        //tableau lors de chaque modification des données.
        table.setModel(new MyTableModel(data) {
            public void setValueAt(Object value, int rowIndex, int columnIndex) {
                //Mettre a jour le tableau et la liste après toute modification
                data[rowIndex][columnIndex] = value;
                list = transformToList(data);
                //Mettre a jour le fond rouge des cellules nom lors de chaque modification des donnees
                setUpErrorColor(table.getColumnModel().getColumn(1));
            }
        });

        //Fixer la largeur de la colonne num a 10
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        //Mettre a jour le fond rouge des cellules nom qui sont vides
        setUpErrorColor(table.getColumnModel().getColumn(1));
        //Creation des cellules avec la liste déroulante pour le type
        setUpTypeColumn(table.getColumnModel().getColumn(2));
    }
}