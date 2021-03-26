/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.CircuitSurveyBean;
import com.survey.tableClasses.PoleDetailTypeBean;
import com.survey.tableClasses.PrimarySurveyBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class PoleSurveyDataModel {
    
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
             //connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("AraeTYpeTypeModel setConnection() Error: " + e);
        }
    }
   public byte[] generateMapReport(String jrxmlFilePath, List<PrimarySurveyBean> listAll) {
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
            pstmt =  connection.prepareStatement(query);
           
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
    
      public String getImageDestinationPath(int imageid1) {
        String destination_path = "";

        String query = " SELECT image_path FROM pole_survey_image_map where pole_survey_id=" + imageid1 + " ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                destination_path = rset.getString("image_path");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return destination_path;
    }
         public String getImageDestinationPath1(int imageid1) {
        String destination_path = "";

        String query = " SELECT imageoflastpole FROM circuit where id=" + imageid1 + " ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                destination_path = rset.getString("imageoflastpole");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return destination_path;
    }
public List<String> getSearchMeterName(String q) {
        List<String> list = new ArrayList<String>();

        String query =" select pole_no "
                      +" from pole_survey ";
                     
                
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String areaName = rset.getString("pole_no");
                if (areaName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(areaName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Primary survey Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:AreaType1Model-" + e);
        }
        return list;
    }

    public List<String> getSearchIrvsNo(String q) {
        List<String> list = new ArrayList<String>();

        String query =" select irvs_no "
                    +" from circuit ";
                

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no_m = rset.getString("irvs_no");
                if (ward_no_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such primary Survey Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:primary Survey Type exists. Error-" + e);
        }
        return list;
    }
   public List<String> getCircuitNo(String q) {
        List<String> list = new ArrayList<String>();

         String query = " SELECT circuitno FROM circuit ";
                

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no_m = rset.getString("circuitno");
                if (ward_no_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_no_m);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such primary Survey Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:primary Survey Type exists. Error-" + e);
        }
        return list;
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

// public int insertRecord(PrimarySurveyBean bean) {
//        String query = " INSERT INTO area(area_name, remark, ward_id_m ) VALUES(?, ?, ?) ";
//        int rowsAffected = 0;
//        try {
//          PreparedStatement pstmt = connection.prepareStatement(query);
//
//            pstmt.setString(1, bean.getArea_name());
//            pstmt.setString(2, bean.getRemark());
//            pstmt.setInt(3, bean.getWard_id());
////            pstmt.setInt(4, bean.getWard_rev_no());
////            pstmt.setString(5, bean.getActive());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("error in area TYpe Model Insert() method:" + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }

   public int getNoOfRows(String meter_name, String irvs_no,String circuitno) {
        int noOfRows = 0;
        try {
            String query = " SELECT COUNT(*) "
                    + " FROM circuit "
                    + " WHERE "
                    + " IF ( '" + meter_name + "'  = '' , meter_name like '%%' , meter_name = ? ) "
                    + " AND IF ( '" + irvs_no + "'  = '' , irvs_no like '%%' , irvs_no = ? ) "
                     + " AND IF ( '" + circuitno + "'  = '' , circuitno like '%%' , circuitno = ? ) "
                    + " ORDER BY survey_type_search ";
           PreparedStatement pst = connection.prepareStatement(query);
            
            pst.setString(1, meter_name);
            pst.setString(2, irvs_no);
            pst.setString(3, circuitno);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error in getRows() of AreaTypeModel...." + e);
        }
        return noOfRows;
    }

    public List<PoleDetailTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String circuit_name) {
        List<PoleDetailTypeBean> list = new ArrayList<PoleDetailTypeBean>();
        //PreparedStatement pstmt = null;
int parent_id=0;
String query="SELECT ps.pole_id,ps.pole_span, ps.pole_height, ps.mounting_height,r.road_name,a.area_name,pt.pole_type_name,mt.mounting_type,"
        + "tt.traffic_type,plt.light_name,ps.remark,ps.pole_no,ps.created_date,ps.max_avg_lux_level,ps.avg_lux_level,ps.min_avg_lux_level,ps.standard_lux_level"
        + ",ps.latitude,ps.longitude FROM  pole_survey as ps,road as r ,area as a,pole_type as pt,mounting_type as mt,traffic_type as tt ,"
        + "pole_light_type as plt where ps.road_id=r.road_id and ps.area_id=a.area_id and ps.pole_type_id=pt.pole_type_id and "
        + " ps.mounting_type_id=mt.mounting_type_id and ps.traffic_type_id=tt.traffic_type_id and ps.pole_light_type_id =plt.pole_light_type_id "
       
 
                        +"  AND if( '" + circuit_name + "'  = '' , pole_no like '%%' , pole_no = '" + circuit_name + "'  )"
//                       +" AND  if( '" + irvs_no + "'  = '' , irvs_no like '%%' , irvs_no = ? )"
//                 +" AND  if( '" + circuitno + "'  = '' , circuitno like '%%' , circuitno = ? )"
                        +" order by pole_id desc LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

 
        try {
          PreparedStatement pstmt = connection.prepareStatement(query);
            
          
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PoleDetailTypeBean bean = new PoleDetailTypeBean();
                bean.setPole_id(rset.getInt("pole_id"));
                bean.setPole_span(rset.getString("pole_span"));
                bean.setPole_height(rset.getString("pole_height"));
                bean.setMounting_height(rset.getString("mounting_height"));
                bean.setRoad_name(rset.getString("road_name"));
                bean.setArea_name(rset.getString("area_name"));
                bean.setPole_type(rset.getString("pole_type_name"));
                bean.setMounting_type(rset.getString("mounting_type"));
                bean.setTraffic_type(rset.getString("traffic_type"));
                bean.setLightname(rset.getString("light_name"));
                bean.setRemark(rset.getString("remark"));
                bean.setPole_no(rset.getString("pole_no"));
                bean.setCreated_date(rset.getString("created_date"));
                bean.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
                bean.setAvg_lux_level(rset.getString("avg_lux_level"));
                bean.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
                bean.setStandard_lux_level(rset.getString("standard_lux_level"));
                bean.setLatitude(rset.getDouble("latitude"));
                bean.setLongitude(rset.getDouble("longitude"));
             
             
            
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }
        public List<PoleDetailTypeBean> showDataById11(String id,PoleDetailTypeBean ps) {
        List<PoleDetailTypeBean> list = new ArrayList<PoleDetailTypeBean>();
       String imgpathnew=ps.getImgpath();
String query="SELECT ps.pole_id,ps.pole_span, ps.pole_height, ps.mounting_height,r.road_name,a.area_name,pt.pole_type_name,mt.mounting_type,"
        + "tt.traffic_type,plt.light_name,ps.remark,ps.pole_no,ps.created_date,ps.max_avg_lux_level,ps.avg_lux_level,ps.min_avg_lux_level,ps.standard_lux_level"
        + ",ps.latitude,ps.longitude,ps.isSwitchingPoint,ps.road_id,ps.area_id,ps.traffic_type_id FROM  pole_survey as ps,road as r ,area as a,pole_type as pt,mounting_type as mt,traffic_type as tt ,"
        + "pole_light_type as plt where ps.road_id=r.road_id and ps.area_id=a.area_id and ps.pole_type_id=pt.pole_type_id and "
        + " ps.mounting_type_id=mt.mounting_type_id and ps.traffic_type_id=tt.traffic_type_id and ps.pole_light_type_id =plt.pole_light_type_id and ps.pole_id='"+id+"' ";
 
//                       +"  if( '" + circuit_name + "'  = '' , circuit_name like '%%' , circuit_name = ? )"
//                       +" AND  if( '" + irvs_no + "'  = '' , irvs_no like '%%' , irvs_no = ? )"
//                 +" AND  if( '" + circuitno + "'  = '' , circuitno like '%%' , circuitno = ? )"
//                       +" LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

 
        try {
          PreparedStatement pstmt = connection.prepareStatement(query);
            
          
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PoleDetailTypeBean bean = new PoleDetailTypeBean();
                bean.setPole_id(rset.getInt("pole_id"));
                bean.setRoad_id(rset.getInt("road_id"));
                bean.setArea_id(rset.getInt("area_id"));
                bean.setTraffic_type_id(rset.getInt("traffic_type_id"));
                bean.setPole_span(rset.getString("pole_span"));
                bean.setPole_height(rset.getString("pole_height"));
                bean.setMounting_height(rset.getString("mounting_height"));
                bean.setRoad_name(rset.getString("road_name"));
                bean.setArea_name(rset.getString("area_name"));
                bean.setPole_type(rset.getString("pole_type_name"));
                bean.setMounting_type(rset.getString("mounting_type"));
                bean.setTraffic_type(rset.getString("traffic_type"));
                bean.setLightname(rset.getString("light_name"));
                bean.setRemark(rset.getString("remark"));
                bean.setPole_no(rset.getString("pole_no"));
                bean.setCreated_date(rset.getString("created_date"));
                bean.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
                bean.setAvg_lux_level(rset.getString("avg_lux_level"));
                bean.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
                bean.setStandard_lux_level(rset.getString("standard_lux_level"));
                bean.setLatitude(rset.getDouble("latitude"));
                bean.setLongitude(rset.getDouble("longitude"));
                bean.setIs_switch_point(rset.getString("isSwitchingPoint"));
                bean.setImgpath(imgpathnew);
               
             
            
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }
    
      public List<PoleDetailTypeBean> showDataById(String id) {
        List<PoleDetailTypeBean> list = new ArrayList<PoleDetailTypeBean>();
       
String query="SELECT ps.pole_id,ps.pole_span, ps.pole_height, ps.mounting_height,r.road_name,a.area_name,pt.pole_type_name,mt.mounting_type,"
        + "tt.traffic_type,plt.light_name,ps.remark,ps.pole_no,ps.created_date,ps.max_avg_lux_level,ps.avg_lux_level,ps.min_avg_lux_level,ps.standard_lux_level"
        + ",ps.latitude,ps.longitude,ps.isSwitchingPoint,ps.road_id,ps.area_id,ps.traffic_type_id FROM  pole_survey as ps,road as r ,area as a,pole_type as pt,mounting_type as mt,traffic_type as tt ,"
        + "pole_light_type as plt where ps.road_id=r.road_id and ps.area_id=a.area_id and ps.pole_type_id=pt.pole_type_id and "
        + " ps.mounting_type_id=mt.mounting_type_id and ps.traffic_type_id=tt.traffic_type_id and ps.pole_light_type_id =plt.pole_light_type_id and ps.pole_id='"+id+"' ";
 
//                       +"  if( '" + circuit_name + "'  = '' , circuit_name like '%%' , circuit_name = ? )"
//                       +" AND  if( '" + irvs_no + "'  = '' , irvs_no like '%%' , irvs_no = ? )"
//                 +" AND  if( '" + circuitno + "'  = '' , circuitno like '%%' , circuitno = ? )"
//                       +" LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

 
        try {
          PreparedStatement pstmt = connection.prepareStatement(query);
            
          
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PoleDetailTypeBean bean = new PoleDetailTypeBean();
                bean.setPole_id(rset.getInt("pole_id"));
                bean.setRoad_id(rset.getInt("road_id"));
                bean.setArea_id(rset.getInt("area_id"));
                bean.setTraffic_type_id(rset.getInt("traffic_type_id"));
                bean.setPole_span(rset.getString("pole_span"));
                bean.setPole_height(rset.getString("pole_height"));
                bean.setMounting_height(rset.getString("mounting_height"));
                bean.setRoad_name(rset.getString("road_name"));
                bean.setArea_name(rset.getString("area_name"));
                bean.setPole_type(rset.getString("pole_type_name"));
                bean.setMounting_type(rset.getString("mounting_type"));
                bean.setTraffic_type(rset.getString("traffic_type"));
                bean.setLightname(rset.getString("light_name"));
                bean.setRemark(rset.getString("remark"));
                bean.setPole_no(rset.getString("pole_no"));
                bean.setCreated_date(rset.getString("created_date"));
                bean.setMax_avg_lux_level(rset.getString("max_avg_lux_level"));
                bean.setAvg_lux_level(rset.getString("avg_lux_level"));
                bean.setMin_avg_lux_level(rset.getString("min_avg_lux_level"));
                bean.setStandard_lux_level(rset.getString("standard_lux_level"));
                bean.setLatitude(rset.getDouble("latitude"));
                bean.setLongitude(rset.getDouble("longitude"));
                bean.setIs_switch_point(rset.getString("isSwitchingPoint"));
               
             
            
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }
 public String getparentname(int parent_id) {
        String query = " select circuit_name "
                       +" from circuit "
                       +" where parent_id ="+parent_id;
      String parent_name = "";
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            rset.next();
            parent_name = rset.getString("circuit_name");
        } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return parent_name;
    }
 public List<String> getImagePathpdf(int parent_id) {
        String query = " select image_path "
                       +" from pole_survey_image_map "
                       +" where pole_survey_id ="+parent_id;
      List<String> parent_name = new ArrayList<String>();
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            while(rset.next()){
          String name = rset.getString("image_path");
          parent_name.add(name);
        } } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return parent_name;
    }
// public List<AreaTypeBean>showAllData(String area_name, String ward_no) {
//        List<AreaTypeBean> list = new ArrayList<AreaTypeBean>();
//        //PreparedStatement pstmt = null;
//
//        String query = " SELECT a.area_id, a.area_name, w.ward_no_m, a.remark,DATE_FORMAT(a.created_date,'%d-%m-%Y') AS created_date FROM area a, ward_m w  "
//                + " WHERE a.ward_id_m=w.ward_id_m  and a.active='Y' "
//                + " AND  if( '" + ward_no + "'  = '' , w.ward_no_m like '%%' , w.ward_no_m = ? )"
//                + " AND  if( '" + area_name + "'  = '' , a.area_name like '%%' , a.area_name = ? )";
//              //  + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
//        try {
//           PreparedStatement pstmt = connection.prepareStatement(query);
//           
//            pstmt.setString(1, ward_no);
//            pstmt.setString(2, area_name);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                AreaTypeBean bean = new AreaTypeBean();
//                bean.setArea_id(rset.getInt("area_id"));
//                bean.setArea_name(rset.getString("area_name"));
//                bean.setWard_no(rset.getString("ward_no"));
//             //   bean.setWard_name(rset.getString("ward_name"));
//                //bean.setCity_name(rset.getString("city_name"));
//                bean.setCreated_date(rset.getString("created_date"));
//                bean.setRemark(rset.getString("remark"));
//                //bean.setActive(rset.getString("active"));
//                list.add(bean);
//            }
//        } catch (Exception e) {
//            System.out.println("Error in ShowData() :AreaTypeModel" + e);
//        }
//        return list;
//    }
// public int updateRecord(AreaTypeBean areaTypeBean) {
//        String query = " UPDATE area SET  area_name = ?, remark=?, ward_id_m=? WHERE area_id = ? ";
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
//
//            pstmt.setString(1, areaTypeBean.getArea_name());
//            pstmt.setString(2, areaTypeBean.getRemark());
//            pstmt.setInt(3, areaTypeBean.getWard_id());
////            pstmt.setString(4, areaTypeBean.getActive());
//            pstmt.setInt(4, areaTypeBean.getArea_id());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("AreaModel updateRecord() Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record updated successfully......";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot update the record, some error......";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }
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
 public int insertPoleSurveyRecordInMain(PoleDetailTypeBean bean) {
        int rowsAffected = 0;
           int survey_id = 0;
        
           
        String imageQuery = "INSERT INTO `pole`(`pole_type_id`,`pole_span`,`pole_height`,`mounting_height`,`remark`,"
                + "`mounting_type_id`,`active`,`pole_no`,`gps_code`,`max_avg_lux_level`,`min_avg_lux_level`,`avg_lux_level`,"
                + "`standard_lux_level`,`is_working`,`pole_rev_no`,`latitude`,`longitude`,`isSwitchingPoint`,`area_id`,"
                + "`road_id`,`traffic_type_id`,`road_rev_no`,`tube_well_detail_id`,`tube_well_rev_no`,"
                + "`switching_point_detail_id`,`switching_rev_no`,`pole_light_type_id`)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {      
               
            PreparedStatement pstmt = connection.prepareStatement(imageQuery, Statement.RETURN_GENERATED_KEYS);
            //  pstmt.setInt(1, bean.getPole_id());
            pstmt.setInt(1, getPoleTypeId(bean.getPole_type()));
            pstmt.setString(2, bean.getPole_span());
            pstmt.setString(3, bean.getPole_height());
            pstmt.setString(4, bean.getMounting_height());
            pstmt.setString(5, bean.getRemark());
            pstmt.setInt(6, getMountingTypeId(bean.getMounting_type()));
            pstmt.setString(7, bean.getActive());
            pstmt.setString(8, bean.getPole_no());
            pstmt.setString(9, bean.getGps_code());
            pstmt.setString(10, bean.getMax_avg_lux_level());
            pstmt.setString(11, bean.getMin_avg_lux_level());
            pstmt.setString(12, bean.getAvg_lux_level());
            pstmt.setString(13, bean.getStandard_lux_level());
            pstmt.setString(14, bean.getIs_working());
            pstmt.setInt(15, bean.getPole_rev_no());
            pstmt.setDouble(16, bean.getLatitude());
            pstmt.setDouble(17, bean.getLongitude());
            pstmt.setString(18, bean.getIs_switch_point());
            pstmt.setInt(19, getAreaTypeId(bean.getArea_name()));
            pstmt.setInt(20, getroadTypeId(bean.getRoad_name()));
            pstmt.setInt(21, getTrafficTypeId(bean.getTraffic_type()));
            pstmt.setInt(22, bean.getRoad_rev_no());
            pstmt.setInt(23, bean.getTubewell_id());
            pstmt.setInt(24, bean.getTubewell_revno());
            pstmt.setInt(25, bean.getSwitch_point_detail_id());
            pstmt.setInt(26, bean.getSwitching_rev_no());
            pstmt.setInt(27, getPoleLightTypeId(bean.getLightname()));
     
       
            rowsAffected = pstmt.executeUpdate();
         ResultSet rs=null;
           
            if (rowsAffected > 0) {
           
                 rs = pstmt.getGeneratedKeys();
                 while (rs.next()) {
                     survey_id = rs.getInt(1);
               
               }
 
            }
 
            pstmt.close();
        } catch (Exception e) {
            System.out.println(" l--insertSurvey Record " + e);
        }
        return survey_id;
    }
 public byte[] generateSiteList(String jrxmlFilePath,List listAll) {
        byte[] reportInbytes = null;        
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in  generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
  public int getPoleTypeId(String parent_id) {
        String query = " select pole_type_id "
                       +" from pole_type "
                       +" where pole_type_name ='"+parent_id+"' ";
      int id = 0;
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            rset.next();
            id = rset.getInt("pole_type_id");
            pst.close();
        } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return id;
    }
  public int getPoleLightTypeId(String  parent_id) {
        String query = " select pole_light_type_id "
                       +" from pole_light_type "
                       +" where light_name ='"+parent_id+"' ";
      int id = 0;
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            rset.next();
            id = rset.getInt("pole_light_type_id");
              pst.close();
        } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return id;
    }
  public int getMountingTypeId(String  parent_id) {
        String query = " select mounting_type_id "
                       +" from mounting_type "
                       +" where mounting_type ='"+parent_id+"' ";
      int id = 0;
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            rset.next();
            id = rset.getInt("mounting_type_id");
              pst.close();
        } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return id;
    }
  public int getAreaTypeId(String  parent_id) {
        String query = " select area_id "
                       +" from area "
                       +" where area_name ='"+parent_id+"' ";
      int id = 0;
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            rset.next();
            id = rset.getInt("area_id");
                pst.close();
        } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return id;
    }
  public int getroadTypeId(String  parent_id) {
        String query = " select road_id "
                       +" from road "
                       +" where road_name ='"+parent_id+"' ";
      int id = 0;
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            rset.next();
            id = rset.getInt("road_id");
               pst.close();
        } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return id;
    }
  public int getTrafficTypeId(String  parent_id) {
        String query = " select traffic_type_id "
                       +" from traffic_type "
                       +" where traffic_type ='"+parent_id+"' ";
      int id = 0;
        try {
               PreparedStatement pst = connection.prepareStatement(query);
                 ResultSet rset = pst.executeQuery();
            rset.next();
            id = rset.getInt("traffic_type_id");
              pst.close();
        } catch (Exception e) {
            System.out.println("CircuitModel deleteRecord() Error: " + e);
        }
     
        return id;
    }
 
 
  public String getImagePath(String imageid1) {
        String destination_path = "";

        String query = " SELECT image_path FROM pole_survey_image_map where pole_survey_id='" + imageid1 + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                destination_path = rset.getString("image_path");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return destination_path;
    }
  public int insertImagePath(String poleid,String path) {
        int rowsAffected = 0;

        String query = " insert into pole_image_map(image_path,pole_id)values ('"+path+"','"+poleid+"')  ";
        try {
          PreparedStatement ps = connection.prepareStatement(query);
            rowsAffected = ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
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

