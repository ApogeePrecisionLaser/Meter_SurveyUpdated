/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.tableClasses;

/**
 *
 * @author Pooja
 */
public class Readings {

    int meter_readings_id;
    int junction_id;
    String junction_name;
    int program_version_no;
    long voltage1;
    long voltage2;
    long voltage3;
    long current1;
    long current2;
    long current3;
    long power_factor;
    long consumed_unit;
    long connected_load;
    int voltage_unit_id;
    String voltage_unit;
    String ivrs_no;

    int current_unit_id;
    String current_unit;
    int created_by;
    String remark;
    String reading_date;
    String reading_time;
    String timestamp;
    private int on_time_hr;
    private int on_time_min;
    private int off_time_hr;
    private int off_time_min;

    public String getIvrs_no() {
        return ivrs_no;
    }

    public void setIvrs_no(String ivrs_no) {
        this.ivrs_no = ivrs_no;
    }

    

    public int getOff_time_hr() {
        return off_time_hr;
    }

    public int getOff_time_min() {
        return off_time_min;
    }

    public int getOn_time_hr() {
        return on_time_hr;
    }

    public int getOn_time_min() {
        return on_time_min;
    }

    public void setOff_time_hr(int off_time_hr) {
        this.off_time_hr = off_time_hr;
    }

    public void setOff_time_min(int off_time_min) {
        this.off_time_min = off_time_min;
    }

    public void setOn_time_hr(int on_time_hr) {
        this.on_time_hr = on_time_hr;
    }

    public void setOn_time_min(int on_time_min) {
        this.on_time_min = on_time_min;
    }

    public long getConnected_load() {
        return connected_load;
    }

    public void setConnected_load(long connected_load) {
        this.connected_load = connected_load;
    }

    public String getReading_time() {
        return reading_time;
    }

    public void setReading_time(String reading_time) {
        this.reading_time = reading_time;
    }
    
    public long getConsumed_unit() {
        return consumed_unit;
    }

    public void setConsumed_unit(long consumed_unit) {
        this.consumed_unit = consumed_unit;
    }

    public String getCurrent_unit() {
        return current_unit;
    }

    public void setCurrent_unit(String current_unit) {
        this.current_unit = current_unit;
    }

    public String getJunction_name() {
        return junction_name;
    }

    public void setJunction_name(String junction_name) {
        this.junction_name = junction_name;
    }

    public String getVoltage_unit() {
        return voltage_unit;
    }

    public void setVoltage_unit(String voltage_unit) {
        this.voltage_unit = voltage_unit;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public long getCurrent1() {
        return current1;
    }

    public void setCurrent1(long current1) {
        this.current1 = current1;
    }

    public long getCurrent2() {
        return current2;
    }

    public void setCurrent2(long current2) {
        this.current2 = current2;
    }

    public long getCurrent3() {
        return current3;
    }

    public void setCurrent3(long current3) {
        this.current3 = current3;
    }

    public int getCurrent_unit_id() {
        return current_unit_id;
    }

    public void setCurrent_unit_id(int current_unit_id) {
        this.current_unit_id = current_unit_id;
    }

    public int getJunction_id() {
        return junction_id;
    }

    public void setJunction_id(int junction_id) {
        this.junction_id = junction_id;
    }

    public int getMeter_readings_id() {
        return meter_readings_id;
    }

    public void setMeter_readings_id(int meter_readings_id) {
        this.meter_readings_id = meter_readings_id;
    }

    public long getPower_factor() {
        return power_factor;
    }

    public void setPower_factor(long power_factor) {
        this.power_factor = power_factor;
    }

    public int getProgram_version_no() {
        return program_version_no;
    }

    public void setProgram_version_no(int program_version_no) {
        this.program_version_no = program_version_no;
    }

    public String getReading_date() {
        return reading_date;
    }

    public void setReading_date(String reading_date) {
        this.reading_date = reading_date;
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

    public long getVoltage1() {
        return voltage1;
    }

    public void setVoltage1(long voltage1) {
        this.voltage1 = voltage1;
    }

    public long getVoltage2() {
        return voltage2;
    }

    public void setVoltage2(long voltage2) {
        this.voltage2 = voltage2;
    }

    public long getVoltage3() {
        return voltage3;
    }

    public void setVoltage3(long voltage3) {
        this.voltage3 = voltage3;
    }

    public int getVoltage_unit_id() {
        return voltage_unit_id;
    }

    public void setVoltage_unit_id(int voltage_unit_id) {
        this.voltage_unit_id = voltage_unit_id;
    }
}
