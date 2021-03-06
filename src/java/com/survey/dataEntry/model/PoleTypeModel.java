/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.PoleTypeBean;
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
public class PoleTypeModel {
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
 public byte[] generateMapReport(String jrxmlFilePath, List<PoleTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
         //HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in PoleTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(PoleTypeBean poleTypeBean) {

        String query = "INSERT INTO pole_type (pole_type_name, active, createdby, remark) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, poleTypeBean.getPole_type());
            pstmt.setString(2, poleTypeBean.getActive());
            pstmt.setString(3, poleTypeBean.getCreated_by());
            pstmt.setString(4, poleTypeBean.getRemark());
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
  public int updateRecord(PoleTypeBean poleTypeBean) {
        String query = "UPDATE pole_type SET pole_type_name=?, active=?, createdby=?, remark=? WHERE pole_type_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, poleTypeBean.getPole_type());
            pstmt.setString(2, poleTypeBean.getActive());
            pstmt.setString(3, poleTypeBean.getCreated_by());
            pstmt.setString(4, poleTypeBean.getRemark());
            pstmt.setInt(5, poleTypeBean.getPole_type_id());
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
     public List<String> getPoleType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT pole_type_id, pole_type_name FROM pole_type GROUP BY pole_type_name ORDER BY pole_type_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_type = rset.getString("pole_type_name");
                if (pole_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such pole Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleType ERROR inside poleTypeModel - " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchPoleType) {
        String query = " SELECT Count(*) "
                + " FROM pole_type "
                + " WHERE IF('" + searchPoleType + "' = '', pole_type_name LIKE '%%',pole_type_name =?) "
                + " ORDER BY pole_type_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchPoleType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows pole type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<PoleTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchPoleType) {
        List<PoleTypeBean> list = new ArrayList<PoleTypeBean>();

       String query = " SELECT pole_type_id, pole_type_name, active, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, createdby, remark "
                + " FROM pole_type "
                + " WHERE IF('" + searchPoleType + "' = '',pole_type_name  LIKE '%%', pole_type_name =?) "
                + " ORDER BY pole_type_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchPoleType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PoleTypeBean poleType = new PoleTypeBean();
                poleType.setPole_type_id(rset.getInt("pole_type_id"));
                poleType.setPole_type(rset.getString("pole_type_name"));
                poleType.setActive(rset.getString("active"));
                poleType.setCreated_date(rset.getString("created_date"));
                poleType.setCreated_by(rset.getString("createdby"));
                poleType.setRemark(rset.getString("remark"));
                list.add(poleType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    public List<PoleTypeBean> showAllData(String searchPoleType) {
        List<PoleTypeBean> listAll = new ArrayList<PoleTypeBean>();

       String query = " SELECT pole_type_id, pole_type_name, active, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, createdby, remark "
                + " FROM pole_type "
                + " WHERE IF('" + searchPoleType + "' = '',pole_type_name  LIKE '%%', pole_type_name =?) "
                + " ORDER BY pole_type_name ";
              //  + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchPoleType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PoleTypeBean poleType = new PoleTypeBean();
                poleType.setPole_type_id(rset.getInt("pole_type_id"));
                poleType.setPole_type(rset.getString("pole_type_name"));
                poleType.setActive(rset.getString("active"));
                poleType.setCreated_date(rset.getString("created_date"));
                poleType.setCreated_by(rset.getString("createdby"));
                poleType.setRemark(rset.getString("remark"));
                listAll.add(poleType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }
  public int deleteRecord(int pole_id) {
        String query = "DELETE FROM pole_type WHERE pole_type_id=" + pole_id;
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
            System.out.println("Error inside closeConnection PoleTypeModel:" + e);
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

