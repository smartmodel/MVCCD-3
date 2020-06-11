package diagram.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import main.MVCCDWindow;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mcd.services.IMCDModelService;
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

        //la palette doit être un BoxLayout
        panelPalette.setLayout(new BoxLayout(panelPalette,BoxLayout.Y_AXIS));
        //Changement de la couleur de fond
        panelPalette.setBackground(Color.WHITE);

        JLabel labelName = new JLabel("Palette");
        JButton btnEntite = createEntityBtn();
        btnEntite.addActionListener(this::actionPerformedEntity);

        JButton btnAssociation = createAssociationBtn();
        btnAssociation.addActionListener(this::actionPerformedAssociation);




        panelPalette.add(labelName);
        panelPalette.add(btnEntite);
        panelPalette.add(btnAssociation);
    }

    //Création du boutton entité
    public JButton createEntityBtn(){
        JButton btnEntite = new JButton();
        btnEntite.setBackground(Color.white);
        try {
            Image img = ImageIO.read(getClass().getResource("entite.PNG")).getScaledInstance(75,45,1);
            btnEntite.setIcon(new ImageIcon(img));
            btnEntite.setText("Entité");
            btnEntite.setHorizontalTextPosition(SwingConstants.EAST);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnEntite;

    }

    //Création du boutton associtaion
    public JButton createAssociationBtn(){
        JButton btnAssociation = new JButton();
        btnAssociation.setBackground(Color.white);
        try {
            Image img = ImageIO.read(getClass().getResource("association.PNG")).getScaledInstance(45,35,1);
            btnAssociation.setIcon(new ImageIcon(img));
            btnAssociation.setText("Association");
            btnAssociation.setHorizontalTextPosition(SwingConstants.EAST);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return btnAssociation;

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
