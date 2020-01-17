package window.preferences;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PrefInputMLDRFormat extends JPanel {

    JTextField colFormat = new JTextField();
    JTextField colDerived = new JTextField();
    JTextField pkFormat = new JTextField();
    JTextField fkFormat = new JTextField();
    JTextField colFkFormat = new JTextField();
    JTextField ajUserName =  new JTextField();
    JTextField ajDateName =  new JTextField();
    JTextField moUserName =  new JTextField();
    JTextField moDateName =  new JTextField();
    private JPanel printColLPSubPanel = new JPanel();
    JTextField lienProgName =  new JTextField();
    private JComboBox<String> lienProgSep = new JComboBox<>();
    private JComboBox<String> lienProgCar = new JComboBox<>();

    JTextField roleNameGS = new JTextField();
    JTextField nidFormat = new JTextField();
    JTextField uFormat = new JTextField();
    JTextField cpFormat = new JTextField();
    JTextField lpFormat = new JTextField();
    JTextField simPKFormat = new JTextField();
    JTextField fkMaxOneFormat = new JTextField();
    JTextField jnlFormat = new JTextField();
    JTextField peaFormat = new JTextField();
    JTextField peaSepFormat = new JTextField();
    JTextField parentRoleSepFormat = new JTextField();
    JTextField parentSepFormat = new JTextField();
    JTextField childRoleSepFormat = new JTextField();
    JTextField tableMultiFormat = new JTextField();
    JTextField orderEntityFormat = new JTextField();
    JTextField orderAssEndChildFormat = new JTextField();
    JTextField orderAssEndNNFormat = new JTextField();
    JTextField orderColEntityFormat = new JTextField();
    JTextField orderColAssEndChildFormat = new JTextField();
    JTextField orderColAssEndNNFormat = new JTextField();

    public PrefInputMLDRFormat() {

        colFormat.setPreferredSize((new Dimension(300,20)));
        colDerived.setPreferredSize((new Dimension(60,20)));
        pkFormat.setPreferredSize((new Dimension(200,20)));
        fkFormat.setPreferredSize((new Dimension(500,20)));
        colFkFormat.setPreferredSize((new Dimension(500,20)));
        ajUserName.setPreferredSize((new Dimension(200,20)));
        ajDateName.setPreferredSize((new Dimension(200,20)));
        moUserName.setPreferredSize((new Dimension(200,20)));
        moDateName.setPreferredSize((new Dimension(200,20)));
        lienProgName.setPreferredSize((new Dimension(100,20)));
        lienProgSep.setPreferredSize((new Dimension(40,20)));


        roleNameGS.setPreferredSize((new Dimension(200,20)));
        nidFormat.setPreferredSize((new Dimension(500,20)));
        uFormat.setPreferredSize((new Dimension(500,20)));
        cpFormat.setPreferredSize((new Dimension(500,20)));
        lpFormat.setPreferredSize((new Dimension(500,20)));
        simPKFormat.setPreferredSize((new Dimension(500,20)));
        fkMaxOneFormat.setPreferredSize((new Dimension(500,20)));
        jnlFormat.setPreferredSize((new Dimension(500,20)));
        peaFormat.setPreferredSize((new Dimension(500,20)));
        peaSepFormat.setPreferredSize((new Dimension(500,20)));
        parentRoleSepFormat.setPreferredSize((new Dimension(500,20)));
        parentSepFormat.setPreferredSize((new Dimension(500,20)));
        childRoleSepFormat.setPreferredSize((new Dimension(500,20)));
        tableMultiFormat.setPreferredSize((new Dimension(500,20)));
        orderEntityFormat.setPreferredSize((new Dimension(500,20)));
        orderAssEndChildFormat.setPreferredSize((new Dimension(500,20)));
        orderAssEndNNFormat.setPreferredSize((new Dimension(500,20)));
        orderColEntityFormat.setPreferredSize((new Dimension(200,20)));
        orderColAssEndChildFormat.setPreferredSize((new Dimension(500,20)));
        orderColAssEndNNFormat.setPreferredSize((new Dimension(500,20)));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor= GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10,10,0,0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(new JLabel("Format des colonnes: "),gbc);
        gbc.gridx = 1;
        this.add(colFormat, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Marqueur de col. dérivée: "),gbc);
        gbc.gridx = 1;
        this.add(colDerived, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de PK: "),gbc);
        gbc.gridx = 1;
        this.add(pkFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de FK: "),gbc);

        gbc.gridx = 1;
        this.add(fkFormat, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format col. de FK: "),gbc);
        gbc.gridx = 1;
        this.add(colFkFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Nom col. audit Aj. User: "),gbc);
        gbc.gridx = 1;
        this.add(ajUserName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Nom col. audit Aj. Date: "),gbc);
        gbc.gridx = 1;
        this.add(ajDateName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Nom col. audit Mod. User: "),gbc);
        gbc.gridx = 1;
        this.add(moUserName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Nom col. audit Mod. Date: "),gbc);
        gbc.gridx = 1;
        this.add(moDateName, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        //gbc.gridheight = 2;
        //gbc.weightx = 2;
        //gbc.weighty = 2;


        Border border = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border, "Colonne de lien de programmation" );
        printColLPSubPanel.setBorder(titledBorder);
        printColLPSubPanel.setLayout(new GridBagLayout());
        printColLPSubPanel.setAlignmentX(LEFT_ALIGNMENT);
        this.add(printColLPSubPanel,gbc);
        GridBagConstraints gbcColLienProg = new GridBagConstraints();
        gbcColLienProg.gridx = 0;
        gbcColLienProg.gridy = 0;
        printColLPSubPanel.add(new JLabel("Nom : "), gbcColLienProg);
        gbcColLienProg.gridx++;
        printColLPSubPanel.add(lienProgName, gbcColLienProg);
        gbcColLienProg.gridx++;
        printColLPSubPanel.add(new JLabel("     "), gbcColLienProg);
        gbcColLienProg.gridx++;
        printColLPSubPanel.add(new JLabel("Séparateur : "), gbcColLienProg);
        gbcColLienProg.gridx++;
        printColLPSubPanel.add(lienProgSep, gbcColLienProg);
        gbcColLienProg.gridx++;
        printColLPSubPanel.add(new JLabel("     "), gbcColLienProg);
        gbcColLienProg.gridx++;
        printColLPSubPanel.add(new JLabel("Caractère : "), gbcColLienProg);
        gbcColLienProg.gridx++;
        printColLPSubPanel.add(lienProgCar, gbcColLienProg);

        //gbc.weightx = 1;
        //gbc.weighty = 1;
        //gbc.gridheight = 1;
        gbc.gridwidth = 1;



        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Role d'entité généralisée: "),gbc);
        gbc.gridx = 1;
        this.add(roleNameGS, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de NID: "),gbc);
        gbc.gridx = 1;
        this.add(nidFormat, gbc);
/*
        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de U: "),gbc);
        gbc.gridx = 1;
        this.add(uFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de CP: "),gbc);
        gbc.gridx = 1;
        this.add(cpFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de LP: "),gbc);
        gbc.gridx = 1;
        this.add(lpFormat, gbc);
// Bon ici		
        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de sim. de PK: "),gbc);
        gbc.gridx = 1;
        this.add(simPKFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de FK max 1: "),gbc);
        gbc.gridx = 1;
        this.add(fkMaxOneFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de Journal: "),gbc);
        gbc.gridx = 1;
        this.add(jnlFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de PEA: "),gbc);
        gbc.gridx = 1;
        this.add(peaFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Sép. de marquage de col. PEA: "),gbc);
        gbc.gridx = 1;
        this.add(peaSepFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Sép. de rôle parent: "),gbc);
        gbc.gridx = 1;
        this.add(parentRoleSepFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Sép. de parent: "),gbc);
        gbc.gridx = 1;
        this.add(parentSepFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Sép. de rôle enfant: "),gbc);
        gbc.gridx = 1;
        this.add(childRoleSepFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de table multi: "),gbc);
        gbc.gridx = 1;
        this.add(tableMultiFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de {ordered} pour entité: "),gbc);
        gbc.gridx = 1;
        this.add(orderEntityFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de {ordered} pour extr d'ass. 1..*: "),gbc);
        gbc.gridx = 1;
        this.add(orderAssEndChildFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de {ordered} pour extr d'ass. *..*: "),gbc);
        gbc.gridx = 1;
        this.add(orderAssEndNNFormat, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de col. d'ordre pour entité: "),gbc);
        gbc.gridx = 1;
        this.add(orderColEntityFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de col. d'ordre pour extr d'ass. 1..*: "),gbc);
        gbc.gridx = 1;
        this.add(orderColAssEndChildFormat, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(new JLabel("Format de col. d'ordre pour extr d'ass. *..*: "),gbc);
        gbc.gridx = 1;
        this.add(orderColAssEndNNFormat, gbc);
*/
        

    }


}
