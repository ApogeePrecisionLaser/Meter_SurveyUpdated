/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.general.model;

import com.survey.tableClasses.MISTypeBean;
import com.survey.util.GetDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class AlertsModel {

    private Connection connection;
    private int user_id;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<String> getMessageFactor() {
        List<String> list = new ArrayList<String>();
        String query = " SELECT message_factor FROM alert_message where active = 'Y'";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                list.add(rset.getString("message_factor"));
            }
        } catch (Exception e) {
            System.out.println("Error: AlertsModel getMessageFactor" + e);
        }
        return list;
    }

    private int getAlertIdByParamenter(String alert_parameter) {
        int id = 0;
        String query = " SELECT message_id FROM alert_message WHERE message_factor = '" + alert_parameter + "';";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt("message_id");
            }
        } catch (Exception e) {
            System.out.println("Error: AlertsModel getAlertIdByParamenter" + e);
        }
        return id;
    }

    public String getAlertQuery(int tube_well_survey_id, String pole_no, int alert_id) {
        String query = "";
        //  String prevBillMonth = GetDate.getPreviousMonth(bill_month);
        GetDate getDate = new GetDate();
        String year = String.valueOf(getDate.getCurrentYear());
        String previous_month = getDate.getPreviousMonth(getDate.getCurrentMonth(), year);

        if (alert_id == 1) {

            query = "  SELECT ts.r_phase as parameter1,td.r_phase as parameter2,"
                    + "round(((ts.r_phase-td.r_phase)*100)/td.r_phase,2) as difference_amt"
                    + " FROM tube_well_survey as ts,survey as s,pole as p,tube_well_detail as td"
                    + " where ts.tube_well_survey_id=s.survey_id and s.status='Y' and ts.active='Y' "
                    + "and td.active='Y' and p.active='Y'and td.pole_id=p.pole_id "
                    + "and td.pole_no_s=s.pole_no and s.pole_no='" + pole_no + "' and tube_well_survey_id='" + tube_well_survey_id + "' "
                    + "and if(round(((ts.r_phase-td.r_phase)*100)/td.r_phase,2)>10,true,false)";

        } else if (alert_id == 2) {
            query = "  SELECT ts.y_phase as parameter1,td.y_phase as parameter2,"
                    + "round(((ts.y_phase-td.y_phase)*100)/td.y_phase,2) as difference_amt"
                    + " FROM tube_well_survey as ts,survey as s,pole as p,tube_well_detail as td"
                    + " where ts.tube_well_survey_id=s.survey_id and s.status='Y' and ts.active='Y' "
                    + "and td.active='Y' and p.active='Y'and td.pole_id=p.pole_id "
                    + "and td.pole_no_s=s.pole_no and s.pole_no='" + pole_no + "' and tube_well_survey_id='" + tube_well_survey_id + "'"
                    + "and if(round(((ts.y_phase-td.y_phase)*100)/td.y_phase,2)>10,true,false)";

        } else if (alert_id == 3) {

            query = "  SELECT ts.b_phase as parameter1,td.b_phase as parameter2,"
                    + "round(((ts.b_phase-td.b_phase)*100)/td.b_phase,2) as difference_amt"
                    + " FROM tube_well_survey as ts,survey as s,pole as p,tube_well_detail as td"
                    + " where ts.tube_well_survey_id=s.survey_id and s.status='Y' and ts.active='Y' "
                    + "and td.active='Y' and p.active='Y'and td.pole_id=p.pole_id "
                    + "and td.pole_no_s=s.pole_no and s.pole_no='" + pole_no + "' and tube_well_survey_id='" + tube_well_survey_id + "'"
                    + "and if(round(((ts.b_phase-td.b_phase)*100)/td.b_phase,2)>10,true,false)";

        } else if (alert_id == 4) {
            query = " select round((((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000),2) as parameter1,ts.r_phase,ts.y_phase,ts.b_phase,td.pole_no_s,"
                    + " if(mb.consume_unit is null,0.0,mb.consume_unit) as parameter2,(round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000,2)"
                    + "- if(mb.consume_unit is null,0.0,mb.consume_unit)) as difference_Amt from tube_well_detail as td ,meters as m left join"
                    + " mpeb_meter_bill as mb on mb.meter_id=m.meter_id AND mb.final_revision = 'VALID' and mb.bill_month='Nov-2015',pole as p,tube_well_survey"
                    + "  as ts,survey as s where m.meter_id=td.meter_id and ts.tube_well_survey_id=s.survey_id and ts.pole_id=p.pole_id  and s.status='Y'"
                    + "  and ts.active='Y' and td.pole_id=p.pole_id and td.active='Y' and m.final_revision='VALID'"
                    + " and p.active='Y' and td.meter_no_s=ts.meter_no and m.poll_no='" + pole_no + "'and  if(round(round((((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000),2)"
                    + "-if(mb.current_reading is null,0.0,mb.current_reading) ,2)< (3*round((((ts.r_phase+ts.y_phase+ts.b_phase)*230*4*ts.hours)/1000)+ts.meter_reading,2)),false,true)";
//            query = " select round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000,2) as parameter1,"
//                    + "ts.r_phase,ts.y_phase,ts.b_phase,td.pole_no_s,if(mb.current_reading is null,0.0,mb.current_reading) as parameter2,"
//                    + "(round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000,2)-if(mb.current_reading is null,0.0,mb.current_reading)) as difference_Amt "
//                    + "from tube_well_detail as td ,meters as m left join mpeb_meter_bill as mb "
//                    + "on mb.meter_id=m.meter_id AND mb.final_revision = 'VALID' and mb.bill_month='Nov-2015',"//mb.bill_month='" + previous_month + "',"
//                    + "pole as p,tube_well_survey as ts,survey as s where m.meter_id=td.meter_id and ts.tube_well_survey_id=s.survey_id "
//                    + "and ts.pole_id=p.pole_id  and s.status='Y' and ts.active='Y' and td.pole_id=p.pole_id and td.active='Y'"
//                    + " and m.final_revision='VALID' and p.active='Y' and td.meter_no_s=ts.meter_no and m.poll_no='" + pole_no + "'"
//                    + "and if((round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*4)/1000,2)-if(mb.current_reading is null,0.0,mb.current_reading))<(3*round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*4)/1000,2)),true,false)";

        } else if (alert_id == 5) {

            query ="select round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours)/1000,2) as parameter1,s.survey_date,"
                    + "mb.current_reading_date,DATEDIFF(s.survey_date,mb.current_reading_date) as days,ts.r_phase,ts.y_phase,"
                    + "ts.b_phase,td.pole_no_s,if(mb.consume_unit is null,0.0,mb.consume_unit) as parameter2,"
                    + "round((ts.meter_reading)-mb.current_reading+(DATEDIFF(s.survey_date,mb.current_reading_date)*round(((ts.r_phase+ts.y_phase+ts.b_phase)*230)/1000,2))) as difference_Amt "
                    + "from tube_well_detail as td ,meters as m left join mpeb_meter_bill as mb on mb.meter_id=m.meter_id AND mb.final_revision = 'VALID' and "
                    + "mb.bill_month='Nov-2015',pole as p,tube_well_survey as ts,survey as s where m.meter_id=td.meter_id "
                    + "and ts.tube_well_survey_id=s.survey_id and ts.pole_id=p.pole_id  and s.status='Y' and ts.active='Y' "
                    + "and td.pole_id=p.pole_id and td.active='Y' and m.final_revision='VALID' and p.active='Y' and "
                    + "td.meter_no_s=ts.meter_no and m.poll_no='mr-46/3' and if(((ts.meter_reading)-mb.current_reading+"
                    + "(DATEDIFF(s.survey_date,mb.current_reading_date)*round(((ts.r_phase+ts.y_phase+ts.b_phase)*230)/1000,2)))<(3*round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*4)/1000,2)),false,true)";

//                    "select round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000,2) as parameter1,s.survey_date,"
//                    + "mb.current_reading_date,DATEDIFF(s.survey_date,mb.current_reading_date) as days,ts.r_phase,ts.y_phase,"
//                    + "ts.b_phase,td.pole_no_s,if(mb.consume_unit is null,0.0,mb.consume_unit) as parameter2,"
//                    + "(if(mb.consume_unit is null,0.0,mb.consume_unit)-round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000,2)"
//                    + "+(DATEDIFF(s.survey_date,mb.current_reading_date)*round(((ts.r_phase+ts.y_phase+ts.b_phase)*230)/1000,2))) as difference_Amt "
//                    + "from tube_well_detail as td ,meters as m left join mpeb_meter_bill as mb on mb.meter_id=m.meter_id AND mb.final_revision = 'VALID' and"
//                    + "mb.bill_month='Nov-2015',pole as p,tube_well_survey as ts,survey as s where m.meter_id=td.meter_id"
//                    + " and ts.tube_well_survey_id=s.survey_id and ts.pole_id=p.pole_id  and s.status='Y' and ts.active='Y'"
//                    + " and td.pole_id=p.pole_id and td.active='Y' and m.final_revision='VALID' and p.active='Y' and"
//                    + " td.meter_no_s=ts.meter_no and m.poll_no='" + pole_no + "' and if((if(mb.consume_unit is null,0.0,mb.consume_unit)-round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*ts.hours*30)/1000,2)+"
//                    + "(DATEDIFF(s.survey_date,mb.current_reading_date)*round(((ts.r_phase+ts.y_phase+ts.b_phase)*230)/1000,2)))<(3*round(((ts.r_phase+ts.y_phase+ts.b_phase)*230*4)/1000,2)),false,true)";

        }

        return query;
    }

    public int insertAlertSheetData(int tube_well_survey_id, String pole_no) {
        String query = "";
        int rowsAffected = 0;

        List<String> messageFactor = getMessageFactor();
        Iterator<String> itr = messageFactor.iterator();
        PreparedStatement pstmt;
        try {
            while (itr.hasNext()) {
                String msg_factor = (String) itr.next();
                int alert_id = getAlertIdByParamenter(msg_factor);
                query = getAlertQuery(tube_well_survey_id, pole_no, alert_id);
                if (query != null || !query.isEmpty()) {
                    try {
                        pstmt = connection.prepareStatement(query);
                        ResultSet rset = pstmt.executeQuery();
                        while (rset.next()) {
                            MISTypeBean misTypeBean = new MISTypeBean();
                            misTypeBean.setTube_well_survey_id(tube_well_survey_id);
                            misTypeBean.setRevision_no(getFinalRevision(tube_well_survey_id));
                            misTypeBean.setAlert_id(alert_id);
                            misTypeBean.setParameter1(rset.getString("parameter1"));
                            misTypeBean.setParameter2(rset.getString("parameter2"));
                            misTypeBean.setDifference_amt(rset.getDouble("difference_amt"));
                            misTypeBean.setRemark("backend insertion");
                            rowsAffected = insertData(misTypeBean);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: AlertsModel insertAlertSheetData" + e);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: AlertsModel insertAlertSheetData" + e);
        }
        return rowsAffected;
    }

    public int getFinalRevision(int meter_id) {
        int rev_no = 0;
        try {
            String query = " SELECT revison_no FROM tube_well_survey WHERE tube_well_survey_id = " + meter_id + " AND active='Y' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                rev_no = rst.getInt("revison_no");
            }
        } catch (Exception e) {
            System.out.println("Errror in AlertsModel getMeter_id" + e);
        }
        return rev_no;
    }

    public int insertData(MISTypeBean misTypeBean) {
        int rowAffected = 0;
        try {
            String query = "insert into bill_alert(tube_well_survey_id, revision_no, alert_id, parameter1, parameter2, difference1, remark) "
                    + " values(?,?,?,?,? ,? ,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, misTypeBean.getTube_well_survey_id());
            pstmt.setInt(2, misTypeBean.getRevision_no());
            // pstmt.setString(3, misTypeBean.getBill_month1());
            pstmt.setInt(3, misTypeBean.getAlert_id());
            pstmt.setString(4, misTypeBean.getParameter1());
            pstmt.setString(5, misTypeBean.getParameter2());
            pstmt.setDouble(6, misTypeBean.getDifference_amt());
            pstmt.setString(7, misTypeBean.getRemark());
            // pstmt.setInt(9, user_id);
            rowAffected = pstmt.executeUpdate();
            if (rowAffected == 0) {
                System.out.println("########## alert_id: " + misTypeBean.getAlert_id() + " Error For meter_id: " + misTypeBean.getTube_well_survey_id() + " #############3");
            }
        } catch (Exception e) {
            System.out.println("Error MISModel insertData" + e);
        }

        return rowAffected;
    }
}
