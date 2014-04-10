/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupavisioJ.DataFilePIXE;
import java.util.ArrayList;
import ij.*;
import java.io.*;
import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.DataFile.DataFile;

/**
 *
 * @author fbuga
 */
public class DataFilePIXE extends DataFile {
    private ADC adc=null;
    
    public DataFilePIXE(String path){
      filePath=path;
    }
    
    public ADC open(){
      ADC adc1=new ADC();
      DataInputStream ips=null;
      try{
	ips=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));  
	int[] evt = new int[3];
	while(true){
	  evt[0]=ips.readShort();
	  evt[1]=ips.readShort();
	  evt[2]=ips.readInt();
	  IJ.log("Ajout d'un événement à "+evt[0]+" , "+evt[1]+" avec une energie de canal : "+evt[2]);
	  adc1.addEvent(evt);
	}
      }
      catch (Exception e){
        try{ips.close();}
        catch (Exception IOError){
            IJ.log("Échec d'ouverture du fichier "+filePath);
        }
      }
	return adc1;
    }
}
