/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * @created Mar 4, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin.jfreechart.outputs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.PersistenceException;

  
/**
 * @author wseyler
 *
 */
public class JFreeChartOutput implements IOutput {
  JFreeChart chart;
  int fileType;
  String filename;
  OutputStream outputStream;
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getAsStream()
   */
  public OutputStream getChartAsStream() throws PersistenceException {
    if (outputStream == null) {
      outputStream = new ByteArrayOutputStream();
    }
    try {
      outputStream.flush();
    } catch (IOException e1) {
      throw new PersistenceException(e1);
    }
    if (fileType == IOutput.FILE_TYPE_JPEG) {
      try {
        ChartUtilities.writeChartAsJPEG(outputStream, chart, 400, 400);
      } catch (IOException e) {
        throw new PersistenceException(e);
      }
    } else if (fileType == IOutput.FILE_TYPE_PNG) {
      try {
        ChartUtilities.writeChartAsPNG(outputStream, chart, 400, 400);
      } catch (IOException e) {
        throw new PersistenceException(e);
      }
    }
    return outputStream;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getChart()
   */
  public JFreeChart getChart() {
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getFileType()
   */
  public int getFileType() {
    return fileType;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persist()
   */
  public void persist() throws PersistenceException {
    if (filename != null && filename.length() > 0) {
      if (fileType == IOutput.FILE_TYPE_JPEG) {
        try {
          ChartUtilities.saveChartAsJPEG(new File(filename), chart, 400, 400);
        } catch (IOException e) {
          throw new PersistenceException(e);
        }
      } else if (fileType == IOutput.FILE_TYPE_PNG) {
        try {
          ChartUtilities.saveChartAsPNG(new File(filename), chart, 400, 400);
        } catch (IOException e) {
          throw new PersistenceException(e);
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setChart(org.pentaho.experimental.chart.plugin.api.engine.Chart)
   */
  public void setChart(Object chart) {
    this.chart = (JFreeChart) chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setFileType(int)
   */
  public void setFileType(int fileType) {
    this.fileType = fileType;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setFilename(java.lang.String)
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getFilename()
   */
  public String getFilename() {
    return filename;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setOutputStream(java.io.OutputStream)
   */
  public void setOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

}