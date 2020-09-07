/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.mysql.jdbc.Connection;

import com.survey.tableClasses.SwitchingPointReportBean;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SwitchingPointReportModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public List<SwitchingPointReportBean> getZoneReport() {
        SwitchingPointReportBean bean = null;
        int sum_switching_point_nos = 0;
        int sum_pole_nos = 0;
        List<SwitchingPointReportBean> list = new ArrayList<SwitchingPointReportBean>();
        //   String query = "select city, count(*) as Total_SP, SUM(no_of_poles) as Total_Poles from switching_point_detail group by city";
        String query = "select spd.city, count(*) as Total_SP, SUM(spd.no_of_poles) as Total_Poles, "
                + " (select count(*) from pole p ,switching_point_detail spdd "
                + " WHERE p.switching_point_detail_id =  spdd.switching_point_detail_id AND "
                + " spd.city = spdd.city "
                + " group by spdd.city) as pole_no_of_poles "
                + " FROM switching_point_detail spd group by spd.city ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                bean = new SwitchingPointReportBean();
                bean.setCity(rset.getString("city"));
                bean.setNo_of_switching_points(rset.getInt("Total_SP"));
                bean.setSwitching_no_of_poles(rset.getInt("Total_Poles"));
                bean.setPole_no_of_poles(rset.getInt("pole_no_of_poles"));
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("Error inside getZOneReport is " + e);
        }
        return list;
    }

    public int getSumTotalSwitchingNos() {
        int sum_switching_point_nos = 0;
        String query = "select count(*) from switching_point_detail";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                sum_switching_point_nos = rset.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error inside getZOneReport is " + e);
        }
        return sum_switching_point_nos;
    }

    public int getSumTotalPoleNos() {

        int sum_pole_nos = 0;

        String query = "select SUM(no_of_poles) from switching_point_detail";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                sum_pole_nos = rset.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error inside getZOneReport is " + e);
        }
        return sum_pole_nos;
    }

    public int getSumTotalPoleNosFromPole() {

        int sum_pole_nos = 0;

        String query = "select count(*) from pole";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                sum_pole_nos = rset.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error inside getSumTotalPoleNosFromPole is " + e);
        }
        return sum_pole_nos;
    }

    public List<SwitchingPointReportBean> showZoneData(String zone) {
        List<SwitchingPointReportBean> list = new ArrayList<SwitchingPointReportBean>();
        SwitchingPointReportBean bean = null;

        String query = "select spd.pole_no_s, spd.meter_no_s, spd.switching_point_name, spd.feeder, spd.no_of_poles "
                + " from switching_point_detail spd "
                + " where city = ? ";


        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, zone);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                bean = new SwitchingPointReportBean();
                bean.setSwitching_point_no(rset.getString("pole_no_s"));
                bean.setMeter_no(rset.getString("meter_no_s"));
                bean.setSwitching_point_name(rset.getString("switching_point_name"));
                bean.setFeeder(rset.getString("feeder"));
                bean.setSwitching_no_of_poles(rset.getInt("no_of_poles"));
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("Error inside showZoneData is " + e);
        }
        return list;
    }

    public List<SwitchingPointReportBean> showSwitchingPointsOfPoles(String zone) {
        List<SwitchingPointReportBean> list = new ArrayList<SwitchingPointReportBean>();
        SwitchingPointReportBean bean = null;

        String query = "select spd.pole_no_s, spd.switching_point_name, spd.feeder,count(*) as total_poles "
                + " FROM pole p, switching_point_detail spd "
                + " WHERE p.switching_point_detail_id = spd.switching_point_detail_id "
                + " AND spd.city = ? GROUP BY spd.switching_point_name";


        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, zone);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                bean = new SwitchingPointReportBean();
                bean.setSwitching_point_no(rset.getString("pole_no_s"));
                bean.setSwitching_point_name(rset.getString("switching_point_name"));
                bean.setFeeder(rset.getString("feeder"));
                bean.setPole_no_of_poles(rset.getInt("total_poles"));
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("Error inside showSwitchingPointsOfPoles is " + e);
        }
        return list;
    }

    public List<SwitchingPointReportBean> showPoleDetailofSwitchingPoint(String zone, String switching_pt_name) {
        List<SwitchingPointReportBean> list = new ArrayList<SwitchingPointReportBean>();
        SwitchingPointReportBean bean = null;

        String query = "select p.pole_no ,pt.pole_type, p.pole_span, p.pole_height, p.mounting_height"
                + "  FROM pole p, switching_point_detail spd, pole_type pt "
                + " WHERE p.switching_point_detail_id = spd.switching_point_detail_id "
                + " AND pt.pole_type_id = p.pole_type_id"
                + " AND spd.city= ? and spd.switching_point_name = ? ";


        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, zone);
            stmt.setString(2, switching_pt_name);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                bean = new SwitchingPointReportBean();
                bean.setPole_no(rset.getString("pole_no"));
                bean.setPole_type(rset.getString("pole_type"));
                bean.setPole_span(rset.getDouble("pole_span"));
                bean.setPole_height(rset.getDouble("pole_height"));
                bean.setMounting_height(rset.getDouble("mounting_height"));
                list.add(bean);
            }

        } catch (Exception e) {
            System.out.println("Error inside showPoleDetailofSwitchingPoint is " + e);
        }
        return list;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }
}
