/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.WardTypeBean;
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
public class WardTypeModel {

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
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<WardTypeBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
         //HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in WardTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
    public List<String> getCityType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT city_name FROM city GROUP BY city_name ORDER BY city_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("city_name");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getCityType ERROR inside WardTypeModel - " + e);
        }
        return list;
    }

    public List<String> getWardType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_no FROM ward GROUP BY ward_no ORDER BY ward_no ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no = rset.getString("ward_no");
                if (ward_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such ward No exists.");
            }
        } catch (Exception e) {
            System.out.println("getWardType ERROR inside WardTypeModel - " + e);
        }
        return list;
    }

    public int getCityId(String city_name) {
        int city_id = 0;
        String query = " SELECT city_id FROM city WHERE city_name = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("city_id");
        } catch (Exception e) {
            System.out.println("WardTypeModel getCityId() Error: " + e);
        }
        return city_id;
    }

    private int getMaxId() {
        int ward_id = 0;
        String query = "SELECT MAX(ward_id) AS new_ward_id FROM ward ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                ward_id = rset.getInt("new_ward_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ward_id;
    }
   private int getRevNo() {
        int ward_rev_no = 0;
        String query = "SELECT MAX(ward_rev_no)  FROM ward ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                ward_rev_no = rset.getInt("ward_rev_no");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ward_rev_no;
    }
    public int insertRecord(WardTypeBean wardTypeBean) {

        String query = "INSERT INTO ward (ward_no,created_by, remark, city_id, ward_id) "
                + " VALUES (?,?,?,?,?) ";


        int count_ward_no=0;

        int ward_id = getMaxId();
        int rowsAffected = 0;
        try {

             count_ward_no=countWardNo(wardTypeBean.getWard_no(),wardTypeBean.getCity_id()) ;


            if(count_ward_no>0){
            System.out.println("Ward no "+wardTypeBean.getWard_no()+" already  exist for "+wardTypeBean.getCity_name());
          
            }else{

              java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, wardTypeBean.getWard_no());
         //   pstmt.setString(2, wardTypeBean.getWard_name());
            pstmt.setString(2, wardTypeBean.getCreated_by());
            pstmt.setString(3, wardTypeBean.getRemark());
            pstmt.setInt(4, wardTypeBean.getCity_id());
            pstmt.setInt(5, ward_id + 1);
            //   pstmt.setInt(7, wardTypeBean.getWard_rev_no());
            //  pstmt.setString(7, wardTypeBean.getActive());
            rowsAffected = pstmt.executeUpdate();

            }
          } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0 && count_ward_no==0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        }
        else if(count_ward_no > 0)
        {
             message = "  Ward no "+wardTypeBean.getWard_no()+ " already  exist for "+wardTypeBean.getCity_name();
             msgBgColor = COLOR_ERROR;

          }
        else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int reviseRecord(WardTypeBean wardTypeBean) {
        String query1 = "INSERT INTO ward (ward_no,created_by, remark, city_id, ward_id, ward_rev_no) "
                + " VALUES (?,?,?,?,?,?) ";
        String query2 = " UPDATE ward SET active='Revised'"
                + " WHERE ward_id = ? and ward_rev_no = ? ";
       //   int ward_rev_no = getRevNo();
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, wardTypeBean.getWard_no());
        //    pstmt.setString(2, wardTypeBean.getWard_name());
            pstmt.setString(2, wardTypeBean.getCreated_by());
            pstmt.setString(3, wardTypeBean.getRemark());
            pstmt.setInt(4, wardTypeBean.getCity_id());
            pstmt.setInt(5, wardTypeBean.getWard_id());
            pstmt.setInt(6, wardTypeBean.getWard_rev_no() + 1);

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
              
                pstmt = connection.prepareStatement(query2);
                pstmt.setInt(1, wardTypeBean.getWard_id());
                pstmt.setInt(2, wardTypeBean.getWard_rev_no());
                rowsAffected = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("error while updating record........." + e);
        }
        if (rowsAffected > 0) {

            message = "Record Revised successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot revise the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int getNoOfRows(String searchCityType, String ward_no) {
        String query =" SELECT count(*) "
                + " FROM city c, ward w "
                + " WHERE w.city_id= c.city_id  "
                + " AND IF('" + searchCityType + "' = '', c.city_name LIKE '%%', c.city_name =? ) "
                + " AND IF('" + ward_no + "' = '', w.ward_no LIKE '%%', w.ward_no =?) ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchCityType);
            stmt.setString(2, ward_no);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Ward model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }


        public int countWardNo(String ward_no,int city_id) {
        String query = " select  count(*) from ward   where ward_no=? and  city_id=?  and active='Active'";

        int noOfRows = 0;
        try {
           PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ward_no);
            stmt.setInt(2, city_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside countWardNo Ward model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<WardTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCityType, String searchWardType) {
        List<WardTypeBean> list = new ArrayList<WardTypeBean>();
        /*ward_no, ward_name, created_date, created_by, remark, city_id, ward_id, ward_rev_no, active; */
        String query = " SELECT w.ward_id, w.ward_rev_no, w.ward_no,DATE_FORMAT(w.created_date,'%d-%m-%Y') AS created_date, w.created_by, w.remark, c.city_name, c.pin_code, c.std_code,w.active "
                + " FROM city c, ward w "
                + " WHERE w.city_id= c.city_id  "
                + " AND IF('" + searchCityType + "' = '', c.city_name LIKE '%%', c.city_name =? ) "
                + " AND IF('" + searchWardType + "' = '', w.ward_no LIKE '%%', w.ward_no =?) "
                + " ORDER BY w.ward_no "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCityType);
            pstmt.setString(2, searchWardType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();
                wardType.setWard_id(rset.getInt("ward_id"));
                wardType.setWard_rev_no(rset.getInt("ward_rev_no"));
                wardType.setWard_no(rset.getString("ward_no"));
               // wardType.setWard_name(rset.getString("ward_name"));
                wardType.setCity_name(rset.getString("city_name"));
                wardType.setStd_code(rset.getString("std_code"));
                wardType.setPin_code(rset.getString("pin_code"));
                wardType.setCreated_date(rset.getString("created_date"));
                wardType.setCreated_by(rset.getString("created_by"));
                wardType.setRemark(rset.getString("remark"));
                wardType.setActive(rset.getString("active"));
                list.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
public List<WardTypeBean> showAllData(String searchCityType, String searchWardType) {
        List<WardTypeBean> list = new ArrayList<WardTypeBean>();
        /*ward_no, ward_name, created_date, created_by, remark, city_id, ward_id, ward_rev_no, active; */
        String query = " SELECT w.ward_id, w.ward_rev_no, w.ward_no,DATE_FORMAT(w.created_date,'%d-%m-%Y') AS created_date, w.created_by, w.remark, c.city_name, c.pin_code, c.std_code,w.active "
                + " FROM city c, ward w "
                + " WHERE w.city_id= c.city_id  "
                + " AND IF('" + searchCityType + "' = '', c.city_name LIKE '%%', c.city_name =? ) "
                + " AND IF('" + searchWardType + "' = '', w.ward_no LIKE '%%', w.ward_no =?) "
                + " ORDER BY w.ward_no ";
             //   + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCityType);
            pstmt.setString(2, searchWardType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();
                wardType.setWard_id(rset.getInt("ward_id"));
                wardType.setWard_rev_no(rset.getInt("ward_rev_no"));
                wardType.setWard_no(rset.getString("ward_no"));
             //   wardType.setWard_name(rset.getString("ward_name"));
                wardType.setCity_name(rset.getString("city_name"));
                wardType.setStd_code(rset.getString("std_code"));
                wardType.setPin_code(rset.getString("pin_code"));
                wardType.setCreated_date(rset.getString("created_date"));
                wardType.setCreated_by(rset.getString("created_by"));
                wardType.setRemark(rset.getString("remark"));
                wardType.setActive(rset.getString("active"));
                list.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
   public int cancelRecord(int ward_id, int ward_rev_no) {
        String query = "UPDATE ward SET active='Cancelled' WHERE ward_id = "+ ward_id
                + " and ward_rev_no = "+ ward_rev_no ;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record cancelled successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
  /* public int cancelRecord(WardTypeBean wd) {
        String query = "UPDATE ward SET active='N' WHERE ward_id = ? "
                + " and ward_rev_no = ? " ;
        int rowsAffected = 0;
        try {
             PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, wd.getWard_id());
                pstmt.setInt(2, wd.getWard_rev_no());
                rowsAffected = pstmt.executeUpdate();
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
    }*/


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
