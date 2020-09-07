/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.mysql.jdbc.Connection;
import com.survey.tableClasses.TimerTypeBean;
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
public class TimerTypeModel {
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
            System.out.println("TimerTypeModel setConnection() Error: " + e);
        }
    }
public byte[] generateMapReport(String jrxmlFilePath, List<TimerTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in TimerTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(TimerTypeBean timerTypeBean) {

        String query = "INSERT INTO timer (timer_type, created_by, remark) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, timerTypeBean.getTimer_type());
            pstmt.setString(2, timerTypeBean.getCreated_by());
            pstmt.setString(3, timerTypeBean.getRemark());
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

  public int updateRecord(TimerTypeBean timerTypeBean) {
        String query = "UPDATE timer SET timer_type=?, created_by=?, remark=? WHERE timer_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, timerTypeBean.getTimer_type());
            pstmt.setString(2, timerTypeBean.getCreated_by());
            pstmt.setString(3, timerTypeBean.getRemark());
            pstmt.setInt(4, timerTypeBean.getTimer_id());
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

  public List<String> getTimerType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT timer_id, timer_type FROM timer GROUP BY timer_type ORDER BY timer_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String timer_type = rset.getString("timer_type");
                if (timer_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(timer_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Timer exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimerType ERROR inside TimerTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchTimerType) {
        String query = " SELECT Count(*) "
                + " FROM timer "
                + " WHERE IF('" + searchTimerType + "' = '', timer_type LIKE '%%',timer_type =?) "
                + " ORDER BY timer_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchTimerType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Timer type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<TimerTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchTimerType) {
        List<TimerTypeBean> list = new ArrayList<TimerTypeBean>();

       String query = " SELECT timer_id, timer_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM timer "
                + " WHERE IF('" + searchTimerType + "' = '',timer_type  LIKE '%%',timer_type =?) "
                + " ORDER BY timer_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchTimerType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TimerTypeBean timerType = new TimerTypeBean();
                timerType.setTimer_id(rset.getInt("timer_id"));
                timerType.setTimer_type(rset.getString("timer_type"));
                timerType.setCreated_by(rset.getString("created_by"));
                timerType.setCreated_date(rset.getString("created_date"));
                timerType.setRemark(rset.getString("remark"));
                list.add(timerType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    public List<TimerTypeBean> showAllData(String searchTimerType) {
        List<TimerTypeBean> list = new ArrayList<TimerTypeBean>();

       String query = " SELECT timer_id, timer_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM timer "
                + " WHERE IF('" + searchTimerType + "' = '',timer_type  LIKE '%%',timer_type =?) "
                + " ORDER BY timer_type ";
              //  + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchTimerType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TimerTypeBean timerType = new TimerTypeBean();
                timerType.setTimer_id(rset.getInt("timer_id"));
                timerType.setTimer_type(rset.getString("timer_type"));
                timerType.setCreated_by(rset.getString("created_by"));
                timerType.setCreated_date(rset.getString("created_date"));
                timerType.setRemark(rset.getString("remark"));
                list.add(timerType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
  public int deleteRecord(int timer_id) {
        String query = "DELETE FROM timer WHERE timer_id=" + timer_id;
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
            System.out.println("Error inside closeConnection TimerTypeModel:" + e);
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
