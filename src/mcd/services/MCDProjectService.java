package mcd.services;

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
                Preferences.NAME_REGEXPR, "naming.of.name", "of.project");
    }

}
