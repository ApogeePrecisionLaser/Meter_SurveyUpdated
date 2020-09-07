/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.model;

import com.survey.energyMeter.tableClasses.EnergyMeterStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pooja
 */
public class EnergyMeterStatusModel {
    
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("EnergyMeterStatusModel setConnection() Error: " + e);
        }
    }

     public EnergyMeterStatus showData(String searchJunction, String searchReadingDateFrom, String searchReadingDateTo) {
         EnergyMeterStatus energyMeterStatus = new EnergyMeterStatus();
        try {
            String query = "SELECT ivrs_no,spd.switching_point_detail_id, spd.switching_rev_no, switching_point_name,j.junction_id, j.program_version_no,"
                    + " sanctioned_load, phase1_healthy_voltage, phase2_healthy_voltage, phase3_healthy_voltage, ph,no_of_poles,type_of_premises_id,"
                    + " phase1_healthy_current, phase2_healthy_current, phase3_healthy_current, voltage1, voltage2, voltage3,"
                    + " current1, current2, current3, power_factor, consumed_unit,j.connected_load,  v.unit AS voltage_unit, c.unit AS current_unit,"
                    + " on_time_hr, on_time_min, off_time_hr, off_time_min, "
                    + " DATE_FORMAT(mr.reading_date, '%d-%m-%Y') AS reading_date, mr.reading_time, oa.health_status AS over_all_status, "
                    + " p1.health_status AS phase1_status, p2.health_status AS phase2_status, p3.health_status AS phase3_status, DATE_FORMAT(hsm.reading_date, '%d-%m-%Y') AS health_status_date,"
                    + " hsm.reading_time AS health_status_time, p1vc.health_status AS phase1_vc_status, p2vc.health_status AS phase2_vc_status, p3vc.health_status AS phase3_vc_status "

                    + " FROM junction AS j, switching_point_detail AS spd, meter_readings AS mr , unit AS v, unit AS c, health_status_map AS hsm, health_status AS oa,"
                    + " health_status AS p1, health_status AS p2, health_status AS p3, health_status AS p1vc, health_status AS p2vc, health_status AS p3vc  "

                    + " WHERE spd.switching_point_detail_id=j.switching_point_detail_id AND spd.active='Y' "
                    + " AND j.junction_id = mr.junction_id  AND j.active = 'Y' AND mr.voltage_unit_id = v.unit_id "
                    + " AND mr.current_unit_id = c.unit_id  AND j.junction_id = hsm.junction_id  "
                    + " AND hsm.health_status_id = oa.health_status_id  AND hsm.phase1_status_id = p1.health_status_id  AND hsm.phase2_status_id = p2.health_status_id "
                    + " AND hsm.phase3_status_id = p3.health_status_id AND hsm.phase1_vc_status_id = p1vc.health_status_id AND hsm.phase2_vc_status_id = p2vc.health_status_id "
                    + " AND hsm.phase3_vc_status_id = p3vc.health_status_id "
                    + " AND switching_point_name = '"+searchJunction.trim()+"' "
                    + " ORDER BY meter_readings_id DESC LIMIT 1";

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
               
                energyMeterStatus.setIvrs(rset.getString("ivrs_no"));
                energyMeterStatus.setSwitching_point_detail_id(rset.getInt("switching_point_detail_id"));
                energyMeterStatus.setJunction_id(rset.getInt("junction_id"));
                energyMeterStatus.setSwitching_rev_no(rset.getInt("switching_rev_no"));
                energyMeterStatus.setProgram_version_no(rset.getInt("program_version_no"));
                energyMeterStatus.setJunction_name(rset.getString("switching_point_name"));
                energyMeterStatus.setSanctioned_load(rset.getLong("sanctioned_load"));
                energyMeterStatus.setPhase1_healthy_voltage(rset.getLong("phase1_healthy_voltage"));
                energyMeterStatus.setPhase2_healthy_voltage(rset.getLong("phase2_healthy_voltage"));
                energyMeterStatus.setPhase3_healthy_voltage(rset.getLong("phase3_healthy_voltage"));
                energyMeterStatus.setPhase1_healthy_current(rset.getLong("phase1_healthy_current"));
                energyMeterStatus.setPhase2_healthy_current(rset.getLong("phase2_healthy_current"));
                energyMeterStatus.setPhase3_healthy_current(rset.getLong("phase3_healthy_current"));
                energyMeterStatus.setVoltage1(rset.getLong("voltage1"));
                energyMeterStatus.setVoltage2(rset.getLong("voltage2"));
                energyMeterStatus.setVoltage3(rset.getLong("voltage3"));
                energyMeterStatus.setCurrent1(rset.getLong("current1"));
                energyMeterStatus.setCurrent2(rset.getLong("current2"));
                energyMeterStatus.setCurrent3(rset.getLong("current3"));
                energyMeterStatus.setPower_factor(rset.getLong("power_factor"));
                energyMeterStatus.setConsumed_unit(rset.getLong("consumed_unit"));
                energyMeterStatus.setConnected_load(rset.getLong("connected_load"));
                energyMeterStatus.setVoltage_unit(rset.getString("voltage_unit"));
                energyMeterStatus.setCurrent_unit(rset.getString("current_unit"));
                energyMeterStatus.setReading_date(rset.getString("reading_date"));
                energyMeterStatus.setReading_time(rset.getString("reading_time"));
                energyMeterStatus.setOverall_status(rset.getString("over_all_status"));
                energyMeterStatus.setPhase1_status(rset.getString("phase1_status"));
                energyMeterStatus.setPhase2_status(rset.getString("phase2_status"));
                energyMeterStatus.setPhase3_status(rset.getString("phase3_status"));
                energyMeterStatus.setHealth_status_date(rset.getString("health_status_date"));
                energyMeterStatus.setHealth_status_time(rset.getString("health_status_time"));
                energyMeterStatus.setNo_of_phases(rset.getInt("ph"));
                energyMeterStatus.setNo_of_poles(rset.getInt("no_of_poles"));
                energyMeterStatus.setType_of_premises_id(rset.getInt("type_of_premises_id"));
                energyMeterStatus.setPhase1_vc_status(rset.getString("phase1_vc_status"));
                energyMeterStatus.setPhase2_vc_status(rset.getString("phase2_vc_status"));
                energyMeterStatus.setPhase3_vc_status(rset.getString("phase3_vc_status"));
                energyMeterStatus.setJuncOnTimeHr(rset.getInt("on_time_hr"));
                energyMeterStatus.setJuncOnTimeMin(rset.getInt("on_time_min"));
                energyMeterStatus.setJuncOffTimeHr(rset.getInt("off_time_hr"));
                energyMeterStatus.setJuncOffTimeMin(rset.getInt("off_time_min"));
            }

        } catch (Exception e) {
            System.out.println("Error: EnergyMeterStatusModel-showData--- " + e);
        }
        return energyMeterStatus;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPassword() {
        return db_userPassword;
    }

    public void setDb_userPassword(String db_userPassword) {
        this.db_userPassword = db_userPassword;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

     public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("EnergyMeterStatusModel closeConnection() Error: " + e);
        }
    }
}
