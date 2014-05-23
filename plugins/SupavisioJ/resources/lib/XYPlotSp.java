package SupavisioJ.resources.lib;
import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.MainFrame.MainFrame;
import SupavisioJ.Spectra.Spectra;
import SupavisioJ.resources.lib.CheckBoxListenerSp;

import ij.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

/**
 * Class XYPlotSp is the class representing the spectra management window
 */
public class XYPlotSp extends JFrame {
    
    private String titleGraph;
    private Spectra spectraDrew;
    private JFreeChart chart;
    private static MainFrame parentWindow;
    private boolean yIsLog=false;
    
    public XYPlotSp(Spectra spectraDrew, final String titleWindow,final String titleGraph2, double[] energiesX, double[] dataY) {
        super(titleWindow);
        this.parentWindow=spectraDrew.getParentWindowS();
        this.titleGraph=titleGraph2;
        this.spectraDrew=spectraDrew;
        //create the dataset
        final XYDataset dataset = createDataset(energiesX,dataY);
        //create the chart
        final JFreeChart chart = createChart(dataset,titleGraph2);
        this.chart = chart;
        final CustomChartPanel chartPanel = new CustomChartPanel(chart,this);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        jButtonLogLinActionPerformed(null);
        initComponents(chartPanel);
    }
    
    public Vector getVectButtonsSupp(){
        return vectButtonsSupp;
    }
    
    public JFreeChart getChart(){
        return chart;
    }
    /**
     * Creates the dataset of the Spectra
     * @returns a dataset.
     */
    private static XYDataset createDataset(double[] energiesX,double[] dataY) {
        final XYSeries series1 = new XYSeries(tr("Spectra")+" 1");
        for(int i=0;i<energiesX.length;i++){
            if(dataY[i]>0)
                series1.add(energiesX[i],dataY[i]);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        return dataset;
    }
    
    /**
     * Creates a chart.
     * @param dataset  the data for the chart.
     * @returns a chart.
     */
    private static JFreeChart createChart(final XYDataset dataset, final String titleGraph2) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            titleGraph2,      // chart title
            tr("Energies"),                      // x axis label
            tr("Events number"),              // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // plot orientation (pretty obvious this one)
            false,                    // if true show the legeng (useful for several lignes)
            true,                     // tooltips
            false                     // URLs
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        //final StandardLegend legend = (StandardLegend) chart.getLegend();
        //legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        //code for points and no lines on the chart
        //final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        //renderer.setSeriesLinesVisible(0, false);
        //renderer.setSeriesShapesVisible(1, false);
        //plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;
    }
    
    /**
     * Use this method to show the window to the user
     */
    public void showVisible() {
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }
    
    public void windowClosing(final WindowEvent evt){
        if(evt.getWindow() == this){
            dispose();
        }
    }
    
    /**
     * This method will link each CheckBox to CheckBoxListenerSp
     * @see CheckBoxListenerSp
     */
    private void initComponents(ChartPanel chartPanel) {
        for(int i=0;i<3;i++){
            JComponent[] buttonsToAdd= new JComponent[4];
            JCheckBox jCheckBox1 = new JCheckBox();
            jCheckBox1.setText(tr("NA"));
            jCheckBox1.addItemListener(new CheckBoxListenerSp(this));
            buttonsToAdd[0] = jCheckBox1;
            buttonsToAdd[1] = new JTextField();
            buttonsToAdd[2] = new JTextField();
            buttonsToAdd[3] = new JTextField();
            vectButtonsSupp.add(buttonsToAdd);
        }
        jButtonPlus = new JButton();
        jButtonPlus.setText(tr("More ..."));
        
        jButtonPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlusActionPerformed(evt);
            }
        });
        jButtonGenImg = new JButton();
        jButtonGenImg.setText(tr("Generate pictures"));
        jButtonGenImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenImgActionPerformed(evt);
            }
        });
        
        jButtonLogLin = new JButton();
        jButtonLogLin.setText(tr("Log/Lin"));
        jButtonLogLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogLinActionPerformed(evt);
            }
        });
        
        jButtonSave = new JButton();
        jButtonSave.setText(tr("Save"));
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        
        jButtonSaveGup = new JButton();
        jButtonSaveGup.setText(tr("Save Gupix"));
        jButtonSaveGup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveGupActionPerformed(evt);
            }
        });
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        //Layout horizontal
        ParallelGroup paralGroupHor=layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        SequentialGroup grpChartPanel=layout.createSequentialGroup();
        grpChartPanel.addComponent(chartPanel);
        paralGroupHor.addGroup(grpChartPanel);
        SequentialGroup grp1 = layout.createSequentialGroup();
        grp1.addContainerGap();
        for (int i=0; i<3;i++){
            JComponent[] tablJComp = (JComponent[]) vectButtonsSupp.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tablJComp[0];
            JTextField textFieldCurrentName= (JTextField) tablJComp[1];
            JTextField textFieldCurrentMin= (JTextField) tablJComp[2];
            JTextField textFieldCurrentMax=(JTextField) tablJComp[3];
            textFieldCurrentName.setText(tr("Name"));
            textFieldCurrentMin.setText(tr("Min"));
            textFieldCurrentMax.setText(tr("Max"));
            grp1.addComponent(checkBoxCurrent, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE);
            grp1.addPreferredGap(ComponentPlacement.RELATED);
            grp1.addComponent(textFieldCurrentName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            grp1.addPreferredGap(ComponentPlacement.RELATED);
            grp1.addComponent(textFieldCurrentMin, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE);
            grp1.addPreferredGap(ComponentPlacement.RELATED);
            grp1.addComponent(textFieldCurrentMax, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE);

            if (i!=3){
                grp1.addGap(40,40,40);
            }
            else{
                grp1.addContainerGap(25, Short.MAX_VALUE);
            }
        }
        paralGroupHor.addGroup(grp1);
        
        SequentialGroup grpButton = layout.createSequentialGroup();
        grpButton.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        //grpButton.addGap(130);
        grpButton.addComponent(jButtonPlus, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE);
        grpButton.addPreferredGap(ComponentPlacement.RELATED);
        grpButton.addComponent(jButtonGenImg, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE);
        grpButton.addPreferredGap(ComponentPlacement.RELATED);
        grpButton.addComponent(jButtonLogLin, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE);
        grpButton.addPreferredGap(ComponentPlacement.RELATED);
        grpButton.addComponent(jButtonSave, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE);
        grpButton.addPreferredGap(ComponentPlacement.RELATED);
        grpButton.addComponent(jButtonSaveGup, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE);
        grpButton.addGap(70,70,70);
        paralGroupHor.addGroup(GroupLayout.Alignment.TRAILING, grpButton);
        
        //Layout vertical 
        
        ParallelGroup grpIntChannel = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        for (int i=0;i<3;i++){
            JComponent[] tablJComp = (JComponent[]) vectButtonsSupp.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tablJComp[0];
            JTextField textFieldCurrentName= (JTextField) tablJComp[1];
            JTextField textFieldCurrentMin= (JTextField) tablJComp[2];
            JTextField textFieldCurrentMax= (JTextField) tablJComp[3];
            grpIntChannel.addComponent(checkBoxCurrent);
            grpIntChannel.addComponent(textFieldCurrentName,GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE);
            grpIntChannel.addComponent(textFieldCurrentMin,GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE);
            grpIntChannel.addComponent(textFieldCurrentMax,GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE);
        }
       
        ParallelGroup paralGroup2Vert=layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        SequentialGroup grpAll = layout.createSequentialGroup();
        ParallelGroup gpChart = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        gpChart.addComponent(chartPanel);
        grpAll.addGroup(gpChart);
        grpAll.addContainerGap(10, Short.MAX_VALUE).addGroup(grpIntChannel);
        grpAll.addPreferredGap(ComponentPlacement.RELATED);
        ParallelGroup grpButtons = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        grpButtons.addComponent(jButtonPlus, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE);
        grpButtons.addComponent(jButtonGenImg, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE);
        grpButtons.addComponent(jButtonLogLin, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE);
        grpButtons.addComponent(jButtonSave, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE);
        grpButtons.addComponent(jButtonSaveGup, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE);
        grpAll.addContainerGap(10, Short.MAX_VALUE).addGroup(grpButtons);
        grpAll.addGap(6, 6, 6);
      
        paralGroup2Vert.addGroup(GroupLayout.Alignment.TRAILING,grpAll);
        
        layout.setHorizontalGroup(paralGroupHor);
        layout.setVerticalGroup(paralGroup2Vert);
        pack();
    }                 

    private void jButtonPlusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO create 3 group of boutons at each evt
        IJ.log("fonctionnalité non codée pour le moment");
        //for(int i=0;i<3;i++){
        //    JComponent[] buttonsToAdd= new JComponent[4];
        //    buttonsToAdd[0]=new JCheckBox();
        //    buttonsToAdd[1] = new JTextField();
        //    buttonsToAdd[2] = new JTextField();
        //    buttonsToAdd[3] = new JTextField();
        //    vectButtonsSupp.add(buttonsToAdd);
        //}
        
    }   
    
    private void jButtonGenImgActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // gen img for all checkbox selected
        //First which case is selected
        // number of case = vectButtonsSupp.size()
        // the whole is taken if JComp[0] is selected and 1,2 et 3 are corrects
        try {
            ImageGenerated[] tabImgGenFromSpectra;
            Vector vectValues = getValuesMinMaxNames(false,true);
            ArrayList<String> nameOfImgGen = (ArrayList<String>) vectValues.get(0);
            ArrayList<float[]> minMaxSpectra = (ArrayList<float[]>) vectValues.get(1);
            float[][] minMaxSpectraArray = minMaxSpectra.toArray(new float[minMaxSpectra.size()][2]);
            tabImgGenFromSpectra = spectraDrew.generatePicture(minMaxSpectraArray);
            for (int i=0;i<tabImgGenFromSpectra.length;i++){
                tabImgGenFromSpectra[i].show(nameOfImgGen.get(i));
            }
        }
        catch(NullPointerException e){}
    }
    
    
    public ArrayList<JCheckBox> getCheckBoxSelected(){
        ArrayList<JCheckBox> tabCheckBoxSelected = new ArrayList<JCheckBox>();
        for(int i=0;i<vectButtonsSupp.size();i++){
            JComponent[] tabJComp = (JComponent[]) vectButtonsSupp.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tabJComp[0];
            if(checkBoxCurrent.isSelected()){
                tabCheckBoxSelected.add(checkBoxCurrent);
            }
        }
        return tabCheckBoxSelected;
    }
    
    /**
     * Gets the min, max and name values if checkboxs are selected
     * @param minOrMax if false min AND max have to be correct 
     * @param nameIsImportant if false the method will not check the name
     * @return a vector containing the arraylist of the names and the arraylist of the [min,max]
     */
    public Vector getValuesMinMaxNames(boolean minOrMax, boolean nameIsImportant){
        ArrayList<String> nameOfImgGen = new ArrayList<String>();
        ArrayList<float[]> minMaxSpectra = new ArrayList<float[]>();
        for(int i=0; i<vectButtonsSupp.size();i++){
            JComponent[] tabJCompToCheck = (JComponent[]) vectButtonsSupp.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tabJCompToCheck[0];
            if(checkBoxCurrent.isSelected()){
                JTextField name= (JTextField) tabJCompToCheck[1];
                JTextField min= (JTextField) tabJCompToCheck[2];
                JTextField max=(JTextField) tabJCompToCheck[3];
                if(!( (min.getText().equals(tr("Min")) && max.getText().equals(tr("Max"))) )){
                    if(minOrMax|| !((min.getText().equals(tr("Min")) || max.getText().equals(tr("Max"))))){
                        if ( !nameIsImportant || !(name.getText().equals(tr("Name"))) ) {
                            float start=-1;
                            float end=-1;
                            try {
                                start = Float.valueOf(min.getText());
                            }
                            catch(NumberFormatException e){
                                IJ.log(tr("Put numbers in the Min and Max text fields"));
                            }
                            try {
                                end = Float.valueOf(max.getText());
                            }
                            catch(NumberFormatException e){}
                            if(minOrMax||start<end){
                                if (spectraDrew.energyExist(start) || spectraDrew.energyExist(end)){
                                    if (!spectraDrew.energyExist(start)){
                                        start=spectraDrew.getEnergies()[0];
                                    }
                                    else if(!spectraDrew.energyExist(end)) {
                                        int length = spectraDrew.getEnergies().length;
                                        end=spectraDrew.getEnergies()[length-1];
                                    }
                                    if( !nameIsImportant || !nameOfImgGen.contains(name.getText()) ){//useful to avoid saving problems
                                        if( !nameIsImportant || !(name.getText().contains("_")) ){//useful to avoid restoring problems
                                            nameOfImgGen.add(name.getText());
                                            float[] tabMinMax = new float[2];
                                            tabMinMax[0]=start;
                                            tabMinMax[1]=end;
                                            minMaxSpectra.add(tabMinMax);
                                        }
                                        else{
                                            IJ.log(tr("The character '_' is not allowed"));
                                        }
                                    }
                                    else{
                                        IJ.log(tr("Give different title for each picture please"));
                                    }
                                }
                            }
                        }
                        else {
                            IJ.log(tr("Give names please (other that Name)"));
                        }
                    }
                }
            }
        }
        Vector vectToReturn = new Vector();
        vectToReturn.add(nameOfImgGen);
        vectToReturn.add(minMaxSpectra);
        return vectToReturn;
    }
    
    private String selectDirectory(){
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = fileChooser.showDialog(null,tr("Choose directory"));
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
             // if the user accidently clicks on a file, the parent directory is selected.
            if (!selectedFile.isDirectory()) {
                selectedFile = selectedFile.getParentFile();
            }
        }
        if (selectedFile!=null)
            return selectedFile.getAbsolutePath()+"/";
        return null;
    }
    
    private void jButtonLogLinActionPerformed(java.awt.event.ActionEvent evt) {                                         
        XYPlot plot = (XYPlot) chart.getPlot();
        if (!yIsLog){
            plot.setRangeAxis(new LogarithmicAxis(tr("Events number")));
            yIsLog=true;
        }
        else{
            plot.setRangeAxis(new NumberAxis(tr("Events number")));
            yIsLog=false;
        }
    }
    
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String directory=selectDirectory();
        if(directory!=null){
            spectraDrew.save(directory);
        }
    }
    
    /**
     * @see ADC.saveGupixSpectra 
     */
    private void jButtonSaveGupActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String directory=selectDirectory();
        if(directory!=null){
            String nameToSave=spectraDrew.getFileName()+".gup";
            spectraDrew.getADC().saveGupixSpectra(directory+nameToSave);
        }
    }
    
    public static String tr(String strToTranslate){
        return parentWindow.tr(strToTranslate);
    }
    
    public float getRealXValue(double value, boolean rightIncluded){
        if(spectraDrew.energyExist((float) value)){
            int indiceNearValue=spectraDrew.getIndiceEnergy((float)value,rightIncluded);
            return spectraDrew.getEnergies()[indiceNearValue];
        }
        return -1;
    }
    
    public JTextField getField(JCheckBox checkBoxOfInterest,String nameField){
        for(int i=0;i<vectButtonsSupp.size();i++){
            JComponent[] tabJComp = (JComponent[]) vectButtonsSupp.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tabJComp[0];
            if(checkBoxCurrent==checkBoxOfInterest){
                int indexOfInterest=-1;
                switch(nameField){
                    case "Name" : indexOfInterest=1;break;
                    case "Min" : indexOfInterest=2; break;
                    case "Max" : indexOfInterest=3;break;
                }
                if (indexOfInterest!=-1){
                    return (JTextField) tabJComp[indexOfInterest];
                }
            }
        }
        return null;
    }
        
    // Variables declaration - do not modify 
    private Vector vectButtonsSupp=new Vector();
    private JButton jButtonPlus;
    private JButton jButtonGenImg;
    private JButton jButtonLogLin;
    private JButton jButtonSave;
    private JButton jButtonSaveGup;
    // End of variables declaration               
}

