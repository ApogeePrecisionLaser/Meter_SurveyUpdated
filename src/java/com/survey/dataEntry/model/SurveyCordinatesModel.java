/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import java.sql.Connection;
import com.survey.tableClasses.SurveyCoordinatesBean;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author JPSS
 */
public class SurveyCordinatesModel {
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
            System.out.println("AraeTYpeTypeModel setConnection() Error: " + e);
        }
    }
   public byte[] generateMapReport(String jrxmlFilePath, List<SurveyCoordinatesBean> listAll) {
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
    public List<String> getWardNo(String q, String searchCity) {
        List<String> list = new ArrayList<String>();
         PreparedStatement pstmt;
        String query = "SELECT ward_no ,city_name FROM ward AS d ,city AS s "
                        + " WHERE d.city_id = s.city_id and d.active='Active'"
                        + " and city_name =? ";
                        //   + " AND IF('" + searchCity + "'='', city_name like '%%', city_name = ?) group by ward_no ";
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, searchCity);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_no = rset.getString("ward_no");
                if (ward_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Ward No exists.");
            }
        } catch (Exception e) {
            System.out.println("getWardType ERROR inside WardTypeModel - " + e);
        }
        return list;
    }
public List<String> getAreaName(String q, String searchWard, String searchCity) {
        List<String> list = new ArrayList<String>();

        String query =" SELECT area_name ,ward_no, city_name "
                + " FROM area AS a ,ward AS w, city AS c "
                + " WHERE a.ward_id = w.ward_id "
                + " AND w.city_id = c.city_id "
                + " AND IF('" + searchWard + "'='', ward_no like '%%', ward_no = ?) "
                + " AND IF('" + searchCity + "'='', city_name like '%%', city_name = ?) "
                + " Group by area_name ";
                
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWard);
            pstmt.setString(2, searchCity);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String areaName = rset.getString("area_name");
                if (areaName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(areaName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Area Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:AreaType1Model-" + e);
        }
        return list;
    }
 public int getWardId(String ward_no) {
        int ward_id = 0;
        String query = " SELECT ward_id FROM ward WHERE ward_no = ? GROUP BY ward_id ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ward_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            ward_id = rset.getInt("ward_id");
        } catch (Exception e) {
            System.out.println("WardTypeModel getWardId() Error: " + e);
        }
        return ward_id;
    }
   public int getWard_rev_no(String ward_no) {
        int ward_rev_no = 0;
        String query = " SELECT ward_rev_no FROM ward WHERE ward_no =?  and active='Active'";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ward_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            ward_rev_no = rset.getInt("ward_rev_no");
        } catch (Exception e) {
            System.out.println("WardTypeModel getWardId() Error: " + e);
        }
        return ward_rev_no;
    }

 public int insertRecord(SurveyCoordinatesBean bean) {
        String query = " INSERT INTO area(area_name, remark, ward_id,ward_rev_no,active ) VALUES(?, ?, ?, ?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, bean.getArea_name());
            pstmt.setString(2, bean.getRemark());
            pstmt.setInt(3, bean.getWard_id());
            pstmt.setInt(4, bean.getWard_rev_no());
            pstmt.setString(5, bean.getActive());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in area TYpe Model Insert() method:" + e);
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

   public int getNoOfRows(String area_name, String ward_no, String city_name) {
        int noOfRows = 0;
        try {
            String query = " SELECT COUNT(survey_cordinates_id) "
                    + " FROM survey_cordinates";
            java.sql.PreparedStatement pst = connection.prepareStatement(query);            
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = rset.getInt(1);
        } catch (Exception e) {
            System.out.println("Error in getRows() of AreaTypeModel...." + e);
        }
        return noOfRows;
    }

    public List<SurveyCoordinatesBean> showData(int lowerLimit, int noOfRowsToDisplay, String area_name, String ward_no, String city_name) {
        List<SurveyCoordinatesBean> list = new ArrayList<SurveyCoordinatesBean>();
        //PreparedStatement pstmt = null;

        String query = " SELECT survey_cordinates_id, latitude, longitude, imei_no, contact_no, device_type"
                 + " FROM survey_cordinates sc LEFT JOIN device_type dt ON dt.device_type_id = sc.device_type_id "
                 + " ORDER BY survey_cordinates_id DESC"
                + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SurveyCoordinatesBean bean = new SurveyCoordinatesBean();
                bean.setArea_id(rset.getInt("survey_cordinates_id"));
                bean.setArea_name(rset.getString("latitude"));
                bean.setWard_no(rset.getString("longitude"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }

    public List<SurveyCoordinatesBean> showDataBean(int lowerLimit, int noOfRowsToDisplay, String area_name, String ward_no, String city_name) {
        List<SurveyCoordinatesBean> list = new ArrayList<SurveyCoordinatesBean>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        int Hr = cal.get(Calendar.HOUR_OF_DAY);        
        int Min = cal.get(Calendar.MINUTE);
        int Date = cal.get(Calendar.DATE);
        int Month = cal.get(Calendar.MONTH) + 1;
        int Year = cal.get(Calendar.YEAR);
        String date = Year +"-" + Month + "-" + Date + " " + Hr + ":" + Min + ":00";
        //PreparedStatement pstmt = null;

//        String query = " SELECT survey_cordinates_id, latitude, longitude"
//                 + " FROM survey_cordinates GROUP BY imei_no ORDER BY survey_cordinates_id DESC";
        String query = "SELECT * FROM (SELECT survey_cordinates_id, latitude, longitude, imei_no "
                + " FROM survey_cordinates "//WHERE //created_date >= '"+ date +"' "
                + " ORDER BY survey_cordinates_id DESC) AS sc GROUP BY imei_no";
                //+ " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;        
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SurveyCoordinatesBean bean = new SurveyCoordinatesBean();
                bean.setArea_id(rset.getInt("survey_cordinates_id"));
                bean.setArea_name(rset.getString("latitude"));
                bean.setWard_no(rset.getString("longitude"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }

 public List<SurveyCoordinatesBean>showAllData(String area_name, String ward_no, String city_name) {
        List<SurveyCoordinatesBean> list = new ArrayList<SurveyCoordinatesBean>();
        //PreparedStatement pstmt = null;

        String query = " SELECT a.area_id, a.area_name, w.ward_no,c.city_name, a.active, a.remark,DATE_FORMAT(a.created_date,'%d-%m-%Y') AS created_date FROM city c, area a, ward w  "
                + " WHERE a.ward_id=w.ward_id "
                + " AND w.city_id=c.city_id "
                + " AND  if( '" + city_name + "'  = '' , c.city_name like '%%' , c.city_name = ? ) "
                + " AND  if( '" + ward_no + "'  = '' , w.ward_no like '%%' , w.ward_no = ? )"
                + " AND  if( '" + area_name + "'  = '' , a.area_name like '%%' , a.area_name = ? )";
              //  + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            pstmt.setString(2, ward_no);
            pstmt.setString(3, area_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SurveyCoordinatesBean bean = new SurveyCoordinatesBean();
                bean.setArea_id(rset.getInt("area_id"));
                bean.setArea_name(rset.getString("area_name"));
                bean.setWard_no(rset.getString("ward_no"));
             //   bean.setWard_name(rset.getString("ward_name"));
                bean.setCity_name(rset.getString("city_name"));
                bean.setCreated_date(rset.getString("created_date"));
                bean.setRemark(rset.getString("remark"));
                bean.setActive(rset.getString("active"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }
 public int updateRecord(SurveyCoordinatesBean SurveyCoordinatesBean) {
        String query = " UPDATE area SET  area_name = ?, remark=?, ward_id=?, active=? WHERE area_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, SurveyCoordinatesBean.getArea_name());
            pstmt.setString(2, SurveyCoordinatesBean.getRemark());
            pstmt.setInt(3, SurveyCoordinatesBean.getWard_id());
            pstmt.setString(4, SurveyCoordinatesBean.getActive());
            pstmt.setInt(5, SurveyCoordinatesBean.getArea_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("AreaModel updateRecord() Error: " + e);
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
 public int deleteRecord(int area_id) {
        String query = " DELETE FROM area WHERE area_id = " + area_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("AreaModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
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
