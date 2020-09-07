/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.general.tableClasses;

/**
 *
 * @author pooja
 */
public class History {

    private String ip_address;
    private int junction_id;
    private int program_version_no;
    private int fileNo;
    private int port;
    private String login_timestamp_date;
    private String login_timestamp_time;
    private boolean status;
    private String logout_timestamp_date;
    private String logout_timestamp_time;
    private String junction_last_time_set;
    private String application_last_time_set;
    private String time_synchronization_status;
    private String error_state;

    public String getError_state() {
        return error_state;
    }

    public void setError_state(String error_state) {
        this.error_state = error_state;
    }


    //these fields is for displaying logged in junction details

    private String junction_name;
    private String city_name;

    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    public int getProgram_version_no() {
        return program_version_no;
    }

    public void setProgram_version_no(int program_version_no) {
        this.program_version_no = program_version_no;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getJunction_name() {
        return junction_name;
    }

    public void setJunction_name(String junction_name) {
        this.junction_name = junction_name;
    }

    public int getJunction_id() {
        return junction_id;
    }

    public void setJunction_id(int junction_id) {
        this.junction_id = junction_id;
    }

    public String getLogout_timestamp_date() {
        return logout_timestamp_date;
    }

    public void setLogout_timestamp_date(String logout_timestamp_date) {
        this.logout_timestamp_date = logout_timestamp_date;
    }

    public String getLogout_timestamp_time() {
        return logout_timestamp_time;
    }

    public void setLogout_timestamp_time(String logout_timestamp_time) {
        this.logout_timestamp_time = logout_timestamp_time;
    }

    public String getLogin_timestamp_date() {
        return login_timestamp_date;
    }

    public void setLogin_timestamp_date(String login_timestamp_date) {
        this.login_timestamp_date = login_timestamp_date;
    }

    public String getLogin_timestamp_time() {
        return login_timestamp_time;
    }

    public void setLogin_timestamp_time(String login_timestamp_time) {
        this.login_timestamp_time = login_timestamp_time;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getJunction_last_time_set() {
        return junction_last_time_set;
    }

    public void setJunction_last_time_set(String junction_last_time_set) {
        this.junction_last_time_set = junction_last_time_set;
    }

    public String getTime_synchronization_status() {
        return time_synchronization_status;
    }

    public void setTime_synchronization_status(String time_synchronization_status) {
        this.time_synchronization_status = time_synchronization_status;
    }

    public String getApplication_last_time_set() {
        return application_last_time_set;
    }

    public void setApplication_last_time_set(String application_last_time_set) {
        this.application_last_time_set = application_last_time_set;
    }
}
