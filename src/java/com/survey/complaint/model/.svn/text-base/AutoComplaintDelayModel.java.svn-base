package com.survey.complaint.model;

import com.survey.mail.MailBean;
import com.survey.mail.SmsBean;
import com.survey.util.MailSmsCredentialModel;
import com.survey.util.SendMailSmsModel;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;


public class AutoComplaintDelayModel  extends TimerTask{
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private Connection connection;  
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private int hd_id = 0;
    private String hd_name = "";
    private String hd_emailID = "";
    private String hd_mobileNo = "";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("AutoComplaintDelayModel setConnection() Error: " + e);
        }
    }
/*
     public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("AuotoComplaintDelayModel setConnection() Error: " + e);
        }
    }
     */
    public boolean getComplaintSendSmsMailStatus() {
        setConnection();
        String send_status  = "No";
        try {
            String query = " select send_status  from complaint_messages   where send_date =curdate() ";
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            if (resultSet.next()) {
                send_status  = resultSet.getString("send_status");
            }

        //    connection.close();
        } catch (Exception e) {
            System.out.println("Error in AutoComplaintDelayModel  getComplaintSendSmsMailStatus()" + e);
        }
        return send_status.equals("No") ? true : false;
    }

    public boolean isLikelyExtended() {
        boolean result = false;
        try {
            String query ="SELECT complaint_register_id,if(count(*)>0,'true', 'false'  ) as result "
                + " FROM complaint_register cr,complaint_status cs where cr.active='Y' and  complition_date <=SUBDATE(curdate(),0) and "
                +" cs.complaint_status_id =2";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                result = rst.getBoolean("result");
            }
            rst.close();
        } catch (Exception e) {
            System.out.println("Error in isLikelyExtended" + e);
        }
        return result;
    }
    public  void  run(){
    try{
      //   setConnection();
        synchronized (this) {
            if (isLikelyExtended()) {
                autoSendSmsAndMail();
                //   List list =autoSendSmsAndMail();
              //  Iterator itr = list.iterator();
            }
    }
    }
         catch(Exception e){
        System.out.println("Erroor in AutoComplaintDelayModel run" +e);
    }

}
//autoSendSmsAndMail()
  public void autoSendSmsAndMail() {
       int rowsAffected = 0;
        String subject="Complaint Delayed";
       // String bodyText=" ";
        String messages=" "; 
        //String  bodyText1=" ";
      //  int count;
        int complaint_messages=0;
        String complaint_type="";
        String posted_by_mob_no=" ";
        String assinged_for_mob_no=" ";
        String posted_by_email_id=" ";
        String assinged_for_email_id=" ";
        int complaint_no=0, posted_by = 0, assigned_for = 0;
        int complaint_messages_complaint_no=0;
        int tempComplaintNo = 0;
        int complaint_id = 0;
        PreparedStatement pstmt = null;
      //  List list = new ArrayList();
        try {
            /*String query =  "SELECT complaint_register_id as complaint_no,cs.complaint_status,ct.complaint_type,ct.complaint_type_eng,concat(site_name , ' , ' ,address1) as site_name,kp.key_person_name as posted_by,kp.key_person_name as assinged_for,kp.mobile_no1 as posted_by_mob_no,k2.mobile_no1 as assinged_person_mob_no"
                          + " ,k2.email_id1  as assinged_person_email_id,kp.email_id1  as posted_by_email_id FROM complaint_register cr left join( key_person k2 ) on k2. key_person_id = cr.assinged_for,complaint_status cs,complaint_type ct,ad_site ad,key_person kp where cr.active='Y'"
                          +" and  complition_date <=SUBDATE(curdate(),2) and cs.complaint_status_id= cr.complaint_status_id and cs.complaint_status_id =2 and cr.complaint_type_id=ct.complaint_type_id and ad.site_id=cr.site_id  and kp.key_person_id=cr.posted_by ";*/
            /*String query = "SELECT complaint_register_id as complaint_no,cs.complaint_status,cst.complaint_sub_type,cst.complaint_sub_type_eng,pole_no,kp.key_person_name as posted_by,kp.key_person_name as assinged_for,kp.mobile_no1 as posted_by_mob_no,k2.mobile_no1 as assinged_person_mob_no"
                    + " ,k2.email_id1  as assinged_person_email_id,kp.email_id1  as posted_by_email_id FROM complaint_register cr left join( key_person k2 ) on k2. key_person_id = cr.assinged_for,complaint_status cs,complaint_sub_type cst,pole p,key_person kp where cr.active='Y'"
                    + " and  complition_date <=SUBDATE(curdate(),0) and cs.complaint_status_id= cr.complaint_status_id and cs.complaint_status_id =2 and cr.complaint_sub_type_id=cst.complaint_sub_type_id and p.pole_id=cr.pole_id  and kp.key_person_id=cr.posted_by";*/
            String query = "SELECT complaint_register_id as complaint_no,cs.complaint_status,cst.complaint_sub_type,cst.complaint_sub_type_eng,pole_no,kp.key_person_name as posted_by,k2.key_person_name as assinged_for,kp.mobile_no1 as posted_by_mob_no,"
                    + " k2.mobile_no1 as assinged_person_mob_no, k2.email_id1  as assinged_person_email_id,kp.email_id1  as posted_by_email_id, cr.posted_by as posted_by_id, k2.key_person_id as assigned_for_id "
                    + " FROM complaint_register cr, complaint_status cs,complaint_sub_type cst,pole p,key_person kp, key_person k2, (select * from mail_records order by mail_record_id desc) AS mr "
                    + " where cr.active='Y' AND mr.mail_type_id = 3 AND cr.posted_by != mr.key_person_id AND mr.mail_type_value = cr.complaint_register_id AND k2.key_person_id = mr.key_person_id"
                    //+ " AND ph.designated_person_id = mr.key_person_id  AND k2.key_person_id = ph.higher_designated_person_id "
                    + " and complition_date <= SUBDATE(curdate(),0) and cs.complaint_status_id= cr.complaint_status_id and cs.complaint_status_id <= 2 "
                    + " and cr.complaint_sub_type_id=cst.complaint_sub_type_id and p.pole_id=cr.pole_id  and kp.key_person_id=cr.posted_by "
                    + " group by mail_type_value";
                    //+ " ORDER BY complaint_no";

      //     String query1= "SELECT  complaint_no FROM complaint_messages";
         //  String query2= "SELECT  count(*) FROM complaint_messages";
           String query3= "insert into complaint_messages(complaint_no,send_date,send_status) values(?,curdate(),'yes')";
           ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
             //   list.add(rst.getString("complaint_no ") + "," + rst.getString("assinged_for") + "," + rst.getString("posted_by"));
                   complaint_no=rst.getInt("complaint_no");
                   
                   posted_by_mob_no =rst.getString("posted_by_mob_no");
                   assinged_for_mob_no =rst.getString("assinged_person_mob_no");
                   posted_by_email_id =rst.getString("posted_by_email_id");
                   assinged_for_email_id =rst.getString("assinged_person_email_id");
                   complaint_type=rst.getString("complaint_sub_type_eng");
                   posted_by = rst.getInt("posted_by_id");
                   assigned_for = rst.getInt("assigned_for_id");
                   int higher_designated_person_id = getHigherDesignationID(assigned_for);
                       if(complaint_type==null)
                        complaint_type="";


                        messages ="Your complaint is not underprocess "
                            + "\n  Complaint Type- " + complaint_type
                            + "\n  Complaint no-  " +rst.getInt("complaint_no")
                            + "\n  Pole_no- " + rst.getString("pole_no");

                        String mail_direction = "outgoing";
                          


            
       /*     ResultSet rst2 = connection.prepareStatement(query2).executeQuery();
            if(rst2.next()) {
               complaint_messages= Integer.parseInt(rst2.getString(1));;
            }  */
          //  ResultSet rst1 = connection.prepareStatement(query1).executeQuery();
          //  count=1;
         //   while(rst1.next()) {
                     
           /*         complaint_messages_complaint_no=rst1.getInt("complaint_no");
                     if(complaint_no==complaint_messages_complaint_no)
                     {
                         break;
                     }
                     if(count==complaint_messages && complaint_no!=complaint_messages_complaint_no)
                     {  */
                      //sendSms(posted_by_mob_no,messages);
                      //sendSms(assinged_for_mob_no,messages);
                      //sendMail(messages, subject, posted_by_email_id);
                      //insertMailRecords(posted_by,posted_by, mail_direction, complaint_no + "");
                      if(higher_designated_person_id == 0){
                          sendMail(messages, subject,assinged_for_email_id);
                          insertMailRecords(assigned_for,assigned_for, mail_direction, complaint_no + "");
                      }else {
                      sendMail(messages, subject,hd_emailID);
                      insertMailRecords(hd_id,hd_id, mail_direction, complaint_no + "");
                }
                      //sendMail_Posted_by(bodyText, subject, post_by_email_id1);
                     
                       // sendMail_Assigned_For(bodyText, subject, assigned_for_email_id1);
                     

                    pstmt = connection.prepareStatement(query3);
                    pstmt.setInt(1,complaint_no); 
                    rowsAffected = pstmt.executeUpdate();

                    // }
                 //    count++;
                  //   }
                     }
           // rst.close(); not use
        } catch (Exception e) {
            System.out.println("Error in autosendSms Mail" +e);
        }
       // return list;
    }
  public int getHigherDesignationID(int designation_id){
      int hd_id = 0;
      String query = "SELECT kp.key_person_id, kp.key_person_name, kp.email_id1, kp.mobile_no1 FROM person_hierarchy ph, key_person kp "
              + " WHERE kp.key_person_id = ph.higher_designated_person_id AND designated_person_id = " + designation_id;
      try{
          ResultSet rs = connection.prepareStatement(query).executeQuery();
          if(rs.next()){
              hd_id = rs.getInt("higher_designation_id");
              this.hd_id = hd_id;
              hd_name = rs.getString("key_person_name");
              hd_emailID = rs.getString("email_id1");
              hd_mobileNo = rs.getString("mobile_no1");
          }
      }catch(Exception ex){

      }
      return hd_id;
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
  public String sendSms(String numberStr, String messageStr) {
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


            messageStr = java.net.URLEncoder.encode(messageStr, "UTF-8");
            String queryString = "user=" + user_name + ":" + user_password + "&senderID=" + sender_id + "&receipientno=" + numberStr + "&msgtxt=" + messageStr;
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

   public boolean insertMailRecords(int user_id, int key_person_id, String mail_direction,String payment_req_no) {
        boolean isUpdated = false;
        int count = 0, mail_record_id = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        System.out.println(dateFormat.format(cal.getTime()));
        //RequisitionGenerationModel reqModel = new RequisitionGenerationModel();
        //reqModel.setConnection(connection);
        String check_query = "select count(*) as count,mail_record_id from mail_records as m,mail_status as ms "
                + "where m.mail_status_id=ms.mail_status_id and ms.mail_status IN('NO','Partial') "
                + "and m.mail_direction='incoming' and m.active='Y' and m.mail_type_value='" + payment_req_no + "' ";
        String query = " INSERT INTO mail_records(mail_type_value, key_person_id, "//division_keyperson_id
                + "mail_status_id, mail_type_id "//bill_month,
                + " ,mail_date_time,mail_direction,created_by,description) "
                + "  VALUES(?, ?, ?, ?, ?, ? ,? ,?)";
        String revisequery = "INSERT INTO mail_records (mail_type_value, key_person_id,"//division_keyperson_id
                + "created_by,mail_status_id,mail_type_id,bill_month,mail_direction,mail_date_time,"
                + "type_of_transaction,description,mail_revision_no)"
                + " select ?, key_person_id,created_by,mail_status_id,mail_type_id,bill_month"//division_keyperson_id
                + ", mail_direction,?,type_of_transaction,description,"
                + "(Select max(mail_revision_no)+1 from mail_records where mail_type_value='" + payment_req_no + "' )"
                + "as A from mail_records where mail_record_id=? ";
        String updateQuery = "UPDATE mail_records Set active='N' "
                + "where mail_type_value='" + payment_req_no + "' and mail_direction='" + mail_direction + "' and mail_revision_no=?";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(check_query);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            count = rs.getInt("count");
            mail_record_id = rs.getInt("mail_record_id");
        } catch (Exception e) {
        }
        try {
            if (count > 0) {
                pstmt = connection.prepareStatement(revisequery);
             //   pstmt.setInt(1, mail_record_id);
                pstmt.setString(1, payment_req_no);
                pstmt.setString(2, date);
                pstmt.setInt(3, mail_record_id);
                isUpdated = pstmt.executeUpdate() > 0 ? true : false;
                if (isUpdated == true) {
                    System.out.println("Data is Revised Successfully in mail_records");
                    pstmt = connection.prepareStatement(updateQuery);
                    pstmt.setInt(1, getMailRevisionNo(payment_req_no));
                    isUpdated = pstmt.executeUpdate() > 0 ? true : false ;
                    if (isUpdated == true) {
                        System.out.println("Mail Records updated Successfully");
                    }
                }
            } else {
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, payment_req_no);
                pstmt.setInt(2, key_person_id);
                //pstmt.setString(3, bill_month);
                pstmt.setInt(3, getMailStatusId("Yes"));
                pstmt.setInt(4, getMailTypeId("Complaint Mail"));
                pstmt.setString(5, date);
                pstmt.setString(6, mail_direction);
                pstmt.setInt(7, user_id);
                pstmt.setString(8, "This email is for Complaint");
                isUpdated = pstmt.executeUpdate() > 0 ? true : false;
            }

        } catch (Exception e) {
            System.out.println("ComplaintRegisterModel insertMailRecords error -- " + e);
        }
        return isUpdated;
    }

   public int getMailRevisionNo(String req_no) {
        int mail_revision_no = 0;
        try {
            String query = "select max(mail_revision_no)-1 as mail_revision_no "
                    + "from mail_records where mail_type_value='" + req_no + "'";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
              mail_revision_no = rs.getInt("mail_revision_no");
            }

        } catch (Exception e) {
            System.out.println("ComplaintRegisterModel ExcelModel exception : " + e);
        }
        return mail_revision_no;
    }

  public int getMailStatusId(String mail_status) {
        int mail_status_id = 0;
        String query = " SELECT mail_status_id FROM mail_status WHERE mail_status = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mail_status);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                mail_status_id = rset.getInt("mail_status_id");
            }
        } catch (Exception e) {
            System.out.println("getMailStatusId() ComplaintRegisterModel ERROR - " + e);
        }
        return mail_status_id;
    }

    public int getMailTypeId(String mail_type) {
        int mail_type_id = 0;
        String query = " SELECT mail_type_id FROM mail_type WHERE mail_type = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, mail_type);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                mail_type_id = rset.getInt("mail_type_id");
            }
        } catch (Exception e) {
            System.out.println("getMailStatus() ComplaintRegisterModel ERROR - " + e);
        }
        return mail_type_id;
    }

public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPasswrod() {
        return db_userPasswrod;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPasswrod = db_userPasswrod;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    } 
}
