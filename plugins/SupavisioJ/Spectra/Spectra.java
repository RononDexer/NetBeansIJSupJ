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
  Float stepEnergy=null;//value of energy between two channels
        
  public Spectra(ADC adc) {
    this.adc=adc;
    yEvt=adc.getSpectra();
    int i = yEvt.length-1;
    while (yEvt[i]<1){
      i--;
    }
    yEvt=Arrays.copyOfRange(yEvt, 0, i+1);
  } 

  public ADC getADC (){
      return adc;
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
      XYPlotSp plot1=new XYPlotSp(titleWindow,titleGraph,xEnergies,yEvt);
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
    int resX=searchMax("x");
    int resY=searchMax("y");
    int indMin= getIndiceEnergy(start,false);
    int indMax= getIndiceEnergy(end,true);
    double[] valNbEventPerXY= new double[(resX+1)*(resY+1)];
    for (int i=0;i<adc.getNEvents();i++){
      int[] event=adc.getEvent(i);
      if (event[2]>=indMin && event[2]<=indMax){
        valNbEventPerXY[event[0]+event[1]*(resX+1)]+=1;
      }
    }
    IJ.log("Génération de l'image");
    ImageGenerated img= new ImageGenerated(this,valNbEventPerXY,start,end,resX,resY);
    return img;
  }
  
}