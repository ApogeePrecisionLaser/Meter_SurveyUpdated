/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class TimeIintervalBean {
  
  private int time_interval_id;
  private String time_interval;
  private String time_interval_for;
  private String interval_type;

  
    public int getTime_interval_id() {
        return time_interval_id;
    }


    public void setTime_interval_id(int time_interval_id) {
        this.time_interval_id = time_interval_id;
    }

 
    public String getTime_interval() {
        return time_interval;
    }

 
    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }

   
    public String getTime_interval_for() {
        return time_interval_for;
    }

    /**
     * @param time_interval_for the time_interval_for to set
     */
    public void setTime_interval_for(String time_interval_for) {
        this.time_interval_for = time_interval_for;
    }

 
    public String getInterval_type() {
        return interval_type;
    }

  
    public void setInterval_type(String interval_type) {
        this.interval_type = interval_type;
    }
 

  
}
