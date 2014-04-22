package lib;
import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.Spectra.Spectra;
import java.awt.Color;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JCheckBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import ij.*;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.jfree.chart.ChartUtilities;

public class XYPlotSp extends JFrame {
    
    private String titleGraph;
    private Spectra spectraDrew;
    
    public XYPlotSp(Spectra spectraDrew, final String titleWindow,final String titleGraph2, double[] energiesX, double[] dataY) {
        super(titleWindow);
        this.titleGraph=titleGraph2;
        this.spectraDrew=spectraDrew;
        //create the dataset
        final XYDataset dataset = createDataset(energiesX,dataY);
        //create the chart
        final JFreeChart chart = createChart(dataset,titleGraph2);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        initComponents(chartPanel);
    }
    /**
     * Creates the dataset.
     * 
     * @return a sample dataset.
     */
    private static XYDataset createDataset(double[] energiesX,double[] dataY) {
        final XYSeries series1 = new XYSeries("Spectre 1");
        for(int i=0;i<energiesX.length;i++){
            series1.add(energiesX[i],dataY[i]);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        return dataset;
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private static JFreeChart createChart(final XYDataset dataset, final String titleGraph2) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            titleGraph2,      // chart title
            "Energies",                      // x axis label
            "Nbre événements",              // y axis label
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
    
    private void initComponents(ChartPanel chartPanel) {
        for(int i=0;i<3;i++){
            JComponent[] buttonsToAdd= new JComponent[4];
            JCheckBox jCheckBox1 = new JCheckBox();
            jCheckBox1.setText("NA");
            buttonsToAdd[0] = jCheckBox1;
            buttonsToAdd[1] = new JTextField();
            buttonsToAdd[2] = new JTextField();
            buttonsToAdd[3] = new JTextField();
            vectButtonsSupp.add(buttonsToAdd);
        }
        jButtonPlus = new JButton();
        jButtonPlus.setText("Plus ...");
        
        jButtonPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlusActionPerformed(evt);
            }
        });
        jButtonGenImg = new JButton();
        jButtonGenImg.setText("Génerer images");
        jButtonGenImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenImgActionPerformed(evt);
            }
        });
        
        jButtonLogLin = new JButton();
        jButtonLogLin.setText("Log/Lin");
        jButtonLogLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogLinActionPerformed(evt);
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
            textFieldCurrentName.setText("Name");
            textFieldCurrentMin.setText("Min");
            textFieldCurrentMax.setText("Max");
            grp1.addComponent(checkBoxCurrent, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE);
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
        grpButton.addGap(155, 155, 155);
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
        // TODO gen img for all checkbox selected
        //First which case is selected
        // number of case = vectButtonsSupp.size()
        // the whole is taken if JComp[0] is selected and 1,2 et 3 are corrects
        try {
            ImageGenerated[] tabImgGenFromSpectra;
            ArrayList<String> nameOfImgGen = new ArrayList<String>();
            ArrayList<float[]> minMaxSpectra = new ArrayList<float[]>();
            for(int i=0; i<vectButtonsSupp.size();i++){
                JComponent[] tabJCompToCheck = (JComponent[]) vectButtonsSupp.get(i);
                JCheckBox checkBoxCurrent = (JCheckBox) tabJCompToCheck[0];
                if(checkBoxCurrent.isSelected()){
                    JTextField name= (JTextField) tabJCompToCheck[1];
                    JTextField min= (JTextField) tabJCompToCheck[2];
                    JTextField max=(JTextField) tabJCompToCheck[3];
                    if(!(name.getText().equals("name") || min.getText().equals("min") || max.getText().equals("max"))){
                        float start=-1;
                        float end=-1;
                        try {
                            start = Float.valueOf(min.getText());
                            end = Float.valueOf(max.getText());
                        }
                        catch(NumberFormatException e){
                            IJ.log("Mettez des chiffres dans les champs min et max");
                        }
                        if (spectraDrew.energyExist(start) || spectraDrew.energyExist(end)){
                            if (!spectraDrew.energyExist(start)){
                                start=spectraDrew.getEnergies()[0];
                            }
                            else if(!spectraDrew.energyExist(end)) {
                                int length = spectraDrew.getEnergies().length;
                                end=spectraDrew.getEnergies()[length-1];
                            }
                            nameOfImgGen.add(name.getText());
                            float[] tabMinMax = new float[2];
                            tabMinMax[0]=start;
                            tabMinMax[1]=end;
                            minMaxSpectra.add(tabMinMax);
                        }
                    }
                }
            }
            float[][] minMaxSpectraArray = minMaxSpectra.toArray(new float[minMaxSpectra.size()][2]);
            tabImgGenFromSpectra = spectraDrew.generatePicture(minMaxSpectraArray);
            for (int i=0;i<tabImgGenFromSpectra.length;i++){
                tabImgGenFromSpectra[i].show(nameOfImgGen.get(i));
            }
        }
        catch(NullPointerException e){}
    }
    
    private void jButtonLogLinActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO afficher log ou lin l'axe des y
        IJ.log("fonctionnalité non codée pour le moment");
    }
        
    // Variables declaration - do not modify 
    private Vector vectButtonsSupp=new Vector();
    private JButton jButtonPlus;
    private JButton jButtonGenImg;
    private JButton jButtonLogLin;
    // End of variables declaration                   
}
