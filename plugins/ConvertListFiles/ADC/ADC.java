package ConvertListFiles.ADC;
import java.util.ArrayList;
import ij.*;
import java.io.*;


/**
 * This class is used to describe ADC and associated methods
 * ADC basically contains a list of events
 * @author GD
 */

public class ADC{

//declaration
private  ArrayList<int[]> eventList = new ArrayList<int[]>();
private ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
private	ArrayList<Integer> median = new ArrayList<Integer>();
private ArrayList<Integer> roiMap = new ArrayList<Integer>();
private ArrayList<ImagePlus> roiStack = new ArrayList<ImagePlus>();

private Integer sizeMapX=700;
private Integer sizeMapY=700;

//constructor
public ADC(){
	eventList.add(new int[3]);
	median.add(0);
        roiMap.add(0);
	
}
//destructor
public void finalize(){
}
private void initializeMedianMap(){
for (int i=0;i<sizeMapX*sizeMapY+1;i++){
		map.add(new ArrayList<Integer>());
		map.get(i).add(0);
	}
}
private void initializeRoiMap(){
roiMap.clear();
roiMap.trimToSize();
roiMap.add(0);
}
//getter
public int[] getEvent(int position){
	return eventList.get(position);
}
private int[] getlastEvent(){
	return eventList.get(getNEvents()-1);
}

public int getNEvents(){
	return eventList.size();
}
/**
 * Calculates a 4096 channels spectra from an event list
 * @return Table containing 4096 values (double type)
 */
public double [] getSpectra(){
	int size=4096;
	double[] spectra=new double[size];
	try{
	
	for (int i=0;i<getNEvents();i++){
			int [] evt=new int[3];
			evt=getEvent(i);
			if (evt[2]<size) spectra[evt[2]]+=1;
			}
	
	}
	catch (Exception e){
	IJ.log(e.toString());
	}
	return spectra;
}

/**
 * Calculates a spectra from an event list
 * @param size size of spectra (number of channel) to be calculated
 * @return spectra as a table containing [size] values
 */
public double [] getSpectra(int size){
	double[] spectra=new double[size];
	try{
		for (int i=0;i<getNEvents();i++){
			int [] evt=new int[3];
			evt=getEvent(i);
			if (evt[2]<size) spectra[evt[2]]+=1;
		}
	}
	catch (Exception e){
		IJ.log(e.toString());
	}
	return spectra;
}
/**
 * Return X-coordinate of event (X,Y,E)
 * @param event Index in event list
 * @return X-coordinate for this event
 */
public int getX(int event){
	int [] XYE=getEvent(event);
	return XYE[0];
}
/**
 * Return Y-coordinate of event (X,Y,E)
 * @param event Index in event list
 * @return Y-coordinate for this event
 */
public int getY(int event){
	int [] XYE=getEvent(event);
	return XYE[1];
}
/**
 * Return energy of event (X,Y,E)
 * @param event Index in event list
 * @return Energy for this event
 */
public int getE(int event){
	int [] XYE=getEvent(event);
	return XYE[2];
}
/**
 * Get the median value stored in median energy array list
 * @param index index of arraylist
 * @return median energy value
 */
public int getMedianValue(int index){
    return median.get(index);
}
/**
 * Gets the median energy value for the corresponding pixel on a 256-pixel-width map
 * @param x x-coordinate for pixel
 * @param y y-coordinate for pixel
 * @return median energy corresponding to x,y coordinate
 */
public int getMedianValue(int x, int y){
    return median.get(x+sizeMapX*y);
}
/**
 * Gets the median energy value for the corresponding pixel
 * @param x x-coordinate for pixel
 * @param y y-coordinate for pixel
 * @param sizeMapY Height of map in pixels
 * @return median energy corresponding to x,y coordinate
 */
public int getMedianValue(int x, int y, int sizeMapY){
    return median.get(x+sizeMapX*y);
}

//setter

/**
 * Append one event to the events list
 * @param event (X,Y,E) to be added to the list
 */
public void addEvent(int[] event){
	eventList.add(event);
}
//functions

/**
 * Save a spectra as a text file (1 column)
 * @param path filename for the spectra to be saved
 */
public void saveCountsSpectra(String path){
	try{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
		double [] tab = getSpectra();
		for (int i=0;i<4096;i++) {
			out.println(String.valueOf((int)tab[i]));
		}
		out.close();
	}
	catch (Exception e){
	}
}
/**
 * Save a spectra in the Gupix format
 * @param path filename for spectra to be saved
 */
public void saveGupixSpectra(String path){
	try{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
		double [] tab = getSpectra();
		out.println("4096 0");
		for (int i=0;i<4096;i++) {
			out.println(String.valueOf((int)tab[i]));
		}
		out.close();
	}
	catch (Exception e){
	}
}
/**
 * Save spectra in 2 columns text file accounting for channel (1st) and counts
 * @param path filename for text file
 */
public void saveChannelCountsSpectra(String path){
	try{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
		double [] tab = getSpectra();
		for (int i=0;i<4096;i++) {
			out.println(String.valueOf(i)+"\t"+String.valueOf((int)tab[i]));
		}
		out.close();
	}
	catch (Exception e){
	}
}
/**
 * Save list data file binary format (short)X,(short)Y,(Integer)E
 * @param path filename for the datafile to be saved
 */
public void saveXYEListFile(String path){
	try{
		DataOutputStream ops=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
		
		for (int i=0;i<getNEvents();i++){
			ops.writeShort(getX(i));
			ops.writeShort(getY(i));
			ops.writeInt(getE(i));
		}
		ops.close();
	}
	catch (Exception e){
	}
}
/**
 * Save list data file in the supavisio format (see below)
 * @param path filename for the datafile to be saved
 * @param type type of data to be saved (see below)
 * supavisio is little endian type
 * (short)0:pixe;1(rbs);2:Stim;5:erda
 * (short)X size of map
 * (short)Y size of map
 * events: (short)X,(short)Y, (short)E
 */
public void saveXYEListFile(String path, Short type){

	try{
		DataOutputStream ops=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
		ops.writeShort(LittleEndian(type));
		ops.writeShort(LittleEndian((short)255));
		ops.writeShort(LittleEndian((short)255));
		for (int i=1;i<getNEvents();i++){
			if ( ((getX(i)-1) >=0)&((getY(i)-1)>=0)&(getE(i)>0) & getE(i)<4096  ){
                            ops.writeShort(LittleEndian((short)(getX(i)-1)));
                            ops.writeShort(LittleEndian((short)(getY(i)-1)));
                            ops.writeShort(LittleEndian((short)getE(i)));
                        }
		}
		ops.close();
	}
	catch (Exception e){
	}
}
/**
 * Method to convert short integer to little endian as required for supavisio reading
 * @param v integer to be converted
 * @return v as little endian
 */
private int LittleEndian (short v){
    return ((v>>8)|(v<<8));
}
/**
 * Sort event according to X,Y and process median energy map
 * @return median, a list of integer corresponding to median energy in pixel
 */

public ArrayList<Integer> medianSort(){
	
	try{
            initializeMedianMap();
            for (int i=1;i<getNEvents();i++){
		int index=(int)getX(i)+sizeMapX*(int)getY(i)+1;
		try{
                    map.get(index).add((int)getE(i));
		}
		catch(Exception e){
                    IJ.log("map.g "+e.toString());
		}
            }
	}
	catch (Exception e){
            IJ.log(e.toString());
	}
	for (int i=1;i<map.size();i++){
            java.util.Collections.sort(map.get(i));
            int size=map.get(i).size();
            median.add(map.get(i).get((int)(size/2)));
	}
	return  median;
}
/**
 * Sort event according to X,Y and process median energy map
 * @return median, a list of integer corresponding to median energy in pixel
 */

public ArrayList<Integer> makeRoiMap(){
//methode en cours de developpement
	try{
            initializeRoiMap();
            for (int i=1;i<getNEvents();i++){
		int index=(int)getX(i)+256*(int)getY(i)+1;
		try{
                    if (getE(i)>280 & getE(i) <320) roiMap.set(index,roiMap.get(index)+1);
		}
		catch(Exception e){
                    IJ.log("roimap.g "+e.toString());
		}
            }
	}
	catch (Exception e){
            IJ.log(e.toString());
	}

	return  roiMap;
}
/**
 * Save median map as a 2D text file
 * @param path filename for 2D text file
 */
public void saveMedianTextImage(String path){
	try{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
		
		for (int x=2;x<sizeMapX;x++) {
			String line="";
			for (int y=1;y<sizeMapY;y++){
				line+=String.valueOf(median.get(x+sizeMapX*y))+" ";
			}
			out.println(line);
		}
		out.close();
	}
	catch (Exception e){
	}
}
public void saveRoiTextImage(String path){
	try{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)));

		for (int x=2;x<sizeMapX;x++) {
			String line="";
			for (int y=1;y<sizeMapY;y++){
				line+=String.valueOf(median.get(x+sizeMapX*y))+" ";
			}
			out.println(line);
		}
		out.close();
	}
	catch (Exception e){
	}
}
}