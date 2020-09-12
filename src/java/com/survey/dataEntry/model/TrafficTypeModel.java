/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import java.sql.Connection;
import com.survey.tableClasses.TrafficTypeBean;
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
 * @author JPSS
 */
public class TrafficTypeModel {
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public void setConnection() {
        try {
            Class.forName(driverClass);
           // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("TrafficTypeModel setConnection() Error: " + e);
        }
    }

        public byte[] generateMapReport(String jrxmlFilePath, List<TrafficTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
         HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in TrafficMapModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }


    public int insertRecord(TrafficTypeBean trafficTypeBean) {

        String query = "INSERT INTO traffic_type (traffic_type, created_by, remark) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, trafficTypeBean.getTraffic_type());
            pstmt.setString(2, trafficTypeBean.getCreated_by());
            pstmt.setString(3, trafficTypeBean.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;

    }

    public int updateRecord(TrafficTypeBean trafficTypeBean) {
        String query = "UPDATE traffic_type SET traffic_type=?, created_by=?, created_date=?, remark=? WHERE traffic_type_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, trafficTypeBean.getTraffic_type());
            pstmt.setString(2, trafficTypeBean.getCreated_by());
            pstmt.setString(3, trafficTypeBean.getCreated_date());
            pstmt.setString(4, trafficTypeBean.getRemark());
            pstmt.setInt(5, trafficTypeBean.getTraffic_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error while updating record........." + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getTrafficType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT traffic_type_id, traffic_type FROM traffic_type GROUP BY traffic_type ORDER BY traffic_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String traffic_type = rset.getString("traffic_type");
                if (traffic_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(traffic_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Traffic Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getTrafficType ERROR inside trafficTypeModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchTrafficType) {
        String query = " SELECT Count(*) "
                + " FROM traffic_type "
                + " WHERE IF('" + searchTrafficType + "' = '', traffic_type LIKE '%%',traffic_type =?) "
                + " ORDER BY traffic_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchTrafficType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows traffic type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<TrafficTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchTrafficType) {
        List<TrafficTypeBean> list = new ArrayList<TrafficTypeBean>();

       String query = " SELECT traffic_type_id, traffic_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM traffic_type "
                + " WHERE IF('" + searchTrafficType + "' = '', traffic_type LIKE '%%',traffic_type =?) "
                + " ORDER BY traffic_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchTrafficType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TrafficTypeBean trafficType = new TrafficTypeBean();
                trafficType.setTraffic_type_id(rset.getInt("traffic_type_id"));
                trafficType.setTraffic_type(rset.getString("traffic_type"));
                trafficType.setCreated_by(rset.getString("created_by"));
                trafficType.setCreated_date(rset.getString("created_date"));
                trafficType.setRemark(rset.getString("remark"));
                list.add(trafficType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
 public List<TrafficTypeBean> showAllData(String searchTrafficType) {
        List<TrafficTypeBean> listAll = new ArrayList<TrafficTypeBean>();

       String query = " SELECT traffic_type_id, traffic_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM traffic_type "
                + " WHERE IF('" + searchTrafficType + "' = '', traffic_type LIKE '%%',traffic_type =?) "
                + " ORDER BY traffic_type ";
                
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchTrafficType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TrafficTypeBean trafficType = new TrafficTypeBean();
                trafficType.setTraffic_type_id(rset.getInt("traffic_type_id"));
                trafficType.setTraffic_type(rset.getString("traffic_type"));
                trafficType.setCreated_by(rset.getString("created_by"));
                trafficType.setCreated_date(rset.getString("created_date"));
                trafficType.setRemark(rset.getString("remark"));
                listAll.add(trafficType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

    public int deleteRecord(int traffic_type_id) {
        String query = "DELETE FROM traffic_type WHERE traffic_type_id=" + traffic_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
     public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
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

}
