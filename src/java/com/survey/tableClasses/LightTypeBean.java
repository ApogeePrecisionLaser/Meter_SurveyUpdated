/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class LightTypeBean {
  private int light_type_id;
  private int wattage_id;
  private int source_id;
  private String source_type;
  private String wattage_value;
  private String created_by;
  private String created_date;
  private String remark;
  private String isParent;
  private String ischild;
  private int childid;

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getIschild() {
        return ischild;
    }

    public void setIschild(String ischild) {
        this.ischild = ischild;
    }

    public int getChildid() {
        return childid;
    }

    public void setChildid(int childid) {
        this.childid = childid;
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

    public int getLight_type_id() {
        return light_type_id;
    }

    public void setLight_type_id(int light_type_id) {
        this.light_type_id = light_type_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public int getWattage_id() {
        return wattage_id;
    }

    public void setWattage_id(int wattage_id) {
        this.wattage_id = wattage_id;
    }

    public String getWattage_value() {
        return wattage_value;
    }

    public void setWattage_value(String wattage_value) {
        this.wattage_value = wattage_value;
    }
  
}
