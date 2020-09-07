/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.general.model;

//import com.survey.dataEntry.model.*;
import com.survey.tableClasses.TimeIintervalBean;
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
public class TimeIntervalModel {
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
  public List<String> getTimeIntervalFor(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT time_interval_for FROM time_interval  GROUP BY time_interval_for ORDER BY time_interval_for ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String Search_time_interval_for = rset.getString("time_interval_for");
                if (Search_time_interval_for.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(Search_time_interval_for);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such  Time Interval For Exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimeIntervalFor ERROR inside TimeIntervalModel - " + e);
        }
        return list;
    }

 public byte[] generateMapReport(String jrxmlFilePath, List<TimeIintervalBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
      //   HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in TimeIntervalMOdel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    } 
 public int insertRecord(TimeIintervalBean timeIintervalBean) {

        String query = "INSERT INTO time_interval (time_interval,time_interval_for,interval_type) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,Integer.parseInt(timeIintervalBean.getTime_interval()));
            pstmt.setString(2, timeIintervalBean.getTime_interval_for());
            pstmt.setString(3, timeIintervalBean.getInterval_type());
         //   pstmt.setString(4, timeIintervalBean.getRemark());
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
  public int updateRecord(TimeIintervalBean timeIintervalBean) {
        String query = "UPDATE time_interval SET time_interval=?, time_interval_for=?, interval_type=? WHERE time_interval_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
         
            pstmt.setString(1, timeIintervalBean.getTime_interval());
            pstmt.setString(2, timeIintervalBean.getTime_interval_for());
            pstmt.setString(3, timeIintervalBean.getInterval_type());
            pstmt.setInt(4, timeIintervalBean.getTime_interval_id());
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
  
    public int getNoOfRows(String searchTimeIntervalFor) {
        String query =  " SELECT count(*)"
                + " FROM time_interval "
               + " WHERE IF('" + searchTimeIntervalFor + "' = '',time_interval_for  LIKE '%%',time_interval_for ='" + searchTimeIntervalFor + "') "
                + " ORDER BY interval_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            //stmt.setString(1, searchSourceType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Light_source type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<TimeIintervalBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchTimeIntervalFor) {
        List<TimeIintervalBean> list = new ArrayList<TimeIintervalBean>();

       String query = " SELECT time_interval,time_interval_for, interval_type, time_interval_id "
                 + " FROM time_interval "
                 + " WHERE IF('" + searchTimeIntervalFor + "' = '',time_interval_for  LIKE '%%',time_interval_for ='" + searchTimeIntervalFor + "') "
                 + " ORDER BY interval_type "
                 + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
        
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TimeIintervalBean sourceType = new TimeIintervalBean();
                sourceType.setTime_interval_id(rset.getInt("time_interval_id"));

             
              int time_interval= Integer.parseInt(rset.getString("time_interval"));
                if(rset.getString("interval_type").equals("H")){
                  sourceType.setInterval_type("Hours");
                  
                  time_interval=time_interval/3600;
                  String time_interval1= String.valueOf(time_interval);
                  sourceType.setTime_interval(time_interval1);
               }
                else  if(rset.getString("interval_type").equals("M"))
                {
                  sourceType.setInterval_type("Minutes");
                  time_interval=time_interval/60;
                  String time_interval1= String.valueOf(time_interval);
                  sourceType.setTime_interval(time_interval1);

                }else
                  {
                  
                    sourceType.setInterval_type("Seconds");
                    sourceType.setTime_interval(rset.getString("time_interval"));
                }
                sourceType.setTime_interval_for(rset.getString("time_interval_for"));                               
                list.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    } 
        public List<TimeIintervalBean> showAllData(String searchTimeIntervalFor) {
        List<TimeIintervalBean> listAll = new ArrayList<TimeIintervalBean>();


       String query = " SELECT time_interval,time_interval_for, interval_type, time_interval_id "
                 + " FROM time_interval "
                 + " WHERE IF('" + searchTimeIntervalFor + "' = '',time_interval_for  LIKE '%%',time_interval_for ='" + searchTimeIntervalFor + "') "
                 + " ORDER BY interval_type ";
               //  + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TimeIintervalBean sourceType = new TimeIintervalBean();
                sourceType.setTime_interval_id(rset.getInt("time_interval_id"));


              int time_interval= Integer.parseInt(rset.getString("time_interval"));
                if(rset.getString("interval_type").equals("H")){
                  sourceType.setInterval_type("Hours");

                  time_interval=time_interval/3600;
                  String time_interval1= String.valueOf(time_interval);
                  sourceType.setTime_interval(time_interval1);
               }
                else  if(rset.getString("interval_type").equals("M"))
                {
                  sourceType.setInterval_type("Minutes");
                  time_interval=time_interval/60;
                  String time_interval1= String.valueOf(time_interval);
                  sourceType.setTime_interval(time_interval1);

                }else
                  {

                    sourceType.setInterval_type("Seconds");
                    sourceType.setTime_interval(rset.getString("time_interval"));
                }
                sourceType.setTime_interval_for(rset.getString("time_interval_for"));              
                listAll.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }  
  public int deleteRecord(int time_interval_id) {
        String query = "DELETE FROM time_interval WHERE time_interval_id=" + time_interval_id;
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
