/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.model;

import com.survey.energyMeter.tableClasses.JunctionBean;

import com.survey.energyMeter.tableClasses.HealthStatusMapBean;
import com.survey.energyMeter.tableClasses.PhaseBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author jpss
 */
public class JunctionDetailModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;

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
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public HealthStatusMapBean getJunctionHealthBitData(HealthStatusMapBean bean, int junction_id){
        EnergyMeterWebServiceModel energeyMeterModel = new EnergyMeterWebServiceModel();
        //HealthStatusMapBean bean = new HealthStatusMapBean();
        String query = "SELECT junction_phase_health_id, junction_id, phase1_bits, phase2_bits, phase3_bits, contactor_status_bits, command "
                + " FROM junction_phase_health "
                + " WHERE junction_id=" + junction_id + " ORDER BY junction_phase_health_id DESC LIMIT 0, 1 ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){                
                String phase1 = rs.getString("phase1_bits");
                String phase2 = rs.getString("phase2_bits");
                String phase3 = rs.getString("phase3_bits");
                bean.setPhase1Bean(new PhaseBean());
                bean.setPhase2Bean(new PhaseBean());
                bean.setPhase3Bean(new PhaseBean());
                energeyMeterModel.getHealthStatus_1(phase1.split(""), bean.getPhase1Bean());
                energeyMeterModel.getHealthStatus_1(phase2.split(""), bean.getPhase2Bean());
                energeyMeterModel.getHealthStatus_1(phase3.split(""), bean.getPhase3Bean());

                String[] contactor_status = rs.getString("contactor_status_bits").split("");
                int a = contactor_status.length - 1;
                if(a != -1)
                    if(contactor_status[a].equals("0"))
                        bean.setContactor_status("OFF");
                    else
                        bean.setContactor_status("ON");

                int command = rs.getInt("command");
                if(command == 0)
                    bean.setContactor_command("OFF");
                else
                    bean.setContactor_command("ON");
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getJunctionHealthBitData() in JunctionDetailModel : " + ex);
        }
        return bean;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection con) {
        this.connection = con;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("JunctionModel closeConnection() Error: " + e);
        }
    }
}
