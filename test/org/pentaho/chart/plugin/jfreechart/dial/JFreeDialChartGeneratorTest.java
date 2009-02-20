package org.pentaho.chart.plugin.jfreechart.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.net.URL;

import junit.framework.TestCase;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGenerator;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGenerator.SingleLineDialFrame;



public class JFreeDialChartGeneratorTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
    ChartBoot.getInstance().start();
  }

  protected ChartDocumentContext getChartDocumentContext(final String filename, final ChartTableModel chartTableModel)
      throws Exception {
    final URL chartURL = this.getClass().getResource(filename);
    if (chartURL == null) {
      throw new Exception("chartURL is null"); //$NON-NLS-1$
    }
    return ChartFactory.generateChart(chartURL, chartTableModel);
  }

  protected ChartTableModel getChartTableModel(final Object[][] dataArray) {
    ChartTableModel chartTableModel = new ChartTableModel();
    chartTableModel.setData(dataArray);
    return chartTableModel;
  }

  protected JFreeChart getJFreeChart(final String filename, final Object[][] dataArray) throws Exception {
    JFreeDialChartGenerator chartGen = new JFreeDialChartGenerator();
    ChartTableModel chartTableModel = getChartTableModel(dataArray);
    return chartGen.createChart(getChartDocumentContext(filename, chartTableModel), chartTableModel);
  }
  
  public void testPlotStyle() throws Exception {
    JFreeChart chart = getJFreeChart("PluginTest16b.xml", new Object[][] { { 8D } }); //$NON-NLS-1$
    DialPlot plot = (DialPlot) chart.getPlot(); 
    SingleLineDialFrame frame = (SingleLineDialFrame) plot.getDialFrame();
    assertEquals(frame.getForegroundPaint(), Color.RED);
    assertEquals(String.format("expected: %s but was: %s", 3D, ((BasicStroke) frame.getStroke()).getLineWidth()), frame.getStroke(), new BasicStroke(3F));
  }
  
  public void testCapStyle() throws Exception {
    JFreeChart chart = getJFreeChart("PluginTest16b.xml", new Object[][] { { 8D } }); //$NON-NLS-1$
    DialPlot plot = (DialPlot) chart.getPlot(); 
    DialCap cap = (DialCap) plot.getCap();
    assertEquals(cap.getFillPaint(), new Color(0, 128, 0)); // CSS constant green is 0, 128, 0
    assertEquals(cap.getOutlinePaint(), Color.RED);
    assertEquals(String.format("expected: %s but was: %s", 1D, ((BasicStroke) cap.getOutlineStroke()).getLineWidth()), cap.getOutlineStroke(), new BasicStroke(1F));
    assertEquals(cap.getRadius(), 0.06D);
  }
  
  public void testScale() throws Exception {
    JFreeChart chart = getJFreeChart("PluginTest16b.xml", new Object[][] { { 8D } }); //$NON-NLS-1$
    DialPlot plot = (DialPlot) chart.getPlot(); 
    StandardDialScale scale = (StandardDialScale) plot.getScale(0);
    assertEquals(-20D, scale.getLowerBound());
    assertEquals(20D, scale.getUpperBound());
    assertEquals(-135D, scale.getStartAngle());
    assertEquals(-180D, scale.getExtent());
    
    assertEquals(5D, scale.getMajorTickIncrement());
    assertEquals(2, scale.getMinorTickCount());
    
    assertEquals(new Color(255, 105, 180) /*hotpink*/, scale.getTickLabelPaint());
    assertEquals(12, scale.getTickLabelFont().getSize());
    assertEquals(Font.ITALIC | Font.BOLD, scale.getTickLabelFont().getStyle());
    // had trouble getting a font comparison to work without breaking it down into size, style, and name
    assertEquals("monospace", scale.getTickLabelFont().getName());
  }
  
  public void testValueIndicator() throws Exception {
    // couldn't find a way to retrieve the layer that adds the DialValueIndicator;
    // so there is no low-level unit test for value indicator
  }
  
  public void testAnnotation() throws Exception {
    // couldn't find a way to retrieve the layer that adds the DialTextAnnotation;
    // so there is no low-level unit test for value indicator    
  }

}