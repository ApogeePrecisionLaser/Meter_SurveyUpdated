/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.tableClasses;

import com.survey.energyMeter.tcpServer.ClientResponder;

/**
 *
 * @author jpss
 */
public class JunctionBean {
    private int junction_id;
    private int program_version_no;
    private int switching_point_detail_id;
    private int switching_rev_no;
    private String controller_model;
    private String mobile_no;
    private String sim_no;
    private String imei_no;
    private String panel_file_no;
    private String panel_transferred_status;
    private String energy_meter_no;
    private double sanctioned_load;
    private double connected_load;
    private double phase1_healthy_voltage;
    private double phase2_healthy_voltage;
    private double phase3_healthy_voltage;
    private double phase1_healthy_current;
    private double phase2_healthy_current;
    private double phase3_healthy_current;
    private String remark;
    private String switching_point_name;
    private String area;
    private String road;
    private String city;
    private String ivrs_no;
    private String ward;
    private String active;
    private String junction_name;
    private int phase;

  // these fields r NOT the part of the junction table it only belongs to the Registration Status.
    private boolean registration_status;
    private String lastVisitedTime;
    private String lastDataInstertedTime;

   // ClientResponder object is created corresponding to every Modem.
    private ClientResponder clientResponder;
    private boolean responseFromModemForRefresh;
    private boolean responseFromModemForClearance;

    public String getJunction_name() {
        return junction_name;
    }

    public void setJunction_name(String junction_name) {
        this.junction_name = junction_name;
    }

    

    public String getLastDataInstertedTime() {
        return lastDataInstertedTime;
    }

    public void setLastDataInstertedTime(String lastDataInstertedTime) {
        this.lastDataInstertedTime = lastDataInstertedTime;
    }

    public ClientResponder getClientResponder() {
        return clientResponder;
    }

    public void setClientResponder(ClientResponder clientResponder) {
        this.clientResponder = clientResponder;
    }

    public String getLastVisitedTime() {
        return lastVisitedTime;
    }

    public void setLastVisitedTime(String lastVisitedTime) {
        this.lastVisitedTime = lastVisitedTime;
    }

    public boolean isRegistration_status() {
        return registration_status;
    }

    public void setRegistration_status(boolean registration_status) {
        this.registration_status = registration_status;
    }

    public boolean isResponseFromModemForClearance() {
        return responseFromModemForClearance;
    }

    public void setResponseFromModemForClearance(boolean responseFromModemForClearance) {
        this.responseFromModemForClearance = responseFromModemForClearance;
    }

    public boolean isResponseFromModemForRefresh() {
        return responseFromModemForRefresh;
    }

    public void setResponseFromModemForRefresh(boolean responseFromModemForRefresh) {
        this.responseFromModemForRefresh = responseFromModemForRefresh;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setConnected_load(double connected_load) {
        this.connected_load = connected_load;
    }

    public void setController_model(String controller_model) {
        this.controller_model = controller_model;
    }

    public void setEnergy_meter_no(String energy_meter_no) {
        this.energy_meter_no = energy_meter_no;
    }

    public void setImei_no(String imei_no) {
        this.imei_no = imei_no;
    }

    public void setIvrs_no(String ivrs_no) {
        this.ivrs_no = ivrs_no;
    }

    public void setJunction_id(int junction_id) {
        this.junction_id = junction_id;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public void setPanel_file_no(String panel_file_no) {
        this.panel_file_no = panel_file_no;
    }

    public void setPanel_transferred_status(String panel_transferred_status) {
        this.panel_transferred_status = panel_transferred_status;
    }

    public void setPhase1_healthy_current(double phase1_healthy_current) {
        this.phase1_healthy_current = phase1_healthy_current;
    }

    public void setPhase1_healthy_voltage(double phase1_healthy_voltage) {
        this.phase1_healthy_voltage = phase1_healthy_voltage;
    }

    public void setPhase2_healthy_current(double phase2_healthy_current) {
        this.phase2_healthy_current = phase2_healthy_current;
    }

    public void setPhase2_healthy_voltage(double phase2_healthy_voltage) {
        this.phase2_healthy_voltage = phase2_healthy_voltage;
    }

    public void setPhase3_healthy_current(double phase3_healthy_current) {
        this.phase3_healthy_current = phase3_healthy_current;
    }

    public void setPhase3_healthy_voltage(double phase3_healthy_voltage) {
        this.phase3_healthy_voltage = phase3_healthy_voltage;
    }

    public void setProgram_version_no(int program_version_no) {
        this.program_version_no = program_version_no;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public void setSanctioned_load(double sanctioned_load) {
        this.sanctioned_load = sanctioned_load;
    }

    public void setSim_no(String sim_no) {
        this.sim_no = sim_no;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setSwitching_point_detail_id(int switching_point_detail_id) {
        this.switching_point_detail_id = switching_point_detail_id;
    }

    public void setSwitching_point_name(String switching_point_name) {
        this.switching_point_name = switching_point_name;
    }

    public void setSwitching_rev_no(int switching_rev_no) {
        this.switching_rev_no = switching_rev_no;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }



    public String getActive() {
        return active;
    }

    public String getCity() {
        return city;
    }

    public double getConnected_load() {
        return connected_load;
    }

    public String getController_model() {
        return controller_model;
    }

    public String getEnergy_meter_no() {
        return energy_meter_no;
    }

    public String getImei_no() {
        return imei_no;
    }

    public String getIvrs_no() {
        return ivrs_no;
    }

    public int getJunction_id() {
        return junction_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getPanel_file_no() {
        return panel_file_no;
    }

    public String getPanel_transferred_status() {
        return panel_transferred_status;
    }

    public double getPhase1_healthy_current() {
        return phase1_healthy_current;
    }

    public double getPhase1_healthy_voltage() {
        return phase1_healthy_voltage;
    }

    public double getPhase2_healthy_current() {
        return phase2_healthy_current;
    }

    public double getPhase2_healthy_voltage() {
        return phase2_healthy_voltage;
    }

    public double getPhase3_healthy_current() {
        return phase3_healthy_current;
    }

    public double getPhase3_healthy_voltage() {
        return phase3_healthy_voltage;
    }

    public int getProgram_version_no() {
        return program_version_no;
    }

    public String getRemark() {
        return remark;
    }

    public String getRoad() {
        return road;
    }

    public double getSanctioned_load() {
        return sanctioned_load;
    }

    public String getSim_no() {
        return sim_no;
    }

    public String getArea() {
        return area;
    }

    public int getSwitching_point_detail_id() {
        return switching_point_detail_id;
    }

    public String getSwitching_point_name() {
        return switching_point_name;
    }

    public int getSwitching_rev_no() {
        return switching_rev_no;
    }

    public String getWard() {
        return ward;
    }

    public int getPhase() {
        return phase;
    }


}
