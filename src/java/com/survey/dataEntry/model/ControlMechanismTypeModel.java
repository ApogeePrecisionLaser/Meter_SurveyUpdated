/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.ControlMechanismTypeBean;
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
public class ControlMechanismTypeModel {
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
public byte[] generateMapReport(String jrxmlFilePath, List<ControlMechanismTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
         //HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in WardTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(ControlMechanismTypeBean controlTypeBean) {

        String query = "INSERT INTO control_mechanism (control_mechanism_type, created_date, remark) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, controlTypeBean.getControl_mechanism_type());
            pstmt.setString(2, controlTypeBean.getCreated_date());
            pstmt.setString(3, controlTypeBean.getRemark());

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

  public int updateRecord(ControlMechanismTypeBean wattageTypeBean) {
        String query = "UPDATE control_mechanism SET control_mechanism_type=?, remark=? WHERE control_mechanism_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, wattageTypeBean.getControl_mechanism_type());
            pstmt.setString(2, wattageTypeBean.getRemark());
            pstmt.setInt(3, wattageTypeBean.getControl_mechanism_id());
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

  public List<String> getMechanismType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT control_mechanism_id, control_mechanism_type FROM control_mechanism GROUP BY control_mechanism_type ORDER BY control_mechanism_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mechanism_type = rset.getString("control_mechanism_type");
                if (mechanism_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mechanism_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Control Mechanism Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getcontrol_mechanismType ERROR inside control_mechanismTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchMechanismType) {
        String query = " SELECT Count(*) "
                + " FROM control_mechanism "
                + " WHERE IF('" + searchMechanismType + "' = '', control_mechanism_type LIKE '%%',control_mechanism_type =?) "
                + " ORDER BY control_mechanism_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchMechanismType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows control_mechanism type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<ControlMechanismTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchMechanismType) {
        List<ControlMechanismTypeBean> list = new ArrayList<ControlMechanismTypeBean>();

       String query = " SELECT control_mechanism_id, control_mechanism_type, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM control_mechanism "
                + " WHERE IF('" + searchMechanismType + "' = '',control_mechanism_type  LIKE '%%',control_mechanism_type =?) "
                + " ORDER BY control_mechanism_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchMechanismType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ControlMechanismTypeBean mechanismType = new ControlMechanismTypeBean();
                mechanismType.setControl_mechanism_id(rset.getInt("control_mechanism_id"));
                mechanismType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                mechanismType.setCreated_date(rset.getString("created_date"));
                mechanismType.setRemark(rset.getString("remark"));
                list.add(mechanismType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
  public List<ControlMechanismTypeBean> showAllData(String searchMechanismType) {
        List<ControlMechanismTypeBean> list = new ArrayList<ControlMechanismTypeBean>();

       String query = " SELECT control_mechanism_id, control_mechanism_type, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM control_mechanism "
                + " WHERE IF('" + searchMechanismType + "' = '',control_mechanism_type  LIKE '%%',control_mechanism_type =?) "
                + " ORDER BY control_mechanism_type ";
               // + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchMechanismType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ControlMechanismTypeBean mechanismType = new ControlMechanismTypeBean();
                mechanismType.setControl_mechanism_id(rset.getInt("control_mechanism_id"));
                mechanismType.setControl_mechanism_type(rset.getString("control_mechanism_type"));
                mechanismType.setCreated_date(rset.getString("created_date"));
                mechanismType.setRemark(rset.getString("remark"));
                list.add(mechanismType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
  public int deleteRecord(int mecahnism_type_id) {
        String query = "DELETE FROM control_mechanism WHERE control_mechanism_id=" + mecahnism_type_id;
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
            System.out.println("Error inside closeConnection control_mechanismTypeModel:" + e);
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
