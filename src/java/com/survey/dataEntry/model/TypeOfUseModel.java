/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.survey.tableClasses.TypeOfUseBean;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class TypeOfUseModel {
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
public byte[] generateMapReport(String jrxmlFilePath, List<TypeOfUseBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in FuseTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(TypeOfUseBean typeOfUseBean) {

        String query = "INSERT INTO type_of_use (type_of_use_name, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, typeOfUseBean.getTypeOfUseName());
            pstmt.setString(2, typeOfUseBean.getRemark());
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

  public int updateRecord(TypeOfUseBean typeOfUseBean) {
        String query = "UPDATE type_of_use SET type_of_use_name=?, remark=? WHERE type_of_use_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt =  connection.prepareStatement(query);
            pstmt.setString(1, typeOfUseBean.getTypeOfUseName());
            pstmt.setString(2, typeOfUseBean.getRemark());
            pstmt.setInt(3, typeOfUseBean.getTypeOfUseiD());
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

  public List<String> getTypeOfUse(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select type_of_use_name "
                       +" from type_of_use GROUP BY type_of_use_name ORDER BY type_of_use_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String fuse_type = rset.getString("type_of_use_name");
                if (fuse_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(fuse_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such type_of_use_name exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside FuseTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchTypeOfUse) {
        String query = " SELECT Count(*) "
                +" from type_of_use "
                + " WHERE IF('" + searchTypeOfUse + "' = '',type_of_use_name  LIKE '%%',type_of_use_name =?) "
                + " ORDER BY type_of_use_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchTypeOfUse);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<TypeOfUseBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchTypeOfUse) {
        List<TypeOfUseBean> list = new ArrayList<TypeOfUseBean>();

       String query = " select type_of_use_id,type_of_use_name,remark "
                    +" from type_of_use "
                + " WHERE IF('" + searchTypeOfUse + "' = '',type_of_use_name  LIKE '%%',type_of_use_name =?) "
                + " ORDER BY type_of_use_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, searchTypeOfUse);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TypeOfUseBean typeOfUseBean = new TypeOfUseBean();
                typeOfUseBean.setTypeOfUseiD(rset.getInt("type_of_use_id"));
                typeOfUseBean.setTypeOfUseName(rset.getString("type_of_use_name"));

                typeOfUseBean.setRemark(rset.getString("remark"));
                list.add(typeOfUseBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
   public List<TypeOfUseBean> showAllData(String searchTypeOfUse) {
        List<TypeOfUseBean> listAll = new ArrayList<TypeOfUseBean>();

       String query = " SELECT fuse_id, fuse_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM fuse "
                + " WHERE IF('" + searchTypeOfUse + "' = '',fuse_type  LIKE '%%',fuse_type =?) "
                + " ORDER BY fuse_type ";
                //+ " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, searchTypeOfUse);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TypeOfUseBean fuseType = new TypeOfUseBean();
//                fuseType.setFuse_id(rset.getInt("fuse_id"));
//                fuseType.setFuse_type(rset.getString("fuse_type"));
//                fuseType.setCreated_by(rset.getString("created_by"));
//                fuseType.setCreated_date(rset.getString("created_date"));
//                fuseType.setRemark(rset.getString("remark"));
                listAll.add(fuseType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

  public int deleteRecord(int type_of_use_id) {
        String query = "DELETE FROM type_of_use WHERE type_of_use_id=" + type_of_use_id;
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
