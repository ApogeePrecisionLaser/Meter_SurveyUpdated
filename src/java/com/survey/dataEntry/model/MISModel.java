/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.model;

import java.sql.Connection;
import com.survey.tableClasses.MISTypeBean;
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
public class MISModel {

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

    public byte[] generateMapReport(String jrxmlFilePath, List<MISTypeBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in MccbTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

//    public int insertRecord(MISTypeBean mccbTypeBean) {
//
//        String query = "INSERT INTO mccb (mccb_type, created_by, remark) VALUES (?,?,?) ";
//        int rowsAffected = 0;
//        try {
//            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, mccbTypeBean.getMccb_type());
//            pstmt.setString(2, mccbTypeBean.getCreated_by());
//            pstmt.setString(3, mccbTypeBean.getRemark());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error while inserting record...." + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//
//    }
//
//    public int updateRecord(MISTypeBean mccbTypeBean) {
//        String query = "UPDATE mccb SET mccb_type=?, created_by=?, remark=? WHERE mccb_id=? ";
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, mccbTypeBean.getMccb_type());
//            pstmt.setString(2, mccbTypeBean.getCreated_by());
//            pstmt.setString(3, mccbTypeBean.getRemark());
//            pstmt.setInt(4, mccbTypeBean.getMccb_id());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("error while updating record........." + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record updated successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot update the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }
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

    public List<MISTypeBean> showData() {
        List<MISTypeBean> list = new ArrayList<MISTypeBean>();
        //  String parameter1 = "", parameter2 = "", parameter3 = "";

//        String query = "select t.tube_well_survey_id,survey_date,s.pole_no,s.survey_pole_no,survey_date,"
//                + "meter_no,t.r_phase,t.y_phase,t.b_phase from tube_well_survey as t,survey as s "
//                + "where t.tube_well_survey_id=s.survey_id and t.active='Y' and s.status='Y'";
        String query="select t.tube_well_survey_id,s.pole_no,s.survey_pole_no,survey_date,b.difference1,a.message_factor,"
                + " meter_no,t.r_phase,t.y_phase,t.b_phase,a.message from tube_well_survey as t,survey as s,"
                + "bill_alert as b,alert_message as a where t.tube_well_survey_id=s.survey_id and b.tube_well_survey_id=t.tube_well_survey_id"
                + " and b.alert_id=a.message_id and t.active='Y' and s.status='Y' and alert_id='1' or '2' or '3'";
              try {
            PreparedStatement pstmt = null;
            //    pstmt.setString(1, searchMccbType);
            ResultSet rset = null;
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                 MISTypeBean misTypeBean = new MISTypeBean();
                misTypeBean.setPole_no(rset.getString("survey_pole_no"));
                misTypeBean.setSurvey_date(rset.getString("survey_date"));
                misTypeBean.setMeter_no(rset.getString("meter_no"));
                misTypeBean.setR_phase(rset.getString("r_phase"));
                misTypeBean.setY_phase(rset.getString("y_phase"));
                misTypeBean.setB_phase(rset.getString("b_phase"));
               misTypeBean.setMessage_factor(rset.getString("message_factor"));
                //misTypeBean.setParameter1(rset.getString("parameter1"));
                //misTypeBean.setParameter2(rset.getString("parameter2"));
                misTypeBean.setDifference_amt(rset.getDouble("difference1"));
                misTypeBean.setAlert_message(rset.getString("message"));
                list.add(misTypeBean);
            }



        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<MISTypeBean> showProjectedConsumptionData() {
        List<MISTypeBean> list = new ArrayList<MISTypeBean>();
        //  String parameter1 = "", parameter2 = "", parameter3 = "";
        String query1 = "select t.tube_well_survey_id,s.pole_no,s.survey_pole_no,survey_date,"
                + "b.difference1,parameter1,parameter2, meter_no,t.r_phase,t.y_phase,t.b_phase,a.message from "
                + "tube_well_survey as t,survey as s,bill_alert as b,alert_message as a where t.tube_well_survey_id=s.survey_id "
                + "and b.tube_well_survey_id=t.tube_well_survey_id and b.alert_id=a.message_id "
                + "and t.active='Y' and s.status='Y' and alert_id='4'";
        try {
            PreparedStatement pstmt = null;
            //    pstmt.setString(1, searchMccbType);
            ResultSet rset = null;
            pstmt = connection.prepareStatement(query1);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                MISTypeBean misTypeBean = new MISTypeBean();
                misTypeBean.setPole_no(rset.getString("survey_pole_no"));
                misTypeBean.setSurvey_date(rset.getString("survey_date"));
                misTypeBean.setMeter_no(rset.getString("meter_no"));
                misTypeBean.setR_phase(rset.getString("r_phase"));
                misTypeBean.setY_phase(rset.getString("y_phase"));
                misTypeBean.setB_phase(rset.getString("b_phase"));
                misTypeBean.setParameter1(rset.getString("parameter1"));
                misTypeBean.setParameter2(rset.getString("parameter2"));
                misTypeBean.setDifference_amt(rset.getDouble("difference1"));
                misTypeBean.setAlert_message(rset.getString("message"));
                list.add(misTypeBean);
            }



        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<MISTypeBean> showSurveyConsumptionData() {
        List<MISTypeBean> list = new ArrayList<MISTypeBean>();
        //  String parameter1 = "", parameter2 = "", parameter3 = "";
        String query = "select parameter1,parameter2,s.survey_date,mb.current_reading_date,mb.current_reading_date,DATEDIFF(s.survey_date,mb.current_reading_date) as days,ts.r_phase,"
                + "ts.y_phase,ts.b_phase,s.pole_no,parameter2,b.difference1 from tube_well_detail as td ,meters as m left join mpeb_meter_bill as mb "
                + "on mb.meter_id=m.meter_id AND mb.final_revision = 'VALID' and mb.bill_month='Dec-2015',pole as p,tube_well_survey as ts,survey as s,"
                + "bill_alert as b,alert_message as a where m.meter_id=td.meter_id and b.tube_well_survey_id=ts.tube_well_survey_id and b.alert_id=a.message_id "
                + "and ts.tube_well_survey_id=s.survey_id and ts.pole_id=p.pole_id  and s.status='Y' and ts.active='Y' and td.pole_id=p.pole_id "
                + "and td.active='Y' and m.final_revision='VALID' and p.active='Y' and alert_id='5'";
        try {
            PreparedStatement pstmt = null;
            //    pstmt.setString(1, searchMccbType);
            ResultSet rset = null;
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                MISTypeBean misTypeBean = new MISTypeBean();
                misTypeBean.setPole_no(rset.getString("survey_pole_no"));
                misTypeBean.setSurvey_date(rset.getString("survey_date"));
                misTypeBean.setCurrent_reading_date(rset.getString("current_reading_date"));
                misTypeBean.setDays(rset.getString("days"));
                misTypeBean.setMeter_no(rset.getString("meter_no"));
                misTypeBean.setR_phase(rset.getString("r_phase"));
                misTypeBean.setY_phase(rset.getString("y_phase"));
                misTypeBean.setB_phase(rset.getString("b_phase"));
                misTypeBean.setParameter1(rset.getString("parameter1"));
                misTypeBean.setParameter2(rset.getString("parameter2"));
                misTypeBean.setDifference_amt(rset.getDouble("difference1"));
                misTypeBean.setAlert_message(rset.getString("message"));
                misTypeBean.setCurrent_reading_date(rset.getString("current_reading"));
                list.add(misTypeBean);
            }



        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<MISTypeBean> generateAlerts(int tube_well_survey_id) {
        List<MISTypeBean> list = new ArrayList<MISTypeBean>();



        String query = "select if(difference1>10,a.message,'No Fault') as message,a.message_factor,b.difference1 from bill_alert as b,"
                + "alert_message as a,deviation_detail as d where a.message_id=b.alert_id and a.message_id=d.deviation_detail_id"
                + " and b.tube_well_survey_id='" + tube_well_survey_id + "' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                MISTypeBean misTypeBean = new MISTypeBean();
                misTypeBean.setDifference_amt(rset.getDouble("difference1"));
                misTypeBean.setAlert_message(rset.getString("message"));
                misTypeBean.setMessage_factor(rset.getString("message_factor"));
                list.add(misTypeBean);

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

    public List getyearLst() {
        List list = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        try {
            for (int i = year; i >= 1999; i--) {
                list.add(i);
            }
        } catch (Exception e) {
            System.out.println("MISModel getyearLst Error: " + e);
        }
        return list;
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
