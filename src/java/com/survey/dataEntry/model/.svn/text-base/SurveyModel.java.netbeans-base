/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.survey.tableClasses.SurveyBean;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author JPSS
 */
public class SurveyModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    String destination_path = "";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }

    public List<String> getPoleNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select pole_no from pole where active = 'Y' AND isSwitchingPoint = 'No' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("pole_no");
                if (pole_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Pole No Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getSearchPole_No(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select p.pole_no from survey s left join pole p on "
                + " if(s.pole_id != null, s.pole_id = p.pole_id and s.pole_rev_no = p.pole_rev_no, "
                + "  s.switching_point_detail_id = p.switching_point_detail_id and s.switching_rev_no = p.switching_rev_no ) "
                + " where p.active = 'Y' GROUP BY p.pole_no";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("pole_no");
                if (pole_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Pole No Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getSwitchingPoleNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select p.pole_no from switching_point_detail spd "
                + " LEFT JOIN pole p ON p.switching_point_detail_id = spd.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no "
                + " WHERE spd.active = 'Y' AND p.active = 'Y' AND p.isSwitchingPoint = 'Yes' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("pole_no");
                if (pole_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Switching Pole Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getSwichingPointNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT pole_no_s FROM switching_point_detail GROUP BY pole_no_s ORDER BY pole_no_s ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no_s = rset.getString("pole_no_s");
                if (pole_no_s.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no_s);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Switching Point No exists.");
            }
        } catch (Exception e) {
            System.out.println("getSwitchingPointNo ERROR inside SurveyModel - " + e);
        }
        return list;
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

    public int getSwichingPointId(String Pole_no_s) {
        int switching_point_id = 0;
        String query = " SELECT switching_point_detail_id FROM switching_point_detail WHERE switching_point_detail_id = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, Pole_no_s);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            switching_point_id = rset.getInt("pole_id");
        } catch (Exception e) {
            System.out.println("SurveyModel getSwitchingPointId() Error: " + e);
        }
        return switching_point_id;
    }

    public int getSurveyId() {
        int survey_id = 0;
        try {
            String query = "select max(survey_id) from survey ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                survey_id = rs.getInt("max(survey_id)");
            }

        } catch (Exception e) {
            System.out.println("Error: SurveyModel getSurveyId():" + e);
        }
        return survey_id;
    }

    public int getSwitchingPointSurveyId() {
        int switching_point_survey_id = 0;
        try {
            String query = "select max(switching_point_survey_id) from switching_point_survey ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                switching_point_survey_id = rs.getInt("max(switching_point_survey_id)");
            }

        } catch (Exception e) {
            System.out.println("Error: SurveyModel getSwitchingPointSurveyId():" + e);
        }
        return switching_point_survey_id;
    }

    private int getRevNo(int survey_id) {
        int survey_rev_no = 0;
        String query = "SELECT (survey_rev_no) FROM survey where survey_id=" + survey_id;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                survey_rev_no = rset.getInt("survey_rev_no");
            }
        } catch (Exception e) {
            System.out.println("Exception in getRevNO() in surveyModel" + e);
        }
        return survey_rev_no;
    }

    public int getPole_id(String pole_no) {
        int pole_id = 0;
        String query = "Select pole_id from pole where pole_no=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pole_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                pole_id = rset.getInt("pole_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPoleID() in surveyModel" + e);
        }
        return pole_id;

    }

    public String getSwitchingPoint_id(String pole_no) {
        String switching_id_rev = "";
        String query = "select spd.switching_point_detail_id, spd.switching_rev_no from switching_point_detail spd "
                + " LEFT JOIN pole p ON p.switching_point_detail_id = spd.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no "
                + " WHERE spd.active = 'Y' AND p.active = 'Y' AND p.isSwitchingPoint = 'Yes' AND p.pole_no = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pole_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switching_id_rev = String.valueOf(rset.getInt("switching_point_detail_id")) + "_" + String.valueOf(rset.getInt("switching_rev_no"));
            }
        } catch (Exception e) {
            System.out.println("Exception in SwitchingPoint_id() in surveyModel" + e);
        }
        return switching_id_rev;

    }

    public int getSwitchingPointMaxRevNo(String pole_no) {
        int rev_no = 0;
        String query = "select switching_rev_no from switching_point_detail "
                + " where pole_no_s= ? and active='Active'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pole_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                rev_no = rset.getInt("switching_rev_no");
            }
        } catch (Exception e) {
            System.out.println("Exception in getSwitchingPointMaxRevNo() in surveyModel" + e);
        }
        return rev_no;
    }

    public int getMaxRevNo(String pole_no) {
        int rev_no = 0;
        String query = "select pole_rev_no from pole where pole_no=? And active='Active' ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pole_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                rev_no = rset.getInt("pole_rev_no");
            }
        } catch (Exception e) {
            System.out.println("Exception in getmaxRevNo() in surveyModel" + e);
        }
        return rev_no;
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

    public int insertRecord(SurveyBean surveyBean, List list) {
        String query = "INSERT INTO survey (survey_file_no, survey_page_no, mobile_no, pole_id, pole_rev_no, survey_type, "
                + " remark, survey_date,image_name,image_date_time,longitude,lattitude,survey_pole_no) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query2 = "INSERT INTO switching_point_survey (survey_id, meter_no, meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, mccb_functional,"
                + " remark,created_by,fuse1,fuse2,fuse3,contacter_id,"
                + "contacter_make_id,contacter_capacity,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,no_of_phase,meter_phase,meter_reading,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,reason_id)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description) "
                + " VALUES(?, ?, ?, ?)";

        String updateQuery = "UPDATE survey set general_image_details_id=? where image_name=? and status='Y' ";
        int survey_id = 0;
        int survey_rev_no = 0;
        String image_uploaded_for = "Survey Image";
        String pole_no = surveyBean.getPole_no();
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        int rowsAffected = 0;
        ResultSet rs = null;

        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
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
                pstmt.setInt(4, surveyBean.getPole_id());
                pstmt.setInt(5, surveyBean.getPole_rev_no());
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.setString(6, surveyBean.getSurvey_type());
            pstmt.setString(7, surveyBean.getRemark());

            if (surveyBean.getSurvey_date() != null && !(surveyBean.getSurvey_date().trim()).isEmpty()) {
                pstmt.setDate(8, convertToSqlDate(surveyBean.getSurvey_date()));
            } else {
                pstmt.setNull(8, java.sql.Types.DATE);
            }
            pstmt.setString(9, surveyBean.getImage_name());
            pstmt.setString(10, null);
            pstmt.setString(11, surveyBean.getLongitude());
            pstmt.setString(12, surveyBean.getLatitude());
            pstmt.setString(13, surveyBean.getSurvey_pole_no());
            rowsAffected = pstmt.executeUpdate();
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                if (rowsAffected > 0) {
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        survey_id = rs.getInt(1);
                        //        survey_rev_no = rs.getInt(14);
                    }
                    rowsAffected = 0;
                    pstmt.close();
                    pstmt = connection.prepareStatement(query2);

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

                    if (surveyBean.getNo_of_phase() == '3') {
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
                    pstmt.setString(16, surveyBean.getContacter_id());
                    pstmt.setString(17, surveyBean.getContacter_make_id());
                    pstmt.setString(18, surveyBean.getContacter_capacity());
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
                        pstmt.setString(35, getReasonId(surveyBean.getReason_type()));
                    } else {
                        pstmt.setNull(35, java.sql.Types.INTEGER);
                    }

                    if (rowsAffected > 0) {
                        if (!surveyBean.getImage_name().isEmpty()) {
                            try {
                                pstmt = connection.prepareStatement(imageQuery);
                                pstmt.setString(1, surveyBean.getImage_name());
                                pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
                                pstmt.setString(3, current_date);
                                pstmt.setString(4, "this image is for survey");

                                rowsAffected = pstmt.executeUpdate();
                                pstmt.close();
                            } catch (Exception e) {
                                System.out.println("Error:keypersonModel--insertRecord-- " + e);
                            }
                            if (rowsAffected > 0) {
                                try {
                                    if (writeImage(surveyBean.getImage_name(), list) > 0) {
                                        pstmt = connection.prepareStatement(updateQuery);
                                        pstmt.setInt(1, getgeneral_image_details_id(surveyBean.getImage_name()));
                                        pstmt.setString(2, surveyBean.getImage_name());
                                        pstmt.executeUpdate();
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exception :" + e);
                                }
                            }
                        }
                        // rowsAffected = writeImage(key, itr, destination);
                        message = "Record saved successfully.";
                        msgBgColor = COLOR_OK;
                        connection.commit();
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
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
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
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
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

    public int ChangeStatusToApproved(int survey_id, int survey_rev_no) {
        int rowsAffected = 0;
        String query = "UPDATE survey SET status='Approved' "
                + " WHERE survey_id = ? and survey_rev_no = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, survey_id);
            ps.setInt(2, survey_rev_no);
            rowsAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception in ChangeStatusToApproved() in surveyModel" + e);
        }
        return rowsAffected;
    }

    public int ChangeStatusToCancel(int survey_id, int survey_rev_no) {
        int rowsAffected = 0;
        String query = "UPDATE survey SET status='Canceled' "
                + " WHERE survey_id = ? and survey_rev_no = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, survey_id);
            ps.setInt(2, survey_rev_no);
            rowsAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception in ChangeStatusToCanceled() in surveyModel" + e);
        }
        return rowsAffected;
    }

    public int ChangeStatusToApprovedInSwitching(int switching_point_survey_id, int switching_point_survey_rev_no) {
        int rowsAffected = 0;
        String query = "Update switching_point_survey SET active='N' WHERE switching_point_survey_id = ? and "
                + "switching_point_survey_rev_no = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, switching_point_survey_id);
            ps.setInt(2, switching_point_survey_rev_no);
            rowsAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception in ChangeStatusToApprovedInSwitching() in surveyModel" + e);
        }
        return rowsAffected;
    }

    public int cancelRecord(SurveyBean surveyBean) {
        String query1 = "UPDATE survey SET status = 'N' WHERE survey_id = ? AND survey_rev_no = ?";
        String query2 = "UPDATE switching_point_survey SET active = 'N' WHERE switching_point_survey_id = ? AND switching_point_survey_rev_no = ? ";
        int rowsAffected = 0;

        int survey_id = surveyBean.getSurvey_id();
        int survey_rev_no = surveyBean.getSurvey_rev_no();
        int switching_point_survey_id = surveyBean.getSwitching_point_survey_id();
        int switching_point_survey_rev_no = surveyBean.getSwitching_point_survey_rev_no();
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, survey_id);
            pstmt.setInt(2, survey_rev_no);
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                if (surveyBean.getSurvey_type().equals("switching_type_survey")) {
                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(query2);
                    pstmt.setInt(1, switching_point_survey_id);
                    pstmt.setInt(2, switching_point_survey_rev_no);
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.commit();
                }
            } else {
                connection.rollback();
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
            }
        }

        if (rowsAffected > 0) {
            message = "Record Cancelled successfully.";
            msgBgColor = COLOR_OK;
        } else {

            message = "Couldn't Cancel the record, Some Error.";
            msgBgColor = COLOR_ERROR;

        }
        return rowsAffected;
    }

    public int reviseRecord(SurveyBean surveyBean) {
        String query = "INSERT INTO survey ( switching_point_detail_id, switching_rev_no, "
                + " survey_file_no, survey_page_no, survey_by, pole_id, pole_rev_no, survey_type, "
                + " remark, survey_date, survey_id, survey_rev_no) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query2 = "INSERT INTO switching_point_survey ( survey_id, meter_no,"
                + "  meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, mccb_functional,"
                + " timer_functional,remark,created_by, switching_point_survey_id, switching_point_survey_rev_no, survey_rev_no)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String query4 = "UPDATE survey SET status = 'N' WHERE survey_id = ?";
        String query5 = "UPDATE switching_point_survey SET active = 'N' WHERE switching_point_survey_id = ?";
        String status = surveyBean.getStatus();
        int rowsAffected = 0;

        int survey_id = surveyBean.getSurvey_id();
        int survey_rev_no = surveyBean.getSurvey_rev_no();
        int switching_point_survey_id = surveyBean.getSwitching_point_survey_id();
        int switching_point_survey_rev_no = surveyBean.getSwitching_point_survey_rev_no();
        String pole_no = surveyBean.getPole_no();

        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query4);
            pstmt.setInt(1, survey_id);
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                if (surveyBean.getSurvey_type().equals("switching_type_survey")) {
                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(query5);
                    pstmt.setInt(1, switching_point_survey_id);
                    rowsAffected = pstmt.executeUpdate();
                }
                if (rowsAffected > 0) {
                    rowsAffected = 0;
                    pstmt.close();
                    pstmt = connection.prepareStatement(query);
                    //pstmt.setInt(1, survey_id + 1);
                    //pstmt.setInt(1, surveyBean.getSwitching_point_detail_id());
                    if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                        pstmt.setNull(1, java.sql.Types.INTEGER);
                        pstmt.setNull(2, java.sql.Types.INTEGER);
                    } else {
                        pstmt.setInt(1, surveyBean.getSwitching_point_detail_id());
                        pstmt.setInt(2, surveyBean.getSwitching_rev_no());
                    }
                    pstmt.setString(3, surveyBean.getSurvey_file_no());
                    pstmt.setString(4, surveyBean.getSurvey_page_no());
                    pstmt.setString(5, surveyBean.getSurvey_by());
                    if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                        pstmt.setInt(6, surveyBean.getPole_id());
                        pstmt.setInt(7, surveyBean.getPole_rev_no());
                        //      pstmt.setInt(6, getPole_id(pole_no));
                        //      pstmt.setInt(7, getMaxRevNo(pole_no));
                    } else {
                        pstmt.setNull(6, java.sql.Types.INTEGER);
                        pstmt.setNull(7, java.sql.Types.INTEGER);
                    }
                    pstmt.setString(8, surveyBean.getSurvey_type());
                    pstmt.setString(9, surveyBean.getRemark());

                    if (surveyBean.getSurvey_date() != null && !(surveyBean.getSurvey_date().trim()).isEmpty()) {
                        pstmt.setDate(10, convertToSqlDate(surveyBean.getSurvey_date()));
                    } else {
                        pstmt.setNull(10, java.sql.Types.DATE);
                    }
                    pstmt.setInt(11, survey_id);
                    pstmt.setInt(12, survey_rev_no + 1);

                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        //             rowsAffected = ChangeStatusToApproved(survey_id, survey_rev_no);
                        if (surveyBean.getSurvey_type().equals("switching_type_survey")) {


                            if (rowsAffected > 0) {
                                rs = pstmt.getGeneratedKeys();
                                while (rs.next()) {
                                    survey_id = rs.getInt(1);
                                }
                                rowsAffected = 0;
                                pstmt.close();
                                pstmt = connection.prepareStatement(query2);

                                pstmt.setInt(1, survey_id);
                                pstmt.setString(2, surveyBean.getMeter_no());
                                pstmt.setString(3, surveyBean.getMeter_functional());
                                pstmt.setString(4, surveyBean.getR_phase());
                                pstmt.setString(5, surveyBean.getY_phase());
                                pstmt.setString(6, surveyBean.getB_phase());
                                pstmt.setString(7, surveyBean.getPower());
                                pstmt.setString(8, surveyBean.getFuse_functional());
                                // pstmt.setString(9, surveyBean.getContacter_funactional());
                                pstmt.setString(10, surveyBean.getMccb_functional());
                                // pstmt.setString(11, surveyBean.getTimer_functional());
                                pstmt.setString(12, surveyBean.getRemark());
                                pstmt.setString(13, "Viney Srivastva");
                                pstmt.setInt(14, switching_point_survey_id);
                                pstmt.setInt(15, switching_point_survey_rev_no + 1);
                                pstmt.setInt(16, survey_rev_no + 1);
                                //      pstmt.setInt(14,survey_rev_no);
                                rowsAffected = pstmt.executeUpdate();

                                if (rowsAffected > 0) {

                                    connection.commit();
                                } else {

                                    connection.rollback();
                                }

                            } else {

                                connection.rollback();
                            }
                        }
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
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
            }
        }

        if (rowsAffected > 0) {
            message = "Record Revised successfully.";
            msgBgColor = COLOR_OK;

        } else {
            message = "Cannot revise the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        return rowsAffected;
        /* String query = "INSERT INTO survey (survey_id, switching_point_detail_id, "
        + " survey_file_no, survey_page_no, survey_by, pole_id, survey_type, "
        + " remark, survey_rev_no ) "
        + " VALUES (?,?,?,?,?,?,?,?,?) ";
        String query2 = " UPDATE survey SET status='Approved' "
        + " WHERE survey_id = ? and survey_rev_no = ? ";
        String query3 = "INSERT INTO switching_point_survey (switching_point_survey_id, survey_id, meter_no, meter_functional, "
        + " r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, mccb_functional, "
        + " timer_functional, active, remark )"
        + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query4 = "Update switching_point_survey SET active='N' WHERE switching_point_survey_id = ? and "
        + "switching_point_survey_rev_no = ? ";

        //String query5 = "Update pole_light_type_mapping SET light_type_id=?, quantity=? "
        //        + " WHERE pole_id = ? and pole_rev_no = ? ";
        int rowsAffected = 0;
        try {
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, surveyBean.getSurvey_id());
        pstmt.setInt(2, surveyBean.getSwitching_point_detail_id());
        pstmt.setString(3, surveyBean.getSurvey_file_no());
        pstmt.setString(4, surveyBean.getSurvey_page_no());
        pstmt.setString(5, surveyBean.getSurvey_by());
        pstmt.setInt(6, surveyBean.getPole_id());
        pstmt.setString(7, surveyBean.getSurvey_type());
        pstmt.setString(8, surveyBean.getRemark());
        pstmt.setInt(9, surveyBean.getSurvey_rev_no() + 1);
        rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
        pstmt = connection.prepareStatement(query2);
        pstmt.setInt(1, surveyBean.getPole_id());
        pstmt.setInt(2, surveyBean.getPole_rev_no());
        rowsAffected = pstmt.executeUpdate();
        }
        if (rowsAffected > 0) {
        //              String[] source_wattage = surveyBean.getSources_wattage_M();
        pstmt = connection.prepareStatement(query3);
        pstmt.setInt(1, surveyBean.getSwitching_point_survey_id());
        pstmt.setInt(2, surveyBean.getSurvey_id());
        pstmt.setString(3, surveyBean.getMeter_no());
        pstmt.setString(4, surveyBean.getMeter_functional());
        pstmt.setString(5, surveyBean.getR_phase());
        pstmt.setString(6, surveyBean.getY_phase());
        pstmt.setString(7, surveyBean.getB_phase());
        pstmt.setString(8, surveyBean.getPower());
        pstmt.setString(9, surveyBean.getFuse_functional());
        pstmt.setString(10, surveyBean.getContacter_funactional());
        pstmt.setString(11, surveyBean.getMccb_functional());
        pstmt.setString(12, surveyBean.getTimer_functional());
        pstmt.setString(13, surveyBean.getRemark());
        pstmt.setInt(14, surveyBean.getSwitching_point_survey_rev_no() + 1);
        rowsAffected = pstmt.executeUpdate();
        }
        if (rowsAffected > 0) {
        pstmt = connection.prepareStatement(query4);
        pstmt.setInt(1, surveyBean.getPole_id());
        pstmt.setInt(2, surveyBean.getPole_rev_no());
        rowsAffected = pstmt.executeUpdate();
        }
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
        return rowsAffected;  */
    }

    public int cancellRecord(SurveyBean surveyBean) {
        String query = "INSERT INTO survey ( switching_point_detail_id, switching_rev_no, "
                + " survey_file_no, survey_page_no, survey_by, pole_id, pole_rev_no, survey_type, "
                + " remark, survey_date, survey_id, survey_rev_no) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query2 = "INSERT INTO switching_point_survey ( survey_id, meter_no,"
                + "  meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, mccb_functional,"
                + " timer_functional,remark,created_by, switching_point_survey_id, switching_point_survey_rev_no, survey_rev_no)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String status = surveyBean.getStatus();
        int rowsAffected = 0;

        int survey_id = surveyBean.getSurvey_id();
        int survey_rev_no = surveyBean.getSurvey_rev_no();
        int switching_point_survey_id = surveyBean.getSwitching_point_survey_id();
        int switching_point_survey_rev_no = surveyBean.getSwitching_point_survey_rev_no();
        String pole_no = surveyBean.getPole_no();

        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setInt(1, survey_id + 1);
            //pstmt.setInt(1, surveyBean.getSwitching_point_detail_id());
            if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                pstmt.setNull(1, java.sql.Types.INTEGER);
                pstmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(1, 1);
                pstmt.setInt(2, 0);
            }
            pstmt.setString(3, surveyBean.getSurvey_file_no());
            pstmt.setString(4, surveyBean.getSurvey_page_no());
            pstmt.setString(5, surveyBean.getSurvey_by());
            if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                pstmt.setInt(6, getPole_id(pole_no));
                pstmt.setInt(7, getMaxRevNo(pole_no));
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }
            pstmt.setString(8, surveyBean.getSurvey_type());
            pstmt.setString(9, surveyBean.getRemark());

            if (surveyBean.getSurvey_date() != null && !(surveyBean.getSurvey_date().trim()).isEmpty()) {
                pstmt.setDate(10, convertToSqlDate(surveyBean.getSurvey_date()));
            } else {
                pstmt.setNull(10, java.sql.Types.DATE);
            }
            pstmt.setInt(11, survey_id);
            pstmt.setInt(12, survey_rev_no + 1);

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rowsAffected = ChangeStatusToCancel(survey_id, survey_rev_no);
                if (surveyBean.getSurvey_type().equals("switching_type_survey")) {


                    if (rowsAffected > 0) {
                        rs = pstmt.getGeneratedKeys();
                        while (rs.next()) {
                            survey_id = rs.getInt(1);
                        }
                        rowsAffected = 0;
                        pstmt.close();
                        pstmt = connection.prepareStatement(query2);

                        pstmt.setInt(1, survey_id);
                        pstmt.setString(2, surveyBean.getMeter_no());
                        pstmt.setString(3, surveyBean.getMeter_functional());
                        pstmt.setString(4, surveyBean.getR_phase());
                        pstmt.setString(5, surveyBean.getY_phase());
                        pstmt.setString(6, surveyBean.getB_phase());
                        pstmt.setString(7, surveyBean.getPower());
                        pstmt.setString(8, surveyBean.getFuse_functional());
                        //  pstmt.setString(9, surveyBean.getContacter_funactional());
                        pstmt.setString(10, surveyBean.getMccb_functional());
                        //  pstmt.setString(11, surveyBean.getTimer_functional());
                        pstmt.setString(12, surveyBean.getRemark());
                        pstmt.setString(13, "Viney Srivastva");
                        pstmt.setInt(14, switching_point_survey_id);
                        pstmt.setInt(15, switching_point_survey_rev_no + 1);
                        pstmt.setInt(16, survey_rev_no + 1);
                        //      pstmt.setInt(14,survey_rev_no);
                        rowsAffected = pstmt.executeUpdate();

                        if (rowsAffected > 0) {

                            connection.commit();
                        } else {

                            connection.rollback();
                        }

                    } else {

                        connection.rollback();
                    }
                }
            } else {
                connection.rollback();
            }

        } catch (Exception e) {
            rowsAffected = -1;
            System.out.println("Error while canceling record...." + e);
            try {
                connection.rollback();
            } catch (SQLException sql) {
                System.out.println("SQLException occured during Cancel is: " + sql);
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

        if (rowsAffected > 0) {
            message = "Record Canceled successfully.";
            msgBgColor = COLOR_OK;
        } else {
            if (!status.equals("Pending")) {
                message = "Cannot Cancel Record of 'Approved' Status";
                msgBgColor = COLOR_ERROR;
            } else {
                message = "Cannot cancel the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        }
        return rowsAffected;
    }

    public List<String> getAutoSwitchType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT st.switch_type FROM switch_type as st,switch as s where s.switch_id-st.switch_id"
                + " and s.switch='auto_switch' GROUP BY switch_type ORDER BY switch_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_type = rset.getString("switch_type");
                if (switch_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switch_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Auto Switch Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimerType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getMainSwitchType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT st.switch_type FROM switch_type as st,switch as s where s.switch_id-st.switch_id"
                + " and s.switch='main_switch' GROUP BY switch_type ORDER BY switch_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_type = rset.getString("switch_type");
                if (switch_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switch_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Auto Switch Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimerType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getEnclosureType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT enclosure_type FROM enclosure_type "
                + "  GROUP BY enclosure_type ORDER BY enclosure_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String enclosure_type = rset.getString("enclosure_type");
                if (enclosure_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(enclosure_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Auto Switch Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getTimerType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String pole_no) {
//        String query = " SELECT Count(*) "
//                + " FROM survey s, switching_point_survey sps, pole p, switching_point_detail sp "
//                + " WHERE s.survey_id = sps.survey_id "
//                + " AND s.survey_rev_no = sps.survey_rev_no "
//                + " AND IF('" + searchPoleno + "' = '', p.pole_no LIKE '%%', p.pole_no =? ) "
//                + " AND IF('" + searchSwitchNo + "' = '', sp.pole_no_s LIKE '%%', sp.pole_no_s =? ) "
//                + " ORDER BY pt.pole_type";

        String queryy = "select @r:=(select count(*) as total"
                + " from survey s ) as d ,"//where switching_point_detail_id IS NULL
                + " @s:=(SELECT count(*) as total"
                + " FROM survey s , switching_point_survey sps where "//s.switching_point_detail_id IS NOT NULL AND"
                + " s.survey_id = sps.switching_point_survey_id AND s.survey_rev_no = sps.survey_rev_no)as t ,"
                + " @r+@S as total from (select @r:=0)as r , (select @s:=0)as s";
        //            + " WHERE IF('" + pole_no + "' = '', s.survey_id LIKE '%%', p.pole_no=? ) ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(queryy);
            //   stmt.setString(1, pole_no);
            //   stmt.setString(2, searchSwitchNo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                noOfRows = Integer.parseInt(rs.getString("total"));
            }
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows Light model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public String getPole_No(int pole_id, int pole_rev_no) {
        String pole_no = null;
        String query = "select pole_no from pole where pole_id=? and pole_rev_no = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, pole_id);
            ps.setInt(2, pole_rev_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                pole_no = rset.getString("pole_no");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return pole_no;
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
            System.out.println("Exception in getPole_No() in surveyModel" + e);
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
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return reason_id;
    }

    public String getSwitchingTypePole_No(int switching_point_detail_id, int switching_rev_no) {
        String pole_no = null;
        String query = "select pole_no from pole where switching_point_detail_id=? and switching_rev_no =?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, switching_point_detail_id);
            ps.setInt(2, switching_rev_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                pole_no = rset.getString("pole_no");
            }
        } catch (Exception e) {
            System.out.println("Exception in getSwitchingTypePole_No() in surveyModel" + e);
        }
        return pole_no;
    }

    //public List<SurveyBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchPoleNo, String searchSwitchNo) {
    public List<SurveyBean> showData(int lowerLimit, int noOfRowsToDisplay, String pole_no) {
        List<SurveyBean> list = new ArrayList<SurveyBean>();
        String query = null;

//        String query = " select s.survey_id, s.switching_rev_no,"
//                + "s.survey_file_no, s.survey_date, s.survey_page_no, s.survey_by "
//                + "s.survey_type,s.created_date, s.status, s.remark, s.survey_rev_no, "
//                + "sp.meter_no, sp.meter_functional, "
//                + "sp.switching_point_survey_id, sp.r_phase, sp.y_phase, sp.b_phase, sp.power,"
//                + " sp.fuse_functional, sp.switching_point_survey_rev_no "
//                + "sp.contacter_functional, sp.mccb_functional, p.pole_no, sd.pole_no_s, p.pole_rev_no, "
//                + "sp.timer_functional, sp.switching_point_survey_rev_no "
//                + "from survey s, switching_point_survey sp, pole p, switching_point_detail sd "
//                + "where s.switching_point_detail_id = sd.switching_point_detail_id "
//                + "And s.switching_rev_no = sd.switching_rev_no "
//                + "AND s.pole_id= p.pole_id "
//                + "AND s.pole_rev_no = p.pole_rev_no"
//                + "AND s.survey_id= sp.survey_id ";
   /*     String query1 = "select"
        + " s.survey_id, s.switching_point_detail_id, s.switching_rev_no, s.survey_file_no, s.survey_date,"
        + " s.survey_page_no, s.survey_by, s.pole_id, s.pole_rev_no, s.survey_type, s.created_date, s.status, s.remark, s.survey_rev_no,"
        + " '' as switching_point_survey_id, '' as survey_id, '' as meter_no,"
        + " '' as meter_functional, '' as r_phase, '' as y_phase, '' as b_phase,"
        + " '' as power, '' as fuse_functional, '' as contacter_functional,"
        + " '' as mccb_functional, '' as timer_functional, '' as created_date,"
        + " '' as active, '' as remark, '' as switching_point_survey_rev_no, '' as created_by, '' as survey_rev_no"
        + " from survey s where switching_point_detail_id IS NULL";
        String query2 = " SELECT"
        + " s.survey_id,"
        + " s.switching_point_detail_id, s.switching_rev_no, s.survey_file_no, s.survey_date, s.survey_page_no, s.survey_by, s.pole_id,"
        + " s.pole_rev_no, s.survey_type, s.created_date, s.status, s.remark, s.survey_rev_no,"
        + " sps.switching_point_survey_id, sps.survey_id, sps.meter_no,"
        + " sps.meter_functional, sps.r_phase, sps.y_phase, sps.b_phase,"
        + " sps.power, sps.fuse_functional, sps.contacter_functional,"
        + " sps.mccb_functional, sps.timer_functional, sps.created_date,"
        + " sps.active, sps.remark, sps.switching_point_survey_rev_no, sps.created_by, sps.survey_rev_no"
        + " FROM survey s , switching_point_survey sps where s.switching_point_detail_id IS NOT NULL"
        + " And s.survey_id = sps.survey_id AND s.survey_rev_no = sps.survey_rev_no";  *///s.switching_point_detail_id, s.switching_rev_no,
        String query1 = "select s.survey_id, s.survey_file_no, s.survey_date, s.survey_page_no, s.mobile_no," //s.pole_id, "
                + " s.pole_rev_no, s.survey_type, s.created_date, s.status, s.remark, s.survey_rev_no, '' as switching_point_survey_id, '' as survey_id, '' as meter_no, "
                + " '' as meter_functional, '' as r_phase, '' as y_phase, '' as b_phase, '' as power, '' as fuse_functional, '' as contacter_functional, '' as mccb_functional, "
                + " '' as timer_functional, '' as created_date, '' as active, '' as remark, '' as switching_point_survey_rev_no, '' as created_by, '' as survey_rev_no "
                + " from survey s, switching_point_detail spd, pole p where s.status= 'Y' and "
                + " IF(s.switching_point_detail_id != null, spd.switching_point_detail_id = s.switching_point_detail_id and spd.switching_rev_no = s.switching_rev_no "
                + "  and spd.switching_point_detail_id = p.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no and p.isSwitchingPoint = 'Yes' and p.active='Y', "
                + "  s.pole_id = p.pole_id and s.pole_rev_no = p.pole_rev_no) AND IF( ? = '', s.survey_id LIKE '%%', p.pole_no= ? ) ";
        String query2 = "SELECT s.survey_id, s.switching_point_detail_id, s.switching_rev_no, s.survey_file_no, "
                + " s.survey_date, s.survey_page_no, s.mobile_no, s.pole_id, s.pole_rev_no, s.survey_type, s.created_date, s.status, s.remark, s.survey_rev_no, "
                + " sps.switching_point_survey_id, sps.survey_id, sps.meter_no, sps.meter_functional, sps.r_phase, sps.y_phase, sps.b_phase, sps.power, sps.fuse_functional, "
                + " sps.contacter_functional, sps.mccb_functional, sps.timer_functional, sps.created_date, sps.active, sps.remark, sps.switching_point_survey_rev_no, "
                + " sps.created_by, sps.survey_rev_no ,spd.fuse1,spd.fuse2,spd.fuse3,spd.mccb1,spd.mccb2,spd.mccb3,spd.cotracter1,spd.cotracter2,spd.cotracter3, spd.timer1,spd.timer2,spd.timer3,"
                + "sps.fuse_id1,sps.fuse_id2,sps.fuse_id3,sps.mccb_id1,sps.mccb_id2,sps.mccb_id3,sps.contracter_id1,sps.contracter_id2,sps.contracter_id3,sps.timer_type_id1,sps.timer_type_id2,sps.timer_type_id3 "
                + " FROM survey s , switching_point_survey sps, switching_point_detail spd, pole p "
                + " where s.status= 'Y' "
                + " and spd.switching_point_detail_id = s.switching_point_detail_id and spd.switching_rev_no = s.switching_rev_no "
                + " and spd.switching_point_detail_id = p.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no and p.isSwitchingPoint = 'Yes' and p.active='Y' "
                + " AND IF( ? = '', s.survey_id LIKE '%%', p.pole_no=? ) ";

        query = query1 + " union " + query2 + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        //          + " AND IF('" + pole_no + "' = '', s.survey_id LIKE '%%', p.pole_no=? ) "


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_no);
            pstmt.setString(2, pole_no);
            pstmt.setString(3, pole_no);
            pstmt.setString(4, pole_no);
            //  pstmt.setString(2, searchSwitchNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SurveyBean surveyType = new SurveyBean();
                surveyType.setSurvey_id(rset.getInt("survey_id"));
                //surveyType.setSwitching_point_detail_id(rset.getInt("switching_point_detail_id"));
                //surveyType.setSwitching_rev_no(rset.getInt("switching_rev_no"));
                surveyType.setSurvey_file_no(rset.getString("survey_file_no"));
                surveyType.setSurvey_date(rset.getString("survey_date"));
                surveyType.setSurvey_page_no(rset.getString("survey_page_no"));
                surveyType.setSurvey_by(rset.getString("mobile_no"));
                //surveyType.setPole_id(rset.getInt("pole_id"));
                surveyType.setPole_rev_no(rset.getInt("pole_rev_no"));
                surveyType.setSurvey_type(rset.getString("survey_type"));
                surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setStatus(rset.getString("status"));
                surveyType.setRemark(rset.getString("remark"));
                surveyType.setSurvey_rev_no(rset.getInt("survey_rev_no"));

                surveyType.setSwitching_point_survey_id(rset.getInt("switching_point_survey_id"));
                surveyType.setSurvey_id(rset.getInt("survey_id"));
                surveyType.setMeter_no(rset.getString("meter_no"));
                surveyType.setMeter_functional(rset.getString("meter_functional"));
                surveyType.setR_phase(rset.getString("r_phase"));
                surveyType.setY_phase(rset.getString("y_phase"));
                surveyType.setB_phase(rset.getString("b_phase"));
                surveyType.setPower(rset.getString("power"));
                surveyType.setFuse_functional(rset.getString("fuse_functional"));
                surveyType.setContacter_functional(rset.getString("contacter_functional"));
                surveyType.setMccb_functional(rset.getString("mccb_functional"));
                /// surveyType.setTimer_functional(rset.getString("timer_functional"));
                surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setActive(rset.getString("active"));
                surveyType.setRemark(rset.getString("remark"));
                surveyType.setSwitching_point_survey_rev_no(rset.getInt("switching_point_survey_rev_no"));
                surveyType.setCreated_by(rset.getString("created_by"));
                surveyType.setSurvey_rev_no(rset.getInt("survey_rev_no"));
                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse1(rset.getString("mccb1"));
                surveyType.setFuse1(rset.getString("mccb2"));
                surveyType.setFuse1(rset.getString("mccb3"));
                surveyType.setFuse1(rset.getString("cotracter1"));
                surveyType.setFuse1(rset.getString("cotracter2"));
                surveyType.setFuse1(rset.getString("cotracter3"));
                surveyType.setFuse1(rset.getString("timer1"));
                surveyType.setFuse1(rset.getString("timer2"));
                surveyType.setFuse1(rset.getString("timer3"));
                surveyType.setFuse_type1(getFuseTYpe(rset.getInt("fuse_id1")));
                surveyType.setFuse_type2(getFuseTYpe(rset.getInt("fuse_id2")));
                surveyType.setFuse_type3(getFuseTYpe(rset.getInt("fuse_id3")));
                surveyType.setMccb_type1(getFuseTYpe(rset.getInt("mccb_id1")));
                surveyType.setMccb_type2(getFuseTYpe(rset.getInt("mccb_id1")));
                surveyType.setMccb_type3(getFuseTYpe(rset.getInt("mccb_id1")));
                surveyType.setContacter_capacity(getFuseTYpe(rset.getInt("contracter_id1")));
//                surveyType.setContracter_type2(getFuseTYpe(rset.getInt("contracter_id1")));
//                surveyType.setContracter_type3(getFuseTYpe(rset.getInt("contracter_id1")));
//                surveyType.setTimer_type1(getFuseTYpe(rset.getInt("timer_type_id1")));
//                surveyType.setTimer_type2(getFuseTYpe(rset.getInt("timer_type_id2")));
//                surveyType.setTimer_type3(getFuseTYpe(rset.getInt("timer_type_id3")));
//



                if (rset.getString("survey_type").equals("pole_type_survey")) {
                    surveyType.setPole_no(getPole_No(rset.getInt("pole_id"), rset.getInt("pole_rev_no")));
                } else {
                    surveyType.setPole_no(getSwitchingTypePole_No(rset.getInt("switching_point_detail_id"), rset.getInt("switching_rev_no")));
                }


//                surveyType.setPole_no(rset.getString("pole_no"));
//                surveyType.setSwitching_point_name(rset.getString("pole_no_s"));
//                surveyType.setActive(rset.getString("active"));
//                surveyType.setPole_no(rset.getString("pole_no"));
//                surveyType.setContacter_funactional(rset.getString("contacter_functional"));

                list.add(surveyType);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return list;
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
            } 
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return enclosure_type_id;
    }

    public String getContacterMakeId(String make_type) {

        String make_id = "";
        String query = " SELECT make_id FROM make WHERE make_type='" + make_type + "'  "
                + "group by make_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                make_id = rset.getString("make_id");
            } 
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return make_id;
    }

    public List<String> getSwitchingPtName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT switching_point_name FROM switching_point_detail GROUP BY switching_point_name ORDER BY switching_point_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("switching_point_name");
                if (pole_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(pole_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Switching Point exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside SurveyModel - " + e);
        }
        return list;
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
            } 
        } catch (Exception e) {
            System.out.println("SurveyModel getSwitchId() Error: " + e);
        }
        return switch_type_id;
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
