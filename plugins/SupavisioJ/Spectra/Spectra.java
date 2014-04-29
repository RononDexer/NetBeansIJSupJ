package SupavisioJ.Spectra;
import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.MainFrame.MainFrame;
import java.util.ArrayList;
import ij.*;
import java.util.Arrays;
import ij.gui.Plot;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFrame;
import lib.XYPlotSp;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Spectra {
  private ADC adc;
  private double[] yEvt;
  private float energyMin=0;
  private int channelMinim=0;
  private Float stepEnergy=null;//value of energy between two channels
  private int resX=0; // useful if Spectra generated from an ImageGenerated
  private int resY=0; // useful if Spectra generated from an ImageGenerated
  private String directory=null;// useful for saving and restoring
  private String nameFile =null; //if spectra open from a file or a parent spectra
  private int level=0; //to know if the spectra has been produced from a spectra or a file (if file : level=0)
  private MainFrame parentWindow;
  private ArrayList<ImageGenerated> imgsGenProduced = new ArrayList<ImageGenerated>();
  
  public Spectra(String path, MainFrame parentWindow){ //useful for restoring
      restore(path,parentWindow);
  }
  
  public Spectra(ADC adc,String nameFile) {
    this.adc=adc;
    this.nameFile=nameFile;
    yEvt=adc.getSpectra();
    int i = yEvt.length-1;
    while (yEvt[i]<1){
      i--;
    }
    yEvt=Arrays.copyOfRange(yEvt, 0, i+1);
  } 
  
  public Spectra(ADC adc, String nameFile, float energyMin, boolean startAtEnergMin) {//to use if spectra don't begin at channel 0
    this(adc,nameFile);
    if (startAtEnergMin){
      this.channelMinim=getIndiceEnergy(energyMin,false);
      yEvt=Arrays.copyOfRange(yEvt, channelMinim, yEvt.length);
    }
    this.energyMin=energyMin;
  }

  public Spectra(ADC adc,String nameFile,ImageGenerated imgGen) {
    this(adc,nameFile);
    resX=imgGen.getWidth()-1;
    resY=imgGen.getHeight()-1;
  }
  
  public Spectra(ADC adc,String nameFile,ImageGenerated imgGen, float energyMin, boolean startAtEnergMin) {
    this(adc,nameFile,energyMin,startAtEnergMin);
    resX=imgGen.getWidth()-1;
    resY=imgGen.getHeight()-1;
  }

  public ADC getADC (){
    return adc;
  }
  
  public String getDirectory(){
      return directory;
  }
  
  public String getFileName(){
      if (level==0)
        return nameFile;
      return nameFile+"-"+String.valueOf(level);
  }
  
  public int getChannelMin(){
      return channelMinim;
  }
  
  public boolean isSaved(){
      return directory!=null;
  }
  
  public int getLevel(){
      return level;
  } 
   
  public void setLevel(int level){
      this.level=level;
  }
  
  public void setParentWindow(MainFrame parentWindow){
      this.parentWindow=parentWindow;
      parentWindow.addSpectra(this);
  }
  
  public MainFrame getParentWindow(){
      return parentWindow;
  }
  
  public boolean energyExist(float energy){
    if (energy<energyMin)
        return false;
    float energyMax;
    if (stepEnergy==null){
        energyMax = energyMin + (yEvt.length-1) * 1;
    }
    else {
        energyMax = energyMin + (yEvt.length-1) * stepEnergy;
    }
    if (energy>energyMax)
        return false;
    return true;
  }
  
  public void calibEnergy(float energyMin, float stepEnergy){
    this.energyMin=energyMin;
    this.stepEnergy=stepEnergy;
  }

  public float[] getEnergies(){
    float[] energies= new float[yEvt.length];
    energies[0]=energyMin;
    float step;
    if (stepEnergy==null){step=1;}
    else{step=stepEnergy;}
    for (int i=1;i<energies.length;i++){
        energies[i]=energies[i-1]+step;
    }
    return energies;
  }
  
  public double[] convertFloatsToDoubles(float[] input){
    if (input == null){
        return null; 
    }
    double[] output = new double[input.length];
    for (int i = 0; i < input.length; i++){
        output[i] = (double) input[i];
    }
    return output;
  }
  
  public XYPlotSp plotSpectra(String titleWindow, String titleGraph){
    double[] xEnergies = convertFloatsToDoubles(getEnergies());
    XYPlotSp plot1=new XYPlotSp(this,titleWindow,titleGraph,xEnergies,yEvt);
    return plot1;//plot1.showVisible()
  }

  public int getIndiceEnergy(float energyToSearch,boolean rightIncluded){
    float[] energies=getEnergies();
    if (rightIncluded){
      int i=0;
      while(energies[i]<energyToSearch){
        i++;
      }
      return i;
    }
    else {
      int i=energies.length-1;
      while(energies[i]>energyToSearch){
        i--;
      }
      return i;
    }
  }

  public int searchMax(String valToSearch){
    int elemt;
    if (valToSearch.equals("x")){
      elemt=0; 
    }
    else if(valToSearch.equals("y")){
      elemt=1;
    }
    else{
      return -1;
    }
    int max=0;
    for (int i=0;i<adc.getNEvents();i++){
      int[] event=adc.getEvent(i);
      if(event[elemt]>max){max=event[elemt];}
    }
    return max;
  }
  
  public ImageGenerated generatePicture(float start,float end){
    if (resX==0)
        resX=searchMax("x");
    if (resY==0)
        resY=searchMax("y");
    int indMin= getIndiceEnergy(start,false)+channelMinim;
    int indMax= getIndiceEnergy(end,true)+channelMinim;
    double[] valNbEventPerXY= new double[(resX+1)*(resY+1)];
    for (int i=0;i<adc.getNEvents();i++){
      int[] event=adc.getEvent(i);
      if (event[2]>=indMin && event[2]<=indMax){
        valNbEventPerXY[event[0]+event[1]*(resX+1)]+=1;
      }
    }
    ImageGenerated img= new ImageGenerated(this,valNbEventPerXY,start,end,resX,resY);
    imgsGenProduced.add(img);
    return img;
  }
  
  public ImageGenerated[] generatePicture(float[][] startEnd){
    if (resX==0)
        resX=searchMax("x");
    if (resY==0)
        resY=searchMax("y");
    int[][] startEndInt = new int[startEnd.length][2];
    for(int i=0; i<startEnd.length;i++){
        startEndInt[i][0]= getIndiceEnergy(startEnd[i][0],false)+channelMinim;
        startEndInt[i][1]= getIndiceEnergy(startEnd[i][1],true)+channelMinim;
    }
    double[][] valNbEventPerXY= new double[startEnd.length][(resX+1)*(resY+1)];
    for (int i=0;i<adc.getNEvents();i++){
      int[] event=adc.getEvent(i);
      for (int j=0; j<startEnd.length;j++){
        int indMin = startEndInt[j][0];
        int indMax = startEndInt[j][1];
        if (event[2]>=indMin && event[2]<=indMax){
          valNbEventPerXY[j][event[0]+event[1]*(resX+1)]+=1;
        }
      }
    }
    ImageGenerated[] arrayOfImgGen = new ImageGenerated[startEnd.length];
    for (int i=0; i<startEnd.length;i++){
        float start = startEnd[i][0];
        float end = startEnd[i][1];
        arrayOfImgGen[i]= new ImageGenerated(this,valNbEventPerXY[i],start,end,resX,resY);
        imgsGenProduced.add(arrayOfImgGen[i]);
    }
    return arrayOfImgGen;
  }
  
  
   public String getNameToSave(){
       return getFileName()+".spct.spj";
   }
  
  
    public void save(String directory){
        if (isSaved() && this.directory.equals(directory))
            return;
        this.directory=directory;
        String nameToSave=getNameToSave();
        DataOutputStream file = null;
        try {
            file = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(directory+nameToSave)));
            file.writeFloat(energyMin);
            file.writeInt(channelMinim);
            if (stepEnergy==null){
                file.writeFloat(-1);
            }
            else{
                file.writeFloat(stepEnergy);
            }
            file.writeInt(resX);
            file.writeInt(resY);
            adc.saveXYEListFile(file);
        } 
        catch(FileNotFoundException e){
            IJ.log("Echec de la sauvegarde dans "+directory);
        }
        catch(IOException e2){
            IJ.log("Echec de la sauvegarde");
        }
        try{
            if (file!=null) {
               file.close();
            }   
        }
        catch(IOException e){
            IJ.log("Echec de la sauvegarde");
        }
    }
    
    public String[] saveAllImgGen(String directory){
        String[] namesImgSaved = new String[imgsGenProduced.size()];
        for(int i=0;i<imgsGenProduced.size();i++){
            imgsGenProduced.get(i).save(directory);
            namesImgSaved[i]=imgsGenProduced.get(i).getNameToSave();
        }
        return namesImgSaved;
    }
    
    private void restore(String path, MainFrame parentWindow){
        try{
            DataInputStream ips=new DataInputStream(new BufferedInputStream(new FileInputStream(path)));                 
            energyMin=ips.readFloat();
            channelMinim=ips.readInt();
            float stepEnergyTmp=ips.readFloat();
            if (stepEnergyTmp>-0.5){
                stepEnergy=stepEnergyTmp;
            }
            else{
                stepEnergy=null;
            }
            resX=ips.readInt();
            resY=ips.readInt();
            adc = new ADC();
            adc.restoreXYEListFile(ips);
            int index=path.lastIndexOf("/")+1;
            if (index==0)
                index=path.lastIndexOf("\\")+1;
            directory=path.substring(0,index);
            if (path.lastIndexOf("-")!=-1){
                try{
                    nameFile = path.substring(index, path.lastIndexOf("-"));
                    String levelStr=path.substring(path.lastIndexOf("-")+1, path.lastIndexOf("."));
                    levelStr=levelStr.substring(0,levelStr.lastIndexOf("."));
                    level=Integer.valueOf(levelStr);
                }
                catch (NumberFormatException e){
                    nameFile = path.substring(index, path.lastIndexOf("."));
                    nameFile=nameFile.substring(0,path.lastIndexOf("."));
                    level=0;
                }
            }
            else{
                nameFile = path.substring(index, path.lastIndexOf("."));
                nameFile=nameFile.substring(0,nameFile.lastIndexOf("."));
                level=0;
            }
            setParentWindow(parentWindow);
            ips.close();
            yEvt=adc.getSpectra();
            int i = yEvt.length-1;
            while (yEvt[i]<1){
              i--;
            }
            yEvt=Arrays.copyOfRange(yEvt, channelMinim, i+1);
        }
        catch(FileNotFoundException e){}
        catch(IOException e2) {}
    }
    
    public ImageGenerated restoreImgGen(String path){
        try{
            DataInputStream ips=new DataInputStream(new BufferedInputStream(new FileInputStream(path)));                 
            float start=ips.readFloat();
            float end=ips.readFloat();
            ips.close();
            String titleImg = path.substring(path.lastIndexOf("_")+1, path.lastIndexOf("."));
            titleImg=titleImg.substring(0,titleImg.lastIndexOf("."));
            ImageGenerated imgGen= generatePicture(start,end);
            imgGen.show(titleImg);
            return imgGen;
        }
        catch(FileNotFoundException e){}
        catch(IOException e2) {}
        return null; 
    }    
       
}