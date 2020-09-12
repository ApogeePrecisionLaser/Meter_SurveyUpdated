/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.tableClasses;

/**
 *
 * @author Administrator
 */
public class PrimarySurveyBean {
    
    int primary_survey_id;
    String type_of_survey, meter_no, meter_reading, image_of_meter_no, image_of_meter_reading,dateTime, accuracy
            ,image_name_meter_no,image_name_meter_reading;
    double latitude, longitude, altitude;

    public int getPrimary_survey_id() {
        return primary_survey_id;
    }

    public void setPrimary_survey_id(int primary_survey_id) {
        this.primary_survey_id = primary_survey_id;
    }

    public String getType_of_survey() {
        return type_of_survey;
    }

    public void setType_of_survey(String type_of_survey) {
        this.type_of_survey = type_of_survey;
    }

    public String getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public String getMeter_reading() {
        return meter_reading;
    }

    public void setMeter_reading(String meter_reading) {
        this.meter_reading = meter_reading;
    }

    public String getImage_of_meter_no() {
        return image_of_meter_no;
    }

    public void setImage_of_meter_no(String image_of_meter_no) {
        this.image_of_meter_no = image_of_meter_no;
    }

    public String getImage_of_meter_reading() {
        return image_of_meter_reading;
    }

    public void setImage_of_meter_reading(String image_of_meter_reading) {
        this.image_of_meter_reading = image_of_meter_reading;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getImage_name_meter_no() {
        return image_name_meter_no;
    }

    public void setImage_name_meter_no(String image_name_meter_no) {
        this.image_name_meter_no = image_name_meter_no;
    }

    public String getImage_name_meter_reading() {
        return image_name_meter_reading;
    }

    public void setImage_name_meter_reading(String image_name_meter_reading) {
        this.image_name_meter_reading = image_name_meter_reading;
    }
    
    
    
}
