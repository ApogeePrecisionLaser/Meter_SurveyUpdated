/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.tableClasses;

/**
 *
 * @author Shruti
 */
public class ComplaintStatus {

    private int complaint_status_id;
    private String complaint_status;
    private String description;
    private String[] complaint_status_idM;
    private String[] complaint_statusM;
    private String[] descriptionM;

    public String getComplaint_status() {
        return complaint_status;
    }

    public void setComplaint_status(String complaint_status) {
        this.complaint_status = complaint_status;
    }

    public String[] getComplaint_statusM() {
        return complaint_statusM;
    }

    public void setComplaint_statusM(String[] complaint_statusM) {
        this.complaint_statusM = complaint_statusM;
    }

    public int getComplaint_status_id() {
        return complaint_status_id;
    }

    public void setComplaint_status_id(int complaint_status_id) {
        this.complaint_status_id = complaint_status_id;
    }

    public String[] getComplaint_status_idM() {
        return complaint_status_idM;
    }

    public void setComplaint_status_idM(String[] complaint_status_idM) {
        this.complaint_status_idM = complaint_status_idM;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getDescriptionM() {
        return descriptionM;
    }

    public void setDescriptionM(String[] descriptionM) {
        this.descriptionM = descriptionM;
    }


}
