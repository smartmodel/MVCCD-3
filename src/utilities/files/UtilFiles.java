package utilities.files;

import main.MVCCDManager;
import org.apache.commons.lang.StringUtils;

import java.io.File;

public class UtilFiles {

    public static String getStrDirectory(File file){
        String directory = StringUtils.removeEnd(file.getPath(), file.getName());
        directory = StringUtils.substring(directory, 0, directory.length()-1);
        return directory;
    }

    public static File getDirectory(File file){
        return new File(getStrDirectory(file));
    }
}
