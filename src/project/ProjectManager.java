package project;

import main.MVCCDManager;

public class ProjectManager {

    private static ProjectManager instance;
    private static Project project;

    public static synchronized ProjectManager instance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        project = MVCCDManager.instance().getProject();
        return instance;
    }
}

    /*
    public  ProjectElement getElementById (int id) {
        return getElementById(project, id);
    }

    public  ProjectElement getElementById (ProjectElement projectElement, int id) {
        ProjectElement resultat = null;
        for (MVCCDElement mvccdElement : projectElement.getChilds()) {
            if (mvccdElement instanceof ProjectElement) {
                ProjectElement child = (ProjectElement) mvccdElement;
                if (child.getId() == id) {
                    resultat = child;
                } else {
                    if (resultat == null) {
                        resultat = getElementById(child, id);
                    }
                }
            }
        }
        return resultat;
    }

     */

    /*
    private ArrayList<ProjectElement> getProjectElementsByClassName(ProjectElement racine,
                                                                    String className){
        ArrayList<ProjectElement> resultat = new ArrayList<ProjectElement>();
        for (MVCCDElement element :  racine.getChilds()){
            if (element instanceof ProjectElement){

                if (element.getClass().getName().equals(className)){
                    resultat.add((ProjectElement) element);
                }
                resultat.addAll(getProjectElementsByClassName((ProjectElement) element, className));
            }
        }
        return resultat;

    }
*/
    /*
    public ArrayList<MCDEntity> getEntities(){
        ArrayList<MCDEntity> resultat =  new ArrayList<MCDEntity>();
        for (ProjectElement element :  getProjectElementsByClassName(project, MCDEntity.class.getName())){
            resultat.add((MCDEntity) element);
        }
        return resultat;
    }

    public MCDEntity getMCDEntityByName(String name){
        for (MCDEntity mcdEntity: getEntities()){
            if (mcdEntity.getName().equals(name)){
                return mcdEntity;
            }
        }
        return null;
    }
*/




