package mpdr.tapis;

import mpdr.MPDRColumn;
import mpdr.interfaces.IMPDRElement;
import mpdr.tapis.services.MPDRColumnsJnalService;
import project.ProjectElement;

public abstract class MPDRColumnJnalDatas extends MPDRColumnJnal{

    private static final long serialVersionUID = 1000;



    public MPDRColumnJnalDatas(ProjectElement parent,
                          IMPDRElement mpdrElementSource) {
        super(parent, mpdrElementSource);
    }


    public MPDRColumn getMpdrColumnSource(){
        if (getMpdrElementSource() instanceof MPDRColumn){
            return (MPDRColumn) getMpdrElementSource();
        }
        return null;
    }


    public int compareToDefault(MPDRColumnJnal other) {
        return MPDRColumnsJnalService.compareToDefault(this, other);
    }


}
