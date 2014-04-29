/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupavisioJ.DataFileXYEList;
import java.util.ArrayList;
import ij.*;
import java.io.*;
import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.DataFile.DataFile;

/**
 *
 * @author fbuga
 */
public class DataFileXYEList extends DataFile {
    private ADC adc=new ADC();
    
    public DataFileXYEList(String path){
      filePath=path;
    }
    
    public ADC open(){
      DataInputStream ips=null;
      try{
	ips=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath))); 
        adc.restoreXYEListFile(ips);
      }
      catch (FileNotFoundException e){
          IJ.log("fichier non trouvé ou erreur d'ouverture");
      }
      if(ips!=null){
        try{ips.close();}
        catch (IOException e2){
            IJ.log("Échec d'ouverture du fichier "+filePath);
        }
      }
      return adc;
    }
}
