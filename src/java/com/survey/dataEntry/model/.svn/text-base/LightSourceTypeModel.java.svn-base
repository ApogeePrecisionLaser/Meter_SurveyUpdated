/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.LightSourceTypeBean;
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
public class LightSourceTypeModel {
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
 public byte[] generateMapReport(String jrxmlFilePath, List<LightSourceTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
      //   HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in LightSourceModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(LightSourceTypeBean lightTypeBean) {

        String query = "INSERT INTO light_source_type (source_type, active, created_by, remark) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, lightTypeBean.getSource_type());
            pstmt.setString(2, lightTypeBean.getActive());
            pstmt.setString(3, lightTypeBean.getCreated_by());
            pstmt.setString(4, lightTypeBean.getRemark());
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
  public int updateRecord(LightSourceTypeBean lightTypeBean) {
        String query = "UPDATE light_source_type SET source_type=?, active=?, created_by=?, remark=? WHERE source_type_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, lightTypeBean.getSource_type());
            pstmt.setString(2, lightTypeBean.getActive());
            pstmt.setString(3, lightTypeBean.getCreated_by());
            pstmt.setString(4, lightTypeBean.getRemark());
            pstmt.setInt(5, lightTypeBean.getSource_type_id());
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
   public List<String> getSourceType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT source_type_id, source_type FROM light_source_type GROUP BY source_type ORDER BY source_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String source_type = rset.getString("source_type");
                if (source_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(source_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Source Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getSourceType ERROR inside source_typeTypeModel - " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchSourceType) {
        String query = " SELECT Count(*) "
                + " FROM light_source_type "
                + " WHERE IF('" + searchSourceType + "' = '', source_type LIKE '%%',source_type =?) "
                + " ORDER BY source_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchSourceType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Light_source type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<LightSourceTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchSourceType) {
        List<LightSourceTypeBean> list = new ArrayList<LightSourceTypeBean>();

       String query = " SELECT source_type_id, source_type, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM light_source_type "
                + " WHERE IF('" + searchSourceType + "' = '',source_type  LIKE '%%',source_type =?) "
                + " ORDER BY source_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchSourceType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LightSourceTypeBean sourceType = new LightSourceTypeBean();
                sourceType.setSource_type_id(rset.getInt("source_type_id"));
                sourceType.setSource_type(rset.getString("source_type"));
                sourceType.setActive(rset.getString("active"));
                sourceType.setCreated_by(rset.getString("created_by"));
                sourceType.setCreated_date(rset.getString("created_date"));
                sourceType.setRemark(rset.getString("remark"));
                list.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
        public List<LightSourceTypeBean> showAllData(String searchSourceType) {
        List<LightSourceTypeBean> listAll = new ArrayList<LightSourceTypeBean>();

       String query = " SELECT source_type_id, source_type, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM light_source_type "
                + " WHERE IF('" + searchSourceType + "' = '',source_type  LIKE '%%',source_type =?) "
                + " ORDER BY source_type ";
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchSourceType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LightSourceTypeBean sourceType = new LightSourceTypeBean();
                sourceType.setSource_type_id(rset.getInt("source_type_id"));
                sourceType.setSource_type(rset.getString("source_type"));
                sourceType.setActive(rset.getString("active"));
                sourceType.setCreated_by(rset.getString("created_by"));
                sourceType.setCreated_date(rset.getString("created_date"));
                sourceType.setRemark(rset.getString("remark"));
                listAll.add(sourceType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }
  public int deleteRecord(int source_type_id) {
        String query = "DELETE FROM light_source_type WHERE source_type_id=" + source_type_id;
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
