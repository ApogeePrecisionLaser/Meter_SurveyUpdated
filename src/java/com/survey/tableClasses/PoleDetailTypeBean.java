/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.tableClasses;

/**
 *
 * @author JPSS
 */
public class PoleDetailTypeBean {

    private int pole_id;
    private int pole_type_id;
    private int mounting_type_id;
    private int pole_rev_no;
    private String pole_span;
    private String pole_height;
    private String mounting_type;
    private String mounting_height;
    private String pole_type;
    private String created_by;
    private String created_date;
    private String active;
    private String remark;
    private String is_switch_point;
    private String feeder_name;
    private int light_type_id;
    private String pole_no;
    private String gps_code;
    private String max_avg_lux_level;
    private String min_avg_lux_level;
    private String avg_lux_level;
    private String standard_lux_level;
    private String is_working;
    private String source_wattage;
    private String[] sources_wattage_M;
    private String[] quantity;
    private String[] mapp_id;
    private String[] map_light_type_id;
    private int[] light_type_id_M;
    private int addRowCount;
    private int newRowCount;
    private String switching_point_name;
    private int switching_load;
    private double latitude;
    private double longitude;
    private int switch_point_detail_id;
    private String map_id;
    private int area_id;
    private int road_id;
    private int traffic_type_id;
    private int road_rev_no;
    private String area_name;
    private String road_name;
    private String traffic_type;
    private String road_category;
    private String road_use;
    private String city;
    private String ward_no;
    private int switching_rev_no;
    private String pole_no_client;
    private String[] pole_id_showData;
    private String[] client_pole_no_id;  // for check uncheck
    private String[] client_pole_no_edit;

    public String[] getClient_pole_no_edit() {
        return client_pole_no_edit;
    }

    public void setClient_pole_no_edit(String[] client_pole_no_edit) {
        this.client_pole_no_edit = client_pole_no_edit;
    }

    public String[] getClient_pole_no_id() {
        return client_pole_no_id;
    }

    public void setClient_pole_no_id(String[] client_pole_no_id) {
        this.client_pole_no_id = client_pole_no_id;
    }

    public String[] getPole_id_showData() {
        return pole_id_showData;
    }

    public void setPole_id_showData(String[] pole_id_showData) {
        this.pole_id_showData = pole_id_showData;
    }
   
    public String getPole_no_client() {
        return pole_no_client;
    }

    public void setPole_no_client(String pole_no_client) {
        this.pole_no_client = pole_no_client;
    }
    
    public int getSwitching_rev_no() {
        return switching_rev_no;
    }

    public void setSwitching_rev_no(int switching_rev_no) {
        this.switching_rev_no = switching_rev_no;
    }

    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoad_category() {
        return road_category;
    }

    public void setRoad_category(String road_category) {
        this.road_category = road_category;
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

    public String getTraffic_type() {
        return traffic_type;
    }

    public void setTraffic_type(String traffic_type) {
        this.traffic_type = traffic_type;
    }

    public int getRoad_rev_no() {
        return road_rev_no;
    }

    public void setRoad_rev_no(int road_rev_no) {
        this.road_rev_no = road_rev_no;
    }

    public int getTraffic_type_id() {
        return traffic_type_id;
    }

    public void setTraffic_type_id(int traffic_type_id) {
        this.traffic_type_id = traffic_type_id;
    }

    public int getRoad_id() {
        return road_id;
    }

    public void setRoad_id(int road_id) {
        this.road_id = road_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public int getPole_rev_no() {
        return pole_rev_no;
    }

    public void setPole_rev_no(int pole_rev_no) {
        this.pole_rev_no = pole_rev_no;
    }

    public int[] getLight_type_id_M() {
        return light_type_id_M;
    }

    public void setLight_type_id_M(int[] light_type_id_M) {
        this.light_type_id_M = light_type_id_M;
    }

    public String[] getQuantity() {
        return quantity;
    }

    public void setQuantity(String[] quantity) {
        this.quantity = quantity;
    }

    public String[] getSources_wattage_M() {
        return sources_wattage_M;
    }

    public void setSources_wattage_M(String[] sources_wattage_M) {
        this.sources_wattage_M = sources_wattage_M;
    }

    public String getAvg_lux_level() {
        return avg_lux_level;
    }

    public void setAvg_lux_level(String avg_lux_level) {
        this.avg_lux_level = avg_lux_level;
    }

    public String getGps_code() {
        return gps_code;
    }

    public void setGps_code(String gps_code) {
        this.gps_code = gps_code;
    }

    public String getIs_working() {
        return is_working;
    }

    public void setIs_working(String is_working) {
        this.is_working = is_working;
    }

    public int getLight_type_id() {
        return light_type_id;
    }

    public void setLight_type_id(int light_type_id) {
        this.light_type_id = light_type_id;
    }

    public String getMax_avg_lux_level() {
        return max_avg_lux_level;
    }

    public void setMax_avg_lux_level(String max_avg_lux_level) {
        this.max_avg_lux_level = max_avg_lux_level;
    }

    public String getMin_avg_lux_level() {
        return min_avg_lux_level;
    }

    public void setMin_avg_lux_level(String min_avg_lux_level) {
        this.min_avg_lux_level = min_avg_lux_level;
    }

    public String getPole_no() {
        return pole_no;
    }

    public void setPole_no(String pole_no) {
        this.pole_no = pole_no;
    }

    public String getSource_wattage() {
        return source_wattage;
    }

    public void setSource_wattage(String source_wattage) {
        this.source_wattage = source_wattage;
    }

    public String getStandard_lux_level() {
        return standard_lux_level;
    }

    public void setStandard_lux_level(String standard_lux_level) {
        this.standard_lux_level = standard_lux_level;
    }

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

    public int getMounting_type_id() {
        return mounting_type_id;
    }

    public void setMounting_type_id(int mounting_type_id) {
        this.mounting_type_id = mounting_type_id;
    }

    public String getPole_height() {
        return pole_height;
    }

    public void setPole_height(String pole_height) {
        this.pole_height = pole_height;
    }

    public int getPole_id() {
        return pole_id;
    }

    public void setPole_id(int pole_id) {
        this.pole_id = pole_id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSwitching_point_name() {
        return switching_point_name;
    }

    public void setSwitching_point_name(String switching_point_name) {
        this.switching_point_name = switching_point_name;
    }

    public int getSwitching_load() {
        return switching_load;
    }

    public void setSwitching_load(int switching_load) {
        this.switching_load = switching_load;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the switch_point_detail_id
     */
    public int getSwitch_point_detail_id() {
        return switch_point_detail_id;
    }

    /**
     * @param switch_point_detail_id the switch_point_detail_id to set
     */
    public void setSwitch_point_detail_id(int switch_point_detail_id) {
        this.switch_point_detail_id = switch_point_detail_id;
    }

    /**
     * @return the feeder_name
     */
    public String getFeeder_name() {
        return feeder_name;
    }

    /**
     * @param feeder_name the feeder_name to set
     */
    public void setFeeder_name(String feeder_name) {
        this.feeder_name = feeder_name;
    }

    /**
     * @return the is_switch_point
     */
    public String getIs_switch_point() {
        return is_switch_point;
    }

    /**
     * @param is_switch_point the is_switch_point to set
     */
    public void setIs_switch_point(String is_switch_point) {
        this.is_switch_point = is_switch_point;
    }

    /**
     * @return the addRowCount
     */
    public int getAddRowCount() {
        return addRowCount;
    }

    /**
     * @param addRowCount the addRowCount to set
     */
    public void setAddRowCount(int addRowCount) {
        this.addRowCount = addRowCount;
    }

    /**
     * @return the newRowCount
     */
    public int getNewRowCount() {
        return newRowCount;
    }

    /**
     * @param newRowCount the newRowCount to set
     */
    public void setNewRowCount(int newRowCount) {
        this.newRowCount = newRowCount;
    }

    /**
     * @return the mapp_id
     */
    public String[] getMapp_id() {
        return mapp_id;
    }

    /**
     * @param mapp_id the mapp_id to set
     */
    public void setMapp_id(String[] mapp_id) {
        this.mapp_id = mapp_id;
    }

    /**
     * @return the map_light_type_id
     */
    public String[] getMap_light_type_id() {
        return map_light_type_id;
    }

    /**
     * @param map_light_type_id the map_light_type_id to set
     */
    public void setMap_light_type_id(String[] map_light_type_id) {
        this.map_light_type_id = map_light_type_id;
    }
}
