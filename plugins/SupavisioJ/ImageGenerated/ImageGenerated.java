package SupavisioJ.ImageGenerated;

import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.Spectra.Spectra;
import SupavisioJ.CustomWindowImage.CustomWindowImage;
import ij.*;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import ij.process.FloatProcessor;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator 
 */
public class ImageGenerated {
  Spectra sourceSpectra;
  double[] sourcePixels;
  ImageProcessor imageProc;
  float startSpectra;
  float endSpectra;//value of energy between two channels
  String title;
  CustomWindowImage imgWindow;
  
  
  public ImageGenerated(Spectra spectra,double[] valNbEventPerXY,float start, float end,int resX,int resY) {
    sourceSpectra=spectra;
    sourcePixels=valNbEventPerXY;
    imageProc = new FloatProcessor(resY+1,resX+1,valNbEventPerXY);
    startSpectra=start;
    endSpectra=end;
  }
  
  public int getWidth(){
      return imageProc.getWidth();
  }
          
  public int getHeight(){
      return imageProc.getHeight();
  }
  
  public void show(String title){
    this.title=title;
    show();
  }
  
  private void show(){
    ImagePlus ipOfImageGen = new ImagePlus(sourceSpectra.getFileName()+"_"+title,imageProc);
    imgWindow = new CustomWindowImage(ipOfImageGen,this);
    ImageCanvas icOfImageGen = imgWindow.getCanvas();
    icOfImageGen.requestFocus();
  }
    
  public ImageProcessor getIrregularRoi(){
    return imgWindow.getImagePlus().getMask();
  }
  
  public Roi getRegularRoi(){
    return imgWindow.getImagePlus().getRoi();
  }
  

  public Spectra generateSpectraFromRoi(){
      Roi ipRoi = getRegularRoi();
      if(ipRoi!=null){
        ADC adcToCalcFromRoi = new ADC();
        ADC sourceAdc = sourceSpectra.getADC();
        int channelMin=sourceSpectra.getIndiceEnergy(startSpectra, false)+sourceSpectra.getChannelMin();
        int channelMax=sourceSpectra.getIndiceEnergy(endSpectra, true)+sourceSpectra.getChannelMin();
        for (int nbEvt=0; nbEvt<sourceAdc.getNEvents(); nbEvt++){
            int[] currentEvt= sourceAdc.getEvent(nbEvt);
            int xPix = currentEvt[0];
            int yPix = currentEvt[1];
            int channelEnerPix = currentEvt[2];
              if (ipRoi.contains(xPix,yPix)){
                  adcToCalcFromRoi.addEvent(currentEvt);
              }
        }   
        String nameFile=sourceSpectra.getFileName();
        Spectra spectreNewCalc= new Spectra(adcToCalcFromRoi,nameFile,this);
        spectreNewCalc.setLevel(sourceSpectra.getLevel()+1);
        spectreNewCalc.setParentWindow(sourceSpectra.getParentWindow());
        return spectreNewCalc;
      }
      else{
          IJ.log("Veuillez faire une sélection");
          return null;
      }
  }
  
  public String getNameToSave(){
       return sourceSpectra.getFileName()+"_"+title+".img.spj";
  }
  
  public void save(String directory){//directory : path to directory to save
        DataOutputStream file=null;
        String nameToSave = getNameToSave();
        try {
            file = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(directory+nameToSave)));
            file.writeFloat(startSpectra);
            file.writeFloat(endSpectra);
            if( !(sourceSpectra.isSaved() && directory.equals(sourceSpectra.getDirectory())) ){
                sourceSpectra.save(directory);
            }
        }
        catch (IOException e) {
            IJ.log("Echec de la sauvegarde");
        }
        finally {
           try {
               if(file != null) {
                   file.close();
               }
           } 
           catch (IOException e2) {
               IJ.log("Echec de la sauvegarde");
           }
        } 
  }
   
  public void saveAll(String directory){
      sourceSpectra.saveAllImgGen(directory);
  }        
    
}