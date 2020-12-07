package mcd.transform;

import mdr.MDRColumn;
import mdr.MDROperation;
import mdr.MDRParameter;
import mdr.MDRTableOrView;
import mdr.interfaces.IMDRParameter;

import java.util.ArrayList;

public abstract class MDRAdjustParameters {


    public void adjustParameters(MDRTableOrView mdrTableOrView,
                                 MDROperation mdrOperation,
                                 ArrayList<MDRColumn> mdrColumns) {
        // Suppresion des paramètres référant une colonne qui n'existe plus
        deleteParameters(mdrTableOrView, mdrOperation);

        // Ajout des paramètres référant une nouvelle colonne
        addParameters(mdrTableOrView, mdrOperation, mdrColumns);

        // Modification des noms de colonne
        // rien à faire car le paramètre réfère la colonne

    }

    protected abstract void addParameters(MDRTableOrView mdrTableOrView,
                                          MDROperation mdrOperation,
                                          ArrayList<MDRColumn> mdrColumns);


    private void deleteParameters(MDRTableOrView mdrTableOrView, MDROperation mdrOperation) {
        // Suppression des paramètres référant des cibles (colonnes) qui n'existent plus
        ArrayList<MDRParameter> mdrParameters = mdrOperation.getMDRParameters();
        if (mdrParameters.size() > 0) {
            for (int i = mdrParameters.size() - 1; i >= 0; i--) {
                IMDRParameter imdrParameter = mdrParameters.get(i).getTarget();
                if (imdrParameter instanceof MDRColumn) {
                    MDRColumn mdrColumn = (MDRColumn) imdrParameter;
                    if (!mdrTableOrView.existColumn(mdrColumn)) {
                        mdrParameters.remove(i);
                    }
                }
            }
        }
    }



    }
