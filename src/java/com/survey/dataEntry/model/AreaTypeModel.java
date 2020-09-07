/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;

import com.mysql.jdbc.Connection;
import com.survey.tableClasses.AreaTypeBean;
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
public class AreaTypeModel {
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
   public byte[] generateMapReport(String jrxmlFilePath, List<AreaTypeBean> listAll) {
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
//    public List<String> getCityType(String q) {
//        List<String> list = new ArrayList<String>();
//        String query = " SELECT city_name FROM city GROUP BY city_name ORDER BY city_name ";
//        try {
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {    // move cursor from BOR to valid record.
//                String city_name = rset.getString("city_name");
//                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(city_name);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such City Type exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("getCityType ERROR inside WardTypeModel - " + e);
//        }
//        return list;
//    }
    public List<String> getWardNo(String q) {
        List<String> list = new ArrayList<String>();
         PreparedStatement pstmt;
        String query = "select ward_no_m "
                       +" from ward_m wm "
                       +" where wm.active='Active' ";
                        
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_no_m = rset.getString("ward_no_m");
                if (ward_no_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no_m);
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
public List<String> getSearchAreaName(String q) {
        List<String> list = new ArrayList<String>();

//        String query =" SELECT area_name ,ward_no, city_name "
//                + " FROM area AS a ,ward AS w, city AS c "
//                + " WHERE a.ward_id = w.ward_id "
//                + " AND w.city_id = c.city_id "
//                + " AND IF('" + searchWard + "'='', ward_no like '%%', ward_no = ?) "
//                + " AND IF('" + searchCity + "'='', city_name like '%%', city_name = ?) "
//                + " Group by area_name ";
        String query =" select area_name "
                      +" from area a "
                     +" where a.active='Y'";
                
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            
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

    public List<String> getSearchWardNo(String q) {
        List<String> list = new ArrayList<String>();

        String query =" select ward_no_m "
                    +" from ward_m wm,area a "
                    +" where a.ward_id_m=a.ward_id_m "
                    +" and a.active='Y' "
                    +" and wm.active='Active' group by ward_no_m ";

        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no_m = rset.getString("ward_no_m");
                if (ward_no_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no_m);
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
        int ward_id_m = 0;
        String query = " SELECT ward_id_m FROM ward_m WHERE ward_no_m = ? GROUP BY ward_id_m ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ward_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            ward_id_m = rset.getInt("ward_id_m");
        } catch (Exception e) {
            System.out.println("WardTypeModel getWardId() Error: " + e);
        }
        return ward_id_m;
    }
//   public int getWard_rev_no(String ward_no) {
//        int ward_rev_no = 0;
//        String query = " SELECT ward_rev_no FROM ward WHERE ward_no =?  and active='Active'";
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, ward_no);
//            ResultSet rset = pstmt.executeQuery();
//            rset.next();    // move cursor from BOR to valid record.
//            ward_rev_no = rset.getInt("ward_rev_no");
//        } catch (Exception e) {
//            System.out.println("WardTypeModel getWardId() Error: " + e);
//        }
//        return ward_rev_no;
//    }

 public int insertRecord(AreaTypeBean bean) {
        String query = " INSERT INTO area(area_name, remark, ward_id_m ) VALUES(?, ?, ?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, bean.getArea_name());
            pstmt.setString(2, bean.getRemark());
            pstmt.setInt(3, bean.getWard_id());
//            pstmt.setInt(4, bean.getWard_rev_no());
//            pstmt.setString(5, bean.getActive());
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

   public int getNoOfRows(String area_name, String ward_no) {
        int noOfRows = 0;
        try {
            String query = " SELECT COUNT(*) "
                    + " FROM area a, ward_m w  "
                    + " WHERE a.ward_id_m=w.ward_id_m "
                    + " AND IF ( '" + ward_no + "'  = '' , w.ward_no_m like '%%' , w.ward_no_m = ? ) "
                    + " AND IF ( '" + area_name + "'  = '' , a.area_name like '%%' , a.area_name = ? ) "
                    + " ORDER BY a.area_name ";
            java.sql.PreparedStatement pst = connection.prepareStatement(query);
            
            pst.setString(1, ward_no);
            pst.setString(2, area_name);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error in getRows() of AreaTypeModel...." + e);
        }
        return noOfRows;
    }

    public List<AreaTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String area_name, String ward_no) {
        List<AreaTypeBean> list = new ArrayList<AreaTypeBean>();
        //PreparedStatement pstmt = null;

        String query = " SELECT a.area_id, a.area_name, w.ward_no_m, a.active, a.remark,DATE_FORMAT(a.created_date,'%d-%m-%Y') AS created_date "
                       +" FROM area a, ward_m w "
                       +" WHERE a.ward_id_m=w.ward_id_m and a.active='Y' "
                       +" AND  if( '" + ward_no + "'  = '' , w.ward_no_m like '%%' , w.ward_no_m = ? )"
                       +" AND  if( '" + area_name + "'  = '' , a.area_name like '%%' , a.area_name = ? )"
                       +" LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            
            pstmt.setString(1, ward_no);
            pstmt.setString(2, area_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                AreaTypeBean bean = new AreaTypeBean();
                bean.setArea_id(rset.getInt("area_id"));
                bean.setArea_name(rset.getString("area_name"));
                bean.setWard_no(rset.getString("ward_no_m"));
              //  bean.setWard_name(rset.getString("ward_name"));
                //bean.setCity_name(rset.getString("city_name"));
                bean.setCreated_date(rset.getString("created_date"));
                bean.setRemark(rset.getString("remark"));
                //bean.setActive(rset.getString("active"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }

 public List<AreaTypeBean>showAllData(String area_name, String ward_no) {
        List<AreaTypeBean> list = new ArrayList<AreaTypeBean>();
        //PreparedStatement pstmt = null;

        String query = " SELECT a.area_id, a.area_name, w.ward_no_m, a.remark,DATE_FORMAT(a.created_date,'%d-%m-%Y') AS created_date FROM area a, ward_m w  "
                + " WHERE a.ward_id_m=w.ward_id_m  and a.active='Y' "
                + " AND  if( '" + ward_no + "'  = '' , w.ward_no_m like '%%' , w.ward_no_m = ? )"
                + " AND  if( '" + area_name + "'  = '' , a.area_name like '%%' , a.area_name = ? )";
              //  + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
           
            pstmt.setString(1, ward_no);
            pstmt.setString(2, area_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                AreaTypeBean bean = new AreaTypeBean();
                bean.setArea_id(rset.getInt("area_id"));
                bean.setArea_name(rset.getString("area_name"));
                bean.setWard_no(rset.getString("ward_no"));
             //   bean.setWard_name(rset.getString("ward_name"));
                //bean.setCity_name(rset.getString("city_name"));
                bean.setCreated_date(rset.getString("created_date"));
                bean.setRemark(rset.getString("remark"));
                //bean.setActive(rset.getString("active"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }
 public int updateRecord(AreaTypeBean areaTypeBean) {
        String query = " UPDATE area SET  area_name = ?, remark=?, ward_id_m=? WHERE area_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            pstmt.setString(1, areaTypeBean.getArea_name());
            pstmt.setString(2, areaTypeBean.getRemark());
            pstmt.setInt(3, areaTypeBean.getWard_id());
//            pstmt.setString(4, areaTypeBean.getActive());
            pstmt.setInt(4, areaTypeBean.getArea_id());
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
        String query = " update area "
                       +" set active='N' "
                       +" where area_id ="+area_id;
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
