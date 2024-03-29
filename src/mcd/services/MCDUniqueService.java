package mcd.services;

import m.MRelEndMultiPart;
import mcd.MCDAssEnd;
import mcd.MCDAttribute;
import mcd.MCDEntity;
import mcd.MCDUnique;
import mcd.interfaces.IMCDParameter;
import messages.MessagesBuilder;
import utilities.window.scomponents.STable;
import window.editor.mcd.operation.OperationParamTableColumn;

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

        // Au moins un attribut o une extrémité d'association optionnelle
        if (table.getRowCount() >= 1) {
            boolean oneAttributeOptional = false;
            boolean oneAssEndOptional = false;
            for (int line = 0; line < table.getRowCount(); line++) {
                IMCDParameter target = MCDParameterService.getTargetByTypeAndNameTarget(mcdEntity,
                        (String) table.getValueAt(line, OperationParamTableColumn.TYPE.getPosition()),
                        (String) table.getValueAt(line, OperationParamTableColumn.NAME.getPosition()));
                if (target instanceof MCDAttribute) {
                    MCDAttribute mcdAttribute = (MCDAttribute) target;
                    if (!mcdAttribute.isMandatory()) {
                        oneAttributeOptional = true;
                    }
                }
                if (target instanceof MCDAssEnd) {
                    MCDAssEnd mcdAssEnd = (MCDAssEnd) target;
                    if (mcdAssEnd.getMultiMinStd() == MRelEndMultiPart.MULTI_ZERO) {
                        oneAssEndOptional = true;
                    }
                }
            }
            if ( ! (oneAttributeOptional || oneAssEndOptional)) {
                messages.add(MessagesBuilder.getMessagesProperty(
                        "editor.unique.table.parameter.attribute.not.optional"
                        , new String[]{contextMessage}));
            }
        }
        return messages;
    }

    }
