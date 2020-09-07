/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author Navitus1
 */
public class ErrorMessageBean
{
       private int error_message_id;
       private String message,remark,timestamp,createdBy,active;
       private String message1[],remark1[],cretedBy1[],active1[],message_id[];

    public String[] getActive1() {
        return active1;
    }

    public void setActive1(String[] active1) {
        this.active1 = active1;
    }

    public String[] getCretedBy1() {
        return cretedBy1;
    }

    public void setCretedBy1(String[] cretedBy1) {
        this.cretedBy1 = cretedBy1;
    }

    public String[] getMessage1() {
        return message1;
    }

    public void setMessage1(String[] message1) {
        this.message1 = message1;
    }

    public String[] getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String[] message_id) {
        this.message_id = message_id;
    }

    public String[] getRemark1() {
        return remark1;
    }

    public void setRemark1(String[] remark1) {
        this.remark1 = remark1;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    public int getError_message_id() {
        return error_message_id;
    }

    public void setError_message_id(int error_message_id) {
        this.error_message_id = error_message_id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
