package SupavisioJ.MainFrame;

import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.ConvertListFiles.FrameC.FrameC;
import SupavisioJ.DataFileXYEList.DataFileXYEList;
import SupavisioJ.FrameConfigSave.FrameConfigSave;
import SupavisioJ.FrameConfigLang.FrameConfigLang;
import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.Spectra.Spectra;

import ij.IJ;
import ij.Menus;
import ij.WindowManager;
import ij.plugin.frame.RoiManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser; 
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *This class is the first window which will be open at the launch of SupavisioJ
 */
public class MainFrame extends javax.swing.JFrame implements WindowListener{
    private FrameConfigLang frameConfigLang= null;
    private FrameC frameConfigLst = new FrameC();
    private FrameConfigSave frameConfigSaveSession = new FrameConfigSave(this);
    private ArrayList<Spectra> spectrasProduced = new ArrayList<Spectra>();
    private boolean saveImagesOfSession = false;
    private String nameOfApplication = "SupavisioJ";
    private static ArrayList<String> availableLanguages = new ArrayList<String>();
    private static ArrayList<String[]> languageData = new ArrayList<>();
    private static RoiManager roiManager =null;
    private static boolean roiManagerVisibility=true;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {        
        availableLanguages = new ArrayList<String>();
        languageData = new ArrayList<>();                
        searchAvailableLanguages();
        String language = chooseLanguage();
        initComponents();
        chooseIconLanguage(language);
        String languageName=null;
        try{
            languageName = readLanguageName("plugins/SupavisioJ/resources/language/"+language+".txt");
        }
        catch(IOException e){}
        frameConfigLang=new FrameConfigLang(this,languageName);
        this.setIconImage(new ImageIcon(getClass()
                .getResource("/SupavisioJ/resources/images" + "/atome-16.png")).getImage());
    }
    
    public void setSaveImg(boolean saveImg){
        saveImagesOfSession=saveImg;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonOpenLst = new JButton();
        jLabelAsk = new JLabel();
        jButtonOpenXYEList = new JButton();
        jButtonParamLst = new JButton();
        jButtonParamPIXE = new JButton();
        jButtonSaveSession = new JButton();
        jButtonParamSaveSession = new JButton();
        jButtonRestore = new JButton();
        jButtonParamRestore = new JButton();
        jButtonLanguage = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SupavisioJ");

        jButtonOpenLst.setText(tr("Open a .LST file"));
        jButtonOpenLst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonOpenLstActionPerformed(evt);
            }
        });

        jLabelAsk.setText(tr("What do you want to do?"));

        jButtonOpenXYEList.setText(tr("Open a file in a XYE format"));
        jButtonOpenXYEList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonOpenXYEListActionPerformed(evt);
            }
        });

        jButtonParamLst.setIcon(new ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamLst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonParamLstActionPerformed(evt);
            }
        });

        jButtonParamPIXE.setIcon(new ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamPIXE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonParamPIXEActionPerformed(evt);
            }
        });

        jButtonSaveSession.setText(tr("Save the SpJ session"));
        jButtonSaveSession.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSaveSessionActionPerformed(evt);
            }
        });

        jButtonParamSaveSession.setIcon(new ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamSaveSession.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonParamSaveSessionActionPerformed(evt);
            }
        });

        jButtonRestore.setText(tr("Restore files or sessions"));
        jButtonRestore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonRestoreActionPerformed(evt);
            }
        });

        jButtonParamRestore.setIcon(new ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamRestore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonParamRestoreActionPerformed(evt);
            }
        });

        jButtonLanguage.setIcon(new ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonLanguage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonLanguageActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonOpenXYEList, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonParamPIXE, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonSaveSession, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonParamSaveSession, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonRestore, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonParamRestore, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jButtonOpenLst, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jLabelAsk)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonParamLst, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonLanguage, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelAsk, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLanguage, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpenLst)
                    .addComponent(jButtonParamLst, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOpenXYEList)
                    .addComponent(jButtonParamPIXE, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSaveSession)
                    .addComponent(jButtonParamSaveSession, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRestore)
                    .addComponent(jButtonParamRestore, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOpenLstActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonOpenLstActionPerformed
        frameConfigLst.openLST();
    }//GEN-LAST:event_jButtonOpenLstActionPerformed

    private void jButtonParamLstActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonParamLstActionPerformed
        frameConfigLst.setVisible(true);
    }//GEN-LAST:event_jButtonParamLstActionPerformed

    private void jButtonOpenXYEListActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonOpenXYEListActionPerformed
        String path=selectFile();
        DataFileXYEList fileXYE=new DataFileXYEList(path);
        ADC adcXYE = fileXYE.open(); 
        if ( adcXYE!=null && adcXYE.getNEvents()>1 && (adcXYE.getlastEvent()[0]!=0 && adcXYE.getlastEvent()[1]!=0) ){//check if a correct file has been open
            Spectra spectraXYE= new Spectra(adcXYE,fileXYE.getName());
            if(spectraXYE.getEnergies().length>1){//check if a correct file has been open
                spectraXYE.setParentWindow(this);
                spectraXYE.plotSpectra(nameOfApplication, (String) tr("Spectra")+" "+spectraXYE.getFileName()).showVisible();
            }
        }
    }//GEN-LAST:event_jButtonOpenXYEListActionPerformed

    private void jButtonParamPIXEActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonParamPIXEActionPerformed
        IJ.log(tr("No settings available for the moment"));
    }//GEN-LAST:event_jButtonParamPIXEActionPerformed

    private void jButtonSaveSessionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSaveSessionActionPerformed
        if(spectrasProduced.size()>0){
            String directory=selectDirectory();
            ArrayList<String> nameSpectra = new ArrayList<String>();
            ArrayList<String[]> nameImgGen = new ArrayList<String[]>();
            if (directory!=null){
                for(int i=0;i<spectrasProduced.size();i++){
                    spectrasProduced.get(i).save(directory);
                    String currentNameToSave = spectrasProduced.get(i).getNameToSave();
                    if (!nameSpectra.contains(currentNameToSave)){
                        nameSpectra.add(currentNameToSave);
                        if(saveImagesOfSession){
                            nameImgGen.add(spectrasProduced.get(i).saveAllImgGen(directory));
                        }
                    }
                }
                saveSession(directory,nameSpectra,nameImgGen);
            }
        }
    }//GEN-LAST:event_jButtonSaveSessionActionPerformed

    private void jButtonParamSaveSessionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonParamSaveSessionActionPerformed
        frameConfigSaveSession.setVisible(true);
    }//GEN-LAST:event_jButtonParamSaveSessionActionPerformed

    private void jButtonRestoreActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonRestoreActionPerformed
        String[] paths = selectFiles();
        for (String path : paths) {
            if(path.contains("sess.spj")){
                restoreSession(path);
            }
            else if(path.contains("spct.spj")){
                Spectra spectToRestore = new Spectra(path,this);
                spectToRestore.plotSpectra(nameOfApplication, (String)  tr("Spectra")+" "+spectToRestore.getFileName()).showVisible();
            }
            else if(path.contains("img.spj")){
                int index=path.lastIndexOf("/")+1;
                if (index==0)
                    index=path.lastIndexOf("\\")+1;
                String nameSourceSpectra = path.substring(index,path.lastIndexOf("_"));
                Spectra sourceSpectra=null;
                for(int i=0; i<spectrasProduced.size(); i++){
                    Spectra currentSpct = spectrasProduced.get(i);
                    if (currentSpct.getFileName().equals(nameSourceSpectra)){
                        sourceSpectra=currentSpct;
                    }
                }
                if (sourceSpectra==null){
                    String directory = path.substring(0, index);
                    String nameToSave = nameSourceSpectra+".spct.spj";
                    sourceSpectra=new Spectra(directory+nameToSave, this);
                    sourceSpectra.plotSpectra(nameOfApplication, (String) tr("Spectra")+" "+sourceSpectra.getFileName());
                }
                sourceSpectra.restoreImgGen(path);
            }
        }
    }//GEN-LAST:event_jButtonRestoreActionPerformed

    private void jButtonParamRestoreActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonParamRestoreActionPerformed
        IJ.log(tr("Uncoded feature for the moment"));
    }//GEN-LAST:event_jButtonParamRestoreActionPerformed

    private void jButtonLanguageActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonLanguageActionPerformed
        frameConfigLang.setVisible(true);
    }//GEN-LAST:event_jButtonLanguageActionPerformed

    /**
     * Use this method to get the RoiManager (it avoid some closing bug of the RoiManager)
     * @return the current RoiManager or a new if non-existing. The new will not (and can not) be displayed.
     */
    public RoiManager getRoiManager(){
        if (roiManager == null){
            roiManager = RoiManager.getInstance();
            if (roiManager == null){
                roiManager = new RoiManager(true);
                setRoiManagerVisibility(false);
            }
            setRoiManager(roiManager);
        }
        return roiManager;
    }
    
    /**
     * Remove the window listener from the RoiManager to avoid some bugs existing with this plugin in ImageJ
     * Add this class as a window listener to hide the window on close
     * @param roiManager the current RoiManager 
     */
    public void setRoiManager(RoiManager roiManager){
        this.roiManager=roiManager;
        WindowListener[] roiManagerListeners= roiManager.getWindowListeners();
        for(WindowListener roiManagerListener : roiManagerListeners){
            roiManager.removeWindowListener(roiManagerListener);
        }
        roiManager.addWindowListener(this);
    }
    
    public void setRoiManagerVisibility(boolean roiManagerVisibility){
        this.roiManagerVisibility=roiManagerVisibility;
    }
    
    public boolean getRoiManagerVisibility(){
        return roiManagerVisibility;
    }
    
    public void handlesRoiManagerClose(){
        getRoiManager().setVisible(false);
    }
    
    /**
     * Window listener for the RoiManager frame (and NOT for this frame)
     */
    public void windowClosed(WindowEvent e){
        //handlesRoiManagerClose();
    }
    
    /**
     * Window listener for the RoiManager frame (and NOT for this frame)
     */
    public void windowClosing(WindowEvent e){
        handlesRoiManagerClose();
    }
    
    /**
     * Window listener for the RoiManager frame (and NOT for this frame)
     */
    public void windowOpened(WindowEvent e) {}

    /**
     * Window listener for the RoiManager frame (and NOT for this frame)
     */
    public void windowIconified(WindowEvent e) {}

    /**
     * Window listener for the RoiManager frame (and NOT for this frame)
     */
    public void windowDeiconified(WindowEvent e) {}

    /**
     * Window listener for the RoiManager frame (and NOT for this frame)
     */
    public void windowActivated(WindowEvent e) {
        if (IJ.isMacintosh() && IJ.getInstance()!=null) {
            IJ.wait(10); // may be needed for Java 1.4 on OS X
            setMenuBar(Menus.getMenuBar());
        }
        WindowManager.setWindow(this);
    }
    
    /**
     * Window listener for the RoiManager frame (and NOT for this frame)
     */
    public void windowDeactivated(WindowEvent e) {}
    
    /**
     * This method will save the session (meaning all Spectras produced since the launch) in the given directory.
     * The ImageGenerated objects will be save if the user has changed the saving settings.
     * @param directory absolute path to the directory where all files will be saved
     * @param nameSpectra an arrayList of names of the saved Spectras
     * @param nameImgGen an arrayList of names of the saved ImageGenerateds
     */
    private void saveSession(String directory,ArrayList<String> nameSpectra,ArrayList<String[]> nameImgGen){
        Calendar currentDate = Calendar.getInstance();
        String year = String.valueOf(currentDate.get(Calendar.YEAR));
        String month = String.valueOf(currentDate.get(Calendar.MONTH)+1);
        String day = String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(currentDate.get(Calendar.HOUR_OF_DAY));
        String min = String.valueOf(currentDate.get(Calendar.MINUTE));
        String sec = String.valueOf(currentDate.get(Calendar.SECOND));
        String fileName = day+"-"+month+"-"+year+"_"+hour+"h"+min+"m"+sec+"s.sess.spj";
        String path = directory+fileName;
        try{
            BufferedWriter buff = new BufferedWriter(new FileWriter(path,false));//Création du fichier si non existant et append=false si existant
            for (int i=0;i<nameSpectra.size();i++){
                buff.write(nameSpectra.get(i));
                buff.write("\n");
                if(saveImagesOfSession){
                    for (int j=0;j<nameImgGen.get(i).length;j++){
                        buff.write(nameImgGen.get(i)[j]);
                        buff.write("\n");
                    }
                }
            }
            buff.flush();   // buffer is released
            buff.close();   // buffer & stream are closed.    
        }
        catch(IOException e){
            IJ.log(tr("Fail to save the session Spj"));
        }
    }
    
    /**
     * restore a file session (*.sess.spj) and all the files mentionned in this session
     * @param path absolute path to the file session
     */
    private void restoreSession(String path){
        try{
            String[] readLines=readLinesFile(path);
            int index=path.lastIndexOf("/")+1;
            if (index==0)
                index=path.lastIndexOf("\\")+1;
            String directory=path.substring(0,index);
            for (String readLine : readLines) {
                if (readLine.contains("spct")){
                    Spectra spectToRestore = new Spectra(directory+readLine,this);
                    spectToRestore.plotSpectra(nameOfApplication,  (String) tr("Spectra")+" "+spectToRestore.getFileName()).showVisible();
                }
                else if (readLine.contains("img")){
                    Spectra sourceSpectra = spectrasProduced.get(spectrasProduced.size()-1);
                    sourceSpectra.restoreImgGen(directory+readLine);
                }
            }
            
        }
        catch(IOException e){}
    }
    
    
    private void searchAvailableLanguages(){
        final File folder = new File("plugins/SupavisioJ/resources/language");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.getName().endsWith("txt")){
                try{
                    String name = readLanguageName(file.getAbsolutePath());
                    if (name!=null){
                       availableLanguages.add(name); 
                    }
                }
                catch(IOException e){}
            }
        }
    }
    
    private String chooseLanguage(){
        try{
            String[] lines =readLinesFile("plugins/SupavisioJ/resources/language/default");
            if (!lines[0].contains("english")&&!lines[0].contains("source")){
                String[] linesSource=readLinesFile("plugins/SupavisioJ/resources/language/source.txt");
                String[] linesTranslated=readLinesFile("plugins/SupavisioJ/resources/language/"+lines[0]+".txt");
                languageData.add(linesSource);
                languageData.add(linesTranslated);
                return lines[0];
            }
            else{
                return lines[0];
            }
        }
        catch(IOException e){}
        return null;
    }
    
    private void chooseIconLanguage(String language){
        jButtonLanguage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SupavisioJ/resources/language/"+language+".png")));
    }
    
    public String[] getLanguages(){
        return availableLanguages.toArray( new String[availableLanguages.size()] );
    }
    
    public String getNameFileLang(String languageName){
        final File folder = new File("plugins/SupavisioJ/resources/language");//");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.getName().endsWith("txt")){
                try{
                    String name = readLanguageName(file.getAbsolutePath());
                    if (name!=null && name.equals(languageName)){
                        String fileName = file.getName();
                        return fileName.substring(0,fileName.lastIndexOf("."));
                    }
                }
                catch(IOException e){}
            }
        }
        return "source";
    }
    
    public static String tr(String dataToTranslate){
        if(dataToTranslate!=null){
            if(languageData.size()>1){
                String[] source=languageData.get(0);
                for (int i=0;i<source.length;i++){
                    String lineSource=source[i];
                    if (lineSource.equals(dataToTranslate)){
                        String[] translate = languageData.get(1);
                        String line = translate[i];
                        return line;
                    }
                }
            }
        }
        return dataToTranslate;
    }
    
    public String readLanguageName(String path)throws IOException{
        BufferedReader buff=null;
        String languageName= null;
        try {
          buff=buff = new BufferedReader(new FileReader(path));//file opening
          for (int i=0;i<3;i++) {
            if(i==2){
                languageName = buff.readLine();
            }
            else{
                buff.readLine();
            }
          }
          buff.close();
        }	
        catch(FileNotFoundException e){
          IJ.log(tr("Error. Language file not found or invalid"));
        }
        catch (NullPointerException e){//end of the file
          buff.close();
        }
        return languageName;
    }
  
    /**
     * Method to read a text file
     * @return return an array containing the lines of file. "\n" has been removed.
     */
    public String[] readLinesFile(String path)throws IOException{
        BufferedReader buff=null;
        ArrayList<String> arrayLines=new ArrayList<String>();
        try {
          buff=buff = new BufferedReader(new FileReader(path));//file opening
          for (int i=0;;i++) {
            String line = buff.readLine();
            arrayLines.add(line);
            if (line.equals(null) | line.equals("\n")){//end of the file can produced a NullPointerException
              arrayLines.remove(arrayLines.size()-1);
              buff.close();
              break;
            }
          }
        }	
        catch(FileNotFoundException e){
          IJ.log(tr("Error. File not found."));
        }
        catch (NullPointerException e){//end of the file
          arrayLines.remove(arrayLines.size()-1);
          buff.close();
        }
        String[] tabLines =  new String[arrayLines.size()];//convert arrayList to String[]
        for (int i=0;i<arrayLines.size();i++){
          tabLines[i]= ((String)arrayLines.get(i));
        }
        return tabLines;
    }
    
    /**
     * Method to select one and only one file
     * @return the absolute path of the file
     */
    private String selectFile(){
        File selectedFile = null;
        try{
          JFileChooser jF = new JFileChooser();//création dun nouveau filechooser
          jF.setApproveButtonText(tr("OK")); //intitulé du bouton
          jF.setMultiSelectionEnabled(false);

          jF.showOpenDialog(null); //affiche la boite de dialogue

          selectedFile = jF.getSelectedFile(); 
        }
        catch (Exception e){
          IJ.log(tr("Error"));
        }
        if (selectedFile!=null)
            return selectedFile.getAbsolutePath();
        return null;        
    }
    
    /**
     * Method to select one or several files
     * @return array containing the absolute path of the files
     */
    private String[] selectFiles(){
        File[] selectedFiles = null;
        try{
          JFileChooser jF = new JFileChooser();//création dun nouveau filechosser
          jF.setApproveButtonText(tr("OK")); //intitulé du bouton
          jF.setMultiSelectionEnabled(true);

          jF.showOpenDialog(null); //affiche la boite de dialogue

          selectedFiles = jF.getSelectedFiles(); 
        }
        catch (Exception e){
          IJ.log(tr("Error"));
        }
        String[] pathsToReturn = null;
        if (selectedFiles!=null){
            pathsToReturn = new String[selectedFiles.length];
            for(int i=0;i<selectedFiles.length;i++){
                pathsToReturn[i]=selectedFiles[i].getAbsolutePath();
            }
        }
        return pathsToReturn; 
    }
    
    /**
     * Method to select one and only one directory
     * Files are showed to show context to the user but can not be selected
     * @return the absolute path of the directory
     */
    private String selectDirectory(){
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = fileChooser.showDialog(null,tr("Choose directory"));
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
             // if the user accidently click a file, then select the parent directory.
            if (!selectedFile.isDirectory()) {
                selectedFile = selectedFile.getParentFile();
            }
        }
        if (selectedFile!=null)
            return selectedFile.getAbsolutePath()+"/";
        return null;
    }
    
    /**
     * Add a Spectra : useful for saving all the Spectras when saving a session 
     */
    public void addSpectra(Spectra spectra){
        spectrasProduced.add(spectra);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonLanguage;
    private JButton jButtonOpenLst;
    private JButton jButtonOpenXYEList;
    private JButton jButtonParamLst;
    private JButton jButtonParamPIXE;
    private JButton jButtonParamRestore;
    private JButton jButtonParamSaveSession;
    private JButton jButtonRestore;
    private JButton jButtonSaveSession;
    private JLabel jLabelAsk;
    // End of variables declaration//GEN-END:variables
}
