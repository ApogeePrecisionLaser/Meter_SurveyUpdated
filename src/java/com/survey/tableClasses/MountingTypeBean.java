/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class MountingTypeBean {
  private int mounting_type_id;
  private String mounting_type;
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

    public String getMounting_type() {
        return mounting_type;
    }

    public void setMounting_type(String mounting_type) {
        this.mounting_type = mounting_type;
    }

    public int getMounting_type_id() {
        return mounting_type_id;
    }

    public void setMounting_type_id(int mounting_type_id) {
        this.mounting_type_id = mounting_type_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
