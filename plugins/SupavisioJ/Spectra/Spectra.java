package SupavisioJ.Spectra;
import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.ConvertListFiles.ADC.ADC;
import java.util.ArrayList;
import ij.*;
import java.util.Arrays;
import ij.gui.Plot;
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
  ADC adc;
  double[] yEvt;
  float energyMin=0;
  int channelMinim=0;
  Float stepEnergy=null;//value of energy between two channels
  int resX=0; // useful if Spectra generated from an ImageGenerated
  int resY=0; // useful if Spectra generated from an ImageGenerated
        
  public Spectra(ADC adc) {
    this.adc=adc;
    yEvt=adc.getSpectra();
    int i = yEvt.length-1;
    while (yEvt[i]<1){
      i--;
    }
    yEvt=Arrays.copyOfRange(yEvt, 0, i+1);
  } 
  
  public Spectra(ADC adc, float energyMin, boolean startAtEnergMin) {
    this(adc);
    if (startAtEnergMin){
      this.channelMinim=getIndiceEnergy(energyMin,false);
      yEvt=Arrays.copyOfRange(yEvt, channelMinim, yEvt.length);
    }
    this.energyMin=energyMin;
    IJ.log(String.valueOf(yEvt.length));
  }
  
  public Spectra(ADC adc,ImageGenerated imgGen, float energyMin, boolean startAtEnergMin) {
    this(adc,energyMin,startAtEnergMin);
    resX=imgGen.getWidth()-1;
    resY=imgGen.getHeight()-1;
  }

  public ADC getADC (){
    return adc;
  }
  
  public int getChannelMin(){
      return channelMinim;
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
        return null; // Or throw an exception - your choice
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
        IJ.log(String.valueOf(startEndInt[i][0])+" "+String.valueOf(startEndInt[i][1]));
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
    }
    return arrayOfImgGen;
  }
  
}