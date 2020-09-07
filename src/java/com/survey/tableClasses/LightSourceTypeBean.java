/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class LightSourceTypeBean {
  private int source_type_id;
  private String source_type;
  private String active;
  private String created_by;
  private String created_date;
  private String remark;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public int getSource_type_id() {
        return source_type_id;
    }

    public void setSource_type_id(int source_type_id) {
        this.source_type_id = source_type_id;
    }
  
}
