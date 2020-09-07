/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.tcpServer;

import com.survey.energyMeter.general.tableClasses.History;
import com.survey.energyMeter.tableClasses.EnergyMeterStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;

public class ClientResponderModel extends HttpServlet {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("ClientResponderModel setConnection() Error: " + e);
        }
    }

    public int getTypeOfConnection(int junction_id, int program_version_no) {
        int type_of_premises_id = 0;

        String query = "SELECT type_of_premises_id FROM switching_point_detail AS spd, junction AS j  "
                + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no "
                + " AND spd.active = 'Y' AND j.junction_id= ? AND j.program_version_no= ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                type_of_premises_id = rset.getInt("type_of_premises_id");
                System.out.println(type_of_premises_id);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return type_of_premises_id;
    }

    public String getTypeOfConnection(int id) {
        String type_of_premises = "";

        String query = "SELECT type_of_premsis FROM type_of_premises WHERE type_of_premises_id = ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                type_of_premises = rset.getString(1);
                System.out.println(type_of_premises);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getTypeOfConnection() Error: " + e);
        }
        return type_of_premises;
    }

    public List<Integer> getCitySunriseSunset(int month, int cityID) {
        List<Integer> risesetList = new ArrayList<Integer>();
        Integer sunriseHrs = 0, sunriseMin = 0, sunsetHrs = 0, sunsetMin = 0;
        String query = " SELECT sunrise_hr, sunrise_min, sunset_hr, sunset_min FROM jn_rise_set_time "
                + " WHERE SUBSTRING(date, 6, 2) = ? AND city_id = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, month);
            pstmt.setInt(2, cityID);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                sunriseHrs = rset.getInt("sunrise_hr");
                sunriseMin = rset.getInt("sunrise_min");
                sunsetHrs = rset.getInt("sunset_hr");
                sunsetMin = rset.getInt("sunset_min");
                risesetList.add(sunriseHrs);
                risesetList.add(sunriseMin);
                risesetList.add(sunsetHrs);
                risesetList.add(sunsetMin);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getCitySunriseSunset() Error: " + e);
        }
        return risesetList;
    }

    public String getJunctionName(int junctionID, int program_version_no) {
        String query = " SELECT switching_point_name FROM switching_point_detail AS spd, junction AS j  "
                + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no "
                + " AND spd.active = 'Y' AND j.junction_id= ? AND j.program_version_no= ?";
        String junction_name = "";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_name = rset.getString("switching_point_name");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getJunctionName() Error: " + e);
        }
        return junction_name;
    }

    public int updateFileNo(int junctionID, int file_no, int program_version_no) {
        String query = null;
        PreparedStatement pstmt = null;
        query = "UPDATE junction SET panel_file_no= ? WHERE junction_id = ? AND program_version_no = ? ";
        int rowsAffected = 0;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, file_no);
            pstmt.setInt(2, junctionID);
            pstmt.setInt(3, program_version_no);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException sqlEx) {
            System.out.println("ClientResponderModel updateFileNo() Error: " + sqlEx.getMessage());
        }
        return rowsAffected;
    }

    public boolean insertRecord(History history) {
        int rowsReturned = 0;
        if (checkJunctionHistory(history.getIp_address(), history.getPort())) {
            updateRecord(history);
        }
        String junctionQuery = " INSERT INTO log_history(ip_address, port, login_timestamp_date, login_timestamp_time, status, junction_id, program_version_no) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement pstmt;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            Logger.getLogger(ClientResponderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());
        boolean errorOccured = false;
        try {
            pstmt = connection.prepareStatement(junctionQuery);
            pstmt.setString(1, history.getIp_address());
            pstmt.setInt(2, history.getPort());
            pstmt.setDate(3, sqltDate);
            pstmt.setTime(4, sqltTime);
            pstmt.setString(5, history.isStatus() == false ? "Y" : "N");
            pstmt.setInt(6, history.getJunction_id());
            pstmt.setInt(7, history.getProgram_version_no());
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0) {
                System.out.println("ClientResponderModel insertRecord() Record inserted successfully ");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel insertRecord() Error: " + e);
        }
        return !errorOccured;
    }

    public boolean updateRecord(History history) {
        String junctionQuery = " UPDATE log_history SET status= ?, logout_timestamp_date= ?, "
                + " logout_timestamp_time= ? WHERE ip_address= ? AND port != ? AND logout_timestamp_date is null AND logout_timestamp_time is null  ";
        PreparedStatement pstmt = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
//        System.out.println(junctionQuery);
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            Logger.getLogger(ClientResponderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());
        boolean errorOccured = false;
        int rowsReturned = 0;
        try {
            pstmt = connection.prepareStatement(junctionQuery);
            boolean status = history.isStatus();
            pstmt.setString(1, "N");
            pstmt.setDate(2, sqltDate);
            pstmt.setTime(3, sqltTime);
            pstmt.setString(4, history.getIp_address());
            pstmt.setInt(5, history.getPort());
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0) {
                System.out.println("ClientResponderModel update() Record updated successfully ");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel update() Error: " + e);
        }
        return !errorOccured;
    }

    public boolean checkJunctionHistory(String ip, int port) {
        String query = " SELECT Count(*) FROM log_history WHERE ip_address= ? AND port != ? AND logout_timestamp_date is null AND logout_timestamp_time is null  ";

        int rowsReturned = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ip);
            pstmt.setInt(2, port);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                rowsReturned = rset.getInt(1);
            }
            rset.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel checkJunctionHistory() Error: " + e);
        }
        return rowsReturned > 0 ? true : false;
    }

    public boolean updateErrorStateOfLoggedInJunctions() {
        String junctionQuery = " UPDATE log_history SET status= ?, logout_timestamp_date= ?, logout_timestamp_time= ?, error_state= ? "
                + " WHERE status = 'Y' AND logout_timestamp_date is null AND logout_timestamp_time is null  ";
        PreparedStatement pstmt = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
//        System.out.println(junctionQuery);
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            Logger.getLogger(ClientResponderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());

        boolean errorOccured = false;
        int rowsReturned = 0;
        try {
            pstmt = connection.prepareStatement(junctionQuery);
            pstmt.setString(1, "N");
            pstmt.setDate(2, sqltDate);
            pstmt.setTime(3, sqltTime);
            pstmt.setString(4, "Y");
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0) {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Error state of all last logged in junctions updated successfully @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel updateErrorStateOfLoggedInJunctions() Error: " + e);
        }
        return !errorOccured;
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from log_history WHERE logout_timestamp_time is null ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public int getNoOfRowsInShowAll(String ipAddress, int port) {
        int noOfRows = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement("select count(*) from log_history WHERE ip_address= ? AND port= ? ");
            pstmt.setString(1, ipAddress);
            pstmt.setInt(2, port);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfRowsInShowAll() Error: " + e);
        }
        return noOfRows;
    }

    public List<History> showDetails(String ipAddress, int port, int lowerLimit, int noOfRowsToDisplay) {
        List<History> list = new ArrayList<History>();
        try {
            String query = " SELECT ip_address, port, login_timestamp_date, login_timestamp_time, status, logout_timestamp_date, "
                    + " logout_timestamp_time , status FROM  log_history WHERE ip_address= ? AND port= ? ORDER BY login_timestamp_date DESC, "
                    + " login_timestamp_time DESC LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
            PreparedStatement pstmt;
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ipAddress);
            pstmt.setInt(2, port);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History log_history = new History();
                log_history.setIp_address(rset.getString("ip_address"));
                log_history.setPort(rset.getInt("port"));
                log_history.setLogin_timestamp_date(rset.getString("login_timestamp_date"));
                log_history.setLogin_timestamp_time(rset.getString("login_timestamp_time"));
                log_history.setStatus(rset.getString("status").equals("Y") ? true : false);
                String logout_timestamp_date = rset.getString("logout_timestamp_date");
                String logout_timestamp_time = rset.getString("logout_timestamp_time");
                if (logout_timestamp_date == null) {
                    logout_timestamp_date = "";
                }
                if (logout_timestamp_time == null) {
                    logout_timestamp_time = "";
                }
                log_history.setLogout_timestamp_date(logout_timestamp_date);
                log_history.setLogout_timestamp_time(logout_timestamp_time);
                list.add(log_history);
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-showDetails--- " + e);
        }
        return list;
    }

    public List<History> showLoggedInJunctionDetails() {
        List<History> list = new ArrayList<History>();
        try {
            String query = " SELECT j.junction_id, switching_point_name, IF(isOnPole = 'Y' ,c.city_name, sc.city_name) AS city_name, ip_address, port, j.program_version_no, j.panel_file_no, "
                    + " IF(synchronization_status is null or synchronization_status = '' ,'N',synchronization_status) AS synchronization_status ,"
                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(application_hr,':',application_min,' ',application_date,'-',application_month,'-',application_year)) AS application_last_time, "
                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(junction_hr,':',junction_min,' ',junction_date,'-',junction_month,'-',junction_year)) AS junction_last_time"
                    + " FROM junction AS j LEFT JOIN time_synchronization_detail AS tsd "
                    + "         ON j.junction_id = tsd.junction_id AND tsd.final_revision='VALID' AND j.program_version_no=tsd.program_version_no, "
                    + "      switching_point_detail AS spd LEFT JOIN (pole AS p,city AS c, ward AS w, area AS a ,city AS sc, ward AS sw, area AS sa ) "
                    + "         ON spd.switching_point_detail_id = p.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no "
                    + "         AND spd.active = 'Y' AND p.active='Y' AND p.area_id = a.area_id "
                    + "         AND a.ward_id = w.ward_id AND a.ward_rev_no = w.ward_rev_no AND w.city_id=c.city_id AND w.active='Y' "
                    + "         AND spd.area_id = sa.area_id AND sa.ward_id = sw.ward_id AND sa.ward_rev_no = sw.ward_rev_no AND sw.city_id=sc.city_id AND sw.active='Y'"
                    + " , log_history AS lh "
                    + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no AND j.active = 'Y' AND spd.active='Y'"
                    + " AND j.junction_id=lh.junction_id AND j.program_version_no=lh.program_version_no AND logout_timestamp_time is null "
                    + " ORDER BY login_timestamp_date DESC, login_timestamp_time DESC ";
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                History log_history = new History();
                log_history.setJunction_id(rset.getInt("junction_id"));
                log_history.setProgram_version_no(rset.getInt("program_version_no"));
                log_history.setFileNo(rset.getInt("panel_file_no"));
                log_history.setJunction_name(rset.getString("switching_point_name"));
                log_history.setCity_name(rset.getString("city_name"));
                log_history.setIp_address(rset.getString("ip_address"));
                log_history.setPort(rset.getInt("port"));
                log_history.setApplication_last_time_set(rset.getString("application_last_time"));
                log_history.setJunction_last_time_set(rset.getString("junction_last_time"));
                log_history.setTime_synchronization_status(rset.getString("synchronization_status"));
                list.add(log_history);
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-showLoggedInJunctionDetails--- " + e);
        }
        return list;
    }

    public List<Integer> getJunctionID() {
        int junction_id = 0;
        List<Integer> list = new ArrayList<Integer>();
        String query = "SELECT j.junction_id FROM  junction AS j, log_history AS lh "
                + " WHERE j.junction_id=lh.junction_id AND logout_timestamp_time is null "
                + " ORDER BY login_timestamp_date DESC, login_timestamp_time DESC ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("j.junction_id");
                list.add(junction_id);
            }
        } catch (Exception e) {
            System.out.println("Error: clientResponderModel getJunctionID " + e);
        }
        return list;
    }

    public int checkJunctionId(int junctionID, int program_version_no, int file_no) {
        int result = 0;
        int j_id = 0, pv_no = 0, f_no = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement("SELECT junction_id, program_version_no, panel_file_no FROM junction "
                    + "WHERE (junction_id= " + junctionID + " OR program_version_no = " + program_version_no + ") AND active='Y' ");

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            j_id = rset.getInt("junction_id");
            pv_no = rset.getInt("program_version_no");
            f_no = rset.getInt("panel_file_no");

            result = j_id == junctionID ? pv_no == program_version_no ? 12 : 6 : 5;

        } catch (Exception e) {
            result = 0;
            System.out.println("ClientResponderModel checkJunctionId() Error: " + e);
        }
        return (junctionID==0 || j_id==0) ? 5 : result;
    }

    public int checkJunctionError(String imeiNo, String energyMeterNo) {
        int result = 0;
        String imei_no, energy_meter_no;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement("SELECT imei_no, energy_meter_no FROM junction "
                    + " WHERE (imei_no = '" + imeiNo + "' OR energy_meter_no = '" + energyMeterNo + "') AND active='Y' ");

            ResultSet rset = pstmt.executeQuery();
            rset.next();
            imei_no = rset.getString("imei_no");
            energy_meter_no = rset.getString("energy_meter_no");

            result = imei_no.equals(imeiNo) ? energy_meter_no.equals(energyMeterNo) ? 17 : 16 : 15;

        } catch (Exception e) {
            result = 0;
            System.out.println("ClientResponderModel checkJunctionError() Error: " + e);
        }
        return result;
    }

    public List<History> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<History> list = new ArrayList<History>();
        try {
            String query = " SELECT ip_address, port, login_timestamp_date, login_timestamp_time, status, logout_timestamp_date, "
                    + " logout_timestamp_time FROM  log_history WHERE logout_timestamp_time is null ORDER BY login_timestamp_date DESC, "
                    + " login_timestamp_time DESC LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                History log_history = new History();
                log_history.setIp_address(rset.getString("ip_address"));
                log_history.setPort(rset.getInt("port"));
                log_history.setLogin_timestamp_date(rset.getString("login_timestamp_date"));
                log_history.setLogin_timestamp_time(rset.getString("login_timestamp_time"));
                log_history.setStatus(rset.getString("status").equals("Y") ? true : false);
                list.add(log_history);
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-showData--- " + e);
        }
        return list;
    }

    public boolean checkJunctionLastSynchronisation(String ipAddress, String port, int junctionID, int program_version_no, boolean testRequest) {
        boolean result = false;
        try {
            String query1 = " SELECT COUNT(*) FROM log_history "
                    + " WHERE CONCAT_WS(' ',logout_timestamp_date,logout_timestamp_time) BETWEEN DATE_SUB(now(),INTERVAL 4 Hour) AND now() "
                    + " AND ip_address='" + ipAddress + "' AND status='N' AND junction_id= " + junctionID + " AND program_version_no = " + program_version_no
                    + " AND IF(" + testRequest + "=1, error_state LIKE '%%' , error_state = 'Y') ";

            ResultSet rset1 = connection.prepareStatement(query1).executeQuery();
            if (rset1.next()) {
                result = rset1.getInt(1) > 0 ? true : false;
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-checkJunctionLastSynchronisation--- " + e);
        }
        return result;
    }

    public boolean isJunctionLive(String ipAddress, String port, int junctionID, int program_version_no) {
        boolean result = false;
        try {
            String query = " SELECT COUNT(*) FROM log_history WHERE ip_address='" + ipAddress + "' AND status='Y' AND junction_id = " + junctionID + " AND program_version_no = " + program_version_no;
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                result = rset.getInt(1) == 0 ? false : true;
            }
        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-isJunctionLive--- " + e);
        }
        return result;
    }

    public int getNoOfPhases(int junction_id, int program_version_no) {
        int noOfPhases = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT ph FROM switching_point_detail AS spd, junction AS j  "
                    + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no "
                    + " AND spd.active = 'Y' AND junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfPhases = rset.getInt(1);
            System.out.println(noOfPhases);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPhases() Error: " + e);
        }
        return noOfPhases;
    }

    public int getFileNo(int junction_id, int program_version_no) {
        int fileNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT panel_file_no FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            fileNo = rset.getInt("panel_file_no");
            //System.out.println(fileNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getFIleNo() Error: " + e);
        }
        return fileNo;
    }

    public int getJunctionID(String imeiNo, String energyMeterNo) {
//        System.out.println(IMEINo);
        int junction_id = 0;
        String queryJunctionID = " SELECT junction_id FROM junction WHERE imei_no = ? AND energy_meter_no = ? AND active = 'Y'";

        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, imeiNo);
            pstmt.setString(2, energyMeterNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("junction_id");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getJunctionID() Error" + e);
        }
        return junction_id;
    }

    public int getProgramVersionNo(String imeiNo, String energyMeterNo, int junctionID) {
        int program_Version_no = 0;
        String queryJunctionID = " SELECT program_version_no FROM junction WHERE imei_no = ? AND energy_meter_no = ? AND junction_id = ? AND active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, imeiNo);
            pstmt.setString(2, energyMeterNo);
            pstmt.setInt(3, junctionID);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                program_Version_no = rset.getInt("program_version_no");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getProgramVersionNo() Error" + e);
        }
        return program_Version_no;
    }

    public int getCityId(int junctionID) {
        int id = 0;
        String query = "SELECT IF(isOnPole = 'Y' ,c.city_id, sc.city_id) AS city_id FROM junction AS j,"
                + " switching_point_detail AS spd LEFT JOIN (pole AS p,city AS c, ward AS w, area AS a ,city AS sc, ward AS sw, area AS sa )"
                + " ON spd.switching_point_detail_id = p.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no"
                + " AND spd.active = 'Y' AND p.active='Y' AND p.area_id = a.area_id"
                + " AND a.ward_id = w.ward_id AND a.ward_rev_no = w.ward_rev_no AND w.city_id=c.city_id AND w.active='Y'"
                + " AND spd.area_id = sa.area_id AND sa.ward_id = sw.ward_id AND sa.ward_rev_no = sw.ward_rev_no AND sw.city_id=sc.city_id AND sw.active='Y'"
                + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no AND j.active = 'Y' AND spd.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt("city_id");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getCityId() Error" + e);
        }
        return id;
    }

    public boolean isValidJunction(int junctionID, int program_version_no) {
        boolean result = false;
        String queryJunctionID = " SELECT count(*) AS c FROM junction WHERE junction_id = ? AND program_version_no = ? AND active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                result = rset.getInt("c") > 0 ? true : false;
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel: isValidJunction() Error" + e);
        }
        return result;
    }

    public int getHealthStatusId(String status) {
        int healthStatusId = 0;
        String query = "SELECT health_status_id FROM health_status WHERE health_status = '" + status.trim() + "'";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                healthStatusId = rset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getHealthStatusId() Error: " + e);
        }
        return healthStatusId;
    }

    public int insertReadings(EnergyMeterStatus readings) {
        int rowAffected = 0;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            Calendar cal = Calendar.getInstance();
            String currentTime = dateFormat.format(cal.getTime());
            java.util.Date parsedUtilDate = null;
            java.util.Date parsedUtilTime = null;
            try {
                parsedUtilDate = dateFormat.parse(currentDate);
                System.out.println(parsedUtilDate);
                parsedUtilTime = dateFormat.parse(currentTime);
            } catch (ParseException ex) {
                System.out.println("MeterReadingModel insertRecord dateFormat ParseException: " + ex);
            }
            java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
            java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());
            java.sql.Time sqltTime1 = new java.sql.Time(readings.getJuncOnTimeHr());

            String query = "INSERT INTO meter_readings( junction_id, program_version_no, voltage1, voltage2, voltage3, current1, current2, current3, "
                    + " power_factor, voltage_unit_id, current_unit_id, reading_date, reading_time, consumed_unit, connected_load, on_time_hr, on_time_min, off_time_hr, off_time_min ) "
                    + " VALUES(?, ?, ?, ?, ?, ? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, readings.getJunction_id());
            pstmt.setInt(2, readings.getProgram_version_no());
            pstmt.setDouble(3, readings.getVoltage1());
            pstmt.setDouble(4, readings.getVoltage2());
            pstmt.setDouble(5, readings.getVoltage3());
            pstmt.setDouble(6, readings.getCurrent1());
            pstmt.setDouble(7, readings.getCurrent2());
            pstmt.setDouble(8, readings.getCurrent3());
            pstmt.setDouble(9, readings.getPower_factor());
            pstmt.setInt(10, 1);
            pstmt.setInt(11, 1);
            pstmt.setDate(12, sqltDate);
            pstmt.setTime(13, sqltTime);
            pstmt.setDouble(14, readings.getConsumed_unit());
            pstmt.setDouble(15, readings.getConnected_load());
            pstmt.setInt(16, readings.getJuncOnTimeHr());
            pstmt.setInt(17, readings.getJuncOnTimeMin());
            pstmt.setInt(18, readings.getJuncOffTimeHr());
            pstmt.setInt(19, readings.getJuncOffTimeMin());
            rowAffected = pstmt.executeUpdate();
            if (rowAffected > 0 && !getLastFinalHealthStatus(readings.getJunction_id(), readings.getProgram_version_no(), readings.getOverall_status_id(), readings.getPhase1_status_id(), readings.getPhase2_status_id(), readings.getPhase3_status_id(), readings.getPhase1_vc_status_id(), readings.getPhase2_vc_status_id(), readings.getPhase3_vc_status_id())) {
                rowAffected = 0;
                rowAffected = insertHealthStatus(readings.getJunction_id(), readings.getProgram_version_no(), readings.getOverall_status_id(), readings.getPhase1_status_id(), readings.getPhase2_status_id(), readings.getPhase3_status_id(), readings.getPhase1_vc_status_id(), readings.getPhase2_vc_status_id(), readings.getPhase3_vc_status_id());
            }

        } catch (Exception e) {
            System.out.println("Error: MeterReadingModel-insertRecord--- " + e);
        }

        return rowAffected;
    }

    private boolean getLastFinalHealthStatus(int junction_id, int program_version_no, int overAllStatusId, int phase1StatusId, int phase2StatusId, int phase3StatusId, int phase1VCStatusId, int phase2VCStatusId, int phase3VCStatusId) {
        int result = 0;
        String query = " SELECT IF(health_status_id = " + overAllStatusId + " AND  phase1_status_id = " + phase1StatusId + " AND phase2_status_id = " + phase2StatusId
                + " AND  phase3_status_id = " + phase3StatusId + " AND  phase1_vc_status_id = " + phase1VCStatusId + " AND  phase2_vc_status_id = " + phase2VCStatusId
                + " AND phase3_vc_status_id = " + phase3VCStatusId + " , 1 , 0 ) AS matchedLastStatus "
                + " FROM health_status_map WHERE junction_id =" + junction_id + " AND program_version_no= " + program_version_no + " ORDER BY health_status_map_id DESC LIMIT 1 ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                result = rset.getInt("matchedLastStatus");
            }
        } catch (Exception e) {
            System.out.println("ClientResponder: getLastFinalHealth() Error" + e);
        }
        return result == 1 ? true : false;
    }

    public int insertHealthStatus(int junction_id, int program_version_no, int overAllStatusId, int phase1StatusId, int phase2StatusId, int phase3StatusId, int phase1VCStatusId, int phase2VCStatusId, int phase3VCStatusId) {
        int rowsReturned = 0;

        String query = "INSERT INTO health_status_map(health_status_id, junction_id, program_version_no, reading_date, reading_time, phase1_status_id, phase2_status_id,"
                + " phase3_status_id, phase1_vc_status_id, phase2_vc_status_id, phase3_vc_status_id ) "
                + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";

        PreparedStatement pstmt = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
//        System.out.println(junctionQuery);
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            Logger.getLogger(ClientResponderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, overAllStatusId);
            pstmt.setInt(2, junction_id);
            pstmt.setInt(3, program_version_no);
            pstmt.setDate(4, sqltDate);
            pstmt.setTime(5, sqltTime);
            pstmt.setInt(6, phase1StatusId);
            pstmt.setInt(7, phase2StatusId);
            pstmt.setInt(8, phase3StatusId);
            pstmt.setInt(9, phase1VCStatusId);
            pstmt.setInt(10, phase2VCStatusId);
            pstmt.setInt(11, phase3VCStatusId);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ClientResponderModel insertHealthStatus() Error: " + e);
        }
        return rowsReturned;
    }

    public String getOverAllStatus(int noOfPhasesFromDB, String phase1Status, String phase2Status, String phase3Status, String phase1VCStatus, String phase2VCStatus, String phase3VCStatus) {
        String status = "Not Healthy";
        if (noOfPhasesFromDB == 3) {
            status = phase1Status.equals(phase2Status) && phase2Status.equals(phase3Status) && phase3Status.equals("No Fault")
                    && phase1VCStatus.equals(phase2VCStatus) && phase2VCStatus.equals(phase3VCStatus) && phase3VCStatus.equals("Healthy") ? "Healthy" : "Not Healthy";
        } else if (noOfPhasesFromDB == 2) {
            status = phase1Status.equals(phase2Status) && phase2Status.equals("No Fault")
                    && phase1VCStatus.equals(phase2VCStatus) && phase2VCStatus.equals("Healthy") ? "Healthy" : "Not Healthy";
        } else {
            status = phase1Status.equals("No Fault")
                    && phase1VCStatus.equals("Healthy") ? "Healthy" : "Not Healthy";
        }
        return status;
    }

    public int getProgramVersionNo(int junctionID) {
        int program_Version_no = 0;
        String queryJunctionID = " SELECT program_version_no FROM junction WHERE junction_id = ? AND active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                program_Version_no = rset.getInt("program_version_no");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getProgramVersionNo() Error" + e);
        }
        return program_Version_no;
    }

    public boolean updateJunctionTransferredStatus(int junctionId, int program_version_no, String status) {
        boolean isTransferred = false;
        String queryJunctionID = " UPDATE junction SET panel_transferred_status = ? WHERE junction_id = ? AND program_version_no= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, status);
            pstmt.setInt(2, junctionId);
            pstmt.setInt(3, program_version_no);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                isTransferred = true;
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: updateJunctionTransferredStatus() Error" + e);
        }
        return isTransferred;
    }

    public int synchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        int rowAffected = 0;
        String activity = "";
        String lastTimeSynchronizationStatus = getLastTimeSynchronizationStatus(junctionID, program_version_no);
        String currentTimeSynchronizationStatus = getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);

        if (lastTimeSynchronizationStatus.equals(currentTimeSynchronizationStatus)) {
            rowAffected = updateSynchronizeTimeData(junctionID, program_version_no, juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);
            activity = "updated";
        } else {
            rowAffected = insertSynchronizeTimeData(junctionID, program_version_no, juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear, currentTimeSynchronizationStatus);
            activity = "inserted";
        }
        if (rowAffected > 0) {
            System.out.println("Synchronized time data " + activity + " successfully.");
        }
        return rowAffected;
    }

    public String getCurrentTimeSynchronizationStatus(int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        String currentTimeSynchronizationStatus = "N";
        if (juncHr == appHr && juncMin == appMin && juncDat == appDat && juncMonth == appMonth && juncYear == appYear) {
            currentTimeSynchronizationStatus = "Y";
        } else {
            currentTimeSynchronizationStatus = "N";
        }
        return currentTimeSynchronizationStatus;
    }

    public String getLastTimeSynchronizationStatus(int junctionID, int program_version_no) {
        String status = "No Record";
        int final_rev_no = getLastTimeSynchronizationId(junctionID, program_version_no);
        if (final_rev_no != -1) {
            String query = " SELECT synchronization_status FROM time_synchronization_detail "
                    + " WHERE time_synchronization_detail_id = ? "
                    + " AND junction_id = ? AND program_version_no = ? ";
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, final_rev_no);
                pstmt.setInt(2, junctionID);
                pstmt.setInt(3, program_version_no);
                ResultSet rset = pstmt.executeQuery();
                while (rset.next()) {
                    status = rset.getString("synchronization_status");
                }
//            connection.close();
            } catch (Exception e) {
                System.out.println("ClientResponderModel: getLastTimeSynchronizationStatus() Error" + e);
            }
        }
        return status;
    }

    public int updateSynchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        int rowsReturned = 0;
        String insertQuery = " UPDATE time_synchronization_detail SET  junction_hr = ?, application_hr = ?, junction_min = ?, application_min = ?, "
                + " junction_date = ?, application_date = ?, junction_month = ?, application_month = ?, junction_year = ?, application_year = ? WHERE "
                + " junction_id = ? AND time_synchronization_detail_id = ? AND program_version_no = ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, juncHr);
            pstmt.setInt(2, appHr);
            pstmt.setInt(3, juncMin);
            pstmt.setInt(4, appMin);
            pstmt.setInt(5, juncDat);
            pstmt.setInt(6, appDat);
            pstmt.setInt(7, juncMonth);
            pstmt.setInt(8, appMonth);
            pstmt.setInt(9, juncYear);
            pstmt.setInt(10, appYear);
            pstmt.setInt(11, junctionID);
            pstmt.setInt(12, getLastTimeSynchronizationId(junctionID, program_version_no));
            pstmt.setInt(13, program_version_no);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ClientResponderModel updateSynchronizeTimeData() Error: " + e);
        }
        return rowsReturned;
    }

    public int insertSynchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear, String currentTimeSynchronizationStatus) {
        int rowsReturned = 0;
        String insertQuery = " INSERT INTO time_synchronization_detail(junction_id, time_synchronization_detail_id, junction_hr, application_hr, junction_min, application_min, "
                + " junction_date, application_date, junction_month, application_month, junction_year, application_year, synchronization_status, remark, program_version_no) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        PreparedStatement pstmt;
        try {
            int final_rev_no = getLastTimeSynchronizationId(junctionID, program_version_no);
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, (final_rev_no + 1));
            pstmt.setInt(3, juncHr);
            pstmt.setInt(4, appHr);
            pstmt.setInt(5, juncMin);
            pstmt.setInt(6, appMin);
            pstmt.setInt(7, juncDat);
            pstmt.setInt(8, appDat);
            pstmt.setInt(9, juncMonth);
            pstmt.setInt(10, appMonth);
            pstmt.setInt(11, juncYear);
            pstmt.setInt(12, appYear);
            pstmt.setString(13, currentTimeSynchronizationStatus);
            pstmt.setString(14, "");
            pstmt.setInt(15, program_version_no);
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0 && final_rev_no != -1) {
                rowsReturned = 0;
                rowsReturned = updateSynchronizeTimeFinalRevision(junctionID, program_version_no, final_rev_no);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel insertSynchronizeTimeData() Error: " + e);
        }
        return rowsReturned;
    }

    public int updateSynchronizeTimeFinalRevision(int junctionID, int program_version_no, int time_synchronization_detail_id) {
        String updateQuery = "UPDATE time_synchronization_detail SET  final_revision = ? WHERE  junction_id = ? AND time_synchronization_detail_id = ? AND program_version_no = ? ";
        int rowsReturned = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, "EXPIRED");
            pstmt.setInt(2, junctionID);
            pstmt.setInt(3, time_synchronization_detail_id);
            pstmt.setInt(4, program_version_no);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ClientResponderModel updateSynchronizeTimeFinalRevision() Error: " + e);
        }
        return rowsReturned;
    }

    public int getLastTimeSynchronizationId(int junctionID, int program_version_no) {
        int id = 0;
        String query = " SELECT COUNT(*) AS c,time_synchronization_detail_id AS id FROM time_synchronization_detail WHERE final_revision='VALID' AND junction_id = ? AND program_version_no = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt("c") != 0 ? rset.getInt("id") : -1;
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: getLastTimeSynchronizationId() Error" + e);
        }
        return id;
    }

    public int getjuncOnOffTimeSynchronizationStatus(int junctionID, int program_version_no, int juncOnTimeHr, int juncOnTimeMin, int juncOffTimeHr, int juncOffTimeMin) {
        int status = 0;
        String query = " SELECT IF(sunrise_hr = " + juncOnTimeHr + " AND  sunrise_min = " + juncOnTimeHr + " AND sunset_hr = " + juncOnTimeHr + " AND sunset_min = " + juncOnTimeHr + ", 1,0 ) AS status, "
                + " city_id,date,SUBSTRING(date, 6),SUBSTRING(CURDATE(),6) FROM jn_rise_set_time WHERE city_id = " + getCityId(junctionID)
                + " AND SUBSTRING(date, 6)=SUBSTRING(CURDATE(),6) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                status = rset.getInt("status");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: getjuncOnOffTimeSynchronizationStatus() Error" + e);
        }
        return status;
    }

    public long getTimeInterval() {
        long timeInterval = 0;
        String query = " SELECT time_interval AS time_interval_in_seconds FROM time_interval t WHERE time_interval_for='Energy Meter Data' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                timeInterval = rset.getLong("time_interval_in_seconds");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: getTimeInterval() Error" + e);
        }
        return timeInterval;
    }

    public int insertIntoErrorLog(int junction_id, int error, String received_data, String sent_data) {
        int rowsReturned = 0;
        String insertQuery = " INSERT INTO error_log(received_data, sent_data, error_id, remark, junction_id) "
                + " VALUES (?, ?, ?, ?, ?) ";

        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setString(1, received_data);
            pstmt.setString(2, sent_data);
            pstmt.setInt(3, error);
            pstmt.setString(4, "");
            pstmt.setInt(5, junction_id);
            rowsReturned = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("ClientResponderModel insertIntoErrorLog() Error: " + e);
        }
        return rowsReturned;
    }

    public String getSentErrorMessage(int error) {
        String error_message = "";
        String query = " SELECT message FROM error_message WHERE error_message_id = " + error;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                error_message = rset.getString("message");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: getSentErrorMessage() Error" + e);
        }
        return error_message;
    }

    public String getMasterMobileNo(int junction_id, int program_version_no) {
        String master_mobile_no = null;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT master_mobile_no FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            master_mobile_no = rset.getString("master_mobile_no");
        } catch (Exception e) {
            System.out.println("ClientResponderModel getMasterMobileNo() Error: " + e);
        }
        return master_mobile_no==null || master_mobile_no.isEmpty() ? "0000000000" : master_mobile_no ;
    }

    public String getSecMobile1(int junction_id, int program_version_no) {
        String secondary_mobile_no1 = null;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT secondary_mobile_no1 FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            secondary_mobile_no1 = rset.getString("secondary_mobile_no1");
        } catch (Exception e) {
            System.out.println("ClientResponderModel getSecMobile1() Error: " + e);
        }
        return secondary_mobile_no1==null || secondary_mobile_no1.isEmpty() ? "0000000000" : secondary_mobile_no1 ;
    }

    public String getSecMobile2(int junction_id, int program_version_no) {
        String secondary_mobile_no2 = null;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT secondary_mobile_no2 FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            secondary_mobile_no2 = rset.getString("secondary_mobile_no2");
        } catch (Exception e) {
            System.out.println("ClientResponderModel getSecMobile2() Error: " + e);
        }
        return secondary_mobile_no2==null || secondary_mobile_no2.isEmpty() ? "0000000000" : secondary_mobile_no2 ;
    }

    public String getIp1(int junction_id, int program_version_no) {
        String ip_address1 = null;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT ip_address1 FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            ip_address1 = rset.getString("ip_address1");
        } catch (Exception e) {
            System.out.println("ClientResponderModel getIp1() Error: " + e);
        }
        return ip_address1==null  ? "" : ip_address1 ;
    }

     public String getIp2(int junction_id, int program_version_no) {
        String ip_address2 = null;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT ip_address2 FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            ip_address2 = rset.getString("ip_address2");
        } catch (Exception e) {
            System.out.println("ClientResponderModel getIp2() Error: " + e);
        }
        return ip_address2==null  ? "" : ip_address2 ;
    }

    public int getPort1(int junction_id, int program_version_no) {
        int port1 = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT port1 FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            port1 = rset.getInt("port1");
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPort1() Error: " + e);
        }
        return port1 ;
    }

    public int getPort2(int junction_id, int program_version_no) {
        int port2 = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT port2 FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            port2 = rset.getInt("port2");
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPort2() Error: " + e);
        }
        return port2 ;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel: closeConnection() Error: " + e);
        }
    }

    public void setDriverClass(String driverclass) {
        this.driverClass = driverclass;
    }

    public void setConnectionString(String connectionstring) {
        this.connectionString = connectionstring;
    }

    public void setDb_userName(String username) {
        this.db_userName = username;
    }

    public void setDb_userPasswrod(String pass) {
        this.db_userPassword = pass;
    }
}
