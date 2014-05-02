package SupavisioJ.MainFrame;

import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.ConvertListFiles.FrameC.FrameC;
import SupavisioJ.DataFileXYEList.DataFileXYEList;
import SupavisioJ.FrameConfigSave.FrameConfigSave;
import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.Spectra.Spectra;
import ij.IJ;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *This class is the first window which will be open at the launch of SupavisioJ
 */
public class MainFrame extends javax.swing.JFrame {
    private FrameC frameConfigLst = new FrameC();
    private FrameConfigSave frameConfigSaveSession = new FrameConfigSave(this);
    private ArrayList<Spectra> spectrasProduced = new ArrayList<Spectra>();
    private boolean saveImagesOfSession = false;
    private String nameOfApplication = "SupavisioJ";

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
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

        jButtonOpenLst = new javax.swing.JButton();
        jLabelAsk = new javax.swing.JLabel();
        jButtonOpenXYEList = new javax.swing.JButton();
        jButtonParamLst = new javax.swing.JButton();
        jButtonParamPIXE = new javax.swing.JButton();
        jButtonSaveSession = new javax.swing.JButton();
        jButtonParamSaveSession = new javax.swing.JButton();
        jButtonRestore = new javax.swing.JButton();
        jButtonParamRestore = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SupavisioJ");

        jButtonOpenLst.setText("Ouvrir un fichier .lst");
        jButtonOpenLst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenLstActionPerformed(evt);
            }
        });

        jLabelAsk.setText("Que voulez-vous faire?");

        jButtonOpenXYEList.setText("Ouvrir un fichier format XYE");
        jButtonOpenXYEList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenXYEListActionPerformed(evt);
            }
        });

        jButtonParamLst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamLst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParamLstActionPerformed(evt);
            }
        });

        jButtonParamPIXE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamPIXE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParamPIXEActionPerformed(evt);
            }
        });

        jButtonSaveSession.setText("Sauvegarder la session SpJ");
        jButtonSaveSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveSessionActionPerformed(evt);
            }
        });

        jButtonParamSaveSession.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamSaveSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParamSaveSessionActionPerformed(evt);
            }
        });

        jButtonRestore.setText("Restaurer fichiers/sessions");
        jButtonRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRestoreActionPerformed(evt);
            }
        });

        jButtonParamRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SupavisioJ/resources/images/avance-parametres-32.png"))); // NOI18N
        jButtonParamRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParamRestoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabelAsk))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButtonOpenLst, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonParamLst, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButtonOpenXYEList, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonParamPIXE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButtonSaveSession, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonParamSaveSession, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButtonRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonParamRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAsk, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpenLst)
                    .addComponent(jButtonParamLst, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOpenXYEList)
                    .addComponent(jButtonParamPIXE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSaveSession)
                    .addComponent(jButtonParamSaveSession, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRestore)
                    .addComponent(jButtonParamRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOpenLstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenLstActionPerformed
        frameConfigLst.openLST();
    }//GEN-LAST:event_jButtonOpenLstActionPerformed

    private void jButtonParamLstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParamLstActionPerformed
        frameConfigLst.setVisible(true);
    }//GEN-LAST:event_jButtonParamLstActionPerformed

    private void jButtonOpenXYEListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenXYEListActionPerformed
        String path=selectFile();
        DataFileXYEList fileXYE=new DataFileXYEList(path);
        ADC adcXYE = fileXYE.open(); 
        if ( adcXYE!=null && adcXYE.getNEvents()>1 && (adcXYE.getlastEvent()[0]!=0 && adcXYE.getlastEvent()[1]!=0) ){//check if a correct file has been open
            Spectra spectraXYE= new Spectra(adcXYE,fileXYE.getName());
            if(spectraXYE.getEnergies().length>1){//check if a correct file has been open
                spectraXYE.setParentWindow(this);
                spectraXYE.plotSpectra(nameOfApplication,"Spectre "+spectraXYE.getFileName()).showVisible();
            }
        }
    }//GEN-LAST:event_jButtonOpenXYEListActionPerformed

    private void jButtonParamPIXEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParamPIXEActionPerformed
        IJ.log("Pas de paramètres pour le moment");
    }//GEN-LAST:event_jButtonParamPIXEActionPerformed

    private void jButtonSaveSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveSessionActionPerformed
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

    private void jButtonParamSaveSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParamSaveSessionActionPerformed
        frameConfigSaveSession.setVisible(true);
    }//GEN-LAST:event_jButtonParamSaveSessionActionPerformed

    private void jButtonRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRestoreActionPerformed
        String[] paths = selectFiles();
        for (String path : paths) {
            if(path.contains("sess.spj")){
                restoreSession(path);
            }
            else if(path.contains("spct.spj")){
                Spectra spectToRestore = new Spectra(path,this);
                spectToRestore.plotSpectra(nameOfApplication, "Spectre "+spectToRestore.getFileName()).showVisible();
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
                    sourceSpectra.plotSpectra(nameOfApplication, "Spectre "+sourceSpectra.getFileName());
                }
                sourceSpectra.restoreImgGen(path);
            }
        }
    }//GEN-LAST:event_jButtonRestoreActionPerformed

    private void jButtonParamRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParamRestoreActionPerformed
        IJ.log("Fonctionnalité non codée pour le moment");
    }//GEN-LAST:event_jButtonParamRestoreActionPerformed

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
            IJ.log("Echec de l'enregistrement de la session");
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
                    spectToRestore.plotSpectra(nameOfApplication, "Spectre "+spectToRestore.getFileName()).showVisible();
                }
                else if (readLine.contains("img")){
                    Spectra sourceSpectra = spectrasProduced.get(spectrasProduced.size()-1);
                    sourceSpectra.restoreImgGen(directory+readLine);
                }
            }
            
        }
        catch(IOException e){}
    }
    
    /**
     * Method to read a text file
     * @return return an array containing the lines of file. "\n" has been removed.
     */
    private String[] readLinesFile(String path)throws IOException{
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
          IJ.log("Erreur. Fichier de sauvegarde non trouvé");
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
          jF.setApproveButtonText("OK"); //intitulé du bouton
          jF.setMultiSelectionEnabled(false);

          jF.showOpenDialog(null); //affiche la boite de dialogue

          selectedFile = jF.getSelectedFile(); 
        }
        catch (Exception e){
          IJ.log("Erreur");
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
          jF.setApproveButtonText("OK"); //intitulé du bouton
          jF.setMultiSelectionEnabled(true);

          jF.showOpenDialog(null); //affiche la boite de dialogue

          selectedFiles = jF.getSelectedFiles(); 
        }
        catch (Exception e){
          IJ.log("Erreur");
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
        int option = fileChooser.showDialog(null,"Choisir dossier");
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
    private javax.swing.JButton jButtonOpenLst;
    private javax.swing.JButton jButtonOpenXYEList;
    private javax.swing.JButton jButtonParamLst;
    private javax.swing.JButton jButtonParamPIXE;
    private javax.swing.JButton jButtonParamRestore;
    private javax.swing.JButton jButtonParamSaveSession;
    private javax.swing.JButton jButtonRestore;
    private javax.swing.JButton jButtonSaveSession;
    private javax.swing.JLabel jLabelAsk;
    // End of variables declaration//GEN-END:variables
}
