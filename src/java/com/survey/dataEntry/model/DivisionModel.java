/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;
import com.survey.tableClasses.DivisionTypeBean;
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
public class DivisionModel {
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
public byte[] generateMapReport(String jrxmlFilePath, List<DivisionTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
    //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in DivisionModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public int insertRecord(DivisionTypeBean divisionTypeBean) {

        String query = "INSERT INTO division_m (division_name_m, description, circle_id) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, divisionTypeBean.getDivision_name_m());
            pstmt.setString(2, divisionTypeBean.getDescription());
            pstmt.setInt(3, divisionTypeBean.getCircle_id());
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

  public int updateRecord(DivisionTypeBean divisionTypeBean) {
        String query = "UPDATE division_m SET division_name_m=?, description=?, circle_id=? WHERE division_id_m=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, divisionTypeBean.getDivision_name_m());
            pstmt.setString(2, divisionTypeBean.getDescription());
            pstmt.setInt(3, divisionTypeBean.getCircle_id());
            pstmt.setInt(4, divisionTypeBean.getDivision_id_m());
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

  public List<String> getDivisionType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_id_m, division_name_m FROM division_m GROUP BY division_name_m ORDER BY division_name_m ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_name_m = rset.getString("division_name_m");
                if (division_name_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_name_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getDivisionType ERROR inside DivisionModel - " + e);
        }
        return list;
    }
public List<String> getCircleType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT circle_name FROM circle GROUP BY circle_name ORDER BY circle_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_name_m = rset.getString("circle_name");
                if (division_name_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_name_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getCircleType ERROR inside DivisionModel - " + e);
        }
        return list;
    }
   public int getNoOfRows(String searchDivisionType) {
        String query = " SELECT Count(*) "
                + " FROM division_m "
                + " WHERE IF('" + searchDivisionType + "' = '', division_name_m LIKE '%%',division_name_m =?) "
                + " ORDER BY division_name_m ";
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
  public int getCircleId(String circle_type) {
        String query = " select circle_id from circle where circle_name='"+circle_type+"'";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
         
            ResultSet rs = stmt.executeQuery();
         if(rs.next()){
         noOfRows=rs.getInt("circle_id");
         }
        } catch (Exception e) {
            System.out.println("Error inside getCircleId division type model" + e);
        }
       
        return noOfRows;
    }
    public List<DivisionTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchDivisionType) {
        List<DivisionTypeBean> list = new ArrayList<DivisionTypeBean>();

       String query = " SELECT d.division_id_m, division_name_m, description, c.circle_name "
                + " FROM division_m as d,circle as c"
                + " WHERE active='Y'  and d.circle_id=c.circle_id "
                + "AND IF('" + searchDivisionType + "' = '',division_name_m  LIKE '%%',division_name_m =?) "
                + " ORDER BY division_name_m "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchDivisionType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DivisionTypeBean divisionTypeBean = new DivisionTypeBean();
                divisionTypeBean.setDivision_id_m(rset.getInt("division_id_m"));
                divisionTypeBean.setDivision_name_m(rset.getString("division_name_m"));
                divisionTypeBean.setDescription(rset.getString("description"));
                divisionTypeBean.setCircle_name(rset.getString("circle_name"));
                list.add(divisionTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
   public List<DivisionTypeBean> showAllData(String searchDivisionType) {
        List<DivisionTypeBean> listAll = new ArrayList<DivisionTypeBean>();

       String query =  "SELECT d.division_id_m, division_name_m, description, c.circle_name "
                + " FROM division_m as d,circle as c"
                + " WHERE active='Y'  and d.circle_id=c.circle_id "
                + "AND IF('" + searchDivisionType + "' = '',division_name_m  LIKE '%%',division_name_m =?) "
                + " ORDER BY division_name_m  ";
             //   + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchDivisionType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DivisionTypeBean divisionTypeBean = new DivisionTypeBean();
                divisionTypeBean.setDivision_id_m(rset.getInt("division_id_m"));
                divisionTypeBean.setDivision_name_m(rset.getString("division_name_m"));
                divisionTypeBean.setDescription(rset.getString("description"));
                divisionTypeBean.setCircle_name(rset.getString("circle_name"));
               
                listAll.add(divisionTypeBean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

  public int deleteRecord(int fuse_id) {
        String query = "DELETE FROM division_m WHERE division_id_m=" + fuse_id;
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
