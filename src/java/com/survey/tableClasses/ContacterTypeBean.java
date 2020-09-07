/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class ContacterTypeBean {
private int contacter_id;
private String contacter_type;
private String created_by;
private String created_date;
private String remark;

    public int getContacter_id() {
        return contacter_id;
    }

    public void setContacter_id(int contacter_id) {
        this.contacter_id = contacter_id;
    }

    public String getContacter_type() {
        return contacter_type;
    }

    public void setContacter_type(String contacter_type) {
        this.contacter_type = contacter_type;
    }

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

}
