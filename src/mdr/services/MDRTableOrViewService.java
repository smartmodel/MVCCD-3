package mdr.services;

import mdr.MDRTableOrView;

public class MDRTableOrViewService {



    public static String getListColumnsAsString(MDRTableOrView mdrTableOrView,
                                                String separator,
                                                boolean withTableOrView){
        return MDRColumnsService.getListColumnsAsString(mdrTableOrView.getMDRColumnsSortDefault(), separator,
                withTableOrView);
    }

}
