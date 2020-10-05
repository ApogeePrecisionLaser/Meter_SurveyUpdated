///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
package com.survey.dataEntry.model;

import com.survey.general.model.AlertsModel;
import com.survey.tableClasses.SurveyBean;
import com.survey.tableClasses.TubeWellSurveyBean;
import com.survey.util.GetDate;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.codehaus.jettison.json.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class MeterSurveyWebServicesModel {

    private Connection connection;
    private String driverClass;
    private String db_username;
    private String db_password;
    private String connectionString;
    
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    String destination_path = "";
    

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    

    public Connection getConnection() {
        return connection;
    }

//    public void setConnection() {
//        try {
//            System.out.println("hii");
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/meter_survey", "jpss_2", "jpss_1277");
//        } catch (Exception e) {
//            System.out.println("ReadMailModel setConnection() Error: " + e);
//        }
//    }
    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }

    public void closeConnection(){
        try{
            connection.close();
        }catch(SQLException ex){
            System.out.println("ERROR : in closeConnection of MeterSurveyWebServicesModel : " + ex);
        }
    }

    public int insertRecord(SurveyBean surveyBean, List list) {
        String query = "INSERT INTO survey (survey_file_no, survey_page_no, mobile_no, pole_no, pole_rev_no, survey_type, "
                + " remark, survey_date,image_name,image_date_time,longitude,lattitude,survey_pole_no,survey_id, survey_with, survey_with_name) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query2 = "INSERT INTO switching_point_survey (switching_point_survey_id, meter_no, meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, mccb_functional,"
                + " remark,created_by,fuse1,fuse2,fuse3,contacter_id,"
                + "contacter_make_id,contacter_capacity,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,no_of_phase,meter_phase,meter_reading,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,reason_id,survey_rev_no, meter_name_auto, service_conn_no)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description) "
                + " VALUES(?, ?, ?, ?)";
        String query3 = "INSERT INTO tube_well_survey (tube_well_survey_id, meter_no, meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, starter_functional, mccb_functional,"
                + " remark,created_by,fuse1,fuse2,fuse3,starter_id,"
                + "starter_make_id,starter_capacity,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,no_of_phase,meter_phase,meter_reading,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,reason_id,revison_no, meter_name_auto, service_conn_no)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String updateQuery = "UPDATE survey set general_image_details_id=? where image_name=? and status='Y' ";
        String meter_name_auto = surveyBean.getMeter_name_auto();
        String service_conn_no = surveyBean.getService_conn_no();
        String updateMeters = "UPDATE meters SET latitude="+surveyBean.getLatitude()+", longitude="+surveyBean.getLongitude()+" WHERE meter_name_auto='"+ meter_name_auto +"' AND final_revision='VALID' ";

        int survey_rev_no = 0;
        String image_uploaded_for = "Survey Image";
        String pole_no = surveyBean.getPole_no();
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        int rowsAffected = 0;
        int survey_id=0;
        ResultSet rs = null;
//        insertSurveyCordinates(surveyBean.getLatitude(), surveyBean.getLongitude());

        try {
            String meter_query = "SELECT meter_id FROM meters WHERE meter_name_auto='"+ meter_name_auto +"' AND final_revision='VALID'";
            ResultSet rst = connection.prepareStatement(meter_query).executeQuery();
            String meter_id = "0";
            if(rst.next())
                meter_id = rst.getString(1);
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
             survey_id = getSurveyId();
            //pstmt.setInt(1, survey_id + 1);
            //pstmt.setInt(1, surveyBean.getSwitching_point_detail_id());
//            if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
//                pstmt.setNull(1, java.sql.Types.INTEGER);
//                pstmt.setNull(2, java.sql.Types.INTEGER);
//            } else {
//                pstmt.setInt(1, surveyBean.getSwitching_point_detail_id());
//                pstmt.setInt(2, surveyBean.getSwitching_rev_no());
//            }
            pstmt.setString(1, surveyBean.getSurvey_file_no());
            pstmt.setString(2, surveyBean.getSurvey_page_no());
            pstmt.setString(3, surveyBean.getSurvey_by());
            if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                pstmt.setString(4, surveyBean.getPole_no());
                pstmt.setInt(5, surveyBean.getPole_rev_no());
            } else {
                pstmt.setString(4, surveyBean.getPole_no());
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.setString(6, surveyBean.getSurvey_type());
            pstmt.setString(7, surveyBean.getRemark());

            String surveyDate = surveyBean.getSurvey_date();
            if (surveyDate != null && !(surveyDate.trim()).isEmpty()) {
                pstmt.setString(8, surveyDate);
                //pstmt.setDate(8, convertToSqlDate(surveyBean.getSurvey_date()));
            } else {
                pstmt.setNull(8, java.sql.Types.DATE);
            }
            pstmt.setString(9, surveyBean.getImage_name());
            pstmt.setString(10, null);
            pstmt.setString(11, surveyBean.getLongitude());
            pstmt.setString(12, surveyBean.getLatitude());
            pstmt.setString(13, surveyBean.getSurvey_pole_no());
            pstmt.setInt(14, survey_id);
            pstmt.setString(15, surveyBean.getSurvey_with_contact());
            pstmt.setString(16, surveyBean.getSurvey_with_name());
            rowsAffected = pstmt.executeUpdate();
            //rowsAffected = 0;
            if (surveyBean.getSurvey_type().equals("Switching Point") || surveyBean.getSurvey_type().equals("tubewell_type_survey")) {
                if (rowsAffected > 0) {
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        survey_id = rs.getInt(1);
                        //survey_rev_no = rs.getInt(36);
                    }
                    rowsAffected = 0;
                    pstmt.close();
                    if (surveyBean.getSurvey_type().equals("Switching Point")) {
                        pstmt = connection.prepareStatement(query2);
                    }
                    if (surveyBean.getSurvey_type().equals("tubewell_type_survey")) {
                        pstmt = connection.prepareStatement(query3);
                    }

                    pstmt.setInt(1, survey_id);
                    if (surveyBean.getMeter_status().equals("Y")) {
                        if (surveyBean.getMeter_functional().equals("Y")) {
                            pstmt.setString(2, surveyBean.getMeter_no());
                            pstmt.setString(3, surveyBean.getMeter_functional());
                            pstmt.setInt(29, surveyBean.getMeter_phase());
                            pstmt.setString(30, surveyBean.getMeter_reading());
                        } else {
                            pstmt.setString(2, surveyBean.getMeter_no());
                            pstmt.setString(3, surveyBean.getMeter_functional());
                            pstmt.setInt(29, surveyBean.getMeter_phase());
                            pstmt.setString(30, surveyBean.getMeter_reading());
                        }
                    } else {
                        pstmt.setString(2, surveyBean.getMeter_no());
                        pstmt.setString(3, surveyBean.getMeter_functional());
                        pstmt.setInt(29, surveyBean.getMeter_phase());
                        pstmt.setString(30, surveyBean.getMeter_reading());
                    }

                    if (surveyBean.getNo_of_phase() == 3) {
                        pstmt.setString(4, surveyBean.getR_phase());
                        pstmt.setString(5, surveyBean.getY_phase());
                        pstmt.setString(6, surveyBean.getB_phase());
                    } else {
                        pstmt.setNull(4, java.sql.Types.DOUBLE);
                        pstmt.setNull(5, java.sql.Types.DOUBLE);
                        pstmt.setString(6, surveyBean.getB_phase());
                    }

                    pstmt.setString(7, surveyBean.getPower());
                    pstmt.setString(8, surveyBean.getFuse_functional());
                    pstmt.setString(9, surveyBean.getContacter_functional());
                    pstmt.setString(10, surveyBean.getMccb_functional());
                    //  pstmt.setString(11, surveyBean.getTimer_functional());
                    pstmt.setString(11, surveyBean.getRemark());
                    pstmt.setString(12, "Viney Srivastva");
                    //    pstmt.setString(13, surveyBean.getMeter_status());
                    pstmt.setString(13, surveyBean.getFuse1());
                    pstmt.setString(14, surveyBean.getFuse2());
                    pstmt.setString(15, surveyBean.getFuse3());
                    if (surveyBean.getSurvey_type().equals("Switching Point")) {
                        pstmt.setString(16, surveyBean.getContacter_id());
                        pstmt.setString(17, surveyBean.getContacter_make_id());
                        pstmt.setString(18, surveyBean.getContacter_capacity());
                    } else {
                        pstmt.setInt(16, surveyBean.getStarter_id());
                        pstmt.setInt(17, surveyBean.getStarter_make_id());
                        pstmt.setString(18, surveyBean.getStarter_capacity());
                    }
                    pstmt.setString(19, surveyBean.getMccb1());
                    pstmt.setString(20, surveyBean.getMccb2());
                    pstmt.setString(21, surveyBean.getMccb3());
                    pstmt.setString(22, surveyBean.getFuse_id1());
                    pstmt.setString(23, surveyBean.getFuse_id2());
                    pstmt.setString(24, surveyBean.getFuse_id3());
                    pstmt.setString(25, surveyBean.getMccb_id1());
                    pstmt.setString(26, surveyBean.getMccb_id2());
                    pstmt.setString(27, surveyBean.getMccb_id3());
                    pstmt.setInt(28, surveyBean.getNo_of_phase());

                    pstmt.setString(31, surveyBean.getAuto_switch_type_id());
                    pstmt.setString(32, surveyBean.getMain_switch_type_id());
                    pstmt.setString(33, surveyBean.getMain_switch_rating());
                    pstmt.setString(34, surveyBean.getEnclosure_type_id());
                    if (surveyBean.getMeter_functional().equals("N")) {
                        pstmt.setString(35, getReasonId("abc"));//surveyBean.getReason_type()
                    } else {
                        pstmt.setNull(35, java.sql.Types.INTEGER);
                    }

                    pstmt.setInt(36, survey_rev_no);
                    //if(meter_name_auto == null)
                        pstmt.setString(37, meter_name_auto);
                   // else
                      //  pstmt.setNull(37, java.sql.Types.NULL);
                    
                    //if(service_conn_no == null)
                        pstmt.setString(38, service_conn_no);
                    //else
                       // pstmt.setNull(38, java.sql.Types.NULL);
                    rowsAffected = pstmt.executeUpdate();
                    if(rowsAffected > 0 &&  meter_name_auto!=null && !meter_name_auto.isEmpty()){
                        pstmt = connection.prepareStatement(updateMeters);
                        rowsAffected = pstmt.executeUpdate();                        
                        updateStatusData(meter_id);
                    }
                    if(rowsAffected > 0)
                    rowsAffected = updateCalculatedLoad(surveyBean.getPower(), meter_name_auto);
                        rowsAffected=1;
                    if (rowsAffected > 0) {
//                        if (surveyBean.getImage_name() != null && !surveyBean.getImage_name().isEmpty()) {
//                            try {
//                                pstmt = connection.prepareStatement(imageQuery);
//                                pstmt.setString(1, surveyBean.getImage_name());
//                                pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
//                                pstmt.setString(3, current_date);
//                                pstmt.setString(4, "this image is for survey");
//
//                                rowsAffected = pstmt.executeUpdate();
//                                pstmt.close();
//                            } catch (Exception e) {
//                                System.out.println("Error:keypersonModel--insertRecord-- " + e);
//                            }
//                            if (rowsAffected > 0) {
//                                try {
//                                    //if (writeImage(surveyBean.getImage_name(), list) > 0) {
//                                        pstmt = connection.prepareStatement(updateQuery);
//                                        pstmt.setInt(1, getgeneral_image_details_id(surveyBean.getImage_name()));
//                                        pstmt.setString(2, surveyBean.getImage_name());
//                                        pstmt.executeUpdate();
//                                    //}
//                                } catch (Exception e) {
//                                    System.out.println("Exception :" + e);
//                                    rowsAffected = 1;
//                                }
//                            }
//                        }


                        // rowsAffected = writeImage(key, itr, destination);
                        if (rowsAffected > 0) {
                            message = "Record saved successfully.";
                            msgBgColor = COLOR_OK;
                            connection.commit();
                            if (rowsAffected > 0) {
                                AlertsModel alertModel = new AlertsModel();
                                alertModel.setConnection(connection);
                               // rowsAffected = alertModel.insertAlertSheetData(survey_id, pole_no);
                            }
                            rowsAffected = 1;
                        }

                    } else {

                        connection.rollback();
                    }
                } else {
                    //query1 execution
                    connection.rollback();
                }
            }
        } catch (Exception e) {
            rowsAffected = -1;
            System.out.println("Error while inserting record...." + e);
            try {
                connection.rollback();
            } catch (SQLException sql) {
                System.out.println("SQLException occured during Updation is: " + sql);
            }

        } finally {
            try {
                if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                    if (rowsAffected > 0) {
                        connection.commit();
                    }
                }
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("SQLException occured while setting autoCommit = true duing new flex updation :" + ex);
                // Logger.getLogger(CreateEstimateModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rowsAffected > 0 ) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return survey_id;
    }

    public JSONArray surveyRecordOfSelectedDateTime(int survey_id,String ivrs_no,String date_time)
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT   tube_well_survey_id,t.revison_no,meter_no, meter_functional,r_phase,y_phase, b_phase, power, "
                +" fuse_functional, starter_functional, mccb_functional,   tube_well_survey_rev_no, "
                +" reason_id, no_of_phase,fuse1, fuse2, fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, "
                +" mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, starter_capacity, "
                +" if(t.starter_id is null,null,sr.starter_type) as starter_type , "
                +" meter_phase, meter_reading, "
                +" if(t.starter_make_id is null,null,sm.starter_make) as starter_make, "
                +" auto_switch_type_id, main_switch_type_id, main_switch_rating, enclosure_type_id, "
                +" t.meter_name_auto, t.service_conn_no, previous_reading, consume_unit,amount,area_id,t.meter_status,t.meter_address, "

                +" s.general_image_details_id,s.survey_id, "
                +" s.survey_rev_no,survey_file_no,s.survey_date as survey_date, survey_page_no,mobile_no, "
                +" pole_no, pole_rev_no, survey_type, status, image_name, s.lattitude, s.longitude, image_date_time, "
                +" data_entry_type_id, video_name, survey_pole_no,s.survey_with,s.survey_with_name "

                +" FROM tube_well_survey as t "
                +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
                +" left join feeder as f ON f.feeder_id=m.feeder_id "
                +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
                +" AND top.type_of_premises_id=ptm.type_of_premises_id "
                +" ,survey as s,starter as sr,starter_make as sm "
                +" where t.tube_well_survey_id=s.survey_id  and sr.starter_id=t.starter_id "
                +" and sm.starter_make_id=t.starter_make_id "
                +" and service_conn_no='"+ivrs_no+"' "
                +" and s.survey_date='"+date_time+"' "
                +" and s.survey_id="+survey_id
                +" and s.status='Y' "
                +" and t.active='Y' "
                +" order by tube_well_survey_id desc limit 1";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                JSONObject obj = new JSONObject();
          ///////////////////////from tube_well_survey table start////////////////////////////
                    obj.put("tube_well_survey_id", rset.getInt("tube_well_survey_id"));//
                    obj.put("t_w_s_revison_no", rset.getInt("revison_no"));//
                    obj.put("meter_no", rset.getInt("meter_no"));//
                    obj.put("meter_functional", rset.getString("meter_functional"));//
                    obj.put("r_phase", rset.getString("r_phase"));//
                    obj.put("y_phase", rset.getString("y_phase"));//
                    obj.put("b_phase", rset.getString("b_phase"));//
                    obj.put("power", rset.getString("power"));//
                    obj.put("fuse_functional", rset.getString("fuse_functional"));//
                    obj.put("starter_functional", rset.getString("starter_functional"));//

                    obj.put("mccb_functional", rset.getString("mccb_functional"));//
                    obj.put("tube_well_survey_rev_no",rset.getInt("tube_well_survey_rev_no"));//
                    obj.put("reason_type", getReasonType(rset.getString("reason_id")));//
                    obj.put("no_of_phase", rset.getString("no_of_phase"));//
                    obj.put("fuse1", rset.getString("fuse1"));//
                    obj.put("fuse2", rset.getString("fuse2"));//
                    obj.put("fuse3", rset.getString("fuse3"));//
                    obj.put("fuse_id1", getFuseType(rset.getString("fuse_id1")));//
                    obj.put("fuse_id2", getFuseType(rset.getString("fuse_id2")));//
                    obj.put("fuse_id3", getFuseType(rset.getString("fuse_id3")));//

                    obj.put("mccb1", rset.getString("mccb1"));//
                    obj.put("mccb2", rset.getString("mccb2"));//
                    obj.put("mccb3", rset.getString("mccb3"));//
                    obj.put("mccb_id1",rset.getString("mccb_id1"));//
                    obj.put("mccb_id2", rset.getString("mccb_id2"));//
                    obj.put("mccb_id3",rset.getString("mccb_id3"));//
                    obj.put("starter_capacity", rset.getString("starter_capacity"));//
                    obj.put("starter_type",rset.getString("starter_type"));//
                    obj.put("meter_phase", rset.getString("meter_phase"));//

                    obj.put("meter_reading", rset.getString("meter_reading"));//
                    obj.put("starter_make",rset.getString("starter_make"));//
                    obj.put("auto_switch", getSwitchType(rset.getInt("auto_switch_type_id")));//
                    obj.put("main_switch", getSwitchType(rset.getInt("main_switch_type_id")));//
                    obj.put("main_switch_rating", rset.getString("main_switch_rating"));//
                    obj.put("enclosure", getEnclosureType(rset.getInt("enclosure_type_id")));//
                    obj.put("meter_name_auto", rset.getString("meter_name_auto"));//
                    obj.put("service_conn_no", rset.getString("service_conn_no"));//
                    obj.put("previous_reading", rset.getString("previous_reading"));//
                    obj.put("consume_unit", rset.getString("consume_unit"));//
                    obj.put("amount", rset.getString("amount"));//
                    obj.put("area_id", rset.getString("area_id"));//

               ///////////////////////from survey table start////////////////////////////
                    obj.put("survey_id", rset.getInt("survey_id"));//
                    obj.put("survey_rev_no", rset.getInt("survey_rev_no"));//
                    obj.put("survey_file_no", rset.getString("survey_file_no"));//
                    obj.put("survey_date", rset.getString("survey_date"));//
                    obj.put("survey_page_no", rset.getString("survey_page_no"));//

                    obj.put("mobile_no", rset.getString("mobile_no"));//
                    obj.put("pole_no", rset.getString("pole_no"));//
                    obj.put("pole_rev_no", rset.getString("pole_rev_no"));//
                    obj.put("survey_type", rset.getString("survey_type"));//
                    obj.put("status", rset.getString("status"));//
                    obj.put("image_name",rset.getString("image_name"));//
                    obj.put("lattitude",rset.getString("lattitude"));//
                    obj.put("longitude", rset.getString("longitude"));//
                    obj.put("general_image_details_id", rset.getString("general_image_details_id"));//
                    obj.put("image_date_time", rset.getString("image_date_time"));//

                    obj.put("data_entry_type_id", rset.getString("data_entry_type_id"));//
                    obj.put("video_name", rset.getString("video_name"));//
                    obj.put("survey_pole_no", rset.getString("survey_pole_no"));//
                    obj.put("survey_with", rset.getString("survey_with"));//
                    obj.put("survey_with_name", rset.getString("survey_with_name"));//

                    obj.put("meter_status",rset.getString("meter_status"));
                    obj.put("meter_address", rset.getString("meter_address"));
                    obj.put("meter_remark", "");
                     obj.put("sp_remark", "");

                    rowData.put(obj);

           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }

        return rowData;
    }

    public JSONArray imageCount(String survey_id)
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "select toi.type_of_image_id,toi.image_type, count(toi.image_type) as image_count "
                +" from survey_gen_image_map sgim,general_image_details gid,type_of_image toi "
                +" where toi.type_of_image_id=gid.type_of_image_id "
                +" and gid.general_image_details_id=sgim.gen_image_detail_id "
                +" and sgim.survey_id="+survey_id
                +" group by image_type";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                    String type_of_image_id=rset.getString("type_of_image_id");
                    String image_type=rset.getString("image_type");
                    String image_count=rset.getString("image_count");

                    obj.put("type_of_image_id", type_of_image_id);//
                    obj.put("image_type", image_type);//
                    obj.put("image_count", image_count);//

                    String query1="select image_name "
                     +" from survey_gen_image_map sgim,general_image_details gid "
                     +" where sgim.gen_image_detail_id=gid.general_image_details_id "
                     +" and gid.type_of_image_id="+type_of_image_id
                     +" and sgim.survey_id="+survey_id;
                    try{
                        JSONArray rowData1 = new JSONArray();
                        //JSONObject obj1 = new JSONObject();
                         PreparedStatement pstmt1 = connection.prepareStatement(query1);
                         ResultSet rset1 = pstmt1.executeQuery();
                         while(rset1.next()){
                             JSONObject obj1 = new JSONObject();
                             obj1.put("image_name", rset1.getString("image_name"));//
                             rowData1.put(obj1);

                         }
                        obj.put("image_name_array", rowData1);//
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    rowData.put(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
///////////////////////////////////////////////////////////////////

    public String getAttachmentPath(String image_name,String survey_id,String image_type_id,String survey_type) {
        String attachment_destination = "";


        String query="SELECT id.destination_path "
              +" FROM general_image_details AS gid, image_destination As id,survey_gen_image_map as sgim "
              +" WHERE gid.image_destination_id = id.image_destination_id "
              +" and gid.general_image_details_id=sgim.gen_image_detail_id "
              +" and gid.type_of_image_id="+image_type_id
              +" and gid.image_name='"+image_name+"'"
              +" and sgim.survey_id="+survey_id;

        try {//C:\ssadvt_repository\meter_survey\survey_image\tube_well\survey_id_1099
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                attachment_destination = rset.getString("destination_path");
            }


            //attachment_destination = attachment_destination.concat(year).concat("\\").concat(month).concat("\\").concat(date).concat("\\").concat(id).concat("\\").concat(image_name);
            attachment_destination = attachment_destination.concat("\\").concat(survey_type).concat("\\").concat("survey_id_").concat(survey_id).concat("\\").concat(image_name);

        } catch (Exception e) {
            System.out.println("messageViewModel getAttachmentPath() Error: " + e);
        }

        return attachment_destination;
    }
///////////////////////////////////////////////////////////////////

     public String getSurveyType(String survey_id) {
        String survey_type = "";
        String query="select survey_type "
                     +" from survey s "
                     +" where s.status='Y' "
                     +" and survey_id="+survey_id;

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                survey_type = rset.getString("survey_type");
            }

        } catch (Exception e) {
            System.out.println("messageViewModel getAttachmentPath() Error: " + e);
        }
        return survey_type;
    }



    public String getFuseType(String fuse_id) {
        String fuse_type = "";
        String query = "select fuse_type from fuse where fuse_id='" + fuse_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, fuse_type);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                fuse_type = rset.getString("fuse_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getFuseTYpe() in surveyModel" + e);
        }
        return fuse_type;
    }
 public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdirs();
        }
        return result;
    }
    public String getReasonType(String reason_id) {
        String reason_type = "";
        String query = "select reason_type from reason_type "
                       +" where reason_id="+reason_id;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                reason_type = rset.getString("reason_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return reason_type;
    }
    public String getSwitchType(int switch_id) {
        String switch_type = "";
        String query = "select switch_type from switch_type where switch_type_id='" + switch_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switch_type = rset.getString("switch_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return switch_type;
    }
    public String getEnclosureType(int enclusre_type_id) {
        String enclosure_type = "";
        String query = "select enclosure_type from enclosure_type "
                       +" where enclosure_type_id="+enclusre_type_id;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                enclosure_type = rset.getString("enclosure_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return enclosure_type;
    }


    public JSONArray last5SurveyDateTime(String ivrs_no1)
        {
        JSONArray rowData = new JSONArray();
        String query = null;
                //"SELECT s.survey_id,s.survey_type,s.survey_date as survey_date,tws.service_conn_no "
        query = "SELECT s.survey_id,s.survey_type,s.survey_date as survey_date,tws.service_conn_no,s.lattitude,s.longitude "
                +" FROM survey as s,tube_well_survey as tws "
                +" where tws.tube_well_survey_id=s.survey_id "
                +" and tws.service_conn_no='"+ivrs_no1+"' "
                +" and s.status='Y' "
                +" and tws.active='Y' "
                +" Order By survey_id desc "
                +" limit 5";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                    String survey_id=rset.getString("survey_id");
                    String survey_type=rset.getString("survey_type");
                    String survey_date_time=rset.getString("survey_date");
//                    String dateTime[] = survey_date_time.split(" ");
//                    String survey_date=dateTime[1];
//                    String survey_time=dateTime[0];
                    String ivrs_no=rset.getString("service_conn_no");
                    String lattitude=rset.getString("lattitude");
                    String longitude=rset.getString("longitude");

                    obj.put("survey_id", survey_id);//
                    obj.put("survey_type", survey_type);//
                    obj.put("survey_date_time", survey_date_time);//
                    obj.put("ivrs_no", ivrs_no);//
                    obj.put("lattitude", lattitude);//
                    obj.put("longitude", longitude);//


                    rowData.put(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }





    public JSONArray getZone_m_data(){
        JSONArray rowData = new JSONArray();
        List<String> list = new ArrayList<String>();
        String meter_id = null;
        String query = " select IFNULL(zone_id_m, '') zone_id_m, "
                + " IFNULL(zone_m, '') zone_m,"
                + " IFNULL(description, '') description "
                       +" from zone_m z ";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("zone_id_m", rset.getString("zone_id_m"));
                    obj.put("zone_m", rset.getString("zone_m"));
                    obj.put("description", rset.getString("description"));
                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("MeterSurveyWebServiceModel getZone_m_data() Error: " + e);
        }
        return rowData;
    }

    public JSONArray getWard_m_data(){
        JSONArray rowData = new JSONArray();
        List<String> list = new ArrayList<String>();
        String meter_id = null;
        String query = " select IFNULL(ward_id_m, '') ward_id_m,"
                + " IFNULL(ward_no_m, '') ward_no_m,IFNULL(remark, '') remark,"
                + " IFNULL(zone_id_m, '') zone_id_m,IFNULL(ward_name, '')ward_name "
                       +" from ward_m wm "
                       +" where wm.active='Active'";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("ward_id_m", rset.getString("ward_id_m"));
                    obj.put("ward_no_m", rset.getString("ward_no_m"));
                    obj.put("remark", rset.getString("remark"));
                    obj.put("zone_id_m", rset.getString("zone_id_m"));
                    obj.put("ward_name", rset.getString("ward_name"));
                    //obj.put("city_id", rset.getString("city_id"));
                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("MeterSurveyWebServiceModel getWard_m_data() Error: " + e);
        }
        return rowData;
    }

    public JSONArray getArea_data(){
        JSONArray rowData = new JSONArray();
        List<String> list = new ArrayList<String>();
        String meter_id = null;
        String query = " select IFNULL(area_id, '') area_id,IFNULL(area_name, '') area_name,"
                + " IFNULL(remark, '') remark,IFNULL(ward_id_m, '') ward_id_m "
                       +" from area a "
                       +" where a.active='Y'";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("area_id", rset.getString("area_id"));
                    obj.put("area_name", rset.getString("area_name"));
                    obj.put("remark", rset.getString("remark"));
                    obj.put("ward_id_m", rset.getString("ward_id_m"));

                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("MeterSurveyWebServiceModel getAreaData() Error: " + e);
        }
        return rowData;
    }



    public int insertImageRecord(String image_name,int image_type_id) {
        int rowsAffected = 0;
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String image_uploaded_for = "Survey Image";
        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,type_of_image_id) "
                + " VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(imageQuery,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, image_name);
            pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
            pstmt.setString(3, current_date);
            pstmt.setString(4, "this file is for survey");
            pstmt.setInt(5, image_type_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                rowsAffected = rs.getInt(1);
            }
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error:metersurveyWebServicesModel--insertImageRecord-- " + e);
        }
        return rowsAffected;
    }

    public int insertPDFRecord(String image_name) {
        int rowsAffected = 0;
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String image_uploaded_for = "Survey Image";
        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description) "
                + " VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(imageQuery);
            pstmt.setString(1, image_name);
            pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
            pstmt.setString(3, current_date);
            pstmt.setString(4, "this file is for survey");
           // pstmt.setInt(5, image_type_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                rowsAffected = rs.getInt(1);
            }
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error:metersurveyWebServicesModel--insertImageRecord-- " + e);
        }
        return rowsAffected;
    }

    public int insertSurveyImageMapRecord(int survey_id,int gen_image_detail_id) {
        int rowsAffected = 0;       
        String imageQuery = "INSERT INTO survey_gen_image_map (survey_id, gen_image_detail_id) "
                + " VALUES(?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(imageQuery,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, survey_id);
            pstmt.setInt(2, gen_image_detail_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                rowsAffected = rs.getInt(1);
            }
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error:metersurveyWebServicesModel--insertSurveyImageMapRecord " + e);
        }
        return rowsAffected;
    }

    public int updateCalculatedLoad(String power, String meter_name_auto){
        int rowsAffected = 0;
        int revision = getMeterRevision(meter_name_auto);
        String query = "INSERT INTO meters (meter_id, revision, meter_name, security_deposit, sd_receipt_no, date, initial_reading, city_id, meter_service_number, poll_no, active, organisation_id, org_office_id, switching_point_id, feeder_id, sanctioned_load_kw, reason, final_revision, phase, accessed_load, effective_date, calculated_load, description, tariff_code, msn_first_part, msn_sec_part, msn_third_part, msn_fourth_part, ivrs_no, file_no, calculated_security_deposit, meter_name_auto, sanct_load_unit_id, latitude, longitude, ward_no, bill_sanction_load, premises_tariff_map_id, premises_tariff_map_rev, address_asper_Bill, general_img_details_id)"
                + " SELECT  meter_id, revision+1, meter_name, security_deposit, sd_receipt_no, date, initial_reading, city_id, meter_service_number, poll_no, active, organisation_id, org_office_id, switching_point_id, feeder_id, sanctioned_load_kw, reason, final_revision, phase, accessed_load, effective_date, 22, description, tariff_code, msn_first_part, msn_sec_part, msn_third_part, msn_fourth_part, ivrs_no, file_no, calculated_security_deposit, meter_name_auto, sanct_load_unit_id, latitude, longitude, ward_no, bill_sanction_load, premises_tariff_map_id, premises_tariff_map_rev, address_asper_Bill, general_img_details_id "
                + " FROM meters WHERE meter_name_auto = '"+ meter_name_auto +"' AND final_revision='VALID'";
        String updateQuery = "UPDATE meters SET final_revision='EXPIRED' WHERE meter_name_auto = '"+ meter_name_auto +"' AND revision=" + revision;
        try{
            rowsAffected = connection.prepareStatement(query).executeUpdate();
            if(rowsAffected > 0)
                rowsAffected = connection.prepareStatement(updateQuery).executeUpdate();
        }catch(Exception ex){
            System.out.println("ERROR : in updateCalculatedLoad in MeterSurveywebServiceModel : " + ex);
        }
        return rowsAffected;
    }

    public int getMeterRevision(String meter_name_auto){
        int revision = 0;
        String query = "SELECT revision FROM meters WHERE meter_name_auto = '"+ meter_name_auto +"' AND final_revision='VALID'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                revision = rs.getInt(1);
        }catch(Exception ex){
            System.out.println("ERROR : in getMeterRevision in MeterSurveywebServiceModel : " + ex);
        }
        return revision;
    }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public boolean validationCheck(int id, int rev_no, String survey_type) {
        int rowCount = 0;
        String id_field_name = "";
        String rev_field_name = "";
        if (survey_type.equals("switching_type_survey")) {
            id_field_name = "switching_point_detail_id = ";
            rev_field_name = "switching_rev_no = ";
        } else {
            id_field_name = "pole_id = ";
            rev_field_name = "pole_rev_no = ";
        }
        String query = "select count(*) from survey where " + id_field_name + id + " AND " + rev_field_name + rev_no + " AND status = 'Y'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                rowCount = rs.getInt("count(*)");
            }
        } catch (Exception e) {
            System.out.println("Exception inside validation check " + e);
        }
        if (rowCount > 0) {
            if (survey_type.equals("switching_type_survey")) {
                message = "Survey of this Switching Point has Already been done, Kindly Revise it..";
            } else {
                message = "Survey of this Pole has Already been done, Kindly De-Activate the former one";
            }
            msgBgColor = COLOR_ERROR;
        }
        return rowCount > 0 ? false : true;
    }

    public String getSwitchingPoint_id(String pole_no) {
        String switching_id_rev = "";
//        String query = "select spd.switching_point_detail_id, spd.switching_rev_no from switching_point_detail spd "
//                + " LEFT JOIN pole p ON p.switching_point_detail_id = spd.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no "
//                + " WHERE spd.active = 'Y' AND p.active = 'Y' AND p.isSwitchingPoint = 'Yes' AND p.pole_no = ? ";
        String query = "select meter_id from meters where poll_no='" + pole_no + "' and final_revision='VALID'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, pole_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switching_id_rev = rset.getString("meter_id");//String.valueOf(rset.getInt("switching_point_detail_id")) + "_" + String.valueOf(rset.getInt("switching_rev_no"));
            }
        } catch (Exception e) {
            System.out.println("Exception in SwitchingPoint_id() in surveyModel" + e);
        }
        return switching_id_rev;

    }

    public String getPoleId(String Pole_no) {
        String pole_id_rev = "";
        String query = " select pole_id , pole_rev_no from pole where active = 'Y' AND isSwitchingPoint = 'No' AND pole_no = ?";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, Pole_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            pole_id_rev = String.valueOf(rset.getInt("pole_id")) + "_" + String.valueOf(rset.getInt("pole_rev_no"));
        } catch (Exception e) {
            System.out.println("SurveyModel getPoleId() Error: " + e);
        }
        return pole_id_rev;
    }

    public int insertFuseRecord(String fuse_type) {

        String query = "INSERT INTO fuse (fuse_type, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fuse_type);
            pstmt.setString(2, "Done");
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


    public int insertMccbRecord(String mccb_type) {

        String query = "INSERT INTO mccb (mccb_type, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mccb_type);
            pstmt.setString(2, "Done");
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

    public int insertAutoSwitchRecord(SurveyBean surveyBean) {

        String query = "INSERT INTO switch_type(switch_type,switch_id, remark) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            // if (!surveyBean.getAuto_switch_type().isEmpty() || surveyBean.getAuto_switch_type() != null) {
            pstmt.setString(1, surveyBean.getAuto_switch_type());
            pstmt.setInt(2, 1);
            //}
            pstmt.setString(3, "Done");
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
    public int insertMainSwitchRecord(SurveyBean surveyBean) {

        String query = "INSERT INTO switch_type(switch_type,switch_id, remark) VALUES (?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            // if (!surveyBean.getAuto_switch_type().isEmpty() || surveyBean.getAuto_switch_type() != null) {
            pstmt.setString(1, surveyBean.getMain_switch_type());
            pstmt.setInt(2, 1);
            // } else if (!surveyBean.getMain_switch_type().isEmpty() || surveyBean.getMain_switch_type() != null) {
            //   pstmt.setString(1, surveyBean.getMain_switch_type());
            //   pstmt.setInt(2, 2);
            // }
            pstmt.setString(3, "Done");
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

    public int insertContacterRecord(SurveyBean surveyBean) {

        String query = "INSERT INTO contacter (contacter_type, remark) VALUES (?,?) ";
        String query1 = "INSERT INTO starter (starter_type, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = null;
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, surveyBean.getContacter_type());
                pstmt.setString(2, "Done");
            } else {
                pstmt = connection.prepareStatement(query1);
                pstmt.setString(1, surveyBean.getStarter_type());
                pstmt.setString(2, "Done");
            }


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

    public int insertContacterMakeRecord(SurveyBean surveyBean) {

        String query = "INSERT INTO contacter_make (contacter_make, remark) VALUES (?,?) ";
        String query1 = "INSERT INTO starter_make (starter_make, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = null;
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, surveyBean.getContacter_make());
                pstmt.setString(2, "Done");
            } else {
                pstmt = connection.prepareStatement(query1);
                pstmt.setString(1, surveyBean.getStarter_make());
                pstmt.setString(2, "Done");
            }


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

    public int insertEnclosureRecord(SurveyBean surveyBean) {

        String query = "INSERT INTO enclosure_type (enclosure_type, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, surveyBean.getEnclosure_type());
            pstmt.setString(2, "Done");
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

    public int insertReasonRecord(SurveyBean surveyBean) {

        String query = "INSERT INTO reason_type (reason_type, remark) VALUES (?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, surveyBean.getReason_type());
            pstmt.setString(2, "Done");
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

    public int writeImage(String imageName, List<File> file) {
        // getimage_destination_id();
        System.out.println("Start to write payment images in Repository");
        try {
            File srcfile = null;
            //      String dayOfMonthFolder = createAppropriateDirectories(destination_path);
            //File folder = new File(dayOfMonthFolder);
            boolean isuploaded = false;
            Iterator<File> fileItr = file.iterator();
            //  int number_of_file = folder.list().length;
            while (fileItr.hasNext()) {
//                Object image = fileItr.next();
//                tempSource = image.toString();
                //  number_of_file++;
                srcfile = fileItr.next();
                // String ext = srcfile.getName().replace(".", "%#");
                //ext = ext.split("%#")[1];
                String image = srcfile.toString();
                int index = image.indexOf('.');
                System.out.println(index);
                String ext = image.substring(index, image.length());
                // String image_name = imageName + "_" + number_of_file + ext;
                // System.out.println("" + image_name);
                // String forlder_path = dayOfMonthFolder + "/" + imageName + "_" + number_of_file;
                File desfile = new File(destination_path + "/" + imageName);
                isuploaded = srcfile.renameTo(desfile);

                if (isuploaded) {
                    message = "Image Uploaded Successfully.";
                    msgBgColor = COLOR_OK;

                }

            }
            File deleteFile = new File(getRepositoryPath("Temp"));
            deleteDirectory(deleteFile);
        } catch (Exception ex) {
            System.out.println("File write error" + ex);
            message = "Cannot upload the image, some error.";
            msgBgColor = COLOR_ERROR;
            PreparedStatement pstmt;
            int rowsAffected = 0, id;
            String query, query1;
            query = "UPDATE survey"
                    + "SET general_image_details_id = NULL  "
                    + " where image_name = ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query);
                pstmt.setString(1, imageName);
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: GeneralImageDetailsModel-record cannot updated when image is not uploaded successfully-" + e);
            }
            query1 = "DELETE from general_image_details WHERE general_image_details_id= ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query1);
                pstmt.setInt(1, getgeneral_image_details_id(imageName));
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: ReadMailModel-record cannot deleted when image is not uploaded successfully-" + e);
            }
        }
        return message.equals("Image Uploaded Successfully.") ? 1 : 0;
    }

    public boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }

    public int getgeneral_image_details_id(String image_name) {
        String query;
        int key_person_id = 0;
        query = "select general_image_details_id from general_image_details where image_name='" + image_name + "' and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("general_image_details_id");

            }

        } catch (Exception ex) {
            System.out.println("Error:  getgeneral_image_details_id-" + ex);
        }
        return key_person_id;
    }

    public int getImage_type_id(String image_name) {
        String query;
        int type_of_image_id = 0;
        query = "select type_of_image_id from type_of_image where image_type='"+image_name+"' " ;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                type_of_image_id = rset.getInt("type_of_image_id");
            }
        } catch (Exception ex) {
            System.out.println("Error:  getImage_type_id()-" + ex);
        }
        return type_of_image_id;
    }

    public int getSurveyId() {
        String query;
        int survey_id = 0;
        query = "select MAX(survey_id) as id from survey where status='Y'  ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                survey_id = rset.getInt("id");
                survey_id = survey_id + 1;
            }

        } catch (Exception ex) {
            System.out.println("Error: getSurveyId() " + ex);
        }
        return survey_id;
    }

    public int getimage_destination_id(String image_uploaded_for) {
        String query;
        int image_destination_id = 0;
        query = " SELECT image_destination_id, destination_path from image_destination AS id , image_uploaded_for As i "
                + " WHERE id.image_uploaded_for_id=i.image_uploaded_for_id AND i.image_uploaded_for_name= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                image_destination_id = rset.getInt("image_destination_id");
            }

        } catch (Exception ex) {
            System.out.println("Error: getimage_destination_id-" + ex);
        }
        return image_destination_id;
    }

    public String getRepositoryPath(String image_uploaded_for) {
        String query;

        query = " SELECT destination_path from image_destination AS id , image_uploaded_for As i "
                + " WHERE id.image_uploaded_for_id=i.image_uploaded_for_id AND i.image_uploaded_for_name= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, image_uploaded_for);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                this.destination_path = rset.getString("destination_path");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return this.destination_path;
    }

    public String getFuseId(String fuse_type) {
        String fuse_id = "";
        String query = "select fuse_id from fuse where fuse_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, fuse_type);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                fuse_id = rset.getString("fuse_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return fuse_id;
    }

    public String getContracterId(String contracter_type) {
        String contracter_id = "";
        String query = "select contacter_id from contacter where contacter_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, contracter_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                contracter_id = rset.getString("contacter_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return contracter_id;
    }

    public int getStarterId(String contracter_type) {
        int contracter_id = 0;
        String query = "select starter_id from starter where starter_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, contracter_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                contracter_id = rset.getInt("starter_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getStarterId() in surveyModel" + e);
        }
        return contracter_id;
    }

    public String getMccbId(String mccb_type) {
        String mccb_id = "";
        String query = "select mccb_id from mccb where mccb_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                mccb_id = rset.getString("mccb_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return mccb_id;
    }

    public int getTimerId(String timer_type) {
        int timer_id = 0;
        String query = "select timer_id from timer where timer_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, timer_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                timer_id = rset.getInt("timer_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return timer_id;
    }

    public int getSurveyIdForImage(String service_no,String survey_type,String survey_date){
        int revision = 0;
        String query="";
        if(survey_type.equals("Tube Well")){
        //query = "SELECT tube_well_survey_id FROM tube_well_survey WHERE service_conn_no = '"+ service_no +"' AND final_revision='VALID'";
        query = "select tube_well_survey_id from tube_well_survey as tw,survey as s where s.survey_date='"+ survey_date +"' and s.survey_id=tw.tube_well_survey_id and tw.service_conn_no ='"+ service_no +"'  order by tube_well_survey_id desc limit 1";
        }else if(survey_type.equals("Switching Point")){
        //query = "SELECT tube_well_survey_id FROM tube_well_survey WHERE service_conn_no = '"+ service_no +"' AND final_revision='VALID'";
        query = "select switching_point_survey_id from switching_point_survey as tw,survey as s where s.survey_date='"+ survey_date +"' and s.survey_id=tw.switching_point_survey_id and tw.service_conn_no ='"+ service_no +"'  order by switching_point_survey_id desc limit 1";
        }else{
        query = "SELECT survey_id FROM survey WHERE service_no = '"+ service_no +"' AND final_revision='VALID'";
        }
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                revision = rs.getInt(1);
        }catch(Exception ex){
            System.out.println("ERROR : in getSurveyIdForImage in MeterSurveywebServiceModel : " + ex);
        }
        return revision;
    }

    public String getFuseTYpe(int fuse_id) {
        String fuse_type = "";
        String query = "select fuse_type from fuse where fuse_id='" + fuse_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, fuse_type);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                fuse_type = rset.getString("fuse_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return fuse_type;
    }

    public String getContracterType(int contracter_id) {
        String contracter_type = "";
        String query = "select contacter_type from contacter where contacter_id'" + contracter_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            // ps.setString(1, contracter_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                contracter_type = rset.getString("contacter_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return contracter_type;
    }

    public String getMccbType(int mccb_id) {
        String mccb_type = "";
        String query = "select mccb_type from mccb where mccb_id='" + mccb_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                mccb_type = rset.getString("mccb_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return mccb_type;
    }

    public String getTimerId(int timer_id) {
        String timer_type = "";
        String query = "select timer_id from timer where timer_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, timer_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                timer_type = rset.getString("timer_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return timer_type;
    }

    public String getReasonId(String reason_type) {
        String reason_id = "";
        String query = "select reason_id from reason_type where reason_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, reason_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                reason_id = rset.getString("reason_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getReasonId() in surveyModel" + e);
        }
        return reason_id;
    }

    public String getSwitchId(String switch_type) {

        String switch_type_id = "";
        String query = " SELECT st.switch_type_id FROM switch_type as st,switch as s WHERE  "
                + "s.switch_id=st.switch_id and st.switch_type='" + switch_type + "' group by switch_type_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                switch_type_id = rset.getString("switch_type_id");
            } else {
                switch_type_id = "";
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getSwitchId() Error: " + e);
        }
        return switch_type_id;
    }

    public String getEnclosureTypeId(String enclosure_type) {

        String enclosure_type_id = "";
        String query = " SELECT enclosure_type_id FROM enclosure_type WHERE  enclosure_type='" + enclosure_type + "'"
                + "group by enclosure_type_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                enclosure_type_id = rset.getString("enclosure_type_id");
            } else {
                enclosure_type_id = "";
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return enclosure_type_id;
    }

    public int getStarterMakeId(String starter_make) {

        int starter_make_id = 0;
        String query = " SELECT starter_make_id FROM starter_make WHERE starter_make='" + starter_make + "'  "
                + "group by starter_make_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                starter_make_id = rset.getInt("starter_make_id");
            } else {
                starter_make_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getStarterMakeId() Error: " + e);
        }
        return starter_make_id;
    }

    public String getContacterMakeId(String make_type) {

        String make_id = "";
        String query = " SELECT contacter_make_id FROM contacter_make WHERE contacter_make='" + make_type + "'  "
                + "group by contacter_make_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                make_id = rset.getString("contacter_make_id");
            } else {
                make_id = "";
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getContacterMakeId() Error: " + e);
        }
        return make_id;
    }

    public int getContacterStarterType() {

        int make_id = 0;
        String query = "SELECT contacter_type AS key1, 'c' AS value1 FROM contacter "
                + "UNION SELECT starter_type AS key1, 's' AS value1 FROM starter ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                //  if(rset.getString("value1").equals("c")){
                JSONObject obj = new JSONObject();
                JSONArray contacter = new JSONArray();
                contacter.put(rset.getString("key1"));
                obj.put(rset.getString("value1"), contacter);
                // }
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return make_id;
    }

//    public JSONArray showData(String imei) {
//
//        JSONArray rowData = new JSONArray();
//        GetDate getDate = new GetDate();
//        String year = String.valueOf(getDate.getCurrentYear());
//        String previous_month = getDate.getPreviousMonth(getDate.getCurrentMonth(), year);
//        List<String> list = new ArrayList<String>();
//        String meter_id = null;
//        String meter_query = "select meter_id from meters_status";
//        if(imei != null && !imei.isEmpty())
//            meter_query = "select meter_id from meters_status where status='Yes'";
//        String query = "select m.longitude,m.latitude,m.ivrs_no, if(mb.current_reading is null,0.0,mb.current_reading) as current_reading,if(m.switching_point_id is not null,"
//                + "concat_ws(sp.address1,sp.address2,sp.address3),concat_ws(of.address_line1,of.address_line2,of.address_line3) ) as address,"
//                + "tp.type_of_premsis, m.meter_id, m.meter_service_number , m.poll_no , m.meter_name, m.switching_point_id ,m.initial_reading,"
//                + " m.phase,o.organisation_name , tp.type_of_premsis,tp.premises_individual_detail, c.city_name, m.meter_name_auto,m.meter_service_number,"
//                + "m.latitude,m.longitude FROM meters as m LEFT JOIN mpeb_meter_bill as  mb ON mb.meter_id = m.meter_id AND mb.final_revision = 'VALID' "
//                + "and mb.bill_month='Nov-2015' left join switching_point as sp ON m.switching_point_id=sp.switching_point_id left join"
//                + " org_office as of ON m.org_office_id=of.org_office_id,city c ,organisation_name o, type_of_premises tp , feeder f, zone z,division d, "
//                + "tarrif_gen_details t, premises_tariff_map ptm, company cy, circle ci  ,meters_status as ms "
//                + " WHERE ms.meter_id=m.meter_id  "
//                + " and IF('"+ imei +"'='', ms.status LIKE '%%', ms.status = 'Yes')"
//                + " and IF(0=0 , m.org_office_id like '%%' OR m.org_office_id is null,"
//                + " m.org_office_id = 0 ) AND   m.city_id = c.city_id  and m.organisation_id= o.organisation_id  and m.final_revision='VALID'"
//                + " AND cy.company_id = ci.company_id and ci.circle_id = d.circle_id  and  f.zone_id = z.zone_id and  z.division_id = d.division_id"
//                + "  and m.active='Y'  and m.feeder_id = f.feeder_id and m.premises_tariff_map_id = ptm.premises_tariff_map_id AND ptm.active='Y'"
//                + " and d.active = 'Y' AND ptm.type_of_premises_id = tp.type_of_premises_id and   ptm.tarrif_gen_details_id = t.tarrif_gen_details_id"
//                + " and t.active = 'Y'  AND  if(0=0 , cy.company_id like '%%' , cy.company_id=0)"
//                + "  AND IF ('ALL'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = 'ALL')"
//                + " AND m.premises_tariff_map_id not IN (10,12,13) ";            //ID of Building type connection
////        String query = "SELECT pole_no_s as poll_no,meter_no_s as meter_name,p.type_of_premsis,meter_id,longitude,"
////                + "lattitude as latitude,ph as phase,measured_load as current_reading,ivrs_no as meter_service_number "
////                + "FROM tube_well_detail as t ,type_of_premises as p "
////                + " where p.type_of_premises_id=t.type_of_premises_id and  active='Y' and meter_id is not null";
//
//        try {
//            java.sql.PreparedStatement pstmt = null;
//            ResultSet rset = null;
////            pstmt = connection.prepareStatement(meter_query);
////            rset = pstmt.executeQuery();
////            while (rset.next()) {
////                meter_id = rset.getString("meter_id");
////                list.add(meter_id);
////            }
////
////            pstmt.close();
////            Iterator<String> itr = list.iterator();
////            while (itr.hasNext()) {
////                String listString = itr.next();
//                pstmt = connection.prepareStatement(query);
//                //pstmt.setString(1, listString);
//                rset = pstmt.executeQuery();
//
//                while (rset.next()) {
//
//                    JSONObject obj = new JSONObject();
//                    //    int numColumns = rsmd.getColumnCount();
//                    //   for (int i = 1; i < numColumns + 1; i++) {
//
//                    //   String column_name = rsmd.getColumnLabel(i);
//                    obj.put("pole_type", rset.getString("type_of_premsis"));
//                    obj.put("meter_id", rset.getString("meter_id"));
//                    obj.put("poll_no", rset.getString("poll_no"));
//                    obj.put("meter_name", rset.getString("meter_name"));
//                    obj.put("phase", rset.getString("phase"));
//                    obj.put("initial_reading", rset.getString("current_reading"));
//                    obj.put("meter_address", rset.getString("address"));
//                    obj.put("lattitude", rset.getString("latitude"));
//                    obj.put("longitude", rset.getString("longitude"));
//                    obj.put("service_number", rset.getString("ivrs_no"));
//                    obj.put("meter_name_auto", rset.getString("meter_name_auto"));
//
//
//
////                  obj.put("pole_type", rset.getString("type_of_premsis"));
////                obj.put("meter_id", rset.getString("meter_id"));
////                obj.put("poll_no", rset.getString("poll_no"));
////                obj.put("meter_name", rset.getString("meter_name"));
////                obj.put("phase", rset.getString("phase"));
////                obj.put("initial_reading", rset.getString("current_reading"));
////                //obj.put("meter_address", rset.getString("address"));
////                obj.put("meter_address", "abc");
////                obj.put("service_number", rset.getString("meter_service_number"));
////                obj.put("lattitude", rset.getString("latitude"));
////                obj.put("longitude", rset.getString("longitude"));
//
//                    //obj.put("Company List", rowData);
////                    if(updateStatusData(meter_id)>0)
////                    {
//                    rowData.put(obj);
////                    }
//
//                    //}
//
//
//                    // }
//
//                }
//
//          //  }
//        } catch (Exception e) {
//            System.out.println("SurveyModel showData() Error: " + e);
//        }
//        return rowData;
//    }

    public JSONArray showData(String imei) {

        JSONArray rowData = new JSONArray();
        GetDate getDate = new GetDate();
        String year = String.valueOf(getDate.getCurrentYear());
        String previous_month = getDate.getPreviousMonth(getDate.getCurrentMonth(), year);
        List<String> list = new ArrayList<String>();
        String meter_id = null;
        String meter_query = "select meter_id from meters_status";
        if(imei != null && !imei.isEmpty())
            meter_query = "select meter_id from meters_status where status='Yes'";
        String query = "select twd.area_id,m.longitude,m.latitude,m.ivrs_no, if(mb.current_reading is null,0.0,mb.current_reading) as current_reading,if(m.switching_point_id is not null,"
                + "concat_ws(sp.address1,sp.address2,sp.address3),concat_ws(offi.address_line1,offi.address_line2,offi.address_line3) ) as address,"
                + "tp.type_of_premsis, m.meter_id, m.meter_service_number , m.poll_no , m.meter_name, m.switching_point_id ,m.initial_reading,"
                + " m.phase,o.organisation_name , tp.type_of_premsis,tp.premises_individual_detail, c.city_name, m.meter_name_auto,m.meter_service_number,"
                + "m.latitude,m.longitude FROM meters as m LEFT JOIN mpeb_meter_bill as  mb ON mb.meter_id = m.meter_id AND mb.final_revision = 'VALID' "
                + "and mb.bill_month='Nov-2015' left join switching_point as sp ON m.switching_point_id=sp.switching_point_id "
                + " left join tube_well_detail as twd ON m.ivrs_no=twd.ivrs_no "
                + "left join org_office as offi ON m.org_office_id=offi.org_office_id,city c ,organisation_name o, type_of_premises tp , feeder f, zone z,division d, "
                + "tarrif_gen_details t, premises_tariff_map ptm, company cy, circle ci  ,meters_status as ms "
                + " WHERE ms.meter_id=m.meter_id  "
                + " and IF('"+ imei +"'='', ms.status LIKE '%%', ms.status = 'Yes')"
                + " and IF(0=0 , m.org_office_id like '%%' OR m.org_office_id is null,"
                + " m.org_office_id = 0 ) AND   m.city_id = c.city_id  and m.organisation_id= o.organisation_id  and m.final_revision='VALID'"
                + " AND cy.company_id = ci.company_id and ci.circle_id = d.circle_id  and  f.zone_id = z.zone_id and  z.division_id = d.division_id"
                + "  and m.active='Y'  and m.feeder_id = f.feeder_id and m.premises_tariff_map_id = ptm.premises_tariff_map_id AND ptm.active='Y'"
                + " and d.active = 'Y' AND ptm.type_of_premises_id = tp.type_of_premises_id and   ptm.tarrif_gen_details_id = t.tarrif_gen_details_id"
                + " and t.active = 'Y'  AND  if(0=0 , cy.company_id like '%%' , cy.company_id=0)"
                + "  AND IF ('ALL'='ALL' ,tp.type_of_premsis LIKE '%%' ,tp.type_of_premsis = 'ALL')"
                + " AND m.premises_tariff_map_id not IN (10,12,13) "
                + " and twd.active='Y' ";            //ID of Building type connection
//        String query = "SELECT pole_no_s as poll_no,meter_no_s as meter_name,p.type_of_premsis,meter_id,longitude,"
//                + "lattitude as latitude,ph as phase,measured_load as current_reading,ivrs_no as meter_service_number "
//                + "FROM tube_well_detail as t ,type_of_premises as p "
//                + " where p.type_of_premises_id=t.type_of_premises_id and  active='Y' and meter_id is not null";

        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
//            pstmt = connection.prepareStatement(meter_query);
//            rset = pstmt.executeQuery();
//            while (rset.next()) {
//                meter_id = rset.getString("meter_id");
//                list.add(meter_id);
//            }
//
//            pstmt.close();
//            Iterator<String> itr = list.iterator();
//            while (itr.hasNext()) {
//                String listString = itr.next();
                pstmt = connection.prepareStatement(query);
                //pstmt.setString(1, listString);
                rset = pstmt.executeQuery();

                while (rset.next()) {

                    JSONObject obj = new JSONObject();
                    //    int numColumns = rsmd.getColumnCount();
                    //   for (int i = 1; i < numColumns + 1; i++) {

                    //   String column_name = rsmd.getColumnLabel(i);
                    obj.put("area_id", rset.getString("area_id") == null? "" : rset.getString("area_id"));
                    obj.put("pole_type", rset.getString("type_of_premsis") == null? "" : rset.getString("type_of_premsis"));
                    obj.put("meter_id", rset.getString("meter_id") == null? "" : rset.getString("meter_id"));
                    obj.put("poll_no", rset.getString("poll_no") == null? "" : rset.getString("poll_no"));
                    obj.put("meter_name", rset.getString("meter_name") == null? "" : rset.getString("meter_name"));
                    obj.put("phase", rset.getString("phase") == null? "" : rset.getString("phase"));
                    obj.put("initial_reading", rset.getString("current_reading") == null? "" : rset.getString("current_reading"));
                    obj.put("meter_address", rset.getString("address") == null?"" : rset.getString("address"));
                    obj.put("lattitude", rset.getString("latitude") == null? "" : rset.getString("latitude"));
                    obj.put("longitude", rset.getString("longitude") == null ? "" : rset.getString("longitude"));
                    obj.put("service_number", rset.getString("ivrs_no") == null? "" : rset.getString("ivrs_no"));
                    obj.put("meter_name_auto", rset.getString("meter_name_auto") == null? "" : rset.getString("meter_name_auto"));



//                  obj.put("pole_type", rset.getString("type_of_premsis"));
//                obj.put("meter_id", rset.getString("meter_id"));
//                obj.put("poll_no", rset.getString("poll_no"));
//                obj.put("meter_name", rset.getString("meter_name"));
//                obj.put("phase", rset.getString("phase"));
//                obj.put("initial_reading", rset.getString("current_reading"));
//                //obj.put("meter_address", rset.getString("address"));
//                obj.put("meter_address", "abc");
//                obj.put("service_number", rset.getString("meter_service_number"));
//                obj.put("lattitude", rset.getString("latitude"));
//                obj.put("longitude", rset.getString("longitude"));

                    //obj.put("Company List", rowData);
//                    if(updateStatusData(meter_id)>0)
//                    {
                    rowData.put(obj);
//                    }

                    //}


                    // }

                }

          //  }
        } catch (Exception e) {
            System.out.println("SurveyModel showData() Error: " + e);
        }
        return rowData;
    }

    public JSONArray getTubeWellBoreData() {

        JSONArray rowData = new JSONArray();
//        GetDate getDate = new GetDate();
//        String year = String.valueOf(getDate.getCurrentYear());
//        String previous_month = getDate.getPreviousMonth(getDate.getCurrentMonth(), year);
        List<String> list = new ArrayList<String>();
        String meter_id = null;

//        if(imei != null && !imei.isEmpty())
//            meter_query = "select meter_id from meters_status where status='Yes'";
        String query = "select tbd.tubewell_bore_data_id,"
                + "  tbd.tube_well_detail_id, "
                + " td.ivrs_no,tbd.depth,tbd.depth,tbd.bore_diameter, "
                       +" tbd.bore_casing_type_Id, "
                + " tbd.motore_capacity,"
                + " tbd.motor_type_id,"
                + " tbd.suction_diameter, "
                       +" tbd.delivery_diameter,"
                + " tbd.discharge_capacity,"
                + " tbd.contact_person_name,"
                + " tbd.contact_person_address, "
                       +" tbd.contact_person_mobile_no,"
                + " tbd.operated_by,"
                + " tbd.type_of_use_id,"
                + " tbd.operator_name, "
                       +" tbd.operator_mobile_no,"
                + " tbd.date_of_installation,"
                + " tbd.ward_id,"
                + " tbd.created_date "
                       +" from tubewell_bore_data tbd,tube_well_detail td "
                       +" where tbd.tube_well_detail_id=td.tube_well_detail_id "
                       +" and tbd.active='Y'";

        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("tubewell_bore_data_id", rset.getString("tubewell_bore_data_id") == null? "" : rset.getString("tubewell_bore_data_id"));
                    obj.put("tube_well_detail_id", rset.getString("tube_well_detail_id") == null? "" : rset.getString("tube_well_detail_id"));
                    obj.put("ivrs_no", rset.getString("ivrs_no") == null? "" : rset.getString("ivrs_no"));
                    obj.put("depth", rset.getString("depth") == null? "" : rset.getString("depth"));
                    obj.put("bore_diameter", rset.getString("bore_diameter") == null? "" : rset.getString("bore_diameter"));
                    obj.put("bore_casing_type_Id", rset.getString("bore_casing_type_Id") == null? "" : rset.getString("bore_casing_type_Id"));
                    obj.put("motore_capacity", rset.getString("motore_capacity") == null? "" : rset.getString("motore_capacity"));
                    obj.put("motor_type_id", rset.getString("motor_type_id") == null? "" : rset.getString("motor_type_id"));
                    obj.put("suction_diameter", rset.getString("suction_diameter") == null? "" : rset.getString("suction_diameter"));
                    obj.put("delivery_diameter", rset.getString("delivery_diameter") == null? "" : rset.getString("delivery_diameter"));
                    obj.put("discharge_capacity", rset.getString("discharge_capacity") == null? "" : rset.getString("discharge_capacity"));
                    obj.put("contact_person_name", rset.getString("contact_person_name") == null? "" : rset.getString("contact_person_name"));
                    obj.put("contact_person_address", rset.getString("contact_person_address") == null? "" :  rset.getString("contact_person_address"));
                    obj.put("contact_person_mobile_no", rset.getString("contact_person_mobile_no")== null? "" : rset.getString("contact_person_mobile_no"));

                    obj.put("operated_by", rset.getString("operated_by") == null? "" : rset.getString("operated_by"));
                    obj.put("type_of_use_id", rset.getString("type_of_use_id") == null? "" : rset.getString("type_of_use_id"));
                    obj.put("operator_name", rset.getString("operator_name") == null? "" : rset.getString("operator_name"));
                    obj.put("operator_mobile_no", rset.getString("operator_mobile_no") == null? "" : rset.getString("operator_mobile_no"));

                    obj.put("date_of_installation", rset.getString("date_of_installation") == null? "" : rset.getString("date_of_installation"));
                    obj.put("ward_id", rset.getString("ward_id") == null? "" : rset.getString("ward_id"));
                    obj.put("created_date", rset.getString("created_date") == null? "" : rset.getString("created_date"));

                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("SurveyModel showData() Error: " + e);
        }
        return rowData;
    }
    public JSONArray getTypeOfUseData() {

        JSONArray rowData = new JSONArray();

        List<String> list = new ArrayList<String>();
        String meter_id = null;


        String query = "select IFNULL(type_of_use_id, '') type_of_use_id,"
                + " IFNULL(type_of_use_name, '') type_of_use_name "
                       +" from type_of_use ";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("type_of_use_id", rset.getString("type_of_use_id"));
                    obj.put("type_of_use_name", rset.getString("type_of_use_name"));
                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("SurveyModel showData() Error: " + e);
        }
        return rowData;
    }
    public JSONArray getBoreCasingTypeData() {

        JSONArray rowData = new JSONArray();

        List<String> list = new ArrayList<String>();
        String meter_id = null;


        String query = "select IFNULL(bore_casing_type_id, '') bore_casing_type_id,"
                + " IFNULL(bore_casing_type_name, '') bore_casing_type_name "
                       +" from bore_casing_type ";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("bore_casing_type_id", rset.getString("bore_casing_type_id"));
                    obj.put("bore_casing_type_name", rset.getString("bore_casing_type_name"));
                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("SurveyModel showData() Error: " + e);
        }
        return rowData;
    }
    public JSONArray getMotorTypeData() {
        JSONArray rowData = new JSONArray();
        List<String> list = new ArrayList<String>();
        String meter_id = null;
        String query = " select  IFNULL(motor_type_id, '') motor_type_id,"
                + "  IFNULL(motor_type_name, '') motor_type_name "
                      +" from motor_type ";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("motor_type_id", rset.getString("motor_type_id"));
                    obj.put("motor_type_name", rset.getString("motor_type_name"));
                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("SurveyModel showData() Error: " + e);
        }
        return rowData;
    }

    public JSONArray getImageType() {
        JSONArray rowData = new JSONArray();
        List<String> list = new ArrayList<String>();
        String meter_id = null;
        String query = " select IFNULL(type_of_image_id, '') type_of_image_id,"
                + " IFNULL(image_type, '') image_type "
                      +" from type_of_image ";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("type_id", rset.getString("type_of_image_id"));
                    obj.put("type_name", rset.getString("image_type"));
                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("SurveyModel getImageType( Error: " + e);
        }
        return rowData;
    }

    public JSONArray getWardData() {
        JSONArray rowData = new JSONArray();
        List<String> list = new ArrayList<String>();
        String meter_id = null;
        String query = " select ward_no,ward_id,zone_id "
                       +" from ward w "
                       +" where w.active='Y' ";
        try {
            java.sql.PreparedStatement pstmt = null;
            ResultSet rset = null;
                pstmt = connection.prepareStatement(query);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("ward_no", rset.getString("ward_no"));
                    obj.put("ward_id", rset.getString("ward_id"));
                    obj.put("zone_id", rset.getString("zone_id"));
                    rowData.put(obj);
                }
        } catch (Exception e) {
            System.out.println("SurveyModel showData() Error: " + e);
        }
        return rowData;
    }




    public int updateStatusData(String meter_id){
        int row_affected=0;
        String query="UPDATE meters_status set status='Yes' where meter_id='"+meter_id+"' ";
        try{
            PreparedStatement pstmt=connection.prepareStatement(query);
            row_affected=pstmt.executeUpdate();

        }
        catch(Exception e){}


        return row_affected;
    }

    public int insertSurveyCordinates(String lat, String lng, String imei, String type, String mobile_no){
        int rowAffected = 0;
        String query = "INSERT INTO survey_cordinates (latitude, longitude, imei_no, contact_no) VALUES("+ lat +","+ lng +",'"+ imei +"', '"+ mobile_no +"')";
        try{
            rowAffected = connection.prepareStatement(query).executeUpdate();
        }catch(Exception ex){
            System.out.println("ERROR: in insertSurveyCordinates in MeterSurveyWebServicesModel : " + ex);
        }
        return rowAffected;
    }
    
    
     
     public String getDestinationPath(String image_uploaded_for) {
        String destination_path = "";
        String query = " SELECT destination_path FROM image_destination where image_uploaded_for_id=13";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return destination_path;
    }
     
       public int insertRecordPrimary(JSONObject json1, String imagefirstpolePath,String imagelastpolePath) {
            int roweffected =0;
           try{
               
                String ivrs_no = "";
                String meter_no = "";
                String circuitno = "";
                String accuracyfirstpole = "";
                String accuracylastpole = "";
                String parent = "";
                double longitudefirstpole = 0;
                double latitudefirstpole = 0;
                double altitudefirstpole = 0;
                double longitudelastpole = 0;
                double latitudelastpole = 0;
                double altitudelastpole = 0;
                String is_child ="";
                String time ="";
                int swicthing_point_detail_id=0;
                ivrs_no = json1.get("ivrs_no").toString();
                meter_no = json1.get("meter_no").toString();
                circuitno = json1.get("circuitno").toString();
                time = json1.get("survey_time").toString();
               
                
                
                try{
                    longitudefirstpole = Double.parseDouble(json1.get("longitudefirstpole").toString());
                    latitudefirstpole = Double.parseDouble(json1.get("latitudefirstpole").toString());
                    altitudefirstpole = Double.parseDouble(json1.get("altitudefirstpole").toString());
                    longitudelastpole = Double.parseDouble(json1.get("longitudelastpole").toString());
                    latitudelastpole = Double.parseDouble(json1.get("latitudelastpole").toString());
                    altitudelastpole = Double.parseDouble(json1.get("altitudelastpole").toString());
                    accuracyfirstpole = json1.get("accuracyfirstpole").toString();
                 accuracylastpole = json1.get("accuracylastpole").toString();
                  swicthing_point_detail_id = Integer.parseInt(json1.get("spdt_id").toString());
                }catch(Exception e)
                {
                    swicthing_point_detail_id=1;
                    System.out.println("Exception"+e);
                }
                String circuit_name="";
                int first_pole_id=0;
                first_pole_id=1;
                
                int last_pole_id=0;
                last_pole_id=1;
                int cable_type_id=0;
                cable_type_id=1;
                int parent_id=0;
                parent_id=getparentid(meter_no);
                
                String query = "insert into circuit(circuit_name, irvs_no, circuitno, "
                        + " switching_point_detail_id, first_pole_id, last_pole_id, cable_type_id, parent_id, "
                        + " time, is_child, imageoffirstpole, imageoflastpole, "
                        + " lattitudefirstpole, longitudefirstpole, altitudefirstpole, accuracyfirstpole, lattitudelasttpole, "
                        + " longitudelasttpole, altitudelastpole, accuracylasttpole"
                        + " )" + " VALUES('" +meter_no+"','"+ ivrs_no+"','"+ circuitno+"',"
                        + swicthing_point_detail_id+","+ first_pole_id+","+ last_pole_id+","+ cable_type_id+","
                        + parent_id + ",'"+ time+"','"+ is_child+"','"+ imagefirstpolePath + "',"
                        + " '"+ imagelastpolePath+"',"+ latitudefirstpole+","+ longitudefirstpole+","+ altitudefirstpole+","
                        + " '"+ accuracyfirstpole+"',"+ latitudelastpole+","+ longitudelastpole+","+ altitudelastpole+","
                        + "'"+ accuracylastpole+"')";
//String query = "insert into circuit(circuit_name, irvs_no, circuitno, "
//                        + " switching_point_detail_id, parent_id, "
//                        + " time, is_child, imageoffirstpole, imageoflastpole, "
//                        + " lattitudefirstpole, longitudefirstpole, altitudefirstpole, accuracyfirstpole, lattitudelasttpole, "
//                        + " longitudelasttpole, altitudelastpole, accuracylasttpole"
//                        + " )" + " VALUES('" +meter_no+"','"+ ivrs_no+"','"+ circuitno+"',"
//                        + swicthing_point_detail_id+","
//                        + parent_id + ",'"+ time+"','"+ is_child+"','"+ imagefirstpolePath + "',"
//                        + " '"+ imagelastpolePath+"',"+ latitudefirstpole+","+ longitudefirstpole+","+ altitudefirstpole+","
//                        + " '"+ accuracyfirstpole+"',"+ latitudelastpole+","+ longitudelastpole+","+ altitudelastpole+","
//                        + "'"+ accuracylastpole+"')";
                try {
                    PreparedStatement psmt = connection.prepareStatement(query);
                    
                    roweffected= psmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("DataSendModel Error: " + e);
                }
                
                
               
                
            }catch(JSONException ex)
            {
             Logger.getLogger(MeterSurveyWebServicesModel.class.getName()).log(Level.SEVERE, null, ex);
            }
         return roweffected;
    }
         public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from primary_survey ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("TableViewModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
             public int getNoOfRowsCircuit() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from circuit ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("TableViewModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
             public int getparentid(String meter_no) {
        int circuit_id = 0;
        try {
            ResultSet rset = connection.prepareStatement("select id from circuit where circuit_name= "+meter_no).executeQuery();
            rset.next();
            circuit_id = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("TableViewModel getNoOfRows() Error: " + e);
        }
        return circuit_id;
    }
    public int getNoOfRowsCricuit() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from circuit ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("TableViewModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
   
}


