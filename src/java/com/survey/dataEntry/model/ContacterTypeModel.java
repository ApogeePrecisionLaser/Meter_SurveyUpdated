/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.mysql.jdbc.Connection;
import com.survey.tableClasses.ContacterTypeBean;
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
public class ContacterTypeModel {
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
            System.out.println("ContacterTypeModel setConnection() Error: " + e);
        }
    }
public byte[] generateMapReport(String jrxmlFilePath, List<ContacterTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in ContacterTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(ContacterTypeBean fuseTypeBean) {

        String query = "INSERT INTO contacter (contacter_type, created_by, remark) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fuseTypeBean.getContacter_type());
            pstmt.setString(2, fuseTypeBean.getCreated_by());
            pstmt.setString(3, fuseTypeBean.getRemark());
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

  public int updateRecord(ContacterTypeBean contacterTypeBean) {
        String query = "UPDATE contacter SET contacter_type=?, created_by=?, remark=? WHERE contacter_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, contacterTypeBean.getContacter_type());
            pstmt.setString(2, contacterTypeBean.getCreated_by());
            pstmt.setString(3, contacterTypeBean.getRemark());
            pstmt.setInt(4, contacterTypeBean.getContacter_id());
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

  public List<String> getContacterType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT contacter_id, contacter_type FROM contacter GROUP BY contacter_type ORDER BY contacter_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String contacter_type = rset.getString("contacter_type");
                if (contacter_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(contacter_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Contacter exists.");
            }
        } catch (Exception e) {
            System.out.println("getContacterType ERROR inside ContacterTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchContacterType) {
        String query = " SELECT Count(*) "
                + " FROM contacter "
                + " WHERE IF('" + searchContacterType + "' = '', contacter_type LIKE '%%',contacter_type =?) "
                + " ORDER BY contacter_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchContacterType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Contacter type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<ContacterTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchContacterType) {
        List<ContacterTypeBean> list = new ArrayList<ContacterTypeBean>();

       String query = " SELECT contacter_id, contacter_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM contacter "
                + " WHERE IF('" + searchContacterType + "' = '',contacter_type  LIKE '%%',contacter_type =?) "
                + " ORDER BY contacter_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchContacterType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ContacterTypeBean contacterType = new ContacterTypeBean();
                contacterType.setContacter_id(rset.getInt("contacter_id"));
                contacterType.setContacter_type(rset.getString("contacter_type"));
                contacterType.setCreated_by(rset.getString("created_by"));
                contacterType.setCreated_date(rset.getString("created_date"));
                contacterType.setRemark(rset.getString("remark"));
                list.add(contacterType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
 public List<ContacterTypeBean>showAllData(String searchContacterType) {
        List<ContacterTypeBean> list = new ArrayList<ContacterTypeBean>();

       String query = " SELECT contacter_id, contacter_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM contacter "
                + " WHERE IF('" + searchContacterType + "' = '',contacter_type  LIKE '%%',contacter_type =?) "
                + " ORDER BY contacter_type ";
              //  + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchContacterType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ContacterTypeBean contacterType = new ContacterTypeBean();
                contacterType.setContacter_id(rset.getInt("contacter_id"));
                contacterType.setContacter_type(rset.getString("contacter_type"));
                contacterType.setCreated_by(rset.getString("created_by"));
                contacterType.setCreated_date(rset.getString("created_date"));
                contacterType.setRemark(rset.getString("remark"));
                list.add(contacterType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    public int deleteRecord(int contacter_id) {
        String query = "DELETE FROM contacter WHERE contacter_id=" + contacter_id;
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
            System.out.println("Error inside closeConnection ContacterTypeModel:" + e);
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
