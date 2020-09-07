/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;
import com.survey.tableClasses.StatusTypeBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/**
 *
 * @author Administrator
 */
public class StatusModel {
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
            System.out.println("Status Model setConnection() Error: " + e);
        }
    }
public byte[] generateMapReport(String jrxmlFilePath, List<StatusTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in StatusModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(StatusTypeBean statusTypeBean) {

        String query = "INSERT INTO status (status_type, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, statusTypeBean.getStatus_type());
            pstmt.setString(2, statusTypeBean.getRemark());
          
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

  public int updateRecord(StatusTypeBean statusTypeBean) {
        String query = "UPDATE status SET status_type=?, remark=? WHERE status_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, statusTypeBean.getStatus_type());
            pstmt.setString(2, statusTypeBean.getRemark());
            pstmt.setInt(4, statusTypeBean.getStatus_id());
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

  public List<String> getStatusType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT status_id, status_type FROM status GROUP BY status_type ORDER BY status_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String status_type = rset.getString("status_type");
                if (status_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(status_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getDivisionType ERROR inside StatusModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchDivisionType) {
        String query = " SELECT Count(*) "
                + " FROM status "
                + " WHERE IF('" + searchDivisionType + "' = '', status_type LIKE '%%',status_type =?) "
                + " ORDER BY status_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchDivisionType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows division type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }
  
    public List<StatusTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchDivisionType) {
        List<StatusTypeBean> list = new ArrayList<StatusTypeBean>();

       String query = " SELECT d.status_id, status_type, remark "
                + " FROM status as d "
                + "WHERE IF('" + searchDivisionType + "' = '',status_type  LIKE '%%',status_type =?) "
                + " ORDER BY status_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchDivisionType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                StatusTypeBean statusTypeBean = new StatusTypeBean();
                statusTypeBean.setStatus_id(rset.getInt("status_id"));
                statusTypeBean.setStatus_type(rset.getString("status_type"));
                statusTypeBean.setRemark(rset.getString("remark"));
                list.add(statusTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
   public List<StatusTypeBean> showAllData(String searchDivisionType) {
        List<StatusTypeBean> listAll = new ArrayList<StatusTypeBean>();

       String query =  "SELECT d.status_id, status_type, remark "
                + " FROM status as d "
                + "WHERE IF('" + searchDivisionType + "' = '',status_type  LIKE '%%',status_type =?) "
                + " ORDER BY status_type   ";
             //   + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchDivisionType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                StatusTypeBean statusTypeBean = new StatusTypeBean();
                 statusTypeBean.setStatus_id(rset.getInt("status_id"));
                statusTypeBean.setStatus_type(rset.getString("status_type"));
                statusTypeBean.setRemark(rset.getString("remark"));
               
                listAll.add(statusTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

  public int deleteRecord(int status_id) {
        String query = "DELETE FROM status WHERE status_id=" + status_id;
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
