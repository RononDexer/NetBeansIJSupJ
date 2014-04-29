package SupavisioJ.DataFile;
import SupavisioJ.ConvertListFiles.ADC.ADC;

/**
 *
 * @author fbga
 */
public abstract class DataFile {
    protected String filePath;

    public String getDirectoryAndName(){
        return filePath.substring(0,filePath.lastIndexOf("."));
    }
    
    public String getName(){
        int index=filePath.lastIndexOf("/")+1;
        if (index==0)
            index=filePath.lastIndexOf("\\")+1;
        return filePath.substring(index,filePath.lastIndexOf("."));
    }

    public String getPath(){
        return filePath;
    }

    public void setPath(String path){
        filePath=path;
    }

    public abstract ADC open();
}

