package exceptions.service;

import console.ViewLogsManager;
import m.MElement;
import m.services.MElementService;
import main.MVCCDElement;
import main.MVCCDManager;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComponent;

import java.awt.*;

public class ExceptionService {


    public static void exceptionUnhandled(Exception e,
                                    Window window,
                                    MVCCDElement mvccdElement,
                                    String propertyMessage,
                                    String propertyAction) {

        try {
            String action = MessagesBuilder.getMessagesProperty(propertyAction);
            //String nameMVCCDElement = mvccdElement.getNameTree();
            String nameElement = mvccdElement.getName();
            if (mvccdElement instanceof MElement) {
                nameElement = ((MElement)mvccdElement).getNameTreePath();
            }
            String nameClassMVCCDElement = mvccdElement.getClass().getName();
            String message = MessagesBuilder.getMessagesProperty(propertyMessage,
                    new String[]{action, nameElement, nameClassMVCCDElement});
            ViewLogsManager.catchException(e, window, message);
        } catch (Exception eInterne){
            ViewLogsManager.catchException(eInterne, MVCCDManager.instance().getMvccdWindow(),
                    "ExceptionService.exceptionUnhandled() - Erreur dans la résolution de l'exception");
        }
    }

    public static void exceptionUnhandled(Exception e,
                                          Window window,
                                          PanelInputContent panelInputContent,
                                          MVCCDElement mvccdElement,
                                          String propertyMessage,
                                          String propertyAction) {
        try {
            String action = MessagesBuilder.getMessagesProperty(propertyAction);
            //String nameMVCCDElement = mvccdElement.getNameTree();
            String nameElement = mvccdElement.getName();
            if (mvccdElement instanceof MElement) {
                nameElement = ((MElement)mvccdElement).getNamePath();
            }
            String nameClassMVCCDElement = mvccdElement.getClass().getName();
            String message = MessagesBuilder.getMessagesProperty(propertyMessage,
                    new String[]{action, nameElement, nameClassMVCCDElement, panelInputContent.getClass().getName()});
            ViewLogsManager.catchException(e, window, message);
        } catch (Exception eInterne){
            ViewLogsManager.catchException(eInterne, MVCCDManager.instance().getMvccdWindow(),
                    "ExceptionService.exceptionUnhandled() - Erreur dans la résolution de l'exception");
        }

    }

}
