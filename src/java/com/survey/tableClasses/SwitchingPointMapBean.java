/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.tableClasses;

/**
 *
 * @author Administrator
 */
public class SwitchingPointMapBean {
    private int switching_point_map_id;
    private int switching_point_detail_id;
    private int meter_id;
    private String ivrs_no;
    private String ivrs_no_meter;
    private String service_conn_no;
    private String sevice_conn_no_meter;
    private String switching_point_name;
    private String switching_point_name_meter;
    private String feeder;
    private String feeder_meter;
    private String address;
    private String address_meter;
    private String zone;
    private String zone_meter;
    private Double measured_load;
    private Double sanctioned_load;

    public Double getMeasured_load() {
        return measured_load;
    }

    public void setMeasured_load(Double measured_load) {
        this.measured_load = measured_load;
    }

    public Double getSanctioned_load() {
        return sanctioned_load;
    }

    public void setSanctioned_load(Double sanctioned_load) {
        this.sanctioned_load = sanctioned_load;
    }
    
    public int getSwitching_point_map_id() {
        return switching_point_map_id;
    }

    public void setSwitching_point_map_id(int switching_point_map_id) {
        this.switching_point_map_id = switching_point_map_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_meter() {
        return address_meter;
    }

    public void setAddress_meter(String address_meter) {
        this.address_meter = address_meter;
    }

    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public String getFeeder_meter() {
        return feeder_meter;
    }

    public void setFeeder_meter(String feeder_meter) {
        this.feeder_meter = feeder_meter;
    }

    public String getIvrs_no() {
        return ivrs_no;
    }

    public void setIvrs_no(String ivrs_no) {
        this.ivrs_no = ivrs_no;
    }

    public String getIvrs_no_meter() {
        return ivrs_no_meter;
    }

    public void setIvrs_no_meter(String ivrs_no_meter) {
        this.ivrs_no_meter = ivrs_no_meter;
    }

    public int getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(int meter_id) {
        this.meter_id = meter_id;
    }

    public String getService_conn_no() {
        return service_conn_no;
    }

    public void setService_conn_no(String service_conn_no) {
        this.service_conn_no = service_conn_no;
    }

    public String getSevice_conn_no_meter() {
        return sevice_conn_no_meter;
    }

    public void setSevice_conn_no_meter(String sevice_conn_no_meter) {
        this.sevice_conn_no_meter = sevice_conn_no_meter;
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

    public String getSwitching_point_name_meter() {
        return switching_point_name_meter;
    }

    public void setSwitching_point_name_meter(String switching_point_name_meter) {
        this.switching_point_name_meter = switching_point_name_meter;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getZone_meter() {
        return zone_meter;
    }

    public void setZone_meter(String zone_meter) {
        this.zone_meter = zone_meter;
    }



  /*  private int switching_point_map_id;
    private int switching_point_id;
    private int switching_point_detail_id;
    private String switching_pt_meter;
    private String switching_pt_detail;

    private String auto_switching_point;
    private String address1;
    private String address2;
    private String address3;
    private String description;
    private String active;
    private String meter_service_num;
    private int meter_id;
    private String feeder_name;
    private int feeder_id;
    private String zone;
    private String switching_point_type;
    private String switching_point_code;
    private String switching_point_type_old;
    private String division_name;
    private String meter_name;
    private String sanctioned_load_kw;



    private int survey_rev_no;
    private int light_type_id;
    private int pole_type_id;
    private int area_id;
    private int road_id;
    private int traffic_type_id;
    private int control_mechanism_id;
    private int fuse_id;
    private int contacter_id;
    private int mccb_id;
    private int timer_id;
    private int main_survey_id;
    private String control_mechanism_type;
    private String survey_date;
    private String pole_no;
    private String gps_code;
    private String is_working;
    private String max_avg_lux_level;
    private String min_avg_lux_level;
    private String avg_lux_level;
    private String standard_avg_lux_level;
    private String created_date;
    private String created_by;
    private String remark;
    private String pole_span;
    private String pole_height;
    private String mounting_height;
    private String area_name;
    private String road_name;
    private String traffic_type;
    private String road_category;
    private String road_use;
    private String width;
    private String pole_type;
    private String mounting_type;
    private String source_type;
    private String wattage_value;
    private String ward_no;
    private String pole_no_s;
    private String gps_code_s;
    private String meter_no_s;
    private String phase;
    private String source_wattage;
    private String[] sources_wattage_M;
    private String[] quantity;
    private int[] light_type_id_M;
    private String switching_point_name;
    private String fuse;
    private String contacter;
    private String mccb;
    private String timer;
    private String fuse_type;
    private String contacter_type;
    private String mccb_type;
    private String timer_type;
    private String fuse_quantity;
    private String contacter_quantity;
    private String mccb_quantity;
    private String timer_quantity;
    private String feeder;
    private String isworking;
    private int no_of_poles;
    private int switching_load;




    public int getSwitching_point_detail_id() {
        return switching_point_detail_id;
    }

    public void setSwitching_point_detail_id(int switching_point_detail_id) {
        this.switching_point_detail_id = switching_point_detail_id;
    }

    public int getSwitching_point_id() {
        return switching_point_id;
    }

    public void setSwitching_point_id(int switching_point_id) {
        this.switching_point_id = switching_point_id;
    }

    public int getSwitching_point_map_id() {
        return switching_point_map_id;
    }

    public void setSwitching_point_map_id(int switching_point_map_id) {
        this.switching_point_map_id = switching_point_map_id;
    }

    public String getSwitching_pt_detail() {
        return switching_pt_detail;
    }

    public void setSwitching_pt_detail(String switching_pt_detail) {
        this.switching_pt_detail = switching_pt_detail;
    }

    public String getSwitching_pt_meter() {
        return switching_pt_meter;
    }

    public void setSwitching_pt_meter(String switching_pt_meter) {
        this.switching_pt_meter = switching_pt_meter;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAuto_switching_point() {
        return auto_switching_point;
    }

    public void setAuto_switching_point(String auto_switching_point) {
        this.auto_switching_point = auto_switching_point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDivision_name() {
        return division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    public int getFeeder_id() {
        return feeder_id;
    }

    public void setFeeder_id(int feeder_id) {
        this.feeder_id = feeder_id;
    }

    public String getFeeder_name() {
        return feeder_name;
    }

    public void setFeeder_name(String feeder_name) {
        this.feeder_name = feeder_name;
    }

    public int getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(int meter_id) {
        this.meter_id = meter_id;
    }

    public String getMeter_name() {
        return meter_name;
    }

    public void setMeter_name(String meter_name) {
        this.meter_name = meter_name;
    }

    public String getMeter_service_num() {
        return meter_service_num;
    }

    public void setMeter_service_num(String meter_service_num) {
        this.meter_service_num = meter_service_num;
    }

    public String getSanctioned_load_kw() {
        return sanctioned_load_kw;
    }

    public void setSanctioned_load_kw(String sanctioned_load_kw) {
        this.sanctioned_load_kw = sanctioned_load_kw;
    }

    public String getSwitching_point_code() {
        return switching_point_code;
    }

    public void setSwitching_point_code(String switching_point_code) {
        this.switching_point_code = switching_point_code;
    }

    public String getSwitching_point_type() {
        return switching_point_type;
    }

    public void setSwitching_point_type(String switching_point_type) {
        this.switching_point_type = switching_point_type;
    }

    public String getSwitching_point_type_old() {
        return switching_point_type_old;
    }

    public void setSwitching_point_type_old(String switching_point_type_old) {
        this.switching_point_type_old = switching_point_type_old;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAvg_lux_level() {
        return avg_lux_level;
    }

    public void setAvg_lux_level(String avg_lux_level) {
        this.avg_lux_level = avg_lux_level;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public int getContacter_id() {
        return contacter_id;
    }

    public void setContacter_id(int contacter_id) {
        this.contacter_id = contacter_id;
    }

    public String getContacter_quantity() {
        return contacter_quantity;
    }

    public void setContacter_quantity(String contacter_quantity) {
        this.contacter_quantity = contacter_quantity;
    }

    public String getContacter_type() {
        return contacter_type;
    }

    public void setContacter_type(String contacter_type) {
        this.contacter_type = contacter_type;
    }

    public int getControl_mechanism_id() {
        return control_mechanism_id;
    }

    public void setControl_mechanism_id(int control_mechanism_id) {
        this.control_mechanism_id = control_mechanism_id;
    }

    public String getControl_mechanism_type() {
        return control_mechanism_type;
    }

    public void setControl_mechanism_type(String control_mechanism_type) {
        this.control_mechanism_type = control_mechanism_type;
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

    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public String getFuse() {
        return fuse;
    }

    public void setFuse(String fuse) {
        this.fuse = fuse;
    }

    public int getFuse_id() {
        return fuse_id;
    }

    public void setFuse_id(int fuse_id) {
        this.fuse_id = fuse_id;
    }

    public String getFuse_quantity() {
        return fuse_quantity;
    }

    public void setFuse_quantity(String fuse_quantity) {
        this.fuse_quantity = fuse_quantity;
    }

    public String getFuse_type() {
        return fuse_type;
    }

    public void setFuse_type(String fuse_type) {
        this.fuse_type = fuse_type;
    }

    public String getGps_code() {
        return gps_code;
    }

    public void setGps_code(String gps_code) {
        this.gps_code = gps_code;
    }

    public String getGps_code_s() {
        return gps_code_s;
    }

    public void setGps_code_s(String gps_code_s) {
        this.gps_code_s = gps_code_s;
    }

    public String getIs_working() {
        return is_working;
    }

    public void setIs_working(String is_working) {
        this.is_working = is_working;
    }

    public String getIsworking() {
        return isworking;
    }

    public void setIsworking(String isworking) {
        this.isworking = isworking;
    }

    public int getLight_type_id() {
        return light_type_id;
    }

    public void setLight_type_id(int light_type_id) {
        this.light_type_id = light_type_id;
    }

    public int[] getLight_type_id_M() {
        return light_type_id_M;
    }

    public void setLight_type_id_M(int[] light_type_id_M) {
        this.light_type_id_M = light_type_id_M;
    }

    public int getMain_survey_id() {
        return main_survey_id;
    }

    public void setMain_survey_id(int main_survey_id) {
        this.main_survey_id = main_survey_id;
    }

    public String getMax_avg_lux_level() {
        return max_avg_lux_level;
    }

    public void setMax_avg_lux_level(String max_avg_lux_level) {
        this.max_avg_lux_level = max_avg_lux_level;
    }

    public String getMccb() {
        return mccb;
    }

    public void setMccb(String mccb) {
        this.mccb = mccb;
    }

    public int getMccb_id() {
        return mccb_id;
    }

    public void setMccb_id(int mccb_id) {
        this.mccb_id = mccb_id;
    }

    public String getMccb_quantity() {
        return mccb_quantity;
    }

    public void setMccb_quantity(String mccb_quantity) {
        this.mccb_quantity = mccb_quantity;
    }

    public String getMccb_type() {
        return mccb_type;
    }

    public void setMccb_type(String mccb_type) {
        this.mccb_type = mccb_type;
    }

    public String getMeter_no_s() {
        return meter_no_s;
    }

    public void setMeter_no_s(String meter_no_s) {
        this.meter_no_s = meter_no_s;
    }

    public String getMin_avg_lux_level() {
        return min_avg_lux_level;
    }

    public void setMin_avg_lux_level(String min_avg_lux_level) {
        this.min_avg_lux_level = min_avg_lux_level;
    }

    public String getMounting_height() {
        return mounting_height;
    }

    public void setMounting_height(String mounting_height) {
        this.mounting_height = mounting_height;
    }

    public String getMounting_type() {
        return mounting_type;
    }

    public void setMounting_type(String mounting_type) {
        this.mounting_type = mounting_type;
    }

    public int getNo_of_poles() {
        return no_of_poles;
    }

    public void setNo_of_poles(int no_of_poles) {
        this.no_of_poles = no_of_poles;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getPole_height() {
        return pole_height;
    }

    public void setPole_height(String pole_height) {
        this.pole_height = pole_height;
    }

    public String getPole_no() {
        return pole_no;
    }

    public void setPole_no(String pole_no) {
        this.pole_no = pole_no;
    }

    public String getPole_no_s() {
        return pole_no_s;
    }

    public void setPole_no_s(String pole_no_s) {
        this.pole_no_s = pole_no_s;
    }

    public String getPole_span() {
        return pole_span;
    }

    public void setPole_span(String pole_span) {
        this.pole_span = pole_span;
    }

    public String getPole_type() {
        return pole_type;
    }

    public void setPole_type(String pole_type) {
        this.pole_type = pole_type;
    }

    public int getPole_type_id() {
        return pole_type_id;
    }

    public void setPole_type_id(int pole_type_id) {
        this.pole_type_id = pole_type_id;
    }

    public String[] getQuantity() {
        return quantity;
    }

    public void setQuantity(String[] quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoad_category() {
        return road_category;
    }

    public void setRoad_category(String road_category) {
        this.road_category = road_category;
    }

    public int getRoad_id() {
        return road_id;
    }

    public void setRoad_id(int road_id) {
        this.road_id = road_id;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public String getRoad_use() {
        return road_use;
    }

    public void setRoad_use(String road_use) {
        this.road_use = road_use;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getSource_wattage() {
        return source_wattage;
    }

    public void setSource_wattage(String source_wattage) {
        this.source_wattage = source_wattage;
    }

    public String[] getSources_wattage_M() {
        return sources_wattage_M;
    }

    public void setSources_wattage_M(String[] sources_wattage_M) {
        this.sources_wattage_M = sources_wattage_M;
    }

    public String getStandard_avg_lux_level() {
        return standard_avg_lux_level;
    }

    public void setStandard_avg_lux_level(String standard_avg_lux_level) {
        this.standard_avg_lux_level = standard_avg_lux_level;
    }

    public String getSurvey_date() {
        return survey_date;
    }

    public void setSurvey_date(String survey_date) {
        this.survey_date = survey_date;
    }

    public int getSurvey_rev_no() {
        return survey_rev_no;
    }

    public void setSurvey_rev_no(int survey_rev_no) {
        this.survey_rev_no = survey_rev_no;
    }

    public int getSwitching_load() {
        return switching_load;
    }

    public void setSwitching_load(int switching_load) {
        this.switching_load = switching_load;
    }

    public String getSwitching_point_name() {
        return switching_point_name;
    }

    public void setSwitching_point_name(String switching_point_name) {
        this.switching_point_name = switching_point_name;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getTimer_id() {
        return timer_id;
    }

    public void setTimer_id(int timer_id) {
        this.timer_id = timer_id;
    }

    public String getTimer_quantity() {
        return timer_quantity;
    }

    public void setTimer_quantity(String timer_quantity) {
        this.timer_quantity = timer_quantity;
    }

    public String getTimer_type() {
        return timer_type;
    }

    public void setTimer_type(String timer_type) {
        this.timer_type = timer_type;
    }

    public String getTraffic_type() {
        return traffic_type;
    }

    public void setTraffic_type(String traffic_type) {
        this.traffic_type = traffic_type;
    }

    public int getTraffic_type_id() {
        return traffic_type_id;
    }

    public void setTraffic_type_id(int traffic_type_id) {
        this.traffic_type_id = traffic_type_id;
    }

    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }

    public String getWattage_value() {
        return wattage_value;
    }

    public void setWattage_value(String wattage_value) {
        this.wattage_value = wattage_value;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

*/


}
