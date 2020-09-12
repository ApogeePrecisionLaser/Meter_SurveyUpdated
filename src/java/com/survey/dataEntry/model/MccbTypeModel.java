/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;
import java.sql.Connection;
import com.survey.tableClasses.MccbTypeBean;
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
public class MccbTypeModel {
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
            System.out.println("MccbTypeModel setConnection() Error: " + e);
        }
    }
public byte[] generateMapReport(String jrxmlFilePath, List<MccbTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in MccbTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(MccbTypeBean mccbTypeBean) {

        String query = "INSERT INTO mccb (mccb_type, created_by, remark) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mccbTypeBean.getMccb_type());
            pstmt.setString(2, mccbTypeBean.getCreated_by());
            pstmt.setString(3, mccbTypeBean.getRemark());
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

  public int updateRecord(MccbTypeBean mccbTypeBean) {
        String query = "UPDATE mccb SET mccb_type=?, created_by=?, remark=? WHERE mccb_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mccbTypeBean.getMccb_type());
            pstmt.setString(2, mccbTypeBean.getCreated_by());
            pstmt.setString(3, mccbTypeBean.getRemark());
            pstmt.setInt(4, mccbTypeBean.getMccb_id());
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

  public List<String> getMccbType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT mccb_id, mccb_type FROM mccb GROUP BY mccb_type ORDER BY mccb_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mccb_type = rset.getString("mccb_type");
                if (mccb_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mccb_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Mccb exists.");
            }
        } catch (Exception e) {
            System.out.println("getMccbType ERROR inside MccbTypeModel - " + e);
        }
        return list;
    }

   public int getNoOfRows(String searchMccbType) {
        String query = " SELECT Count(*) "
                + " FROM mccb "
                + " WHERE IF('" + searchMccbType + "' = '', mccb_type LIKE '%%',mccb_type =?) "
                + " ORDER BY mccb_type ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchMccbType);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Mccb type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<MccbTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchMccbType) {
        List<MccbTypeBean> list = new ArrayList<MccbTypeBean>();

       String query = " SELECT mccb_id, mccb_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM mccb "
                + " WHERE IF('" + searchMccbType + "' = '',mccb_type  LIKE '%%',mccb_type =?) "
                + " ORDER BY mccb_type "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchMccbType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                MccbTypeBean mccbType = new MccbTypeBean();
                mccbType.setMccb_id(rset.getInt("mccb_id"));
                mccbType.setMccb_type(rset.getString("mccb_type"));
                mccbType.setCreated_by(rset.getString("created_by"));
                mccbType.setCreated_date(rset.getString("created_date"));
                mccbType.setRemark(rset.getString("remark"));
                list.add(mccbType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<MccbTypeBean> showAllData(String searchMccbType) {
        List<MccbTypeBean> list = new ArrayList<MccbTypeBean>();

       String query = " SELECT mccb_id, mccb_type, created_by, DATE_FORMAT(created_date,'%d-%m-%Y') AS created_date, remark "
                + " FROM mccb "
                + " WHERE IF('" + searchMccbType + "' = '',mccb_type  LIKE '%%',mccb_type =?) "
                + " ORDER BY mccb_type ";
            //    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchMccbType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                MccbTypeBean mccbType = new MccbTypeBean();
                mccbType.setMccb_id(rset.getInt("mccb_id"));
                mccbType.setMccb_type(rset.getString("mccb_type"));
                mccbType.setCreated_by(rset.getString("created_by"));
                mccbType.setCreated_date(rset.getString("created_date"));
                mccbType.setRemark(rset.getString("remark"));
                list.add(mccbType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    public int deleteRecord(int mccb_id) {
        String query = "DELETE FROM mccb WHERE mccb_id=" + mccb_id;
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
            System.out.println("Error inside closeConnection MccbTypeModel:" + e);
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
