/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.tableClasses;

/**
 *
 * @author Administrator
 */
public class TubeWellUserDataTypeBean {

    private int tubewell_user_data_id;
    private String reading;
    private String longitude;
    private String lattitude;
    private String image_name;
    private int tubewell_user_id;
    private int general_image_details_id, status_id;
    private String tubewell_user;
    private String remark;
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGeneral_image_details_id() {
        return general_image_details_id;
    }

    public void setGeneral_image_details_id(int general_image_details_id) {
        this.general_image_details_id = general_image_details_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getTubewell_user() {
        return tubewell_user;
    }

    public void setTubewell_user(String tubewell_user) {
        this.tubewell_user = tubewell_user;
    }

    public int getTubewell_user_data_id() {
        return tubewell_user_data_id;
    }

    public void setTubewell_user_data_id(int tubewell_user_data_id) {
        this.tubewell_user_data_id = tubewell_user_data_id;
    }

    public int getTubewell_user_id() {
        return tubewell_user_id;
    }

    public void setTubewell_user_id(int tubewell_user_id) {
        this.tubewell_user_id = tubewell_user_id;
    }
}
