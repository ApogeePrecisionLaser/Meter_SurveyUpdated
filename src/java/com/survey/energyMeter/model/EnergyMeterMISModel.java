/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.model;

import com.survey.energyMeter.tableClasses.HealthStatusMapBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Pooja
 */
public class EnergyMeterMISModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= (Connection) DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",db_username,db_password);
        } catch (Exception e) {
            System.out.println("EnergyMeterMISModel setConnection Exception: "+ e);
        }
    }

    public List<HealthStatusMapBean> generateFaultyHealthStatusList() {
        
        List<HealthStatusMapBean> list = new ArrayList<HealthStatusMapBean>();
        String query = " SELECT health_status_map_id,switching_point_name,reading_date,reading_time,ph,"
                + " hs.health_status AS over_all_status ,hs1.health_status AS phase1_status ,hs2.health_status AS phase2_status ,"
                + " hs3.health_status AS phase3_status ,hsvc1.health_status AS phase1_vc_status ,hsvc2.health_status AS phase2_vc_status ,"
                + " hsvc3.health_status AS phase3_vc_status"
                + " FROM health_status_map AS hsm, junction AS j, switching_point_detail AS spd,"
                + " health_status AS hs, health_status AS hs1, health_status AS hs2, health_status AS hs3,"
                + " health_status AS hsvc1, health_status AS hsvc2, health_status AS hsvc3"
                + " WHERE hsm.health_status_id = hs.health_status_id AND hsm.phase1_status_id = hs1.health_status_id AND"
                + " hsm.phase2_status_id = hs2.health_status_id AND hsm.phase3_status_id = hs3.health_status_id AND"
                + " hsm.phase1_vc_status_id = hsvc1.health_status_id AND hsm.phase2_vc_status_id = hsvc2.health_status_id AND"
                + " hsm.phase3_vc_status_id = hsvc3.health_status_id AND hsm.junction_id = j.junction_id"
                + " AND j.active='Y' AND j.switching_point_detail_id = spd.switching_point_detail_id"
                + " AND spd.active='Y' AND DATE_SUB(CURDATE(),INTERVAL 8 DAY) <= reading_date AND hs.health_status = 'Not Healthy'"
                + " AND  health_status_map_id in (SELECT max(health_status_map_id) FROM health_status_map group By junction_id) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                HealthStatusMapBean healthStatusMapBean = new HealthStatusMapBean();
                healthStatusMapBean.setHealth_status_map_id(rset.getInt("health_status_map_id"));
                healthStatusMapBean.setSwitching_point_name(rset.getString("switching_point_name"));
                healthStatusMapBean.setReading_date(rset.getString("reading_date"));
                healthStatusMapBean.setReading_time(rset.getString("reading_time"));
                healthStatusMapBean.setPhase(rset.getInt("ph"));
                healthStatusMapBean.setHealth_status(rset.getString("over_all_status"));
                healthStatusMapBean.setPhase1_status(rset.getString("phase1_status"));
                healthStatusMapBean.setPhase2_status(rset.getString("phase2_status"));
                healthStatusMapBean.setPhase3_status(rset.getString("phase3_status"));
                healthStatusMapBean.setPhase1_vc_status(rset.getString("phase1_vc_status"));
                healthStatusMapBean.setPhase2_vc_status(rset.getString("phase2_vc_status"));
                healthStatusMapBean.setPhase3_vc_status(rset.getString("phase3_vc_status"));
                list.add(healthStatusMapBean);
            }
        } catch (Exception e) {
            System.out.println("Error in EnergyMeterMISModel generateFaultyHealthStatusList()....: " + e);
        }
        return list;
    }

     public byte[] generateFaultyHealthStatusReport(String jrxmlFilePath,List list) {
                byte[] reportInbytes = null;
                    HashMap mymap = new HashMap();
                   try{
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, jrBean);
                } catch (Exception e) {
                    System.out.println("EnergyMeterMISModel generateFaultyHealthStatusReport() JRException: " + e);
                }
                return reportInbytes;
            }


    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
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
            System.out.println("EnergyMeterMISModel closeConnection() Error: " + e);
        }
    }

}
