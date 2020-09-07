/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.model;
import com.survey.tableClasses.SwitchingSurveyBean;
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
public class SwitchingSurveyModel {
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
            System.out.println("SwitchingSurveyTypeModel setConnection() Error: " + e);
        }
    }
 public byte[] generateMapReport(String jrxmlFilePath, List<SwitchingSurveyBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
     //    HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in SwitchingSurveyModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
public List<String> getSwitchNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT pole_no_s FROM switching_point_detail GROUP BY pole_no_s ORDER BY pole_no_s ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switch_no = rset.getString("pole_no_s");
                if (switch_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switch_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Switch no exists.");
            }
        } catch (Exception e) {
            System.out.println("getSwitchNo ERROR inside switchSurveyModel - " + e);
        }
        return list;
    }
public List<String> getMeterNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT meter_no FROM switching_point_survey GROUP BY meter_no ORDER BY meter_no ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String meter_no = rset.getString("meter_no");
                if (meter_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(meter_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Meter no exists.");
            }
        } catch (Exception e) {
            System.out.println("getMeterNo ERROR inside switching_survey_model - " + e);
        }
        return list;
    }

public List<String> getSurveyDate(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT survey_date FROM survey GROUP BY survey_date ORDER BY survey_date ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String survey_date = rset.getString("survey_date");
                if (survey_date.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(survey_date);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Survey Date exists.");
            }
        } catch (Exception e) {
            System.out.println("getSurveyDate ERROR inside switchSurveyModel - " + e);
        }
        return list;
    }
public int getSwitchPointId(String pole_no_s) {
        int switch_id = 0;
        String query = " SELECT switching_point_detail_id FROM switching_point_detail WHERE pole_no_s = ? ";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_no_s);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            switch_id = rset.getInt("switching_point_detail_id");
        } catch (Exception e) {
            System.out.println("switchSurveyModel getSwitchNo() Error: " + e);
        }
        return switch_id;
    }
private int getMaxId() {
        int switch_survey_id = 0;
        String query = "SELECT MAX(switching_point_survey_id) AS new_switch_id FROM switching_point_survey ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                switch_survey_id = rset.getInt("new_switch_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return switch_survey_id;
    }
 public int insertRecord(SwitchingSurveyBean switchTypeBean) {

        String query = "INSERT INTO switching_point_survey (switching_point_survey_id, survey_id, "
                + " meter_no, meter_functional, r_phase, y_phase, b_phase, power, "
                + " fuse_functional, contacter_functional, mccb_functional, timer_functional, "
                + " active, remark, created_by) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


        int switch_survey_id = getMaxId();
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, switch_survey_id + 1);
            pstmt.setInt(2, switchTypeBean.getSurvey_id());
            pstmt.setString(3, switchTypeBean.getMeter_no());
            pstmt.setString(4, switchTypeBean.getMeter_functional());
            pstmt.setString(5, switchTypeBean.getR_phase());
            pstmt.setString(6, switchTypeBean.getY_phase());
            pstmt.setString(7, switchTypeBean.getB_phase());
            pstmt.setString(8, switchTypeBean.getPower());
            pstmt.setString(9, switchTypeBean.getFuse_functional());
            pstmt.setString(10, switchTypeBean.getContacter_functional());
            pstmt.setString(11, switchTypeBean.getMccb_functional());
            pstmt.setString(12, switchTypeBean.getTimer_functional());
            pstmt.setString(13, switchTypeBean.getActive());
            pstmt.setString(14, switchTypeBean.getRemark());
            pstmt.setString(15, switchTypeBean.getCreated_by());
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
 public int reviseRecord(SwitchingSurveyBean switchTypeBean) {
        String query1 = "INSERT INTO switching_point_survey (switching_point_survey_id, survey_id, "
                + " meter_no, meter_functional, r_phase, y_phase, b_phase, power, "
                + " fuse_functional, contacter_functional, mccb_functional, timer_functional, "
                + " active, remark, ss_rev_no, created_by) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        String query2 = " UPDATE switching_point_survey SET active='Revised'"
                + " WHERE switching_point_survey_id = ? and ss_rev_no = ? ";
       //   int ward_rev_no = getRevNo();
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, switchTypeBean.getSwitching_point_survey_id());
            //pstmt.setInt(2, switchTypeBean.getSwitching_point_detail_id());
            pstmt.setInt(2, switchTypeBean.getSurvey_id());
            pstmt.setString(3, switchTypeBean.getMeter_no());
            pstmt.setString(4, switchTypeBean.getMeter_functional());
            pstmt.setString(5, switchTypeBean.getR_phase());
            pstmt.setString(6, switchTypeBean.getY_phase());
            pstmt.setString(7, switchTypeBean.getB_phase());
            pstmt.setString(8, switchTypeBean.getPower());
            pstmt.setString(9, switchTypeBean.getFuse_functional());
            pstmt.setString(10, switchTypeBean.getContacter_functional());
            pstmt.setString(11, switchTypeBean.getMccb_functional());
            pstmt.setString(12, switchTypeBean.getTimer_functional());
            pstmt.setString(13, switchTypeBean.getActive());
            pstmt.setString(14, switchTypeBean.getRemark());
            pstmt.setString(15, switchTypeBean.getCreated_by());
            pstmt.setInt(16, switchTypeBean.getSs_rev_no() + 1);

            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {

                pstmt = connection.prepareStatement(query2);
                pstmt.setInt(1, switchTypeBean.getSwitching_point_survey_id());
                pstmt.setInt(2, switchTypeBean.getSs_rev_no());
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
 public int getNoOfRows(String searchMeterNo, String searchSwitchNo) {
        String query = " SELECT Count(*) "
                + " FROM switching_point_survey sps, switching_point_detail spd "
                + " WHERE IF('" + searchMeterNo + "' = '', sps.meter_no LIKE '%%', sps.meter_no =?) "
                + " AND IF('" + searchSwitchNo + "' = '',spd.pole_no_s LIKE '%%',spd.pole_no_s =?) "
                + " ORDER BY sps.meter_no ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchMeterNo);
            stmt.setString(2, searchSwitchNo);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows SwitchingPointSurvey model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }
    public List<SwitchingSurveyBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchMeterNo, String searchSwitchNo) {
        List<SwitchingSurveyBean> list = new ArrayList<SwitchingSurveyBean>();
        /*ward_no, ward_name, created_date, created_by, remark, city_id, ward_id, ward_rev_no, active; */
        String query = " SELECT sps.switching_point_survey_id, sps.ss_rev_no, sps.meter_no, sps.meter_functional, DATE_FORMAT(sps.created_date,'%d-%m-%Y') AS created_date, sps.created_by, sps.remark, "
                + " sps.r_phase, sps.y_phase, sps.b_phase,sps.active, "
                + " sps.power, sps.fuse_functional, sps.contacter_functional, sps.mccb_functional, sps.timer_functional "
                + " FROM switching_point_survey sps "
                + " WHERE IF('" + searchMeterNo + "' = '', sps.meter_no LIKE '%%', sps.meter_no =? ) "
            //    + " AND IF('" + searchSwitchNo + "' = '',spd.pole_no_s LIKE '%%', spd.pole_no_s =?) "
                + " ORDER BY sps.meter_no "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchMeterNo);
            pstmt.setString(2, searchSwitchNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SwitchingSurveyBean switchSurveyType = new SwitchingSurveyBean();
                switchSurveyType.setSwitching_point_survey_id(rset.getInt("switching_point_survey_id"));
                switchSurveyType.setSs_rev_no(rset.getInt("ss_rev_no"));
     //         switchSurveyType.setSwitching_pole_no(rset.getString("pole_no_s"));
                switchSurveyType.setMeter_no(rset.getString("meter_no"));
                switchSurveyType.setMeter_functional(rset.getString("meter_functional"));
                switchSurveyType.setR_phase(rset.getString("r_phase"));
                switchSurveyType.setY_phase(rset.getString("y_phase"));
                switchSurveyType.setB_phase(rset.getString("b_phase"));
                switchSurveyType.setPower(rset.getString("pin_code"));
                switchSurveyType.setFuse_functional(rset.getString("fuse_functional"));
                switchSurveyType.setContacter_functional(rset.getString("contacter_functional"));
                switchSurveyType.setMccb_functional(rset.getString("mccb_functional"));
                switchSurveyType.setTimer_functional(rset.getString("timer_functional"));
                //switchSurveyType.setSurvey_date(rset.getString("survey_date"));
                switchSurveyType.setCreated_date(rset.getString("created_date"));
                switchSurveyType.setCreated_by(rset.getString("created_by"));
                switchSurveyType.setRemark(rset.getString("remark"));
                switchSurveyType.setActive(rset.getString("active"));
                list.add(switchSurveyType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

   public int cancelRecord(int switching_survey_id, int ss_rev_no) {
        String query = "UPDATE switching_point_survey SET active='Cancelled' WHERE switching_point_survey_id = "+ switching_survey_id
                + " and ss_rev_no = "+ ss_rev_no ;
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
