/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfWriter;
import com.survey.general.model.AlertsModel;
import com.survey.tableClasses.SwitchingPointSurveyBean;
import com.survey.tableClasses.TubeWellSurveyBean;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

/**
 *
 * @author JPSS
 */
public class NewSwitchingPointSurveyModel {

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
    private int general_image_details_id;

    public void setConnection() {
        try {
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<TubeWellSurveyBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in tubeWellUserTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
    
    public ByteArrayOutputStream generateXLSReport(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
            //print(jasperPrint);
        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        return reportInbytes;
    }
    public List<String> getSearchFeeder(String q) {
         List<String> list = new ArrayList<String>();
        String query = " select feeder_name from feeder  order by feeder_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String feeder_name = rset.getString("feeder_name");
                if (feeder_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(feeder_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Feeder Exists.");
            }
        } catch (Exception e) {
            System.out.println("getFeeder ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getSearchTypeOfConnection(String q) {
         List<String> list = new ArrayList<String>();
        String query = " select type_of_premsis from type_of_premises order by type_of_premsis ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String type_of_premsis = rset.getString("type_of_premsis");
                if (type_of_premsis.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type_of_premsis);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such type_of_premsis No Exists.");
            }
        } catch (Exception e) {
            System.out.println("type_of_premsis ERROR inside SurveyModel - " + e);
        }
        return list;
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
        String query = "Select poll_no from meters "
                + " where final_revision='VALID' = 'Y' GROUP BY poll_no";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_no = rset.getString("poll_no");
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
        String query = " select pole_no from pole where active='Y' ";
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
        String query = " SELECT pole_no_s FROM tube_well_detail GROUP BY pole_no_s ORDER BY pole_no_s ";
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
        String query = " SELECT tube_well_detail_id FROM tube_well_detail WHERE tube_well_detail_id = ? ";
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
        String query;
        int survey_id = 0;
        query = "select MAX(survey_id) as id from survey where status='Y' ";
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

    public int checkImage(String image_name) {
        String query;
        int total = 0;
        if(!image_name.isEmpty()){
        query = "SELECT count(*) as total FROM general_image_details where image_name Like '%" + image_name + "%' and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                total = rset.getInt("total");

            }

        } catch (Exception ex) {
            System.out.println("Error: getSurveyId() " + ex);
        }
        }
        return total;
    }

    public int getSwitchingPointSurveyId() {
        int tube_well_survey_id = 0;
        try {
            String query = "select max(tube_well_survey_id) from tube_well_survey ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tube_well_survey_id = rs.getInt("max(tube_well_survey_id)");
            }

        } catch (Exception e) {
            System.out.println("Error: SurveyModel getSwitchingPointSurveyId():" + e);
        }
        return tube_well_survey_id;
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
        String query = "select meter_id from meters where poll_no='" + pole_no + "' and final_revision='VALID'";
        //"select spd.tube_well_detail_id, spd.tube_well_rev_no from tube_well_detail spd "
//                + " LEFT JOIN pole p ON p.tube_well_detail_id = spd.tube_well_detail_id AND spd.tube_well_rev_no = p.tube_well_rev_no "
//                + " WHERE spd.active = 'Y' AND p.active = 'Y' AND p.isSwitchingPoint = 'Yes' AND p.pole_no = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //ps.setString(1, pole_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switching_id_rev = rset.getString("meter_id");//String.valueOf(rset.getInt("tube_well_detail_id")) + "_" + String.valueOf(rset.getInt("tube_well_rev_no"));
            }
        } catch (Exception e) {
            System.out.println("Exception in SwitchingPoint_id() in surveyModel" + e);
        }
        return switching_id_rev;

    }

    public int getSwitchingPointMaxRevNo(String pole_no) {
        int rev_no = 0;
        String query = "select tube_well_rev_no from tube_well_detail "
                + " where pole_no_s= ? and active='Active'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pole_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                rev_no = rset.getInt("tube_well_rev_no");
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
        if (survey_type.equals("tubewell_type_survey")) {
            id_field_name = "tube_well_detail_id = ";
            rev_field_name = "tube_well_rev_no = ";
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
            if (survey_type.equals("tubewell_type_survey")) {
                message = "Survey of this Switching Point has Already been done, Kindly Revise it..";
            } else {
                message = "Survey of this Pole has Already been done, Kindly De-Activate the former one";
            }
            msgBgColor = COLOR_ERROR;
        }
        return rowCount > 0 ? false : true;
    }

    public int insertRecord(TubeWellSurveyBean tubeWellSurveyBean, List list) {
        String query = "INSERT INTO survey (survey_file_no, survey_page_no, mobile_no, pole_no, pole_rev_no, survey_type, "
                + " remark, survey_date,image_name,image_date_time,longitude,lattitude,survey_pole_no,survey_id) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query2 = "INSERT INTO switching_point_survey (switching_point_survey_id, meter_no, meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, mccb_functional,"
                + " remark,created_by,fuse1,fuse2,fuse3,contacter_id,"
                + "contacter_make_id,contacter_capacity,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,no_of_phase,meter_phase,meter_reading,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,reason_id,survey_rev_no)"//pole_id
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description) "
                + " VALUES(?, ?, ?, ?)";

        String updateQuery = "UPDATE survey set general_image_details_id=?,image_name=? where image_name=? and status='Y' ";
        int survey_id = getSurveyId();
        int survey_rev_no = 0;
        String image_uploaded_for = "Survey Image";
        String pole_no = tubeWellSurveyBean.getPole_no();
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        int rowsAffected = 0;
        ResultSet rs = null;

        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setInt(1, survey_id + 1);
            //pstmt.setInt(1, tubeWellSurveyBean.getSwitching_point_detail_id());
//            if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
//                pstmt.setNull(1, java.sql.Types.INTEGER);
//                pstmt.setNull(2, java.sql.Types.INTEGER);
//            } else {
//                pstmt.setInt(1, tubeWellSurveyBean.getSwitching_point_detail_id());
//                pstmt.setInt(2, tubeWellSurveyBean.getSwitching_rev_no());
//            }
            pstmt.setString(1, tubeWellSurveyBean.getSurvey_file_no());
            pstmt.setString(2, tubeWellSurveyBean.getSurvey_page_no());
            pstmt.setString(3, tubeWellSurveyBean.getSurvey_by());
            if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
                pstmt.setInt(4, tubeWellSurveyBean.getPole_id());
                pstmt.setInt(5, tubeWellSurveyBean.getPole_rev_no());
            } else {
                pstmt.setString(4, tubeWellSurveyBean.getPole_no());
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.setString(6, "Switching Point");//tubeWellSurveyBean.getSurvey_type());
            pstmt.setString(7, tubeWellSurveyBean.getRemark());

            if (tubeWellSurveyBean.getSurvey_date() != null && !(tubeWellSurveyBean.getSurvey_date().trim()).isEmpty()) {
                pstmt.setDate(8, convertToSqlDate(tubeWellSurveyBean.getSurvey_date()));
            } else {
                pstmt.setNull(8, java.sql.Types.DATE);
            }
            pstmt.setString(9, tubeWellSurveyBean.getImage_name());
            pstmt.setString(10, null);
            pstmt.setString(11, tubeWellSurveyBean.getLongitude());
            pstmt.setString(12, tubeWellSurveyBean.getLatitude());
            pstmt.setString(13, tubeWellSurveyBean.getSurvey_pole_no());
            pstmt.setInt(14, survey_id);
            //pstmt.setString(15, tubeWellSurveyBean.getSurvey_meter_no());
            rowsAffected = pstmt.executeUpdate();
            if (tubeWellSurveyBean.getSurvey_type().equals("tubewell_type_survey")) {
                if (rowsAffected > 0) {
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        // survey_id = rs.getInt(1);
                        //        survey_rev_no = rs.getInt(14);
                    }
                    rowsAffected = 0;
                    pstmt.close();
                    pstmt = connection.prepareStatement(query2);

                    pstmt.setInt(1, survey_id);
                    if (tubeWellSurveyBean.getMeter_status().equals("Y")) {
                        if (tubeWellSurveyBean.getMeter_functional().equals("Y")) {
                            if (!tubeWellSurveyBean.getMeter_no().isEmpty()) {
                                pstmt.setString(2, tubeWellSurveyBean.getMeter_no());
                            } else {
                                pstmt.setNull(2, java.sql.Types.NULL);
                            }

                            pstmt.setString(3, tubeWellSurveyBean.getMeter_functional());
                            pstmt.setInt(29, tubeWellSurveyBean.getMeter_phase());
                            if (!tubeWellSurveyBean.getMeter_reading().isEmpty()) {
                                pstmt.setString(30, tubeWellSurveyBean.getMeter_reading());
                            } else {
                                pstmt.setNull(30, java.sql.Types.NULL);
                            }

                        } else {
                            pstmt.setString(2, tubeWellSurveyBean.getMeter_no());
                            pstmt.setString(3, tubeWellSurveyBean.getMeter_functional());
                            pstmt.setInt(29, tubeWellSurveyBean.getMeter_phase());
                            if (!tubeWellSurveyBean.getMeter_reading().isEmpty()) {
                                pstmt.setString(30, tubeWellSurveyBean.getMeter_reading());
                            } else {
                                pstmt.setNull(30, java.sql.Types.NULL);
                            }

                        }
                    } else {
                        pstmt.setString(2, tubeWellSurveyBean.getMeter_no());
                        pstmt.setString(3, tubeWellSurveyBean.getMeter_functional());
                        pstmt.setInt(29, tubeWellSurveyBean.getMeter_phase());
                        if (!tubeWellSurveyBean.getMeter_reading().isEmpty()) {
                            pstmt.setString(30, tubeWellSurveyBean.getMeter_reading());
                        } else {
                            pstmt.setNull(30, java.sql.Types.NULL);
                        }

                    }

                    if (tubeWellSurveyBean.getNo_of_phase() == 3) {
                        pstmt.setString(4, tubeWellSurveyBean.getR_phase());
                        pstmt.setString(5, tubeWellSurveyBean.getY_phase());
                        pstmt.setString(6, tubeWellSurveyBean.getB_phase());
                    } else {
                        pstmt.setNull(4, java.sql.Types.DOUBLE);
                        pstmt.setNull(5, java.sql.Types.DOUBLE);
                        pstmt.setString(6, tubeWellSurveyBean.getB_phase());
                    }

                    pstmt.setString(7, tubeWellSurveyBean.getPower());
                    pstmt.setString(8, tubeWellSurveyBean.getFuse_functional());
                    pstmt.setString(9, tubeWellSurveyBean.getStarter_functional());
                    pstmt.setString(10, tubeWellSurveyBean.getMccb_functional());
                    //  pstmt.setString(11, tubeWellSurveyBean.getTimer_functional());
                    pstmt.setString(11, tubeWellSurveyBean.getRemark());
                    pstmt.setString(12, "Viney Srivastva");
                    //    pstmt.setString(13, tubeWellSurveyBean.getMeter_status());
                    if (!tubeWellSurveyBean.getFuse1().isEmpty()) {
                        pstmt.setString(13, tubeWellSurveyBean.getFuse1());
                    } else {
                        pstmt.setNull(13, java.sql.Types.NULL);
                    }
                    if (!tubeWellSurveyBean.getFuse2().isEmpty()) {
                        pstmt.setString(14, tubeWellSurveyBean.getFuse2());
                    } else {
                        pstmt.setNull(14, java.sql.Types.NULL);
                    }

                    if (!tubeWellSurveyBean.getFuse3().isEmpty()) {
                        pstmt.setString(15, tubeWellSurveyBean.getFuse3());
                    } else {
                        pstmt.setNull(15, java.sql.Types.NULL);
                    }
                    if (tubeWellSurveyBean.getStarter_id() != 0) {
                        pstmt.setInt(16, tubeWellSurveyBean.getStarter_id());
                    } else {
                        pstmt.setNull(16, java.sql.Types.INTEGER);
                    }
                    if (tubeWellSurveyBean.getStarter_make_id() != 0) {
                        pstmt.setInt(17, tubeWellSurveyBean.getStarter_make_id());
                    } else {
                        pstmt.setNull(17, java.sql.Types.INTEGER);
                    }
                    pstmt.setString(18, tubeWellSurveyBean.getStarter_capacity());
                    if (!tubeWellSurveyBean.getMccb1().isEmpty()) {
                        pstmt.setString(19, tubeWellSurveyBean.getMccb1());
                    } else {
                        pstmt.setNull(19, java.sql.Types.NULL);
                    }
                    if (!tubeWellSurveyBean.getMccb2().isEmpty()) {
                        pstmt.setString(20, tubeWellSurveyBean.getMccb2());
                    } else {
                        pstmt.setNull(20, java.sql.Types.NULL);
                    }
                    if (!tubeWellSurveyBean.getMccb3().isEmpty()) {
                        pstmt.setString(21, tubeWellSurveyBean.getMccb3());
                    } else {
                        pstmt.setNull(21, java.sql.Types.NULL);
                    }
                    if (tubeWellSurveyBean.getFuse_id1() != 0) {
                        pstmt.setInt(22, tubeWellSurveyBean.getFuse_id1());
                    } else {
                        pstmt.setNull(22, java.sql.Types.INTEGER);
                    }
                    if (tubeWellSurveyBean.getFuse_id2() != 0) {
                        pstmt.setInt(23, tubeWellSurveyBean.getFuse_id2());
                    } else {
                        pstmt.setNull(23, java.sql.Types.INTEGER);
                    }
                    if (tubeWellSurveyBean.getFuse_id3() != 0) {
                        pstmt.setInt(24, tubeWellSurveyBean.getFuse_id3());
                    } else {
                        pstmt.setNull(24, java.sql.Types.INTEGER);
                    }
                    if (tubeWellSurveyBean.getMccb_id1() != 0) {
                        pstmt.setInt(25, tubeWellSurveyBean.getMccb_id1());
                    } else {
                        pstmt.setNull(25, java.sql.Types.INTEGER);
                    }
                    if (tubeWellSurveyBean.getMccb_id2() != 0) {
                        pstmt.setInt(26, tubeWellSurveyBean.getMccb_id2());
                    } else {
                        pstmt.setNull(26, java.sql.Types.INTEGER);
                    }
                    if (tubeWellSurveyBean.getMccb_id3() != 0) {
                        pstmt.setInt(27, tubeWellSurveyBean.getMccb_id3());
                    } else {
                        pstmt.setNull(27, java.sql.Types.INTEGER);
                    }

                    pstmt.setInt(28, tubeWellSurveyBean.getNo_of_phase());
                    if (tubeWellSurveyBean.getAuto_switch_type_id() != 0) {
                        pstmt.setInt(31, tubeWellSurveyBean.getAuto_switch_type_id());
                    } else {
                        pstmt.setNull(31, java.sql.Types.INTEGER);
                    }
                    if (tubeWellSurveyBean.getMain_switch_type_id() != 0) {
                        pstmt.setInt(32, tubeWellSurveyBean.getMain_switch_type_id());
                    } else {
                        pstmt.setNull(32, java.sql.Types.INTEGER);
                    }
                    if (!tubeWellSurveyBean.getMain_switch_rating().isEmpty()) {
                        pstmt.setString(33, tubeWellSurveyBean.getMain_switch_rating());
                    } else {
                        pstmt.setNull(33, java.sql.Types.NULL);
                    }
                    if (tubeWellSurveyBean.getEnclosure_type_id() != 0) {
                        pstmt.setInt(34, tubeWellSurveyBean.getEnclosure_type_id());
                    } else {
                        pstmt.setNull(34, java.sql.Types.NULL);
                    }
                    if (tubeWellSurveyBean.getMeter_functional().equals("N")) {
                        pstmt.setInt(35, tubeWellSurveyBean.getReason_id());//getReasonId(
                    } else {
                        pstmt.setNull(35, java.sql.Types.INTEGER);
                    }
                    pstmt.setInt(36, survey_rev_no);
                   // pstmt.setInt(37, tubeWellSurveyBean.getPole_id());
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        if (!tubeWellSurveyBean.getImage_name().isEmpty()) {
                            try {
                               
                            } catch (Exception e) {
                                System.out.println("Error:keypersonModel--insertRecord-- " + e);
                            }
                            if (rowsAffected > 0) {
                                try {
                                    String tmp_image = tubeWellSurveyBean.getImage_name();
                                    rowsAffected = 0;
                                    if (!list.isEmpty() && writeImage(tubeWellSurveyBean, list, survey_id, survey_rev_no) > 0) {
                                         pstmt = connection.prepareStatement(imageQuery);
                                         pstmt.setString(1, tubeWellSurveyBean.getImage_name());
                                         pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
                                         pstmt.setString(3, current_date);
                                         pstmt.setString(4, "this image is for survey");
                                         rowsAffected = pstmt.executeUpdate();
                                         ResultSet img_rs = pstmt.getGeneratedKeys();
                                         int id = 0;
                                         if(img_rs.next())
                                             id = img_rs.getInt(1);
                                         pstmt.close();

                                        pstmt = connection.prepareStatement(updateQuery);
                                        pstmt.setInt(1, id);//getgeneral_image_details_id(tubeWellSurveyBean.getImage_name()));
                                        pstmt.setString(2, tubeWellSurveyBean.getImage_name());
                                        pstmt.setString(3, tmp_image);
                                        rowsAffected = pstmt.executeUpdate();

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
                        if (rowsAffected > 0) {
                            AlertsModel alertModel = new AlertsModel();
                            alertModel.setConnection(connection);
                            rowsAffected = alertModel.insertAlertSheetData(survey_id, pole_no);
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
                if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
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

    public int writeImage(TubeWellSurveyBean surveyBean, List<File> file, int survey_id, int survey_rev_no) {
        // getimage_destination_id();
        boolean isCreated = true;
        String imageName = surveyBean.getImage_name();
        String image_name = "";
        int rowsAffected = 0;
        try {
            File srcfile = null;
            survey_rev_no = survey_rev_no + 1;
            //      String dayOfMonthFolder = createAppropriateDirectories(destination_path);
            destination_path = getRepositoryPath("Survey Image") + "//" + "switching_point";//survey_id;
            File desfile = new File(destination_path);
            if (!desfile.exists()) {
                isCreated = desfile.mkdirs();
            }
            File folder = new File(destination_path);
            boolean isuploaded = false;
            Iterator<File> fileItr = file.iterator();
            int number_of_file = folder.list().length;
            while (fileItr.hasNext()) {
//                Object image = fileItr.next();
//                tempSource = image.toString();
                //       number_of_file++;
                srcfile = fileItr.next();
                String img_name = srcfile.getName();//.replace(".", "%#");
                if(image_name.isEmpty())
                    image_name = img_name;
                else
                    image_name = image_name + "," + img_name;
                // String ext = srcfile.getName().replace(".", "%#");
                //ext = ext.split("%#")[1];
//                String image = srcfile.toString();
//                int index = image.indexOf('.');
//                String fileNameWithOutExt = FilenameUtils.removeExtension(imageName);
//                String ext = image.substring(index, image.length());
//                String image_name = fileNameWithOutExt + "_" + number_of_file + ext;
//                System.out.println("" + image_name);

                File temp_desfile = new File(desfile + "/" + img_name);
                // desfile = new File(desfile + "/" + fileNameWithOutExt + "_" + number_of_file + ext);
                isuploaded = srcfile.renameTo(temp_desfile);

                //isuploaded = srcfile.renameTo(desfile);

                if (isuploaded) {
                    rowsAffected = 1;
                    message = "Image Uploaded Successfully.";
                    msgBgColor = COLOR_OK;

                }

            }
            surveyBean.setImage_name(image_name);
            File deleteFile = new File(getRepositoryPath("General"));
            deleteDirectory(deleteFile);
        } catch (Exception ex) {
            System.out.println("File write error" + ex);
            message = "Cannot upload the image, some error.";
            msgBgColor = COLOR_ERROR;
            PreparedStatement pstmt;
            int id;
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
        return rowsAffected;
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
//   public String getImageName(int survey_id, int survey_rev_no) {
//        String image_name = "";
//        String query = "select image_name from survey WHERE survey_id = '"+survey_id+"' and survey_rev_no = '"+survey_rev_no+"' and status='Y'";
//        try {
//            PreparedStatement ps = connection.prepareStatement(query);
//System.out.println(""+query);
////            ps.setInt(2, survey_rev_no);
//            ResultSet rset = ps.executeQuery();
//           while (rset.next()) {
//                image_name = rset.getString("image_name");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Exception in ChangeStatusToApproved() in surveyModel" + e);
//        }
//        return image_name;
//    }

    public String getImageName(int survey_id, int survey_rev_no) {
        String image_name = "";
        String query = "select image_name, general_image_details_id from survey WHERE survey_id = '" + survey_id + "' and survey_rev_no=" + survey_rev_no;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, fuse_type);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                image_name = rset.getString("image_name");
                general_image_details_id = rset.getInt("general_image_details_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getFuseId() in surveyModel" + e);
        }
        return image_name;
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

    public int ChangeStatusToApprovedInSwitching(int tube_well_survey_id, int tube_well_survey_rev_no) {
        int rowsAffected = 0;
        String query = "Update tube_well_survey SET active='N' WHERE tube_well_survey_id = ? and "
                + "tube_well_survey_rev_no = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tube_well_survey_id);
            ps.setInt(2, tube_well_survey_rev_no);
            rowsAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception in ChangeStatusToApprovedInSwitching() in surveyModel" + e);
        }
        return rowsAffected;
    }

    public int cancelRecord(TubeWellSurveyBean tubeWellSurveyBean) {
        String query1 = "UPDATE survey SET status = 'N' WHERE survey_id = ? AND survey_rev_no = ?";
        String query2 = "UPDATE switching_point_survey SET active = 'N' WHERE switching_point_survey_id = ? AND switching_point_survey_rev_no = ? ";
        int rowsAffected = 0;

        int survey_id = tubeWellSurveyBean.getSurvey_id();
        int survey_rev_no = tubeWellSurveyBean.getSurvey_rev_no();
        int tube_well_survey_id = tubeWellSurveyBean.getTube_well_survey_id();
        int tube_well_survey_rev_no = tubeWellSurveyBean.getTube_well_survey_rev_no();
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, survey_id);
            pstmt.setInt(2, survey_rev_no);
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                if (tubeWellSurveyBean.getSurvey_type().equals("tubewell_type_survey")) {
                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(query2);
                    pstmt.setInt(1, tube_well_survey_id);
                    pstmt.setInt(2, tube_well_survey_rev_no);
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
                if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
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

    public int reviseRecord(TubeWellSurveyBean tubeWellSurveyBean, List list) {
        String query = "INSERT INTO survey (survey_file_no, survey_page_no, mobile_no, pole_no,  survey_type, "
                + " remark, survey_date,image_name,image_date_time,longitude,lattitude,survey_pole_no,survey_rev_no,survey_id, general_image_details_id) "
                + "Select ?,?,?,?,?,?,?,?,?,?,?,?,?,?, general_image_details_id from survey where  survey_id=? and survey_rev_no=?";
        String query2 = "INSERT INTO switching_point_survey (switching_point_survey_id, meter_no, meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, mccb_functional,"
                + " remark,created_by,fuse1,fuse2,fuse3, contacter_id,"
                + "contacter_make_id,contacter_capacity,mccb1,mccb2,mccb3,fuse_id1,fuse_id2,"
                + "fuse_id3,mccb_id1,mccb_id2,mccb_id3,no_of_phase,meter_phase,meter_reading,"
                + "auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id,reason_id, "
                + " switching_point_survey_rev_no,survey_rev_no, meter_name_auto, service_conn_no, previous_reading, consume_unit, amount)"
                + " Select switching_point_survey_id,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, "
                + " meter_name_auto, service_conn_no, previous_reading, consume_unit, amount "
                + " from switching_point_survey where switching_point_survey_id=? and switching_point_survey_rev_no=? ";

        String query4 = "UPDATE survey SET status = 'N' WHERE survey_id = ? and survey_rev_no=?";
        String query5 = "UPDATE switching_point_survey SET active = 'N' WHERE switching_point_survey_id = ? and switching_point_survey_rev_no=?";
        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description) "
                + " VALUES(?, ?, ?, ?)";

        String updateQuery = "UPDATE survey set general_image_details_id=?,image_name=? where image_name=? and status='Y' ";

        String revseQuery = "meter_id, revision, meter_name, security_deposit, sd_receipt_no, date, initial_reading, city_id, meter_service_number,"
                + " poll_no, active, organisation_id, org_office_id, switching_point_id, feeder_id, sanctioned_load_kw, reason, final_revision, phase,"
                + " accessed_load, effective_date, updated_date, calculated_load, description, tariff_code, msn_first_part, msn_sec_part, msn_third_part, "
                + " msn_fourth_part, ivrs_no, file_no, calculated_security_deposit, meter_name_auto, sanct_load_unit_id, latitude, longitude, "
                + "ward_no, bill_sanction_load, premises_tariff_map_id, premises_tariff_map_rev, address_asper_Bill, general_img_details_id, program_version_no";
        int survey_id = 0;
        int survey_rev_no = 0;
        String image_uploaded_for = "Survey Image";
        String pole_no = tubeWellSurveyBean.getPole_no();
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String status = tubeWellSurveyBean.getStatus();
        //   String[] image_date_time= tubeWellSurveyBean.getImage_name().split("-");
        //   String date_time=image_date_time[1];
        int rowsAffected = 0;

        survey_id = tubeWellSurveyBean.getSurvey_id();
        survey_rev_no = tubeWellSurveyBean.getSurvey_rev_no();
        int tube_well_survey_id = tubeWellSurveyBean.getTube_well_survey_id();
        int tube_well_survey_rev_no = tubeWellSurveyBean.getTube_well_survey_rev_no();
        pole_no = tubeWellSurveyBean.getPole_no();
        String image_name = getImageName(survey_id, survey_rev_no);
        String fileNameWithOutExt = FilenameUtils.removeExtension(image_name);
        int no_of_file = checkImage(fileNameWithOutExt);
        if (no_of_file > 0 && !list.isEmpty()) {
//            fileNameWithOutExt = FilenameUtils.removeExtension(image_name);
//            int index = image_name.indexOf('.');
//            System.out.println(index);
//            String ext = image_name.substring(index, image_name.length());
//
//            image_name = fileNameWithOutExt + "_" + no_of_file + ext;
//            tubeWellSurveyBean.setImage_name(image_name);

        } else {
            if(list.isEmpty())
                tubeWellSurveyBean.setImage_name(image_name);
        }
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);


            PreparedStatement pstmt = connection.prepareStatement(query4);
            pstmt.setInt(1, survey_id);
            pstmt.setInt(2, survey_rev_no);
            rowsAffected = pstmt.executeUpdate();
            // if (rowsAffected > 0) {
            if (tubeWellSurveyBean.getSurvey_type().equals("tubewell_type_survey")) {
                pstmt.close();
                rowsAffected = 0;
                pstmt = connection.prepareStatement(query5);
                pstmt.setInt(1, tube_well_survey_id);
                pstmt.setInt(2, survey_rev_no);
                rowsAffected = pstmt.executeUpdate();

            }
            pstmt.close();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, tubeWellSurveyBean.getSurvey_file_no());
            pstmt.setString(2, tubeWellSurveyBean.getSurvey_page_no());
            pstmt.setString(3, tubeWellSurveyBean.getSurvey_by());
            if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
                pstmt.setInt(4, tubeWellSurveyBean.getPole_id());
                //  pstmt.setInt(5, tubeWellSurveyBean.getPole_rev_no());
            } else {
                pstmt.setString(4, tubeWellSurveyBean.getPole_no());
                // pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.setString(5, "Switching Point");//tubeWellSurveyBean.getSurvey_type()
            pstmt.setString(6, tubeWellSurveyBean.getRemark());

            if (tubeWellSurveyBean.getSurvey_date() != null && !(tubeWellSurveyBean.getSurvey_date().trim()).isEmpty()) {
                pstmt.setDate(7, convertToSqlDate(tubeWellSurveyBean.getSurvey_date()));
            } else {
                pstmt.setNull(7, java.sql.Types.DATE);
            }
            pstmt.setString(8, tubeWellSurveyBean.getImage_name());
            pstmt.setString(9, null);
            pstmt.setString(10, tubeWellSurveyBean.getLongitude());
            pstmt.setString(11, tubeWellSurveyBean.getLatitude());
            pstmt.setString(12, tubeWellSurveyBean.getSurvey_pole_no());
            pstmt.setInt(13, survey_rev_no + 1);
            pstmt.setInt(14, survey_id);
            pstmt.setInt(15, survey_id);
            pstmt.setInt(16, survey_rev_no);
            //pstmt.setInt(17, general_image_details_id);
            rowsAffected = pstmt.executeUpdate();
            pstmt = connection.prepareStatement(query2);
            //pstmt.setInt(1, survey_id);
            if (tubeWellSurveyBean.getMeter_status().equals("Y")) {
                if (tubeWellSurveyBean.getMeter_functional().equals("Y")) {
                    if (!tubeWellSurveyBean.getMeter_no().isEmpty()) {
                        pstmt.setString(1, tubeWellSurveyBean.getMeter_no());
                    } else {
                        pstmt.setNull(1, java.sql.Types.NULL);
                    }

                    pstmt.setString(2, tubeWellSurveyBean.getMeter_functional());
                    pstmt.setInt(28, tubeWellSurveyBean.getMeter_phase());
                    if (!tubeWellSurveyBean.getMeter_reading().isEmpty()) {
                        pstmt.setString(29, tubeWellSurveyBean.getMeter_reading());
                    } else {
                        pstmt.setNull(29, java.sql.Types.NULL);
                    }

                } else {
                    pstmt.setString(1, tubeWellSurveyBean.getMeter_no());
                    pstmt.setString(2, tubeWellSurveyBean.getMeter_functional());
                    pstmt.setInt(28, tubeWellSurveyBean.getMeter_phase());
                    if (!tubeWellSurveyBean.getMeter_reading().isEmpty()) {
                        pstmt.setString(29, tubeWellSurveyBean.getMeter_reading());
                    } else {
                        pstmt.setNull(29, java.sql.Types.NULL);
                    }
                }
            } else {
                pstmt.setString(2, tubeWellSurveyBean.getMeter_no());
                pstmt.setString(3, tubeWellSurveyBean.getMeter_functional());
                pstmt.setInt(28, tubeWellSurveyBean.getMeter_phase());
                if (!tubeWellSurveyBean.getMeter_reading().isEmpty()) {
                    pstmt.setString(29, tubeWellSurveyBean.getMeter_reading());
                } else {
                    pstmt.setNull(29, java.sql.Types.NULL);
                }
                ;
            }

            if (tubeWellSurveyBean.getNo_of_phase() == 3) {
                pstmt.setString(3, tubeWellSurveyBean.getR_phase());
                pstmt.setString(4, tubeWellSurveyBean.getY_phase());
                pstmt.setString(5, tubeWellSurveyBean.getB_phase());
            } else {
                pstmt.setNull(3, java.sql.Types.DOUBLE);
                pstmt.setNull(4, java.sql.Types.DOUBLE);
                pstmt.setString(5, tubeWellSurveyBean.getB_phase());
            }

            pstmt.setString(6, tubeWellSurveyBean.getPower());
            pstmt.setString(7, tubeWellSurveyBean.getFuse_functional());
            pstmt.setString(8, tubeWellSurveyBean.getStarter_functional());
            pstmt.setString(9, tubeWellSurveyBean.getMccb_functional());
            //  pstmt.setString(11, tubeWellSurveyBean.getTimer_functional());
            pstmt.setString(10, tubeWellSurveyBean.getRemark());
            pstmt.setString(11, "Viney Srivastva");
            //    pstmt.setString(13, tubeWellSurveyBean.getMeter_status());
            if (!tubeWellSurveyBean.getFuse1().isEmpty()) {
                pstmt.setString(12, tubeWellSurveyBean.getFuse1());
            } else {
                pstmt.setNull(12, java.sql.Types.NULL);
            }
            if (!tubeWellSurveyBean.getFuse2().isEmpty()) {
                pstmt.setString(13, tubeWellSurveyBean.getFuse2());
            } else {
                pstmt.setNull(13, java.sql.Types.NULL);
            }

            if (!tubeWellSurveyBean.getFuse3().isEmpty()) {
                pstmt.setString(14, tubeWellSurveyBean.getFuse3());
            } else {
                pstmt.setNull(14, java.sql.Types.NULL);
            }
            if (tubeWellSurveyBean.getStarter_id() != 0) {
                pstmt.setInt(15, tubeWellSurveyBean.getStarter_id());
            } else {
                pstmt.setNull(15, java.sql.Types.INTEGER);
            }
            if (tubeWellSurveyBean.getStarter_make_id() != 0) {
                pstmt.setInt(16, tubeWellSurveyBean.getStarter_make_id());
            } else {
                pstmt.setNull(16, java.sql.Types.INTEGER);
            }
            pstmt.setString(17, tubeWellSurveyBean.getStarter_capacity());
            if (!tubeWellSurveyBean.getMccb1().isEmpty()) {
                pstmt.setString(18, tubeWellSurveyBean.getMccb1());
            } else {
                pstmt.setNull(18, java.sql.Types.NULL);
            }
            if (!tubeWellSurveyBean.getMccb2().isEmpty()) {
                pstmt.setString(19, tubeWellSurveyBean.getMccb2());
            } else {
                pstmt.setNull(19, java.sql.Types.NULL);
            }
            if (!tubeWellSurveyBean.getMccb3().isEmpty()) {
                pstmt.setString(20, tubeWellSurveyBean.getMccb3());
            } else {
                pstmt.setNull(20, java.sql.Types.NULL);
            }
            if (tubeWellSurveyBean.getFuse_id1() != 0) {
                pstmt.setInt(21, tubeWellSurveyBean.getFuse_id1());
            } else {
                pstmt.setNull(21, java.sql.Types.INTEGER);
            }
            if (tubeWellSurveyBean.getFuse_id2() != 0) {
                pstmt.setInt(22, tubeWellSurveyBean.getFuse_id2());
            } else {
                pstmt.setNull(22, java.sql.Types.INTEGER);
            }
            if (tubeWellSurveyBean.getFuse_id3() != 0) {
                pstmt.setInt(23, tubeWellSurveyBean.getFuse_id3());
            } else {
                pstmt.setNull(23, java.sql.Types.INTEGER);
            }
            if (tubeWellSurveyBean.getMccb_id1() != 0) {
                pstmt.setInt(24, tubeWellSurveyBean.getMccb_id1());
            } else {
                pstmt.setNull(24, java.sql.Types.INTEGER);
            }
            if (tubeWellSurveyBean.getMccb_id2() != 0) {
                pstmt.setInt(25, tubeWellSurveyBean.getMccb_id2());
            } else {
                pstmt.setNull(25, java.sql.Types.INTEGER);
            }
            if (tubeWellSurveyBean.getMccb_id3() != 0) {
                pstmt.setInt(26, tubeWellSurveyBean.getMccb_id3());
            } else {
                pstmt.setNull(26, java.sql.Types.INTEGER);
            }

            pstmt.setInt(27, tubeWellSurveyBean.getNo_of_phase());
            if (tubeWellSurveyBean.getAuto_switch_type_id() != 0) {
                pstmt.setInt(30, tubeWellSurveyBean.getAuto_switch_type_id());
            } else {
                pstmt.setNull(30, java.sql.Types.INTEGER);
            }
            if (tubeWellSurveyBean.getMain_switch_type_id() != 0) {
                pstmt.setInt(31, tubeWellSurveyBean.getMain_switch_type_id());
            } else {
                pstmt.setNull(31, java.sql.Types.INTEGER);
            }
            if (!tubeWellSurveyBean.getMain_switch_rating().isEmpty()) {
                pstmt.setString(32, tubeWellSurveyBean.getMain_switch_rating());
            } else {
                pstmt.setNull(32, java.sql.Types.NULL);
            }
            if (tubeWellSurveyBean.getEnclosure_type_id() != 0) {
                pstmt.setInt(33, tubeWellSurveyBean.getEnclosure_type_id());
            } else {
                pstmt.setNull(33, java.sql.Types.NULL);
            }
            if (tubeWellSurveyBean.getMeter_functional().equals("N")) {
                pstmt.setInt(34, getReasonId("abc"));//tubeWellSurveyBean.getReason_type()
            } else {
                pstmt.setNull(34, java.sql.Types.INTEGER);
            }
            pstmt.setInt(35, tube_well_survey_rev_no + 1);
            pstmt.setInt(36, survey_rev_no + 1);
            pstmt.setInt(37, survey_id);
            pstmt.setInt(38, survey_rev_no);
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {

                //  if (rowsAffected > 0) {
                if (!tubeWellSurveyBean.getImage_name().isEmpty() && !list.isEmpty()) {
                    try {
                        
                    } catch (Exception e) {
                        System.out.println("Error:keypersonModel--insertRecord-- " + e);
                    }
                }
                if (rowsAffected > 0) {
                    try {
                        String tmp_img = tubeWellSurveyBean.getImage_name();
                        //   tubeWellSurveyBean.setImage_name(getImageName(survey_id, survey_rev_no));
                        if (!list.isEmpty() && writeImage(tubeWellSurveyBean, list, survey_id, survey_rev_no) > 0) {
                            pstmt = connection.prepareStatement(imageQuery);
                            pstmt.setString(1, tubeWellSurveyBean.getImage_name());
                            pstmt.setInt(2, getimage_destination_id(image_uploaded_for));
                            pstmt.setString(3, current_date);
                            pstmt.setString(4, "this image is for survey");
                            rowsAffected = pstmt.executeUpdate();
                            ResultSet img_rs = pstmt.getGeneratedKeys();
                            int id = 0;
                            if(img_rs.next())
                                id = img_rs.getInt(1);
                            pstmt.close();

                            pstmt = connection.prepareStatement(updateQuery);
                            pstmt.setInt(1, id);//getgeneral_image_details_id(tubeWellSurveyBean.getImage_name()));
                            pstmt.setString(2, tubeWellSurveyBean.getImage_name());
                            pstmt.setString(3, tmp_img);
                            pstmt.executeUpdate();
                        }
                    } catch (Exception e) {
                        System.out.println("Exception :" + e);
                    }

                }
                // }
                // rowsAffected = writeImage(key, itr, destination);
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
                connection.commit();
                if (rowsAffected > 0) {
                    AlertsModel alertModel = new AlertsModel();
                    alertModel.setConnection(connection);
                    rowsAffected = alertModel.insertAlertSheetData(survey_id, pole_no);
                    rowsAffected = 1;
                }
                // if(rowsAffected>0){
//                 pstmt.close();
//                        rowsAffected = 0;
//                        pstmt = connection.prepareStatement(updateQuery1);
//                        pstmt.setInt(1, getSusrvey(tubeWellSurveyBean.getPole_no()));
//                        pstmt.setInt(2, getSusrveyRevNo(tubeWellSurveyBean.getPole_no()));
//                        rowsAffected = pstmt.executeUpdate();
//                }

            } else {
                connection.rollback();
            }
//            }
//            else {
//                connection.rollback();
//            }
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
                if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
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
    }

    public int cancellRecord(TubeWellSurveyBean tubeWellSurveyBean) {
        String query = "INSERT INTO survey ( tube_well_detail_id, tube_well_rev_no, "
                + " survey_file_no, survey_page_no, survey_by, pole_id, pole_rev_no, survey_type, "
                + " remark, survey_date, survey_id, survey_rev_no) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query2 = "INSERT INTO tube_well_survey ( survey_id, meter_no,"
                + "  meter_functional,"
                + " r_phase, y_phase, b_phase, power, fuse_functional, starter_functional, mccb_functional,"
                + " timer_functional,remark,created_by, tube_well_survey_id, tube_well_survey_rev_no, survey_rev_no)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String status = tubeWellSurveyBean.getStatus();
        int rowsAffected = 0;

        int survey_id = tubeWellSurveyBean.getSurvey_id();
        int survey_rev_no = tubeWellSurveyBean.getSurvey_rev_no();
        int tube_well_survey_id = tubeWellSurveyBean.getTube_well_survey_id();
        int tube_well_survey_rev_no = tubeWellSurveyBean.getTube_well_survey_rev_no();
        String pole_no = tubeWellSurveyBean.getPole_no();

        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setInt(1, survey_id + 1);
            //pstmt.setInt(1, tubeWellSurveyBean.getSwitching_point_detail_id());
            if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
                pstmt.setNull(1, java.sql.Types.INTEGER);
                pstmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(1, 1);
                pstmt.setInt(2, 0);
            }
            pstmt.setString(3, tubeWellSurveyBean.getSurvey_file_no());
            pstmt.setString(4, tubeWellSurveyBean.getSurvey_page_no());
            pstmt.setString(5, tubeWellSurveyBean.getSurvey_by());
            if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
                pstmt.setInt(6, getPole_id(pole_no));
                pstmt.setInt(7, getMaxRevNo(pole_no));
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }
            pstmt.setString(8, tubeWellSurveyBean.getSurvey_type());
            pstmt.setString(9, tubeWellSurveyBean.getRemark());

            if (tubeWellSurveyBean.getSurvey_date() != null && !(tubeWellSurveyBean.getSurvey_date().trim()).isEmpty()) {
                pstmt.setDate(10, convertToSqlDate(tubeWellSurveyBean.getSurvey_date()));
            } else {
                pstmt.setNull(10, java.sql.Types.DATE);
            }
            pstmt.setInt(11, survey_id);
            pstmt.setInt(12, survey_rev_no + 1);

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rowsAffected = ChangeStatusToCancel(survey_id, survey_rev_no);
                if (tubeWellSurveyBean.getSurvey_type().equals("tubewell_type_survey")) {


                    if (rowsAffected > 0) {
                        rs = pstmt.getGeneratedKeys();
                        while (rs.next()) {
                            survey_id = rs.getInt(1);
                        }
                        rowsAffected = 0;
                        pstmt.close();
                        pstmt = connection.prepareStatement(query2);

                        pstmt.setInt(1, survey_id);
                        pstmt.setString(2, tubeWellSurveyBean.getMeter_no());
                        pstmt.setString(3, tubeWellSurveyBean.getMeter_functional());
                        pstmt.setString(4, tubeWellSurveyBean.getR_phase());
                        pstmt.setString(5, tubeWellSurveyBean.getY_phase());
                        pstmt.setString(6, tubeWellSurveyBean.getB_phase());
                        pstmt.setString(7, tubeWellSurveyBean.getPower());
                        pstmt.setString(8, tubeWellSurveyBean.getFuse_functional());
                        //  pstmt.setString(9, tubeWellSurveyBean.getContacter_funactional());
                        pstmt.setString(10, tubeWellSurveyBean.getMccb_functional());
                        //  pstmt.setString(11, tubeWellSurveyBean.getTimer_functional());
                        pstmt.setString(12, tubeWellSurveyBean.getRemark());
                        pstmt.setString(13, "Viney Srivastva");
                        pstmt.setInt(14, tube_well_survey_id);
                        pstmt.setInt(15, tube_well_survey_rev_no + 1);
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
                if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
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
        String query = " SELECT st.switch_type FROM switch_type as st,switch as s where s.switch_id=switch_type_id"
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
        String query = " SELECT st.switch_type FROM switch_type as st,switch as s where s.switch_id=switch_type_id"
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

    public int getNoOfRows(String pole_no, String searchIvrsNo,String searchFileNo,String searchPageNo,String searchByDate,String meter_name_auto,String survey_id,String searchMeterFunctional,String searchFeeder,String searchTypeOfConnection,String searchToDate) {
//        String query = " SELECT Count(*) "
//                + " FROM survey s, tube_well_survey sps, pole p, tube_well_detail sp "
//                + " WHERE s.survey_id = sps.survey_id "
//                + " AND s.survey_rev_no = sps.survey_rev_no "
//                + " AND IF('" + searchPoleno + "' = '', p.pole_no LIKE '%%', p.pole_no =? ) "
//                + " AND IF('" + searchSwitchNo + "' = '', sp.pole_no_s LIKE '%%', sp.pole_no_s =? ) "
//                + " ORDER BY pt.pole_type";
        
        /*String queryy = "SELECT count(switching_point_survey_id) as total "
                + " FROM switching_point_survey as t "
                + " LEFT JOIN(contacter as sr,contacter_make as sm) ON sr.contacter_id=t.contacter_id AND sm.contacter_make_id=t.contacter_make_id "
                + "left join meters as m ON m.meter_name_auto=t.meter_name_auto"
                +"   left join feeder as f ON f.feeder_id=m.feeder_id"
                +"  left join premises_tariff_map as ptm ON m.premises_tariff_map_id=ptm.premises_tariff_map_id"
                +"    left join type_of_premises as top ON top.type_of_premises_id=ptm.type_of_premises_id"
                + " ,survey as s "//,contacter as sr,contacter_make as sm "
                + " where t.active='Y' and s.status='Y' "
                + " AND IF('" + pole_no + "' = '',  s.pole_no LIKE '%%', s.pole_no=? )"
                + " AND IF('" + searchFileNo + "' = '',  s.survey_file_no LIKE '%%', s.survey_file_no=? )"
                + " AND IF('" + searchPageNo + "' = '',  s.survey_page_no LIKE '%%', s.survey_page_no=? )"
                + " AND IF('" + searchIvrsNo + "' = '',  t.service_conn_no LIKE '%%' OR t.service_conn_no IS null, t.service_conn_no=? )"
                + " AND IF('" + searchByDate + "' = '',  s.survey_date LIKE '%%', s.survey_date>= '"+searchByDate+"' )"
                + " AND IF('" + survey_id+ "' = '',  s.survey_id LIKE '%%', s.survey_id = '"+survey_id+"' )"
                 + " AND IF('" + searchMeterFunctional+ "' = '',  t.meter_functional LIKE '%%', t.meter_functional = '"+searchMeterFunctional+"' )"
                 + " AND IF('" + searchFeeder + "' = '',  f.feeder_name LIKE '%%', f.feeder_name= '"+searchFeeder+"' )"
                 + " AND IF('" + searchTypeOfConnection + "' = '',  top.type_of_premsis LIKE '%%', top.type_of_premsis= '"+searchTypeOfConnection+"' )"
                 + " AND IF('" + searchToDate + "' = '',  s.survey_date LIKE '%%', s.survey_date= '"+searchToDate+"' )"
                + "  order by switching_point_survey_id ";*/
//                + " FROM tube_well_survey as t,survey as s where  t.tube_well_survey_id=s.survey_id "
//                + " and t.active='Y' and s.status='Y'"
//                + " AND IF('" + pole_no + "' = '',  s.pole_no LIKE '%%', s.pole_no=? ) "
//                + " AND IF('" + searchMeterNo + "' = '',  t.meter_no LIKE '%%', t.meter_no=? )";
        //    + " group by tube_well_survey_id order by tube_well_survey_id ";
        //"SELECT count(*) as total FROM tube_well_survey as t, fuse as f , fuse as f1, fuse as f2  , mccb as m,"
//                + "   mccb as m1  ,mccb as m2,  switch_type as st, switch_type as st1 ,survey as s,starter as sr,"
//                + "starter_make as sm,reason_type as r,enclosure_type as e where t.survey_id=s.survey_id and "
//                + "t.survey_rev_no=s.survey_rev_no  and sr.starter_id=t.starter_id and sm.starter_make_id=t.starter_make_id"
//                + " and f.fuse_id=t.fuse_id1 and f1.fuse_id=t.fuse_id2 and f2.fuse_id=t.fuse_id3 and m.mccb_id=t.mccb_id1"
//                + " and m1.mccb_id=t.mccb_id2 and m2.mccb_id=t.mccb_id3 and t.auto_switch_type_id=st.switch_type_id "
//                + "and  t.main_switch_type_id=st1.switch_type_id and e.enclosure_type_id=t.enclosure_type_id "
//                + "AND if(fuse_id1 is null,null,fuse_id1) AND if(fuse_id2 is null,null,fuse_id2) "
//                + "AND if(fuse_id3 is null,null,fuse_id3) AND if(mccb_id1 is null,null,mccb_id1) "
//                + "AND if(mccb_id2 is null,null,mccb_id2) AND if(mccb_id3 is null,null,mccb_id3) "
//                + "order by tube_well_survey_id ";
        //            + " WHERE IF('" + pole_no + "' = '', s.survey_id LIKE '%%', p.pole_no=? ) ";
        String queryy="SELECT count(*) as total"
                       +" FROM switching_point_survey as t"
                       +" LEFT JOIN(contacter as sr,contacter_make as sm) ON sr.contacter_id=t.contacter_id AND sm.contacter_make_id=t.contacter_make_id"
                       +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID'"
                       +" left join feeder as f ON f.feeder_id=m.feeder_id"
                       +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id"
                       +" AND top.type_of_premises_id=ptm.type_of_premises_id"
                       +" ,survey as s"
                       +" where t.switching_point_survey_id=s.survey_id   and t.active='Y'"
                       +" and s.status='Y'"
                       +" AND IF('"+pole_no+"' = '',  s.pole_no LIKE '%%', s.pole_no=? )"
                       +" AND IF('"+searchFileNo+"' = '',  s.survey_file_no LIKE '%%', s.survey_file_no=? )"
                       +" AND IF('"+searchPageNo+"' = '',  s.survey_page_no LIKE '%%', s.survey_page_no=? )"
                       +" AND IF('"+searchIvrsNo+"' = '',  t.service_conn_no LIKE '%%' OR t.service_conn_no IS null, t.service_conn_no=? )"
                       +" AND IF('"+searchByDate+"' = '',  s.survey_date LIKE '%%', s.survey_date >= '"+searchByDate+"' )"
                       +" AND IF('"+meter_name_auto+"' = '',  t.meter_name_auto LIKE '%%' OR t.meter_name_auto IS null, t.meter_name_auto = '"+meter_name_auto+"' )"
                       +" AND IF('"+survey_id+"' = '',  s.survey_id LIKE '%%', s.survey_id = '"+survey_id+"' )"
                       +" AND IF('"+searchMeterFunctional+"' = '',  t.meter_functional LIKE '%%', t.meter_functional = '"+searchMeterFunctional+"' )"
                       +" AND IF('"+searchFeeder+"' = '',  f.feeder_name LIKE '%%', f.feeder_name= '"+searchFeeder+"' )"
                       +"  AND IF('"+searchTypeOfConnection+"' = '',  top.type_of_premsis LIKE '%%', top.type_of_premsis= '"+searchTypeOfConnection+"' )"
                       +"  AND IF('"+searchToDate+"' = '',  s.survey_date LIKE '%%', s.survey_date <= '"+searchToDate+"' )"
                       +" order by switching_point_survey_id";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(queryy);
            stmt.setString(1, pole_no);
            stmt.setString(2, searchFileNo);
            stmt.setString(3, searchPageNo);
            stmt.setString(4, searchIvrsNo);
            
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

    public String getPole_No(int pole_id) {
        String pole_no = null;
        String query = "select pole_no from pole where pole_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, pole_id);
            //   ps.setInt(2, pole_rev_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                pole_no = rset.getString("pole_no");
            }
        } catch (Exception e) {
            System.out.println("Exception in getPole_No() in surveyModel" + e);
        }
        return pole_no;
    }

    public int getFuseId(String fuse_type) {
        int fuse_id = 0;
        String query = "select fuse_id from fuse where fuse_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, fuse_type);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                fuse_id = rset.getInt("fuse_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getFuseId() in surveyModel" + e);
        }
        return fuse_id;
    }
    
 public int getContacterId(String contracter_type) {
        int contracter_id = 0;
        String query = "select contacter_id from contacter where contacter_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, contracter_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                contracter_id = rset.getInt("contacter_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in  getContacterId() in NewSwitchingPointSurveyModel " + e);
        }
        return contracter_id;
    }

    public int getMccbId(String mccb_type) {
        int mccb_id = 0;
        String query = "select mccb_id from mccb where mccb_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                mccb_id = rset.getInt("mccb_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbId() in surveyModel" + e);
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

    public String getContracterType(int contracter_id) {
        String contracter_type = "";
        String query = "select starter_type from starter where starter_id'" + contracter_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            // ps.setString(1, contracter_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                contracter_type = rset.getString("starter_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getContracterType() in surveyModel" + e);
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

    public String getSwitchType(int switch_id) {
        String switch_type = "";
        String query = "select switch_type from switch_type where switch_type_id=" + switch_id + "";
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

    public String getEnclosureType(int enclosure_type_id) {
        String switch_type = "";
        String query = "select enclosure_type from enclosure_type where enclosure_type_id='" + enclosure_type_id + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            //  ps.setString(1, mccb_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switch_type = rset.getString("enclosure_type");
            }
        } catch (Exception e) {
            System.out.println("Exception in getMccbType() in surveyModel" + e);
        }
        return switch_type;
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

    public int getReasonId(String reason_type) {
        int reason_id = 0;
        String query = "select reason_id from reason_type where reason_type=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, reason_type);

            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                reason_id = rset.getInt("reason_id");
            }
        } catch (Exception e) {
            System.out.println("Exception in getReasonId() in surveyModel" + e);
        }
        return reason_id;
    }

    public String getSwitchingTypePole_No(int tube_well_detail_id, int tube_well_rev_no) {
        String pole_no = null;
        String query = "select pole_no from pole where tube_well_detail_id=? and tube_well_rev_no =?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tube_well_detail_id);
            ps.setInt(2, tube_well_rev_no);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                pole_no = rset.getString("pole_no");
            }
        } catch (Exception e) {
            System.out.println("Exception in getSwitchingTypePole_No() in surveyModel" + e);
        }
        return pole_no;
    }

    //public List<TubeWellSurveyBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchPoleNo, String searchSwitchNo) {
    public List showData(int lowerLimit, int noOfRowsToDisplay, String pole_no, String searchIvrsNo,String searchFileNo,String searchPageNo,String searchByDate, String meter_name_auto,int countnum,String survey_id,String searchMeterFunctional,String searchFeeder,String searchTypeOfConnection,String searchToDate)
        {
        List list = new ArrayList();
        int surveyId=0;
        if(survey_id.isEmpty() || survey_id==null)
        {
            survey_id="";
        }
        String query = null;
        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1)
            addLimit = "";
        
//        String query = " select s.survey_id, s.tube_well_rev_no,"
//                + "s.survey_file_no, s.survey_date, s.survey_page_no, s.survey_by "
//                + "s.survey_type,s.created_date, s.status, s.remark, s.survey_rev_no, "
//                + "sp.meter_no, sp.meter_functional, "
//                + "sp.tube_well_survey_id, sp.r_phase, sp.y_phase, sp.b_phase, sp.power,"
//                + " sp.fuse_functional, sp.tube_well_survey_rev_no "
//                + "sp.starter_functional, sp.mccb_functional, p.pole_no, sd.pole_no_s, p.pole_rev_no, "
//                + "sp.timer_functional, sp.tube_well_survey_rev_no "
//                + "from survey s, tube_well_survey sp, pole p, tube_well_detail sd "
//                + "where s.tube_well_detail_id = sd.tube_well_detail_id "
//                + "And s.tube_well_rev_no = sd.tube_well_rev_no "
//                + "AND s.pole_id= p.pole_id "
//                + "AND s.pole_rev_no = p.pole_rev_no"
//                + "AND s.survey_id= sp.survey_id ";
   /*     String query1 = "select"
        + " s.survey_id, s.tube_well_detail_id, s.tube_well_rev_no, s.survey_file_no, s.survey_date,"
        + " s.survey_page_no, s.survey_by, s.pole_id, s.pole_rev_no, s.survey_type, s.created_date, s.status, s.remark, s.survey_rev_no,"
        + " '' as tube_well_survey_id, '' as survey_id, '' as meter_no,"
        + " '' as meter_functional, '' as r_phase, '' as y_phase, '' as b_phase,"
        + " '' as power, '' as fuse_functional, '' as starter_functional,"
        + " '' as mccb_functional, '' as timer_functional, '' as created_date,"
        + " '' as active, '' as remark, '' as tube_well_survey_rev_no, '' as created_by, '' as survey_rev_no"
        + " from survey s where tube_well_detail_id IS NULL";
        String query2 = " SELECT"
        + " s.survey_id,"
        + " s.tube_well_detail_id, s.tube_well_rev_no, s.survey_file_no, s.survey_date, s.survey_page_no, s.survey_by, s.pole_id,"
        + " s.pole_rev_no, s.survey_type, s.created_date, s.status, s.remark, s.survey_rev_no,"
        + " sps.tube_well_survey_id, sps.survey_id, sps.meter_no,"
        + " sps.meter_functional, sps.r_phase, sps.y_phase, sps.b_phase,"
        + " sps.power, sps.fuse_functional, sps.starter_functional,"
        + " sps.mccb_functional, sps.timer_functional, sps.created_date,"
        + " sps.active, sps.remark, sps.tube_well_survey_rev_no, sps.created_by, sps.survey_rev_no"
        + " FROM survey s , tube_well_survey sps where s.tube_well_detail_id IS NOT NULL"
        + " And s.survey_id = sps.survey_id AND s.survey_rev_no = sps.survey_rev_no";  */  //s.survey_meter_no,
        query = "SELECT f.feeder_name,top.type_of_premsis,t.meter_name_auto,s.general_image_details_id,s.image_name,switching_point_survey_id,s.survey_id,t.switching_point_survey_rev_no,s.survey_rev_no, "
                + " meter_no,t.created_by,t.active, meter_functional,r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, "
                + " mccb_functional,   switching_point_survey_rev_no, reason_id, no_of_phase, fuse1, fuse2, "
                + " fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, "
                + " contacter_capacity, if(t.contacter_id is null,null,sr.contacter_type) as contacter_type ,meter_phase, "
                + " meter_reading, if(t.contacter_make_id is null,null,sm.contacter_make) as contacter_make, "
                + " auto_switch_type_id, main_switch_type_id, main_switch_rating, enclosure_type_id ,survey_file_no, "
                + " DATE_FORMAT(survey_date, '%d-%m-%Y') AS survey_date, survey_page_no, mobile_no, pole_no, pole_rev_no, survey_type, status, image_name, "
                + " s.lattitude, s.longitude, image_date_time,  data_entry_type_id, video_name, survey_pole_no, "
                + " t.meter_name_auto, t.service_conn_no, t.previous_reading, t.consume_unit, t.amount "
                + " FROM switching_point_survey as t "
                +" LEFT JOIN(contacter as sr,contacter_make as sm) ON sr.contacter_id=t.contacter_id AND sm.contacter_make_id=t.contacter_make_id "
                +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
                +" left join feeder as f ON f.feeder_id=m.feeder_id "
                +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
                +" AND top.type_of_premises_id=ptm.type_of_premises_id "
                + " ,survey as s "//,contacter as sr,contacter_make as sm "
                + " where t.switching_point_survey_id=s.survey_id  "
                //+ " and sr.contacter_id=t.contacter_id "
                //+ " and sm.contacter_make_id=t.contacter_make_id
                + " and t.active='Y' and s.status='Y' "
                + " AND IF('" + pole_no + "' = '',  s.pole_no LIKE '%%', s.pole_no=? )"
                + " AND IF('" + searchFileNo + "' = '',  s.survey_file_no LIKE '%%', s.survey_file_no=? )"
                + " AND IF('" + searchPageNo + "' = '',  s.survey_page_no LIKE '%%', s.survey_page_no=? )"
                + " AND IF('" + searchIvrsNo + "' = '',  t.service_conn_no LIKE '%%' OR t.service_conn_no IS null, t.service_conn_no=? )"
                + " AND IF('" + searchByDate + "' = '',  s.survey_date LIKE '%%', s.survey_date >= '"+searchByDate+"' )"
                //+ " AND IF('" + meter_name_auto + "' = '' OR '" + meter_name_auto + "' = 'null',  t.meter_name_auto LIKE '%%', t.meter_name_auto = '"+meter_name_auto+"' )"
                + " AND IF('" + meter_name_auto + "' = '',  t.meter_name_auto LIKE '%%' OR t.meter_name_auto IS null, t.meter_name_auto = '"+meter_name_auto+"' )"
                + " AND IF('" + survey_id+ "' = '',  s.survey_id LIKE '%%', s.survey_id = '"+survey_id+"' )"
                + " AND IF('" + searchMeterFunctional+ "' = '',  t.meter_functional LIKE '%%', t.meter_functional = '"+searchMeterFunctional+"' )"
                + " AND IF('" + searchFeeder + "' = '',  f.feeder_name LIKE '%%', f.feeder_name= '"+searchFeeder+"' )"
                 + " AND IF('" + searchTypeOfConnection + "' = '',  top.type_of_premsis LIKE '%%', top.type_of_premsis= '"+searchTypeOfConnection+"' )"
                 + " AND IF('" + searchToDate + "' = '',  s.survey_date LIKE '%%', s.survey_date <= '"+searchToDate+"' )"
                + "  order by switching_point_survey_id "
                + addLimit;


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_no);
            pstmt.setString(2, searchFileNo);
            pstmt.setString(3, searchPageNo);
            pstmt.setString(4, searchIvrsNo);
            
//            pstmt.setString(3, pole_no);
//            pstmt.setString(4, pole_no);
            //  pstmt.setString(2, searchSwitchNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                if(countnum==1){
                SwitchingPointSurveyBean surveyType=new SwitchingPointSurveyBean();
                
                
                //surveyType.setSwitching_point_name(rset.getString("switching_point_name"));
                
                surveyType.setMeter_no_s(rset.getString("meter_no"));
                
               
                //surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setCreated_by(rset.getString("created_by"));
                //surveyType.setRemark(rset.getString("remark"));
                //      surveyType.setMounting_type(rset.getString("mounting_type"));
                surveyType.setActive(rset.getString("active"));
                surveyType.setMeter_name_auto(rset.getString("meter_name_auto"));
                surveyType.setPole_no_s(rset.getString("survey_pole_no"));
                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse2(rset.getString("fuse2"));
                surveyType.setFuse3(rset.getString("fuse3"));
                surveyType.setMccb1(rset.getString("mccb1"));
                surveyType.setMccb2(rset.getString("mccb2"));
                surveyType.setMccb3(rset.getString("mccb3"));
                surveyType.setFuse_type(getFuseTYpe(rset.getInt("fuse_id1")));
                surveyType.setFuse_type1(getFuseTYpe(rset.getInt("fuse_id2")));
                surveyType.setFuse_type2(getFuseTYpe(rset.getInt("fuse_id3")));
                surveyType.setMccb_type(getMccbType(rset.getInt("mccb_id1")));
                surveyType.setMccb_type1(getMccbType(rset.getInt("mccb_id2")));
                surveyType.setMccb_type2(getMccbType(rset.getInt("mccb_id3")));
                //surveyType.setFuse_quantity(rset.getString("fuse_quantity"));
                surveyType.setContacter_capacity(rset.getString("contacter_capacity"));
                surveyType.setContacter_type(rset.getString("contacter_type"));
                surveyType.setContacter_make(rset.getString("contacter_make"));
                surveyType.setAuto_switch_type(getSwitchType(rset.getInt("auto_switch_type_id")));
                surveyType.setMain_switch_type(getSwitchType(rset.getInt("main_switch_type_id")));
                surveyType.setEnclosure_type(getEnclosureType(rset.getInt("enclosure_type_id")));
                surveyType.setMain_switch_reading(rset.getString("main_switch_rating"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setIvrs_no(rset.getString("service_conn_no"));

                list.add(surveyType);
                }

                    else
                {
                TubeWellSurveyBean surveyType = new TubeWellSurveyBean();
                //surveyType.setSurvey_meter_no(rset.getString("survey_meter_no"));
                surveyType.setImage_name("image_name");
                surveyType.setGeneral_image_detials_id(rset.getInt("general_image_details_id"));
                surveyType.setSurvey_id(rset.getInt("survey_id"));
                surveyType.setTube_well_survey_id(rset.getInt("switching_point_survey_id"));
                surveyType.setTube_well_survey_rev_no(rset.getInt("switching_point_survey_rev_no"));
                surveyType.setSurvey_file_no(rset.getString("survey_file_no"));
                surveyType.setSurvey_date(rset.getString("survey_date"));
                surveyType.setSurvey_page_no(rset.getString("survey_page_no"));
                surveyType.setSurvey_by(rset.getString("mobile_no"));
                surveyType.setPole_no(rset.getString("pole_no"));
                surveyType.setPole_rev_no(rset.getInt("pole_rev_no"));
                surveyType.setSurvey_type(rset.getString("survey_type"));
                // surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setStatus(rset.getString("status"));
                //   surveyType.setRemark(rset.getString("remark"));
                surveyType.setSurvey_rev_no(rset.getInt("survey_rev_no"));

                //surveyType.setTube_well_survey_id(rset.getInt("tube_well_survey_id"));
                //  surveyType.setSurvey_id(rset.getInt("survey_id"));
                surveyType.setMeter_no(rset.getString("meter_no"));
                surveyType.setMeter_functional(rset.getString("meter_functional"));
                surveyType.setR_phase(rset.getString("r_phase"));
                surveyType.setY_phase(rset.getString("y_phase"));
                surveyType.setB_phase(rset.getString("b_phase"));
                surveyType.setPower(rset.getString("power"));
                surveyType.setFuse_functional(rset.getString("fuse_functional"));
                surveyType.setStarter_functional(rset.getString("contacter_functional"));
                surveyType.setMccb_functional(rset.getString("mccb_functional"));
                /// surveyType.setTimer_functional(rset.getString("timer_functional"));
                // surveyType.setCreated_date(rset.getString("created_date"));
                //surveyType.setActive(rset.getString("active"));
                surveyType.setTube_well_survey_rev_no(rset.getInt("switching_point_survey_rev_no"));
                //    surveyType.setCreated_by(rset.getString("created_by"));

                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse2(rset.getString("fuse2"));
                surveyType.setFuse3(rset.getString("fuse3"));
                surveyType.setMccb1(rset.getString("mccb1"));
                surveyType.setMccb2(rset.getString("mccb2"));
                surveyType.setMccb3(rset.getString("mccb3"));
                surveyType.setFuse_type1(getFuseTYpe(rset.getInt("fuse_id1")));
                surveyType.setFuse_type2(getFuseTYpe(rset.getInt("fuse_id2")));
                surveyType.setFuse_type3(getFuseTYpe(rset.getInt("fuse_id3")));
                surveyType.setMccb_type1(getMccbType(rset.getInt("mccb_id1")));
                surveyType.setMccb_type2(getMccbType(rset.getInt("mccb_id2")));
                surveyType.setMccb_type3(getMccbType(rset.getInt("mccb_id3")));
                surveyType.setStarter_capacity(rset.getString("contacter_capacity"));
                surveyType.setStarter_type(rset.getString("contacter_type"));
                surveyType.setStarter_make(rset.getString("contacter_make"));
                surveyType.setLatitude(rset.getString("lattitude"));
                surveyType.setLongitude(rset.getString("longitude"));
                surveyType.setImage_name(rset.getString("image_name"));
                surveyType.setSurvey_pole_no(rset.getString("survey_pole_no"));
                surveyType.setAuto_switch_type(getSwitchType(rset.getInt("auto_switch_type_id")));
                surveyType.setMain_switch_type(getSwitchType(rset.getInt("main_switch_type_id")));
                surveyType.setEnclosure_type(getEnclosureType(rset.getInt("enclosure_type_id")));
                surveyType.setMain_switch_rating(rset.getString("main_switch_rating"));
                surveyType.setMeter_reading(rset.getString("meter_reading"));
                surveyType.setMeter_phase(rset.getInt("meter_phase"));
                surveyType.setService_conn_no(rset.getString("service_conn_no"));
                surveyType.setMeter_name_auto(rset.getString("meter_name_auto"));
                surveyType.setPrevious_reading(rset.getDouble("previous_reading"));
                surveyType.setConsume_unit(rset.getDouble("consume_unit"));
                surveyType.setAmount(rset.getDouble("amount"));
                surveyType.setMeter_name_auto(rset.getString("meter_name_auto"));

                double r_phase = rset.getDouble("r_phase");
                double y_phase = rset.getDouble("y_phase");
                double b_phase = rset.getDouble("b_phase");
//                if(r_phase == null || r_phase.isEmpty())
//                    r_phase = "0";
//                if(y_phase == null || y_phase.isEmpty())
//                    y_phase = "0";
//                if(b_phase == null || b_phase.isEmpty())
//                    b_phase = "0";
//                double r = Double.parseDouble("r_phase");
//                double y = Double.parseDouble("y_phase");
//                double b = Double.parseDouble("b_phase");
                double calculated_power = ((r_phase + y_phase + b_phase) * 220) / 1000;
                double projected_consumption = calculated_power * 12 * 30;//10 is average daily consumption and 30 is days in month
                double c_p = Double.valueOf(new DecimalFormat("#.##").format(calculated_power));
                double p_c = Double.valueOf(new DecimalFormat("#.##").format(projected_consumption));
//                BigDecimal cp = new BigDecimal(calculated_power);
//                cp.setScale(1);
//                BigDecimal pc = new BigDecimal(projected_consumption);
//                pc.setScale(2);
                surveyType.setCalculated_power(c_p);
                surveyType.setProjected_consumption(p_c);
                

//                if (rset.getString("survey_type").equals("pole_type_survey")) {
//                    surveyType.setPole_no(getPole_No(rset.getInt("pole_id"), rset.getInt("pole_rev_no")));
//                } else {
//                    surveyType.setPole_no(getSwitchingTypePole_No(rset.getInt("tube_well_detail_id"), rset.getInt("tube_well_rev_no")));
//                }


//                surveyType.setPole_no(rset.getString("pole_no"));
//                surveyType.setSwitching_point_name(rset.getString("pole_no_s"));
//                surveyType.setActive(rset.getString("active"));
//                surveyType.setPole_no(rset.getString("pole_no"));
//                surveyType.setContacter_funactional(rset.getString("starter_functional"));
                list.add(surveyType);
                //}
                
            }
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return list;
    }

//    public JSONArray showJsonData()
//        {
//        JSONArray rowData = new JSONArray();
////        List list = new ArrayList();
////        int surveyId=0;
////        if(survey_id.isEmpty() || survey_id==null)
////        {
////            survey_id="";
////        }
//        String query = null;
////        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
////        if(lowerLimit == -1)
////            addLimit = "";
//        query = "SELECT f.feeder_name,top.type_of_premsis,t.meter_name_auto,s.general_image_details_id,s.image_name,switching_point_survey_id,s.survey_id,t.switching_point_survey_rev_no,s.survey_rev_no, "
//                + " meter_no,t.created_by,t.active, meter_functional,r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, "
//                + " mccb_functional,   switching_point_survey_rev_no, reason_id, no_of_phase, fuse1, fuse2, "
//                + " fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, "
//                + " contacter_capacity, if(t.contacter_id is null,null,sr.contacter_type) as contacter_type ,meter_phase, "
//                + " meter_reading, if(t.contacter_make_id is null,null,sm.contacter_make) as contacter_make, "
//                + " auto_switch_type_id, main_switch_type_id, main_switch_rating, enclosure_type_id ,survey_file_no, "
//                + " DATE_FORMAT(survey_date, '%d-%m-%Y') AS survey_date, survey_page_no, mobile_no, pole_no, pole_rev_no, survey_type, status, image_name, "
//                + " s.lattitude, s.longitude, image_date_time,  data_entry_type_id, video_name, survey_pole_no, "
//                + " t.meter_name_auto, t.service_conn_no, t.previous_reading, t.consume_unit, t.amount "
//                + " FROM switching_point_survey as t "
//                +" LEFT JOIN(contacter as sr,contacter_make as sm) ON sr.contacter_id=t.contacter_id AND sm.contacter_make_id=t.contacter_make_id "
//                +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
//                +" left join feeder as f ON f.feeder_id=m.feeder_id "
//                +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
//                +" AND top.type_of_premises_id=ptm.type_of_premises_id "
//                + " ,survey as s "//,contacter as sr,contacter_make as sm "
//                + " where t.switching_point_survey_id=s.survey_id  "
//                //+ " and sr.contacter_id=t.contacter_id "
//                //+ " and sm.contacter_make_id=t.contacter_make_id
//                + " and t.active='Y' and s.status='Y' "
////                + " AND IF('" + pole_no + "' = '',  s.pole_no LIKE '%%', s.pole_no=? )"
////                + " AND IF('" + searchFileNo + "' = '',  s.survey_file_no LIKE '%%', s.survey_file_no=? )"
////                + " AND IF('" + searchPageNo + "' = '',  s.survey_page_no LIKE '%%', s.survey_page_no=? )"
////                + " AND IF('" + searchIvrsNo + "' = '',  t.service_conn_no LIKE '%%' OR t.service_conn_no IS null, t.service_conn_no=? )"
////                + " AND IF('" + searchByDate + "' = '',  s.survey_date LIKE '%%', s.survey_date >= '"+searchByDate+"' )"
////                //+ " AND IF('" + meter_name_auto + "' = '' OR '" + meter_name_auto + "' = 'null',  t.meter_name_auto LIKE '%%', t.meter_name_auto = '"+meter_name_auto+"' )"
////                + " AND IF('" + meter_name_auto + "' = '',  t.meter_name_auto LIKE '%%' OR t.meter_name_auto IS null, t.meter_name_auto = '"+meter_name_auto+"' )"
////                + " AND IF('" + survey_id+ "' = '',  s.survey_id LIKE '%%', s.survey_id = '"+survey_id+"' )"
////                + " AND IF('" + searchMeterFunctional+ "' = '',  t.meter_functional LIKE '%%', t.meter_functional = '"+searchMeterFunctional+"' )"
////                + " AND IF('" + searchFeeder + "' = '',  f.feeder_name LIKE '%%', f.feeder_name= '"+searchFeeder+"' )"
////                 + " AND IF('" + searchTypeOfConnection + "' = '',  top.type_of_premsis LIKE '%%', top.type_of_premsis= '"+searchTypeOfConnection+"' )"
////                 + " AND IF('" + searchToDate + "' = '',  s.survey_date LIKE '%%', s.survey_date <= '"+searchToDate+"' )"
//                + "  order by switching_point_survey_id ";
//                //+ addLimit;
//
//
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
////            pstmt.setString(1, pole_no);
////            pstmt.setString(2, searchFileNo);
////            pstmt.setString(3, searchPageNo);
////            pstmt.setString(4, searchIvrsNo);
//
////            pstmt.setString(3, pole_no);
////            pstmt.setString(4, pole_no);
//            //  pstmt.setString(2, searchSwitchNo);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
////                TubeWellSurveyBean surveyType = new TubeWellSurveyBean();
////                //surveyType.setSurvey_meter_no(rset.getString("survey_meter_no"));
////                surveyType.setImage_name("image_name");
////                surveyType.setGeneral_image_detials_id(rset.getInt("general_image_details_id"));
////                surveyType.setSurvey_id(rset.getInt("survey_id"));
////                surveyType.setTube_well_survey_id(rset.getInt("switching_point_survey_id"));
////                surveyType.setTube_well_survey_rev_no(rset.getInt("switching_point_survey_rev_no"));
////                surveyType.setSurvey_file_no(rset.getString("survey_file_no"));
////                surveyType.setSurvey_date(rset.getString("survey_date"));
////                surveyType.setSurvey_page_no(rset.getString("survey_page_no"));
////                surveyType.setSurvey_by(rset.getString("mobile_no"));
////                surveyType.setPole_no(rset.getString("pole_no"));
////                surveyType.setPole_rev_no(rset.getInt("pole_rev_no"));
////                surveyType.setSurvey_type(rset.getString("survey_type"));
////                // surveyType.setCreated_date(rset.getString("created_date"));
////                surveyType.setStatus(rset.getString("status"));
////                //   surveyType.setRemark(rset.getString("remark"));
////                surveyType.setSurvey_rev_no(rset.getInt("survey_rev_no"));
////
////                //surveyType.setTube_well_survey_id(rset.getInt("tube_well_survey_id"));
////                //  surveyType.setSurvey_id(rset.getInt("survey_id"));
////                surveyType.setMeter_no(rset.getString("meter_no"));
////                surveyType.setMeter_functional(rset.getString("meter_functional"));
////                surveyType.setR_phase(rset.getString("r_phase"));
////                surveyType.setY_phase(rset.getString("y_phase"));
////                surveyType.setB_phase(rset.getString("b_phase"));
////                surveyType.setPower(rset.getString("power"));
////                surveyType.setFuse_functional(rset.getString("fuse_functional"));
////                surveyType.setStarter_functional(rset.getString("contacter_functional"));
////                surveyType.setMccb_functional(rset.getString("mccb_functional"));
////                /// surveyType.setTimer_functional(rset.getString("timer_functional"));
////                // surveyType.setCreated_date(rset.getString("created_date"));
////                //surveyType.setActive(rset.getString("active"));
////                surveyType.setTube_well_survey_rev_no(rset.getInt("switching_point_survey_rev_no"));
////                //    surveyType.setCreated_by(rset.getString("created_by"));
////
////                surveyType.setFuse1(rset.getString("fuse1"));
////                surveyType.setFuse2(rset.getString("fuse2"));
////                surveyType.setFuse3(rset.getString("fuse3"));
////                surveyType.setMccb1(rset.getString("mccb1"));
////                surveyType.setMccb2(rset.getString("mccb2"));
////                surveyType.setMccb3(rset.getString("mccb3"));
////                surveyType.setFuse_type1(getFuseTYpe(rset.getInt("fuse_id1")));
////                surveyType.setFuse_type2(getFuseTYpe(rset.getInt("fuse_id2")));
////                surveyType.setFuse_type3(getFuseTYpe(rset.getInt("fuse_id3")));
////                surveyType.setMccb_type1(getMccbType(rset.getInt("mccb_id1")));
////                surveyType.setMccb_type2(getMccbType(rset.getInt("mccb_id2")));
////                surveyType.setMccb_type3(getMccbType(rset.getInt("mccb_id3")));
////                surveyType.setStarter_capacity(rset.getString("contacter_capacity"));
////                surveyType.setStarter_type(rset.getString("contacter_type"));
////                surveyType.setStarter_make(rset.getString("contacter_make"));
////                surveyType.setLatitude(rset.getString("lattitude"));
////                surveyType.setLongitude(rset.getString("longitude"));
////                surveyType.setImage_name(rset.getString("image_name"));
////                surveyType.setSurvey_pole_no(rset.getString("survey_pole_no"));
////                surveyType.setAuto_switch_type(getSwitchType(rset.getInt("auto_switch_type_id")));
////                surveyType.setMain_switch_type(getSwitchType(rset.getInt("main_switch_type_id")));
////                surveyType.setEnclosure_type(getEnclosureType(rset.getInt("enclosure_type_id")));
////                surveyType.setMain_switch_rating(rset.getString("main_switch_rating"));
////                surveyType.setMeter_reading(rset.getString("meter_reading"));
////                surveyType.setMeter_phase(rset.getInt("meter_phase"));
////                surveyType.setService_conn_no(rset.getString("service_conn_no"));
////                surveyType.setMeter_name_auto(rset.getString("meter_name_auto"));
//
//                ////////////////////
//
//                JSONObject obj = new JSONObject();
////                    obj.put("initial_reading", rset.getString("current_reading"));
////                    obj.put("meter_address", rset.getString("address"));
////                    obj.put("service_number", rset.getString("ivrs_no"));
//
//                    obj.put("pole_no", rset.getString("pole_no"));
//                    obj.put("poll_no", rset.getString("survey_pole_no"));
//                    obj.put("file_no", rset.getString("survey_file_no"));
//                    obj.put("service_conn_no", rset.getString("service_conn_no"));
//
//                    obj.put("page_no", rset.getString("survey_page_no"));
//                    obj.put("survey_by", rset.getString("mobile_no"));
//                    obj.put("survey_type", rset.getString("survey_type"));
//                    obj.put("survey_date", rset.getString("survey_date"));
//                    obj.put("survey_remark", "");
//                    obj.put("meter_name_auto", rset.getString("meter_name_auto"));
//                    obj.put("lattitude", rset.getString("lattitude"));
//                    obj.put("longitude", rset.getString("longitude"));
//
//                    obj.put("img_name", rset.getString("image_name"));
//                    obj.put("vid_name", "");
//                    obj.put("survey_with_name", "");
//                    obj.put("survey_with", "");
//                    obj.put("meter_status", "");
//                    obj.put("meter_no", rset.getString("meter_no"));
//                    obj.put("phase", rset.getString("meter_phase"));
//                    obj.put("meter_reading", rset.getString("meter_reading"));
//                    obj.put("meter_functional", rset.getString("meter_functional"));
//                    obj.put("meter_address", "");
//                    obj.put("meter_remark", "");
//                    obj.put("fuse_status", rset.getString("fuse_functional"));
//                    obj.put("fuse1", rset.getString("fuse1"));
//                    obj.put("fuse1_type", getFuseTYpe(rset.getInt("fuse_id1")));
//                    obj.put("fuse2", rset.getString("fuse2"));
//                    obj.put("fuse2_type", getFuseTYpe(rset.getInt("fuse_id2")));
//                    obj.put("fuse3", rset.getString("fuse3"));
//                    obj.put("fuse3_type", getFuseTYpe(rset.getInt("fuse_id3")));
//                    obj.put("mccb_status", rset.getString("mccb_functional"));
//                    obj.put("mccb1", rset.getString("mccb1"));
//                    obj.put("mccb1_type", getMccbType(rset.getInt("mccb_id1")));
//                    obj.put("mccb2", rset.getString("mccb2"));
//                    obj.put("mccb2_type", getMccbType(rset.getInt("mccb_id2")));
//                    obj.put("mccb3", rset.getString("mccb3"));
//                    obj.put("mccb3_type", getMccbType(rset.getInt("mccb_id3")));
//                    obj.put("contacter_status", rset.getString("contacter_functional"));
//                    obj.put("contacter_type", rset.getString("contacter_type"));
//                    obj.put("contacter_capacity", rset.getString("contacter_capacity"));
//                    obj.put("contacter_make", rset.getString("contacter_make"));
//                    obj.put("b_phase", rset.getString("b_phase"));
//                    obj.put("r_phase", rset.getString("r_phase"));
//                    obj.put("y_phase", rset.getString("y_phase"));
//                    obj.put("power", rset.getString("power"));
//                    obj.put("auto_switch", getSwitchType(rset.getInt("auto_switch_type_id")));
//                    obj.put("main_switch", getSwitchType(rset.getInt("main_switch_type_id")));
//                    obj.put("main_switch_rating", rset.getString("main_switch_rating"));
//                    obj.put("enclosure", getEnclosureType(rset.getInt("enclosure_type_id")));
//                    obj.put("sp_remark", "");
//                    rowData.add(obj);
//
//
//                //list.add(surveyType);
//           }
//        } catch (Exception e) {
//            System.out.println("Error inside show data of survey: " + e);
//        }
//        TubeWellSurveyModel obj = new TubeWellSurveyModel();
//        obj.setConnection(connection);
//        obj.showJsonData(rowData);
//        return rowData;
//    }
//    //switching point data start
//        public JSONArray showSwicthingJsonData(){
//        JSONArray rowData = new JSONArray();
//
//        String query = null;
////        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
////        if(lowerLimit == -1)
////            addLimit = "";
//
//
//
//
//        String query2="select tube_well_detail_id,pole_no_s,area_id,meter_no_s,ph, "
//        +" fuse_id1,fuse_id2,fuse_id3,starter_type,mccb_id1,mccb_id2,mccb_id3,fuse_quantity, "
//        +" starter_capacity,mccb_quantity,tube_well_name,no_of_users,twd.longitude,twd.lattitude, "
//        +" twd.ivrs_no,measured_load,pole_id,type_of_premsis,fuse1,fuse2,fuse3,mccb1,mccb2,mccb3, "
//        +" starter_make,auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id, "
//        +" mccb,fuse,starter,twd.meter_id,r_phase,b_phase,y_phase,m.meter_name_auto, "
//        +" twd.created_date,is_working "
//        +" from tube_well_detail as twd "
//        +" LEFT JOIN(starter as sr) ON sr.starter_id=twd.starter_id "
//        +" LEFT JOIN(starter_make as sm) ON sm.starter_make_id=twd.starter_make_id "
//        +" Left Join(type_of_premises top) ON top.type_of_premises_id=twd.type_of_premises_id "
//        +" Left join(meters as m) ON twd.meter_id=m.meter_id and m.final_revision='VALID' "
//        +" where twd.active='Y' ";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query2);
//
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                JSONObject obj = new JSONObject();
//                    obj.put("tube_well_detail_id", rset.getString("tube_well_detail_id"));//
//                    obj.put("pole_no_s", rset.getString("pole_no_s"));//
//                    obj.put("area_id", rset.getString("area_id"));//
//                    obj.put("meter_no", rset.getString("meter_no_s"));
//                    obj.put("phase", rset.getString("ph"));//
//                    obj.put("fuse1_type", getFuseTYpe(rset.getInt("fuse_id1")));//
//                    obj.put("fuse2_type", getFuseTYpe(rset.getInt("fuse_id2")));//
//                    obj.put("fuse3_type", getFuseTYpe(rset.getInt("fuse_id3")));//
//                    obj.put("starter_type", rset.getString("starter_type"));//
//                    obj.put("mccb1_type", getMccbType(rset.getInt("mccb_id1")));//
//                    obj.put("mccb2_type", getMccbType(rset.getInt("mccb_id2")));//
//                    obj.put("mccb3_type", getMccbType(rset.getInt("mccb_id3")));//
//                    obj.put("fuse_quantity", getMccbType(rset.getInt("fuse_quantity")));//
//                    obj.put("starter_capacity", rset.getString("starter_capacity"));//
//                    obj.put("mccb_quantity", rset.getString("mccb_quantity"));//
//                    obj.put("tube_well_name", rset.getString("tube_well_name"));//
//                    obj.put("no_of_users", rset.getString("no_of_users"));//
//                    obj.put("lattitude", rset.getString("lattitude"));//
//                    obj.put("longitude", rset.getString("longitude"));//
//                    obj.put("ivrs_no", rset.getString("ivrs_no"));//
//                    obj.put("measured_load", rset.getString("measured_load"));//
//                    obj.put("pole_id", rset.getString("pole_id"));//
//                    obj.put("type_of_premsis", rset.getString("type_of_premsis"));//
//                    obj.put("fuse1", rset.getString("fuse1"));//
//                    obj.put("fuse2", rset.getString("fuse2"));//
//                    obj.put("fuse3", rset.getString("fuse3"));//
//                    obj.put("mccb1", rset.getString("mccb1"));//
//                    obj.put("mccb2", rset.getString("mccb2"));//
//                    obj.put("mccb3", rset.getString("mccb3"));//
//                    obj.put("starter_make", rset.getString("starter_make"));//
//                    obj.put("auto_switch", getSwitchType(rset.getInt("auto_switch_type_id")));//
//                    obj.put("main_switch", getSwitchType(rset.getInt("main_switch_type_id")));///////////////////////////////////
//                    obj.put("main_switch_rating", rset.getString("main_switch_rating"));//
//                    obj.put("enclosure", getEnclosureType(rset.getInt("enclosure_type_id")));////////////////////////////////
//                    obj.put("mccb_status", rset.getString("mccb"));//
//                    obj.put("fuse_status", rset.getString("fuse"));//
//                    obj.put("starter_status", rset.getString("starter"));//
//                    obj.put("meter_id", rset.getString("meter_id"));//
//                    obj.put("b_phase", rset.getString("b_phase"));//
//                    obj.put("r_phase", rset.getString("r_phase"));//
//                    obj.put("y_phase", rset.getString("y_phase"));//
//                    obj.put("survey_type", "tube_well");//
//                     obj.put("meter_status", "");
//                     //obj.put("starter_status", "Y");
//                     obj.put("meter_name_auto", rset.getString("meter_name_auto"));//
//                     obj.put("survey_date", rset.getString("created_date"));//
//                     obj.put("is_working", rset.getString("is_working"));//
//
//
////                    obj.put("pole_no", rset.getString("pole_no"));
////                    obj.put("poll_no", rset.getString("survey_pole_no"));
////                    obj.put("file_no", rset.getString("survey_file_no"));
////                    obj.put("service_conn_no", rset.getString("service_conn_no"));
////                    obj.put("page_no", rset.getString("survey_page_no"));
////                    obj.put("survey_by", rset.getString("mobile_no"));
////                    obj.put("survey_type", rset.getString("survey_type"));
////                    obj.put("survey_date", rset.getString("survey_date"));
////                    obj.put("survey_remark", "");
////                    obj.put("meter_name_auto", rset.getString("meter_name_auto"));
////                    obj.put("lattitude", rset.getString("lattitude"));
////                    obj.put("longitude", rset.getString("longitude"));
////                    obj.put("img_name", rset.getString("image_name"));
////                    obj.put("vid_name", "");
////                    obj.put("survey_with_name", "");
////                    obj.put("survey_with", "");
////                    obj.put("meter_status", "");
////                    obj.put("meter_no", rset.getString("meter_no"));
////                    obj.put("phase", rset.getString("meter_phase"));
////                    obj.put("meter_reading", rset.getString("meter_reading"));
////                    obj.put("meter_functional", rset.getString("meter_functional"));
////                    obj.put("meter_address", "");
////                    obj.put("meter_remark", "");
////                    obj.put("fuse_status", rset.getString("fuse_functional"));
////                    obj.put("fuse1", rset.getString("fuse1"));
////                    obj.put("fuse1_type", getFuseTYpe(rset.getInt("fuse_id1")));
////                    obj.put("fuse2", rset.getString("fuse2"));
////                    obj.put("fuse2_type", getFuseTYpe(rset.getInt("fuse_id2")));
////                    obj.put("fuse3", rset.getString("fuse3"));
////                    obj.put("fuse3_type", getFuseTYpe(rset.getInt("fuse_id3")));
////                    obj.put("mccb_status", rset.getString("mccb_functional"));
////                    obj.put("mccb1", rset.getString("mccb1"));
////                    obj.put("mccb1_type", getMccbType(rset.getInt("mccb_id1")));
////                    obj.put("mccb2", rset.getString("mccb2"));
////                    obj.put("mccb2_type", getMccbType(rset.getInt("mccb_id2")));
////                    obj.put("mccb3", rset.getString("mccb3"));
////                    obj.put("mccb3_type", getMccbType(rset.getInt("mccb_id3")));
////                    obj.put("contacter_status", rset.getString("contacter_functional"));
////                    obj.put("contacter_type", rset.getString("contacter_type"));
////                    obj.put("contacter_capacity", rset.getString("contacter_capacity"));
////                    obj.put("contacter_make", rset.getString("contacter_make"));
////                    obj.put("b_phase", rset.getString("b_phase"));
////                    obj.put("r_phase", rset.getString("r_phase"));
////                    obj.put("y_phase", rset.getString("y_phase"));
////                    obj.put("power", rset.getString("power"));
////                    obj.put("auto_switch", getSwitchType(rset.getInt("auto_switch_type_id")));
////                    obj.put("main_switch", getSwitchType(rset.getInt("main_switch_type_id")));
////                    obj.put("main_switch_rating", rset.getString("main_switch_rating"));
////                    obj.put("enclosure", getEnclosureType(rset.getInt("enclosure_type_id")));
////                    obj.put("sp_remark", "");
//
//                    rowData.add(obj);
//
//
//                //list.add(surveyType);
//           }
//        } catch (Exception e) {
//            System.out.println("Error inside show data of survey: " + e);
//        }
//       // TubeWellSurveyModel obj = new TubeWellSurveyModel();
//       // obj.setConnection(connection);
//       // obj.showJsonData(rowData);
//        return rowData;
//    }
//    
    //switching point data start

    public JSONArray showSwitchingJsonData(){
        JSONArray rowData = new JSONArray();

        String query = null;
//        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//        if(lowerLimit == -1)
//            addLimit = "";




        String query2="select  switching_point_detail_id,"
                + "  pole_no_s,"
                + "  area_id,"
                + "  meter_no_s,"
                + "  ph, "
        +"  fuse_id1, fuse_id2,"
                + "  fuse_id3,"
                + "  contacter_type,"
                + "  mccb_id1,"
                + "  mccb_id2,"
                + "  mccb_id3,"
                + "  fuse_quantity, "
        +"  contacter_capacity,"
                + "  mccb_quantity,"
                + "  twd.longitude,"
                + "  twd.lattitude, "
        +" twd.ivrs_no,"
                + "  measured_load,"
                + " pole_id,type_of_premsis,"
                + "  fuse1,"
                + " fuse2,"
                + " fuse3,"
                + " mccb1,"
                + " mccb2,"
                + " mccb3, "
        +" auto_switch_type_id,"
                + " main_switch_type_id,"
                + " main_switch_rating,"
                + " enclosure_type_id, "
        +" mccb,"
                + " fuse,"
                + " contacter,"
                + " twd.meter_id,"
                + " m.meter_name_auto, "
        +" twd.created_date,"
                + " is_working "
        +" from switching_point_detail as twd "
        +" LEFT JOIN(contacter as sr) ON sr.contacter_id=twd.contacter_id "

        +" Left Join(type_of_premises top) ON top.type_of_premises_id=twd.type_of_premises_id "
        +" Left join(meters as m) ON twd.meter_id=m.meter_id and m.final_revision='VALID' "
        +" where twd.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query2);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                    obj.put("switching_point_detail_id", rset.getString("switching_point_detail_id") == null? "" : rset.getString("switching_point_detail_id"));//
                    obj.put("pole_no_s", rset.getString("pole_no_s") == null? "" : rset.getString("pole_no_s"));//
                    obj.put("area_id", rset.getString("area_id") == null? "" : rset.getString("area_id"));//
                    obj.put("meter_no", rset.getString("meter_no_s") == null? "" : rset.getString("meter_no_s"));
                    obj.put("phase", rset.getString("ph") == null? "" : rset.getString("ph"));//
                    obj.put("fuse1_type", getFuseTYpe(rset.getInt("fuse_id1")) == null? "" : getFuseTYpe(rset.getInt("fuse_id1")));//
                    obj.put("fuse2_type", getFuseTYpe(rset.getInt("fuse_id2")) == null? "" : getFuseTYpe(rset.getInt("fuse_id2")));//
                    obj.put("fuse3_type", getFuseTYpe(rset.getInt("fuse_id3")) == null? "" : getFuseTYpe(rset.getInt("fuse_id3")));//
                    obj.put("contacter_type", rset.getString("contacter_type") == null? "" : rset.getString("contacter_type") );//
                    obj.put("mccb1_type", getMccbType(rset.getInt("mccb_id1")) == null? "" : getMccbType(rset.getInt("mccb_id1")));//
                    obj.put("mccb2_type", getMccbType(rset.getInt("mccb_id2")) == null? "" : getMccbType(rset.getInt("mccb_id2")));//
                    obj.put("mccb3_type", getMccbType(rset.getInt("mccb_id3")) == null? "" : getMccbType(rset.getInt("mccb_id3")));//
                    obj.put("fuse_quantity", getMccbType(rset.getInt("fuse_quantity")) == null? "" : getMccbType(rset.getInt("fuse_quantity")));//
                    obj.put("contacter_capacity", rset.getString("contacter_capacity") == null? "" : rset.getString("contacter_capacity"));//
                    obj.put("mccb_quantity", rset.getString("mccb_quantity") == null? "" : rset.getString("mccb_quantity"));//
                    //obj.put("tube_well_name", rset.getString("tube_well_name"));//
                    //obj.put("no_of_users", rset.getString("no_of_users"));//
                    obj.put("lattitude", rset.getString("lattitude") == null? "" : rset.getString("lattitude"));//
                    obj.put("longitude", rset.getString("longitude") == null? "" :  rset.getString("longitude"));//
                    obj.put("ivrs_no", rset.getString("ivrs_no") == null? "" : rset.getString("ivrs_no") );//
                    obj.put("measured_load", rset.getString("measured_load") == null? "" : rset.getString("measured_load"));//
                    obj.put("pole_id", rset.getString("pole_id") == null? "" : rset.getString("pole_id"));//
                    obj.put("type_of_premsis", rset.getString("type_of_premsis") == null? "" : rset.getString("type_of_premsis"));//
                    obj.put("fuse1", rset.getString("fuse1") == null? "" : rset.getString("fuse1"));//
                    obj.put("fuse2", rset.getString("fuse2") == null? "" : rset.getString("fuse2"));//
                    obj.put("fuse3", rset.getString("fuse3") == null? "" : rset.getString("fuse3"));//
                    obj.put("mccb1", rset.getString("mccb1") == null? "" : rset.getString("mccb1"));//
                    obj.put("mccb2", rset.getString("mccb2") == null? "" : rset.getString("mccb2"));//
                    obj.put("mccb3", rset.getString("mccb3") == null? "" : rset.getString("mccb3"));//
                 
                    obj.put("auto_switch", getSwitchType(rset.getInt("auto_switch_type_id")) == null? "" : getSwitchType(rset.getInt("auto_switch_type_id")));//
                    obj.put("main_switch", getSwitchType(rset.getInt("main_switch_type_id")) == null? "" : getSwitchType(rset.getInt("main_switch_type_id")));///////////////////////////////////
                    obj.put("main_switch_rating", rset.getString("main_switch_rating") == null? "" : rset.getString("main_switch_rating"));//
                    obj.put("enclosure", getEnclosureType(rset.getInt("enclosure_type_id")) == null? "" : getEnclosureType(rset.getInt("enclosure_type_id")));////////////////////////////////
                    obj.put("mccb_status", rset.getString("mccb") == null? "" : rset.getString("mccb"));//
                    obj.put("fuse_status", rset.getString("fuse") == null? "" : rset.getString("fuse"));//
                   // obj.put("starter_status", rset.getString("starter"));//
//                    obj.put("meter_id", rset.getString("meter_id"));//
//                    obj.put("b_phase", rset.getString("b_phase"));//
//                    obj.put("r_phase", rset.getString("r_phase"));//
//                    obj.put("y_phase", rset.getString("y_phase"));//
                  obj.put("survey_type", "switching_point");//
                     obj.put("meter_status", "");
                     //obj.put("starter_status", "Y");
                     obj.put("meter_name_auto", rset.getString("meter_name_auto") == null? "" : rset.getString("meter_name_auto"));//
                     obj.put("survey_date", rset.getString("created_date") == null? "" : rset.getString("created_date"));//
                     obj.put("is_working", rset.getString("is_working") == null? "" : rset.getString("is_working"));//



                    rowData.put(obj);


                //list.add(surveyType);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
       // TubeWellSurveyModel obj = new TubeWellSurveyModel();
       // obj.setConnection(connection);
       // obj.showJsonData(rowData);
        return rowData;
    }

// send the switching point data table
    public JSONArray showJsonData(){
        JSONArray rowData = new JSONArray();

        String query = null;
//        String addLimit = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//        if(lowerLimit == -1)
//            addLimit = "";
//        query = "SELECT f.feeder_name,top.type_of_premsis,t.meter_name_auto,s.general_image_details_id,s.image_name,switching_point_survey_id,s.survey_id,t.switching_point_survey_rev_no,s.survey_rev_no, "
//                + " meter_no,t.created_by,t.active, meter_functional,r_phase, y_phase, b_phase, power, fuse_functional, contacter_functional, "
//                + " mccb_functional,   switching_point_survey_rev_no, reason_id, no_of_phase, fuse1, fuse2, "
//                + " fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, "
//                + " contacter_capacity, if(t.contacter_id is null,null,sr.contacter_type) as contacter_type ,meter_phase, "
//                + " meter_reading, if(t.contacter_make_id is null,null,sm.contacter_make) as contacter_make, "
//                + " auto_switch_type_id, main_switch_type_id, main_switch_rating, enclosure_type_id ,survey_file_no, "
//                + " DATE_FORMAT(survey_date, '%d-%m-%Y') AS survey_date, survey_page_no, mobile_no, pole_no, pole_rev_no, survey_type, status, image_name, "
//                + " s.lattitude, s.longitude, image_date_time,  data_entry_type_id, video_name, survey_pole_no, "
//                + " t.meter_name_auto, t.service_conn_no, t.previous_reading, t.consume_unit, t.amount "
//                + " FROM switching_point_survey as t "
//                +" LEFT JOIN(contacter as sr,contacter_make as sm) ON sr.contacter_id=t.contacter_id AND sm.contacter_make_id=t.contacter_make_id "
//                +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
//                +" left join feeder as f ON f.feeder_id=m.feeder_id "
//                +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
//                +" AND top.type_of_premises_id=ptm.type_of_premises_id "
//                + " ,survey as s "//,contacter as sr,contacter_make as sm "
//                + " where t.switching_point_survey_id=s.survey_id  "
//                //+ " and sr.contacter_id=t.contacter_id "
//                //+ " and sm.contacter_make_id=t.contacter_make_id
//                + " and t.active='Y' and s.status='Y' "
////                + " AND IF('" + pole_no + "' = '',  s.pole_no LIKE '%%', s.pole_no=? )"
////                + " AND IF('" + searchFileNo + "' = '',  s.survey_file_no LIKE '%%', s.survey_file_no=? )"
////                + " AND IF('" + searchPageNo + "' = '',  s.survey_page_no LIKE '%%', s.survey_page_no=? )"
////                + " AND IF('" + searchIvrsNo + "' = '',  t.service_conn_no LIKE '%%' OR t.service_conn_no IS null, t.service_conn_no=? )"
////                + " AND IF('" + searchByDate + "' = '',  s.survey_date LIKE '%%', s.survey_date >= '"+searchByDate+"' )"
////                //+ " AND IF('" + meter_name_auto + "' = '' OR '" + meter_name_auto + "' = 'null',  t.meter_name_auto LIKE '%%', t.meter_name_auto = '"+meter_name_auto+"' )"
////                + " AND IF('" + meter_name_auto + "' = '',  t.meter_name_auto LIKE '%%' OR t.meter_name_auto IS null, t.meter_name_auto = '"+meter_name_auto+"' )"
////                + " AND IF('" + survey_id+ "' = '',  s.survey_id LIKE '%%', s.survey_id = '"+survey_id+"' )"
////                + " AND IF('" + searchMeterFunctional+ "' = '',  t.meter_functional LIKE '%%', t.meter_functional = '"+searchMeterFunctional+"' )"
////                + " AND IF('" + searchFeeder + "' = '',  f.feeder_name LIKE '%%', f.feeder_name= '"+searchFeeder+"' )"
////                 + " AND IF('" + searchTypeOfConnection + "' = '',  top.type_of_premsis LIKE '%%', top.type_of_premsis= '"+searchTypeOfConnection+"' )"
////                 + " AND IF('" + searchToDate + "' = '',  s.survey_date LIKE '%%', s.survey_date <= '"+searchToDate+"' )"
//                + "  order by switching_point_survey_id ";
//                //+ addLimit;



        String query2="select tube_well_detail_id,pole_no_s,area_id,meter_no_s,ph, "
        +" fuse_id1,fuse_id2,fuse_id3,starter_type,mccb_id1,mccb_id2,mccb_id3,fuse_quantity, "
        +" starter_capacity,mccb_quantity,tube_well_name,no_of_users,twd.longitude,twd.lattitude, "
        +" twd.ivrs_no,measured_load,pole_id,type_of_premsis,fuse1,fuse2,fuse3,mccb1,mccb2,mccb3, "
        +" starter_make,auto_switch_type_id,main_switch_type_id,main_switch_rating,enclosure_type_id, "
        +" mccb,fuse,starter,twd.meter_id,r_phase,b_phase,y_phase,m.meter_name_auto, "
        +" twd.created_date,is_working "
        +" from tube_well_detail as twd "
        +" LEFT JOIN(starter as sr) ON sr.starter_id=twd.starter_id "
        +" LEFT JOIN(starter_make as sm) ON sm.starter_make_id=twd.starter_make_id "
        +" Left Join(type_of_premises top) ON top.type_of_premises_id=twd.type_of_premises_id "
        +" Left join(meters as m) ON twd.meter_id=m.meter_id and m.final_revision='VALID' "
        +" where twd.active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query2);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                    obj.put("tube_well_detail_id", rset.getString("tube_well_detail_id") == null? "" : rset.getString("tube_well_detail_id"));//
                    obj.put("pole_no_s", rset.getString("pole_no_s") == null? "" : rset.getString("pole_no_s"));//
                    obj.put("area_id", rset.getString("area_id") == null? "" : rset.getString("area_id"));//
                    obj.put("meter_no", rset.getString("meter_no_s") == null? "" : rset.getString("meter_no_s"));
                    obj.put("phase", rset.getString("ph") == null? "" : rset.getString("ph") );//
                    obj.put("fuse1_type", getFuseTYpe(rset.getInt("fuse_id1")) == null? "" : getFuseTYpe(rset.getInt("fuse_id1")));//
                    obj.put("fuse2_type", getFuseTYpe(rset.getInt("fuse_id2")) == null? "" : getFuseTYpe(rset.getInt("fuse_id2")));//
                    obj.put("fuse3_type", getFuseTYpe(rset.getInt("fuse_id3")) == null? "" : getFuseTYpe(rset.getInt("fuse_id3")));//
                    obj.put("starter_type", rset.getString("starter_type") == null? "" : rset.getString("starter_type"));//
                    obj.put("mccb1_type", getMccbType(rset.getInt("mccb_id1")) == null? "" : getMccbType(rset.getInt("mccb_id1")));//
                    obj.put("mccb2_type", getMccbType(rset.getInt("mccb_id2")) == null? "" : getMccbType(rset.getInt("mccb_id2")));//
                    obj.put("mccb3_type", getMccbType(rset.getInt("mccb_id3")) == null? "" : getMccbType(rset.getInt("mccb_id3")));//
                    obj.put("fuse_quantity", getMccbType(rset.getInt("fuse_quantity")) == null? "" : getMccbType(rset.getInt("fuse_quantity")));//
                    obj.put("starter_capacity", rset.getString("starter_capacity") == null? "" : rset.getString("starter_capacity"));//
                    obj.put("mccb_quantity", rset.getString("mccb_quantity") == null? "" : rset.getString("mccb_quantity"));//
                    obj.put("tube_well_name", rset.getString("tube_well_name") == null? "" : rset.getString("tube_well_name"));//
                    obj.put("no_of_users", rset.getString("no_of_users") == null? "" : rset.getString("no_of_users"));//
                    obj.put("lattitude", rset.getString("lattitude") == null? "" : rset.getString("lattitude"));//
                    obj.put("longitude", rset.getString("longitude") == null? "" : rset.getString("longitude"));//
                    obj.put("ivrs_no", rset.getString("ivrs_no") == null? "" : rset.getString("ivrs_no"));//
                    obj.put("measured_load", rset.getString("measured_load") == null? "" : rset.getString("measured_load"));//
                    obj.put("pole_id", rset.getString("pole_id") == null? "" : rset.getString("pole_id"));//
                    obj.put("type_of_premsis", rset.getString("type_of_premsis") == null? "" : rset.getString("type_of_premsis"));//
                    obj.put("fuse1", rset.getString("fuse1") == null? "" : rset.getString("fuse1"));//
                    obj.put("fuse2", rset.getString("fuse2") == null? "" : rset.getString("fuse2"));//
                    obj.put("fuse3", rset.getString("fuse3") == null? "" : rset.getString("fuse3"));//
                    obj.put("mccb1", rset.getString("mccb1") == null? "" : rset.getString("mccb1"));//
                    obj.put("mccb2", rset.getString("mccb2") == null? "" : rset.getString("mccb2"));//
                    obj.put("mccb3", rset.getString("mccb3") == null? "" : rset.getString("mccb3"));//
                    obj.put("starter_make", rset.getString("starter_make") == null? "" : rset.getString("starter_make"));//
                    obj.put("auto_switch", getSwitchType(rset.getInt("auto_switch_type_id")) == null? "" : getSwitchType(rset.getInt("auto_switch_type_id")));//
                    obj.put("main_switch", getSwitchType(rset.getInt("main_switch_type_id")) == null? "" : getSwitchType(rset.getInt("main_switch_type_id")));///////////////////////////////////
                    obj.put("main_switch_rating", rset.getString("main_switch_rating") == null? "" : rset.getString("main_switch_rating"));//
                    obj.put("enclosure", getEnclosureType(rset.getInt("enclosure_type_id")) == null? "" : getEnclosureType(rset.getInt("enclosure_type_id")));////////////////////////////////
                    obj.put("mccb_status", rset.getString("mccb") == null? "" : rset.getString("mccb"));//
                    obj.put("fuse_status", rset.getString("fuse") == null? "" : rset.getString("fuse"));//
                    obj.put("starter_status", rset.getString("starter") == null? "" : rset.getString("starter"));//
                    obj.put("meter_id", rset.getString("meter_id") == null? "" : rset.getString("meter_id"));//
                    obj.put("b_phase", rset.getString("b_phase") == null? "" : rset.getString("b_phase"));//
                    obj.put("r_phase", rset.getString("r_phase") == null? "" : rset.getString("r_phase"));//
                    obj.put("y_phase", rset.getString("y_phase") == null? "" : rset.getString("y_phase"));//
                    obj.put("survey_type", "tube_well");//
                     obj.put("meter_status", "");
                     //obj.put("starter_status", "Y");
                     obj.put("meter_name_auto", rset.getString("meter_name_auto") == null? "" : rset.getString("meter_name_auto"));//
                     obj.put("survey_date", rset.getString("created_date") == null? "" : rset.getString("created_date"));//
                     obj.put("is_working", rset.getString("is_working") == null? "" : rset.getString("is_working"));//




                    rowData.put(obj);


                //list.add(surveyType);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
      
        return rowData;
    }
    
    //send the switching point table end
    public List<TubeWellSurveyBean> showAllData(String pole_no, String searchMeterNo) {
        List<TubeWellSurveyBean> list = new ArrayList<TubeWellSurveyBean>();
        String query = null;


        query = "SELECT s.general_image_details_id,tube_well_survey_id,s.survey_id,t.revison_no,s.survey_rev_no,  meter_no, meter_functional,r_phase, y_phase, b_phase, power, fuse_functional, starter_functional,"
                + " mccb_functional,   tube_well_survey_rev_no, reason_id, no_of_phase, fuse1, fuse2, fuse3,t.fuse_id1, t.fuse_id2, t.fuse_id3, "
                + "mccb1, mccb2, mccb3,t.mccb_id1, t.mccb_id2, t.mccb_id3, starter_capacity, if(t.starter_id is null,null,sr.starter_type) as starter_type , "
                + "meter_phase, meter_reading,if(t.starter_make_id is null,null,sm.starter_make) as starter_make, auto_switch_type_id, main_switch_type_id, "
                + "main_switch_rating, enclosure_type_id ,survey_file_no, survey_date, survey_page_no, mobile_no, pole_no, pole_rev_no, survey_type, status,"
                + "  image_name, lattitude, longitude, image_date_time,  data_entry_type_id, video_name, survey_pole_no  FROM tube_well_survey as t ,"
                + "survey as s,starter as sr,fuse as f,starter_make as sm,switch_type as st,  mccb as m where t.tube_well_survey_id=s.survey_id  and"
                + "  sr.starter_id=t.starter_id and sm.starter_make_id=t.starter_make_id and t.active='Y' and s.status='Y'  "
                + " AND IF('" + pole_no + "' = '',  s.pole_no LIKE '%%', s.pole_no=? )"
                + " AND IF('" + searchMeterNo + "' = '',  t.meter_no LIKE '%%', t.meter_no=? )"
                + " group by tube_well_survey_id order by tube_well_survey_id";
        //          + " AND IF('" + pole_no + "' = '', s.survey_id LIKE '%%', p.pole_no=? ) "


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_no);
            pstmt.setString(2, searchMeterNo);
//            pstmt.setString(3, pole_no);
//            pstmt.setString(4, pole_no);
            //  pstmt.setString(2, searchSwitchNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TubeWellSurveyBean surveyType = new TubeWellSurveyBean();
                surveyType.setGeneral_image_detials_id(rset.getInt("general_image_details_id"));
                surveyType.setSurvey_id(rset.getInt("survey_id"));
                surveyType.setTube_well_survey_id(rset.getInt("tube_well_survey_id"));
                surveyType.setTube_well_survey_rev_no(rset.getInt("tube_well_survey_rev_no"));
                surveyType.setSurvey_file_no(rset.getString("survey_file_no"));
                surveyType.setSurvey_date(rset.getString("survey_date"));
                surveyType.setSurvey_page_no(rset.getString("survey_page_no"));
                surveyType.setSurvey_by(rset.getString("mobile_no"));
                surveyType.setPole_no(rset.getString("pole_no"));
                surveyType.setPole_rev_no(rset.getInt("pole_rev_no"));
                surveyType.setSurvey_type(rset.getString("survey_type"));
                // surveyType.setCreated_date(rset.getString("created_date"));
                surveyType.setStatus(rset.getString("status"));
                //   surveyType.setRemark(rset.getString("remark"));
                surveyType.setSurvey_rev_no(rset.getInt("survey_rev_no"));

                //surveyType.setTube_well_survey_id(rset.getInt("tube_well_survey_id"));
                //  surveyType.setSurvey_id(rset.getInt("survey_id"));
                surveyType.setMeter_no(rset.getString("meter_no"));
                surveyType.setMeter_functional(rset.getString("meter_functional"));
                surveyType.setR_phase(rset.getString("r_phase"));
                surveyType.setY_phase(rset.getString("y_phase"));
                surveyType.setB_phase(rset.getString("b_phase"));
                surveyType.setPower(rset.getString("power"));
                surveyType.setFuse_functional(rset.getString("fuse_functional"));
                surveyType.setStarter_functional(rset.getString("starter_functional"));
                surveyType.setMccb_functional(rset.getString("mccb_functional"));
                /// surveyType.setTimer_functional(rset.getString("timer_functional"));
                // surveyType.setCreated_date(rset.getString("created_date"));
                //surveyType.setActive(rset.getString("active"));
                surveyType.setTube_well_survey_rev_no(rset.getInt("tube_well_survey_rev_no"));
                //    surveyType.setCreated_by(rset.getString("created_by"));

                surveyType.setFuse1(rset.getString("fuse1"));
                surveyType.setFuse2(rset.getString("fuse2"));
                surveyType.setFuse3(rset.getString("fuse3"));
                surveyType.setMccb1(rset.getString("mccb1"));
                surveyType.setMccb2(rset.getString("mccb2"));
                surveyType.setMccb3(rset.getString("mccb3"));
                surveyType.setFuse_type1(getFuseTYpe(rset.getInt("fuse_id1")));
                surveyType.setFuse_type2(getFuseTYpe(rset.getInt("fuse_id2")));
                surveyType.setFuse_type3(getFuseTYpe(rset.getInt("fuse_id3")));
                surveyType.setMccb_type1(getMccbType(rset.getInt("mccb_id1")));
                surveyType.setMccb_type2(getMccbType(rset.getInt("mccb_id2")));
                surveyType.setMccb_type3(getMccbType(rset.getInt("mccb_id3")));
                surveyType.setStarter_capacity(rset.getString("starter_capacity"));
                surveyType.setStarter_type(rset.getString("starter_type"));
                surveyType.setStarter_make(rset.getString("starter_make"));
                surveyType.setLatitude(rset.getString("lattitude"));
                surveyType.setLongitude(rset.getString("longitude"));
                surveyType.setImage_name(rset.getString("image_name"));
                surveyType.setSurvey_pole_no(rset.getString("survey_pole_no"));
                surveyType.setAuto_switch_type(getSwitchType(rset.getInt("auto_switch_type_id")));
                surveyType.setMain_switch_type(getSwitchType(rset.getInt("main_switch_type_id")));
                surveyType.setEnclosure_type(getEnclosureType(rset.getInt("enclosure_type_id")));
                surveyType.setMain_switch_rating(rset.getString("main_switch_rating"));
                surveyType.setMeter_reading(rset.getString("meter_reading"));
                surveyType.setMeter_phase(rset.getInt("meter_phase"));


                list.add(surveyType);
            }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return list;
    }

    public String getdestinationPath(int general_image_details_id, String image_name) {
        String query;
        String destination = "";
        query = " SELECT id.destination_path from image_destination AS id , general_image_details as g"
                + " WHERE id.image_destination_id=g.image_destination_id AND g.general_image_details_id= '" + general_image_details_id + "' and "
                + "g.image_name='" + image_name + "'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                destination = rset.getString("destination_path");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return destination;
    }

    public int getEnclosureTypeId(String enclosure_type) {

        int enclosure_type_id = 0;
        String query = " SELECT enclosure_type_id FROM enclosure_type WHERE  enclosure_type='" + enclosure_type + "'"
                + "group by enclosure_type_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                enclosure_type_id = rset.getInt("enclosure_type_id");
            } else {
                enclosure_type_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return enclosure_type_id;
    }

    public int getStarterMakeId(String starter_make) {

        int starter_make_id = 0;
        String query = " SELECT contacter_make_id FROM contacter_make WHERE contacter_make='" + starter_make + "'  "
                + "group by contacter_make_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                starter_make_id = rset.getInt("contacter_make_id");
            } else {
                starter_make_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getStarterMakeId() Error: " + e);
        }
        return starter_make_id;
    }

    public int getSusrvey(String pole_no) {

        int starter_make_id = 0;
        String query = " SELECT survey_id FROM survey WHERE status='Y' and pole_no='" + pole_no + "' ";

        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                starter_make_id = rset.getInt("survey_id");
            } else {
                starter_make_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return starter_make_id;
    }

    public int getSusrveyRevNo(String pole_no) {

        int starter_make_id = 0;
        String query = " SELECT survey_rev_no FROM survey WHERE status='Y' and pole_no='" + pole_no + "'  ";

        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                starter_make_id = rset.getInt("survey_rev_no");
            } else {
                starter_make_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
        }
        return starter_make_id;
    }
// public String getFuseType(int fuse_id) {
//
//        String fuse_type="";
//        String query = " SELECT fuse_type FROM fuse WHERE fuse_id='" + fuse_id + "'  "
//                + "group by starter_make_id";
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            if (rset.next()) {
//                fuse_type = rset.getString("fuse_type");
//            }
//        } catch (Exception e) {
//            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
//        }
//        return fuse_type;
//    }
//  public String MccbType(int mccb_id) {
//
//        String mccb_type="";
//        String query = " SELECT fuse_type FROM fuse WHERE fuse_id='" + mccb_id + "'  "
//                + "group by starter_make_id";
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            if (rset.next()) {
//                mccb_type = rset.getString("fuse_type");
//            }
//        } catch (Exception e) {
//            System.out.println("SurveyModel getEnclosureTypeId() Error: " + e);
//        }
//        return mccb_type;
//    }

    public List<String> getSwitchingPtName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT switching_point_name FROM tube_well_detail GROUP BY switching_point_name ORDER BY switching_point_name";
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

    public List<String> getFuseType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT fuse_type FROM fuse GROUP BY fuse_type ORDER BY fuse_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String fuse_type = rset.getString("fuse_type");
                if (fuse_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(fuse_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getContacterType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT contacter_type FROM contacter GROUP BY contacter_type ORDER BY contacter_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String starter_type = rset.getString("contacter_type");
                if (starter_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(starter_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Contacter Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getContacterType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getMccbType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT mccb_type FROM mccb GROUP BY mccb_type ORDER BY mccb_type ";
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
                list.add("No such Mccb Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getMccbType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public int getSwitchId(String switch_type) {

        int switch_type_id = 0;
        String query = " SELECT st.switch_type_id FROM switch_type as st,switch as s WHERE  "
                + "s.switch_id=st.switch_id and st.switch_type='" + switch_type + "' group by switch_type_id";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                switch_type_id = rset.getInt("switch_type_id");
            } else {
                switch_type_id = 0;
            }
        } catch (Exception e) {
            System.out.println("SurveyModel getSwitchId() Error: " + e);
        }
        return switch_type_id;
    }

    public List<String> getStarterMake(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT contacter_make FROM contacter_make GROUP BY contacter_make ORDER BY contacter_make ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String starter_make = rset.getString("contacter_make");
                if (starter_make.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(starter_make);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Contacter Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getContacterType ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<String> getIvrsNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT ivrs_no FROM meters  as m where final_revision='VALID' "
                + "GROUP BY ivrs_no ORDER BY ivrs_no ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ivrs_no = rset.getString("ivrs_no");
                if (ivrs_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ivrs_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Contacter Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getIvrsNo ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public String getMeterNumber(String ivrs_no) {
        //   List<String> list = new ArrayList<String>();
        String meter_no = "";
        String query = " SELECT meter_name FROM meters where ivrs_no='" + ivrs_no + "' GROUP BY meter_name ORDER BY meter_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                meter_no = rset.getString("meter_name");
                // list.add(meter_no);
            }

        } catch (Exception e) {
            System.out.println("getContacterType ERROR inside SurveyModel - " + e);
        }
        return meter_no;
    }
     public void SavePdf(String jrxmlFilePath, List list, String reportName) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            //JRXlsExporter exporter = new JRXlsExporter();
            String path = createAppropriateDirectories1("C:/ssadvt_repository/meter_survey/temp_pdf");
            path = path + "/" +reportName;
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("JRException: " + e);
        }
        //return reportInbytes;
    }

     public String createAppropriateDirectories1(String destination_path) {
        // destination_path e.g. "C:/ssadvt_repository/SSADVT/MPWZ/PAYMENT REQUISITIONS/Aug-2014"
        String destPath[] = destination_path.split("/");
        String path = "";
        int length = destPath.length;
        for(int i = 0; i < length; i++)
        {
            if(path.isEmpty())
                path = destPath[i];
            else
                path = path + "/" + destPath[i];
            makeDirectory(path);
        }
        return path;
    }

    public boolean makeDirectory(String dirPathName)
    {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdir();
        }
        return result;
    }


      /* public String imageToPdfConvert(String imagePath,int num) {
        String image_path = "";
        Document document = new Document();
        String filepath="C:/ssadvt_repository/meter_survey/temp_img";
        createAppropriateDirectories1(filepath);
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(filepath + "/Image"+num+".pdf"));
            document.open();

            Image image1 = Image.getInstance(imagePath);
            //image1.setAbsolutePosition(0f, 10f);
            image1.scalePercent(15);
            document.add(image1);
            image_path = filepath + "/Image"+num+".pdf";
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image_path;
    }   */
    //select ii.image_uploaded_for_name from image_uploaded_for as ii left join image_destination as id ON ii.image_uploaded_for_id=id.image_uploaded_for_id where image_uploaded_for_name='Survey Image_Pdf';

    public String imageToPdfConvert(String imagePath,int num) {
       String image_path = "";
       String dateTime = "";
       String[] pathArray = imagePath.split("/");
       if(!pathArray[pathArray.length - 1].equals("no_image.png"))
        {
       String imageName = pathArray[pathArray.length - 1];
       String imageDate = imageName.split("_")[1];
       String imageTime = imageName.split("_")[2];
       
       int dateLength = imageDate.length();
       int timeLength = imageTime.length();
       for(int i = 0; i < dateLength; i++){
           if(i <= 3){
               dateTime = dateTime + imageDate.charAt(i);
               if(i == 3)
                   dateTime = dateTime + "-";
           }
           else if(i <= 5){
               dateTime = dateTime + imageDate.charAt(i);
               if(i == 5)
                   dateTime = dateTime + "-";
           }
           else
               dateTime = dateTime + imageDate.charAt(i);
       }
       dateTime = dateTime + "/";
       for(int i = 0; i < timeLength; i++){
           if(i <= 1){
               dateTime = dateTime + imageTime.charAt(i);
               if(i == 1)
                   dateTime = dateTime + ":";
           }
           else if(i <= 3){
               dateTime = dateTime + imageTime.charAt(i);
               if(i == 3)
                   dateTime = dateTime + ":";
           }
           else if(i <= 5){
               dateTime = dateTime + imageTime.charAt(i);
               if(i == 5)
                   break;
           }
       }
       //Document document = new Document();
        }
            else {
                    
                }
       String filepath="C:/ssadvt_repository/meter_survey/temp_img";
       createAppropriateDirectories1(filepath);
       try {
           //Rectangle pageSize = new Rectangle(width, height);
           Document document = new Document();
           PdfWriter writer = PdfWriter.getInstance(document,
                   new FileOutputStream(filepath + "/Image"+num+".pdf"));
           writer.open();
           document.open();

           BufferedImage bi = ImageIO.read(new File(imagePath));
           Graphics2D graphics = bi.createGraphics();
           Font font = new Font("ARIAL", Font.BOLD, 50);
           graphics.setFont(font);

           graphics.drawString(dateTime, 50, 50);
           bi.flush();
           ImageIO.write(bi, "jpg", new File(filepath + "/" + "test.jpg"));

           Image image1 = Image.getInstance(filepath + "/" + "test.jpg");
           //image1.setAbsolutePosition(0f, 100f);
           if(!pathArray[pathArray.length - 1].equals("no_image.png"))
              image1.scalePercent(15,15);
           else
           image1.scalePercent(100,100);
           document.add(image1);
           image_path = filepath + "/Image"+num+".pdf";
           document.close();
           writer.close();
       } catch (Exception e)
       {
           e.printStackTrace();
       }

       return image_path;
   }
    public int insertGeneral_Image_Details(String pdfFileName,String path)
       {    String meter_name_auto=pdfFileName.split("_")[0];
           int rowEffect=0;
        String query = "insert into general_image_details (image_name, image_destination_id, date_time, description, active) "
                + " values (?,"
                + " ( SELECT i.image_destination_id  FROM image_destination i left join image_uploaded_for as iuf "
                + " ON iuf.image_uploaded_for_id=i.image_uploaded_for_id where image_uploaded_for_name='"+path+"' ), "
                + " ?,?,? ) ";
        String queryUpdate="update meters set "
                + " general_img_details_id=(select max(general_image_details_id) from general_image_details where image_name='"+pdfFileName+"' )"
                + " where meter_name_auto='"+meter_name_auto+"' AND active='Y'";
        try {
             PreparedStatement ps =connection.prepareStatement(query);
              
              ps.setString(1, pdfFileName);
              //ps.setInt(2, 0);
               DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
                Date date = new Date();
                String current_date = dateFormat.format(date);
              ps.setString(2, current_date);
              ps.setString(3, "this image is for survey");
              ps.setString(4, "Y");
              rowEffect = ps.executeUpdate();
              if(rowEffect>0)
              {
                  ps =connection.prepareStatement(queryUpdate);
                  rowEffect = ps.executeUpdate();
              }
        } catch (Exception e) {
            System.out.println("getIvrsNo ERROR inside SurveyModel - " + e);
        }
        return rowEffect;
       }
    /*   public String getBillImagePath(String bill_name) throws SQLException
        {
            String path="";
            String query="select id.destination_path from image_destination as id,image_uploaded_for as iu "
                             +"where id.image_uploaded_for_id=iu.image_uploaded_for_id AND image_uploaded_for_name='"+bill_name+"'";
            PreparedStatement ps=connection.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                path=rs.getString("destination_path");
            return path;
        }*/

    public int getImage_details_id(String meter_name_auto, String date)
    {
        int id=0;
            String query="SELECT general_image_details_id FROM meter_jabalpur.meter_bill mb, meter_jabalpur.meters m where m.meter_id=mb.meter_id"
                            +" AND mb.bill_month='"+date+"' AND m.meter_name_auto='"+meter_name_auto+"'";
            try{
            PreparedStatement ps=connection.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                id=rs.getInt("general_image_details_id");
        }catch(Exception e)
        {
            id=-1;
            e.printStackTrace();
        }
            return id;
    }
    public String geImage_Name(int id) {
            String path="";
            String query="select gid.image_name,id.destination_path from meter_jabalpur.general_image_details as gid,meter_jabalpur.image_destination as id where gid.image_destination_id=id.image_destination_id AND general_image_details_id="+id+"";
            try{
            PreparedStatement ps=connection.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                path=rs.getString("image_name")+"&#"+rs.getString("destination_path");
        }catch(Exception e){
                e.printStackTrace();
        }
        return path;
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
