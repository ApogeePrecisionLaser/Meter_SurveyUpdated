/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class ControlMechanismTypeBean {
 private int control_mechanism_id;
 private String control_mechanism_type;
 private String created_date;
 private String remark;

    public int getControl_mechanism_id() {
        return control_mechanism_id;
    }

    public void setControl_mechanism_id(int control_mechanism_id) {
        this.control_mechanism_id = control_mechanism_id;
    }

    public String getControl_mechanism_type() {
        return control_mechanism_type;
    }

    public void setControl_mechanism_type(String control_mechanism_type) {
        this.control_mechanism_type = control_mechanism_type;
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

}
