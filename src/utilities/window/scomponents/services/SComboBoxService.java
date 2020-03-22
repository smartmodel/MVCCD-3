package utilities.window.scomponents.services;

import utilities.window.scomponents.SComboBox;
import utilities.window.scomponents.STextField;

public class SComboBoxService {

    public static void selectByText(SComboBox sComboBox, String text) {
        for (int i=0 ; i <  sComboBox.getItemCount(); i++){
            if ( sComboBox.getItemAt(i).equals(text)){
                sComboBox.setSelectedIndex(i);
            }
        }
    }

}

