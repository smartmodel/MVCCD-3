package generatorsql.viewer;

import utilities.window.PanelContent;

import javax.swing.*;
import java.awt.*;

public class SQLViewerCodeSQLContent extends PanelContent {

    private JTextArea textArea;

    public SQLViewerCodeSQLContent(SQLViewerCodeSQL sqlViewerCodeSQL) {
        super(sqlViewerCodeSQL);
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        textArea = new JTextArea();
        textArea.setText("CREATE TABLE Article(\n" +
                "\tcode VARCHAR2(10) DEFAULT '' NOT NULL,\n" +
                "\tprix NUMBER(6, 2) DEFAULT '' NOT NULL,\n" +
                "\tunique VARCHAR2(50) DEFAULT '',\n" +
                "\tUniqueBis VARCHAR2(50) DEFAULT '',\n" +
                "\tnum NUMBER(9) NOT NULL,\n" +
                "\tArtCat_cont_num NUMBER(9) NOT NULL,\n" +
                "\tCONSTRAINT PK_Art PRIMARY KEY(num)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE ArtCategorie(\n" +
                "\tcode VARCHAR2(10) DEFAULT '' NOT NULL,\n" +
                "\tnum NUMBER(9) NOT NULL,\n" +
                "\tArtCat_div_num NUMBER(9),\n" +
                "\tCONSTRAINT PK_ArtCat PRIMARY KEY(num)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE ArtAcquis(\n" +
                "\tArt_gs_num NUMBER(9) NOT NULL,\n" +
                "\tCONSTRAINT PK_ArtAcq PRIMARY KEY(Art_gs_num)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Usine(\n" +
                "\tNom VARCHAR2(50) DEFAULT '' NOT NULL,\n" +
                "\tnum NUMBER(9) NOT NULL,\n" +
                "\tCONSTRAINT PK_Usi PRIMARY KEY(num)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Atelier(\n" +
                "\tnom VARCHAR2(50) DEFAULT '' NOT NULL,\n" +
                "\tUsi_comp_num NUMBER(9) NOT NULL,\n" +
                "\tnumDep NUMBER(9) NOT NULL,\n" +
                "\tCONSTRAINT PK_Ate PRIMARY KEY(Usi_comp_num, numDep)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Ville(\n" +
                "\tnom VARCHAR2(50) DEFAULT '' NOT NULL,\n" +
                "\tnum NUMBER(9) NOT NULL,\n" +
                "\tCONSTRAINT PK_Vil PRIMARY KEY(num)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Vil_dep_arr_Vil(\n" +
                "\tVil_dep_num NUMBER(9) NOT NULL,\n" +
                "\tVil_arr_num NUMBER(9) NOT NULL,\n" +
                "\tCONSTRAINT PK_Vil_dep_arr_Vil PRIMARY KEY(Vil_dep_num, Vil_arr_num)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Fournisseur(\n" +
                "\tnum NUMBER(9) DEFAULT '' NOT NULL,\n" +
                "\tCONSTRAINT PK_Four PRIMARY KEY(num)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE Four_fourni_Art(\n" +
                "\tFour_fourni_num NUMBER(9) NOT NULL,\n" +
                "\tArt_fourni_num NUMBER(9) NOT NULL,\n" +
                "\tCONSTRAINT PK_Four_fourni_Art PRIMARY KEY(Four_fourni_num, Art_fourni_num)\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE Article ADD CONSTRAINT FK1_Art_ArtCat_cont FOREIGN KEY(ArtCat_cont_num) REFERENCES ArtCategorie(num);\n" +
                "\n" +
                "ALTER TABLE ArtCategorie ADD CONSTRAINT FK1_ArtCat_ArtCat_div FOREIGN KEY(ArtCat_div_num) REFERENCES ArtCategorie(num);\n" +
                "\n" +
                "ALTER TABLE ArtAcquis ADD CONSTRAINT FK1_ArtAcq_Art_gs FOREIGN KEY(Art_gs_num) REFERENCES Article(num);\n" +
                "\n" +
                "ALTER TABLE Atelier ADD CONSTRAINT FK1_Ate_Usi_comp FOREIGN KEY(Usi_comp_num) REFERENCES Usine(num);\n" +
                "\n" +
                "ALTER TABLE Vil_dep_arr_Vil ADD CONSTRAINT FK1_Vil_dep_arr_Vil_Vil_dep FOREIGN KEY(Vil_dep_num) REFERENCES Ville(num);\n" +
                "\n" +
                "ALTER TABLE Vil_dep_arr_Vil ADD CONSTRAINT FK2_Vil_dep_arr_Vil_Vil_arr FOREIGN KEY(Vil_arr_num) REFERENCES Ville(num);\n" +
                "\n" +
                "ALTER TABLE Four_fourni_Art ADD CONSTRAINT FK1_fourni_Four_ FOREIGN KEY(Four_fourni_num) REFERENCES Fournisseur(num);\n" +
                "\n" +
                "ALTER TABLE Four_fourni_Art ADD CONSTRAINT FK2_fourni_Art_ FOREIGN KEY(Art_fourni_num) REFERENCES Article(num);");

        super.addContent(textArea);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public String getCodeSQL(){
        return textArea.getText();
    }

}
