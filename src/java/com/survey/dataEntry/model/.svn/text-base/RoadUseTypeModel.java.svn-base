/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.RoadUseTypeBean;
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
 * @author JPSS
 */
public class RoadUseTypeModel {
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
            System.out.println("TypeModel setConnection() Error: " + e);
        }
    }
 public byte[] generateMapReport(String jrxmlFilePath, List<RoadUseTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
         HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in RoadUSEModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

 public int insertRecord(RoadUseTypeBean roadTypeBean) {

        String query = "INSERT INTO road_use (road_use, active, created_by, remark) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, roadTypeBean.getRoad_use());
            pstmt.setString(2, roadTypeBean.getActive());
            pstmt.setString(3, roadTypeBean.getCreated_by());
            pstmt.setString(4, roadTypeBean.getRemark());
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

  public int updateRecord(RoadUseTypeBean roadTypeBean) {
        String query = "UPDATE road_use SET road_use=?, active=?, created_by=?, remark=? WHERE road_use_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, roadTypeBean.getRoad_use());
            pstmt.setString(2, roadTypeBean.getActive());
            pstmt.setString(3, roadTypeBean.getCreated_by());
            pstmt.setString(4, roadTypeBean.getRemark());
            pstmt.setInt(5, roadTypeBean.getRoad_use_id());
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

  public List<String> getRoadType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT road_use_id, road_use FROM road_use GROUP BY road_use ORDER BY road_use ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String road_use = rset.getString("road_use");
                if (road_use.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(road_use);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Road Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getRoadType ERROR inside RoadTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchRoadType) {
        String query = " SELECT Count(*) "
                + " FROM road_use "
                + " WHERE IF('" + searchRoadType + "' = '', road_use LIKE '%%',road_use =?) "
                + " ORDER BY road_use ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchRoadType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows road type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<RoadUseTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchRoadType) {
        List<RoadUseTypeBean> list = new ArrayList<RoadUseTypeBean>();

       String query = " SELECT road_use_id, road_use, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM road_use "
                + " WHERE IF('" + searchRoadType + "' = '',road_use  LIKE '%%',road_use =?) "
                + " ORDER BY road_use "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchRoadType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RoadUseTypeBean roadUse = new RoadUseTypeBean();
                roadUse.setRoad_use_id(rset.getInt("road_use_id"));
                roadUse.setRoad_use(rset.getString("road_use"));
                roadUse.setActive(rset.getString("active"));
                roadUse.setCreated_by(rset.getString("created_by"));
                roadUse.setCreated_date(rset.getString("created_date"));
                roadUse.setRemark(rset.getString("remark"));
                list.add(roadUse);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

      public List<RoadUseTypeBean> showAllData(String searchRoadType) {
        List<RoadUseTypeBean> listAll = new ArrayList<RoadUseTypeBean>();

       String query = " SELECT road_use_id, road_use, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM road_use "
                + " WHERE IF('" + searchRoadType + "' = '',road_use  LIKE '%%',road_use =?) "
                + " ORDER BY road_use ";
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchRoadType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RoadUseTypeBean roadUse = new RoadUseTypeBean();
                roadUse.setRoad_use_id(rset.getInt("road_use_id"));
                roadUse.setRoad_use(rset.getString("road_use"));
                roadUse.setActive(rset.getString("active"));
                roadUse.setCreated_by(rset.getString("created_by"));
                roadUse.setCreated_date(rset.getString("created_date"));
                roadUse.setRemark(rset.getString("remark"));
                listAll.add(roadUse);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

  public int deleteRecord(int road_type_id) {
        String query = "DELETE FROM road_use WHERE road_use_id=" + road_type_id;
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
            System.out.println("Error inside closeConnection road_useTypeModel:" + e);
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
