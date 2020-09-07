/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.model;

import com.survey.mail.MailBean;
import com.survey.mail.SmsBean;
import com.survey.complaint.tableClasses.ComplaintFeedback;
import com.survey.util.MailSmsCredentialModel;
import com.survey.util.SendMailSmsModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.Timestamp;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author Shruti
 */
public class ComplaintFeedbackModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    /*public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ComplaintFeedbackModel setConnection() Error: " + e);
        }
    }*/

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("AdAssoSiteDetailsModel setConnection() Error: " + e);
        }
    }

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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("ComplaintFeedbackModel CloseConnection() Error: " + e);
        }
    }

    public List<String> getFeedbackTypelist() {

        List<String> feedback_type_list = new ArrayList<String>();
        try {

            String query = " select * from feedback_type  ORDER BY feedback_type";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                feedback_type_list.add(rst.getString("feedback_type"));
            }


        } catch (Exception e) {
        }
        return feedback_type_list;
    }

    public List<String> getAssignedForlist() {

        List<String> assigned_for_list = new ArrayList<String>();
        try {

            /*   select kp.key_person_name from
            complaint_feedback cf,feedback_type fd, complaint_register AS cr, key_person AS kp
            where cr.assinged_for=kp.key_person_id
            AND cf.feedback_type_id=fd.feedback_type_id
            AND cf.complaint_no=cr.complaint_register_id;
             */

        /*    String query = " select kp.key_person_name from "
                    + " complaint_feedback cf,feedback_type fd, complaint_register AS cr, key_person AS kp "
                    + " where cr.assinged_for=kp.key_person_id "
                    + " AND cf.feedback_type_id=fd.feedback_type_id "
                    + " AND cf.complaint_no=cr.complaint_register_id group by key_person_name";*/

 String query =   "select kp.key_person_name from "
                    + " complaint_register AS cr, key_person AS kp "
                    + " where cr.assinged_for=kp.key_person_id "
                    +" AND cr.active='y' group by key_person_name ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                assigned_for_list.add(rst.getString("key_person_name"));
            }
        } catch (Exception e) {
        }
        return assigned_for_list;
    }

    public java.sql.Timestamp setDateTime(String date) {
        java.sql.Timestamp finalDate = null;
        try {
            String strD = date;
            String[] str1 = strD.split("-");
            strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
            System.out.println("time" + strD);
            finalDate = new java.sql.Timestamp(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
            System.out.println("finalDate " + finalDate);
        } catch (Exception e) {
            System.out.println("ComplaintFeedbackModel setDateTime() -- error " + e);
        }
        return finalDate;
    }

    public int deleteRecord(int feedback_id) {
        System.out.println(feedback_id);
        String query = "DELETE FROM complaint_feedback WHERE feedback_id = " + feedback_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: ComplaintFeedbackModel" + e);
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

    public List<Integer> getComplaintNolist() {

        List<Integer> complaint_list = new ArrayList<Integer>();
        try {

            String query = " select * from complaint_register where active='y'";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
//PreparedStatement ps=connection.prepareStatement("sdf");
//ps.getGeneratedKeys();
            while (rst.next()) {
                complaint_list.add(rst.getInt("complaint_register_id"));
            }


        } catch (Exception e) {
        }
        return complaint_list;
    }
/*
    public int getKeyFeedbackTypeId(String feedback_type) {
        int feedback_type_id = 0;
        String query = "SELECT feedback_type_id FROM feedback_type k where feedback_type=?";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);

            psmt.setString(1, feedback_type);

            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                feedback_type_id = rset.getInt("feedback_type_id");
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintFeedbackModel " + e);
        }
        return feedback_type_id;
    }
*/

    public int getKeyFeedbackTypeId(String complaint_feedback_type) {
        int feedback_type_id = 0;
        String  feedback_type="";
        PreparedStatement psmt=null;
        int rowsAffected = 0;
        String query = "SELECT feedback_type_id FROM feedback_type k where feedback_type=?";
        String query1 = "insert into feedback_type(feedback_type) values(?)";
        String query2= " select feedback_type_id,feedback_type from feedback_type ";

        try {
             psmt = connection.prepareStatement(query2);

          //  psmt.setString(1, feedback_type);

            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                feedback_type = rset.getString("feedback_type");
               if(complaint_feedback_type.equals(feedback_type)){
                  feedback_type_id=rset.getInt("feedback_type_id");
                //  break;
               }
            }// end while loop
         if(feedback_type_id==0){
            psmt = connection.prepareStatement(query1);  
            psmt.setString(1, complaint_feedback_type);
           rowsAffected= psmt.executeUpdate();
         }
        if(rowsAffected>0){
           psmt = connection.prepareStatement(query);
           psmt.setString(1,complaint_feedback_type);
           ResultSet rset2 = psmt.executeQuery();
            while(rset2.next()) {    // move cursor from BOR to valid record.
                feedback_type_id=rset2.getInt("feedback_type_id");
            }//
        }
        } catch (Exception e) {
            System.out.println("Error: ComplaintFeedbackModel " + e);
        }
        return feedback_type_id;
    }

    public int getKeyComplaintRevisionNo(String complaint_no) {
        int complaint_revision_no = 0;
        String query = "SELECT complaint_revision_no FROM complaint_register k where complaint_register_id=? and active='y'";
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, complaint_no);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                complaint_revision_no = rset.getInt("complaint_revision_no");
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintFeedbackModel " + e);
        }
        return complaint_revision_no;
    }

    public int insertRecord(ComplaintFeedback com_complaint_feedback) {
        int rowsAffected = 0;
       String complaint_status;
        String query = null;
        query = " insert into complaint_feedback(feedback_type_id ,feedback_date,complaint_register_id,remark)values(?,?,?,?)";
       String query1="update  complaint_register set complaint_status_id=? where complaint_register_id=? and active='Y'";
         String[] feedbacke_id = com_complaint_feedback.getFeedback_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < feedbacke_id.length; i++) {
                if (com_complaint_feedback.getFeedback_idM()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    //   pstmt.setInt(1, getKeyFeedbackTypeId(com_complaint_feedback.getFeedback_typeM()[i]));
                    pstmt.setInt(1, getKeyFeedbackTypeId(com_complaint_feedback.getFeedback_typeM()[i]));
                    String complaint_feedback_date, s_hour, s_min, s_time;
                    java.sql.Timestamp s_date = null;

                    if (com_complaint_feedback.getFeedback_dateM()[i] != null) {
                        complaint_feedback_date = com_complaint_feedback.getFeedback_dateM()[i];
                        if (com_complaint_feedback.getSubmission_time_hourM()[i] != null && !com_complaint_feedback.getSubmission_time_hourM()[i].isEmpty()) {
                            s_hour = com_complaint_feedback.getSubmission_time_hourM()[i];
                        } else {
                            s_hour = "00";
                        }
                        if (com_complaint_feedback.getSubmission_time_minM()[i] != null && !com_complaint_feedback.getSubmission_time_minM()[i].isEmpty()) {
                            s_min = com_complaint_feedback.getSubmission_time_minM()[i];
                        } else {
                            s_min = "00";
                        }
                        s_time = com_complaint_feedback.getSubmission_timeM()[i];
                        complaint_feedback_date = complaint_feedback_date + " " + s_hour + ":" + s_min + " " + s_time;
                        System.out.println("complaint_feedback_date" + complaint_feedback_date);
                        s_date = setDateTime(complaint_feedback_date);
                        pstmt.setTimestamp(2, s_date);
                        // pstmt.(2, setDateTime(com_complaint_feedback.getFeedback_dateM()[i]));
                        //  pstmt.setDate(2, convertToSqlDate(com_complaint_feedback.getFeedback_dateM()[i]));
                        pstmt.setString(3, com_complaint_feedback.getComplaint_noM()[i]);
                        //pstmt.setInt(4, getKeyComplaintRevisionNo(com_complaint_feedback.getComplaint_noM()[i]));
                        pstmt.setString(4, com_complaint_feedback.getRemarkM()[i]);
                        rowsAffected = pstmt.executeUpdate();
                        if(rowsAffected>0){
                            getDataStringSMS(Integer.parseInt(com_complaint_feedback.getComplaint_noM()[i]));

                        }
                    }  //end inner  loop
                   complaint_status = com_complaint_feedback.getComplaint_statusM()[i];
                   if(rowsAffected>0){
                     if(complaint_status.equals("no")){
                        int complaint_status_id=1;
                         pstmt = connection.prepareStatement(query1);
                         pstmt.setInt(1, complaint_status_id);
                         pstmt.setInt(2, Integer.parseInt(com_complaint_feedback.getComplaint_noM()[i]));
                         //pstmt.setInt(3, getKeyComplaintRevisionNo(com_complaint_feedback.getComplaint_noM()[i]));
                         rowsAffected = pstmt.executeUpdate();                     
                     }
                   if(complaint_status.equals("yes")){
                        int complaint_status_id=4;
                         pstmt = connection.prepareStatement(query1);
                         pstmt.setInt(1, complaint_status_id);
                         pstmt.setInt(2, Integer.parseInt(com_complaint_feedback.getComplaint_noM()[i]));
                         //pstmt.setInt(3, getKeyComplaintRevisionNo(com_complaint_feedback.getComplaint_noM()[i]));
                         rowsAffected = pstmt.executeUpdate();                     
                     }
                   }  
                } // end outer if
            }// end for loop
        } catch (Exception e) {
            System.out.println("Error: ComplaintFeedbackModel" + e);
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

    public int updateRecord(ComplaintFeedback com_complaint_feedback) {
        int rowsAffected = 0;
        String query = " UPDATE complaint_feedback  "
                + " SET feedback_type_id =? ,  feedback_date =?, complaint_register_id = ?,remark=? where feedback_id =? ";

        String[] feedback_id = com_complaint_feedback.getFeedback_idM();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < feedback_id.length; i++) {
                pstmt = connection.prepareStatement(query);
                //      pstmt.setInt(1, getKeyFeedbackTypeId(com_complaint_feedback.getFeedback_typeM()[i]));
                pstmt.setInt(1, getKeyFeedbackTypeId(com_complaint_feedback.getFeedback_typeM()[i]));
                String complaint_feedback_date, s_hour, s_min, s_time;
                java.sql.Timestamp s_date = null;

                if (com_complaint_feedback.getFeedback_dateM()[i] != null) {
                    complaint_feedback_date = com_complaint_feedback.getFeedback_dateM()[i];
                    if (com_complaint_feedback.getSubmission_time_hourM()[i] != null && !com_complaint_feedback.getSubmission_time_hourM()[i].isEmpty()) {
                        s_hour = com_complaint_feedback.getSubmission_time_hourM()[i];
                    } else {
                        s_hour = "00";
                    }
                    if (com_complaint_feedback.getSubmission_time_minM()[i] != null && !com_complaint_feedback.getSubmission_time_minM()[i].isEmpty()) {
                        s_min = com_complaint_feedback.getSubmission_time_minM()[i];
                    } else {
                        s_min = "00";
                    }
                    s_time = com_complaint_feedback.getSubmission_timeM()[i];
                    complaint_feedback_date = complaint_feedback_date + " " + s_hour + ":" + s_min + " " + s_time;
                    System.out.println("complaint_feedback_date" + complaint_feedback_date);
                    s_date = setDateTime(complaint_feedback_date);
                    pstmt.setTimestamp(2, s_date);
                    //   pstmt.setDate(2, convertToSqlDate(com_complaint_feedback.getFeedback_dateM()[i]));
                    pstmt.setString(3, com_complaint_feedback.getComplaint_noM()[i]);
                    //pstmt.setInt(4, getKeyComplaintRevisionNo(com_complaint_feedback.getComplaint_noM()[i]));
                    pstmt.setString(4, com_complaint_feedback.getRemarkM()[i]);
                    pstmt.setInt(5, Integer.parseInt((com_complaint_feedback.getFeedback_idM()[i]).trim()));
                    rowsAffected = pstmt.executeUpdate();
                    if(rowsAffected>0){
                         getDataStringSMS(Integer.parseInt(com_complaint_feedback.getComplaint_noM()[i]));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintFeedbackModel" + e);
        }
        if (rowsAffected > 0) {

            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public void getDataStringSMS(int compaint_no) {
        String msg="";
         String subject="Complaint Feedback";
         ComplaintRegisterModel crm = new ComplaintRegisterModel();
         crm.setConnection(connection);
        try {
            String query = "select k1.key_person_name , mobile_no1,email_id1 , cf.complaint_register_id , feedback_type, cr.posted_by "
                    + "from complaint_register cr , complaint_feedback cf , key_person k1, feedback_type ft  "
                    + " where cr.complaint_register_id = cf.complaint_register_id and cr.active='Y'"
                    //+ " and  cr.complaint_revision_no= cf.complaint_revision_no"
                    + " and cr.active='Y' and k1.key_person_id= cr.posted_by "
                    + " and ft.feedback_type_id = cf.feedback_type_id and cf.complaint_register_id =? and cr.active='Y' ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, compaint_no);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                String feedback = rset.getString("feedback_type");
                String key_person_name = rset.getString("key_person_name");
                String mobile_no = rset.getString("mobile_no1");
                String email_id = rset.getString("email_id1");
                int posted_by  = rset.getInt("posted_by");
                String mail_direction = "outgoing";

                msg="Dear "+key_person_name+"\n"+
                        feedback+" of "+"Complaint No. :"+compaint_no;
                    //sendSms(mobile_no, msg);
                    sendMail(msg, subject, email_id );
                    crm.insertMailRecords(posted_by, posted_by, mail_direction, compaint_no + "");
            }

        } catch (Exception e) {
            System.out.println("Error in get  getDataString" + e);
        }

    }

    public String sendSms(String numberStr1, String messageStr1) {
        String result = "";
        try {

            String query = "SELECT si.user_name, si.user_password, si.sender_id FROM sms_info si ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            String host_url = "http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
            String user_name = rset.getString("user_name");        // e.g. "jpss1277@gmail.com"
            String user_password = rset.getString("user_password"); // e.g. "8826887606"
            String sender_id = java.net.URLEncoder.encode(rset.getString("sender_id"), "UTF-8");         // e.g. "TEST+SMS"

            System.out.println("messageStr1 is =" + messageStr1);
            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");

            String queryString = "user=" + user_name + ":" + user_password + "&senderID=" + sender_id + "&receipientno=" + numberStr1 + "&msgtxt=" + messageStr1;
            String url = host_url + queryString;
            result = callURL(url);
            System.out.println("SMS URL: " + url);
        } catch (Exception e) {
            result = e.toString();
            System.out.println("SMSModel sendSMS() Error: " + e);
        }
        return result;
    }

    private String callURL(String strURL) {
        String status = "";
        try {
            java.net.URL obj = new java.net.URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            status = httpReq.getResponseMessage();
        } catch (MalformedURLException me) {
            status = me.toString();
        } catch (IOException ioe) {
            status = ioe.toString();
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }

   public void sendMail(String bodyText1, String subject, String assigned_for_email_id1) {
        MailSmsCredentialModel mailSmsCred = new MailSmsCredentialModel(connection);
        MailBean mailBean = mailSmsCred.getMailCredentialUser();
        SendMailSmsModel sendMailSms = new SendMailSmsModel();
        List<String> setToEmailList = new ArrayList<String>();
        SmsBean smsBean = mailSmsCred.getSmsCredentialUser();

        setToEmailList.add(assigned_for_email_id1);
        mailBean.setToEmailList(setToEmailList);
        mailBean.setSubject(subject);
        mailBean.setBodyText(bodyText1);
        sendMailSms.sendMail(mailBean);

    }
    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    //  public String getComplaintNoDetails(){
    //   return ;
    // }
    public String getComplaintNoDetails(int complaint_no) {
        String response_data = "";
        try {
            String query = "Select  complaint_status , complaint_type , complaint_sub_type,concat_ws(' ',p.pole_no,', ', spd.switching_point_name) as pole_no, "
                    //+ " concat(site_name , ' , ' ,address1) as site_name ,"
                    + " k1. key_person_name as posted_person, k2.key_person_name as assigned_person"
                    + " from complaint_register cr   left join( key_person k2 ) on k2. key_person_id = cr.assinged_for "
                    + " ,complaint_status cs ,complaint_type ct, complaint_sub_type cst, pole p,  key_person k1, switching_point_detail spd "
                    + " where spd.switching_point_detail_id = p.switching_point_detail_id AND spd.active='Y' AND p.pole_id=cr.pole_id AND p.active='Y' "
                    + " and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_sub_type_id = cst.complaint_sub_type_id AND ct.complaint_type_id = cst.complaint_type_id and k1.key_person_id= cr.posted_by   and cr.active ='Y'  and cr.complaint_register_id=? ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, complaint_no);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                response_data = ""//rset.getString("advertising_sub_type1_name") + ", " + rset.getString("advertising_sub_type2_name") + " ,"
                        + rset.getString("complaint_status") + " ," + rset.getString("complaint_type") + " ," + rset.getString("complaint_sub_type") + " ,"
                        + rset.getString("pole_no") + " ," + rset.getString("posted_person") + " ,"
                        + rset.getString("assigned_person");
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel --getComplaintNoList-- " + e);
        }
        return response_data;
    }

    public int getNoOfRows(int searchTypeOfComplaintNo, String seacrh_feedback_date, String assigned_for) {
        int noOfRows = 0;
        java.sql.Date s_date = null;
        if (seacrh_feedback_date != null) {
            try {
                s_date = convertToSqlDate(seacrh_feedback_date);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            //  System.out.println("s_date in side curly braces=" + s_date);
        }
        try {
           String query = "select count(*)"
                + "from "
                + " complaint_feedback cf,feedback_type fd ,complaint_register AS cr, key_person AS kp"
                + " where cf.feedback_type_id=fd.feedback_type_id "
                + " AND cf.complaint_register_id=cr.complaint_register_id AND cr.active='Y' "
                //+ " AND cf.complaint_revision_no=cr.complaint_revision_no "
                + " AND cr.assinged_for=kp.key_person_id "
                + " and if(" + searchTypeOfComplaintNo + " =0, cf.complaint_register_id like '%%',cf.complaint_register_id=?) "
                + " and if('" + assigned_for + "'='',(kp.key_person_name like'%' or kp.key_person_name is null), kp.key_person_name='" + assigned_for + "') "
                //  + " and if('" + s_date + "'='',feedback_date like '%%', feedback_date=?) "
                + " and if(" + s_date + " is null, feedback_date like '%%',date_format(feedback_date,'%Y-%m-%d')='" + s_date + "')";




            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, searchTypeOfComplaintNo);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel " + e);
        }
        System.out.print("no of rows=" + noOfRows);
        return noOfRows;

    }

    public List<String> getComplaintNoList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        // String query = "SELECT complaint_no FROM complaint_feedback ";
        String query = " select distinct(complaint_register_id) from complaint_feedback  ORDER BY complaint_register_id";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int complaint_no = rset.getInt("complaint_register_id");

                String complaint_no_list = Integer.toString(complaint_no);
                //  if (feedback_type.toUpperCase().startsWith(q.toUpperCase())) {
                list.add(complaint_no_list);
                count++;
                //    }
            }
            if (count == 0) {
                list.add("No Such Type Of Compalint Number  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:ComplaintFeedbackModel --getComplaintNoList-- " + e);
        }
        return list;
    }

    public List<String> getAssignedForSearchlist(String q) {

        List<String> assigned_for_list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        try {
            String query = " select kp.key_person_name from "
                    + " complaint_feedback cf,feedback_type fd, complaint_register AS cr, key_person AS kp "
                    + " where cr.assinged_for=kp.key_person_id "
                    + " AND cf.feedback_type_id=fd.feedback_type_id "
                    + " AND cf.complaint_register_id=cr.complaint_register_id group by key_person_name";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                assigned_for_list.add(rst.getString("key_person_name"));
                count++;
            }
            if (count == 0) {
                assigned_for_list.add("No Such Assigned_for  exists.....");
            }
        } catch (Exception e) {
        }
        return assigned_for_list;
    }

    public List<ComplaintFeedback> showData(int lowerLimit, int noOfRowsToDisplay, int searchTypeOfComplaintNo, String assigned_for, String seacrh_feedback_date) {
        List<ComplaintFeedback> list = new ArrayList<ComplaintFeedback>();

        //  String query ="select cf.feedback_id ,cf.complaint_no,fd.feedback_type,date_format(feedback_date,'%d-%m-%Y') as feedback_date,date_format(feedback_date,'%h') as feedback_date_hr,date_format(feedback_date,'%i') as feedback_date_min,date_format(feedback_date,'%p') as feedback_date_time,cf.complaint_revision_no,cf.remark from  complaint_feedback cf,feedback_type fd where cf.feedback_type_id=fd.feedback_type_id"
        //  +"  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        //System.out.println("Search Complint No=" + seacrh_feedback_date);

        //   System.out.println("User feedback date=" + seacrh_feedback_date);
        java.sql.Date s_date = null;
        if (seacrh_feedback_date != null) {
            try {
                s_date = convertToSqlDate(seacrh_feedback_date);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            System.out.println("s_date in side curly braces=" + s_date);
        }
        System.out.println("s_date=" + s_date);
        String query = "select cf.feedback_id ,cf.complaint_register_id,fd.feedback_type,key_person_name,"
                + " date_format(feedback_date,'%d-%m-%Y') as feedback_date,"
                + " date_format(feedback_date,'%h')"
                + " as feedback_date_hr,date_format(feedback_date,'%i') as feedback_date_min,"
                + "date_format(feedback_date,'%p') as feedback_date_time,cf.remark, cr.complaint_status_id "
                + "from "
                + " complaint_feedback cf,feedback_type fd ,complaint_register AS cr, key_person AS kp"
                + " where cf.feedback_type_id=fd.feedback_type_id "
                + " AND cf.complaint_register_id=cr.complaint_register_id AND active='Y' "
                //+ " AND cf.complaint_revision_no=cr.complaint_revision_no "
                + " AND cr.assinged_for=kp.key_person_id "
                + " and if(" + searchTypeOfComplaintNo + " =0, cf.complaint_register_id like '%%',cf.complaint_register_id=?) "
                + " and if('" + assigned_for + "'='',(kp.key_person_name like'%' or kp.key_person_name is null), kp.key_person_name='" + assigned_for + "') "
                //  + " and if('" + s_date + "'='',feedback_date like '%%', feedback_date=?) "
                + " and if(" + s_date + " is null, feedback_date like '%%',date_format(feedback_date,'%Y-%m-%d')='" + s_date + "')"
                + " Order by cf.complaint_register_id desc"
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;


        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, searchTypeOfComplaintNo);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                ComplaintFeedback com_complaint_feedback = new ComplaintFeedback();
                com_complaint_feedback.setFeedback_id(rset.getInt("feedback_id"));
                com_complaint_feedback.setComplaint_no(rset.getInt("complaint_register_id"));
                com_complaint_feedback.setFeedback_type(rset.getString("feedback_type"));
                com_complaint_feedback.setFeedback_date(rset.getString("feedback_date"));
                com_complaint_feedback.setSubmission_time_hour(rset.getString("feedback_date_hr"));
                com_complaint_feedback.setSubmission_time_min(rset.getString("feedback_date_min"));
                com_complaint_feedback.setSubmission_time(rset.getString("feedback_date_time"));
                //com_complaint_feedback.setComplaint_revision_no(rset.getInt("complaint_revision_no"));
                com_complaint_feedback.setRemark(rset.getString("remark"));
                com_complaint_feedback.setAssigned_for(rset.getString("key_person_name"));
                com_complaint_feedback.setComplaint_status_id(rset.getInt("complaint_status_id"));

                // si.setBalance_unit(getBalanceUnit(rs.getInt("unit_id")));
                list.add(com_complaint_feedback);
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintFeedbackModel" + e);
        }
        return list;
    }

    public String getAssignedComplaintNolist(String assigned_complaint) {

        String assigned_complaint_list = "";
        try {
           /* String query = " select distinct(cr.complaint_register_id) as complaint_no from "
                    + " complaint_feedback cf,feedback_type fd, complaint_register AS cr, key_person AS kp "
                    + " where cr.assinged_for=kp.key_person_id "
                    + " AND cf.feedback_type_id=fd.feedback_type_id "
                    + " AND cf.complaint_no=cr.complaint_register_id "
                    + " and kp.key_person_name= ?  and active='Y' ";*/
           String query = "SELECT  cr.complaint_register_id as complaint_no FROM key_person kp,complaint_register  cr  where cr.assinged_for=kp.key_person_id and active='y'"
                 +  "  and kp.key_person_name= ? ";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, assigned_complaint);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                assigned_complaint_list = assigned_complaint_list + "," + rset.getString("complaint_no");
            }
        } catch (Exception e) {
        }
        return assigned_complaint_list;
    }
    /*  public List<String> getAssignedComplaintNolist(String assigned_complaint) {

    List<String> assigned_complaint_list = new ArrayList<String>();
    try {
    String query = " select cr.complaint_register_id as complaint_no from "
    + " complaint_feedback cf,feedback_type fd, complaint_register AS cr, key_person AS kp "
    + " where cr.assinged_for=kp.key_person_id "
    + " AND cf.feedback_type_id=fd.feedback_type_id "
    + " AND cf.complaint_no=cr.complaint_register_id "
    + " and kp.key_person_name= ?  and active='Y' ";

    PreparedStatement pstmt = connection.prepareStatement(query);
    pstmt.setString(1, assigned_complaint);
    ResultSet rset = pstmt.executeQuery();

    while (rset.next()) {
    assigned_complaint_list.add(rset.getString("complaint_no"));
    }
    } catch (Exception e) {
    }
    return assigned_complaint_list;
    }*/
public byte[] generateComplaintFeedbackReportPdf(String jrxmlFilePath, String assigned_for, int complaint_no) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        
        mymap.put("assigned_for", assigned_for);
        mymap.put("complaint_no", complaint_no);        
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("Error: in bookingViewController generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
public ByteArrayOutputStream generateComplaintFeedbackReportExcel(String jrxmlFilePath,String assigned_for, int complaint_no) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
         mymap.put("assigned_for", assigned_for);
        mymap.put("complaint_no", complaint_no); 
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jp = JasperFillManager.fillReport(compiledReport, mymap, connection);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in ComplaintRegister Controller generatReport() JRException: " + e);
        }
        return baos;
    }

}
