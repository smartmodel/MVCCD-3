package mpdr.services;

import mdr.services.MDRColumnsService;
import mpdr.MPDRColumn;
import mpdr.MPDRIndex;

import java.util.ArrayList;

public class MPDRIndexService {

    public static ArrayList<MPDRColumn> getMPDRColumns(MPDRIndex mpdrIndex) {
        return MDRColumnsService.toMPDRColumns(mpdrIndex.getMDRColumns());
    }
}
