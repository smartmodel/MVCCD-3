package mcd.services;

import mcd.MCDEntity;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.Project;

import java.util.ArrayList;

public class MCDProjectService {


    public static ArrayList<String> check(Project project) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.addAll(checkName(project.getName()));
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.PROJECT_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "project.and.name");
    }

}
