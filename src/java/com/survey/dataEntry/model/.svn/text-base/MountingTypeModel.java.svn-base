/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.survey.tableClasses.MountingTypeBean;
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
public class MountingTypeModel {
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
 public byte[] generateMapReport(String jrxmlFilePath, List<MountingTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
        // HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in MountingTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
  public int insertRecord(MountingTypeBean mountingTypeBean) {

        String query = "INSERT INTO mounting_type (mounting_type, active, created_by, remark) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mountingTypeBean.getMounting_type());
            pstmt.setString(2, mountingTypeBean.getActive());
            pstmt.setString(3, mountingTypeBean.getCreated_by());
            pstmt.setString(4, mountingTypeBean.getRemark());
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
  public int updateRecord(MountingTypeBean mountingTypeBean) {
        String query = "UPDATE mounting_type SET mounting_type=?, active=?, created_by=?, remark=? WHERE mounting_type_id=? ";
      int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mountingTypeBean.getMounting_type());
            pstmt.setString(2, mountingTypeBean.getActive());
            pstmt.setString(3, mountingTypeBean.getCreated_by());
            pstmt.setString(4, mountingTypeBean.getRemark());
            pstmt.setInt(5, mountingTypeBean.getMounting_type_id());
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
     public List<String> getMountingType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT mounting_type_id, mounting_type FROM mounting_type GROUP BY mounting_type ORDER BY mounting_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mounting_type = rset.getString("mounting_type");
                if (mounting_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mounting_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such mounting Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getMountingType ERROR inside mountingTypeModel - " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchMountingType) {
        String query = " SELECT Count(*) "
                + " FROM mounting_type "
                + " WHERE IF('" + searchMountingType + "' = '', mounting_type LIKE '%%',mounting_type =?) "
                + " ORDER BY mounting_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchMountingType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows mounting type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<MountingTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchMountingType) {
        List<MountingTypeBean> list = new ArrayList<MountingTypeBean>();

       String query = " SELECT mounting_type_id, mounting_type, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM mounting_type "
                + " WHERE IF('" + searchMountingType + "' = '',mounting_type  LIKE '%%',mounting_type =?) "
                + " ORDER BY mounting_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchMountingType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                MountingTypeBean mountingType = new MountingTypeBean();
                mountingType.setMounting_type_id(rset.getInt("mounting_type_id"));
                mountingType.setMounting_type(rset.getString("mounting_type"));
                mountingType.setActive(rset.getString("active"));
                mountingType.setCreated_by(rset.getString("created_by"));
                mountingType.setCreated_date(rset.getString("created_date"));
                mountingType.setRemark(rset.getString("remark"));
                list.add(mountingType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
        public List<MountingTypeBean> showAllData(String searchMountingType) {
        List<MountingTypeBean> listAll = new ArrayList<MountingTypeBean>();

       String query = " SELECT mounting_type_id, mounting_type, active, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM mounting_type "
                + " WHERE IF('" + searchMountingType + "' = '',mounting_type  LIKE '%%',mounting_type =?) "
                + " ORDER BY mounting_type ";
                // + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchMountingType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                MountingTypeBean mountingType = new MountingTypeBean();
                mountingType.setMounting_type_id(rset.getInt("mounting_type_id"));
                mountingType.setMounting_type(rset.getString("mounting_type"));
                mountingType.setActive(rset.getString("active"));
                mountingType.setCreated_by(rset.getString("created_by"));
                mountingType.setCreated_date(rset.getString("created_date"));
                mountingType.setRemark(rset.getString("remark"));
                listAll.add(mountingType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }
    public int deleteRecord(int mounting_id) {
        String query = "DELETE FROM mounting_type WHERE mounting_type_id=" + mounting_id;
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
            System.out.println("Error inside closeConnection MountingTypeModel:" + e);
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
