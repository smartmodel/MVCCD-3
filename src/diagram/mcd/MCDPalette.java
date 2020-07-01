package diagram.mcd;

import diagram.Diagram;
import diagram.mcd.testDrawPanel.Attributes;
import diagram.mcd.testDrawPanel.DiagramList;
import diagram.mcd.testDrawPanel.MCDEntityDraw;
import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import main.window.diagram.WinDiagram;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
import preferences.Preferences;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import window.editor.entity.EntityEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
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
        //---------------------------------Panel---------------------------------------------
        panelPalette.setLayout(new FlowLayout(FlowLayout.CENTER,0,5));
        panelPalette.setBackground(Color.WHITE);
        panelPalette.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelPalette.setPreferredSize((new Dimension(200, Preferences.PREFERENCES_WINDOW_HEIGHT)));

        //------------------------------Dimensions-------------------------------------------
        //Dimension pour la taille des boutons ainsi ils ont tous la même taille.
        Dimension btnDimension = new Dimension(195,60);
        Dimension btnDimensionContenantUnDeroulant= new Dimension(165,60);
        Dimension btnDimensionderoulant = new Dimension(30,60);


        //--------------------------Composants du JPanel--------------------------------------
        JLabel labelName = new JLabel("Palette ");

        //Création du bouton Entité et ajout de son listener
        JButton btnEntite = createEntityBtn();
        btnEntite.addActionListener(this::actionPerformedEntity);

        //Création du boutton ... à côté du bouton entité le listener ne marche pas encore
        JButton btnEntitePlus = createEntityPlusBtn();
        //btnEntitePlus.addActionListener(mcdPaletteListener.);

        JButton btnAssociationPlus = createAssociationPlusBtn();


        JButton btnAssociation = createAssociationBtn();
        btnAssociation.addActionListener(this::actionPerformedAssociation);




        JButton btnEntiteAssociative = createEntiteAssociativeBtn();
        JButton btnNote = createNoteBtn();
        JButton btnGenSpe = createGenSpeBtn();
        JButton btnAncre = createAncreBtn();
        JButton btnContrainteOCL = createContrainteOCLBtn();

        //Ajout des boutons dans la palette
        panelPalette.add(labelName).setPreferredSize(new Dimension(175,20));
        panelPalette.add(btnEntite).setPreferredSize(btnDimensionContenantUnDeroulant);
        panelPalette.add(btnEntitePlus).setPreferredSize(btnDimensionderoulant);
        panelPalette.add(btnAssociation).setPreferredSize(btnDimensionContenantUnDeroulant);
        panelPalette.add(btnAssociationPlus).setPreferredSize(btnDimensionderoulant);
        panelPalette.add(btnEntiteAssociative).setPreferredSize(btnDimension);
        panelPalette.add(btnNote).setPreferredSize(btnDimension);
        panelPalette.add(btnGenSpe).setPreferredSize(btnDimension);
        panelPalette.add(btnAncre).setPreferredSize(btnDimension);
        panelPalette.add(btnContrainteOCL).setPreferredSize(btnDimension);
    }



    public JButton createEntityPlusBtn() {
        JButton btnEntityPlus = new JButton("V");
        try {
            btnEntityPlus.setHorizontalAlignment(SwingConstants.CENTER);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnEntityPlus;
    }

    //Création du boutton entité

    public JButton createEntityBtn(){
        JButton btnEntite = new JButton();

        try {
            btnEntite.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("ressources/entite2.PNG")).getScaledInstance(60,35,4);
            btnEntite.setIcon(new ImageIcon(img));
            btnEntite.setText("Entité");
            btnEntite.setHorizontalTextPosition(SwingConstants.RIGHT);



        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnEntite;

    }

    //Création du boutton enumération
    /*public JButton createEnumBtn(){
        JButton btnEnum = new JButton();
        btnEnum.setVisible(false);
        try {
            btnEnum.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("ressources/association.png")).getScaledInstance(55,35,4);
            btnEnum.setIcon(new ImageIcon(img));
            btnEnum.setText("Enumération");
            btnEnum.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnEnum;

    }*/

    //Création du boutton association
    public JButton createAssociationBtn(){
        JButton btnAssociation = new JButton();
        try {
            btnAssociation.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("ressources/association.png")).getScaledInstance(55,35,4);
            btnAssociation.setIcon(new ImageIcon(img));
            btnAssociation.setText("Association");
            btnAssociation.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnAssociation;

    }

    public JButton createAssociationPlusBtn() {
        JButton btnAssociationPlus = new JButton("V");
        try {
            btnAssociationPlus.setHorizontalAlignment(SwingConstants.CENTER);
            //Image img = ImageIO.read(getClass().getResource("btnPlus.PNG")).getScaledInstance(10,10,4);
            //btnAssociationPlus.setIcon(new ImageIcon(img));

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnAssociationPlus;
    }

    //Création du boutton entité associative
    public JButton createEntiteAssociativeBtn(){
        JButton btnEntiteAssociative = new JButton("<html>Entité<br/>Associative</html>");
        try {
            btnEntiteAssociative.setHorizontalAlignment(SwingConstants.LEFT);
            Image img = ImageIO.read(getClass().getResource("ressources/entiteAssociative.PNG")).getScaledInstance(60,35,4);
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
            Image img = ImageIO.read(getClass().getResource("ressources/notes.PNG")).getScaledInstance(60,35,4);
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
            Image img = ImageIO.read(getClass().getResource("ressources/gen_spe.PNG")).getScaledInstance(60,35,4);
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
            Image img = ImageIO.read(getClass().getResource("ressources/ancre.PNG")).getScaledInstance(60,35,4);
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
            Image img = ImageIO.read(getClass().getResource("ressources/ocl.PNG")).getScaledInstance(60,35,4);
            btnContrainteOCL.setIcon(new ImageIcon(img));
            btnContrainteOCL.setHorizontalTextPosition(SwingConstants.RIGHT);


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnContrainteOCL;

    }

    //Cacher et afficher certains bouton. Pas prêt pour l'instant
    /*public void actionPerformedHideUnderEntity(ActionEvent actionEvent) {

        JPanel panelEnumDomaine = new JPanel();
        if (panelEnumDomaine.isVisible()){
            panelEnumDomaine.setVisible(false);
        } else {
            panelEnumDomaine.setVisible(true);
        }
        Dimension btnDimension = new Dimension(175,60);

        //Le flowLayout va alligner tous les composants sur l'axe Y
        panelEnumDomaine.setLayout(new FlowLayout(FlowLayout.CENTER,0,5));
        //Changement de la couleur de fond
        panelEnumDomaine.setBackground(Color.WHITE);
        panelEnumDomaine.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelEnumDomaine.setPreferredSize((new Dimension(185, 180)));

        JButton btnEnum = mcdPalette.createEnumBtn();

        panelEnumDomaine.add(btnEnum);




    }*/

    //Listener pour créer une entité si l'utilisateur clique sur l'entité dans la palette
    public void actionPerformedEntity(ActionEvent actionEvent){
        MVCCDWindow mvccdWindow = MVCCDManager.instance().getMvccdWindow();
        MCDContEntities mcdContEntities = (MCDContEntities) parent.getBrotherByClassName(MCDContEntities.class.getName());
        MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
        MCDEntity newMCDEntity = (MCDEntity) mcdEntityEditingTreat.treatNew(mvccdWindow, mcdContEntities);
        newMCDEntity.getMcdAttributes();
        System.out.println(newMCDEntity.getMcdAttributes());


        //Nous devons dessiner une nouvelle entité dans la zonne de dessin
        //Création d'un rectangle pour la zone de dessin
        Rectangle rectangleEntite= new Rectangle(0,0,250,150);

        //Création des attributs pour la zone de dessin
        Attributes attributType= new Attributes("<<Entity>>",250/2-"<<Entity>>".length()*6/2,15);
        Attributes attributTitre= new Attributes(newMCDEntity.getName(),250/2-newMCDEntity.getName().length()*6/2,30);

        ArrayList<Attributes> textDEntite= new ArrayList<>();
        textDEntite.add(0,attributType);
        textDEntite.add(1, attributTitre);
        for(int index=0; index<newMCDEntity.getMcdAttributes().size(); index ++){

            ArrayList<Stereotype> stereotype= new ArrayList<>();
            stereotype = newMCDEntity.getMcdAttributes().get(index).getToStereotypes();
            String stereoToString = "";
                for (int indexStereotype=0; indexStereotype<stereotype.size(); indexStereotype++){
                    stereoToString = stereoToString + "<<" + stereotype.get(indexStereotype) + ">> ";
                }
            String attributeAvecStereo=stereoToString+newMCDEntity.getMcdAttributes().get(index).getName();
            if (newMCDEntity.getMcdAttributes().get(index).getName()!= "num" && !stereoToString.contains("AID")){
                attributeAvecStereo=attributeAvecStereo+ ": " + newMCDEntity.getMcdAttributes().get(index).getDatatypeLienProg();
            }
            Attributes attributs= new Attributes(attributeAvecStereo,5,47+((index)*15));
            textDEntite.add(index+2,attributs);
        }

        //Création d'un objet MCDEntityDraw qui va contenir les informations pour la zone de dessin
        MCDEntityDraw entiteADessiner = new MCDEntityDraw(rectangleEntite, textDEntite);
        ArrayList<MCDEntityDraw> diagramAjout = diagram.getMCDEntityDraws();

            diagramAjout.add(entiteADessiner);
            diagram.setMCDEntityDraws(diagramAjout);

        WinDiagram winDiagram = mvccdWindow.getDiagram();
        JPanel panelZoneDessin = winDiagram.getContent().getPanelDraw();


        MCDZoneDessin mcdZoneDessin = new MCDZoneDessin(parent, panelZoneDessin, mode, diagram);
            mcdZoneDessin.getContentUpdate(diagram.getMCDEntityDraws());
            newMCDEntity.setMCDEntityDraws(diagramAjout);
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
