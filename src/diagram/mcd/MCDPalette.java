package diagram.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import preferences.Preferences;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MCDPalette {
    private JPanel panelPalette;
    MVCCDElement parent;
    String mode ;
    MCDDiagram diagram;

    boolean created = false;

    public MCDPalette(MVCCDElement parent, JPanel panelPalette, String mode, MCDDiagram diagram) {
        this.panelPalette = panelPalette;
        this.parent = parent;
        this.mode = mode;
        this.diagram = diagram;
    }
    //Je me suis inspirer de MCDTitlePanel pour la méthode getContent
    public void getContent(){
        // Réinitialisation
        //TODO-1 A affiner
        panelPalette.removeAll();

        //Le flowLayout va alligner tous les composants sur l'axe Y
        panelPalette.setLayout(new FlowLayout());
        //Changement de la couleur de fond
        panelPalette.setBackground(Color.WHITE);
        panelPalette.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelPalette.setPreferredSize((new Dimension(185, Preferences.PREFERENCES_WINDOW_HEIGHT)));

        //Dimension pour la taille des bouttons ainsi ils ont tous la même taille
        Dimension btnDimension = new Dimension(175,60);

        JLabel labelName = new JLabel("Palette ");

        //Création du bouton Entité et ajout de son listener
        JButton btnEntite = createEntityBtn();
        btnEntite.addActionListener(this::actionPerformedEntity);

        JButton btnAssociation = createAssociationBtn();
        btnAssociation.addActionListener(this::actionPerformedAssociation);

        JButton btnEntiteAssociative = createEntiteAssociativeBtn();
        JButton btnNote = createNoteBtn();
        JButton btnGenSpe = createGenSpeBtn();
        JButton btnAncre = createAncreBtn();
        JButton btnContrainteOCL = createContrainteOCLBtn();

        //Ajout des boutons dans la palette
        panelPalette.add(labelName);
        panelPalette.add(btnEntite).setPreferredSize(btnDimension);
        panelPalette.add(btnAssociation).setPreferredSize(btnDimension);
        panelPalette.add(btnEntiteAssociative).setPreferredSize(btnDimension);
        panelPalette.add(btnNote).setPreferredSize(btnDimension);
        panelPalette.add(btnGenSpe).setPreferredSize(btnDimension);
        panelPalette.add(btnAncre).setPreferredSize(btnDimension);
        panelPalette.add(btnContrainteOCL).setPreferredSize(btnDimension);
    }

    //Création du boutton entité
    public JButton createEntityBtn(){
        JButton btnEntite = new JButton();

        try {
            btnEntite.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("entite2.PNG")).getScaledInstance(60,35,4);
            btnEntite.setIcon(new ImageIcon(img));
            btnEntite.setText("Entité");
            btnEntite.setHorizontalTextPosition(SwingConstants.RIGHT);



        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnEntite;

    }

    //Création du boutton associtaion
    public JButton createAssociationBtn(){
        JButton btnAssociation = new JButton();
        try {
            btnAssociation.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("association.png")).getScaledInstance(55,35,4);
            btnAssociation.setIcon(new ImageIcon(img));
            btnAssociation.setText("Association");
            btnAssociation.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnAssociation;

    }

    //Création du boutton entité associative
    public JButton createEntiteAssociativeBtn(){
        JButton btnEntiteAssociative = new JButton("<html>Entité<br/>Associative</html>");
        try {
            btnEntiteAssociative.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("entiteAssociative.PNG")).getScaledInstance(60,35,4);
            btnEntiteAssociative.setIcon(new ImageIcon(img));
            btnEntiteAssociative.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnEntiteAssociative;

    }

    //Création du boutton note
    public JButton createNoteBtn(){
        JButton btnNote = new JButton("Note");
        try {
            btnNote.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("notes.PNG")).getScaledInstance(60,35,4);
            btnNote.setIcon(new ImageIcon(img));
            btnNote.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnNote;

    }

    //Création du boutton de généralisation et spécialisation
    public JButton createGenSpeBtn(){
        JButton btnGenSpe = new JButton("<html>Généralisation /<br/>Spécialisation</html>");
        try {
            btnGenSpe.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("gen_spe.PNG")).getScaledInstance(60,35,4);
            btnGenSpe.setIcon(new ImageIcon(img));
            btnGenSpe.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnGenSpe;

    }

    //Création du boutton ancre
    public JButton createAncreBtn(){
        JButton btnAncre = new JButton("Ancre");
        try {
            btnAncre.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("ancre.PNG")).getScaledInstance(60,35,4);
            btnAncre.setIcon(new ImageIcon(img));
            btnAncre.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnAncre;

    }

    //Création du boutton de contrainte OCL
    public JButton createContrainteOCLBtn(){
        JButton btnContrainteOCL = new JButton("<html>Contrainte<br/> OCL</html>");
        try {
            btnContrainteOCL.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("ocl.PNG")).getScaledInstance(60,35,4);
            btnContrainteOCL.setIcon(new ImageIcon(img));
            btnContrainteOCL.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnContrainteOCL;

    }

    //Listener pour créer une entité si l'utilisateur clique sur l'entité dans la palette
    public void actionPerformedEntity(ActionEvent actionEvent){
        MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        MCDContEntities mcdContEntities = (MCDContEntities) parent.getBrotherByClassName(MCDContEntities.class.getName());
        MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
        MCDEntity newMCDEntity = (MCDEntity) mcdEntityEditingTreat.treatNew(mvccdWindow, mcdContEntities);
    }

    //Listener qui affiche le fenêtre modale pour créer une association si l'utilisateur clique sur l'association
    public void actionPerformedAssociation(ActionEvent actionEvent){
        MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        IMCDModel iMCDModel = IMCDModelService.getIModelContainer((MCDElement) parent);
        ArrayList<MCDEntity> mcdEntities = IMCDModelService.getAllMCDEntitiesInIModel(iMCDModel);

        MCDEntity mcdEntityFrom = null;
        MCDEntity mcdEntityTo= null;
        if (mcdEntities.size() == 1 ){
            mcdEntityFrom = mcdEntities.get(0);
            mcdEntityTo = mcdEntities.get(0);
        } else if (mcdEntities.size() > 1 ){
            mcdEntityFrom = mcdEntities.get(0);
            mcdEntityTo = mcdEntities.get(1);
        }

        MCDContRelations mcdContRelations = (MCDContRelations) parent.getBrotherByClassName(MCDContRelations.class.getName());
        MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
        MCDAssociation newMCDAssociation = mcdAssociationEditingTreat.treatNew(mvccdWindow, mcdContRelations,
                mcdEntityFrom, mcdEntityTo, MCDAssociationNature.NOID);
    }



}
