/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.WattageTypeBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author JPSS
 */
public class WattageTypeModel {
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
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }
public byte[] generateMapReport(String jrxmlFilePath, List<WattageTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in WattageTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(WattageTypeBean trafficTypeBean) {

        String query = "INSERT INTO wattage (wattage_value, active, created_by, remark) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, trafficTypeBean.getWattage_value());
            pstmt.setString(2, trafficTypeBean.getActive());
            pstmt.setString(3, trafficTypeBean.getCreated_by());
            pstmt.setString(4, trafficTypeBean.getRemark());
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

  public int updateRecord(WattageTypeBean wattageTypeBean) {
        String query = "UPDATE wattage SET wattage_value=?, active=?, created_by=?, remark=? WHERE wattage_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, wattageTypeBean.getWattage_value());
            pstmt.setString(2, wattageTypeBean.getActive());
            pstmt.setString(3, wattageTypeBean.getCreated_by());
            pstmt.setString(4, wattageTypeBean.getRemark());
            pstmt.setInt(5, wattageTypeBean.getWattage_id());
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

  public List<String> getWattageType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT wattage_id, wattage_value FROM wattage GROUP BY wattage_value ORDER BY wattage_value ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String wattage_type = rset.getString("wattage_value");
                if (wattage_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(wattage_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Wattage Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getWattageType ERROR inside WattageTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchWattageType) {
        String query = " SELECT Count(*) "
                + " FROM wattage "
                + " WHERE IF('" + searchWattageType + "' = '', wattage_value LIKE '%%',wattage_value =?) "
                + " ORDER BY wattage_value ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchWattageType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows wattage type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<WattageTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchWattageType) {
        List<WattageTypeBean> list = new ArrayList<WattageTypeBean>();

       String query = " SELECT wattage_id, wattage_value, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM wattage "
                + " WHERE IF('" + searchWattageType + "' = '',wattage_value  LIKE '%%',wattage_value =?) "
                + " ORDER BY wattage_value "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWattageType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WattageTypeBean wattageType = new WattageTypeBean();
                wattageType.setWattage_id(rset.getInt("wattage_id"));
                wattageType.setWattage_value(rset.getString("wattage_value"));
                wattageType.setActive(rset.getString("active"));
                wattageType.setCreated_by(rset.getString("created_by"));
                wattageType.setCreated_date(rset.getString("created_date"));
                wattageType.setRemark(rset.getString("remark"));
                list.add(wattageType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
   public List<WattageTypeBean> showAllData(String searchWattageType) {
        List<WattageTypeBean> listAll = new ArrayList<WattageTypeBean>();

       String query = " SELECT wattage_id, wattage_value, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM wattage "
                + " WHERE IF('" + searchWattageType + "' = '',wattage_value  LIKE '%%',wattage_value =?) "
                + " ORDER BY wattage_value ";
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWattageType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WattageTypeBean wattageType = new WattageTypeBean();
                wattageType.setWattage_id(rset.getInt("wattage_id"));
                wattageType.setWattage_value(rset.getString("wattage_value"));
                wattageType.setActive(rset.getString("active"));
                wattageType.setCreated_by(rset.getString("created_by"));
                wattageType.setCreated_date(rset.getString("created_date"));
                wattageType.setRemark(rset.getString("remark"));
                listAll.add(wattageType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

  public int deleteRecord(int wattage_type_id) {
        String query = "DELETE FROM wattage WHERE wattage_id=" + wattage_type_id;
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
