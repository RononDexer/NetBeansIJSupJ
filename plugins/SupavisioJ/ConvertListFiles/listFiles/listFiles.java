package SupavisioJ.ConvertListFiles.listFiles;

import SupavisioJ.ConvertListFiles.ADC.ADC;
import java.io.*;
import ij.*;
import SupavisioJ.ConvertListFiles.MPA3.MPA3;



public class listFiles{
  //declaration
  private String path;
  private int adcIndexScanX;
  private int adcIndexScanY;


  //constructors
  /**
  * Default constructor
  */
  public listFiles(){
  }
  /**
  * Constructor with initialization of pathname
  * @param path Path of the file
  */
  public listFiles(String path){
    this.path=path;
  }

  /**
  * Constructor with initialization of pathname
  * @param X ADC index for scanning X
  * @param Y ADC index for scanning Y
  */
  public listFiles(int adcIndexScanX, int adcIndexScanY){
    this.adcIndexScanX=adcIndexScanX;
    this.adcIndexScanY=adcIndexScanY;
  }

  /**
  * Constructor with initialization of pathname
  * @param path Path of the file
  * @param X ADC index for scanning X
  * @param Y ADC index for scanning Y
	  
  */
  public listFiles(String path, int adcIndexScanX, int adcIndexScanY){
	  this.path=path;
	  this.adcIndexScanX=adcIndexScanX;
	  this.adcIndexScanY=adcIndexScanY;
  }

  /**
  * Sorts a list file
  * @return  A sorted list of MPA according to ADC
  */
  
  public boolean isReadingHeader(DataInputStream ips) throws Exception {
    boolean flag=true;
    //Discards header searching for '[Listdata]'
      if (ips.readUnsignedByte()==91 && ips.readUnsignedByte()==76 && ips.readUnsignedByte()==73 && ips.readUnsignedByte()==83 && ips.readUnsignedByte()==84){
	  if (ips.readUnsignedByte()==68 && ips.readUnsignedByte()==65 && ips.readUnsignedByte()==84 && ips.readUnsignedByte()==65 && ips.readUnsignedByte()==93){
	      flag=false;
	  }
      }
      return flag;
  }

  
  public MPA3 readListFile(int[] arrayOfActiveADC){
	  MPA3 mpa=new MPA3();
	  try{
		  DataInputStream ips=new DataInputStream(new BufferedInputStream(new FileInputStream(path)));  
		  while(isReadingHeader(ips)){
		      IJ.log("Reading header");
		  }
		  
		  ips.readUnsignedByte();
		  
		  //Reset timer tags counter
		  int timerTag=0;
		  int eventTag=0;
		  int SynTag=0;
		  int zevt=0;
		  int tmp=0;
			  
		  // b is a 4 bytes word
		  // b[0] is the high word and b[3] the lowest
		  // Dummy Word is 0xFFFFFFFF
		  //Timer Tag is 0x4000 ie 0100 0000 0000 0000 xxxx xxxx xxxx xxxx
		  // xxxx xxxx xxxx xxxx represent active ADC (bit=1) and inactive ADC (bit=0)
		  // ADC[1] corresponds to bit 0
		  int [] b=new int[4];
		  try{
			  b[0]=ips.readUnsignedByte();
			  //loop until end of file is reached (b=-1)
			  while (b[0]>-1){
				  //Read 4 bytes and fills b[] tab, reversing order to switch from little endian to big endian order
				  for (int i=0;i<4;i++){
					  b[3-i]=ips.readUnsignedByte();
				  }
				  //Counting the number of timer tags
				  switch (checkBit(b[0],6)){
					  //Timer tag or Synchronize mark
					  case 1:
						  switch (checkBit(b[0],7) ){
							  //Synchronize mark
							  case 1:
							    SynTag+=1;
							  break;
							  //Timer tag
							  case 0:
							    timerTag+=1;
							    int[] tmtagPerADC=new int[16];
							    for (int i=0;i<8;i++){
							      tmtagPerADC[i]=checkBit(b[3],i);
							      IJ.log(String.valueOf(tmtagPerADC[i]));
							    }
							    for (int i=0;i<8;i++){
							      tmtagPerADC[i+8]=checkBit(b[2],i);
							    }
                                                            for (int i=0;i<arrayOfActiveADC.length;i++){
                                                                int indexOfADC = arrayOfActiveADC[i];
                                                                ADC adcToComplete= mpa.getADC(indexOfADC);
                                                                adcToComplete.addPeriod(tmtagPerADC[indexOfADC]);
                                                            }
							  break;
						  }
					  break;
					  //Event tag
					  case 0:
						  eventTag+=1;
						  //dummy word
						  if (checkBit(b[0],7)==1) ips.readShort();
						  int [] evt= new int[16];
						  for (int i=0;i<8;i++){
							  if (checkBit(b[3],i)==1) evt[i]=safeBytesToInt(ips.readUnsignedByte(),ips.readUnsignedByte());
						  }
						  for (int i=0;i<8;i++){
							  if (checkBit(b[2],i)==1) evt[i+8]=safeBytesToInt(ips.readUnsignedByte(),ips.readUnsignedByte());
						  }
						  zevt+=sortEvents(mpa,evt);
						  
					  break;
				  }
			  }			
		  }
		  catch (EOFException e){
		  }
		  ips.close(); 
	  }
	  catch (Exception e){
	  }
	  return mpa;
  }

  //getters
  public String getPath(){
	  return path;
  }
  public void setPath(String path){
	  this.path=path;
  }
  public String setExtension(String ext){
	  String name=getPath().substring(0,getPath().lastIndexOf("."));
	  return name+"."+ext;
  }	
  //Reading tags for listfiles
  private boolean checkTimerTag(int thisByte){
	  if (checkBit(thisByte,6)==1) return true;
	  else return false;
  }
  private boolean checkEventTag(int thisByte){
	  if (checkBit(thisByte,6)==0) return true;
	  else return false;

  }
  private boolean checkDummyWord (int thisByte){
	  if (thisByte>0x80) return true;
	  else return false;
  }
  private int checkBit(int thisByte, int position){
	  if (((thisByte>>position)&0x1)==1) return 1;
	  else return 0;

  }

  //conversion of bytes to int
  private int bytesToInt(int byte1, int byte2){
	  return ((byte2<<8)|byte1);
  }

  //conversion of bytes to int
  private int safeBytesToInt(int byte1, int byte2){
      
      return ((byte2<<8)|byte1)&0xFFF;
  }


  //Sorting and writing events
  private int sortEvents(MPA3 mpa, int [] evt){
	  int ct=0;
	  for (int i=0;i<16;i++){
	      if (i!=(adcIndexScanX) & i!=(adcIndexScanY)){
		  if (evt[i]>0){
			  int [] event=new int[3];
			  //X value, Y value, Energy
			  event[0]=evt[adcIndexScanY];
			  event[1]=evt[adcIndexScanX];
			  event[2]=evt[i];
			  mpa.getADC(i).addEvent(event);
			  
			  ct+=1;
		  }
	      }
	  }
	  if (ct>0) return 0;
	  else return 1;
  }

}