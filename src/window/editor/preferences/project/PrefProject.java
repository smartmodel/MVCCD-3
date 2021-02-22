package window.editor.preferences.project;

public class PrefProject {

    private static PrefProject instance;


    public static synchronized PrefProject instance(){
        if(instance == null){
            instance = new PrefProject();
        }
        return instance;
    }





}
