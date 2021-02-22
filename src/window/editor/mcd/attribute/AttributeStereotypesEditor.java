package window.editor.mcd.attribute;

import mcd.MCDAttribute;
import org.apache.commons.lang.StringUtils;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
import stereotypes.StereotypesManager;
import utilities.UtilDivers;
import utilities.window.editor.shuttle.ShuttleDialog;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class AttributeStereotypesEditor {

    ShuttleDialog shuttleDialog ;
    JTextField textField;
    String attributeName;
    ArrayList<String> stereotypesUMLNames;



    public AttributeStereotypesEditor(JTextField textField, String attributeName){
        this.textField = textField;
        this.attributeName = attributeName;


        // Stéréotypes applicables à un attribut
        ArrayList<Stereotype> stereotypes = StereotypesManager.instance().stereotypes().getStereotypesByClassName(MCDAttribute.class.getName());
        stereotypesUMLNames = StereotypeService.getUMLNamesBySterotypes(stereotypes);

        Collections.sort(stereotypesUMLNames);
        init();
    }



    public void init() {
        shuttleDialog = new ShuttleDialog (" Choix des stéréotypes", null);
        String stereotypesCrt = textField.getText();
        ArrayList<String> tabCrtDatas = StereotypeService. getArrayListFromNamesStringTagged(stereotypesCrt, true);
        //ArrayList<String> tabCrtDatas = new ArrayList();
        //tabCrtDatas.add(stereotypesCrt);
        shuttleDialog.putDatas(stereotypesUMLNames, tabCrtDatas);
        if (StringUtils.isEmpty(attributeName)) {
            attributeName = "Nouveau";
        }
        shuttleDialog.setTitle("Stéréotypes attribut :  " + attributeName);
        shuttleDialog.setPosition(textField.getLocationOnScreen());
        shuttleDialog.setVisible(true);
        shuttleDialog.getResultat();
        ArrayList<String> stereotypesNames = shuttleDialog.getResultat();
        textField.setText(UtilDivers.ArrayStringToString(stereotypesNames,""));
        //dialog.setCrtDatas(tabCrtDatas);
        //fireEditingStopped(); //Make the renderer reappear.
    }



}


