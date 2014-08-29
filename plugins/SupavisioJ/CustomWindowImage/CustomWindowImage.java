package SupavisioJ.CustomWindowImage;

import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.Spectra.Spectra;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
import ij.gui.StackWindow;
import ij.gui.YesNoCancelDialog;
import ij.io.FileSaver;
import ij.macro.Interpreter;
import ij.plugin.frame.RoiManager;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;

import java.io.File;
import java.util.Hashtable;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CustomWindowImage extends StackWindow implements ActionListener,AdjustmentListener{
    
    private Button buttonCalc;
    private Button buttonSave;
    private Button buttonSaveAll;
    private Button buttonManageRoi;
    private JLabel imageName;
    private JTextField nameRoiField;
    private ImageGenerated[] selectedImages; 

    // constructor
    public CustomWindowImage(ImagePlus imp, ImageGenerated[] selectedImages) {
        super(imp);
        this.selectedImages = selectedImages;
        setLayout(new FlowLayout());
        if (selectedImages.length>1){
            remove(zSelector);
        }
        addPanel();
    }   

    /**
     * This method will add a panel containing buttons to the window
     */
    // panel with image and buttons layout type for the image generated window
    private void addPanel() {
        Panel panel = new Panel();
        int nbFields=7;
        if (selectedImages.length>1)
            nbFields+=1;
        panel.setLayout(new GridLayout(nbFields,1));
        nameRoiField = new JTextField();
        nameRoiField.setText(tr("Name of the ROI"));
        buttonCalc = new Button(tr("Calculate spectra"));
        buttonCalc.addActionListener(this);
        buttonManageRoi=new Button(tr("Manage ROIs"));
        buttonManageRoi.addActionListener(this);
        buttonSave = new Button(tr("Save"));
        buttonSave.addActionListener(this);
        buttonSaveAll = new Button(tr("Save All"));
        buttonSaveAll.addActionListener(this);
        imageName = new JLabel();
        imageName.setText(selectedImages[0].getTitle());
        panel.add(imageName);
        if (selectedImages.length>1){
            panel.add(sliceSelector);
            sliceSelector.addAdjustmentListener(this); 
        }
        panel.add(new Label(""));
        panel.add(nameRoiField);
        panel.add(buttonCalc);
        panel.add(buttonSave);
        panel.add(buttonSaveAll);
        panel.add(buttonManageRoi);
        add(panel);
        pack();  
    }

    public void actionPerformed(ActionEvent e) {
        Object b = e.getSource();
        int z=0;
        if(selectedImages.length>1)
            z = zSelector.getValue()-1; 
        if (b==buttonCalc) {
            Roi roiToAddOrRename = selectedImages[z].getRoi();
            if(roiToAddOrRename!=null){
                String newName = nameRoiField.getText();
                addRoiToRoiManager(roiToAddOrRename,newName);
                Spectra spectraCalc=selectedImages[z].generateSpectraFromRoi();
                spectraCalc.plotSpectra((String)"SupavisioJ", (String) tr("Calculated spectra")+" "+spectraCalc.getFileName()).showVisible();
            }
            else{
                IJ.error(tr("Please select a region of the picture \n to calculate the corresponding spectra"));
            }
        }
        if (b==buttonSave) {
            // save selected image
            String directory=selectDirectory();
            if (directory!=null){
                selectedImages[z].save(directory);
            }
        }
        if (b==buttonSaveAll) {
            //save all images generate from parent Spectra
            String directory=selectDirectory();
            if (directory!=null)
                selectedImages[z].saveAll(directory);
        }        
        if (b==buttonManageRoi) {
            //show ROI Manager
            RoiManager oldRoiManager = selectedImages[z].getRoiManager();
            if(!selectedImages[z].getRoiManagerVisibility()){
                //In this case the current RoiManager is hidden and can't be displayed because the frame
                //exists but not its components. So we need to create a new RoiManager that can be displayed
                //and transfer the Rois list from the old manager to the new.
                Hashtable<String, Roi> tableToTransfer = oldRoiManager.getROIs();
                Object[] labels = tableToTransfer.keySet().toArray();//to get labels of all the ROIs
                RoiManager newRoiManager = new RoiManager();
                selectedImages[z].setRoiManager(newRoiManager);  
                for(Object label : labels){
                    String roiLabel = (String) label;
                    Roi roiToTransfer = tableToTransfer.get(label);
                    newRoiManager.addRoi(roiToTransfer);//add the Roi
                    this.setNewName(roiToTransfer, roiLabel, 0);//name the Roi added
                }
                oldRoiManager.dispose();
                //newRoiManager.setExtendedState(Frame.NORMAL);
                //newRoiManager.setVisible(true);
                //newRoiManager.toFront();
                selectedImages[z].setRoiManagerVisibility(true);
            }
            else{
                oldRoiManager.setVisible(true);
                oldRoiManager.toFront();//put the frame to front
            }
        }
        ImageCanvas ic = imp.getCanvas();
        if (ic!=null)
            ic.requestFocus();
    }
    
    /**
     * Method to use to add or rename a Roi to the current RoiManager
     * @param roiToAddOrRename the ROI to add or rename
     * @param newName a String containing the new name of the ROI
     * @see setNewName()
     */
    public void addRoiToRoiManager(Roi roiToAddOrRename,String newName){
        if(roiToAddOrRename!=null){
            String labelToCheck=roiToAddOrRename.getName();//get the current name of the ROI
            RoiManager manager = selectedImages[0].getRoiManager();
            if (labelToCheck!=null){//then have to verify if the ROI is not already there
                Hashtable<String, Roi> table = (Hashtable<String, Roi>) manager.getROIs();
                Roi roiToCheck = table.get(labelToCheck);
                if (roiToCheck!=null){//either same ROI either same name
                    if (roiToCheck==roiToAddOrRename||roiToCheck.equals(roiToAddOrRename)){//same ROI
                         //so don't add it (it's already there) but rename it    
                        setNewName(roiToCheck,newName,0);
                    }
                    else{
                        //the ROI to add has the same name of another ROI but its not the same ROI
                        IJ.error(tr("The Roi Manager has encounter an error"));
                        if(!newName.equals(labelToCheck)){
                            setNewName(roiToCheck,newName,0);
                            manager.addRoi(roiToAddOrRename);
                        }
                        else{
                            setNewName(roiToCheck,newName,1);
                            manager.addRoi(roiToAddOrRename);
                        }
                    }
                }
                else{//so add it, it's not already there
                    setNewName(roiToAddOrRename,newName,0);
                    manager.addRoi(roiToAddOrRename);
                }
            }
            else{
                setNewName(roiToAddOrRename,newName,0);
                manager.addRoi(roiToAddOrRename);
            }
        }
        newName=roiToAddOrRename.getName();
        nameRoiField.setText(newName);
    }
    
    /**
     * This method will set a new name but don't interact with the RoiManager (unless the Roi is in its list)
     * @param roiToRename the ROI to add or rename
     * @param newName a String containing the new name of the ROI
     * @param nbToAddToName a number to add to the name (0 don't add anything)
     */
    public void setNewName(Roi roiToRename,String newName,int nbToAddToName){
        //if newName already exists try to rename in newName1 or newName2 ....
        if(nbToAddToName!=0){//it's not the first time that we try to rename the ROI
            if(nbToAddToName>1)
                newName=newName.substring(0, newName.length()-1);//remove the old number
            if(nbToAddToName>10)
                newName=newName.substring(0, newName.length()-1);//remove the old number
            newName+=String.valueOf(nbToAddToName);//add the new number at the end of the name
        }
        if( newName!=null && !newName.equals("") && !newName.equals(roiToRename.getName()) && !newName.equals(tr("Name of the ROI")) ){
            //first check if the new name is not already taken
            boolean labelAlreadyThere=false;
            RoiManager manager = selectedImages[0].getRoiManager();
            Hashtable<String, Roi> table = (Hashtable<String, Roi>) manager.getROIs();
            for (String labelRoi : table.keySet()) {
                if(labelRoi.equals(newName)){
                    if(nbToAddToName==0)
                        IJ.error(tr("This ROI name has already been attribuate please give an other"));
                    Roi roiToCheck = table.get(labelRoi);
                    if(roiToCheck==roiToRename || roiToCheck.equals(roiToRename)){
                        changeNameRoi(roiToCheck,newName);
                        return;
                    }
                    labelAlreadyThere=true;
                }
            }
            if (!labelAlreadyThere){
                roiToRename.setName(newName);
                changeNameRoi(roiToRename,newName);
            }
            else {
                setNewName(roiToRename,newName,nbToAddToName+1);
            }
        }
    }
    
    /**
     * This method change the name of an already existing Roi in the RoiManager
     * @param roiToRename the Roi already existing in the RoiManager's list
     * @param newName a String containing the new name of the ROI
     */
    public void changeNameRoi(Roi roiToRename, String newName){
        RoiManager manager = selectedImages[0].getRoiManager();
        Roi[] arrayOfRoi = manager.getRoisAsArray();
        for (int i=0;i<arrayOfRoi.length;i++){
            Roi currentRoi = arrayOfRoi[i];
            if (roiToRename==currentRoi || roiToRename.equals(currentRoi)){
                manager.select(i);
                manager.runCommand("Rename", newName);
            }
        }
        //solution 2 : not used because the RoiManager won't update itself
        //Hashtable<String, Roi> table = (Hashtable<String, Roi>) manager.getROIs();
        //for (String labelRoi : table.keySet()) {
        //    Roi currentRoi = table.get(labelRoi);
        //    if(roiToRename==currentRoi || roiToRename.equals(currentRoi)){
        //        table.remove(labelRoi);
        //        table.put(newName, roiToRename);
        //    }
        //}
        
    }
    
    public synchronized void adjustmentValueChanged(AdjustmentEvent adjEv) { 
        int z = zSelector.getValue(); 
        String currentName = selectedImages[z-1].getTitle();
        imageName.setText(currentName);
        super.adjustmentValueChanged(adjEv);
    } 
    
    public void mouseWheelMoved(MouseWheelEvent mWEvent) {
        super.mouseWheelMoved(mWEvent);
        if(selectedImages.length>1){
            int z = zSelector.getValue()-1; 
            String currentName = selectedImages[z].getTitle();
            imageName.setText(currentName);
        }
        updateStatusbarValue();
    }

    public void mouseMoved(int x, int y) {
        int z=0;
        if(selectedImages.length>1)
            z = zSelector.getValue()-1; 
        IJ.showStatus(imp.getLocationAsString(x+1,y+1) + selectedImages[z].getValueAsString(x,y));
	savex=x; savey=y;
    }
	
    private int savex, savey;    

    /**
     * Redisplays the (x,y) coordinates and pixel value (which may have changed) in the status bar. Called by the Next Slice and Previous Slice commands to update the z-coordinate and pixel value.
     */
    
    public void updateStatusbarValue() {
        int z=1;
        if(selectedImages.length>1)
            z = zSelector.getValue(); 
        IJ.showStatus(imp.getLocationAsString(savex+1,savey+1) + selectedImages[z-1].getValueAsString(savex,savey));
    }
    
    
    private String selectDirectory(){
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = fileChooser.showDialog(null,tr("Choose directory"));
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
             // if the user accidently clicks on a file,the parent directory is selected.
            if (!selectedFile.isDirectory()) {
                selectedFile = selectedFile.getParentFile();
            }
        }
        if (selectedFile!=null)
            return selectedFile.getAbsolutePath()+"/";
        return null;
    }
    
    /**
     * This method overides the supermethod to avoid the imp=null present in the method of ImageWindow
     */
    public boolean close() { //code taken (partially) from ImageWindow
        boolean isRunning = running || running2;
        running = running2 = false;
        boolean virtual = imp.getStackSize()>1 && imp.getStack().isVirtual();
        if (isRunning) IJ.wait(500);
        if (ij==null || IJ.getApplet()!=null || Interpreter.isBatchMode() || IJ.macroRunning() || virtual)
                imp.changes = false;
        if (imp.changes) {
                String msg;
                String name = imp.getTitle();
                if (name.length()>22)
                        msg = "Save changes to\n" + "\"" + name + "\"?";
                else
                        msg = "Save changes to \"" + name + "\"?";
                YesNoCancelDialog d = new YesNoCancelDialog(this, "ImageJ", msg);
                if (d.cancelPressed())
                        return false;
                else if (d.yesPressed()) {
                        FileSaver fs = new FileSaver(imp);
                        if (!fs.save()) return false;
                }
        }
        closed = true;
        WindowManager.removeWindow(this);
        setVisible(false);
        if (ij!=null && ij.quitting())  // this may help avoid thread deadlocks
                return true;
        dispose();
        return true;
    }

    
    public String tr(String strToTranslate){
        return selectedImages[0].tr(strToTranslate);
    }
    
} 