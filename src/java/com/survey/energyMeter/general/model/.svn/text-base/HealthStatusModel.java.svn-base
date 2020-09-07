/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.general.model;

import com.survey.energyMeter.general.tableClasses.HealthStatusBean;
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
 * @author jpss
 */
public class HealthStatusModel {
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
       private String msgBgColor;

       public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
    private final String COLOR_OK = "yellow";
   private final String COLOR_ERROR = "red";


       public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= (Connection) DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",db_username,db_password);
            System.out.println("connected - "+connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

       public byte[] generateRecordList(String jrxmlFilePath,List list, String searchHealthStatus) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                try {
                    mymap.put("healthStatus", searchHealthStatus.equals("")?"All":searchHealthStatus);
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, jrBean);
                } catch (Exception e) {
                    System.out.println("HealthStatusModel generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

        public int deleteRecord(int health_status_id) {
            //String query1 = " DELETE FROM mapping WHERE sID = " +sID;
            String query2 = " DELETE FROM health_status WHERE health_status_id = " +health_status_id;
            int rowsAffected = 0;
            try {
              //  rowsAffected = connection.prepareStatement(query1).executeUpdate();
                rowsAffected = connection.prepareStatement(query2).executeUpdate();
            } catch (Exception e) {
                System.out.println("HealthStatusModel deleteRecord() Error: " + e);
            }
            if (rowsAffected > 0) {

                message = "Record deleted successfully......";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot delete the record, it is used in another GUI.";
                msgBgColor = COLOR_ERROR;
            }
            return rowsAffected;
        }
          

    public int updateRecord(HealthStatusBean healthStatusBean) {
                String query = " UPDATE health_status SET  health_status=?, health_status_value=?, remark=?  WHERE health_status_id = ? ";
                int rowsAffected = 0;
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

                    pstmt.setString(1, healthStatusBean.getHealth_statusList()[0]);
                    pstmt.setString(2, healthStatusBean.getHealth_status_valueList()[0]);
                    pstmt.setString(3, healthStatusBean.getRemarkList()[0]);
                    pstmt.setInt(4, Integer.parseInt(healthStatusBean.getHealth_status_idList()[0]));
                    rowsAffected = pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("HealthStatusModel updateRecord() Error: " + e);
                }
                if (rowsAffected > 0) {
                    message = "Record updated successfully......";
                    msgBgColor = COLOR_OK;
                } else {
                    message = "Cannot update the record, some error......";
                    msgBgColor = COLOR_ERROR;
                }
                return rowsAffected;
            }

     public int insertMultipleRecord(HealthStatusBean healthStatusBean) {
        String query = "INSERT INTO health_status (health_status , health_status_value, remark) "
                + " VALUES(?, ?, ? ) ";
        int rowsAffected = 0;
        try {
            String[] healthStatus = healthStatusBean.getHealth_statusList();
            for (int i = 0; i < healthStatus.length; i++) {
                 if (healthStatusBean.getHealth_status_idList()[i].equals("1")){
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, healthStatusBean.getHealth_statusList()[i]);
                    pstmt.setString(2, healthStatusBean.getHealth_status_valueList()[i]);
                    pstmt.setString(3, healthStatusBean.getRemarkList()[i]);
                    rowsAffected = pstmt.executeUpdate();
                 }
            }
        } catch (Exception e) {
            System.out.println("HealthStatus Model insertMultipleRecord() Error: " + e);
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
       

    public List<HealthStatusBean> showData(int lowerLimit, int noOfRowsToDisplay , String searchHealthStatus) {
        List<HealthStatusBean> list = new ArrayList<HealthStatusBean>();
        String query = " SELECT health_status_id, health_status, health_status_value, remark "
                + " FROM health_status "
                + " WHERE IF('" + searchHealthStatus + "' = '', health_status LIKE '%%', health_status=?) "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            //System.out.println(query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchHealthStatus);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                HealthStatusBean healthStatusBean = new HealthStatusBean();
                healthStatusBean.setHealth_status_id(rset.getInt("health_status_id"));
                healthStatusBean.setHealth_status(rset.getString("health_status"));
                healthStatusBean.setHealth_status_value(rset.getString("health_status_value"));
                healthStatusBean.setRemark(rset.getString("remark"));
                list.add(healthStatusBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
        public List<HealthStatusBean> showAllData() {
        List<HealthStatusBean> list = new ArrayList<HealthStatusBean>();

        String query = " SELECT health_status_id, health_status, health_status_value, remark "
                + " FROM health_status ";
                //+ " WHERE IF('" + searchStatusType + "' = '', status_type LIKE '%%', status_type =?) "
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchStatusType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                HealthStatusBean healthStatusBean = new HealthStatusBean();
                healthStatusBean.setHealth_status_id(rset.getInt("health_status_id"));
                healthStatusBean.setHealth_status(rset.getString("health_status"));
                healthStatusBean.setHealth_status_value(rset.getString("health_status_value"));
                healthStatusBean.setRemark(rset.getString("remark"));
                list.add(healthStatusBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchHealthStatus) {
        String query = " SELECT count(*) "
                + " FROM health_status "
                + " WHERE IF('" + searchHealthStatus + "' = '', health_status LIKE '%%', health_status=?) "
                + " ORDER BY health_status ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchHealthStatus);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows HealthStatusModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<String> getHealthStatus(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT health_status_id, health_status "
                + " FROM health_status GROUP BY health_status ORDER BY health_status";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String health_status = rset.getString("health_status");
                if (health_status.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(health_status);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Health Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getHealthStatus ERROR inside HealthStatusModel - " + e);
        }
        return list;
    }


        public Connection getConnection() {
        return connection;
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
            System.out.println("HealthStatusModel closeConnection() Error: " + e);
        }
    }

}
