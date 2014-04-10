package SupavisioJ.DataFile;
import SupavisioJ.ConvertListFiles.ADC.ADC;

/**
 *
 * @author fbga
 */
public abstract class DataFile {
    protected String filePath;


    public String getPath(){
        return filePath;
    }

    public void setPath(String path){
        filePath=path;
    }

    public abstract ADC open();
}

