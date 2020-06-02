package mcd.services;

import messages.MessagesBuilder;
import utilities.window.scomponents.STable;
import utilities.window.scomponents.services.STableService;

import java.util.ArrayList;

public class MCDOperationService {

    public static ArrayList<String> checkTargets(STable table,
                                                 String contextProperty,
                                                 String rowTargetProperty) {

        ArrayList<String> messages = new ArrayList<String>();
        String contextMessage = MessagesBuilder.getMessagesProperty(contextProperty);
        String rowTargetMessage = MessagesBuilder.getMessagesProperty(rowTargetProperty);
        if (table.getRowCount() > 1) {
           for (int i = 0; i < table.getRowCount(); i++) {
               for (int j = i + 1; j < table.getRowCount(); j++) {
                   if (table.getValueAt(i, STableService.IDINDEX) == table.getValueAt(j, STableService.IDINDEX)) {
                       messages.add(MessagesBuilder.getMessagesProperty(
                               "editor.operation.table.parameter.target.redondant"
                               , new String[]{contextMessage, rowTargetMessage}));
                   }
               }
           }
        }
        return messages;
    }
}
