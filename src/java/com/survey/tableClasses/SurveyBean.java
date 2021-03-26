/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class SurveyBean {

    private int survey_id;
    private int switching_point_detail_id;
    private int pole_id;
    private int switching_rev_no;
    private int pole_rev_no;
    private int switching_point_survey_id;
    private int switching_point_survey_rev_no;
    private int survey_rev_no;
    private String created_by;
    private String meter_address;
    private String survey_file_no;
    private String survey_page_no;
    private String survey_date;
    private String survey_by;
    private String survey_type;
    private String pole_no;
    private String switching_point_no;
    private String switching_point_name;
    private String remark;
    private String active;
    private String status;
    private String meter_no;
    private String meter_functional;
    private String r_phase;
    private String y_phase;
    private String b_phase;
    private String power;
    private String fuse_functional;
    private String mccb_functional;
    private String created_date;
    private String image_name;
    private String image_date_time;
    private String longitude;
    private String latitude;
    private String fuse_quantity;
    private String mccb_quantity;
    private String fuse1;
    private String fuse2;
    private String fuse3;
    private String mccb1;
    private String mccb2;
    private String mccb3;
    private String contacter_id;
    private String contacter_functional;
    private String contacter_type;
    private String contacter_make_id;
    private String contacter_capacity;
    private String reason_type;
    private String fuse_id1;
    private String fuse_id2;
    private String fuse_id3;
    private String mccb_id1;
    private String mccb_id2;
    private String mccb_id3;
    private String meter_status;
    private String fuse_type1;
    private String fuse_type2;
    private String fuse_type3;
    private String mccb_type1;
    private String mccb_type2;
    private String mccb_type3;
    private String auto_switch_type;
    private String main_switch_type;
    private String main_switch_rating;
    private String auto_switch_type_id;
    private String main_switch_type_id;
    private String enclosure_type_id;
    private String enclosure_type;
    private int no_of_phase;
    private int meter_phase;
    private String meter_reading;
    private String survey_pole_no;
    private String reason_id;
    private int starter_id;
    private String starter_functional;
    private String starter_type;
    private int starter_make_id;
    private String starter_capacity;
    private String starter_make;
    private String contacter_make;
    private String meter_name_auto;
    private String service_conn_no;
    private String survey_with_contact;
    private String survey_with_name;

    public void setSurvey_with_contact(String survey_with_contact) {
        this.survey_with_contact = survey_with_contact;
    }

    public void setSurvey_with_name(String survey_with_name) {
        this.survey_with_name = survey_with_name;
    }

    public String getMeter_address() {
        return meter_address;
    }

    public void setMeter_address(String meter_address) {
        this.meter_address = meter_address;
    }

    public String getSurvey_with_contact() {
        return survey_with_contact;
    }

    public String getSurvey_with_name() {
        return survey_with_name;
    }

    public void setService_conn_no(String service_conn_no) {
        this.service_conn_no = service_conn_no;
    }

    public String getService_conn_no() {
        return service_conn_no;
    }

    public void setMeter_name_auto(String meter_name_auto) {
        this.meter_name_auto = meter_name_auto;
    }

    public String getMeter_name_auto() {
        return meter_name_auto;
    }

    public String getContacter_make() {
        return contacter_make;
    }

    public void setContacter_make(String contacter_make) {
        this.contacter_make = contacter_make;
    }

    public String getStarter_make() {
        return starter_make;
    }

    public void setStarter_make(String starter_make) {
        this.starter_make = starter_make;
    }
    
    public String getStarter_capacity() {
        return starter_capacity;
    }

    public void setStarter_capacity(String starter_capacity) {
        this.starter_capacity = starter_capacity;
    }

    public String getStarter_functional() {
        return starter_functional;
    }

    public void setStarter_functional(String starter_functional) {
        this.starter_functional = starter_functional;
    }

    public int getStarter_id() {
        return starter_id;
    }

    public void setStarter_id(int starter_id) {
        this.starter_id = starter_id;
    }

    public int getStarter_make_id() {
        return starter_make_id;
    }

    public void setStarter_make_id(int starter_make_id) {
        this.starter_make_id = starter_make_id;
    }

    public String getStarter_type() {
        return starter_type;
    }

    public void setStarter_type(String starter_type) {
        this.starter_type = starter_type;
    }

    
    public String getAuto_switch_type_id() {
        return auto_switch_type_id;
    }

    public void setAuto_switch_type_id(String auto_switch_type_id) {
        this.auto_switch_type_id = auto_switch_type_id;
    }

    public String getContacter_id() {
        return contacter_id;
    }

    public void setContacter_id(String contacter_id) {
        this.contacter_id = contacter_id;
    }

    public String getContacter_make_id() {
        return contacter_make_id;
    }

    public void setContacter_make_id(String contacter_make_id) {
        this.contacter_make_id = contacter_make_id;
    }

    public String getEnclosure_type_id() {
        return enclosure_type_id;
    }

    public void setEnclosure_type_id(String enclosure_type_id) {
        this.enclosure_type_id = enclosure_type_id;
    }

    public String getFuse_id1() {
        return fuse_id1;
    }

    public void setFuse_id1(String fuse_id1) {
        this.fuse_id1 = fuse_id1;
    }

    public String getFuse_id2() {
        return fuse_id2;
    }

    public void setFuse_id2(String fuse_id2) {
        this.fuse_id2 = fuse_id2;
    }

    public String getFuse_id3() {
        return fuse_id3;
    }

    public void setFuse_id3(String fuse_id3) {
        this.fuse_id3 = fuse_id3;
    }

    public String getMain_switch_type_id() {
        return main_switch_type_id;
    }

    public void setMain_switch_type_id(String main_switch_type_id) {
        this.main_switch_type_id = main_switch_type_id;
    }

    public String getMccb_id1() {
        return mccb_id1;
    }

    public void setMccb_id1(String mccb_id1) {
        this.mccb_id1 = mccb_id1;
    }

    public String getMccb_id2() {
        return mccb_id2;
    }

    public void setMccb_id2(String mccb_id2) {
        this.mccb_id2 = mccb_id2;
    }

    public String getMccb_id3() {
        return mccb_id3;
    }

    public void setMccb_id3(String mccb_id3) {
        this.mccb_id3 = mccb_id3;
    }

    public String getReason_id() {
        return reason_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
    }

   


    public String getSurvey_pole_no() {
        return survey_pole_no;
    }

    public void setSurvey_pole_no(String survey_pole_no) {
        this.survey_pole_no = survey_pole_no;
    }


    public int getMeter_phase() {
        return meter_phase;
    }

    public void setMeter_phase(int meter_phase) {
        this.meter_phase = meter_phase;
    }

    public String getMeter_reading() {
        return meter_reading;
    }

    public void setMeter_reading(String meter_reading) {
        this.meter_reading = meter_reading;
    }

    public int getNo_of_phase() {
        return no_of_phase;
    }

    public void setNo_of_phase(int no_of_phase) {
        this.no_of_phase = no_of_phase;
    }

    public String getContacter_functional() {
        return contacter_functional;
    }

    public void setContacter_functional(String contacter_functional) {
        this.contacter_functional = contacter_functional;
    }


    public String getAuto_switch_type() {
        return auto_switch_type;
    }

    public void setAuto_switch_type(String auto_switch_type) {
        this.auto_switch_type = auto_switch_type;
    }


    public String getContacter_capacity() {
        return contacter_capacity;
    }

    public void setContacter_capacity(String contacter_capacity) {
        this.contacter_capacity = contacter_capacity;
    }

  

    public String getContacter_type() {
        return contacter_type;
    }

    public void setContacter_type(String contacter_type) {
        this.contacter_type = contacter_type;
    }

    public String getEnclosure_type() {
        return enclosure_type;
    }

    public void setEnclosure_type(String enclosure_type) {
        this.enclosure_type = enclosure_type;
    }

    

    public String getMain_switch_rating() {
        return main_switch_rating;
    }

    public void setMain_switch_rating(String main_switch_rating) {
        this.main_switch_rating = main_switch_rating;
    }

    public String getMain_switch_type() {
        return main_switch_type;
    }

    public void setMain_switch_type(String main_switch_type) {
        this.main_switch_type = main_switch_type;
    }

  

    public String getFuse_type1() {
        return fuse_type1;
    }

    public void setFuse_type1(String fuse_type1) {
        this.fuse_type1 = fuse_type1;
    }

    public String getFuse_type2() {
        return fuse_type2;
    }

    public void setFuse_type2(String fuse_type2) {
        this.fuse_type2 = fuse_type2;
    }

    public String getFuse_type3() {
        return fuse_type3;
    }

    public void setFuse_type3(String fuse_type3) {
        this.fuse_type3 = fuse_type3;
    }

    public String getMccb_type1() {
        return mccb_type1;
    }

    public void setMccb_type1(String mccb_type1) {
        this.mccb_type1 = mccb_type1;
    }

    public String getMccb_type2() {
        return mccb_type2;
    }

    public void setMccb_type2(String mccb_type2) {
        this.mccb_type2 = mccb_type2;
    }

    public String getMccb_type3() {
        return mccb_type3;
    }

    public void setMccb_type3(String mccb_type3) {
        this.mccb_type3 = mccb_type3;
    }

    public String getMeter_status() {
        return meter_status;
    }

    public void setMeter_status(String meter_status) {
        this.meter_status = meter_status;
    }

    
    public String getReason_type() {
        return reason_type;
    }

    public void setReason_type(String reason_type) {
        this.reason_type = reason_type;
    }

    public String getFuse1() {
        return fuse1;
    }

    public void setFuse1(String fuse1) {
        this.fuse1 = fuse1;
    }

    public String getFuse2() {
        return fuse2;
    }

    public void setFuse2(String fuse2) {
        this.fuse2 = fuse2;
    }

    public String getFuse3() {
        return fuse3;
    }

    public void setFuse3(String fuse3) {
        this.fuse3 = fuse3;
    }

    public String getMccb1() {
        return mccb1;
    }

    public void setMccb1(String mccb1) {
        this.mccb1 = mccb1;
    }

    public String getMccb2() {
        return mccb2;
    }

    public void setMccb2(String mccb2) {
        this.mccb2 = mccb2;
    }

    public String getMccb3() {
        return mccb3;
    }

    public void setMccb3(String mccb3) {
        this.mccb3 = mccb3;
    }

    public String getFuse_quantity() {
        return fuse_quantity;
    }

    public void setFuse_quantity(String fuse_quantity) {
        this.fuse_quantity = fuse_quantity;
    }

    public String getMccb_quantity() {
        return mccb_quantity;
    }

    public void setMccb_quantity(String mccb_quantity) {
        this.mccb_quantity = mccb_quantity;
    }

    public String getImage_date_time() {
        return image_date_time;
    }

    public void setImage_date_time(String image_date_time) {
        this.image_date_time = image_date_time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getB_phase() {
        return b_phase;
    }

    public void setB_phase(String b_phase) {
        this.b_phase = b_phase;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getFuse_functional() {
        return fuse_functional;
    }

    public void setFuse_functional(String fuse_functional) {
        this.fuse_functional = fuse_functional;
    }

    public String getMccb_functional() {
        return mccb_functional;
    }

    public void setMccb_functional(String mccb_functional) {
        this.mccb_functional = mccb_functional;
    }

    public String getMeter_functional() {
        return meter_functional;
    }

    public void setMeter_functional(String meter_functional) {
        this.meter_functional = meter_functional;
    }

    public String getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getR_phase() {
        return r_phase;
    }

    public void setR_phase(String r_phase) {
        this.r_phase = r_phase;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSurvey_rev_no() {
        return survey_rev_no;
    }

    public void setSurvey_rev_no(int survey_rev_no) {
        this.survey_rev_no = survey_rev_no;
    }

    public int getSwitching_point_survey_id() {
        return switching_point_survey_id;
    }

    public void setSwitching_point_survey_id(int switching_point_survey_id) {
        this.switching_point_survey_id = switching_point_survey_id;
    }

    public int getSwitching_point_survey_rev_no() {
        return switching_point_survey_rev_no;
    }

    public void setSwitching_point_survey_rev_no(int switching_point_survey_rev_no) {
        this.switching_point_survey_rev_no = switching_point_survey_rev_no;
    }

    public String getY_phase() {
        return y_phase;
    }

    public void setY_phase(String y_phase) {
        this.y_phase = y_phase;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getPole_id() {
        return pole_id;
    }

    public void setPole_id(int pole_id) {
        this.pole_id = pole_id;
    }

    public String getPole_no() {
        return pole_no;
    }

    public void setPole_no(String pole_no) {
        this.pole_no = pole_no;
    }

    public int getPole_rev_no() {
        return pole_rev_no;
    }

    public void setPole_rev_no(int pole_rev_no) {
        this.pole_rev_no = pole_rev_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSurvey_by() {
        return survey_by;
    }

    public void setSurvey_by(String survey_by) {
        this.survey_by = survey_by;
    }

    public String getSurvey_date() {
        return survey_date;
    }

    public void setSurvey_date(String survey_date) {
        this.survey_date = survey_date;
    }

    public String getSurvey_page_no() {
        return survey_page_no;
    }

    public void setSurvey_page_no(String survey_page_no) {
        this.survey_page_no = survey_page_no;
    }

    public String getSurvey_file_no() {
        return survey_file_no;
    }

    public void setSurvey_file_no(String survey_file_no) {
        this.survey_file_no = survey_file_no;
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public String getSurvey_type() {
        return survey_type;
    }

    public void setSurvey_type(String survey_type) {
        this.survey_type = survey_type;
    }

    public int getSwitching_point_detail_id() {
        return switching_point_detail_id;
    }

    public void setSwitching_point_detail_id(int switching_point_detail_id) {
        this.switching_point_detail_id = switching_point_detail_id;
    }

    public String getSwitching_point_name() {
        return switching_point_name;
    }

    public void setSwitching_point_name(String switching_point_name) {
        this.switching_point_name = switching_point_name;
    }

    public String getSwitching_point_no() {
        return switching_point_no;
    }

    public void setSwitching_point_no(String switching_point_no) {
        this.switching_point_no = switching_point_no;
    }

    public int getSwitching_rev_no() {
        return switching_rev_no;
    }

    public void setSwitching_rev_no(int switching_rev_no) {
        this.switching_rev_no = switching_rev_no;
    }
}
