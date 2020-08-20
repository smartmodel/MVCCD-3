package mcd.services;

import m.MRelEndMultiPart;
import mcd.*;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import project.ProjectElement;
import project.ProjectService;
import utilities.window.scomponents.STable;
import utilities.window.scomponents.services.STableService;
import window.editor.operation.OperationParamTableColumn;

import java.util.ArrayList;

public class MCDUniqueService {

    public static ArrayList<String> checkParameters(MCDUnique mcdUnique,
                                                    STable table,
                                                    String contextProperty,
                                                    String rowTargetProperty) {

        ArrayList<String> messages = new ArrayList<String>();
        String contextMessage = MessagesBuilder.getMessagesProperty(contextProperty);
        String rowTargetMessage = MessagesBuilder.getMessagesProperty(rowTargetProperty);

        MCDEntity mcdEntity = (MCDEntity) mcdUnique.getParent().getParent();

        messages.addAll(MCDOperationService.checkParametersTargets(table,
                contextMessage,
                rowTargetMessage));

        // Au moins un attribut optionnel ou
        if (table.getRowCount() >= 1) {
            boolean oneAttributeOptional = false;
            for (int line = 0; line < table.getRowCount(); line++) {
                IMCDParameter target = MCDParameterService.getTargetByTypeAndNameTree(mcdEntity,
                        (String) table.getValueAt(line, OperationParamTableColumn.TYPE.getPosition()),
                        (String) table.getValueAt(line, OperationParamTableColumn.NAME.getPosition()));
                if (target instanceof MCDAttribute) {
                    MCDAttribute mcdAttribute = (MCDAttribute) target;
                    if (!mcdAttribute.isMandatory()) {
                        oneAttributeOptional = true;
                    }
                }
            }
            if ( ! oneAttributeOptional) {
                messages.add(MessagesBuilder.getMessagesProperty(
                        "editor.unique.table.parameter.attribute.not.optional"
                        , new String[]{contextMessage}));
            }
        }
        return messages;
    }

    }
