/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.CircuitSurveyBean;
import com.survey.tableClasses.PrimarySurveyBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class CircuitSurveyModel {
    
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

        String query = " SELECT imageoffirstpole FROM circuit_survey where id=" + imageid1 + " ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                destination_path = rset.getString("imageoffirstpole");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return destination_path;
    }
         public String getImageDestinationPath1(int imageid1) {
        String destination_path = "";

        String query = " SELECT imageoflastpole FROM circuit_survey where id=" + imageid1 + " ";
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

        String query =" select circuit_name "
                      +" from circuit ";
                     
                
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String areaName = rset.getString("circuit_name");
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

        String query =" select ivrs_no "
                    +" from meters ";
                

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_no_m = rset.getString("ivrs_no");
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
      public List<String> getCurcuitName(String q) {
        List<String> list = new ArrayList<String>();
 String finalname="";
        String query =" SELECT c.circuit_name,t.index_no FROM circuit_info as c ,tree as t where t.circuitinfo_id=c.circuitinfo_id ";
                

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String cname = rset.getString("circuit_name");
                String indexno = rset.getString("index_no");
                finalname=cname.concat("("+indexno+")");
                list.add(cname);
                 count++;
            }
            if (count == 0) {
                list.add("No such primary Survey Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:primary Survey Type exists. Error-" + e);
        }
        return list;
    }
      public List<String> getPoleNo(String q) {
        List<String> list = new ArrayList<String>();
 String finalname="";
        String query =" SELECT pole_no from pole";
                

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String cname = rset.getString("pole_no");
               
                list.add(cname);
                 count++;
            }
            if (count == 0) {
                list.add("No such primary Survey Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:primary Survey Type exists. Error-" + e);
        }
        return list;
    }
      public List<String> getCurcuitIndex(String q) {
        List<String> list = new ArrayList<String>();
 String finalname="";
        String query =" SELECT c.circuit_name,t.index_no FROM circuit_info as c ,tree as t where t.circuitinfo_id=c.circuitinfo_id and c.circuit_name='"+q+"' ";
                

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            
            while (rset.next()) {    // move cursor from BOR to valid record.
                String cname = rset.getString("circuit_name");
                String indexno = rset.getString("index_no");
                finalname=cname.concat("("+indexno+")");
                list.add(indexno);
                 count++;
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
      public List<CircuitSurveyBean> showData11(String circuitid) {
        List<CircuitSurveyBean> list = new ArrayList<CircuitSurveyBean>();
        //PreparedStatement pstmt = null;
int parent_id=0;
String parent_name="";
        String query = " SELECT id, circuit_name, irvs_no, circuitno,parent_id, remark, description, timestamp,"
                + "  time, sync_status, is_child,  lattitudefirstpole,lattitudelasttpole, longitudefirstpole,"
                + " altitudefirstpole, accuracyfirstpole, longitudelasttpole, "
                + " altitudelastpole, accuracylasttpole ,switching_point_detail_id,first_pole_id,last_pole_id,cable_type_id,imageoffirstpole,imageoflastpole"
                       +" FROM circuit_survey "
                       +" WHERE id=? "
                 ;
        try {
          PreparedStatement pstmt = connection.prepareStatement(query);
            
            pstmt.setString(1, circuitid);
            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CircuitSurveyBean bean = new CircuitSurveyBean();
                bean.setCircuit_id(rset.getInt("id"));
                bean.setSwitchingpointid(rset.getInt("switching_point_detail_id"));
                bean.setFirstpoleid(rset.getInt("first_pole_id"));
                bean.setLastpoleid(rset.getInt("last_pole_id"));
                bean.setCabletypeid(rset.getInt("cable_type_id"));
               
                bean.setImagepathoffirstpole(rset.getString("imageoffirstpole"));
                bean.setImagepathoflastpole(rset.getString("imageoflastpole"));
                bean.setCircuit_name(rset.getString("circuit_name"));
                bean.setIrvs_no(rset.getString("irvs_no"));
                 bean.setCircuitno(rset.getString("circuitno"));
                bean.setTimestamptime(rset.getString("timestamp"));
                
                 bean.setTime(rset.getString("time"));
                bean.setSync_status(rset.getString("sync_status"));
                 bean.setIs_child(rset.getString("is_child"));
             bean.setAccuracylasttpole(rset.getString("accuracylasttpole"));
                bean.setAccuracyfirstpole(rset.getString("accuracyfirstpole"));
                bean.setLattitudefirstpole(rset.getDouble("lattitudefirstpole"));
                bean.setLattitudelasttpole(rset.getDouble("lattitudelasttpole"));
                bean.setLongitudefirstpole(rset.getDouble("longitudefirstpole"));
                 bean.setLongitudelasttpole(rset.getDouble("longitudelasttpole"));
                bean.setAltitudefirstpole(rset.getDouble("altitudefirstpole"));
                bean.setAltitudelastpole(rset.getDouble("altitudelastpole"));
               
                //bean.setActive(rset.getString("active"));
                parent_id= rset.getInt("parent_id");
                if(parent_id>0){
                    parent_name= getparentname(parent_id);
                }
                 bean.setParent(parent_name);
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }

    public List<CircuitSurveyBean> showData(int lowerLimit, int noOfRowsToDisplay, String circuit_name, String irvs_no,String circuitno) {
        List<CircuitSurveyBean> list = new ArrayList<CircuitSurveyBean>();
        //PreparedStatement pstmt = null;
int parent_id=0;
String parent_name="";
        String query = " SELECT id, circuit_name, irvs_no, circuitno,parent_id, remark, description, timestamp,"
                + "  time, sync_status, is_child,  lattitudefirstpole, longitudefirstpole,"
                + " altitudefirstpole, accuracyfirstpole, lattitudelasttpole, longitudelasttpole, "
                + " altitudelastpole, accuracylasttpole "
                       +" FROM circuit "
                       +" WHERE "
                       +"  if( '" + circuit_name + "'  = '' , circuit_name like '%%' , circuit_name = ? )"
                       +" AND  if( '" + irvs_no + "'  = '' , irvs_no like '%%' , irvs_no = ? )"
                 +" AND  if( '" + circuitno + "'  = '' , circuitno like '%%' , circuitno = ? )"
                       +" LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
          PreparedStatement pstmt = connection.prepareStatement(query);
            
            pstmt.setString(1, circuit_name);
            pstmt.setString(2, irvs_no);
              pstmt.setString(3, circuitno);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CircuitSurveyBean bean = new CircuitSurveyBean();
                bean.setCircuit_id(rset.getInt("id"));
               
               
                bean.setCircuit_name(rset.getString("circuit_name"));
                bean.setIrvs_no(rset.getString("irvs_no"));
                 bean.setCircuitno(rset.getString("circuitno"));
                bean.setTimestamptime(rset.getString("timestamp"));
                
                 bean.setTime(rset.getString("time"));
                bean.setSync_status(rset.getString("sync_status"));
                 bean.setIs_child(rset.getString("is_child"));
             bean.setAccuracylasttpole(rset.getString("accuracylasttpole"));
                bean.setAccuracyfirstpole(rset.getString("accuracyfirstpole"));
                bean.setLattitudefirstpole(rset.getDouble("lattitudefirstpole"));
                bean.setLattitudelasttpole(rset.getDouble("lattitudelasttpole"));
                bean.setLongitudefirstpole(rset.getDouble("longitudefirstpole"));
                 bean.setLattitudelasttpole(rset.getDouble("longitudelasttpole"));
                bean.setAltitudefirstpole(rset.getDouble("altitudefirstpole"));
                bean.setAltitudelastpole(rset.getDouble("altitudelastpole"));
                //bean.setActive(rset.getString("active"));
                parent_id= rset.getInt("parent_id");
                if(parent_id>0){
                    parent_name= getparentname(parent_id);
                }
                 bean.setParent(parent_name);
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData() :AreaTypeModel" + e);
        }
        return list;
    }
    public List<CircuitSurveyBean> showData1(int lowerLimit, int noOfRowsToDisplay, String circuit_name, String irvs_no,String circuitno) {
        List<CircuitSurveyBean> list = new ArrayList<CircuitSurveyBean>();
        //PreparedStatement pstmt = null;
int parent_id=0;
String parent_name="";
        String query = " SELECT id, circuit_name, irvs_no, circuitno,parent_id, remark, description, timestamp,"
                + "  time, sync_status, is_child,  lattitudefirstpole, longitudefirstpole,"
                + " altitudefirstpole, accuracyfirstpole, lattitudelasttpole, longitudelasttpole, "
                + " altitudelastpole, accuracylasttpole "
                       +" FROM circuit_survey "
                       +" WHERE "
                       +"  if( '" + circuit_name + "'  = '' , circuit_name like '%%' , circuit_name = ? )"
                       +" AND  if( '" + irvs_no + "'  = '' , irvs_no like '%%' , irvs_no = ? )"
                 +" AND  if( '" + circuitno + "'  = '' , circuitno like '%%' , circuitno = ? )"
                       +" LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
          PreparedStatement pstmt = connection.prepareStatement(query);
            
            pstmt.setString(1, circuit_name);
            pstmt.setString(2, irvs_no);
              pstmt.setString(3, circuitno);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CircuitSurveyBean bean = new CircuitSurveyBean();
                bean.setCircuit_id(rset.getInt("id"));
               
               
                bean.setCircuit_name(rset.getString("circuit_name"));
                bean.setIrvs_no(rset.getString("irvs_no"));
                 bean.setCircuitno(rset.getString("circuitno"));
                bean.setTimestamptime(rset.getString("timestamp"));
                
                 bean.setTime(rset.getString("time"));
                bean.setSync_status(rset.getString("sync_status"));
                 bean.setIs_child(rset.getString("is_child"));
             bean.setAccuracylasttpole(rset.getString("accuracylasttpole"));
                bean.setAccuracyfirstpole(rset.getString("accuracyfirstpole"));
                bean.setLattitudefirstpole(rset.getDouble("lattitudefirstpole"));
                bean.setLattitudelasttpole(rset.getDouble("lattitudelasttpole"));
                bean.setLongitudefirstpole(rset.getDouble("longitudefirstpole"));
                 bean.setLattitudelasttpole(rset.getDouble("longitudelasttpole"));
                bean.setAltitudefirstpole(rset.getDouble("altitudefirstpole"));
                bean.setAltitudelastpole(rset.getDouble("altitudelastpole"));
                //bean.setActive(rset.getString("active"));
                parent_id= rset.getInt("parent_id");
                if(parent_id>0){
                    parent_name= getparentname(parent_id);
                }
                 bean.setParent(parent_name);
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
 
 
  public int insertRecordPrimary(String meter_no,String ivrs_no,String circuitno,String swicthing_point_detail_id,String first_pole_id,String last_pole_id,String cable_type_id,
         String parent_id,String time,String is_child,String imagefirstpolePath,String imagelastpolePath,String latitudefirstpole,String longitudefirstpole,String altitudefirstpole,String accuracyfirstpole
                 ,String latitudelastpole,String longitudelastpole,String altitudelastpole,String accuracylastpole) {
        int roweffected = 0;
        try {

        

            String query = "insert into circuit_survey(circuit_name, irvs_no, circuitno, "
                    + " switching_point_detail_id, first_pole_id, last_pole_id, cable_type_id, parent_id,"
                    + " time, is_child, imageoffirstpole, imageoflastpole, "
                    + " lattitudefirstpole, longitudefirstpole, altitudefirstpole, accuracyfirstpole, lattitudelasttpole, "
                    + " longitudelasttpole, altitudelastpole, accuracylasttpole"
                    + " )" + " VALUES('" + meter_no + "','" + ivrs_no + "','" + circuitno + "',"
                    + swicthing_point_detail_id + "," + first_pole_id + "," + last_pole_id + "," + cable_type_id + ","
                    + parent_id + "," + time + ",'" + is_child + "','" + imagefirstpolePath + "',"
                    + " '" + imagelastpolePath + "'," + latitudefirstpole + "," + longitudefirstpole + "," + altitudefirstpole + ","
                    + " '" + accuracyfirstpole + "'," + latitudelastpole + "," + longitudelastpole + "," + altitudelastpole + ","
                    + "'" + accuracylastpole + "')";
 
            try {
                PreparedStatement psmt = connection.prepareStatement(query);
 
                roweffected = psmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("DataSendModel Error: " + e);
            }

        } catch (Exception ex) {
            Logger.getLogger(MeterSurveyWebServicesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roweffected;
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

    public int insertRecordPrimary(String circuitname, String ivrsno1, String circuitno1, String switching_id, String firstpole_id, String lastpole_id, String cabletypeid, String parent1, String time1, String child1, String firstpoleimage, String lastpoleimage, String lattitudefirstpole1, String longitudefirstpole1, String altitudefirstpole, String acuracyfirstpole1, String lattitudelastpole1, String longitudelastpole1, String altitudelastpole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

