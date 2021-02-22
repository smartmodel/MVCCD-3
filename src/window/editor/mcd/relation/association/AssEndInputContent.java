package window.editor.mcd.relation.association;

import mcd.MCDAssEnd;
import mcd.MCDEntity;
import mcd.services.MCDAssEndService;
import mcd.services.MCDUtilService;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.editor.PanelInputContent;
import utilities.window.scomponents.SComponent;
import utilities.window.scomponents.STextField;

import java.util.ArrayList;

public class AssEndInputContent  {


    public boolean checkRoleName(AssociationInputContent panelInputContent,
                                 STextField fieldRoleName,
                                 MCDEntity mcdEntity,
                                 MCDAssEnd mcdAssEnd,
                                 MCDEntity mcdEntityOpposite,
                                 boolean unitaire,
                                 int direction) {

        return panelInputContent.checkInput(fieldRoleName, unitaire, MCDAssEndService.checkRoleNameId(
                mcdEntity,
                mcdAssEnd,
                mcdEntityOpposite,
                fieldRoleName.getText(),
                false,
                Preferences.ASSEND_ROLE_NAME_LENGTH,
                buildContextMessage(direction)));


    }

    public boolean checkRoleShortName(AssociationInputContent panelInputContent,
                                 STextField fieldRoleShortName,
                                 MCDEntity mcdEntity,
                                 MCDAssEnd mcdAssEnd,
                                 MCDEntity mcdEntityOpposite,
                                 boolean unitaire,
                                 int direction) {

        return panelInputContent.checkInput(fieldRoleShortName, unitaire, MCDAssEndService.checkRoleShortNameId(
                mcdEntity,
                mcdAssEnd,
                mcdEntityOpposite,
                fieldRoleShortName.getText(),
                false,
                Preferences.ASSEND_ROLE_NAME_LENGTH,
                buildContextMessage(direction)));


    }

    public boolean checkRoleShortName(PanelInputContent panelInputContent,
                                      STextField fieldRoleShortName,
                                      boolean unitaire,
                                      int direction) {
        return panelInputContent.checkInput(fieldRoleShortName, unitaire, MCDUtilService.checkString(
                fieldRoleShortName.getText(),
                false,
                Preferences.ASSEND_ROLE_SHORT_NAME_LENGTH,
                Preferences.NAME_REGEXPR,
                "naming.of.short.name",
                buildContextMessage(direction)));
    }

    public boolean checkRoleNaming(PanelInputContent panelInputContent,
                                   STextField fieldRoleName,
                                   STextField fieldRoleShortName,
                                   boolean unitaire, int from) {


        boolean c1 = StringUtils.isEmpty(fieldRoleName.getText());
        boolean c2 = StringUtils.isEmpty(fieldRoleShortName.getText());
        ArrayList<String> messagesErrors = new ArrayList<String>();
        if ( !c1 && c2 ){
                 String message = MessagesBuilder.getMessagesProperty("association.role.name.and.short.name.error");
                messagesErrors.add(message);
        }
        if ( c1 && !c2 ){
                 String message = MessagesBuilder.getMessagesProperty("association.role.short.name.only.error");
                messagesErrors.add(message);
        }
        if (unitaire) {
            panelInputContent.showCheckResultat(messagesErrors);
        }

        if (messagesErrors.size() != 0){
            fieldRoleShortName.setColor(SComponent.COLORWARNING);
        } else {
            fieldRoleShortName.setColor(SComponent.COLORNORMAL);
        }

        return messagesErrors.size() == 0;

    }


    public String buildContextMessage(int direction){
        if ( direction == MCDAssEnd.FROM){
            return "of.role.from";
        }
        if ( direction == MCDAssEnd.TO){
            return "of.role.to";
        }
        return null;
    }

}
