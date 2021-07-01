package mcd.services;

import mcd.MCDAttribute;
import mcd.MCDEntity;
import mcd.MCDNID;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import utilities.window.scomponents.STable;
import window.editor.mcd.operation.OperationParamTableColumn;

import java.util.ArrayList;

public class MCDNIDService {

    public static ArrayList<String> checkParameters(MCDNID mcdNID,
                                                    STable table,
                                                    String contextProperty,
                                                    String rowTargetProperty) {

        ArrayList<String> messages = new ArrayList<String>();
        String contextMessage = MessagesBuilder.getMessagesProperty(contextProperty);
        String rowTargetMessage = MessagesBuilder.getMessagesProperty(rowTargetProperty);

        messages.addAll(MCDOperationService.checkParametersTargets(table,
                contextMessage,
                rowTargetMessage));

        // Au moins un attribut obligatoire
        boolean oneAttributeMandatory = false;
        if (table.getRowCount() >= 1) {
            MCDEntity mcdEntity = (MCDEntity) mcdNID.getParent().getParent();
            for (int line = 0; line < table.getRowCount(); line++) {
                IMCDParameter target = MCDParameterService.getTargetByTypeAndNameTree(mcdEntity,
                        (String) table.getValueAt(line, OperationParamTableColumn.TYPE.getPosition()),
                        (String) table.getValueAt(line, OperationParamTableColumn.NAME.getPosition()));
                if (target instanceof MCDAttribute) {
                    MCDAttribute mcdAttribute = (MCDAttribute) target;
                    if (mcdAttribute.isMandatory()) {
                        oneAttributeMandatory = true;
                    }
                }
            }
            if ( ! oneAttributeMandatory) {
                        messages.add(MessagesBuilder.getMessagesProperty(
                                "editor.nid.table.parameter.attribute.not.mandatory"
                                , new String[]{contextMessage}));
             }
        }

        //TODO-1 Tester que d'autres paramètres que des attributs ne soient pas présents
        // dans la perspective de l'import d'un fichier XML


        return messages;
    }

}
