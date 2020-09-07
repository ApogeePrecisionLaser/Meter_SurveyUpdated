/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class FuseTypeBean {
 private int fuse_id;
 private String fuse_type;
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

    public int getFuse_id() {
        return fuse_id;
    }

    public void setFuse_id(int fuse_id) {
        this.fuse_id = fuse_id;
    }

    public String getFuse_type() {
        return fuse_type;
    }

    public void setFuse_type(String fuse_type) {
        this.fuse_type = fuse_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
 
}
