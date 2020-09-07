/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class MccbTypeBean {
private int mccb_id;
private String mccb_type;
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

    public int getMccb_id() {
        return mccb_id;
    }

    public void setMccb_id(int mccb_id) {
        this.mccb_id = mccb_id;
    }

    public String getMccb_type() {
        return mccb_type;
    }

    public void setMccb_type(String mccb_type) {
        this.mccb_type = mccb_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
