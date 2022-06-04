package mpdr.tapis;

import mdr.MDRColumn;
import mdr.MDRContColumns;
import project.ProjectElement;

import java.util.ArrayList;

public class MPDRContColumnsJnal extends MDRContColumns {

    // Un éventuel tri ne peut pas se faire en héritant de la méthode du parent MDRContColumns

    private  static final long serialVersionUID = 1000;

    public MPDRContColumnsJnal(ProjectElement parent, String name) {
        super(parent, name);
    }


    public ArrayList<MDRColumn> getMDRColumnsSortDefault(){
        return getMDRColumns();
    }

}
