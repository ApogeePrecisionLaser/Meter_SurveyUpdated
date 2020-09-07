/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class TimerTypeBean {
private int timer_id;
private String timer_type;
private String created_by;
private String created_date;
private String remark;

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTimer_id() {
        return timer_id;
    }

    public void setTimer_id(int timer_id) {
        this.timer_id = timer_id;
    }

    public String getTimer_type() {
        return timer_type;
    }

    public void setTimer_type(String timer_type) {
        this.timer_type = timer_type;
    }

}
