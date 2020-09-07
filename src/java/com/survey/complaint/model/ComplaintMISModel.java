/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.complaint.model;

import com.survey.complaint.tableClasses.ComplaintFeedback;
import com.survey.complaint.tableClasses.ComplaintHistoryReport;
import com.survey.complaint.tableClasses.ComplaintRegister;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author jpss
 */
public class ComplaintMISModel {
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private String sms_mail_message;
    private String sms_mail_msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setDriverClass(String driverclass) {
        this.driverClass = driverclass;
    }

    public void setConnectionString(String connectionstring) {
        this.connectionString = connectionstring;
    }

    public void setDb_userName(String username) {
        this.db_userName = username;
    }

    public void setDb_userPasswrod(String pass) {
        this.db_userPasswrod = pass;
    }
    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public String getSmsMailMessage() {
        return sms_mail_message;
    }

    public String getSmsMailMsgBgColor() {
        return sms_mail_msgBgColor;
    }
    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("AdAssoSiteDetailsModel setConnection() Error: " + e);
        }
    }

//    public void setConnection(Connection con) {
//        try {
//
//            connection = con;
//        } catch (Exception e) {
//            System.out.println("ComplaintRegisterModel setConnection() Error: " + e);
//        }
//    }

    public Map getStatuslist() {

        Map<Integer, String> statuslist = new HashMap<Integer, String>();
        try {

            String query = " select * from complaint_status";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                statuslist.put(rst.getInt("complaint_status_id"), rst.getString("complaint_status"));
            }


        } catch (Exception e) {
        }
        return statuslist;
    }

    public Map getComplintTypelist() {
        Map<Integer, String> complaintTypelist = new HashMap<Integer, String>();
        try {
            String query = " select * from complaint_type  ct ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                complaintTypelist.put(rst.getInt("complaint_type_id"), rst.getString("complaint_type"));
            }


        } catch (Exception e) {
        }
        return complaintTypelist;
    }

    public Map getComplaintStatusList() {
        Map<Integer, String> complaintStatuslist = new HashMap<Integer, String>();
        try {
            String query = " select * from complaint_status  cs ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                complaintStatuslist.put(rst.getInt("complaint_status_id"), rst.getString("complaint_status"));
            }


        } catch (Exception e) {
        }
        return complaintStatuslist;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ComplaintRegisterModel CloseConnection() Error: " + e);
        }
    }
    /*
    public Map getAdSubSite(String site_name){
    // String response_ad_sub_site=" ";
    Map<Integer, String> response_ad_sub_site_name = new HashMap<Integer, String>();
    try {
    String query =" select ss.sub_site_id,concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) as name from ad_site  ad,ad_sub_site ss,ad_mounting am "
    +" where "
    + " ad.site_id=ss.site_id and ss.mounting_id=am.mounting_id"
    + " and  concat(site_name , ' , ' ,address1)  = ?";



    PreparedStatement psmt = connection.prepareStatement(query);
    psmt.setString(1, site_name);

    ResultSet rset = psmt.executeQuery();
    while (rset.next()) {    // move cursor from BOR to valid record.
    response_ad_sub_site_name.put(rset.getInt("sub_site_id"), rset.getString("name"));
    }
    } catch (Exception e) {
    System.out.println("Error: ComplaintRegisterModel " + e);
    }
    System.out.println("response_ad_sub_site_name="+response_ad_sub_site_name);
    return  response_ad_sub_site_name;
    } */
    /*
    public Map getAdSubSiteName(String site_name){
    // String response_ad_sub_site=" ";
    System.out.println("model ad subsite name"+site_name);
    Map<Integer, String> response_ad_sub_site_name = new HashMap<Integer, String>();
    try {
    String query =" select ss.sub_site_id,concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) as name from ad_site  ad,ad_sub_site ss,ad_mounting am "
    +" where "
    + " ad.site_id=ss.site_id and ss.mounting_id=am.mounting_id"
    + " and  site_name  = ?";



    PreparedStatement psmt = connection.prepareStatement(query);
    psmt.setString(1, site_name);

    ResultSet rset = psmt.executeQuery();
    while (rset.next()) {    // move cursor from BOR to valid record.
    response_ad_sub_site_name.put(rset.getInt("sub_site_id"), rset.getString("name"));
    }
    } catch (Exception e) {
    System.out.println("Error: ComplaintRegisterModel " + e);
    }
    System.out.println("response_ad_sub_site_name="+response_ad_sub_site_name);
    return  response_ad_sub_site_name;
    }*/

    public String getAdSubSite(String site_name) {
        String response_ad_sub_site = " ";
        //   List<String> list = new ArrayList<String>();
        System.out.println("model ad subsite name" + site_name);

        try {
            String query = " select ss.sub_site_id,concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) as name from ad_site  ad,ad_sub_site ss,ad_mounting am "
                    + " where "
                    + " ad.site_id=ss.site_id and ss.mounting_id=am.mounting_id"
                    + " and  concat(site_name , ' , ' ,address1)  = ?";


            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, site_name);

            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                response_ad_sub_site += rset.getInt("sub_site_id") + "=" + rset.getString("name") + "#";
                System.out.println("response_ad_sub_site=" + response_ad_sub_site);
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
        // System.out.println("response_ad_sub_site=" +response_ad_sub_site);
        return response_ad_sub_site;
    }

    public List<String> getComplaintNo(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        // String query = "SELECT complaint_no FROM complaint_feedback ";
        String query = " select complaint_register_id from complaint_register where active ='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int complaint_register_id = rset.getInt("complaint_register_id");

                String complaint_no_list = Integer.toString(complaint_register_id);
                //  if (feedback_type.toUpperCase().startsWith(q.toUpperCase())) {
                list.add(complaint_no_list);
                count++;
                //    }
            }
            if (count == 0) {
                list.add("No Such Type Of Compalint Number  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintRegisterModel --getComplaintNo-- " + e);
        }
        return list;
    }

    public List<String> getPostedBy() {
        List<String> list = new ArrayList<String>();
        String query = " SELECT key_person_name  "
                + " FROM key_person k, org_office o,organisation_name oc, organisation_map om,organisation_type ot "
                + " where k.org_office_id=o.org_office_id "
                + " and o.organisation_id= oc.organisation_id  and oc.organisation_id= om.organisation_id  "
                + "and om.organisation_type_id=ot.organisation_type_id and  org_type_name='Home'  ORDER BY key_person_name  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("key_person_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: Complaint_Register_Model  getPostedBy " + e);
        }
        return list;
    }

    public List<String> getAssingedFor() {
        List<String> list = new ArrayList<String>();
        String query = " SELECT key_person_name "
                + "  FROM key_person k, org_office o,organisation_name oc, organisation_map om,organisation_type ot,designation ds"
                + " where k.org_office_id=o.org_office_id "
                + " and o.organisation_id= oc.organisation_id  and oc.organisation_id= om.organisation_id "
                + "  and om.organisation_type_id=ot.organisation_type_id and  org_type_name='Home' and k.designation_id=ds.designation_id and ds.designation='Technician' ORDER BY key_person_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("key_person_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: Complaint_Register_Model  getAssingedFor " + e);
        }
        return list;
    }

    public List<String> getsiteList(String ad_sub_type2) {
        List<String> list = new ArrayList<String>();
        String query = "select concat(site_name , ' , ' ,address1) as site_name from ad_site as s , city as c , organisation_name o ,"
                + " advertising_sub_type2 ads "
                + "where s.city_id = c.city_id and s.organisation_id= o.organisation_id and ads.advertising_sub_type2_id ="
                + "s.advertising_sub_type2_id  "
                + " and advertising_sub_type2_name= ?  ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, ad_sub_type2);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                list.add(rset.getString("site_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
        return list;
    }

    public int getsite_id(String sitename) {
        int site_id = 0;
        String query = "select site_id from ad_site as s "
                + "where "
                + " concat(site_name , ' , ' ,address1)  = ?  ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, sitename);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                site_id = rset.getInt("site_id");
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
        return site_id;
    }

    public int getSubSite_Id(String ad_sub_site_name) {
        int sub_site_id = 0;
        String query = "select sub_site_id from ad_sub_site as ss,ad_mounting am "
                + "where "
                + "  ss.mounting_id=am.mounting_id"
                + " concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) = ? ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, ad_sub_site_name);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                sub_site_id = rset.getInt("sub_site_id");
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
        return sub_site_id;
    }

    public String getAdSubSiteName(String sub_site_name) {
        System.out.println("sub_site_name=" + sub_site_name);
        String response_ad_sub_site = " ";
        String query = "select sub_site_id,name from ad_sub_site as ss,ad_mounting am "
                + "where "
                + "  ss.mounting_id=am.mounting_id"
                + " concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) = ? ";


        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, sub_site_name);

            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                response_ad_sub_site += rset.getInt("sub_site_id") + "=" + rset.getString("name") + "#";
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }

        return response_ad_sub_site;
    }

    public String getSiteName(String site_name) {
        String response_data = "";
        try {
            String query = " select concat(site_name , ' , ' ,address1) as name from  ad_site  where  site_name=? ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, site_name);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                response_data = rset.getString("name");

            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintRegisterModel --getComplaintSiteName-- " + e);
        }
        return response_data;
    }

    public int getKeyPersonId(String keyPerson) {
        int key_person_id = 0;
        String query = "SELECT key_person_id FROM key_person k where key_person_name=?";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, keyPerson);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                key_person_id = rset.getInt("key_person_id");
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
        return key_person_id;
    }

    public int getkey_person_id(String key_person) {
        int key_person_id = 0;
        String query = "SELECT key_person_id  "
                + " FROM key_person k, org_office o,organisation_name oc, organisation_map om,organisation_type ot "
                + " where k.org_office_id=o.org_office_id "
                + " and o.organisation_id= oc.organisation_id  and oc.organisation_id= om.organisation_id  "
                + "and om.organisation_type_id=ot.organisation_type_id and  org_type_name='Home' and key_person_name= ? ORDER BY key_person_name  ";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, key_person);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                key_person_id = rset.getInt("key_person_id");
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
        return key_person_id;
    }

    public int deleteRecord(int complaint_register_id) {
        String query = "DELETE FROM complaint_register WHERE complaint_register_id = " + complaint_register_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
    //public int sendSmsMail(int complaint_register_id){
    //   return ;
    // }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public int getNoOfRows(int status, String posted_by, String assigned_for, String complaintDateFrom, String complaintDateTo, int s_complaint_no) {
        int noOfRows = 0;

        System.out.println("complaintDateFrom=" + complaintDateFrom);

        System.out.println("complaintDateTo=" + complaintDateTo);
        java.sql.Date search_complaint_date_from = null, search_complaint_date_to = null;
        if (complaintDateFrom != null) {
            try {
                search_complaint_date_from = convertToSqlDate(complaintDateFrom);
            } catch (ParseException ex) {
                System.out.println(ex);
            }
        }
        if (complaintDateTo != null) {
            try {
                search_complaint_date_to = convertToSqlDate(complaintDateTo);
            } catch (ParseException ex) {
                System.out.println(ex);
            }

        }
        try {

            String query = "select  count(*)"
                + "  from complaint_register cr "
                + "  left join( key_person k2 ) on k2. key_person_id = cr.assinged_for  "
                + "  ,complaint_status cs ,complaint_type ct, complaint_sub_type cst, pole p, "
                + "  key_person k1 "
                + "  where p.pole_id = cr.pole_id and p.active='Y' "
                //+ "  and  ad1.advertising_sub_type1_id = ad2.advertising_sub_type1_id "
                + "  and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_sub_type_id = cst.complaint_sub_type_id "
                + "  AND ct.complaint_type_id = cst.complaint_type_id and k1.key_person_id= cr.posted_by "// and ss.mounting_id=am.mounting_id"
                + "  and cr.active ='Y'  and "
                + "  if(" + status + "=0,cs.complaint_status_id like'%', cs.complaint_status_id=" + status + " ) "
                + " and "
                + "  if(" + s_complaint_no + "=0,cr.complaint_register_id like'%', cr.complaint_register_id=" + s_complaint_no + " ) "
                + "  and "
                + "  if('" + posted_by + "'='',k1.key_person_name like'%', k1.key_person_name='" + posted_by + "') "
                + "  and if('" + assigned_for + "'='',(k2.key_person_name like'%' or k2.key_person_name is null), k2.key_person_name='" + assigned_for + "') "
                + "  and if(" + search_complaint_date_from + " is null,complaint_date,complaint_date>=?)"
                + "  and if(" + search_complaint_date_to + " is null,complaint_date,complaint_date<=?)";




            PreparedStatement pst = connection.prepareStatement(query);
            // pst.setString(1, searchPrintMedia);
            pst.setDate(1, search_complaint_date_from);
            pst.setDate(2, search_complaint_date_to);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:ComplaintRegisterModel " + e);
        }
        return noOfRows;
    }

    public List<ComplaintHistoryReport> showData(int lowerLimit, int noOfRowsToDisplay, int status, String posted_by, String assigned_for, String complaintDateFrom, String complaintDateTo, int s_complaint_no) {
        List<ComplaintHistoryReport> list = new ArrayList<ComplaintHistoryReport>();
        //List<ComplaintFeedback> list1 = new ArrayList<ComplaintHistoryReport>();
        java.sql.Date search_complaint_date_from = null, search_complaint_date_to = null;
        PreparedStatement pst = null;
        int complaint_no = 0;
        int feedback = 0;
        if (complaintDateFrom != null) {
            try {
                search_complaint_date_from = convertToSqlDate(complaintDateFrom);
            } catch (ParseException ex) {
                System.out.println(ex);
            }
        }
        if (complaintDateTo != null) {
            try {
                search_complaint_date_to = convertToSqlDate(complaintDateTo);
            } catch (ParseException ex) {
                System.out.println(ex);
            }

        }
        String query = "select  complaint_revision_no , pole_no, p.pole_id, complaint_status , complaint_type, complaint_sub_type ,"
                + "  date_format(complaint_date,'%d-%m-%Y') as complaint_date , "
                + "  date_format(complition_date,'%d-%m-%Y') as complition_date , "
                + "  complaint_register_id ,"
                + "  k1. key_person_name as posted_person, k2.key_person_name as assigned_person, "
                + "  cr.complaint_sub_type_id, cr.complaint_status_id, posted_by, posted_date, complaint_date, "
                + "  assinged_for, cr.remark, complition_date "
                + "  from complaint_register cr "
                + "  left join( key_person k2 ) on k2. key_person_id = cr.assinged_for  "
                + "  ,complaint_status cs ,complaint_type ct, complaint_sub_type cst, pole p, "
                + "  key_person k1 "//, advertising_sub_type2 ad2 , advertising_sub_type1  ad1,ad_mounting am "
                + "  where p.pole_id = cr.pole_id and p.active='Y' "
                //+ "  and  ad1.advertising_sub_type1_id = ad2.advertising_sub_type1_id "
                + "  and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_sub_type_id = cst.complaint_sub_type_id "
                + "  AND ct.complaint_type_id = cst.complaint_type_id and k1.key_person_id= cr.posted_by " // and ss.mounting_id=am.mounting_id"
                + "  and cr.active ='Y'  and "
                + "  if(" + status + "=0,cs.complaint_status_id like'%', cs.complaint_status_id=" + status + " ) "
                + " and "
                + "  if(" + s_complaint_no + "=0,cr.complaint_register_id like'%', cr.complaint_register_id=" + s_complaint_no + " ) "
                + "  and "
                + "  if('" + posted_by + "'='',k1.key_person_name like'%', k1.key_person_name='" + posted_by + "') "
                + "  and if('" + assigned_for + "'='',(k2.key_person_name like'%' or k2.key_person_name is null), k2.key_person_name='" + assigned_for + "') "
                + "  and if(" + search_complaint_date_from + " is null,complaint_date,complaint_date>=?)"
                + "  and if(" + search_complaint_date_to + " is null,complaint_date,complaint_date<=?)"
                + " Order by cr.complaint_register_id desc"
                + "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            pst = connection.prepareStatement(query);
            pst.setDate(1, search_complaint_date_from);
            pst.setDate(2, search_complaint_date_to);
            ResultSet rset = pst.executeQuery();
            //   ComplaintHistoryReport com_reg = new ComplaintHistoryReport();
            while (rset.next()) {
                ArrayList<ComplaintFeedback> list1 = new ArrayList<ComplaintFeedback>();
                ComplaintHistoryReport com_reg = new ComplaintHistoryReport();
                //   ComplaintHistoryReport com_feedback = new ComplaintHistoryReport();
                com_reg.setComplaint_reg_no(rset.getInt("complaint_register_id"));
                complaint_no = rset.getInt("complaint_register_id");
                com_reg.setSite_nameM(rset.getString("pole_no"));
                com_reg.setPosted_byM(rset.getString("posted_person"));
                com_reg.setAssigned_forM(rset.getString("assigned_person"));
                com_reg.setStatusM(rset.getString("complaint_status"));
                com_reg.setComplaint_typeM(rset.getString("complaint_type"));
                com_reg.setComplaint_sub_typeM(rset.getString("complaint_sub_type"));
                com_reg.setComplaint_dateM(rset.getString("complaint_date"));
                com_reg.setComplition_date(rset.getString("complition_date"));
                com_reg.setCompalint_rev_no(rset.getInt("complaint_revision_no"));

                String query1 = " select cf.feedback_id ,cf.complaint_register_id,feedback_type, "
                        + " date_format(feedback_date,'%d-%m-%Y') as feedback_date, "
                        + "  date_format(feedback_date,'%h')  "
                        + "   as feedback_date_hr,date_format(feedback_date,'%i') as feedback_date_min, "
                        + "  date_format(feedback_date,'%p') as feedback_date_time "
                        + "from  "
                        + " complaint_feedback cf,feedback_type fd  "
                        + "     where cf.feedback_type_id=fd.feedback_type_id and cf.complaint_register_id=? ";

                pst = connection.prepareStatement(query1);
                pst.setInt(1, complaint_no);
                ResultSet rset1 = pst.executeQuery();
                // list.add(com_reg);
                while (rset1.next()) {
                    ComplaintFeedback feed_back = new ComplaintFeedback();
                    int feed=rset1.getInt("complaint_register_id");

                    feed_back.setFeedback_type(rset1.getString("feedback_type"));
                    feed_back.setFeedback_date(rset1.getString("feedback_date"));
                    feed_back.setFeedback_hr(rset1.getString("feedback_date_hr"));
                    feed_back.setFeedback_min(rset1.getString("feedback_date_min"));
                    feed_back.setFeedback_time(rset1.getString("feedback_date_time"));

                    list1.add(feed_back);
                }
                com_reg.setFeebackList(list1);
                list.add(com_reg);


            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintHistoryModel" + e);
        }
        return list;
    }

    public List<ComplaintRegister> showData(int status, String posted_by, String assigned_for, String complaintDateFrom, String complaintDateTo,int s_complaint_no, String delayed_work, int pole_id, int sp_id ) {
        List<ComplaintRegister> list = new ArrayList<ComplaintRegister>();
        java.sql.Date search_complaint_date_from = null, search_complaint_date_to = null;
        if (complaintDateFrom != null) {
            try {
                search_complaint_date_from = convertToSqlDate(complaintDateFrom);
            } catch (ParseException ex) {
                System.out.println(ex);
            }
        }
        if (complaintDateTo != null) {
            try {
                search_complaint_date_to = convertToSqlDate(complaintDateTo);
            } catch (ParseException ex) {
                System.out.println(ex);
            }

        }
//        String query = "select  complaint_revision_no ,concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) as name,ad1.advertising_sub_type1_name , ad2.advertising_sub_type2_name ,ad.site_id, cr.remark , complaint_status , complaint_type ,"
        String query = "select  complaint_revision_no, p.pole_id, cr.remark , complaint_status , complaint_sub_type , complaint_type, "
                + "  date_format(complaint_date,'%d-%m-%Y') as complaint_date , "
                + "  date_format(complition_date,'%d-%m-%Y') as complition_date , "
                + "  concat_ws(' ',p.pole_no,', ', spd.switching_point_name) as pole_no, cr.complaint_register_id ,"
                + "  k1. key_person_name as posted_person, k2.key_person_name as assigned_person, "
                + "  cr.complaint_sub_type_id, cr.complaint_status_id, posted_by, posted_date, complaint_date, "
                + "  assinged_for, cr.remark, spd.switching_point_detail_id, assign_date "
                + "  from complaint_register cr "
                + "  left join( key_person k2 ) on k2. key_person_id = cr.assinged_for  "
                + "  , complaint_status cs ,complaint_sub_type cst, complaint_type ct, pole p, "
                + "  key_person k1, switching_point_detail spd "//, advertising_sub_type2 ad2 , advertising_sub_type1  ad1,ad_mounting am
                //+ "  where cr.site_id= ad.site_id and cr.sub_site_id=ss.sub_site_id and ad.advertising_sub_type2_id = ad2.advertising_sub_type2_id "
                //+ "  where ss.site_id= ad.site_id and cr.sub_site_id=ss.sub_site_id and ad.advertising_sub_type2_id = ad2.advertising_sub_type2_id "
                + "  where spd.switching_point_detail_id = p.switching_point_detail_id AND spd.active='Y' AND cr.pole_id = p.pole_id AND p.active = 'Y' "
                //+ "  and  ad1.advertising_sub_type1_id = ad2.advertising_sub_type1_id "
                + "  and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_sub_type_id ="
                + "  cst.complaint_sub_type_id AND ct.complaint_type_id=cst.complaint_type_id and k1.key_person_id= cr.posted_by " // and ss.mounting_id=am.mounting_id"
                + "  and cr.active ='Y'  and "
                + "  if(" + status + "=0,cs.complaint_status_id like'%', cs.complaint_status_id=" + status + " ) "
                +    " and "
                + "  if(" + s_complaint_no + "=0,cr.complaint_register_id like'%', cr.complaint_register_id=" + s_complaint_no + " ) "
                + "  and "
                + "  if('" + posted_by + "'='',k1.key_person_name like'%', k1.key_person_name='" + posted_by + "') "
                + "  and if('" + assigned_for + "'='',(k2.key_person_name like'%' or k2.key_person_name is null), k2.key_person_name='" + assigned_for + "') "
                + "  and if(" + search_complaint_date_from + " is null,complaint_date,complaint_date>=?)"
                + "  and if(" + search_complaint_date_to + " is null,complaint_date,complaint_date<=?)"
                + " and if('" + delayed_work + "'= 'Y', (complition_date < SUBDATE(curdate(),0)  and cs.complaint_status_id != 3), complition_date like '%') "//(select feedback_date from complaint_feedback cf where cf.complaint_register_id = cr.complaint_register_id )
                + " AND IF("+ pole_id +"!=0, p.pole_id = "+ pole_id +", p.pole_id like '%')"
                + " AND IF("+ sp_id +"!=0, p.switching_point_detail_id = "+ sp_id +", p.switching_point_detail_id like '%')";
                //+ " Order by cr.complaint_register_id desc";
                //+ "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setDate(1, search_complaint_date_from);
            pst.setDate(2, search_complaint_date_to);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                ComplaintRegister com_reg = new ComplaintRegister();
                com_reg.setComplaint_reg_no(rset.getInt("complaint_register_id"));
                com_reg.setSite_nameM(rset.getString("pole_no"));
                com_reg.setPole_id(rset.getInt("pole_id"));
                com_reg.setSp_id(rset.getInt("switching_point_detail_id"));
                com_reg.setPosted_byM(rset.getString("posted_person"));
                com_reg.setAssigned_forM(rset.getString("assigned_person"));
                com_reg.setAssign_date(rset.getString("assign_date"));
                com_reg.setStatusM(rset.getString("complaint_status"));
                com_reg.setComplaint_typeM(rset.getString("complaint_type"));
                com_reg.setComplaint_sub_typeM(rset.getString("complaint_sub_type"));
                com_reg.setComplaint_dateM(rset.getString("complaint_date"));
                com_reg.setComplition_date(rset.getString("complition_date"));
                com_reg.setRemarkM(rset.getString("remark"));
                //com_reg.setAd_sub_type1(rset.getString("advertising_sub_type1_name"));
                //com_reg.setAd_sub_type2(rset.getString("advertising_sub_type2_name"));
                com_reg.setCompalint_rev_no(rset.getInt("complaint_revision_no"));
                //com_reg.setSub_site_name(rset.getString("name"));
                list.add(com_reg);
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
        }
        return list;
    }


    public byte[] generateComplaintReportPdf(String jrxmlFilePath, String posted_by, String assigned_for, int status_id, String delayed_work,int complaint_register_id, int pole_id, int sp_id) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("complaint_register_id", complaint_register_id);
        mymap.put("posted_by", posted_by);
        mymap.put("assigned_for", assigned_for);
        mymap.put("status_id", status_id);
        mymap.put("delayed_work", delayed_work);
        mymap.put("pole_id", pole_id);
        mymap.put("sp_id", sp_id);

        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("Error: in ComplaintHistoryReportController generatComplaintHistoryReport() JRException: " + e);
        }
        return reportInbytes;
    }
public ByteArrayOutputStream generateComplaintReportExcel(String jrxmlFilePath, String posted_by, String assigned_for, int status_id, String delayed_work,int complaint_register_id, int pole_id, int sp_id) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        mymap.put("complaint_register_id", complaint_register_id);
        mymap.put("posted_by", posted_by);
        mymap.put("assigned_for", assigned_for);
        mymap.put("status_id", status_id);
        mymap.put("delayed_work", delayed_work);
        mymap.put("pole_id", pole_id);
        mymap.put("sp_id", sp_id);
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jp = JasperFillManager.fillReport(compiledReport, mymap, connection);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in ComplaintHistory Controller generatHistoryReport() JRException: " + e);
        }
        return baos;
    }

}
