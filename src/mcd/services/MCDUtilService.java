package mcd.services;

import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class MCDUtilService {

    public static void isEmpty(ArrayList<String> messages, String text,  String partMessage) {
        if (StringUtils.isEmpty(text)) {
            messages.add(MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[]{partMessage}));
        }
    }
}
