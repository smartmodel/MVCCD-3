package mdr.orderbuildnaming;

import m.services.MElementService;
import mcd.MCDElement;
import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;

public class MDROrderService {

    public static String getPath(MCDElement mcdElement){
        String value = "";

        String pathMode = MElementService.PATHSHORTNAME;
        String separator = PreferencesManager.instance().preferences().getMDR_PATH_SEP_FORMAT();

        String path = mcdElement.getPathCustomized(pathMode, separator);

        if (StringUtils.isNotEmpty(path)){
            value = path + separator;
        }
        return value ;
    }
}
