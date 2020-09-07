/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.model;

import com.survey.complaint.tableClasses.ComplaintRegister;
import com.survey.mail.SendMailModel;
import com.survey.mail.SmsBean;
import com.survey.mail.MailBean;
import com.survey.util.SendMailSmsModel;
import com.survey.util.MailSmsCredentialModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
//import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Shruti
 */
public class ComplaintRegisterModel {

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

    public void setConnection(Connection con) {
        connection = con;
    }

    /*public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ComplaintRegisterModel setConnection() Error: " + e);
        }
    }*/

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
  public  String getAdSubSite(String site_name){
     String response_ad_sub_site=" ";
   //   List<String> list = new ArrayList<String>();
     System.out.println("model ad subsite name"+site_name);
      
     try {
      String query =" select ss.sub_site_id,concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) as name from ad_site  ad,ad_sub_site ss,ad_mounting am "
               +" where "
               + " ad.site_id=ss.site_id and ss.mounting_id=am.mounting_id"
                + " and  concat(site_name , ' , ' ,address1)  = ?";


            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, site_name);

            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
              response_ad_sub_site +=rset.getInt("sub_site_id")+"="+ rset.getString("name")+"#";
            System.out.println("response_ad_sub_site=" +response_ad_sub_site);
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
      // System.out.println("response_ad_sub_site=" +response_ad_sub_site);
       return  response_ad_sub_site;
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
        String query =" SELECT key_person_name "
                     + "  FROM key_person k, org_office o,organisation_name oc, organisation_map om,organisation_type ot,designation ds"
                     + " where k.org_office_id=o.org_office_id "
                     + " and o.organisation_id= oc.organisation_id  and oc.organisation_id= om.organisation_id "
                     +"  and om.organisation_type_id=ot.organisation_type_id and  org_type_name='Home' and k.designation_id=ds.designation_id and ds.designation='Technician' ORDER BY key_person_name ";
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

   public String getAdSubSiteName(String sub_site_name){
      System.out.println("sub_site_name="+sub_site_name);
       String response_ad_sub_site=" ";
     String query = "select sub_site_id,name from ad_sub_site as ss,ad_mounting am "
                + "where "
               + "  ss.mounting_id=am.mounting_id"
                + " concat_ws( ' ',name,'  ',location_name,' , ', mounting_name) = ? ";
      
       
    try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, sub_site_name);
           
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                response_ad_sub_site +=rset.getInt("sub_site_id")+"="+ rset.getString("name")+"#";
            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel " + e);
        }
       
       return  response_ad_sub_site;     
   }
 


  public String getSiteName(String  site_name){
       String response_data="" ;
      try{
       String query=" select concat(site_name , ' , ' ,address1) as name from  ad_site  where  site_name=? ";
         PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, site_name);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
               response_data=rset.getString("name");

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

  /*  public int deleteRecord(int complaint_register_id) {
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
*/
    public int cancelRecord(int complaint_register_id) {
        //String query = "DELETE FROM complaint_register WHERE complaint_register_id = " + complaint_register_id;
        String query = " update complaint_register set active='N',complaint_status_id=3"
                            + " where complaint_register_id =" + complaint_register_id + "   and "
                            + " active = 'Y' ";
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

    public int insertRecord(ComplaintRegister com_reg)// throws SQLException
    {
        int rowsAffected = 0;
        String query = null;
        query = " insert into complaint_register(complaint_sub_type_id, complaint_status_id, posted_by, posted_date, complaint_date, assinged_for, "// site_id,
                + " remark , complition_date, pole_id, assign_date) "
                + " values(?, ?, ?, current_date(), ?, ?, ?, ADDDATE(? ,1),?, IF(?=0, null, current_date()))";
        String[] complaint_reg_no = com_reg.getComplaint_register_no();
        PreparedStatement pstmt = null;
        int count = 0;
     //   boolean autocommit = false;
      //  connection.setAutoCommit(false);
        String messages=" ";
        String complaint_type=" ";
        String subsite_name=" ";
        String mob_no=" ", email_id = "";
        try {
            for (int i = 0; i < complaint_reg_no.length; i++) {
                if (com_reg.getComplaint_register_no()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    //pstmt.setInt(1, getsite_id(com_reg.getSite_name()[i]));
                    pstmt.setInt(1, Integer.parseInt((com_reg.getComplaint_type()[i].trim()).split("#")[0]));
                    pstmt.setInt(2, Integer.parseInt(com_reg.getStatus()[i].trim()));
                    pstmt.setInt(3, getkey_person_id(com_reg.getPosted_by()[i]));
                    pstmt.setDate(4, convertToSqlDate(com_reg.getComplaint_date()[i]));
                    if (com_reg.getAssigned_for()[i] != null && !com_reg.getAssigned_for()[i].isEmpty()) {
                        pstmt.setInt(5, getkey_person_id(com_reg.getAssigned_for()[i]));
                    } else {
                        pstmt.setNull(5, java.sql.Types.INTEGER);
                    }
                    pstmt.setString(6, com_reg.getRemark()[i]);
                    pstmt.setDate(7, convertToSqlDate(com_reg.getComplaint_date()[i]));
                    
                   if(com_reg.getAd_sub_site_name()[i].equals("null") ||com_reg.getAd_sub_site_name()[i].isEmpty()){
                   // pstmt.setInt(9,2);
                        subsite_name=null;
                   }
                  else {
                    pstmt.setInt(8, getPoleId(com_reg.getAd_sub_site_name()[i]));
                    }
                    pstmt.setInt(9, getkey_person_id(com_reg.getAssigned_for()[i]));
                    rowsAffected = pstmt.executeUpdate();
                   if(rowsAffected>0){
     /*String query1= "select  kp.key_person_id, complaint_register_id,kp.key_person_name, p.pole_no, kp.mobile_no1,cst.complaint_sub_type, kp.email_id1"
                  + " from complaint_register cr,key_person kp,complaint_sub_type cst,pole p"
                  + " where  cr.complaint_register_id=(select  max(complaint_register_id) from complaint_register) "
                  + " and kp.key_person_name= ?  and cr.complaint_sub_type_id=cst.complaint_sub_type_id and p.pole_id=cr.pole_id AND p.active='Y' ";*/
    String query1 = "";
     if (com_reg.getAssigned_for()[i] != null && !com_reg.getAssigned_for()[i].isEmpty()){
         query1= "select cr.posted_by, cr.assinged_for, cr.complaint_register_id,p.pole_no,kp.key_person_name as posted_by_name,kp1.key_person_name as assigned_for_name,"//concat(site_name , ' , ' ,address1) as site_name,
                    + " kp.mobile_no1 as post_by_mob,kp1.mobile_no1 as assigned_for_mob,"
                     + " kp.email_id1 as post_by_email_id1,kp1.email_id1 as "
                   + " assigned_for_email_id1,cst.complaint_sub_type_eng from complaint_register cr,key_person kp,key_person kp1,complaint_sub_type cst, pole p "
                    + " where p.pole_id=cr.pole_id AND p.active='Y' AND cr.complaint_sub_type_id=cst.complaint_sub_type_id and cr.active='Y' and  cr.posted_by=kp.key_person_id and "
                    + " cr.assinged_for=kp1.key_person_id and cr.complaint_register_id=(select  max(complaint_register_id) from complaint_register) " ;
     } else {
         query1 = "select cr.posted_by, cr.assinged_for, cr.complaint_register_id,p.pole_no,kp.key_person_name as posted_by_name, "
                + " kp.mobile_no1 as post_by_mob, kp.email_id1 as post_by_email_id1,cst.complaint_sub_type_eng "
                + " from complaint_register cr,key_person kp,complaint_sub_type cst, pole p "
                + " where p.pole_id=cr.pole_id AND p.active='Y' AND cr.complaint_sub_type_id=cst.complaint_sub_type_id and cr.active='Y' "
                + " and  cr.posted_by=kp.key_person_id and cr.complaint_register_id=(select  max(complaint_register_id) from complaint_register)";
     }

         String subject=" ";
         String bodyText=" ";
         String bodyText1=" ";
                  pstmt = connection.prepareStatement(query1);
                   //pstmt.setString(1,com_reg.getPosted_by()[i]);
                   ResultSet rset = pstmt.executeQuery();
                  /*while(rset.next()) {
                        mob_no = rset.getString("mobile_no1");
                        email_id = rset.getString("email_id1");
                       if(complaint_type==null)
                        complaint_type="";


                        messages ="We have registered "
                            + "\n  Complaint Type- " + complaint_type
                            + "\n  Complaint no-  " +rset.getInt("complaint_register_id")
                            + "\n  Pole no.- " + rset.getString("pole_no");
                         String subject = "Complaint";
                         String mail_direction = "outgoing";

                      //sendSmsToPostedBy(mob_no,messages);
                      sendMail_Posted_by(messages, subject, email_id);
                      insertMailRecords(rset.getInt("key_person_id"),rset.getInt("key_person_id"), mail_direction,rset.getInt("complaint_register_id") + "");
                        // }
                   }//while*/
                   int posted_by = 0, assigned_for = 0, complaint_register_id = 0;
                   while (rset.next()) {
                      //  com_reg.setPosted_by_name(rset.getString("posted_by_name"));
                      //  com_reg.setAssigned_for_name(rset.getString("assigned_for_name"));
                        com_reg.setMob_no1(rset.getString("post_by_mob"));
                        
                        com_reg.setPost_by_email_id1(rset.getString("post_by_email_id1"));
                        
                   //     com_reg.setComplaint_typeM(rset.getString("complaint_type"));
                        posted_by = rset.getInt("posted_by");
                        assigned_for = rset.getInt("assinged_for");
                        complaint_register_id = rset.getInt("complaint_register_id");
                        if (com_reg.getAssigned_for()[i] != null && !com_reg.getAssigned_for()[i].isEmpty()){
                            com_reg.setMob_no2(rset.getString("assigned_for_mob"));
                            com_reg.setAssigned_for_email_id1(rset.getString("assigned_for_email_id1"));
                        }

                      complaint_type=rset.getString("complaint_sub_type_eng");
                       if(complaint_type==null)
                        complaint_type="";


                        bodyText = " We have registered complaint-  "
                           + "\n no-  " +rset.getInt("complaint_register_id")
                           + "\n Pole No- " + rset.getString("pole_no")
                          + "\n  Complaint Type- " + complaint_type;

                        bodyText1 ="You have assinged "
                            + "\n  Complaint Type- " + complaint_type
                            + "\n  Complaint no-  " +rset.getInt("complaint_register_id")
                            + "\n  pole_no- " + rset.getString("pole_no");

                       // }
                    }

                //    String comp_type = com_reg.getComplaint_typeM();
               //     String posted_by_name = com_reg.getPosted_by_name();
               //     String assigned_for_name = com_reg.getAssigned_for_name();
                    String post_by_email_id1 = com_reg.getPost_by_email_id1();
                    String assigned_for_email_id1 = com_reg.getAssigned_for_email_id1();
                    String posted_by_mob_no = com_reg.getMob_no1();
                    String assigned_for_mob_no = com_reg.getMob_no2();
                    subject = "Complaint";
                //    bodyText = " We have registered complaint-  "
                //            + ",\n no  " + complaint_register_id;
                //            + ",\n Site Name " + post_by_email_id1;
                //            + ",\n  Complaint Type " + post_by_email_id1;
                //        bodyText = " We have registered complaint  " + posted_by_name
              //              + ",\nYour Complaint is  " + comp_type
             //               + ",\nand your email id is " + post_by_email_id1;

             //       bodyText1 = "Dear " + assigned_for_name
               //             + ",\nYour Complaint is  " + comp_type
                 //           + ",\nand your email id is " + assigned_for_email_id1;

               //     String messageStr = "  Dear candidate  your complaint  is " + comp_type;
              //      String messageStr1 = "  Dear candidate  your complaint  is " + comp_type;

                        String mail_direction = "outgoing";
                        sendMail_Posted_by(bodyText, subject, post_by_email_id1);
                        insertMailRecords(posted_by,posted_by, mail_direction, complaint_register_id + "");
                        if (com_reg.getAssigned_for()[i] != null && !com_reg.getAssigned_for()[i].isEmpty()){
                        sendMail_Assigned_For(bodyText1, subject, assigned_for_email_id1);
                        insertMailRecords(assigned_for,assigned_for, mail_direction, complaint_register_id + "");
                       }
                        rowsAffected = 1;

                } //end  inner if condition
               } //end  outer if condition

            }  // end for loop
       } catch (Exception e) {
        //    rowsAffected = 0;
       //     connection.rollback();
       //     connection.setAutoCommit(autocommit);
            System.out.println("Error: ComplaintRegisterModel" + e);
        }
        if (rowsAffected > 0) {
         //   connection.commit();
          //  rowsAffected = 1;
          //  connection.setAutoCommit(autocommit);
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        }
    else if(subsite_name==null){
    message = "Subsite name does not exist in record.";
            msgBgColor = COLOR_ERROR;
    }
        else {
           //  rowsAffected= 0;
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
       
            return rowsAffected;
    }

  public int reviseRecord(ComplaintRegister com_reg) {
        int rowsAffected = 0;
       //  int complaint_status_id=2;
        String query = null;
        query = " insert into complaint_register( complaint_sub_type_id,"
                + " complaint_status_id, posted_by, posted_date, complaint_date, assinged_for, "
                + " remark , complition_date , complaint_register_id , complaint_revision_no,pole_id,assign_date) "
                + " values(?, ?, ?, current_date(), ?, ?, ?, ADDDATE(? ,1) , ? , "
                + "  ( select max(c.complaint_revision_no)+1 from complaint_register  c "
                + " where c.complaint_register_id = ?),?, IF(?=0, null, current_date()))";
        String[] complaint_reg_no = com_reg.getComplaint_register_no();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_reg_no.length; i++) {

                pstmt = connection.prepareStatement(query);
                //pstmt.setInt(1, getsite_id(com_reg.getSite_name()[i]));
                pstmt.setInt(1, Integer.parseInt((com_reg.getComplaint_type()[i].trim()).split("#")[0]));
                pstmt.setInt(2, Integer.parseInt(com_reg.getStatus()[i].trim()));
             //   pstmt.setInt(3,complaint_status_id);
                pstmt.setInt(3, getkey_person_id(com_reg.getPosted_by()[i]));
                pstmt.setDate(4, convertToSqlDate(com_reg.getComplaint_date()[i]));
                if (com_reg.getAssigned_for()[i] != null && !com_reg.getAssigned_for()[i].isEmpty()) {
                    pstmt.setInt(5, getkey_person_id(com_reg.getAssigned_for()[i]));
                } else {
                    pstmt.setNull(5, java.sql.Types.INTEGER);
                }
                pstmt.setString(6, com_reg.getRemark()[i]);
                pstmt.setDate(7, convertToSqlDate(com_reg.getComplaint_date()[i]));
                pstmt.setInt(8, Integer.parseInt(complaint_reg_no[i].trim()));
                pstmt.setInt(9, Integer.parseInt(complaint_reg_no[i].trim()));
                pstmt.setInt(10, getPoleId(com_reg.getAd_sub_site_name()[i]));
                pstmt.setInt(11, getkey_person_id(com_reg.getAssigned_for()[i]));
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    String updatequery = " update complaint_register set active='N',complaint_status_id=3"
                            + " where complaint_register_id =?   and "
                            + " complaint_revision_no = ?  ";
                    pstmt = connection.prepareStatement(updatequery);
                    pstmt.setInt(1, Integer.parseInt(complaint_reg_no[i].trim()));
                    pstmt.setInt(2, com_reg.getCompalint_rev_no());
                  rowsAffected=  pstmt.executeUpdate();

                }

            }
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
            e.printStackTrace();
        }
        if (rowsAffected > 0) {
            message = "Record Revised successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot Revise the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
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


  /*  public int updateRecord(ComplaintRegister com_reg) {
        int rowsAffected = 0;
        int count = 0;
     //   boolean autocommit = false;
      //  connection.setAutoCommit(false);
        String messages=" ";
        String complaint_type=" ";
        String mob_no=" ";
        String query = " UPDATE complaint_register  "
                + " SET site_id = ?,sub_site_id=?,complaint_type_id =? ,complaint_status_id =? , posted_by= ?, posted_date = current_date(), "
                + " complaint_date =? , assinged_for=? , remark =? , complition_date =ADDDATE(? ,1)  WHERE complaint_register_id = ? and complaint_revision_no =? ";
        String[] complaint_reg_no = com_reg.getComplaint_register_no();
        PreparedStatement pstmt = null;
        try {
            for (int i = 0; i < complaint_reg_no.length; i++) {
                pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, getsite_id(com_reg.getSite_name()[i]));
                pstmt.setInt(2,Integer.parseInt(com_reg.getAd_sub_site_name()[i]));
                pstmt.setInt(3, Integer.parseInt(com_reg.getComplaint_type()[i].trim()));
                pstmt.setInt(4, Integer.parseInt(com_reg.getStatus()[i].trim()));
                pstmt.setInt(5, getKeyPersonId(com_reg.getPosted_by()[i]));
                pstmt.setDate(6, convertToSqlDate(com_reg.getComplaint_date()[i]));
                if (com_reg.getAssigned_for()[i] != null && !com_reg.getAssigned_for()[i].isEmpty()) {
                    pstmt.setInt(7, getKeyPersonId(com_reg.getAssigned_for()[i]));
                } else {
                    pstmt.setNull(7, java.sql.Types.INTEGER);
                }
                pstmt.setString(8, com_reg.getRemark()[i]);
                pstmt.setDate(9, convertToSqlDate(com_reg.getComplaint_date()[i]));
                pstmt.setInt(10, Integer.parseInt(complaint_reg_no[i]));
                pstmt.setInt(11, com_reg.getCompalint_rev_no());
                
                rowsAffected = pstmt.executeUpdate();
            if(rowsAffected>0){
              String query1= "select complaint_register_id,kp.key_person_name,concat(site_name , ' , ' ,address1) as site_name, kp.mobile_no1,ct.complaint_type"
                  + " from complaint_register cr,key_person kp,complaint_type ct,ad_site ad "
                  + " where  cr.complaint_register_id=? "
                  + " and kp.key_person_name= ?  and cr.complaint_type_id=ct.complaint_type_id and ad.site_id=cr.site_id ";
                  pstmt = connection.prepareStatement(query1);
                  pstmt.setInt(1, Integer.parseInt(complaint_reg_no[i]));
                  pstmt.setString(2,com_reg.getPosted_by()[i]);

                   ResultSet rset = pstmt.executeQuery();
                  while(rset.next()) {
                        mob_no =rset.getString("mobile_no1");

                       if(complaint_type==null)
                        complaint_type="";


                        messages ="We have updated  complaint register"
                            + "\n  Complaint Type- " + complaint_type
                            + "\n  Complaint no-  " +rset.getInt("complaint_register_id")
                            + "\n  Site Name- " + rset.getString("site_name");


                      sendSmsToPostedBy(mob_no,messages);
            }//end inner if condition

            } //end while loop
          } //end  for loop
       
        } catch (Exception e) {
            System.out.println("Error: ComplaintRegisterModel" + e);
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
*/
    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public int getNoOfRows(int status, String posted_by, String assigned_for, String complaintDateFrom, String complaintDateTo,int s_complaint_no, String delayed_work ) {
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
            String query = "select count(*)"
                    + "   from complaint_register cr "
                    + "  left join( key_person k2 ) on k2. key_person_id = cr.assinged_for "
                    + "   , complaint_status cs ,complaint_sub_type cst, complaint_type ct, pole p, "
                    + "  key_person k1 "
                    //+ "      where cr.site_id= ad.site_id "
                    + "      where cr.pole_id = p.pole_id AND p.active = 'Y' "
                    + "  and cs.complaint_status_id= cr.complaint_status_id and cr.complaint_sub_type_id ="
                    + "    cst.complaint_sub_type_id AND ct.complaint_type_id=cst.complaint_type_id and k1.key_person_id= cr.posted_by "
                    + "   and  cr.active ='Y'  and "
                    + "   if(" + status + "=0,cs.complaint_status_id like'%', cs.complaint_status_id=" + status + " ) "
                    +    " and "
                    + "  if(" + s_complaint_no + "=0,cr.complaint_register_id like'%', cr.complaint_register_id=" + s_complaint_no + " ) "
                    + "    and "
                    + "   if('" + posted_by + "'='',k1.key_person_name like'%', k1.key_person_name='" + posted_by + "') and "
                    + "     if('" + assigned_for + "'='',(k2.key_person_name like'%' or k2.key_person_name is null), k2.key_person_name='" + assigned_for + "') "
                    + "  and if(" + search_complaint_date_from + " is null,complaint_date,complaint_date>=?)"
                    + "  and if(" + search_complaint_date_to + " is null,complaint_date,complaint_date<=?)"
                    + " and if('" + delayed_work + "'= 'Y', (complition_date < SUBDATE(curdate(),0)  and cs.complaint_status_id != 3), complition_date like '%') ";

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

    public List<ComplaintRegister> showData(int lowerLimit, int noOfRowsToDisplay, int status, String posted_by, String assigned_for, String complaintDateFrom, String complaintDateTo,int s_complaint_no, String delayed_work ) {
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
                + " Order by cr.complaint_register_id desc"
                + "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
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

    public void sendSmsMail(String[] check_print, String[] complaint_reg, String sms_check, String mail_check) {
        int rowsAffected = 0;
        String subject=" ";
        String bodyText=" ";
        String  bodyText1=" ";
        String complaint_type=" ";
        try {
          //  String query = "select kp.key_person_name as posted_by_name,kp1.key_person_name as assigned_for_name,"
            //        + " kp.mobile_no1 as post_by_mob,kp1.mobile_no1 as assigned_for_mob,"
           //         + " kp.email_id1 as post_by_email_id1,kp1.email_id1 as "
           //         + " assigned_for_email_id1,ct.complaint_type from complaint_register cr,key_person kp,key_person kp1,complaint_type ct "
           //         + " where cr.complaint_register_id=? and cr.complaint_type_id=ct.complaint_type_id  and  cr.posted_by=kp.key_person_id and "
           //         + " cr.assinged_for=kp1.key_person_id ";


     String query= "select cr.posted_by, cr.assinged_for, cr.complaint_register_id,p.pole_no,kp.key_person_name as posted_by_name,kp1.key_person_name as assigned_for_name,"//concat(site_name , ' , ' ,address1) as site_name,
                    + " kp.mobile_no1 as post_by_mob,kp1.mobile_no1 as assigned_for_mob,"
                     + " kp.email_id1 as post_by_email_id1,kp1.email_id1 as "
                   + " assigned_for_email_id1,cst.complaint_sub_type_eng from complaint_register cr,key_person kp,key_person kp1,complaint_sub_type cst, pole p "
                    + " where p.pole_id=cr.pole_id AND p.active='Y' AND cr.complaint_sub_type_id=cst.complaint_sub_type_id and cr.active='Y' and  cr.posted_by=kp.key_person_id and "
                    + " cr.assinged_for=kp1.key_person_id and cr.complaint_register_id=? " ;

            PreparedStatement pstmt = null;
            ComplaintRegister com_reg = new ComplaintRegister();

            for (int i = 0; i < check_print.length; i++) {

                if (check_print[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, Integer.parseInt(complaint_reg[i]));
                    ResultSet rset = pstmt.executeQuery();
                    int posted_by = 0, assigned_for = 0, complaint_register_id = 0;
                    while (rset.next()) {
                      //  com_reg.setPosted_by_name(rset.getString("posted_by_name"));
                      //  com_reg.setAssigned_for_name(rset.getString("assigned_for_name"));
                        com_reg.setMob_no1(rset.getString("post_by_mob"));
                        com_reg.setMob_no2(rset.getString("assigned_for_mob"));
                        com_reg.setPost_by_email_id1(rset.getString("post_by_email_id1"));
                        com_reg.setAssigned_for_email_id1(rset.getString("assigned_for_email_id1"));
                   //     com_reg.setComplaint_typeM(rset.getString("complaint_type"));
                        posted_by = rset.getInt("posted_by");
                        assigned_for = rset.getInt("assinged_for");
                        complaint_register_id = rset.getInt("complaint_register_id");

                      complaint_type=rset.getString("complaint_sub_type_eng");
                       if(complaint_type==null)
                        complaint_type="";
                       

                        bodyText = " We have registered complaint-  "
                           + "\n no-  " +rset.getInt("complaint_register_id")
                           + "\n Pole No- " + rset.getString("pole_no")
                          + "\n  Complaint Type- " + complaint_type;

                       // }
                    }

                //    String comp_type = com_reg.getComplaint_typeM();
               //     String posted_by_name = com_reg.getPosted_by_name();
               //     String assigned_for_name = com_reg.getAssigned_for_name();
                    String post_by_email_id1 = com_reg.getPost_by_email_id1();
                    String assigned_for_email_id1 = com_reg.getAssigned_for_email_id1();
                    String posted_by_mob_no = com_reg.getMob_no1();
                    String assigned_for_mob_no = com_reg.getMob_no2();
                    subject = "Complaint";
                //    bodyText = " We have registered complaint-  "
                //            + ",\n no  " + complaint_register_id;
                //            + ",\n Site Name " + post_by_email_id1;
                //            + ",\n  Complaint Type " + post_by_email_id1;
                //        bodyText = " We have registered complaint  " + posted_by_name
              //              + ",\nYour Complaint is  " + comp_type
             //               + ",\nand your email id is " + post_by_email_id1;

             //       bodyText1 = "Dear " + assigned_for_name
               //             + ",\nYour Complaint is  " + comp_type
                 //           + ",\nand your email id is " + assigned_for_email_id1;

               //     String messageStr = "  Dear candidate  your complaint  is " + comp_type;
              //      String messageStr1 = "  Dear candidate  your complaint  is " + comp_type;

                    if (mail_check.equals("1")) {
                        String mail_direction = "outgoing";
                        sendMail_Posted_by(bodyText, subject, post_by_email_id1);
                        insertMailRecords(posted_by,posted_by, mail_direction, complaint_register_id + "");
                        sendMail_Assigned_For(bodyText, subject, assigned_for_email_id1);
                        insertMailRecords(assigned_for,assigned_for, mail_direction, complaint_register_id + "");
                        rowsAffected = 1;
                    }
                    if (sms_check.equals("1")) {
                        sendSmsToPostedBy(posted_by_mob_no, bodyText);
                        sendSmsToAssignedFor(assigned_for_mob_no, bodyText);
                        rowsAffected = 1;
                    }
                 //   }
                 //   }
                }  //end of outer if
            }// end of for loop
            if (rowsAffected > 0) {
                sms_mail_message = "Messages has been send  successfully.";
                sms_mail_msgBgColor = COLOR_OK;
            } else {
                sms_mail_message = "Messages has  not been send  , some error.";
                sms_mail_msgBgColor = COLOR_ERROR;
            }
     //       }
        } catch (Exception e) {

            System.out.println("SendSmsMail() Error in ComplaintRegisterModel : " + e);
        }
    }

    public void sendMail_Assigned_For(String bodyText1, String subject, String assigned_for_email_id1) {
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

    public void sendMail_Posted_by(String bodyText, String subject, String post_by_email_id1) {
        MailSmsCredentialModel mailSmsCred = new MailSmsCredentialModel(connection);
        MailBean mailBean = mailSmsCred.getMailCredentialUser();
        SendMailSmsModel sendMailSms = new SendMailSmsModel();
        List<String> setToEmailList = new ArrayList<String>();
        SmsBean smsBean = mailSmsCred.getSmsCredentialUser();

        setToEmailList.add(post_by_email_id1);
        mailBean.setToEmailList(setToEmailList);
        mailBean.setSubject(subject);
        mailBean.setBodyText(bodyText);
        sendMailSms.sendMail(mailBean);

    }

    public String sendSmsToPostedBy(String numberStr, String messageStr) {
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

    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
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

    public byte[] generateComplaintReportPdf(String jrxmlFilePath, String posted_by, String assigned_for, int status_id, String delayed_work) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("posted_by", posted_by);
        mymap.put("assigned_for", assigned_for);
        mymap.put("status_id", status_id);
        mymap.put("delayed_work", delayed_work);
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("Error: in bookingViewController generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
public ByteArrayOutputStream generateComplaintReportExcel(String jrxmlFilePath, String posted_by, String assigned_for, int status_id, String delayed_work) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        mymap.put("posted_by", posted_by);
        mymap.put("assigned_for", assigned_for);
        mymap.put("status_id", status_id);
        mymap.put("delayed_work", delayed_work);
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
  public void autoSendSmsAndMail() {
       int rowsAffected = 0;
        String subject="Complaint Delayed";
       // String bodyText=" ";
        String messages=" ";
        //String  bodyText1=" ";
        String complaint_type="";
        String posted_by_mob_no=" ";
        String assinged_for_mob_no=" ";
        String posted_by_email_id=" ";
        String assinged_for_email_id=" ";


      //  List list = new ArrayList();
        try {
            String query =  "SELECT complaint_register_id as complaint_no,cs.complaint_status,cst.complaint_sub_type,kp.key_person_name as posted_by,kp.key_person_name as assinged_for,kp.mobile_no1 as posted_by_mob_no,k2.mobile_no1 as assinged_person_mob_no"
                          + " ,k2.email_id1  as assinged_person_email_id,kp.email_id1  as posted_by_email_id FROM complaint_register cr left join( key_person k2 ) on k2. key_person_id = cr.assinged_for,complaint_status cs,complaint_sub_type cst,ad_site ad,key_person kp where cr.active='Y'"
                          +" and  complition_date <=SUBDATE(curdate(),2) and cs.complaint_status_id= cr.complaint_status_id and cs.complaint_status_id =2 and cr.complaint_sub_type_id=cst.complaint_sub_type_id and ad.site_id=cr.site_id  and kp.key_person_id=cr.posted_by ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
             //   list.add(rst.getString("complaint_no ") + "," + rst.getString("assinged_for") + "," + rst.getString("posted_by"));
                   posted_by_mob_no =rst.getString("posted_by_mob_no");
                   assinged_for_mob_no =rst.getString("assinged_person_mob_no");
                   posted_by_email_id =rst.getString("posted_by_email_id");
                   assinged_for_email_id =rst.getString("assinged_person_email_id");

                       if(complaint_type==null)
                        complaint_type="";


                        messages ="Your complaint is  pending "
                            + "\n  Complaint Type- " + rst.getString("complaint_type")
                            + "\n  Complaint no-  " +rst.getInt("complaint_no")
                            + "\n  Site Name- " + rst.getString("site_name");


                      sendSms(posted_by_mob_no,messages);
                      sendSms(assinged_for_mob_no,messages);
                      sendMail(messages, subject, posted_by_email_id);
                      sendMail(messages, subject,assinged_for_email_id);
            }
            rst.close();
        } catch (Exception e) {
            System.out.println("Error in autosendSms Mail" +e);
        }
       // return list;
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
  /* private String callURL(String strURL) {
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
    } */

  public int getPoleId(String pole_no){
      int pole_id = 0;
      String query = "SELECT pole_id FROM pole WHERE active = 'Y' AND pole_no='"+pole_no+"'";
      try{
          ResultSet rs = connection.prepareStatement(query).executeQuery();
          if(rs.next()){
              pole_id = rs.getInt("pole_id");
          }
      }catch(Exception ex){

      }
      return pole_id;
  }

  public String getSPIDNameByPole(String pole_no){
      String str = "";
      String query = "SELECT spd.switching_point_detail_id,spd.switching_point_name,spd.feeder_id FROM pole p, switching_point_detail spd WHERE spd.switching_point_detail_id = p.switching_point_detail_id AND spd.active = 'Y' AND p.active = 'Y' AND pole_no='"+pole_no+"'";
      try{
          ResultSet rs = connection.prepareStatement(query).executeQuery();
          if(rs.next()){
              str = rs.getInt("switching_point_detail_id") + "#" + rs.getInt("feeder_id") + "_" + rs.getString("switching_point_name");
          }
      }catch(Exception ex){

      }
      return str;
  }

  public Map<Integer, String> divisionList(){
      Map<Integer, String> divList = new HashMap<Integer, String>();
      int division_id = 0;
      String division_name = "";
      String query = "SELECT division_id, division_name FROM division ORDER BY division_name";
      try{
          ResultSet rs = connection.prepareStatement(query).executeQuery();
          while(rs.next()){
              division_id = rs.getInt("division_id");
              division_name = rs.getString("division_name");
              divList.put(division_id, division_name);
          }
      }catch(Exception ex){
          System.out.println("Error: in divisionList method of ComplaintRegisterModel: " + ex);
      }
      return divList;
  }

  public Map<String, String> zoneList(){
      Map<String, String> zonList = new HashMap<String, String>();
      int zone_id = 0, division_id = 0;
      String zone_name = "";
      String query = "SELECT zone_id, zone, division_id FROM zone ORDER BY zone";
      try{
          ResultSet rs = connection.prepareStatement(query).executeQuery();
          while(rs.next()){
              zone_id = rs.getInt("zone_id");
              zone_name = rs.getString("zone");
              division_id = rs.getInt("division_id");
              zonList.put(zone_id + "#" + division_id, zone_name);
          }
      }catch(Exception ex){
          System.out.println("Error: in zonList method of ComplaintRegisterModel: " + ex);
      }
      return zonList;
  }

  public Map<String, String> feederList(){
      Map<String, String> feederList = new Hashtable<String, String>();
      int feeder_id = 0, zone_id = 0;
      String feeder_name = "";
      String query = "SELECT feeder_id, feeder_name, zone_id FROM feeder ORDER BY feeder_name";
      try{
          ResultSet rs = connection.prepareStatement(query).executeQuery();
          while(rs.next()){
              feeder_id = rs.getInt("feeder_id");
              feeder_name = rs.getString("feeder_name");
              zone_id = rs.getInt("zone_id");
              feederList.put(feeder_id + "#" + zone_id, feeder_name);
          }
      }catch(Exception ex){
          System.out.println("Error: in feederList method of ComplaintRegisterModel: " + ex);
      }
      return feederList;
  }

  public Map<String, String> switchingPointList(){
      Map<String, String> spList = new HashMap<String, String>();
      int feeder_id=0, switching_point_id=0;
      String sw_name = "";
      String query = "SELECT switching_point_detail_id, feeder_id, switching_point_name FROM switching_point_detail WHERE active='Y'";
      try{
          ResultSet rs = connection.prepareStatement(query).executeQuery();
          while(rs.next()){
              feeder_id = rs.getInt("feeder_id");
              switching_point_id = rs.getInt("switching_point_detail_id");
              sw_name = rs.getString("switching_point_name");
              spList.put(switching_point_id + "#" + feeder_id, sw_name);
          }
      }catch(Exception ex){
          System.out.println("Error: in switchingPointList method of ComplaintRegisterModel: " + ex);
      }
      return spList;
  }

  public Map<Integer, String> complaintTypeList(){
        Map<Integer, String> ctList = new HashMap<Integer, String>();
        String query = "SELECT complaint_type_id, complaint_type FROM complaint_type ORDER BY complaint_type";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                ctList.put(rs.getInt("complaint_type_id"), rs.getString("complaint_type"));
            }
        }catch(Exception ex){
            System.out.println("Error: in complaintTypeList method of ComplaintSubTypeModels: " + ex);
        }
        return ctList;
    }

  public Map<String, String> complaintSubTypeList(){
        Map<String, String> ctList = new HashMap<String, String>();
        String query = "SELECT complaint_sub_type_id, complaint_type_id, complaint_sub_type FROM complaint_sub_type ORDER BY complaint_sub_type";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                ctList.put(rs.getInt("complaint_sub_type_id") + "#" + rs.getInt("complaint_type_id"), rs.getString("complaint_sub_type"));
            }
        }catch(Exception ex){
            System.out.println("Error: in complaintSubTypeList method of ComplaintSubTypeModels: " + ex);
        }
        return ctList;
    }

  public List<String> getPoleNo(String q, String sp) {
        List<String> list = new ArrayList<String>();
        int sp_id = 0;
        if(sp.equals("Select") || sp.isEmpty()){
            sp = "";
        }else sp_id = Integer.parseInt(sp);
        String query = " select pole_id, pole_no from pole where active = 'Y' AND isSwitchingPoint = 'No' "
                + " AND IF("+sp_id+"=0, switching_point_detail_id LIKE '%%', switching_point_detail_id="+sp_id+") ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_id = rset.getString("pole_id");
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
            System.out.println("getPoleNo ERROR inside ComplaintResgisterModel - " + e);
        }
        return list;
    }

  public List<String> getAllPoleNo(String q, String sp) {
        List<String> list = new ArrayList<String>();
        int sp_id = 0;
        if(sp.equals("Select") || sp.isEmpty()){
            sp = "";
        }else sp_id = Integer.parseInt(sp);
        String query = " select pole_id, pole_no from pole where active = 'Y'"
                + " AND IF("+sp_id+"=0, switching_point_detail_id LIKE '%%', switching_point_detail_id="+sp_id+") ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String pole_id = rset.getString("pole_id");
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
            System.out.println("getPoleNo ERROR inside ComplaintResgisterModel - " + e);
        }
        return list;
    }

  public List<String> getSwitchingPoleNo(String q, String sp) {
        List<String> list = new ArrayList<String>();
        int sp_id = 0;
        if(sp.equals("Select") || sp.isEmpty()){
            sp = "";
        }else sp_id = Integer.parseInt(sp);
        String query = " select p.pole_no from switching_point_detail spd "
                + " LEFT JOIN pole p ON p.switching_point_detail_id = spd.switching_point_detail_id "//AND spd.switching_rev_no = p.switching_rev_no 
                + " WHERE spd.active = 'Y' AND p.active = 'Y' AND p.isSwitchingPoint = 'Yes' "
                + " AND IF("+sp_id+"=0, p.switching_point_detail_id LIKE '%%', p.switching_point_detail_id="+sp_id+") ";
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
            System.out.println("getPoleNo ERROR inside ComplaintResgisterModel - " + e);
        }
        return list;
    }

  public List<String> getSwitchingPoint(String q, String feeder) {
        List<String> list = new ArrayList<String>();
        int feeder_id = 0;
        if(feeder.equals("Select")){
            feeder = "";
        }else feeder_id = Integer.parseInt(feeder);
        String query = " select switching_point_name from switching_point_detail spd "
                + "  "//AND spd.switching_rev_no = p.switching_rev_no
                + " WHERE spd.active = 'Y' "
                + " AND IF("+feeder_id+"=0, spd.feeder_id LIKE '%%', spd.feeder_id="+feeder_id+") ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String switching_point_name = rset.getString("switching_point_name");
                if (switching_point_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(switching_point_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Switching Pole Exists.");
            }
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside ComplaintResgisterModel - " + e);
        }
        return list;
    }

  public String getSwitchingPointID(String sp_name) {
        String str = "";
        /*int feeder_id = 0;
        if(feeder.equals("Select")){
            feeder = "";
        }else feeder_id = Integer.parseInt(feeder);*/
        String query = " select feeder_id, switching_point_detail_id from switching_point_detail spd "
                + "  "//AND spd.switching_rev_no = p.switching_rev_no
                + " WHERE spd.active = 'Y' "
                + " AND IF('"+sp_name+"'='', spd.switching_point_name LIKE '%%', spd.switching_point_name='"+sp_name+"') ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            
            while (rset.next()) {    // move cursor from BOR to valid record.
                str = rset.getInt("switching_point_detail_id") + "#" + rset.getInt("feeder_id") + "_";
                
            }
            
        } catch (Exception e) {
            System.out.println("getPoleNo ERROR inside ComplaintResgisterModel - " + e);
        }
        return str;
    }

}
